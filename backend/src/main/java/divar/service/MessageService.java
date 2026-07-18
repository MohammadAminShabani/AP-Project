package divar.service;

import divar.dto.request.CreateMessageRequest;
import divar.dto.response.MessageResponse;

import java.util.List;

public interface MessageService {

    MessageResponse send(CreateMessageRequest request);

    List<MessageResponse> getByConversation(Long conversationId);

    void delete(Long id);
}