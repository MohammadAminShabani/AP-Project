package divar.controller;

import divar.config.SceneManager;
import divar.dto.request.RateSellerRequest;
import divar.dto.response.AdvertisementResponse;
import divar.network.ApiException;
import divar.service.SellerRatingService;
import divar.session.AdvertisementSession;
import divar.session.SessionManager;
import divar.util.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;

import java.io.IOException;

public class RatingController {

    @FXML
    private Label adTitleLabel;

    @FXML
    private Label sellerNameLabel;

    @FXML
    private Label messageLabel;

    @FXML
    private Spinner<Integer> scoreSpinner;

    @FXML
    private TextArea commentField;

    private final SellerRatingService ratingService =
            new SellerRatingService();

    private AdvertisementResponse advertisement;

    @FXML
    public void initialize() {

        advertisement = AdvertisementSession.getAdvertisement();

        scoreSpinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 5, 5));
        scoreSpinner.setEditable(true);

        if (advertisement == null) {
            back();
            return;
        }

        boolean isOwner = advertisement.getOwnerId() != null
                && advertisement.getOwnerId().equals(SessionManager.getUserId());

        if (isOwner) {
            showMessage("شما نمی‌توانید به آگهی خودتان امتیاز دهید.");
        }

        adTitleLabel.setText(advertisement.getTitle());
        sellerNameLabel.setText("فروشنده: "
                + (advertisement.getOwnerName() == null
                        ? "-" : advertisement.getOwnerName()));
    }

    @FXML
    private void submitRating() {

        if (advertisement == null) {
            return;
        }

        try {

            // Spinner با ویرایش دستی ممکن است مقدار متن را کامیت نکرده باشد.
            scoreSpinner.increment(0);

            Integer score = scoreSpinner.getValue();

            if (score == null || score < 1 || score > 5) {
                showMessage("امتیاز باید بین ۱ تا ۵ باشد.");
                return;
            }

            RateSellerRequest request = new RateSellerRequest();
            request.setScore(score);
            request.setComment(commentField.getText());

            ratingService.rateSeller(advertisement.getId(), request);

            showMessage(null);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("امتیاز شما با موفقیت ثبت شد.");
            alert.showAndWait();

            back();

        } catch (ApiException e) {

            showMessage(e.getMessage());

        } catch (IOException | InterruptedException e) {

            showMessage("امکان اتصال به سرور وجود ندارد.");
        }
    }

    @FXML
    private void back() {

        SceneManager.loadScene(Constants.ADVERTISEMENT, "جزئیات آگهی");

    }

    private void showMessage(String message) {

        if (messageLabel != null) {
            messageLabel.setText(message);
        }
    }

}
