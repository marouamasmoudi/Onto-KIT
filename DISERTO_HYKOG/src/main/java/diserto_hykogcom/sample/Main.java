package diserto_hykogcom.sample;

import diserto_hykogcom.sample.tools.ApplicationComponentService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        ContextGui contextGui = new ContextGui();
        contextGui.setParentStage(primaryStage);
        MainController controller = new MainController(contextGui);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root, 500, 320);
        primaryStage.setTitle(Constants.APPLICATION_NAME);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> {
            ApplicationComponentService.getInstance().closeApplication();
        });
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
