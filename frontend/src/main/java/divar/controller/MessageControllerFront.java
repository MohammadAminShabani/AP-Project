package divar.controller;

import divar.config.SceneManager;
import divar.dto.request.CreateMessageRequest;
import divar.dto.response.ConversationResponse;
import divar.dto.response.MessageResponse;
import divar.network.ApiException;
import divar.service.MessageService;
import divar.session.ConversationSession;
import divar.session.SessionManager;
import divar.util.Constants;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class MessageControllerFront {

    @FXML
    private Label lblUser;

    @FXML
    private ListView<MessageResponse> messageList;

    @FXML
    private TextField txtMessage;

    @FXML
    private Button btnSend;

    private final MessageService messageService =
            new MessageService();

    private Long conversationId;

    @FXML
    public void initialize() {

        ConversationResponse conversation =
                ConversationSession.getConversation();

        if (conversation == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("گفتگویی برای نمایش انتخاب نشده است.");
            alert.showAndWait();

            SceneManager.loadScene(Constants.CONVERSATIONS, "گفتگوها");

            return;
        }

        conversationId = conversation.getId();

        lblUser.setText(conversation.getOtherUser() == null
                ? "" : conversation.getOtherUser());

        messageList.setCellFactory(param -> new MessageCell());

        loadMessages();
    }

    private void loadMessages() {

        try {

            List<MessageResponse> messages =
                    messageService.getMessages(conversationId);

            ObservableList<MessageResponse> items =
                    FXCollections.observableArrayList(messages);

            messageList.setItems(items);

            if (!items.isEmpty()) {
                Platform.runLater(() ->
                        messageList.scrollTo(items.size() - 1));
            }

        } catch (ApiException e) {

            showError(e.getMessage());

        } catch (IOException | InterruptedException e) {

            showError("امکان اتصال به سرور وجود ندارد.");
        }

    }

    @FXML
    private void sendMessage() {

        String text = txtMessage.getText();

        if (text == null || text.isBlank()) {
            return;
        }

        try {

            CreateMessageRequest request =
                    new CreateMessageRequest();

            request.setConversationId(conversationId);
            request.setContent(text.trim());

            messageService.send(request);

            txtMessage.clear();

            loadMessages();

        } catch (ApiException e) {

            showError(e.getMessage());

        } catch (IOException | InterruptedException e) {

            showError("امکان اتصال به سرور وجود ندارد.");
        }

    }

    @FXML
    private void back() {
        ConversationSession.clear();
        SceneManager.loadScene(Constants.CONVERSATIONS, "گفتگوها");
    }

    @FXML
    private void refresh() {
        loadMessages();
    }

    private void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * سلول سفارشی برای نمایش حباب پیام‌ها؛
     * پیام‌های ارسالی کاربر سمت راست و پیام‌های طرف مقابل سمت چپ نمایش داده می‌شوند.
     */
    private static class MessageCell extends ListCell<MessageResponse> {

        private final Label content = new Label();
        private final Label meta = new Label();
        private final VBox bubble = new VBox(4, content, meta);
        private final HBox wrapper = new HBox(bubble);

        MessageCell() {
            content.setWrapText(true);
            content.getStyleClass().add("chat-bubble-text");
            meta.getStyleClass().add("chat-bubble-meta");
            bubble.getStyleClass().add("chat-bubble");
            bubble.setMaxWidth(420);
            setPadding(new Insets(4, 12, 4, 12));
        }

        @Override
        protected void updateItem(MessageResponse item, boolean empty) {

            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
                return;
            }

            boolean isMine = item.getSender() != null
                    && item.getSender().equalsIgnoreCase(SessionManager.getUsername());

            content.setText(item.getContent());
            meta.setText(item.getSender());

            bubble.getStyleClass().removeAll("chat-bubble-mine", "chat-bubble-theirs");
            bubble.getStyleClass().add(isMine ? "chat-bubble-mine" : "chat-bubble-theirs");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            wrapper.getChildren().setAll(
                    isMine ? spacer : bubble,
                    isMine ? bubble : spacer
            );
            wrapper.setAlignment(Pos.CENTER);

            setText(null);
            setGraphic(wrapper);
        }
    }

}
