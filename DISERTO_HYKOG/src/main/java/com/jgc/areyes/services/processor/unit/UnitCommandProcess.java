package com.jgc.areyes.services.processor.unit;

import org.eclipse.rdf4j.repository.Repository;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * 
 * @author masmoudi
 *
 */
public class UnitCommandProcess {

	private final String key; // key of Mdi

	private final Object values; // value of Mdi

	private final OWLOntology ontology;

	private String thesaurusPath;

	private OWLClass ci; // result of first process = class from the ontology

	private OWLClass cs; // class from the ontology of the Mds (=ci if Mdi =
							// Mds)

	private Object rdfQuad; // result of second process (type : RDFquad)

	private OWLObjectProperty ri; // result of third process (type : objectProperty or
						// dataProperty)

	private String source;

	private String rdfQuadFilePath;
	private Repository repo;

	/**
	 * Constructor for Mdi
	 * 
	 * @param key
	 * @param values
	 * @param source
	 * @param ontology
	 */
	public UnitCommandProcess(String key, Object values, String source,
							  String rdfQuadFilePath, OWLOntology ontology, String thesaurusPath,
							  OWLClass cs, Repository repo) {
		super();
		this.key = key;
		this.values = values;
		this.source = source;
		this.ontology = ontology;
		this.rdfQuadFilePath = rdfQuadFilePath;
		this.cs = cs;
		this.setThesaurusPath(thesaurusPath);
		this.repo = repo;
	}

	/**
	 * Constructor for Mds
	 *  @param key
	 * @param values
	 * @param source
	 * @param rdfQuadFilePath
	 * @param ontology
	 * @param repo
	 */
	public UnitCommandProcess(String key, Object values, String source,
							  String rdfQuadFilePath, OWLOntology ontology, String thesaurusPath, Repository repo) {
		super();
		this.key = key;
		this.values = values;
		this.ontology = ontology;
		this.source = source;
		this.rdfQuadFilePath = rdfQuadFilePath;
		this.setThesaurusPath(thesaurusPath);
		this.repo = repo;
	}

	public OWLClass getCi() {
		return ci;
	}

	public void setCi(OWLClass ci) {
		this.ci = ci;
	}

	public Object getRdfQuad() {
		return rdfQuad;
	}

	public void setRdfQuad(Object rdfQuad) {
		this.rdfQuad = rdfQuad;
	}

	public OWLObjectProperty getRi() {
		return ri;
	}

	public void setRi(OWLObjectProperty ri) {
		this.ri = ri;
	}

	public OWLOntology getOntology() {
		return ontology;
	}

	public String getKey() {
		return key;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRdfQuadFilePath() {
		return rdfQuadFilePath;
	}

	public void setRdfQuadFilePath(String rdfQuadFilePath) {
		this.rdfQuadFilePath = rdfQuadFilePath;
	}

	public OWLClass getCs() {
		return cs;
	}

	public void setCs(OWLClass cs) {
		this.cs = cs;
	}

	public String getThesaurusPath() {
		return thesaurusPath;
	}

	public void setThesaurusPath(String thesaurusPath) {
		this.thesaurusPath = thesaurusPath;
	}

	public Repository getRepo(){
		return repo;
	}
}
