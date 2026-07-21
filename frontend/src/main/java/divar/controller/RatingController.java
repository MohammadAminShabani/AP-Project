package divar.controller;

import divar.dto.request.RateSellerRequest;
import divar.service.SellerRatingService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class RatingController {

    @FXML
    private TextField scoreField;

    @FXML
    private TextArea commentField;

    private final SellerRatingService ratingService =
            new SellerRatingService();

    private Long advertisementId;

    public void setAdvertisementId(Long advertisementId) {

        this.advertisementId = advertisementId;

    }

    @FXML
    private void submitRating() {

        try {

            RateSellerRequest request =
                    new RateSellerRequest();

            request.setScore(
                    Integer.parseInt(scoreField.getText()));

            request.setComment(
                    commentField.getText());

            ratingService.rateSeller(
                    advertisementId,
                    request);

            Alert alert =
                    new Alert(Alert.AlertType.INFORMATION);

            alert.setContentText(
                    "Rating submitted successfully.");

            alert.showAndWait();

        } catch (Exception e) {

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setContentText(
                    e.getMessage());

            alert.showAndWait();

        }

    }

}