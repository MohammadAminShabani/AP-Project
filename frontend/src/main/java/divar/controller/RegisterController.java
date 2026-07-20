package divar.controller;

import divar.config.SceneManager;
import divar.network.ApiException;
import divar.service.AuthService;
import divar.util.Constants;
import divar.util.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class RegisterController {

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button registerButton;

    @FXML
    private Label messageLabel;

    private final AuthService authService = new AuthService();

    @FXML
    public void register() {

        String fullName = trim(fullNameField.getText());
        String username = trim(usernameField.getText());
        String phone = trim(phoneField.getText());
        String email = trim(emailField.getText());
        String password = passwordField.getText() == null ? "" : passwordField.getText();
        String confirmPassword = confirmPasswordField.getText() == null ? "" : confirmPasswordField.getText();

        String validationError = validate(fullName, username, phone, email, password, confirmPassword);

        if (validationError != null) {
            showError(validationError);
            return;
        }

        try {
            setLoading(true);

            authService.register(fullName, username, password, phone, email);

            showSuccessAndGoToLogin();

        } catch (ApiException e) {
            showError(e.getMessage());

        } catch (IOException | InterruptedException e) {
            showError("امکان اتصال به سرور وجود ندارد. مطمئن شوید Backend در حال اجراست.");

        } finally {
            setLoading(false);
        }
    }

    @FXML
    public void goToLogin() {
        SceneManager.loadScene(Constants.LOGIN, "ورود");
    }

    private String validate(String fullName, String username, String phone,
                            String email, String password, String confirmPassword) {

        if (Validator.isEmpty(fullName) || fullName.length() < 3 || fullName.length() > 50) {
            return "نام و نام خانوادگی باید بین ۳ تا ۵۰ کاراکتر باشد";
        }

        if (Validator.isEmpty(username) || username.length() < 4 || username.length() > 30) {
            return "نام کاربری باید بین ۴ تا ۳۰ کاراکتر باشد";
        }

        if (!Validator.validPhone(phone)) {
            return "شماره تماس معتبر نیست (مثال: 09123456789)";
        }

        if (Validator.isEmpty(email) || !Validator.validEmail(email)) {
            return "ایمیل معتبر نیست";
        }

        if (!Validator.validPassword(password)) {
            return "رمز عبور باید حداقل ۶ کاراکتر باشد";
        }

        if (!password.equals(confirmPassword)) {
            return "رمز عبور و تکرار آن یکسان نیستند";
        }

        return null;
    }

    private String trim(String value) {
        return value == null ? "" : value.trim();
    }

    private void showError(String message) {
        messageLabel.setText(message);
    }

    private void setLoading(boolean loading) {
        registerButton.setDisable(loading);
    }

    private void showSuccessAndGoToLogin() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ثبت نام موفق");
        alert.setHeaderText(null);
        alert.setContentText("ثبت نام با موفقیت انجام شد. اکنون وارد حساب خود شوید.");
        alert.showAndWait();

        SceneManager.loadScene(Constants.LOGIN, "ورود");
    }
}
