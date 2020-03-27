package com.jgc.areyes.services.processor.unit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.rdf4j.model.Model;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.Rio;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.search.EntitySearcher;

/**
 * 
 * @author masmoudi
 *
 */
public class MappingWithOntologyProcessor implements UnitProcessor {

	private static OWLOntologyManager manager = OWLManager
			.createOWLOntologyManager();
	private OWLDataFactory df = manager.getOWLDataFactory();

	@Override
	public void process(UnitCommandProcess command) {

		System.out
				.println("******************Start mapping with ontology processor*******************");
		boolean mdiExist = false;

		mdiExist = extractClassFromOntology(command, command.getKey(), mdiExist);

		/**
		 * mdi does not exist in the ontology
		 */

		if (!mdiExist) {
			extractTermFromThesau(command);

		}
	}

	private void extractTermFromThesau(UnitCommandProcess command) {
		try {
			// read the file as an InputStream.
			InputStream input;
			input = new FileInputStream(command.getThesaurusPath());

			// Rio also accepts a java.io.Reader as input for the
			// parser.
			Model model = Rio.parse(input, "", RDFFormat.RDFXML);
			for (Statement statement : model) {
				// System.out.println(statement);
				// System.out.println(statement.getObject().stringValue());
				if (statement.getObject().stringValue()
						.equalsIgnoreCase(command.getKey())) {
					System.out.println(statement.getSubject());
					System.out.println(statement.getPredicate().stringValue());
					String predicate = statement.getPredicate().stringValue();
					switch (predicate) {
					case "http://www.w3.org/2004/02/skos/core#altLabel":
						for (Statement stat : model) {
							// if subject of stat = concept1218
							if (stat.getSubject()
									.stringValue()
									.equalsIgnoreCase(
											statement.getSubject()
													.stringValue())) {
								// extract all objects with altLabel
								// property
								if (stat.getPredicate()
										.stringValue()
										.equalsIgnoreCase(
												"http://www.w3.org/2004/02/skos/core#prefLabel")
										&& stat.getObject() != null
										&& stat.getObject().toString()
												.contains("@en")) {
									System.out.println("2    "
											+ stat.getObject().stringValue());
									extractClassFromOntology(command, stat
											.getObject().stringValue(), false);
								}

							}
						}

						break;
					case "http://www.w3.org/2004/02/skos/core#prefLabel":
						for (Statement stat : model) {
							// if subject of stat = concept1218
							if (stat.getSubject()
									.stringValue()
									.equalsIgnoreCase(
											statement.getSubject()
													.stringValue())) {
								// extract all objects with altLabel
								// property
								if (stat.getPredicate()
										.stringValue()
										.equalsIgnoreCase(
												"http://www.w3.org/2004/02/skos/core#prefLabel")
										&& stat.getObject() != null
										&& stat.getObject().toString()
												.contains("@en")) {
									System.out.println("2    "
											+ stat.getObject().stringValue());
									extractClassFromOntology(command, stat
											.getObject().stringValue(), false);
								}

							}
						}
						break;
					default:
						break;
					}

				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean extractClassFromOntology(UnitCommandProcess command,
			String key, boolean mdiExist) {
		OWLOntology ontology = command.getOntology();
		Set<OWLClass> setClasses = ontology.getClassesInSignature();
		for (OWLClass owlClass : setClasses) {
			Iterator<OWLAnnotation> iterator = EntitySearcher.getAnnotations(
					owlClass.getIRI(), ontology, df.getRDFSLabel()).iterator();
			while (iterator.hasNext()) {
				OWLAnnotation annotation = iterator.next();
				if (annotation.getValue() instanceof OWLLiteral) {
					OWLLiteral label = (OWLLiteral) annotation.getValue();

					if (label.getLiteral().toString().equalsIgnoreCase(key)) {
						/*System.out.println("The concept " + key
								+ " exist in the ontology");
						System.out.println("the class is "
								+ owlClass.toStringID());*/
						command.setCi(owlClass);
						mdiExist = true;
						if (command.getCs() == null) {
							command.setCs(owlClass);
						}
						return mdiExist;
					}
				}
			}
		}
		return mdiExist;
	}

}
