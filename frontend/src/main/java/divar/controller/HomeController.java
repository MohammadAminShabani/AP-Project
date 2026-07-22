package divar.controller;

import divar.config.SceneManager;
import divar.dto.response.AdvertisementResponse;
import divar.network.ApiException;
import divar.service.AdvertisementService;
import divar.session.AdvertisementSession;
import divar.session.SessionManager;
import divar.util.Constants;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HomeController {
    @FXML private Label welcomeLabel;
    @FXML private Label resultCountLabel;
    @FXML private ListView<AdvertisementResponse> advertisementListView;
    @FXML private javafx.scene.layout.VBox emptyStateBox;
    @FXML private Button adminPanelButton;
    @FXML private TextField searchField;
    @FXML private ComboBox<String> sortComboBox;

    private final AdvertisementService advertisementService = new AdvertisementService();
    private List<AdvertisementResponse> allAdvertisements = new ArrayList<>();

    @FXML
    public void initialize() {
        welcomeLabel.setText("خوش آمدی " + SessionManager.getUsername());
        boolean isAdmin = "ADMIN".equalsIgnoreCase(SessionManager.getRole());
        adminPanelButton.setVisible(isAdmin); adminPanelButton.setManaged(isAdmin);
        advertisementListView.setCellFactory(list -> new divar.view.AdvertisementCell());
        sortComboBox.getItems().addAll("جدیدترین", "ارزان‌ترین", "گران‌ترین");
        sortComboBox.setValue("جدیدترین");
        searchField.textProperty().addListener((obs, oldValue, newValue) -> applyFilters());
        sortComboBox.valueProperty().addListener((obs, oldValue, newValue) -> applyFilters());
        advertisementListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                AdvertisementResponse ad = advertisementListView.getSelectionModel().getSelectedItem();
                if (ad != null) openAdvertisement(ad);
            }
        });
        loadAdvertisements();
    }

    private void loadAdvertisements() {
        try {
            allAdvertisements = new ArrayList<>(advertisementService.getAll());
            applyFilters();
        } catch (ApiException e) { showError(e.getMessage()); }
        catch (IOException | InterruptedException e) { showError("ارتباط با سرور برقرار نشد."); }
    }

    private void applyFilters() {
        String query = searchField == null || searchField.getText() == null ? "" : searchField.getText().trim().toLowerCase();
        List<AdvertisementResponse> filtered = allAdvertisements.stream()
                .filter(ad -> query.isBlank() || ((ad.getTitle() != null && ad.getTitle().toLowerCase().contains(query)) || (ad.getDescription() != null && ad.getDescription().toLowerCase().contains(query))))
                .sorted(getComparator())
                .toList();
        advertisementListView.setItems(FXCollections.observableArrayList(filtered));
        if (resultCountLabel != null) resultCountLabel.setText(filtered.size() + " آگهی");
        if (emptyStateBox != null) {
            boolean isEmpty = filtered.isEmpty();
            emptyStateBox.setVisible(isEmpty);
            emptyStateBox.setManaged(isEmpty);
        }
    }

    private Comparator<AdvertisementResponse> getComparator() {
        String sort = sortComboBox == null ? "جدیدترین" : sortComboBox.getValue();
        if ("ارزان‌ترین".equals(sort)) return Comparator.comparing(ad -> ad.getPrice() == null ? Long.MAX_VALUE : ad.getPrice());
        if ("گران‌ترین".equals(sort)) return Comparator.comparing((AdvertisementResponse ad) -> ad.getPrice() == null ? 0L : ad.getPrice()).reversed();
        return Comparator.comparing((AdvertisementResponse ad) -> ad.getId() == null ? 0L : ad.getId()).reversed();
    }

    private void openAdvertisement(AdvertisementResponse advertisement) { AdvertisementSession.setAdvertisement(advertisement); SceneManager.loadScene(Constants.ADVERTISEMENT, "جزئیات آگهی"); }
    @FXML private void refresh() { loadAdvertisements(); }
    @FXML private void createAdvertisement() { SceneManager.loadScene(Constants.CREATE_AD, "ثبت آگهی"); }
    @FXML private void openConversations() { SceneManager.loadScene(Constants.CONVERSATIONS, "گفتگوها"); }
    @FXML private void openFavorites() { SceneManager.loadScene(Constants.FAVORITES, "علاقه‌مندی‌ها"); }
    @FXML private void openAdminPanel() { SceneManager.loadScene(Constants.ADMIN, "پنل مدیریت"); }
    @FXML private void logout() { SessionManager.clear(); SceneManager.loadScene(Constants.LOGIN, "ورود"); }
    private void showError(String message) { Alert alert = new Alert(Alert.AlertType.ERROR); alert.setHeaderText(null); alert.setContentText(message); alert.showAndWait(); }
}
