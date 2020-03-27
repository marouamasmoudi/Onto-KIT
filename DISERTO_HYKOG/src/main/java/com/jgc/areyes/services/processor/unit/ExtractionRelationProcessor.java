package com.jgc.areyes.services.processor.unit;

import java.util.Iterator;
import java.util.Set;

import org.semanticweb.HermiT.Configuration;
import org.semanticweb.HermiT.Reasoner.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

/**
 * @author masmoudi
 */
public class ExtractionRelationProcessor implements UnitProcessor {

    @Override
    public void process(UnitCommandProcess command) {
        System.out
                .println("******************start relation extraction*******************");
        OWLClass mdiClass = command.getCi();
        OWLClass mdsClass = command.getCs();
        OWLOntology ontology = command.getOntology();

        for (Iterator<OWLClassAxiom> iterator = ontology.getAxioms(mdsClass)
                .iterator(); iterator.hasNext(); ) {
            OWLClassAxiom owlAxiom = iterator.next();
            Set<OWLClass> setOwlClass = owlAxiom.getClassesInSignature();
            for (OWLClass owlClass : setOwlClass) {
                if (owlClass.equals(mdiClass)) {

                    //if mdiClass == Year or Month or Day


                    Set<OWLObjectProperty> setProperties = owlAxiom.getObjectPropertiesInSignature();
                    for (OWLObjectProperty objectProperty : setProperties) {
                        command.setRi(objectProperty);
                        break;
                    }

                    //System.out.println(owlAxiom.getObjectPropertiesInSignature());
                }
            }

        }

    }

}
