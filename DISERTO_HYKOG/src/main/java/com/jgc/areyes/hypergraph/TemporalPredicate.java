package com.jgc.areyes.hypergraph;

public enum TemporalPredicate {

    occurs_on("http://www.ontologylibrary.mil/CommonCore/Upper/ExtendedRelationOntology#occurs_on"),
    phenomenonTime("http://www.w3.org/ns/sosa/phenomenonTime"),
    observed_in ("http://www.semanticweb.org/user/ontologies/2019/2/untitled-ontology-35#observed_in");

    private String iriPredicate;

    TemporalPredicate(String iriPredicate) {
        this.iriPredicate = iriPredicate;
    }

    public String getIriPredicate() {
        return iriPredicate;
    }
}
