package divar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) {
        // ایجاد یک لیبل ساده برای تست
        Label label = new Label("Second-hand JavaFX Frontend is running!");

        // قرار دادن لیبل در یک کامپوننت ریشه و تنظیم ابعاد پنجره (عرض ۵۰۰ و ارتفاع ۳۰۰)
        Scene scene = new Scene(label, 500, 300);

        // تنظیمات پنجره اصلی
        stage.setTitle("Second-hand Project");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // متد لود کردن و اجرای برنامه JavaFX
        launch(args);
    }
}