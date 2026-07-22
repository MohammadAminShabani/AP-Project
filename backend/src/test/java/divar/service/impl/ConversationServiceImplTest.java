package divar.service.impl;

import divar.dto.response.ConversationResponse;
import divar.entity.*;
import divar.exception.BadRequestException;
import divar.exception.ResourceNotFoundException;
import divar.repository.AdvertisementRepository;
import divar.repository.ConversationRepository;
import divar.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConversationServiceImplTest {

    @Mock
    private ConversationRepository conversationRepository;

    @Mock
    private AdvertisementRepository advertisementRepository;

    @Mock
    private MessageRepository messageRepository;

    @InjectMocks
    private ConversationServiceImpl conversationService;

    private User buyer;
    private User seller;
    private Advertisement advertisement;

    @BeforeEach
    void setup(){

        buyer = new User();
        buyer.setId(1L);
        buyer.setUsername("buyer");

        seller = new User();
        seller.setId(2L);
        seller.setUsername("seller");

        advertisement = new Advertisement();
        advertisement.setId(10L);
        advertisement.setTitle("Laptop");
        advertisement.setOwner(seller);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        buyer,null
                )
        );
    }

    @Test
    void create_ShouldCreateConversation(){

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.of(advertisement));

        when(conversationRepository.findByBuyerAndAdvertisement(
                buyer,advertisement))
                .thenReturn(Optional.empty());

        ConversationResponse response =
                conversationService.create(10L);

        assertNotNull(response);

        verify(conversationRepository).save(any(Conversation.class));
    }

    @Test
    void create_ShouldReturnExistingConversation(){

        Conversation conversation =
                new Conversation(buyer,seller,advertisement);

        conversation.setId(5L);

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.of(advertisement));

        when(conversationRepository.findByBuyerAndAdvertisement(
                buyer,advertisement))
                .thenReturn(Optional.of(conversation));

        ConversationResponse response =
                conversationService.create(10L);

        assertEquals(5L,response.getId());

        verify(conversationRepository,never()).save(any());
    }

    @Test
    void create_ShouldThrow_WhenAdvertisementNotFound(){

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                ()->conversationService.create(10L));
    }

    @Test
    void create_ShouldThrow_WhenBuyerIsSeller(){

        advertisement.setOwner(buyer);

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.of(advertisement));

        assertThrows(BadRequestException.class,
                ()->conversationService.create(10L));
    }

    @Test
    void findById_ShouldReturnConversation(){

        Conversation conversation =
                new Conversation(buyer,seller,advertisement);

        conversation.setId(7L);

        when(conversationRepository.findById(7L))
                .thenReturn(Optional.of(conversation));

        when(messageRepository.findFirstByConversationOrderBySentAtDesc(
                conversation))
                .thenReturn(Optional.empty());

        ConversationResponse response =
                conversationService.findById(7L);

        assertEquals(7L,response.getId());
    }
    @Test
    void findById_ShouldThrow_WhenConversationNotFound() {

        when(conversationRepository.findById(100L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> conversationService.findById(100L));
    }

    @Test
    void findById_ShouldThrow_WhenUserIsNotParticipant() {

        User stranger = new User();
        stranger.setId(99L);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(stranger, null));

        Conversation conversation =
                new Conversation(buyer, seller, advertisement);

        conversation.setId(5L);

        when(conversationRepository.findById(5L))
                .thenReturn(Optional.of(conversation));

        assertThrows(org.springframework.security.access.AccessDeniedException.class,
                () -> conversationService.findById(5L));
    }

    @Test
    void getUserConversations_ShouldReturnList() {

        Conversation conversation =
                new Conversation(buyer, seller, advertisement);

        conversation.setId(3L);

        when(conversationRepository.findByBuyerOrSeller(buyer, buyer))
                .thenReturn(java.util.List.of(conversation));

        when(messageRepository.findFirstByConversationOrderBySentAtDesc(conversation))
                .thenReturn(Optional.empty());

        var list = conversationService.getUserConversations();

        assertEquals(1, list.size());
        assertEquals(3L, list.get(0).getId());
    }

    @Test
    void getUserConversations_ShouldContainLastMessage() {

        Conversation conversation =
                new Conversation(buyer, seller, advertisement);

        Message message = new Message(conversation, seller, "Hello");

        when(conversationRepository.findByBuyerOrSeller(buyer, buyer))
                .thenReturn(java.util.List.of(conversation));

        when(messageRepository.findFirstByConversationOrderBySentAtDesc(conversation))
                .thenReturn(Optional.of(message));

        var list = conversationService.getUserConversations();

        assertEquals("Hello", list.get(0).getLastMessage());
    }

    @Test
    void create_ShouldThrow_WhenUnauthorized() {

        SecurityContextHolder.clearContext();

        assertThrows(org.springframework.security.access.AccessDeniedException.class,
                () -> conversationService.create(10L));
    }
}