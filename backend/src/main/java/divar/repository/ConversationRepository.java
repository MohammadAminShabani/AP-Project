package divar.repository;

import divar.entity.Conversation;
import divar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    List<Conversation> findByBuyer(User buyer);

    List<Conversation> findBySeller(User seller);

}