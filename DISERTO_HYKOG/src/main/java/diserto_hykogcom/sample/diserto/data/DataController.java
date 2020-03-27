package diserto_hykogcom.sample.diserto.data;

import diserto_hykogcom.sample.ContextGui;
import diserto_hykogcom.sample.diserto.GenerateAction;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class DataController implements Initializable {

    private ContextGui context;
    private File file;

    @FXML
    private Button browse;
    @FXML
    private Button previous;
    @FXML
    private Button generate;
    @FXML
    private TextField textFieldPath;
    @FXML
    private VBox vbox;


    public DataController(ContextGui context) {
        this.context = context;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String desktopPath = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
        this.textFieldPath.setText(desktopPath);
        this.generate.setDisable(true);
        previousButton_Click();
        InputStream inputStream = getClass().getResourceAsStream("./data.jpg");
        // FileInputStream input = new FileInputStream(String.valueOf(inputStream));

        // create a image
        Image image = new Image(inputStream);
        Background bg = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1, 1, true, true, false, false)));
        vbox.setBackground(bg);
        browseButton_Click();
        generate_Click();

    }

    private void previousButton_Click() {
        this.previous.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                VBox vBox = context.getDisertoController().getvBox();
                Node stackPaneToDelete = vBox.getChildren().get(0);
                vBox.getChildren().remove(stackPaneToDelete);
                Node previousStackPane = context.getThesaurusNode();
                vBox.getChildren().add(0, previousStackPane);
            }
        });
    }


    private void browseButton_Click() {
        this.browse.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(final ActionEvent e) {
                        JFileChooser chooser = new JFileChooser();
                        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                        String desktopPath = FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();
                        File currentDirectory = new File(desktopPath);
                        chooser.setCurrentDirectory(currentDirectory);
                        Stage stage = (Stage) context.getDisertoStage();
                        int result = chooser.showOpenDialog(new Frame());
                        if (result == JFileChooser.APPROVE_OPTION) {
                            file = chooser.getSelectedFile();
                            if (file != null) {
                                generate.setDisable(false);
                                textFieldPath.setText(file.getAbsolutePath());
                            }
                        }
                    }
                });
    }

    private void generate_Click(){
        generate.setOnAction(event -> {
            GenerateAction generateAction = new GenerateAction();
            generateAction.generate_Click(context);
        });
    }
    public File getFile() {
        return file;
    }
}
