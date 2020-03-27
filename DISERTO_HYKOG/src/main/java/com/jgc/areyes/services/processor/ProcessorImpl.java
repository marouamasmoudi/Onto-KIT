package com.jgc.areyes.services.processor;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.sail.SailRepository;
import org.eclipse.rdf4j.sail.nativerdf.NativeStore;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;

import com.jgc.areyes.services.processor.unit.ExtractionRelationProcessor;
import com.jgc.areyes.services.processor.unit.LoadOntology;
import com.jgc.areyes.services.processor.unit.MappingWithOntologyProcessor;
import com.jgc.areyes.services.processor.unit.RdfQuadProcessor;
import com.jgc.areyes.services.processor.unit.UnitCommandProcess;
import com.jgc.areyes.services.processor.unit.UnitProcessor;

/**
 * @author masmoudi
 */
public class ProcessorImpl implements Processor {

    private static ProcessorImpl INSTANCE;

    public static ProcessorImpl getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ProcessorImpl();
        }
        return INSTANCE;
    }

    private ProcessorImpl() {

        processors = Arrays.asList(new MappingWithOntologyProcessor(),
                new RdfQuadProcessor(), new ExtractionRelationProcessor());
        processorsOfMds = Arrays.asList(new MappingWithOntologyProcessor(),
                new RdfQuadProcessor());
    }

    private final List<UnitProcessor> processors;
    private final List<UnitProcessor> processorsOfMds;

    @Override
    public void process(ProcessorInput processorInput) {

        // 1. execute three process for each mdi
        OWLOntology ontology = LoadOntology.loadOntology(processorInput
                .getOntologyPath());
        String source = processorInput.getSource();
        String rdfQuadFilePath = processorInput.getRdfQuadFilePath();

        //initialize repository for RDF quad Store
        Repository repo = new SailRepository(new NativeStore(new File("D:\\Intellig_workspace\\DISERTOworkspace\\jfilechooser-sample\\Resources\\RDFQuadStore\\")));
        repo.initialize();
        /**
         * Process for Mds
         */
        UnitCommandProcess unitCommandProcessMds = new UnitCommandProcess(
                processorInput.getMds(), null, source, rdfQuadFilePath,
                ontology, processorInput.getThesaurusPath(), repo);
        for (UnitProcessor processor : processorsOfMds) {
            // execute processor one by one
            processor.process(unitCommandProcessMds);
        }

        // collect all result of mds in processor command
        processorInput.addUnitCommandProcess(unitCommandProcessMds);
        OWLClass cs = unitCommandProcessMds.getCs();

        /**
         * Process for Mdi
         */
        System.out.println("entities " + processorInput.getEntities());

		/*
		Verify How many spatial representations contain the entities Exp: Latitude & Longitude
		 */
		/*
		Verify How many temporal representations contain entities Exp: Year & Month
		 */
        // Le remplacer apres par une ontologie spatial et une temporelle

       /* for (String metadata : processorInput.getEntities().keySet()) {
            switch (metadata.toLowerCase()) {
                case "latitude":
                    processorInput.getEntities().replace(metadata,null,"S");
                    break;
                case "longitude":
                    processorInput.getEntities().replace(metadata,null,"S");
                    break;
                case "year":
                    processorInput.getEntities().replace(metadata,null,"T");
                    break;
                case "month":
                    processorInput.getEntities().replace(metadata,null,"T");
                    break;
                case "day":
                    processorInput.getEntities().replace(metadata,null,"T");
                    break;
            }
        }*/

        if (processorInput.getEntities().size() > 1) {
            for (Entry<String, Object> entry : processorInput.getEntities()
                    .entrySet()) {

                UnitCommandProcess unitCommandProcess = new UnitCommandProcess(
                        entry.getKey(), entry.getValue(), source, rdfQuadFilePath,
                        ontology, processorInput.getThesaurusPath(), cs, repo);
                for (UnitProcessor processor : processors) {
                    // execute processor one by one
                    processor.process(unitCommandProcess);
                }

                // collect all result of mdi in processor command
                processorInput.addUnitCommandProcess(unitCommandProcess);
            }

            int i = 1;
            for (UnitCommandProcess unitCommandProcess : processorInput
                    .getListCommandProcess()) {
                //System.out.println(i);
			/*System.out.println(unitCommandProcess.getKey() + "----"
					+ unitCommandProcess.getCi() + "----"
					+ unitCommandProcess.getRi());*/
                i++;
            }
        }
        repo.shutDown();
        // 2. generate the RML mapping
        RmlGenerator.getInstance().generateRml(processorInput);
    }
}
