package divar;

import divar.config.SceneManager;
import divar.util.Constants;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Font.loadFont(
                MainApplication.class.getResourceAsStream("/fonts/Vazirmatn.ttf"),
                14
        );


        SceneManager.setStage(stage);
        SceneManager.loadScene(Constants.HOME, "خانه");
    }

    public static void main(String[] args) {
        launch(args);
    }
}