package divar.service.impl;

import divar.dto.response.ConversationResponse;
import divar.service.ConversationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationServiceImpl implements ConversationService {

    @Override
    public ConversationResponse create(Long buyerId, Long sellerId, Long advertisementId) {
        return null;
    }

    @Override
    public ConversationResponse findById(Long id) {
        return null;
    }

    @Override
    public List<ConversationResponse> getUserConversations(Long userId) {
        return List.of();
    }
}