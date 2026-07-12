package divar.service.impl;

import divar.dto.request.CreateMessageRequest;
import divar.dto.response.MessageResponse;
import divar.entity.Conversation;
import divar.entity.Message;
import divar.entity.User;
import divar.exception.BadRequestException;
import divar.exception.ResourceNotFoundException;
import divar.repository.ConversationRepository;
import divar.repository.MessageRepository;
import divar.repository.UserRepository;
import divar.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;

    public MessageServiceImpl(MessageRepository messageRepository,
                              ConversationRepository conversationRepository,
                              UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.conversationRepository = conversationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public MessageResponse send(Long senderId, CreateMessageRequest request) {

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Conversation conversation = conversationRepository.findById(request.getConversationId())
                .orElseThrow(() -> new ResourceNotFoundException("Conversation not found"));

        boolean allowed = conversation.getBuyer().getId().equals(senderId)
                        || conversation.getSeller().getId().equals(senderId);

        if (!allowed) throw new BadRequestException("You are not a participant of this conversation.");

        if (request.getContent() == null || request.getContent().trim().isEmpty())
            throw new BadRequestException("Message cannot be empty.");

        Message message = new Message(conversation, sender, request.getContent());
        messageRepository.save(message);

        return toResponse(message);
    }

    @Override
    public List<MessageResponse> getByConversation(Long conversationId) {

        Conversation conversation = conversationRepository.findById(conversationId)
                        .orElseThrow(() -> new ResourceNotFoundException("Conversation not found"));

        List<Message> messages = messageRepository.findByConversationOrderBySentAtAsc(conversation);
        List<MessageResponse> responses = new ArrayList<>();

        for (Message message : messages) responses.add(toResponse(message));

        return responses;
    }

    @Override
    public void delete(Long id) {

        Message message = messageRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Message not found"));

        messageRepository.delete(message);
    }

    private MessageResponse toResponse(Message message) {

        return new MessageResponse(message.getId(),
                message.getSender().getUsername(),
                message.getText(), message.getSentAt());
    }
}