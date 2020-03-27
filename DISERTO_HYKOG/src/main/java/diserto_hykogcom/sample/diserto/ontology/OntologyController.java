package diserto_hykogcom.sample.diserto.ontology;

import diserto_hykogcom.sample.ContextGui;
import diserto_hykogcom.sample.diserto.thesaurus.ThesaurusController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class OntologyController implements Initializable {

    private ContextGui context;
    private File file;

    @FXML
    private Button next;

    @FXML
    private Button browse;

    @FXML
    private Button previous;

    @FXML
    private TextField textFieldPath;

    @FXML
    private VBox vbox;

    public OntologyController(ContextGui context) {
        this.context = context;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            nextButton_Click();
            browseButton_Click();
            next.setDisable(true);
            this.previous.setVisible(false);
            String desktopPath = javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory().getAbsolutePath();//WindowsUtils.getCurrentUserDesktopPath();
            this.textFieldPath.setText(desktopPath);
            InputStream inputStream = getClass().getResourceAsStream("./fontOntology.jpg");
            // FileInputStream input = new FileInputStream(String.valueOf(inputStream));

            // create a image
            Image image = new Image(inputStream);
            Background bg = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1, 1, true, true, false, false)));
            vbox.setBackground(bg);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void nextButton_Click() {
        this.next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    VBox vBox = context.getDisertoController().getvBox();
                    Node stackPaneToDelete = vBox.getChildren().get(0);
                    vBox.getChildren().remove(stackPaneToDelete);

                    if (context.getThesaurusNode() == null) {
                        ThesaurusController controller = new ThesaurusController(context);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../thesaurus/thesaurus.fxml"));
                        loader.setController(controller);
                        Node stackPane = loader.load();
                        context.setThesaurusController(controller);
                        context.setThesaurusNode(stackPane);
                        vBox.getChildren().add(0, stackPane);
                    } else {
                        Node stackPane = context.getThesaurusNode();
                        vBox.getChildren().add(0, stackPane);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                                next.setDisable(false);
                                textFieldPath.setText(file.getAbsolutePath());
                            }
                        }
                    }
                });
    }

    public File getFile() {
        return file;
    }
}
