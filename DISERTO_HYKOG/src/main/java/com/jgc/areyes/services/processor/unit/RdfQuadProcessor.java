package com.jgc.areyes.services.processor.unit;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.eclipse.rdf4j.model.IRI;
import org.eclipse.rdf4j.model.Literal;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.model.vocabulary.RDFS;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryResult;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFHandler;
import org.eclipse.rdf4j.rio.Rio;
import org.eclipse.rdf4j.sail.memory.MemoryStore;
import org.eclipse.rdf4j.sail.nativerdf.NativeStore;

/**
 * @author masmoudi
 */
public class RdfQuadProcessor implements UnitProcessor {

    @Override
    public void process(UnitCommandProcess command) {
        System.out
                .println("******************Start RDF quad processor*******************");

        //repo.setDataDir(new File(command.getRdfQuadFilePath()).getParentFile());
        RepositoryConnection conn = command.getRepo().getConnection();
        ValueFactory factory = conn.getValueFactory();
        String exns = "http://example.org/";
        IRI namedGraph = factory.createIRI(exns, command.getSource());
        IRI subject = factory.createIRI(command.getCi().toStringID());
        Literal object = factory.createLiteral(command.getKey());
        if (!conn.hasStatement(subject, RDFS.LABEL, object, false, namedGraph)) {
            conn.add(subject, RDFS.LABEL, object, namedGraph);
            FileWriter out;
            // conn.getStatements(null, null, null, namedGraph);
            // Export all statements in the context to System.out, in RDF/XML
            // format

            try {
                out = new FileWriter(command.getRdfQuadFilePath(), true);
                RDFHandler writer = Rio.createWriter(RDFFormat.TURTLE, out);
                conn.export(writer, namedGraph);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       /*  System.out.println("--------------------------------------------------------------------");
        RepositoryResult<Statement> statements = conn.getStatements(null, null, null, false);
       System.out.println("\ngetStatements: All triples in all contexts: " + (conn.size()));
        while (statements.hasNext()) {
            System.out.println(statements.next());
        }
        System.out.println("--------------------------------------------------------------------");
    */
    }


}
