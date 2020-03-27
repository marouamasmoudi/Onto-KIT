package com.jgc.areyes.services.queryProcessing;

import com.jgc.areyes.hypergraph.HypernodeModel;

public class SubQueryRewritor {

    public String rewriteSubQuery(HypernodeModel hypernode) {

        SearcherInRDF searcherInSpatialRDF = new SearcherInSpatialRDF();
        String newSpatialValue = searcherInSpatialRDF.translateSpatialRepresentation(hypernode.getSpatialValue(), hypernode.getSpatialObject());

        /*
    For example from : Select ?p where {?p rdf:type memon:precipitation . ?p memon:occurs_at "xxx"}
                To : Select ?p Where {?p rdf:type memon:precipitation . ?p memon:occurs_at ?latlong . ?latlong ex:lat "xx.xx" . ?latlong ex:long "xx.xx"}
     */

        StringBuilder subQuerybuilder = new StringBuilder();
        subQuerybuilder.append("SELECT ?x WHERE {").append("?x rdf:type <").append(hypernode.getHypernodeSubject()).append("> .");
        subQuerybuilder.append(" ?x <").append(hypernode.getSpatialPredicate()).append("> \"").append(newSpatialValue).append("\" }");
        System.out.println("the subquery :");
        System.out.println(subQuerybuilder.toString());
        return subQuerybuilder.toString();
    }
}
