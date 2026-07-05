package divar.service;

import divar.entity.Conversation;
import divar.entity.Message;

import java.util.List;
import java.util.Optional;

public interface MessageService {

    Message sendMessage(Message message);

    Optional<Message> findById(Long id);

    List<Message> findByConversation(Conversation conversation);

    void delete(Long id);

}