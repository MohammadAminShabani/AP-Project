package divar.controller;

import divar.config.SceneManager;
import divar.dto.response.LoginResponse;
import divar.network.ApiException;
import divar.service.AuthService;
import divar.util.Constants;
import divar.util.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import divar.session.SessionManager;

import java.io.IOException;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label messageLabel;

    private final AuthService authService = new AuthService();

    @FXML
    public void login() {

        String username = usernameField.getText() == null ? "" : usernameField.getText().trim();
        String password = passwordField.getText() == null ? "" : passwordField.getText();

        if (Validator.isEmpty(username) || Validator.isEmpty(password)) {
            showError("نام کاربری و رمز عبور را وارد کنید");
            return;
        }

        setLoading(true);

        try {
            LoginResponse response = authService.login(username, password);

            SessionManager.setToken(response.getToken());
            SessionManager.setUserId(response.getUserId());
            SessionManager.setUsername(response.getUsername());
            SessionManager.setRole(response.getRole());

            SceneManager.loadScene(Constants.HOME, "خانه");

        } catch (ApiException e) {
            showError(e.getMessage());

        } catch (IOException | InterruptedException e) {
            showError("امکان اتصال به سرور وجود ندارد. مطمئن شوید Backend در حال اجراست.");

        } finally {
            setLoading(false);
        }
    }

    @FXML
    public void goToRegister() {
        SceneManager.loadScene(Constants.REGISTER, "ثبت نام");
    }

    private void showError(String message) {
        messageLabel.setText(message);
    }

    private void setLoading(boolean loading) {
        loginButton.setDisable(loading);
    }
}
