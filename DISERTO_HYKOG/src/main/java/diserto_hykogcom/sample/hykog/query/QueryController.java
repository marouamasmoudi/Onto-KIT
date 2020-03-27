package diserto_hykogcom.sample.hykog.query;

import com.jgc.areyes.services.queryProcessing.QueryProcessing;
import diserto_hykogcom.sample.ContextGui;
import diserto_hykogcom.sample.hykog.console.ConsoleDialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Window;

import javax.swing.*;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import static diserto_hykogcom.sample.Constants.*;

public class QueryController implements Initializable {

    private ContextGui context;

    @FXML
    private Button launch;
    @FXML
    private Button reset;
    @FXML
    private TextArea queryText;
    @FXML
    private VBox vbox;

    public QueryController(ContextGui context) {
        this.context = context;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            InputStream inputStream = getClass().getResourceAsStream("./query.jpg");
            // FileInputStream input = new FileInputStream(String.valueOf(inputStream));

            // create a image
            Image image = new Image(inputStream);
            Background bg = new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(1, 1, true, true, false, false)));
            vbox.setBackground(bg);
            launchButton_Click();
            resetButton_Click();
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private void resetButton_Click() {
        this.reset.setOnAction(event -> queryText.setText(""));

    }

    private void launchButton_Click() {
        this.launch.setOnAction(e -> {
            {
                String querySTR = queryText.getText();
                System.out
                        .println("******************Query Process Start*******************");


                ConsoleDialog consoleDialog = new ConsoleDialog();
                TextArea textArea = consoleDialog.getConsoleController().getTextArea();
                QueryProcessing queryProcessing = new QueryProcessing(querySTR);
                textArea.setText(queryProcessing.getTextArea());
                consoleDialog.showWindow();

             /*   JFrame jFrame = new JFrame("EXIT");
                int input = JOptionPane.
                        showConfirmDialog(jFrame, SUCCESS_QUERY, SUCCESS_Q,
                                JOptionPane.OK_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE);
                if (input == 0) {

                }*/
            }
        });
    }
}
