package com.jgc.areyes.services.queryProcessing;


import com.jgc.areyes.hypergraph.HypernodeModel;
import com.jgc.areyes.hypergraph.QueryPatternSingle;
import com.jgc.areyes.hypergraph.SpatialPredicate;
import com.jgc.areyes.hypergraph.TemporalPredicate;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.vocabulary.RDF;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.query.algebra.StatementPattern;
import org.eclipse.rdf4j.query.algebra.TupleExpr;
import org.eclipse.rdf4j.query.algebra.Var;
import org.eclipse.rdf4j.query.algebra.helpers.StatementPatternCollector;
import org.eclipse.rdf4j.query.parser.ParsedQuery;
import org.eclipse.rdf4j.query.parser.sparql.SPARQLParser;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.eclipse.rdf4j.sail.nativerdf.NativeStore;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.jgc.areyes.services.Constants.RESOURCE_REPO;


public class QueryProcessing {
    private String textArea = "";
    private String queryStr;
    private ParsedQuery query;
    private Set<QueryPatternSingle> querySchemaPatterns;
    private String prefixObservationHyperedge = "http://example.org/ObservationHyperedge/";
    private String prefixSpatialHyperedge = "http://example.org/SpatialHyperedge/";
    private String prefixTemporalHyperedge = "http://example.org/TemporalHyperedge/";
    private Set<HypernodeModel> setHypernodes = new HashSet<>();

    /**
     * The whole queryProcessing processing
     *
     * @param sparqlQuery
     */
    public QueryProcessing(String sparqlQuery) {
       long tempsDebut, tempsFin;
       double seconds;
        tempsDebut = System.currentTimeMillis();

        this.queryStr = sparqlQuery;

        //Process 1 : Translate the STR queryProcessing to parsed queryProcessing
        query = translateStrToQuery();
        //Process 2 : GET schema pattern of the queryProcessing (EM pattern)
        querySchemaPatterns = getSchemaQuery();
        //Process 3
        queryHypergraph(); //to extract the other hypernodes (EM)
        tempsFin = System.currentTimeMillis();
        seconds = (tempsFin - tempsDebut) / 1000F;
        System.out.println("Opération d'extraction d'hypernoeuds effectuée en: "+ Double.toString(seconds) + " secondes.");
        tempsDebut = System.currentTimeMillis();
        //Foreach EM
        //Process 4
        for (HypernodeModel hypernode : setHypernodes
        ) {
            SubQueryRewritor subQueryRewritor = new SubQueryRewritor();
            String subquery = subQueryRewritor.rewriteSubQuery(hypernode);
            //Process 5
            generateRDFtripletsAndExecuteQuery(subquery,hypernode.getHypernodePath());
        }
        tempsFin = System.currentTimeMillis();
        seconds = (tempsFin - tempsDebut) / 1000F;



    }


    private void generateRDFtripletsAndExecuteQuery( String subquery, String hypernodePath) {

        RDFTriplesGenerator rdfTriplesGenerator = new RDFTriplesGenerator();
        rdfTriplesGenerator.setHypernodePath(hypernodePath);
        File file = rdfTriplesGenerator.generateRDFTriples();
        try {
            Repository repository = new SailRepository(new MemoryStore());
            repository.initialize();
            final RepositoryConnection connection = repository.getConnection();

            connection.add(file, null, RDFFormat.TURTLE);

        TupleQuery tupleQuery = connection.prepareTupleQuery(QueryLanguage.SPARQL, subquery);
        try (TupleQueryResult resultOfSubQuery = tupleQuery.evaluate()) {
            while (resultOfSubQuery.hasNext()) {  // iterate over the result
                BindingSet bindingSet = resultOfSubQuery.next();
                Value valueOfX = bindingSet.getValue("x");
                // do something interesting with the values here...
                System.out.println("queryProcessing result");
                textArea = textArea + valueOfX;
                System.out.println(valueOfX);
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Set<String> queryHypergraph() {

        File dataDir = new File(RESOURCE_REPO + "HypergraphStore\\");
        Repository repo = new SailRepository(new NativeStore(dataDir));
        repo.initialize();
        RepositoryConnection conn = repo.getConnection();
        for (QueryPatternSingle queryPatternSingle : querySchemaPatterns
        ) {
            String query = queryPatternSingle.queryStrBuilder();
            TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, query);
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) {  // iterate over the result
                    HypernodeModel hypernodeModel = new HypernodeModel();
                    hypernodeModel.setHypernodeSubject(queryPatternSingle.getSubject());
                    hypernodeModel.setSpatialPredicate(queryPatternSingle.getKeyPredicateObject().get("spatial").get(0));
                    hypernodeModel.setSpatialQueryValue(queryPatternSingle.getKeyPredicateObject().get("spatial").get(2));
                    setHypernodes.add(hypernodeModel);
                    BindingSet bindingSet = result.next();
                    Value valueOfX = bindingSet.getValue("em");
                    // do something interesting with the values here...
                    System.out.println("em");
                    System.out.println(valueOfX);
                    hypernodeModel.setHypernodeIRI(valueOfX.stringValue());
                    Value valueOfY = bindingSet.getValue("path");
                    // do something interesting with the values here...
                   // System.out.println("path");
                   // System.out.println(valueOfY);
                    hypernodeModel.setHypernodePath(valueOfY.stringValue());
                    Value valueOfZ = bindingSet.getValue("spatialobj");
                   // System.out.println("spatialobj");
                   // System.out.println(valueOfZ);
                    hypernodeModel.setSpatialObject(valueOfZ.stringValue());
                }
            }
        }


        return null;
    }

    /**
     * Method to translate Query String to Parsed Query
     *
     * @return
     */
    private ParsedQuery translateStrToQuery() {
        SPARQLParser parser = new SPARQLParser();
        ParsedQuery query = parser.parseQuery(queryStr, null);
        return query;
    }

    private Set<QueryPatternSingle> getSchemaQuery() {
        //queryProcessing patterns is the set of Patterns (queryProcessing pattern single) for all the vars of the queryProcessing
        Set<QueryPatternSingle> queryPatterns = new HashSet<>();
        List<StatementPattern> triplePatterns = StatementPatternCollector.process(query.getTupleExpr());
        //Extract Vars (?x) from Select ..
        TupleExpr te1 = query.getTupleExpr();
        Set<String> vars = te1.getBindingNames();

        for (String var : vars) {
            //for each var, we create a patternSingle for the input queryProcessing
            QueryPatternSingle patternSingle = new QueryPatternSingle();
            queryPatterns.add(patternSingle);
            String property = null;
            //Extract tp from Where ...
            for (StatementPattern tp : triplePatterns) {
                Var subject = tp.getSubjectVar();
                Var predicate = tp.getPredicateVar();
                Var object = tp.getObjectVar();
                String objectConcept = null;

                //If subject is var && predicate = rdf:type  => get object
                if (!subject.isConstant() && subject.getName().equalsIgnoreCase(var) && predicate.getValue().stringValue().equalsIgnoreCase(RDF.TYPE.stringValue())) {


                    patternSingle.setSubject(object.getValue().stringValue());
                    property = getClassName(object.getValue().stringValue());
                    patternSingle.setObservationHyperedgeIRI(prefixObservationHyperedge + property);
                }
                // If subject is var && predicate != rdf:type => get object
                else if (!subject.isConstant() && subject.getName().equalsIgnoreCase(var) && !predicate.getValue().stringValue().equalsIgnoreCase(RDF.TYPE.stringValue())) {
                    //if predicate is spatial
                    for (SpatialPredicate iri : SpatialPredicate.values()) {
                        if (predicate.getValue().stringValue().equalsIgnoreCase(iri.getIriPredicate())) {
                            objectConcept = getOntologyConceptFromInstance(true, object.getValue().stringValue());
                            patternSingle.setSpatialPredicateObject(predicate.getValue().stringValue(), objectConcept);
                            patternSingle.setSpatialValue(object.getValue().stringValue());
                            patternSingle.setSpatialHyperedgeIRI(prefixSpatialHyperedge + property);
                        }
                    }
                    //if predicate is temporal
                    for (TemporalPredicate temporalPredicate : TemporalPredicate.values()) {
                        if (predicate.getValue().stringValue().equalsIgnoreCase(temporalPredicate.getIriPredicate())) {
                            objectConcept = getOntologyConceptFromInstance(false, object.getValue().stringValue());
                            patternSingle.settemporalPredicateObject(predicate.getValue().stringValue(), objectConcept);
                            patternSingle.setTemporalHyperedgeIRI(prefixTemporalHyperedge + property);
                        }

                    }

                    patternSingle.put(predicate.getValue().stringValue(), objectConcept);
                    //If subject is not var


                }

            }
        }
        return queryPatterns;
    }

    private String getOntologyConceptFromInstance(boolean spatialContext, String valeur) {
        String ontologyConcept = null;
        //if true <=> spatial context => ask S_RDF_Store with a queryProcessing
    /*
    We want to know the concept of the instance
    Exp : valeur: Tunis OWLClass: City
    */

        Repository db = new SailRepository(new MemoryStore());
        db.initialize();

        // Open a connection to the database
        RepositoryConnection conn = db.getConnection();

        File file = new File("D:\\Intellig_workspace\\DISERTOworkspace\\jfilechooser-sample\\Resources\\Tunis.n3");
        // add the RDF data from the inputstream directly to our database
        try {
            conn.add(file, "http://example.org/", RDFFormat.N3);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String queryString = "PREFIX dbpedia-owl: <http://dbpedia.org/ontology/>" +
                "PREFIX geo: <http://www.w3.org/2003/01/geo/wgs84_pos#>" +
                "SELECT ?type WHERE { " +
                "?c rdf:type ?type ; \n" +
                "            rdfs:label \"" + valeur +
                "\"@en ; \n" +
                "} ";
        TupleQuery tupleQuery = conn.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {  // iterate over the result
                BindingSet bindingSet = result.next();
                Value valueOfY = bindingSet.getValue("type");
                // do something interesting with the values here...
                ontologyConcept = valueOfY.stringValue();
                System.out.println("ontologyConcept" + valueOfY.stringValue());
            }
        }

        // Else <=> temporal context => ask T_RDF_Store
        return ontologyConcept;
    }


    private String translateTemporalRepresentation(String oldValue, String oldRepresentation, String newRepresentation) {
        String newValue = null;
        /**
         * Connection to the T RDF Store
         */

        Repository db = new SailRepository(new MemoryStore());
        db.initialize();

        // Open a connection to the database
        RepositoryConnection conn = db.getConnection();

        File file = new File("D:\\Intellig_workspace\\DISERTOworkspace\\jfilechooser-sample\\Resources\\T_RDF_Store.n3");
        // add the RDF data from the inputstream directly to our database
        try {
            conn.add(file, "http://example.org/", RDFFormat.N3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newValue;
    }


    private String getClassName(String owlClassIRI) {
        if (owlClassIRI != null) {
            int indexslash = owlClassIRI.lastIndexOf('/');
            int indexdiez = owlClassIRI.lastIndexOf('#');
            String className;
            if (indexdiez > indexslash) {
                className = owlClassIRI.substring(indexdiez + 1);

            } else {
                className = owlClassIRI.substring(indexslash + 1);

            }
            return className;
        }
        return null;
    }

    public String getTextArea() {
        return textArea;
    }
}