package com.jgc.areyes.hypergraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Class that contains the pattern of a queryProcessing for one var
 * Exp: subject: memon:precipitation
 * predicate:occured at
 * object: memon:City
 */
public class QueryPatternSingle {
    /*
    The class contains only one subject
     */
    private String subject;
    /*
    The class can contain many Predicate Object couples
     */
    private Map<String, ArrayList<String>> keyPredicateObject;

    private String spatialHyperedgeIRI;
    private String temporalHyperedgeIRI;
    private String observationHyperedgeIRI;

    /**
     * Methods
     */
    public QueryPatternSingle() {
        keyPredicateObject = new HashMap<String, ArrayList<String>>();
    }

    public Map<String, ArrayList<String>> getKeyPredicateObject() {
        return keyPredicateObject;
    }

    public void setKeyPredicateObject(Map<String, ArrayList<String>> keyPredicateObject) {
        this.keyPredicateObject = keyPredicateObject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }


    public void setSpatialPredicateObject(String predicate, String object) {
        ArrayList<String> predicateObject = new ArrayList<String>();
        predicateObject.add(0, predicate);
        predicateObject.add(1, object);
        keyPredicateObject.put("spatial", predicateObject);
    }

    public void settemporalPredicateObject(String predicate, String object) {
        ArrayList<String> predicateObject = new ArrayList<String>();
        predicateObject.add(0, predicate);
        predicateObject.add(1, object);
        keyPredicateObject.put("temporal", predicateObject);
    }

    public void put(String predicate, String object) {
        ArrayList<String> predicateObject = new ArrayList<String>();
        predicateObject.add(0, predicate);
        predicateObject.add(1, object);
        keyPredicateObject.put("", predicateObject);
    }

    public String queryStrBuilder() {
        String spatialRepresentation = keyPredicateObject.get("spatial").get(1); //For  now just one spatial representation
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT ?em ?path ?spatialobj WHERE {");
        // builder.append(" GRAPH <").append(spatialHyperedgeIRI).append("> { <").append(spatialRepresentation).append("> ?p ?em}");
        builder.append(" GRAPH <").append(spatialHyperedgeIRI).append("> { ").append("?spatialobj ?p ?em}");
        // builder.append(" GRAPH <").append(temporalHyperedgeIRI).append("> { ").append("?temporalobj ?p ?em} }");
        builder.append(" GRAPH <").append(observationHyperedgeIRI).append("> { ").append("?em ?p2 ?path} }");
        return builder.toString();
    }

    public String getSpatialHyperedgeIRI() {
        return spatialHyperedgeIRI;
    }

    public void setSpatialHyperedgeIRI(String spatialHyperedgeIRI) {
        this.spatialHyperedgeIRI = spatialHyperedgeIRI;
    }

    public String getTemporalHyperedgeIRI() {
        return temporalHyperedgeIRI;
    }

    public void setTemporalHyperedgeIRI(String temporalHyperedgeIRI) {
        this.temporalHyperedgeIRI = temporalHyperedgeIRI;
    }

    public String getObservationHyperedgeIRI() {
        return observationHyperedgeIRI;
    }

    public void setObservationHyperedgeIRI(String observationHyperedgeIRI) {
        this.observationHyperedgeIRI = observationHyperedgeIRI;
    }

    public void setSpatialValue(String stringValue) {
        keyPredicateObject.get("spatial").add(2, stringValue);
    }

    public void setTemporalValue(String stringValue) {
        keyPredicateObject.get("temporal").add(2, stringValue);
    }
}
