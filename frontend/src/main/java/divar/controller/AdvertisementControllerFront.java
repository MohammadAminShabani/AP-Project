package divar.controller;

import divar.config.SceneManager;
import divar.dto.response.AdvertisementResponse;
import divar.network.ApiException;
import divar.service.AdvertisementService;
import divar.service.ConversationService;
import divar.service.FavoriteService;
import divar.dto.response.ConversationResponse;
import divar.session.AdvertisementSession;
import divar.session.ConversationSession;
import divar.session.SessionManager;
import divar.util.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.List;

public class AdvertisementControllerFront {

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
    private Button chatButton;

    @FXML
    private Button favoriteButton;

    @FXML
    private Button rateButton;

    private final AdvertisementService advertisementService =
            new AdvertisementService();
    private final ConversationService conversationService =
            new ConversationService();
    private final FavoriteService favoriteService =
            new FavoriteService();
    private AdvertisementResponse advertisement;
    private boolean isFavorite = false;

    @FXML
    public void initialize() {
        System.out.println("AdvertisementControllerFront initialized");

        advertisement = AdvertisementSession.getAdvertisement();

        if (advertisement == null) {

            back();

            return;
        }

        render();
    }

    private void render() {

        titleLabel.setText(advertisement.getTitle());

        priceLabel.setText("قیمت: "
                + String.format("%,d", advertisement.getPrice())
                + " تومان");

        cityLabel.setText("شهر: " + advertisement.getCity());

        categoryLabel.setText("دسته‌بندی: " + advertisement.getCategory());

        ownerLabel.setText("فروشنده: " + advertisement.getOwnerName());

        rateLabel.setText("امتیاز: " + advertisement.getAverageRate());

        statusLabel.setText("وضعیت: " + statusLabelFa(advertisement.getStatus()));

        descriptionArea.setText(advertisement.getDescription());

        boolean isOwner = advertisement.getOwnerId() != null
                && advertisement.getOwnerId().equals(SessionManager.getUserId());

        ownerActionsBox.setVisible(isOwner);
        ownerActionsBox.setManaged(isOwner);
        chatButton.setVisible(!isOwner);
        chatButton.setManaged(!isOwner);
        favoriteButton.setVisible(!isOwner);
        favoriteButton.setManaged(!isOwner);
        rateButton.setVisible(!isOwner);
        rateButton.setManaged(!isOwner);
        // فقط آگهی فعال قابل تغییر وضعیت به «فروخته شد» است.
        boolean canMarkSold = isOwner && "ACTIVE".equals(advertisement.getStatus());

        markSoldButton.setDisable(!canMarkSold);

        if (!isOwner) {
            loadFavoriteState();
        }
    }

    private void loadFavoriteState() {

        try {

            List<AdvertisementResponse> favorites =
                    favoriteService.getFavorites();

            isFavorite = favorites.stream()
                    .anyMatch(fav -> fav.getId().equals(advertisement.getId()));

            updateFavoriteButtonText();

        } catch (Exception e) {
            // اگر دریافت لیست علاقه‌مندی‌ها با خطا مواجه شد،
            // فرض می‌کنیم آگهی هنوز علاقه‌مند نشده و کاربر می‌تواند دوباره تلاش کند.
            isFavorite = false;
            updateFavoriteButtonText();
        }
    }

    private void updateFavoriteButtonText() {

        favoriteButton.setText(
                isFavorite
                        ? "★ حذف از علاقه‌مندی‌ها"
                        : "☆ افزودن به علاقه‌مندی‌ها");
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

        SceneManager.loadScene(
                Constants.HOME,
                "خانه"
        );

    }

    @FXML
    private void editAdvertisement() {

        SceneManager.loadScene(Constants.EDIT_AD, "ویرایش آگهی");

    }

    @FXML
    private void deleteAdvertisement() {

        Alert confirm = new Alert(
                Alert.AlertType.CONFIRMATION,
                "آیا از حذف این آگهی مطمئن هستید؟"
        );

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
    @FXML
    private void toggleFavorite() {

        try {

            if (isFavorite) {
                favoriteService.remove(advertisement.getId());
                isFavorite = false;
            } else {
                favoriteService.add(advertisement.getId());
                isFavorite = true;
            }

            updateFavoriteButtonText();
            showMessage(null);

        } catch (ApiException e) {

            showMessage(e.getMessage());

        } catch (IOException | InterruptedException e) {

            showMessage("امکان اتصال به سرور وجود ندارد.");
        }
    }

    @FXML
    private void openRating() {

        SceneManager.loadScene(Constants.RATING, "امتیازدهی به فروشنده");

    }

    @FXML
    private void openConversation() {

        System.out.println("Button clicked");

        try {

            ConversationResponse conversation =
                    conversationService.createConversation(advertisement.getId());

            System.out.println("Conversation created");

            ConversationSession.setConversation(conversation);

            SceneManager.loadScene(Constants.CHAT, "گفتگو");

        } catch (Exception e) {

            e.printStackTrace();

            showMessage(e.getMessage());
        }
    }

    private void showMessage(String message) {

        if (messageLabel != null) {
            messageLabel.setText(message);
        }
    }

}