package divar;

import divar.config.SceneManager;
import divar.util.Constants;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        SceneManager.setStage(stage);
        SceneManager.loadScene(Constants.LOGIN, "Divar");
    }

    public static void main(String[] args) {
        launch(args);
    }
}