package divar.controller;

import divar.config.SceneManager;
import divar.network.TokenManager;
import divar.util.Constants;
import divar.util.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HomeController {

    @FXML
    private Label welcomeLabel;

    @FXML
    public void initialize() {
        welcomeLabel.setText("خوش آمدید، " + SessionManager.getUsername());
    }

    @FXML
    public void logout() {

        TokenManager.clearToken();
        SessionManager.logout();

        SceneManager.loadScene(Constants.LOGIN, "ورود");
    }
}
