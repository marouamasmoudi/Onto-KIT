package com.jgc.areyes.services.queryProcessing;

import be.ugent.rml.Executor;
import be.ugent.rml.Utils;
import be.ugent.rml.records.RecordsFactory;
import be.ugent.rml.store.Quad;
import be.ugent.rml.store.QuadStore;
import be.ugent.rml.store.QuadStoreFactory;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.jgc.areyes.services.Constants.*;


public class RDFTriplesGenerator {

    private String hypernodePath;

    public File generateRDFTriples() {

        Executor executor = null;
        try {
            executor = this.createExecutor(hypernodePath);
            QuadStore result = null;
            result = executor.execute(null);

            result.removeDuplicates();
            String string1 = result.toSortedString();
            System.out.println("string1");
            System.out.println(string1);

            File targetFile = null;
            BufferedWriter out;
            String outputFile = hypernodePath+"Output.ttl";
            if (outputFile != null) {
                targetFile = new File(outputFile);

                out = new BufferedWriter(new FileWriter(targetFile));

            } else {
                out = new BufferedWriter(new OutputStreamWriter(System.out));
            }
            result.write(out, "nquads");
            out.close();
            return targetFile;
        }
     catch (Exception e) {
        e.printStackTrace();
    }

return null;

    }

    Executor createExecutor(String mapPath) throws Exception {
        return createExecutor(mapPath, new ArrayList<>());
    }

    Executor createExecutor(String mapPath, List<Quad> extraQuads) throws Exception {
        ClassLoader classLoader = getClass().getClassLoader();
        // execute mapping file
        URL url = classLoader.getResource(mapPath);

        if (url != null) {
            mapPath = url.getFile();
        }

        File mappingFile = new File(mapPath);
        QuadStore rmlStore = QuadStoreFactory.read(mappingFile);
        rmlStore.addQuads(extraQuads);

        return new Executor(rmlStore,
                new RecordsFactory(mappingFile.getParent()), Utils.getBaseDirectiveTurtle(mappingFile));
    }


    public void setHypernodePath(String hypernodePath) {
        this.hypernodePath = hypernodePath;
    }
}
