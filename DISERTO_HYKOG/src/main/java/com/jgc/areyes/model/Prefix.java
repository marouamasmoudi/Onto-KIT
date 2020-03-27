package com.jgc.areyes.model;

public enum Prefix {

	obo("http://purl.obolibrary.org/obo/"),
	geo("http://www.cubrc.org/ontologies/KDD/Mid/GeospatialOntology#"),
	memon("http://www.semanticweb.org/lenovo/ontologies/2017/10/"),
	pfa ("http://www.semanticweb.org/user/ontologies/2019/2/untitled-ontology-35#"),
	qa ("http://www.ontologylibrary.mil/CommonCore/Mid/QualityOntology#");
	
	
	private String prefix ;
	Prefix(String prefix){
		this.setPrefix(prefix);
		
		
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	
}
