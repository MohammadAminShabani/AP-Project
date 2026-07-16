package divar.repository;

import divar.entity.Conversation;
import divar.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findByConversationOrderBySentAtAsc(Conversation conversation);
    Optional<Message> findFirstByConversationOrderBySentAtDesc(
            Conversation conversation);
}