package it.polito.tdp.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model= new Model ();
		ModelWithMap modelWithMap= new ModelWithMap();
		
		model.createGraph(2015);
		model.trovaAdiacenti(2);
		
		
	}

}
