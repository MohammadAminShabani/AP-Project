package divar.controller;

import divar.dto.response.AdvertisementResponse;
import divar.service.FavoriteService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.util.List;

public class FavoriteController {

    @FXML
    private ListView<AdvertisementResponse> favoriteList;

    private final FavoriteService favoriteService =
            new FavoriteService();

    @FXML
    public void initialize() {

        favoriteList.setCellFactory(param ->
                new ListCell<>() {

                    @Override
                    protected void updateItem(AdvertisementResponse item,
                                              boolean empty) {

                        super.updateItem(item, empty);

                        if (empty || item == null) {

                            setText(null);

                        } else {

                            setText(item.getTitle()
                                    + "\n"
                                    + item.getPrice()
                                    + "\n"
                                    + item.getCity());

                        }

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

        } catch (Exception e) {

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setContentText(e.getMessage());

            alert.showAndWait();

        }

    }

}