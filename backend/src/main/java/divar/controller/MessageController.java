package divar.controller;

import divar.dto.request.CreateMessageRequest;
import divar.dto.response.MessageResponse;
import divar.service.MessageService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/{senderId}")
    public MessageResponse send(
            @PathVariable("senderId") Long senderId,
            @Valid @RequestBody CreateMessageRequest request) {

        return messageService.send(senderId, request);
    }

    @GetMapping("/conversation/{conversationId}")
    public List<MessageResponse> getMessages(
            @PathVariable("conversationId") Long conversationId) {

        return messageService.getByConversation(conversationId);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable("id") Long id) {

        messageService.delete(id);
    }
}