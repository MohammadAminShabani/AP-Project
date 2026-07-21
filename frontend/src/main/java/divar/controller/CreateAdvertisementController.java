package divar.controller;

import divar.config.SceneManager;
import divar.dto.request.CreateAdvertisementRequest;
import divar.dto.response.CategoryResponse;
import divar.dto.response.CityResponse;
import divar.network.ApiException;
import divar.service.AdvertisementService;
import divar.service.CategoryService;
import divar.service.CityService;
import divar.util.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class CreateAdvertisementController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField priceField;

    @FXML
    private ComboBox<CityResponse> cityComboBox;

    @FXML
    private ComboBox<CategoryResponse> categoryComboBox;

    @FXML
    private TextArea descriptionArea;

    private final AdvertisementService advertisementService =
            new AdvertisementService();

    private final CityService cityService =
            new CityService();

    private final CategoryService categoryService =
            new CategoryService();

    @FXML
    public void initialize() {

        loadCities();

        loadCategories();

    }

    @FXML
    private void createAdvertisement() {

        if (!validateInputs()) {
            return;
        }

        try {

            CreateAdvertisementRequest request =
                    new CreateAdvertisementRequest();

            request.setTitle(
                    titleField.getText().trim()
            );

            request.setDescription(
                    descriptionArea.getText().trim()
            );

            request.setPrice(
                    Long.parseLong(priceField.getText().trim())
            );

            request.setCityId(
                    cityComboBox.getValue().getId()
            );

            request.setCategoryId(
                    categoryComboBox.getValue().getId()
            );

            advertisementService.create(request);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setHeaderText(null);

            alert.setContentText("آگهی با موفقیت ثبت شد.");

            alert.showAndWait();

            SceneManager.loadScene(
                    Constants.HOME,
                    "خانه"
            );

        } catch (ApiException e) {

            showError(e.getMessage());

        } catch (IOException | InterruptedException e) {

            showError("ارتباط با سرور برقرار نشد.");

        } catch (NumberFormatException e) {

            showError("قیمت باید عدد باشد.");

        }

    }

    @FXML
    private void back() {

        SceneManager.loadScene(
                Constants.HOME,
                "خانه"
        );

    }

    private void loadCities() {

        try {

            cityComboBox.getItems().setAll(
                    cityService.getAll()
            );

        } catch (Exception e) {

            showError("خطا در دریافت لیست شهرها.");

        }

    }

    private void loadCategories() {

        try {

            categoryComboBox.getItems().setAll(
                    categoryService.getAll()
            );

        } catch (Exception e) {

            showError("خطا در دریافت لیست دسته‌بندی‌ها.");

        }

    }

    private boolean validateInputs() {

        if (titleField.getText().isBlank()) {

            showError("عنوان را وارد کنید.");

            return false;

        }

        if (priceField.getText().isBlank()) {

            showError("قیمت را وارد کنید.");

            return false;

        }

        if (descriptionArea.getText().isBlank()) {

            showError("توضیحات را وارد کنید.");

            return false;

        }

        if (cityComboBox.getValue() == null) {

            showError("شهر را انتخاب کنید.");

            return false;

        }

        if (categoryComboBox.getValue() == null) {

            showError("دسته‌بندی را انتخاب کنید.");

            return false;

        }

        return true;

    }

    private void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();

    }

}