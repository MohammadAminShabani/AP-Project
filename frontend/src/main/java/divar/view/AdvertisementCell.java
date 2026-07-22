package divar.view;

import divar.dto.response.AdvertisementResponse;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Locale;


public class AdvertisementCell extends ListCell<AdvertisementResponse> {

    private static final double IMAGE_WIDTH = 168;
    private static final double IMAGE_HEIGHT = 132;
    private static final int DESCRIPTION_MAX_LENGTH = 100;

    private final HBox root = new HBox();

    private final StackPane imageFrame = new StackPane();
    private final ImageView imageView = new ImageView();
    private final Label statusBadge = new Label();

    private final VBox content = new VBox();

    private final Label title = new Label();
    private final Label description = new Label();

    private final HBox tagsRow = new HBox();
    private final Label cityTag = new Label();
    private final Label categoryTag = new Label();

    private final HBox bottomRow = new HBox();
    private final Label ratingLabel = new Label();
    private final Label price = new Label();

    private final Image defaultImage = new Image(getClass().getResourceAsStream("/images/no-image.png"));

    public AdvertisementCell() {

        root.getStyleClass().add("ad-card");
        root.setSpacing(16);
        root.setPadding(new Insets(14));
        root.setAlignment(Pos.TOP_RIGHT);

        buildImageFrame();
        buildContent();

        root.getChildren().addAll(imageFrame, content);
    }

    private void buildImageFrame() {

        imageView.setFitWidth(IMAGE_WIDTH);
        imageView.setFitHeight(IMAGE_HEIGHT);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);

        Rectangle clip = new Rectangle(IMAGE_WIDTH, IMAGE_HEIGHT);
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        imageView.setClip(clip);

        imageFrame.getStyleClass().add("ad-card-image-frame");
        imageFrame.setPrefSize(IMAGE_WIDTH, IMAGE_HEIGHT);
        imageFrame.setMinSize(IMAGE_WIDTH, IMAGE_HEIGHT);
        imageFrame.setMaxSize(IMAGE_WIDTH, IMAGE_HEIGHT);
        imageFrame.setAlignment(Pos.TOP_LEFT);

        statusBadge.getStyleClass().add("ad-card-badge");
        StackPane.setMargin(statusBadge, new Insets(8));

        imageFrame.getChildren().addAll(imageView, statusBadge);
    }

    private void buildContent() {

        title.getStyleClass().add("ad-card-title");
        title.setWrapText(true);
        title.setMaxWidth(Double.MAX_VALUE);

        description.getStyleClass().add("ad-card-description");
        description.setWrapText(true);
        description.setMaxWidth(Double.MAX_VALUE);

        cityTag.getStyleClass().add("tag-chip");
        categoryTag.getStyleClass().add("tag-chip");
        tagsRow.setSpacing(8);
        tagsRow.getChildren().addAll(cityTag, categoryTag);

        ratingLabel.getStyleClass().add("ad-card-rating");

        price.getStyleClass().add("ad-card-price");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        bottomRow.setAlignment(Pos.CENTER_LEFT);
        bottomRow.getChildren().addAll(ratingLabel, spacer, price);

        content.setSpacing(8);
        content.getChildren().addAll(title, description, tagsRow, bottomRow);

        HBox.setHgrow(content, Priority.ALWAYS);
    }

    @Override
    protected void updateItem(AdvertisementResponse ad, boolean empty) {

        super.updateItem(ad, empty);

        if (empty || ad == null) {
            setGraphic(null);
            return;
        }

        title.setText(ad.getTitle());
        description.setText(truncate(ad.getDescription()));

        cityTag.setText("📍 " + safe(ad.getCity()));
        categoryTag.setText("🏷 " + safe(ad.getCategory()));

        if (ad.getAverageRate() != null && ad.getAverageRate() > 0) {
            ratingLabel.setText(String.format(Locale.US, "⭐ %.1f", ad.getAverageRate()));
            ratingLabel.setVisible(true);
            ratingLabel.setManaged(true);
        } else {
            ratingLabel.setVisible(false);
            ratingLabel.setManaged(false);
        }

        price.setText(ad.getPrice() != null
                ? String.format(Locale.US, "%,d تومان", ad.getPrice())
                : "قیمت نامشخص");

        applyStatusBadge(ad.getStatus());
        loadImage(ad);

        setGraphic(root);
    }

    private void applyStatusBadge(String status) {

        statusBadge.getStyleClass().removeAll("badge-sold", "badge-pending", "badge-rejected");

        if (status == null) {
            statusBadge.setVisible(false);
            statusBadge.setManaged(false);
            return;
        }

        switch (status.toUpperCase(Locale.ROOT)) {

            case "SOLD" -> {
                statusBadge.setText("فروخته شده");
                statusBadge.getStyleClass().add("badge-sold");
                show(statusBadge);
            }
            case "PENDING" -> {
                statusBadge.setText("در انتظار بررسی");
                statusBadge.getStyleClass().add("badge-pending");
                show(statusBadge);
            }
            case "REJECTED" -> {
                statusBadge.setText("رد شده");
                statusBadge.getStyleClass().add("badge-rejected");
                show(statusBadge);
            }
            default -> {
                statusBadge.setVisible(false);
                statusBadge.setManaged(false);
            }
        }
    }

    private void show(Label badge) {
        badge.setVisible(true);
        badge.setManaged(true);
    }

    private String truncate(String text) {

        if (text == null) {
            return "";
        }

        if (text.length() <= DESCRIPTION_MAX_LENGTH) {
            return text;
        }

        return text.substring(0, DESCRIPTION_MAX_LENGTH).trim() + "…";
    }

    private String safe(String value) {
        return value == null || value.isBlank() ? "نامشخص" : value;
    }

    private void loadImage(AdvertisementResponse ad) {

        List<String> images = ad.getImageUrls();

        if (images == null || images.isEmpty()) {
            imageView.setImage(defaultImage);
            return;
        }

        String imageUrl = images.get(0);
        String finalUrl = imageUrl.startsWith("http")
                ? imageUrl
                : "http://localhost:8080" + imageUrl;

        try {
            Image image = new Image(finalUrl, IMAGE_WIDTH, IMAGE_HEIGHT, true, true, true);

            image.exceptionProperty().addListener((obs, oldException, newException) -> {
                if (newException != null) {
                    System.out.println("IMAGE LOAD FAILED: " + finalUrl);
                    newException.printStackTrace();
                    imageView.setImage(defaultImage);
                }
            });

            if (image.isError()) {
                System.out.println("IMAGE LOAD FAILED (immediate): " + finalUrl);
                imageView.setImage(defaultImage);
            } else {
                imageView.setImage(image);
            }

        } catch (Exception e) {
            e.printStackTrace();
            imageView.setImage(defaultImage);
        }
    }
}
