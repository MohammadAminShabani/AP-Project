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
    public ConversationResponse create(@RequestParam Long buyerId, @RequestParam Long sellerId,
                                       @RequestParam Long advertisementId) {
        return conversationService.create(buyerId, sellerId, advertisementId);
    }

    @GetMapping("/{id}")
    public ConversationResponse findById(@PathVariable Long id) {
        return conversationService.findById(id);
    }

    @GetMapping("/user/{userId}")
    public List<ConversationResponse> getUserConversations(@PathVariable Long userId) {
        return conversationService.getUserConversations(userId);
    }
}