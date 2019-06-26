package it.polito.tdp.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;

import it.polito.tdp.db.EventsDao;
public class Model {
	
	Graph<Integer, DefaultWeightedEdge> graph;
	EventsDao dao;
	List<Event> eventiDiAnno;
	List<Integer> vertici;
	

	public Model() {
		
		this.graph= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao = new EventsDao();
		this.eventiDiAnno=new ArrayList<>();
		this.vertici= new ArrayList<>();
	}
	
	
	public void createGraph(int anno) {
		
		this.vertici=dao.allVerticesAnno(anno);
		Graphs.addAllVertices(this.graph,vertici);
		
		
		
		
		for (Integer verticeP : this.graph.vertexSet()) {
			for (Integer verticeA : this.graph.vertexSet()) {
				if(!verticeA.equals(verticeP)) {
					//quando l'arco non è orientato bisogna assicurarsi di settarlo una volta 
					//sola altrimenti, ci ridà null pointer exception
					if(this.graph.getEdge(verticeP, verticeA)==null) {
						Graphs.addEdge(graph, verticeP, verticeA, calcolaPeso(verticeP, verticeA, anno));

					}
				}
				
			}
			
		}
		System.out.println("grafo creato!");
		System.out.println("vertici: " + this.graph.vertexSet().size());
		System.out.println("archi: " + this.graph.edgeSet().size());
	}


	public double calcolaPeso(Integer verticeP, Integer verticeA, int anno) {
		LatLng pP=dao.calcolaAVGLatLong(verticeP,anno);
		LatLng pA=dao.calcolaAVGLatLong(verticeA,anno);
		
		return 		LatLngTool.distance(pP, pA, LengthUnit.KILOMETER);
	}
	
	public List<Adiacenti> trovaAdiacenti(Integer vertice){
		List<Integer> adiacenti= new ArrayList<>();
		List<Adiacenti> result= new ArrayList<>();
		
		adiacenti = Graphs.neighborListOf(this.graph, vertice);
		for ( Integer a : adiacenti) {
			
			double peso =this.graph.getEdgeWeight(this.graph.getEdge(vertice, a));
			
			result.add(new Adiacenti(a, peso));
		
		}
		
		Collections.sort(result);
		System.out.println(result.toString());
		return result;
		
		
	}


	public List<Integer> getRaggiungibili(int distretto){
		List<Integer> raggiungibili =new ArrayList<>();
		
		BreadthFirstIterator<Integer, DefaultWeightedEdge> bfIterator = new BreadthFirstIterator<Integer, DefaultWeightedEdge>(this.graph, distretto);
		
		bfIterator.next();
		
		while (bfIterator.hasNext()) {
			raggiungibili.add(bfIterator.next());
		}
		
		return raggiungibili;
	}
	
	
	public List<Integer> getVertici() {
		return vertici;
	}


	public List<Integer> getAnni() {
		return dao.listAnni();
	}
}
