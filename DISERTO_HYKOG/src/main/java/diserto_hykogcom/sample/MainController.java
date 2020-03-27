package diserto_hykogcom.sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import diserto_hykogcom.sample.diserto.DisertoController;
import diserto_hykogcom.sample.hykog.HykogController;
import diserto_hykogcom.sample.tools.ApplicationComponentService;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    private ContextGui contextGui;

    @FXML
    private Button disertoButton;
    @FXML
    private Button hykogButton;
    @FXML
    private ImageView ontologyImage;
    @FXML
    private Button closeButton;

    public MainController(ContextGui contextGui) {
        this.contextGui = contextGui;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeButton(this.disertoButton, "../images/DIS.jpg");
        initializeButton(this.hykogButton, "../images/HyQ.jpg");
        initializeImage();
    }

    private void initializeImage() {
        InputStream input = getClass().getResourceAsStream("../images/Entete.jpg");
        Image image = new Image(input);
        ontologyImage.setImage(image);
        closeButton_Click();
        disertoButton_Click();
        hykogButton_Click();
    }

    private void initializeButton(Button button, String path) {
        InputStream input = getClass().getResourceAsStream(path);
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        button.setGraphic(imageView);
    }

    private void closeButton_Click() {
        this.closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ApplicationComponentService.getInstance().closeApplication();
            }
        });
    }


    private void hykogButton_Click() {
        this.hykogButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    HykogController controller = new HykogController(contextGui);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("./hykog/hykog.fxml"));
                    loader.setController(controller);
                    Parent root = loader.load();
                    Scene scene = new Scene(root, 500, 320);
                    // New window (Stage)
                    Stage hykogStageWindow = new Stage();
                    hykogStageWindow.setTitle("HYQ");
                    hykogStageWindow.setScene(scene);

                    contextGui.setHykogStage(hykogStageWindow);
                    hykogStageWindow.initModality(Modality.WINDOW_MODAL);
                    hykogStageWindow.initOwner(contextGui.getParentStage());
                    hykogStageWindow.show();
                    //  contextGui.getParentStage().hide();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void disertoButton_Click() {
        this.disertoButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    DisertoController controller = new DisertoController(contextGui);
                    FXMLLoader loader = new FXMLLoader(DisertoController.class.getResource("./diserto.fxml"));
                    loader.setController(controller);
                    Parent root = loader.load();
                    Scene scene = new Scene(root, 500, 320);
                    // New window (Stage)
                    Stage disertoStageWindow = new Stage();
                    disertoStageWindow.setTitle("DISERTO");
                    disertoStageWindow.setScene(scene);
                    disertoStageWindow.initModality(Modality.WINDOW_MODAL);
                    disertoStageWindow.initOwner(contextGui.getParentStage());
                    disertoStageWindow.show();
                    contextGui.setDisertoStage(disertoStageWindow);
                    //contextGui.getParentStage().hide();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
