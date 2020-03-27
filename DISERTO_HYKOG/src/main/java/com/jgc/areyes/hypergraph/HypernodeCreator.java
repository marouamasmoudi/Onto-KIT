package com.jgc.areyes.hypergraph;

import com.jgc.areyes.services.processor.ProcessorInput;
import com.jgc.areyes.services.processor.unit.UnitCommandProcess;
import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.nativerdf.NativeStore;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;

import java.io.File;

import static com.jgc.areyes.services.Constants.*;

public class HypernodeCreator {

    private OWLClass cs;

    public IRI createHypernode(ProcessorInput command) {
        System.out
                .println("******************Generate Hypernode*******************");

        File dataDir = new File("D:\\Intellig_workspace\\DISERTOworkspace\\jfilechooser-sample\\Resources\\HypergraphStore\\");
        Repository repo = new SailRepository(new NativeStore(dataDir));
        repo.initialize();
        RepositoryConnection conn = repo.getConnection();
        ValueFactory f = conn.getValueFactory();
        String prefixHypernode = "http://example.org/hypernode/";
        String prefixObservationHyperedge = "http://example.org/ObservationHyperedge/";
        String prefixSpatialHyperedge = "http://example.org/SpatialHyperedge/";
        String prefixTemporalHyperedge = "http://example.org/TemporalHyperedge/";
        String namespace2 = "http://example.org/";

        // extract cs
        cs = command.getListCommandProcess().get(0).getCs();

        // Create URI for resource cs,
        IRI csIRI = f.createIRI(cs.getIRI().getIRIString());

        //Create Literal for file path
        int count = command.getHyperNodeCount();
        Literal pathFile = f.createLiteral(RESOURCE_REPO + "RML mappings\\"+MAPPING_FILE_NAME+count+MAPPING_FILE_EXTENSION);

        // Create URIs to identify the named contexts = hypernode label.
        IRI hypernodeLabel = f.createIRI(prefixHypernode, getClassName(cs)+"/H"+count);
        IRI spatialHyperedgeLabel = f.createIRI(prefixSpatialHyperedge, getClassName(cs));
        IRI temporalHyperedgeLabel = f.createIRI(prefixTemporalHyperedge, getClassName(cs));
        IRI observationHyperedgeLabel = f.createIRI(prefixObservationHyperedge, getClassName(cs));
        IRI iN = f.createIRI(namespace2, "In");
        IRI pathPredicate = f.createIRI(namespace2, "path");
        for (UnitCommandProcess unitCommand : command
                .getListCommandProcess()) {
            OWLObjectProperty ri = unitCommand.getRi();

            OWLClass ci = unitCommand.getCi();

            // Create URIs for resources, predicates and classes.
            IRI ciIRI = f.createIRI(ci.getIRI().getIRIString());
            if (ri != null) {
                IRI riIRI = f.createIRI(ri.getIRI().getIRIString());

                // Assemble new statements and add them to the context.
                conn.add(csIRI, riIRI, ciIRI, hypernodeLabel);
                // if ri ∈ spatial EM ∈ ES
                for (SpatialPredicate iri : SpatialPredicate.values()) {
                    if (riIRI.toString().equalsIgnoreCase(iri.getIriPredicate())) {
                        //hyperedgesCreator.spatialHyperedgeCreate();
                        // Assemble new statements and add them to the context.
                        conn.add(ciIRI, iN, hypernodeLabel, spatialHyperedgeLabel);
                        break;
                    }

                }
                // if ri ∈ temporal EM ∈ ET
                for (TemporalPredicate iri : TemporalPredicate.values()) {
                    if (riIRI.toString().equalsIgnoreCase(iri.getIriPredicate())) {
                        // Assemble new statements and add them to the context.
                        conn.add(ciIRI, iN, hypernodeLabel, temporalHyperedgeLabel);
                    }

                }
            }
        }
        /*
        Add the hypernode EM to the appropriate Observation hyperedge
        Rule :
        (HypernodeIRI, http://example.org/path ,"PATHfile")[ObservationHyperedge IRI]
         */
        conn.add(hypernodeLabel,pathPredicate,pathFile,observationHyperedgeLabel);

        repo.shutDown();
        return hypernodeLabel;
    }

    private String getClassName(OWLClass owlClass) {
        if (owlClass != null) {
            String owlClassIRI = owlClass.toStringID();
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
}
