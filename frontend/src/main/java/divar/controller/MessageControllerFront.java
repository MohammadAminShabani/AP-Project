package divar.controller;

import divar.dto.request.CreateMessageRequest;
import divar.dto.response.MessageResponse;
import divar.service.MessageService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    public void setConversation(Long conversationId,
                                String username) {

        this.conversationId = conversationId;

        lblUser.setText(username);

        loadMessages();
    }

    @FXML
    public void initialize() {

        messageList.setCellFactory(param ->
                new ListCell<>() {

                    @Override
                    protected void updateItem(MessageResponse item,
                                              boolean empty) {

                        super.updateItem(item, empty);

                        if (empty || item == null) {

                            setText(null);

                        } else {

                            String text =
                                    item.getSender()
                                            + " : "
                                            + item.getContent();

                            setText(text);
                        }

                    }

                });

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
    private void refresh() {

        loadMessages();

    }

}