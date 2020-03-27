package com.jgc.areyes.hypergraph;

public enum SpatialPredicate {

    occurs_at("http://www.ontologylibrary.mil/CommonCore/Upper/ExtendedRelationOntology#occurs_at"),
    observed_at("http://www.semanticweb.org/lenovo/ontologies/2017/10/memon_00001139")
    ;

    private String iriPredicate;

    SpatialPredicate(String iriPredicate) {
        this.iriPredicate=iriPredicate;
    }

    public String getIriPredicate() {
        return iriPredicate;
    }
}
