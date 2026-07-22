package divar.controller;

import divar.config.SceneManager;
import divar.dto.response.ConversationResponse;
import divar.network.ApiException;
import divar.service.ConversationService;
import divar.session.ConversationSession;
import divar.util.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ConversationControllerFront {

    @FXML
    private ListView<ConversationResponse> conversationList;

    private final ConversationService conversationService =
            new ConversationService();

    @FXML
    public void initialize() {

        conversationList.setCellFactory(param -> new ConversationCell());

        conversationList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                openSelected();
            }
        });

        loadConversations();
    }

    private void loadConversations() {

        try {

            List<ConversationResponse> conversations =
                    conversationService.getMyConversations();

            ObservableList<ConversationResponse> items =
                    FXCollections.observableArrayList(conversations);

            conversationList.setItems(items);

        } catch (ApiException e) {

            showError(e.getMessage());

        } catch (IOException | InterruptedException e) {

            showError("امکان اتصال به سرور وجود ندارد.");
        }
    }

    @FXML
    private void openChat() {
        openSelected();
    }

    private void openSelected() {

        ConversationResponse conversation =
                conversationList.getSelectionModel().getSelectedItem();

        if (conversation == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("ابتدا یک گفتگو را انتخاب کن.");
            alert.show();

            return;
        }

        ConversationSession.setConversation(conversation);

        SceneManager.loadScene(Constants.CHAT, "گفتگو");
    }

    @FXML
    private void refresh() {
        loadConversations();
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

    private static class ConversationCell extends ListCell<ConversationResponse> {

        private static final DateTimeFormatter TIME_FORMAT =
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

        private final Label title = new Label();
        private final Label meta = new Label();
        private final Label lastMessage = new Label();
        private final VBox root = new VBox(6, title, meta, lastMessage);

        ConversationCell() {
            root.getStyleClass().add("conversation-cell");
            root.setMaxWidth(Double.MAX_VALUE);
            title.getStyleClass().add("conversation-title");
            meta.getStyleClass().add("conversation-meta");
            lastMessage.getStyleClass().add("conversation-last-message");
            lastMessage.setWrapText(true);
            setPadding(new Insets(0, 0, 10, 0));
        }

        @Override
        protected void updateItem(ConversationResponse item, boolean empty) {

            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
                return;
            }

            title.setText(item.getAdvertisementTitle() == null
                    ? "آگهی" : item.getAdvertisementTitle());

            String otherUser = item.getOtherUser() == null ? "" : item.getOtherUser();
            String timeText = item.getLastMessageTime() == null
                    ? "" : " · " + item.getLastMessageTime().format(TIME_FORMAT);

            meta.setText("طرف گفتگو: " + otherUser + timeText);

            lastMessage.setText(item.getLastMessage() == null || item.getLastMessage().isBlank()
                    ? "هنوز پیامی ارسال نشده است."
                    : item.getLastMessage());

            setText(null);
            setGraphic(root);
        }
    }

}
