package divar.controller;

import divar.config.SceneManager;
import divar.dto.response.AdvertisementResponse;
import divar.network.ApiException;
import divar.service.FavoriteService;
import divar.session.AdvertisementSession;
import divar.util.Constants;
import divar.view.AdvertisementCell;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.List;

public class FavoriteControllerFront {

    @FXML
    private ListView<AdvertisementResponse> favoriteList;

    private final FavoriteService favoriteService =
            new FavoriteService();

    @FXML
    public void initialize() {

        favoriteList.setCellFactory(param -> new AdvertisementCell());

        favoriteList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                openSelected();
            }
        });

        loadFavorites();
    }

    private void loadFavorites() {

        try {

            List<AdvertisementResponse> ads =
                    favoriteService.getFavorites();

            favoriteList.setItems(
                    FXCollections.observableArrayList(ads));

        } catch (ApiException e) {

            showError(e.getMessage());

        } catch (IOException | InterruptedException e) {

            showError("امکان اتصال به سرور وجود ندارد.");
        }

    }

    private void openSelected() {

        AdvertisementResponse selected =
                favoriteList.getSelectionModel().getSelectedItem();

        if (selected == null) {
            return;
        }

        AdvertisementSession.setAdvertisement(selected);

        SceneManager.loadScene(Constants.ADVERTISEMENT, "جزئیات آگهی");
    }

    @FXML
    private void removeSelected() {

        AdvertisementResponse selected =
                favoriteList.getSelectionModel().getSelectedItem();

        if (selected == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("ابتدا یک آگهی را از لیست انتخاب کن.");
            alert.show();

            return;
        }

        try {

            favoriteService.remove(selected.getId());

            loadFavorites();

        } catch (ApiException e) {

            showError(e.getMessage());

        } catch (IOException | InterruptedException e) {

            showError("امکان اتصال به سرور وجود ندارد.");
        }
    }

    @FXML
    private void refresh() {
        loadFavorites();
    }

    @FXML
    private void back() {
        SceneManager.loadScene(Constants.HOME, "خانه");
    }

    private void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
