package com.jgc.areyes.services.processor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import org.semanticweb.owlapi.model.OWLClass;

import com.jgc.areyes.model.Prefix;
import com.jgc.areyes.services.processor.unit.UnitCommandProcess;

import static com.jgc.areyes.services.Constants.*;

/**
 * 
 * @author masmoudi
 *
 */
public class RmlGenerator {

	private static RmlGenerator INSTANCE;
	private String inputFilePath;
	private String outputRMLFilePath;
	private String base = "http://example.com/";
	private String mds, md1, csName;
	private String prefix;
	private Prefix prefixCs;

	public static RmlGenerator getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RmlGenerator();
		}
		return INSTANCE;
	}

	private RmlGenerator() {
	}

	@SuppressWarnings("resource")
	public void generateRml(ProcessorInput command) {

		System.out
				.println("******************Generate RML mapping*******************");

		mds = command.getMds();
		md1 = mds;
		inputFilePath = command.getInputFilePath();
		csName = getClassName(command.getListCommandProcess().get(0).getCs());
		prefixCs = getPrefixName(command.getListCommandProcess().get(0).getCs());
		try {
			PrintStream out;
			File directory =  new File(RESOURCE_REPO + "RML mappings\\");
			int count = directory.listFiles().length;
			command.setHyperNodeCount(count);
			File outputRMLfile = new File(RESOURCE_REPO + "RML mappings\\"+MAPPING_FILE_NAME+count+MAPPING_FILE_EXTENSION);
			out = outputRMLfile == null ? System.out : new PrintStream(
					outputRMLfile);
			out.println("@prefix rr: <http://www.w3.org/ns/r2rml#>.\n"
					+ "@prefix  rml: <http://semweb.mmlab.be/ns/rml#> .\n"
					+ "@prefix xsd: <http://www.w3.org/2001/XMLSchema#>.\n"
					+ "@prefix rrx: <http://www.w3.org/ns/r2rml-ext#>.\n"
					+ "@prefix rrxf: <http://www.w3.org/ns/r2rml-ext/functions/def/>.\n"
					+ "@prefix memon: <http://www.semanticweb.org/lenovo/ontologies/2017/10/>.\n"
					+ "@prefix qa: <http://www.ontologylibrary.mil/CommonCore/Mid/QualityOntology#>.\n"
					+ "@prefix ql: <http://semweb.mmlab.be/ns/ql#>. \n"
			+ "@base <http://example.com/base> . \n");

			FileWriter fw = new FileWriter(outputRMLfile, true);

			/** print TriplesMap Name **/

			fw.write(generateTriplesMapName());
			/** print LogicalSource **/

			String logicalSource = generateLocigalSource();
			fw.write(logicalSource + "\n");

			/** print SubjectMap **/
			fw.write(generateSubjectMap());

			/** print PredicateObjectMap **/

			command.getListCommandProcess();
			for (UnitCommandProcess unitCommand : command
					.getListCommandProcess()) {
				Object predicate = unitCommand.getRi();
				if (predicate != null) {
					String mdi = unitCommand.getKey();
					String predicateObjectMap = generatePredicateObjectMap(
							predicate, mdi);
					fw.write(predicateObjectMap);
				}
			}

			fw.write(".\n");
			fw.close();
			System.out.println("RML mapping generated");
			command.setRMLFilePath(outputRMLFilePath);
		} catch (IOException ioe) {

		}

	}

	private Prefix getPrefixName(Object owlClass) {
		Prefix prefix = null;
		if (owlClass != null) {
			String owlClassIRI = owlClass.toString();
			int indexslash = owlClassIRI.lastIndexOf('/');
			int indexdiez = owlClassIRI.lastIndexOf('#');
			String classPrefix;

			if (indexdiez > indexslash) {
				classPrefix = owlClassIRI.substring(1, indexdiez + 1);

			} else {
				classPrefix = owlClassIRI.substring(1, indexslash + 1);

			}
			for (Prefix pref : Prefix.values()) {
				if (pref.getPrefix().equalsIgnoreCase(classPrefix))
				{
				prefix = pref;
					return prefix;}
			}

		}
		return prefix;
	}

	private String getClassName(Object owlClass) {
		if (owlClass != null) {
			String owlClassIRI = owlClass.toString();
			int lengthIRI= owlClassIRI.length();
			int indexslash = owlClassIRI.lastIndexOf('/');
			int indexdiez = owlClassIRI.lastIndexOf('#');
			String className;
			if (indexdiez > indexslash) {
				className = owlClassIRI.substring(indexdiez + 1,lengthIRI-1);

			} else {
				className = owlClassIRI.substring(indexslash + 1,lengthIRI-1);

			}
			return className;
		}
		return null;
	}

	private String generateTriplesMapName() {
		StringBuilder sb = new StringBuilder();
		sb.append("<#" + mds + "Mapping> a rr:TriplesMap; \n");// "Mapping" added
		return sb.toString();
	}

	/**
	 * Method to generate the Logical source of the RML mapping
	 * 
	 * @return
	 */
	private String generateLocigalSource() {
		StringBuilder sb = new StringBuilder();
		sb.append("rml:logicalSource [\n");
		sb.append("\trml:source \"" + inputFilePath + "\";\n");
		sb.append("\trml:referenceFormulation ql:CSV" + ";\n];\n");
		return sb.toString();
	}

	/**
	 * Method to generate the Subject Map of the RML
	 */
	private String generateSubjectMap() {
		StringBuilder sb = new StringBuilder();
		sb.append("rr:subjectMap [\n");
		sb.append("\trr:template \"" + base + "{"+mds + "}\";\n");
		sb.append("\trr:class " + prefixCs.name() + ":" + csName + ";\n");
		sb.append("];\n");
		return sb.toString();
	}

	/**
	 * Method to generate the predicate object Map
	 * 
	 * @param predicate
	 * @param mdi
	 */
	private String generatePredicateObjectMap(Object predicate, String mdi) {
		Prefix prefix = getPrefixName(predicate);
		String predicateName = getClassName(predicate);
		StringBuilder sb = new StringBuilder();

		sb.append("rr:predicateObjectMap [\n");
		sb.append("\trr:predicate " + prefix + ":" + predicateName + ";\n");

		sb.append("\trr:objectMap [\n");
		sb.append("rml:reference \"" + mdi + "\"; ];\n");

		sb.append("\t];\n");

		return sb.toString();
	}

}
