package divar.controller;

import divar.dto.response.ConversationResponse;
import divar.service.ConversationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping
    public ConversationResponse create(
            @RequestParam("buyerId") Long buyerId,
            @RequestParam("sellerId") Long sellerId,
            @RequestParam("advertisementId") Long advertisementId) {

        return conversationService.create(buyerId, sellerId, advertisementId);
    }

    @GetMapping("/{id}")
    public ConversationResponse findById(
            @PathVariable("id") Long id) {

        return conversationService.findById(id);
    }

    @GetMapping("/user/{userId}")
    public List<ConversationResponse> getUserConversations(
            @PathVariable("userId") Long userId) {

        return conversationService.getUserConversations(userId);
    }
}