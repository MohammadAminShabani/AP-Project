package divar.controller;

import divar.config.SceneManager;
import divar.dto.response.ConversationResponse;
import divar.service.ConversationService;
import divar.session.ConversationSession;
import divar.util.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import java.util.List;

public class ConversationControllerFront {

    @FXML
    private ListView<ConversationResponse> conversationList;

    private final ConversationService conversationService =
            new ConversationService();

    @FXML
    public void initialize() {

        conversationList.setCellFactory(param ->
                new ListCell<>() {

                    @Override
                    protected void updateItem(ConversationResponse item,
                                              boolean empty) {

                        super.updateItem(item, empty);

                        if (empty || item == null) {
                            setText(null);
                        } else {

                            String text =
                                    item.getAdvertisementTitle()
                                            + "\n"
                                            + item.getOtherUser();

                            if (item.getLastMessage() != null) {
                                text += "\n"
                                        + item.getLastMessage();
                            }

                            setText(text);
                        }
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

        } catch (Exception e) {

            Alert alert =
                    new Alert(Alert.AlertType.ERROR);

            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }

    @FXML
    private void openChat(ActionEvent event) {

        ConversationResponse conversation =
                conversationList.getSelectionModel()
                        .getSelectedItem();

        if (conversation == null) {

            Alert alert =
                    new Alert(Alert.AlertType.WARNING);

            alert.setContentText("Select a conversation.");

            alert.show();

            return;
        }
            ConversationSession.setConversation(conversation);

            SceneManager.loadScene(
                    Constants.CHAT,
                    "گفتگو"
            );

    }

    @FXML
    private void refresh(ActionEvent event) {

        loadConversations();

    }

}