package divar.service.impl;

import divar.dto.request.CreateMessageRequest;
import divar.dto.response.MessageResponse;
import divar.entity.Conversation;
import divar.entity.Message;
import divar.entity.User;
import divar.enums.UserRole;
import divar.exception.BadRequestException;
import divar.exception.ResourceNotFoundException;
import divar.repository.ConversationRepository;
import divar.repository.MessageRepository;
import divar.service.MessageService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;

    public MessageServiceImpl(
            MessageRepository messageRepository,
            ConversationRepository conversationRepository) {

        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
    }

    @Override
    public MessageResponse send(CreateMessageRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof User sender)) {

            throw new AccessDeniedException("Unauthorized");
        }

        Conversation conversation = conversationRepository.findById(request.getConversationId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Conversation not found"));

        boolean allowed =
                conversation.getBuyer().getId().equals(sender.getId())
                        || conversation.getSeller().getId().equals(sender.getId());

        if (!allowed) {
            throw new AccessDeniedException(
                    "You are not a participant of this conversation.");
        }

        if (request.getContent() == null ||
                request.getContent().trim().isEmpty()) {

            throw new BadRequestException("Message cannot be empty.");
        }

        Message message =
                new Message(conversation, sender, request.getContent());

        messageRepository.save(message);

        return toResponse(message);
    }

    @Override
    public List<MessageResponse> getByConversation(Long conversationId) {

        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Conversation not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof User currentUser)) {

            throw new AccessDeniedException("Unauthorized");
        }

        boolean allowed =
                conversation.getBuyer().getId().equals(currentUser.getId())
                        || conversation.getSeller().getId().equals(currentUser.getId());

        if (!allowed) {
            throw new AccessDeniedException(
                    "You are not allowed to view this conversation.");
        }

        return messageRepository
                .findByConversationOrderBySentAtAsc(conversation)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public void delete(Long id) {

        Message message = messageRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Message not found"));

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !(authentication.getPrincipal() instanceof User currentUser)) {

            throw new AccessDeniedException("Unauthorized");
        }

        if (!message.getSender().getId().equals(currentUser.getId())
                && currentUser.getRole() != UserRole.ADMIN) {

            throw new AccessDeniedException(
                    "You are not allowed to delete this message.");
        }

        messageRepository.delete(message);
    }

    private MessageResponse toResponse(Message message) {

        return new MessageResponse(
                message.getId(),
                message.getSender().getUsername(),
                message.getText(),
                message.getSentAt()
        );
    }
}