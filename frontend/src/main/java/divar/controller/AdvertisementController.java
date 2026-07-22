package divar.controller;

import divar.config.SceneManager;
import divar.dto.response.AdvertisementResponse;
import divar.network.ApiException;
import divar.service.AdvertisementService;
import divar.session.AdvertisementSession;
import divar.session.SessionManager;
import divar.util.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;

public class AdvertisementController {

    private final AdvertisementService advertisementService = new AdvertisementService();
    @FXML
    private Label titleLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label categoryLabel;
    @FXML
    private Label ownerLabel;
    @FXML
    private Label rateLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label messageLabel;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private HBox ownerActionsBox;
    @FXML
    private Button markSoldButton;
    @FXML
    private HBox imageGallery;
    private AdvertisementResponse advertisement;

    @FXML
    public void initialize() {

        advertisement = AdvertisementSession.getAdvertisement();

        if (advertisement == null) {

            back();

            return;
        }

        render();
    }

    private void render() {

        titleLabel.setText(advertisement.getTitle());
        renderImages();

        priceLabel.setText(advertisement.getPrice() != null
                ? String.format("%,d تومان", advertisement.getPrice())
                : "قیمت نامشخص");

        cityLabel.setText("📍 " + safe(advertisement.getCity()));

        categoryLabel.setText("🏷 " + safe(advertisement.getCategory()));

        ownerLabel.setText(safe(advertisement.getOwnerName()));

        rateLabel.setText(advertisement.getAverageRate() != null && advertisement.getAverageRate() > 0
                ? String.format(java.util.Locale.US, "⭐ %.1f از ۵", advertisement.getAverageRate())
                : "هنوز امتیازی ثبت نشده");

        statusLabel.setText(statusLabelFa(advertisement.getStatus()));
        applyStatusStyle(advertisement.getStatus());

        descriptionArea.setText(advertisement.getDescription());

        boolean isOwner = advertisement.getOwnerId() != null && advertisement.getOwnerId().equals(SessionManager.getUserId());

        ownerActionsBox.setVisible(isOwner);
        ownerActionsBox.setManaged(isOwner);

        // فقط آگهی فعال قابل تغییر وضعیت به «فروخته شد» است.
        boolean canMarkSold = isOwner && "ACTIVE".equals(advertisement.getStatus());

        markSoldButton.setDisable(!canMarkSold);
    }

    private String safe(String value) {
        return value == null || value.isBlank() ? "نامشخص" : value;
    }

    private void applyStatusStyle(String status) {

        statusLabel.getStyleClass().removeAll("status-pill-sold", "status-pill-pending", "status-pill-rejected");

        if (status == null) {
            return;
        }

        switch (status) {
            case "SOLD" -> statusLabel.getStyleClass().add("status-pill-sold");
            case "PENDING" -> statusLabel.getStyleClass().add("status-pill-pending");
            case "REJECTED" -> statusLabel.getStyleClass().add("status-pill-rejected");
            default -> { /* ACTIVE از استایل پیش‌فرض سبز status-pill استفاده می‌کند */ }
        }
    }

    private String statusLabelFa(String status) {

        if (status == null) {
            return "";
        }

        return switch (status) {
            case "PENDING" -> "در انتظار بررسی";
            case "ACTIVE" -> "فعال";
            case "REJECTED" -> "رد شده";
            case "DELETED" -> "حذف شده";
            case "SOLD" -> "فروخته شده";
            default -> status;
        };
    }

    @FXML
    private void back() {

        AdvertisementSession.clear();

        SceneManager.loadScene(Constants.HOME, "خانه");

    }

    @FXML
    private void editAdvertisement() {

        SceneManager.loadScene(Constants.EDIT_AD, "ویرایش آگهی");

    }

    @FXML
    private void deleteAdvertisement() {

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "آیا از حذف این آگهی مطمئن هستید؟");

        confirm.showAndWait().ifPresent(response -> {

            if (response.getButtonData().isDefaultButton()) {

                try {

                    advertisementService.delete(advertisement.getId());

                    AdvertisementSession.clear();

                    SceneManager.loadScene(Constants.HOME, "خانه");

                } catch (ApiException e) {

                    showMessage(e.getMessage());

                } catch (IOException | InterruptedException e) {

                    showMessage("امکان اتصال به سرور وجود ندارد.");
                }
            }
        });
    }

    @FXML
    private void markAsSold() {

        try {

            advertisement = advertisementService.markAsSold(advertisement.getId());

            AdvertisementSession.setAdvertisement(advertisement);

            render();

        } catch (ApiException e) {

            showMessage(e.getMessage());

        } catch (IOException | InterruptedException e) {

            showMessage("امکان اتصال به سرور وجود ندارد.");
        }
    }

    private void showMessage(String message) {

        if (messageLabel != null) {
            messageLabel.setText(message);
        }
    }

    private void renderImages() {

        imageGallery.getChildren().clear();

        if (advertisement.getImageUrls() == null || advertisement.getImageUrls().isEmpty()) {

            javafx.scene.layout.VBox emptyBox = new javafx.scene.layout.VBox(6);
            emptyBox.setAlignment(javafx.geometry.Pos.CENTER);
            emptyBox.setPrefSize(220, 180);
            emptyBox.getStyleClass().add("ad-card-image-frame");

            Label icon = new Label("🖼");
            icon.setStyle("-fx-font-size: 32px;");

            Label noImage = new Label("این آگهی تصویری ندارد");
            noImage.getStyleClass().add("card-caption");

            emptyBox.getChildren().addAll(icon, noImage);
            imageGallery.getChildren().add(emptyBox);

            return;
        }

        for (String imageUrl : advertisement.getImageUrls()) {

            javafx.scene.layout.StackPane frame = new javafx.scene.layout.StackPane();
            frame.setPrefSize(220, 180);
            frame.setMinSize(220, 180);
            frame.setMaxSize(220, 180);
            frame.getStyleClass().add("ad-card-image-frame");

            ImageView imageView = new ImageView();
            imageView.setFitWidth(220);
            imageView.setFitHeight(180);
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);

            javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(220, 180);
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            imageView.setClip(clip);

            String finalUrl = imageUrl.startsWith("http") ? imageUrl : "http://localhost:8080" + imageUrl;

            Image image = new Image(finalUrl, 220, 180, true, true, true);

            image.exceptionProperty().addListener((obs, oldEx, newEx) -> {
                if (newEx != null) {
                    imageView.setImage(null);
                }
            });

            if (!image.isError()) {
                imageView.setImage(image);
            }

            frame.getChildren().add(imageView);
            imageGallery.getChildren().add(frame);
        }
    }


}