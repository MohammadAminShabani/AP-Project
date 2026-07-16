package divar.service.impl;

import divar.dto.response.ConversationResponse;
import divar.entity.Advertisement;
import divar.entity.Conversation;
import divar.entity.Message;
import divar.entity.User;
import divar.exception.BadRequestException;
import divar.exception.ResourceNotFoundException;
import divar.repository.AdvertisementRepository;
import divar.repository.ConversationRepository;
import divar.repository.MessageRepository;
import divar.repository.UserRepository;
import divar.service.ConversationService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;
    private final MessageRepository messageRepository;

    public ConversationServiceImpl(
            ConversationRepository conversationRepository,
            UserRepository userRepository,
            AdvertisementRepository advertisementRepository,
            MessageRepository messageRepository) {

        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
        this.advertisementRepository = advertisementRepository;
        this.messageRepository = messageRepository;
    }
    @Transactional
    @Override
    public ConversationResponse create(Long buyerId, Long sellerId, Long advertisementId) {

        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer not found"));

        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new ResourceNotFoundException("Seller not found"));

        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new ResourceNotFoundException("Advertisement not found"));

        if (buyer.getId().equals(seller.getId())) {
            throw new BadRequestException("You cannot create conversation with yourself.");
        }

        Conversation oldConversation =
                conversationRepository
                        .findByBuyerAndAdvertisement(buyer, advertisement).orElse(null);

        if (oldConversation != null) {
            return toResponse(oldConversation, buyer);
        }

        Conversation conversation = new Conversation(buyer, seller, advertisement);
        conversationRepository.save(conversation);

        return toResponse(conversation, buyer);
    }
    @Transactional
    @Override
    public ConversationResponse findById(Long id) {

        Conversation conversation = conversationRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Conversation not found"));

        return toResponse(conversation, conversation.getBuyer());
    }
    @Transactional
    @Override
    public List<ConversationResponse> getUserConversations(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        List<Conversation> conversations = conversationRepository.findByBuyerOrSeller(user, user);

        List<ConversationResponse> responses = new ArrayList<>();

        for (Conversation conversation : conversations) {
            responses.add(toResponse(conversation, user));
        }
        return responses;
    }
    private ConversationResponse toResponse(
            Conversation conversation,
            User currentUser) {

        String otherUser;

        if (conversation.getBuyer().getId().equals(currentUser.getId())) {
            otherUser = conversation.getSeller().getUsername();
        } else {
            otherUser = conversation.getBuyer().getUsername();
        }

        String lastMessage = "";
        LocalDateTime lastMessageTime = null;

        Message message = messageRepository
                .findFirstByConversationOrderBySentAtDesc(conversation)
                .orElse(null);

        if (message != null) {
            lastMessage = message.getText();
            lastMessageTime = message.getSentAt();
        }

        return new ConversationResponse(
                conversation.getId(),
                conversation.getAdvertisement().getTitle(),
                otherUser,
                lastMessage,
                lastMessageTime
        );
    }
}