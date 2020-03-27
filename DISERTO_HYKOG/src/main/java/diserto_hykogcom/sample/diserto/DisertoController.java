package diserto_hykogcom.sample.diserto;

import diserto_hykogcom.sample.ContextGui;
import diserto_hykogcom.sample.diserto.components.CloseController;
import diserto_hykogcom.sample.diserto.ontology.OntologyController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DisertoController implements Initializable {

    private ContextGui contextGui;

    @FXML
    private VBox vBox;

    public DisertoController(ContextGui contextGui) {
        this.contextGui = contextGui;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            contextGui.setDisertoController(this);
            addStackPane();
            addCloseButton();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addStackPane() throws java.io.IOException {
        OntologyController controller = new OntologyController(contextGui);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./ontology/ontology.fxml"));
        loader.setController(controller);
        Node stackPane = loader.load();
        contextGui.setOntologyNode(stackPane);
        contextGui.setOntologyController(controller);
        vBox.getChildren().add(0, stackPane);
    }

    private void addCloseButton() throws java.io.IOException {
        CloseController controller = new CloseController(contextGui);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./components/close.fxml"));
        loader.setController(controller);
        Node close = loader.load();
        vBox.getChildren().add(1, close);
    }


    public VBox getvBox() {
        return vBox;
    }
}
