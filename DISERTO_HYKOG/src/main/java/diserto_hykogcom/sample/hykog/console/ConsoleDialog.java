package diserto_hykogcom.sample.hykog.console;

import diserto_hykogcom.sample.Constants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;

public class ConsoleDialog extends JDialog {


    private ConsoleController consoleController;

    private Stage stage;
    public ConsoleDialog() {
        try {
            stage = new Stage();
            consoleController = new ConsoleController();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("console.fxml"));
            loader.setController(consoleController);
            Parent root = loader.load();
            Scene scene = new Scene(root, 500, 320);
            stage.setTitle(Constants.CONSOLE);
            stage.setScene(scene);
            stage.hide();
            setModal(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showWindow(){
        stage.show();
    }
    public ConsoleController getConsoleController() {
        return consoleController;
    }
}
