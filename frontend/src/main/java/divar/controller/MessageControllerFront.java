package divar.controller;

import divar.config.SceneManager;
import divar.dto.request.CreateMessageRequest;
import divar.dto.response.MessageResponse;
import divar.service.MessageService;
import divar.util.Constants;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import divar.dto.response.ConversationResponse;
import divar.session.ConversationSession;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
            return;
        }

        conversationId = conversation.getId();

        lblUser.setText(conversation.getOtherUser());

        messageList.setCellFactory(param ->
                new ListCell<>() {

                    @Override
                    protected void updateItem(MessageResponse item,
                                              boolean empty) {

                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.getSender() + " : " + item.getContent());
                        }
                    }
                });

        loadMessages();
    }
    private void loadMessages() {

        try {

            List<MessageResponse> messages =
                    messageService.getMessages(conversationId);

            ObservableList<MessageResponse> items =
                    FXCollections.observableArrayList(messages);

            messageList.setItems(items);

            Platform.runLater(() ->
                    messageList.scrollTo(items.size() - 1));

        } catch (Exception e) {

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setContentText(e.getMessage());

            alert.show();

        }

    }

    @FXML
    private void sendMessage() {

        String text = txtMessage.getText();

        if (text == null || text.isBlank())
            return;

        try {

            CreateMessageRequest request =
                    new CreateMessageRequest();

            request.setConversationId(conversationId);

            request.setContent(text);

            messageService.send(request);

            txtMessage.clear();

            loadMessages();

        } catch (Exception e) {

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setContentText(e.getMessage());

            alert.show();

        }

    }
    @FXML
    private void back() {
        SceneManager.switchScene(Constants.CONVERSATIONS);
    }
    @FXML
    private void refresh() {

        loadMessages();

    }

}