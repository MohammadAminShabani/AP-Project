package divar.controller;

import divar.config.SceneManager;
import divar.session.SessionManager;
import divar.util.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController {

    @FXML
    private Label welcomeLabel;

    @FXML
    public void initialize() {

        String username = SessionManager.getUsername();

        if (username == null || username.isBlank()) {
            username = "کاربر";
        }

        welcomeLabel.setText("خوش آمدید، " + username);

    }

    @FXML
    public void logout() {

        SessionManager.clear();

        SceneManager.loadScene(Constants.LOGIN, "ورود");

    }
}