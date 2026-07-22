package divar.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneManager {

    private static Stage primaryStage;

    // اندازه استاندارد تمام پنجره‌ها
    private static final double WINDOW_WIDTH = 1280;
    private static final double WINDOW_HEIGHT = 800;

    private SceneManager() {
    }

    public static void setStage(Stage stage) {
        primaryStage = stage;

        primaryStage.setWidth(WINDOW_WIDTH);
        primaryStage.setHeight(WINDOW_HEIGHT);

        primaryStage.setMinWidth(WINDOW_WIDTH);
        primaryStage.setMinHeight(WINDOW_HEIGHT);

        primaryStage.centerOnScreen();
    }

    public static void loadScene(String fxml, String title) {

        try {

            FXMLLoader loader = new FXMLLoader(
                    SceneManager.class.getResource("/divar/" + fxml)
            );

            Parent root = loader.load();

            Scene scene = new Scene(
                    root,
                    WINDOW_WIDTH,
                    WINDOW_HEIGHT
            );

            primaryStage.setTitle(title);
            primaryStage.setScene(scene);

            primaryStage.setWidth(WINDOW_WIDTH);
            primaryStage.setHeight(WINDOW_HEIGHT);

            primaryStage.centerOnScreen();

            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void switchScene(String fxml) {
        loadScene(fxml, primaryStage.getTitle());
    }
}