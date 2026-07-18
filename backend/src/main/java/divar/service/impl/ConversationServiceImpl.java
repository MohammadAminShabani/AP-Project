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
import divar.service.ConversationService;
import jakarta.transaction.Transactional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final AdvertisementRepository advertisementRepository;
    private final MessageRepository messageRepository;

    public ConversationServiceImpl(
            ConversationRepository conversationRepository,
            AdvertisementRepository advertisementRepository,
            MessageRepository messageRepository) {

        this.conversationRepository = conversationRepository;
        this.advertisementRepository = advertisementRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    @Transactional
    public ConversationResponse create(Long advertisementId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User buyer)) {

            throw new AccessDeniedException("Unauthorized");
        }

        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new ResourceNotFoundException("Advertisement not found"));

        User seller = advertisement.getOwner();

        if (buyer.getId().equals(seller.getId())) {
            throw new BadRequestException(
                    "You cannot create conversation with yourself.");
        }

        Conversation oldConversation = conversationRepository
                        .findByBuyerAndAdvertisement(buyer, advertisement)
                        .orElse(null);

        if (oldConversation != null) {
            return toResponse(oldConversation, buyer);
        }

        Conversation conversation =
                new Conversation(buyer, seller, advertisement);

        conversationRepository.save(conversation);

        return toResponse(conversation, buyer);
    }

    @Override
    @Transactional
    public ConversationResponse findById(Long id) {

        Conversation conversation = conversationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User currentUser)) {

            throw new AccessDeniedException("Unauthorized");
        }

        boolean allowed = conversation.getBuyer().getId().equals(currentUser.getId())
                        || conversation.getSeller().getId().equals(currentUser.getId());

        if (!allowed) {throw new AccessDeniedException(
                    "You are not allowed to access this conversation.");
        }
        return toResponse(conversation, currentUser);
    }

    @Override
    @Transactional
    public List<ConversationResponse> getUserConversations() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof User currentUser)) {

            throw new AccessDeniedException("Unauthorized");
        }

        return conversationRepository
                .findByBuyerOrSeller(currentUser, currentUser)
                .stream()
                .map(conversation -> toResponse(conversation, currentUser))
                .toList();
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
                .findFirstByConversationOrderBySentAtDesc(conversation).orElse(null);

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