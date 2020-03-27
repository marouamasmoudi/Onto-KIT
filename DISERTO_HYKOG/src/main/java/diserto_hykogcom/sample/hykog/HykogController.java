package diserto_hykogcom.sample.hykog;

import diserto_hykogcom.sample.ContextGui;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import diserto_hykogcom.sample.hykog.components.CloseController;
import diserto_hykogcom.sample.hykog.query.QueryController;

import java.net.URL;
import java.util.ResourceBundle;

public class HykogController implements Initializable {

    private ContextGui contextGui;

    @FXML
    private VBox vBox;


    public HykogController(ContextGui contextGui) {
        this.contextGui = contextGui;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            contextGui.setHykogController(this);
            addStackPane();
            addCloseButton();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void addStackPane() throws java.io.IOException {
        QueryController controller = new QueryController(contextGui);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./query/query.fxml"));
        loader.setController(controller);
        Node stackPane = loader.load();
        vBox.getChildren().add(0,stackPane);
    }

    private void addCloseButton() throws java.io.IOException {
        CloseController controller = new CloseController(contextGui);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./components/close.fxml"));
        loader.setController(controller);
        Node close = loader.load();
        vBox.getChildren().add(1,close);
    }

}
