package com.jgc.areyes.services.queryProcessing;

import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.sail.memory.MemoryStore;

import java.io.File;
import java.io.IOException;

import static com.jgc.areyes.services.Constants.RESOURCE_REPO;


public class SearcherInSpatialRDF implements SearcherInRDF {
    @Override
    public String translateSpatialRepresentation(String querySpatialValue, String newSpatialRepresentation) {
        String newValue = null;
        /**
         * Connection to the S RDF STore
         */

        Repository db = new SailRepository(new MemoryStore());
        db.initialize();

        // Open a connection to the database
        RepositoryConnection conn = db.getConnection();

        File file = new File(RESOURCE_REPO+"SrdfStore.n3");
        // add the RDF data from the inputstream directly to our database
        try {
            // x = city
            conn.add(file, "http://example.org/", RDFFormat.N3);
            String queryString = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>\n" +
                    "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
                    "PREFIX memon: <http://www.semanticweb.org/lenovo/ontologies/2017/10/>\n"+
                    "PREFIX dbr:<http://dbpedia.org/resource/>\n" +
                    "SELECT DISTINCT ?labelX WHERE { \n " +
                    "?x rdf:type <" + newSpatialRepresentation + ">. \n" +
                    "?x ?p ?c .\n" +
                    "?c rdfs:label \"" + querySpatialValue + "\"@en .\n"+
                    "?x rdfs:label ?labelX .\n" +
                    "FILTER (langMatches( lang(?labelX), \"en\" ) )} ";
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    BindingSet bindingSet = result.next();
                    Value valueOfY = bindingSet.getValue("labelX");
                    // do something interesting with the values here...
                    newValue = valueOfY.stringValue();
                    System.out.println("city "+newValue);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newValue;
    }
}
