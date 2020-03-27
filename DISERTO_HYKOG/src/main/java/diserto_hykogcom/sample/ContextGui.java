package diserto_hykogcom.sample;

import javafx.scene.Node;
import javafx.stage.Window;
import diserto_hykogcom.sample.diserto.DisertoController;
import diserto_hykogcom.sample.diserto.data.DataController;
import diserto_hykogcom.sample.diserto.ontology.OntologyController;
import diserto_hykogcom.sample.diserto.thesaurus.ThesaurusController;
import diserto_hykogcom.sample.hykog.HykogController;

public class ContextGui {

    private Window parentStage;
    private Window hykogStage;
    private Window disertoStage;

    private DisertoController disertoController;
    private HykogController hykogController;
    private DataController dataController;
    private OntologyController ontologyController;
    private ThesaurusController thesaurusController;

    private Node dataNode;
    private Node ontologyNode;
    private Node thesaurusNode;

    public Window getParentStage() {
        return parentStage;
    }

    public void setParentStage(Window parentStage) {
        this.parentStage = parentStage;
    }

    public Window getHykogStage() {
        return hykogStage;
    }

    public void setHykogStage(Window hykogStage) {
        this.hykogStage = hykogStage;
    }

    public Window getDisertoStage() {
        return disertoStage;
    }

    public void setDisertoStage(Window disertoStage) {
        this.disertoStage = disertoStage;
    }

    public DisertoController getDisertoController() {
        return disertoController;
    }

    public void setDisertoController(DisertoController disertoController) {
        this.disertoController = disertoController;
    }

    public DataController getDataController() {
        return dataController;
    }

    public void setDataController(DataController dataController) {
        this.dataController = dataController;
    }

    public OntologyController getOntologyController() {
        return ontologyController;
    }

    public void setOntologyController(OntologyController ontologyController) {
        this.ontologyController = ontologyController;
    }

    public ThesaurusController getThesaurusController() {
        return thesaurusController;
    }

    public void setThesaurusController(ThesaurusController thesaurusController) {
        this.thesaurusController = thesaurusController;
    }

    public Node getDataNode() {
        return dataNode;
    }

    public void setDataNode(Node dataNode) {
        this.dataNode = dataNode;
    }

    public Node getOntologyNode() {
        return ontologyNode;
    }

    public void setOntologyNode(Node ontologyNode) {
        this.ontologyNode = ontologyNode;
    }

    public Node getThesaurusNode() {
        return thesaurusNode;
    }

    public void setThesaurusNode(Node thesaurusNode) {
        this.thesaurusNode = thesaurusNode;
    }

    public HykogController getHykogController() {
        return hykogController;
    }

    public void setHykogController(HykogController hykogController) {
        this.hykogController = hykogController;
    }
}
