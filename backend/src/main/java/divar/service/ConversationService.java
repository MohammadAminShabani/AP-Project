package divar.service;

import divar.entity.Conversation;
import divar.entity.User;

import java.util.List;
import java.util.Optional;

public interface ConversationService {

    Conversation createConversation(Conversation conversation);

    Optional<Conversation> findById(Long id);

    List<Conversation> findByBuyer(User buyer);

    List<Conversation> findBySeller(User seller);

}