package it.polito.tdp.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;


import it.polito.tdp.db.EventsDao;
import it.polito.tdp.db.EventsDaoMap;

public class ModelWithMap {
	
	Graph<Integer, DefaultWeightedEdge> graph;
	EventsDao dao;
	List<Event> eventiDiAnno;
	List<Integer> vertici;
	
	//con IDMap
	EventsDaoMap daoMap;
	EventIdMap eventIdMap;
	List<Event> eventi;
	
	

	public ModelWithMap() {
		
		this.graph= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao = new EventsDao();
		this.eventiDiAnno=new ArrayList<>();
		this.vertici= new ArrayList<>();
		
		//Con IDMap
		daoMap= new EventsDaoMap();
		eventIdMap = new EventIdMap();
		eventi = daoMap.listAllEvents(eventIdMap);
		System.out.println("size Lista eventi: " +eventi.size());
		System.out.println("size IdMap eventi: " + eventIdMap.getMap().values().size());

		
		
		
	}
	
	

}
