package divar.view;

import divar.dto.response.AdvertisementResponse;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.util.List;

public class AdvertisementCell extends ListCell<AdvertisementResponse> {


    private final HBox root = new HBox();


    private final ImageView imageView = new ImageView();


    private final VBox content = new VBox();


    private final Label title = new Label();


    private final Label description = new Label();


    private final Label city = new Label();


    private final Label category = new Label();


    private final Label price = new Label();


    private final Image defaultImage = new Image(getClass().getResourceAsStream("/images/no-image.png"));


    public AdvertisementCell() {


        root.setSpacing(15);

        root.setPadding(new Insets(12));


        root.setStyle("""
                -fx-background-color:white;
                -fx-background-radius:12;
                -fx-border-radius:12;
                -fx-border-color:#dddddd;
                """);


        imageView.setFitWidth(150);

        imageView.setFitHeight(120);

        imageView.setPreserveRatio(true);

        imageView.setSmooth(true);


        title.setStyle("""
                -fx-font-size:18px;
                -fx-font-weight:bold;
                -fx-text-fill:#222222;
                """);

        title.setWrapText(true);


        description.setStyle("""
                -fx-font-size:14px;
                -fx-text-fill:#666666;
                """);

        description.setWrapText(true);

        description.setMaxWidth(400);


        city.setStyle("""
                -fx-font-size:14px;
                -fx-text-fill:#555555;
                """);


        category.setStyle("""
                -fx-font-size:14px;
                -fx-text-fill:#555555;
                """);


        price.setStyle("""
                -fx-font-size:16px;
                -fx-font-weight:bold;
                -fx-text-fill:#2e7d32;
                """);


        content.setSpacing(8);


        content.getChildren().addAll(title, description, city, category, price);


        HBox.setHgrow(content, Priority.ALWAYS);


        root.getChildren().addAll(imageView, content);
    }


    @Override
    protected void updateItem(AdvertisementResponse ad, boolean empty) {


        super.updateItem(ad, empty);


        if (empty || ad == null) {

            setGraphic(null);

            return;
        }


        // عنوان
        title.setText(ad.getTitle());


        // توضیحات
        description.setText(ad.getDescription());


        // شهر
        city.setText("📍 " + ad.getCity());


        // دسته بندی
        category.setText("🏷 " + ad.getCategory());


        // قیمت
        price.setText(String.format("%,d تومان", ad.getPrice()));


        loadImage(ad);


        setGraphic(root);
    }


    private void loadImage(AdvertisementResponse ad) {


        List<String> images = ad.getImageUrls();


        if (images == null || images.isEmpty()) {

            imageView.setImage(defaultImage);
            return;
        }


        String imageUrl = images.get(0);


        String finalUrl;


        if (imageUrl.startsWith("http")) {

            finalUrl = imageUrl;

        } else {

            finalUrl = "http://localhost:8080" + imageUrl;
        }


        System.out.println("LOADING IMAGE: " + finalUrl);


        try {
            System.out.println(
                    "IMAGE URL => " + finalUrl
            );

            // پارامتر ششم (backgroundLoading=true) حیاتی است: بدون آن، لود
            // به‌صورت synchronous انجام می‌شود و اگر لود همان لحظه fail شود،
            // exceptionProperty قبل از اینکه listener زیر اضافه شود ست می‌شود
            // و دیگر هرگز اطلاع‌رسانی نمی‌شود (imageView خالی/خراب می‌ماند
            // بدون هیچ پیام خطایی). با backgroundLoading=true لود در ترد
            // پس‌زمینه انجام می‌شود و listener همیشه فرصت گرفتن exception را دارد.
            Image image = new Image(finalUrl, 150, 120, true, true, true);

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