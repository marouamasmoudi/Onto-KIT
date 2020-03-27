package diserto_hykogcom.sample.hykog.console;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class ConsoleController implements Initializable {

    @FXML
    private TextArea textArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        textArea.setWrapText(false);
    }

    public TextArea getTextArea() {
        return textArea;
    }
}
