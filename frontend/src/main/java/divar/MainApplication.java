package divar;

import divar.config.SceneManager;
import divar.util.Constants;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MainApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        loadFonts();

        SceneManager.setStage(stage);
        SceneManager.loadScene("home.fxml", "خانه");
    }

    private void loadFonts() {

        Font.loadFont(MainApplication.class.getResourceAsStream("/fonts/Vazirmatn-Regular.ttf"), 14);

        Font.loadFont(MainApplication.class.getResourceAsStream("/fonts/Vazirmatn-Medium.ttf"), 14);

        Font.loadFont(MainApplication.class.getResourceAsStream("/fonts/Vazirmatn-SemiBold.ttf"), 14);

        Font.loadFont(MainApplication.class.getResourceAsStream("/fonts/Vazirmatn-Bold.ttf"), 14);

        Font.loadFont(MainApplication.class.getResourceAsStream("/fonts/Vazirmatn-ExtraBold.ttf"), 14);

        System.out.println("Vazirmatn fonts loaded.");
    }
}