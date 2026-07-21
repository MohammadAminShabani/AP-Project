package divar.view;

import divar.dto.response.AdvertisementResponse;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class AdvertisementCell extends ListCell<AdvertisementResponse> {

    private final VBox root = new VBox(8);
    private final Label title = new Label();
    private final Label description = new Label();
    private final Label city = new Label();
    private final Label category = new Label();
    private final Label price = new Label();
    private final Label badge = new Label("فعال");

    public AdvertisementCell() {
        root.getStyleClass().add("ad-card");
        root.setPadding(new Insets(18, 20, 18, 20));
        root.setMaxWidth(Double.MAX_VALUE);
        root.setMinHeight(150);

        title.getStyleClass().add("ad-card-title");
        title.setMaxWidth(Double.MAX_VALUE);
        title.setWrapText(true);

        description.getStyleClass().add("ad-card-description");
        description.setWrapText(true);
        description.setMaxHeight(42);

        city.getStyleClass().add("ad-card-meta");
        category.getStyleClass().add("ad-card-meta");
        price.getStyleClass().add("ad-card-price");
        badge.getStyleClass().add("ad-card-badge");
    }

    @Override
    protected void updateItem(AdvertisementResponse ad, boolean empty) {
        super.updateItem(ad, empty);

        if (empty || ad == null) {
            setText(null);
            setGraphic(null);
            return;
        }

        String adTitle = ad.getTitle();
        title.setText(adTitle == null || adTitle.trim().isEmpty() ? "بدون عنوان" : adTitle.trim());
        description.setText(ad.getDescription() == null ? "" : ad.getDescription());
        city.setText("📍 " + safe(ad.getCity()));
        category.setText("🏷 " + safe(ad.getCategory()));
        price.setText(formatPrice(ad.getPrice()));

        HBox meta = new HBox(16, city, category);
        meta.setAlignment(Pos.CENTER_RIGHT);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox bottom = new HBox(12, meta, spacer, badge, price);
        bottom.setAlignment(Pos.CENTER_RIGHT);

        root.getChildren().setAll(title, description, bottom);
        setText(null);
        setGraphic(root);
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    private String formatPrice(Long value) {
        if (value == null || value <= 0) return "قیمت توافقی";
        return String.format("%,d تومان", value);
    }
}
