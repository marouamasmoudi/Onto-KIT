package diserto_hykogcom.sample.hykog.components;

import diserto_hykogcom.sample.ContextGui;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

import static diserto_hykogcom.sample.Constants.EXIT_DISERTO;
import static diserto_hykogcom.sample.Constants.EXIT_HYKOG_DISERTO_MESSAGE;

public class CloseController implements Initializable {

    private ContextGui contextGui;

    @FXML
    private Button closeButton;

    public CloseController(ContextGui contextGui) {
        this.contextGui = contextGui;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        closeButton_Click();
    }

    private void closeButton_Click() {
        this.closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                JFrame jFrame = new JFrame("EXIT");
                int input = JOptionPane.showConfirmDialog(jFrame, EXIT_HYKOG_DISERTO_MESSAGE, EXIT_DISERTO,
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (input == 0) {
                    ((Stage) contextGui.getHykogStage()).close();
                    //((Stage) contextGui.getParentStage()).show();
                }
            }
        });
    }
}
