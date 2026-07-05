package divar.service;

import divar.dto.response.ConversationResponse;

import java.util.List;

public interface ConversationService {

    ConversationResponse create(Long buyerId,
                                Long sellerId,
                                Long advertisementId);

    ConversationResponse findById(Long id);

    List<ConversationResponse> getUserConversations(Long userId);

}