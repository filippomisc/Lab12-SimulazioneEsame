package it.polito.tdp.model;


public class Adiacenti implements Comparable<Adiacenti>{
	
	
	
	public Adiacenti(Integer adiacente, double distanza) {
		super();
		this.adiacente = adiacente;
		this.distanza = distanza;
	}
	public Integer adiacente;
	public double distanza;
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Adiacenti [adiacente=");
		builder.append(adiacente);
		builder.append(", distanza=");
		builder.append(distanza);
		builder.append("]");
		return builder.toString();
	}


	@Override
	public int compareTo(Adiacenti o) {
		double result = this.distanza-o.distanza;
		if(result<0)
			return -1;
		else if(result>0)
			return 1;
		else
			return 0;
	}

}
