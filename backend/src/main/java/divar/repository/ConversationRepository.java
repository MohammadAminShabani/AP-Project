package divar.repository;

import divar.entity.Advertisement;
import divar.entity.Conversation;
import divar.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

    List<Conversation> findByBuyer(User buyer);

    List<Conversation> findBySeller(User seller);

    Optional<Conversation> findByBuyerAndAdvertisement(
            User buyer,
            Advertisement advertisement);

    List<Conversation> findByBuyerOrSeller(
            User buyer,
            User seller);
    boolean existsByBuyerAndAdvertisement(User buyer, Advertisement advertisement);}