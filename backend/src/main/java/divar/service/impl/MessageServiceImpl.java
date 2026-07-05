package divar.service.impl;

import divar.dto.request.CreateMessageRequest;
import divar.dto.response.MessageResponse;
import divar.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Override
    public MessageResponse send(Long senderId, CreateMessageRequest request) {
        return null;
    }

    @Override
    public List<MessageResponse> getByConversation(Long conversationId) {
        return List.of();
    }

    @Override
    public void delete(Long id) {

    }
}