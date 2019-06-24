package it.polito.tdp.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model= new Model ();
		
		model.createGraph(2015);
		model.trovaAdiacenti(2);
		
	}

}
