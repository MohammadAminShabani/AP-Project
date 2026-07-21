package divar.controller;

import divar.config.SceneManager;
import divar.dto.response.AdvertisementResponse;
import divar.dto.response.UserResponse;
import divar.network.ApiException;
import divar.service.AdminService;
import divar.session.SessionManager;
import divar.util.Constants;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.List;

public class AdminControllerFront {

    @FXML
    private ListView<AdvertisementResponse> pendingAdsListView;

    @FXML
    private ListView<UserResponse> usersListView;

    @FXML
    private Label messageLabel;

    private final AdminService adminService = new AdminService();

    @FXML
    public void initialize() {

        if (!"ADMIN".equalsIgnoreCase(SessionManager.getRole())) {

            SceneManager.loadScene(Constants.HOME, "خانه");
            return;
        }

        pendingAdsListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(AdvertisementResponse ad, boolean empty) {
                super.updateItem(ad, empty);

                if (empty || ad == null) {
                    setText(null);
                } else {
                    setText(ad.getTitle()
                            + "  |  فروشنده: " + ad.getOwnerName()
                            + "  |  وضعیت: " + ad.getStatus()
                            + "  |  " + String.format("%,d", ad.getPrice()) + " تومان");
                }
            }
        });

        usersListView.setCellFactory(list -> new ListCell<>() {
            @Override
            protected void updateItem(UserResponse user, boolean empty) {
                super.updateItem(user, empty);

                if (empty || user == null) {
                    setText(null);
                } else {
                    setText(user.getFullName()
                            + " (" + user.getUsername() + ")"
                            + "  |  نقش: " + user.getRole()
                            + "  |  وضعیت: " + user.getStatus());
                }
            }
        });

        refreshAds();
        refreshUsers();
    }

    @FXML
    private void refreshAds() {

        try {

            List<AdvertisementResponse> allAds = adminService.getAllAdvertisements();

            List<AdvertisementResponse> pending = allAds.stream()
                    .filter(ad -> "PENDING".equals(ad.getStatus()))
                    .toList();

            pendingAdsListView.setItems(FXCollections.observableArrayList(pending));

        } catch (ApiException e) {
            showMessage(e.getMessage());
        } catch (IOException | InterruptedException e) {
            showMessage("امکان اتصال به سرور وجود ندارد.");
        }
    }

    @FXML
    private void refreshUsers() {

        try {

            List<UserResponse> users = adminService.getAllUsers();

            usersListView.setItems(FXCollections.observableArrayList(users));

        } catch (ApiException e) {
            showMessage(e.getMessage());
        } catch (IOException | InterruptedException e) {
            showMessage("امکان اتصال به سرور وجود ندارد.");
        }
    }

    @FXML
    private void approveSelectedAd() {

        AdvertisementResponse selected =
                pendingAdsListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showMessage("ابتدا یک آگهی را انتخاب کنید.");
            return;
        }

        try {
            adminService.approveAdvertisement(selected.getId());
            showMessage("آگهی تایید شد.");
            refreshAds();
        } catch (ApiException e) {
            showMessage(e.getMessage());
        } catch (IOException | InterruptedException e) {
            showMessage("امکان اتصال به سرور وجود ندارد.");
        }
    }

    @FXML
    private void rejectSelectedAd() {

        AdvertisementResponse selected =
                pendingAdsListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showMessage("ابتدا یک آگهی را انتخاب کنید.");
            return;
        }

        try {
            adminService.rejectAdvertisement(selected.getId());
            showMessage("آگهی رد شد.");
            refreshAds();
        } catch (ApiException e) {
            showMessage(e.getMessage());
        } catch (IOException | InterruptedException e) {
            showMessage("امکان اتصال به سرور وجود ندارد.");
        }
    }

    @FXML
    private void deleteSelectedAd() {

        AdvertisementResponse selected =
                pendingAdsListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showMessage("ابتدا یک آگهی را انتخاب کنید.");
            return;
        }

        try {
            adminService.deleteAdvertisement(selected.getId());
            showMessage("آگهی حذف شد.");
            refreshAds();
        } catch (ApiException e) {
            showMessage(e.getMessage());
        } catch (IOException | InterruptedException e) {
            showMessage("امکان اتصال به سرور وجود ندارد.");
        }
    }

    @FXML
    private void blockSelectedUser() {

        UserResponse selected = usersListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showMessage("ابتدا یک کاربر را انتخاب کنید.");
            return;
        }

        try {
            adminService.blockUser(selected.getId());
            showMessage("کاربر مسدود شد.");
            refreshUsers();
        } catch (ApiException e) {
            showMessage(e.getMessage());
        } catch (IOException | InterruptedException e) {
            showMessage("امکان اتصال به سرور وجود ندارد.");
        }
    }

    @FXML
    private void unblockSelectedUser() {

        UserResponse selected = usersListView.getSelectionModel().getSelectedItem();

        if (selected == null) {
            showMessage("ابتدا یک کاربر را انتخاب کنید.");
            return;
        }

        try {
            adminService.unblockUser(selected.getId());
            showMessage("کاربر فعال شد.");
            refreshUsers();
        } catch (ApiException e) {
            showMessage(e.getMessage());
        } catch (IOException | InterruptedException e) {
            showMessage("امکان اتصال به سرور وجود ندارد.");
        }
    }

    @FXML
    private void back() {

        SceneManager.loadScene(Constants.HOME, "خانه");
    }

    private void showMessage(String message) {

        if (messageLabel != null) {
            messageLabel.setText(message);
        }
    }
}
