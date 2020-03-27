package diserto_hykogcom.sample.diserto;

import com.jgc.areyes.hypergraph.HypernodeCreator;
import com.jgc.areyes.services.ConvertionResult;
import com.jgc.areyes.services.ExtractorService;
import com.jgc.areyes.services.ExtractorServiceImpl;
import com.jgc.areyes.services.processor.ProcessorImpl;
import com.jgc.areyes.services.processor.ProcessorInput;
import diserto_hykogcom.sample.ContextGui;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class GenerateAction {

    private String ontologyPath;
    private String thesaurusPath;
    private File file;

    public void generate_Click(ContextGui contextGui) {

        //file is datafile
        file = contextGui.getDataController().getFile();
        //
        File thesaurusFile = contextGui.getThesaurusController().getFile();

        thesaurusPath = thesaurusFile == null ? "" : thesaurusFile.getAbsolutePath();
        ontologyPath = contextGui.getOntologyController().getFile().getAbsolutePath();
        if (file.isFile()) {
            unitFileProcess(file);
        } else if (file.isDirectory()) {
            List<File> listFiles = Arrays.asList(file.listFiles());
            for (File currentFile : listFiles) {
                unitFileProcess(currentFile);
            }
        }
    }

    private void unitFileProcess(File currentFile) {
        System.out
                .println("******************Process Start*******************");
        // 2. extract data from file
        ExtractorService extractor = new ExtractorServiceImpl();
        // convert file to metadata according to file format
        ConvertionResult result = extractor.extractData(currentFile);

        // 3. launch processor
        ProcessorInput input = new ProcessorInput(result, ontologyPath,
                thesaurusPath, currentFile.getAbsolutePath());
        ProcessorImpl.getInstance().process(input);

        // 4. create hypernode
        HypernodeCreator hypernodeCreator = new HypernodeCreator();
        hypernodeCreator.createHypernode(input);
    }
}
