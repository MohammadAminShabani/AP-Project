package divar.service.impl;

import divar.dto.request.RateSellerRequest;
import divar.dto.response.SellerRatingResponse;
import divar.entity.Advertisement;
import divar.entity.SellerRating;
import divar.entity.User;
import divar.exception.BadRequestException;
import divar.exception.ResourceNotFoundException;
import divar.repository.AdvertisementRepository;
import divar.repository.ConversationRepository;
import divar.repository.SellerRatingRepository;
import divar.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SellerRatingServiceImplTest {

    @Mock
    private SellerRatingRepository sellerRatingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AdvertisementRepository advertisementRepository;

    @Mock
    private ConversationRepository conversationRepository;

    @InjectMocks
    private SellerRatingServiceImpl sellerRatingService;

    @Test
    void rateSeller_ShouldSaveRatingSuccessfully() {

        User buyer = new User();
        buyer.setId(1L);

        User seller = new User();
        seller.setId(2L);

        Advertisement advertisement = new Advertisement();
        advertisement.setId(10L);
        advertisement.setOwner(seller);

        RateSellerRequest request = new RateSellerRequest();
        request.setScore(5);
        request.setComment("Excellent Seller");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(buyer));

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.of(advertisement));

        when(userRepository.findById(2L))
                .thenReturn(Optional.of(seller));

        when(conversationRepository.existsByBuyerAndAdvertisement(buyer, advertisement))
                .thenReturn(true);

        when(sellerRatingRepository.findByBuyerAndAdvertisement(buyer, advertisement))
                .thenReturn(Optional.empty());

        when(sellerRatingRepository.getAverageRating(2L))
                .thenReturn(5.0);

        when(sellerRatingRepository.getRatingCount(2L))
                .thenReturn(1L);

        SellerRatingResponse response =
                sellerRatingService.rateSeller(1L,10L,request);

        assertNotNull(response);

        verify(sellerRatingRepository).save(any());
        verify(userRepository).save(seller);
    }

    @Test
    void rateSeller_ShouldThrow_WhenBuyerRatesHimself(){

        User buyer = new User();
        buyer.setId(1L);

        Advertisement advertisement = new Advertisement();
        advertisement.setOwner(buyer);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(buyer));

        when(advertisementRepository.findById(5L))
                .thenReturn(Optional.of(advertisement));

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(buyer));

        RateSellerRequest request = new RateSellerRequest();
        request.setScore(5);

        assertThrows(BadRequestException.class,
                ()-> sellerRatingService.rateSeller(1L,5L,request));

        verify(sellerRatingRepository,never()).save(any());
    }

    @Test
    void rateSeller_ShouldThrow_WhenConversationDoesNotExist(){

        User buyer = new User();
        buyer.setId(1L);

        User seller = new User();
        seller.setId(2L);

        Advertisement advertisement = new Advertisement();
        advertisement.setOwner(seller);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(buyer));

        when(advertisementRepository.findById(5L))
                .thenReturn(Optional.of(advertisement));

        when(userRepository.findById(2L))
                .thenReturn(Optional.of(seller));

        when(conversationRepository.existsByBuyerAndAdvertisement(buyer, advertisement))
                .thenReturn(false);

        RateSellerRequest request = new RateSellerRequest();
        request.setScore(4);

        assertThrows(BadRequestException.class,
                ()-> sellerRatingService.rateSeller(1L,5L,request));

        verify(sellerRatingRepository,never()).save(any());
    }
    @Test
    void deleteRating_ShouldDeleteSuccessfully() {

        User buyer = new User();
        buyer.setId(1L);

        User seller = new User();
        seller.setId(2L);

        Advertisement advertisement = new Advertisement();
        advertisement.setId(10L);

        SellerRating rating =
                new SellerRating(seller,buyer,advertisement,5,"Good");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(buyer));

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.of(advertisement));

        when(sellerRatingRepository.findByBuyerAndAdvertisement(
                buyer,advertisement))
                .thenReturn(Optional.of(rating));

        when(sellerRatingRepository.getAverageRating(2L))
                .thenReturn(0.0);

        when(sellerRatingRepository.getRatingCount(2L))
                .thenReturn(0L);

        sellerRatingService.deleteRating(1L,10L);

        verify(sellerRatingRepository).delete(rating);
        verify(userRepository).save(seller);
    }

    @Test
    void getSellerAverageRating_ShouldReturnAverage() {

        User seller = new User();
        seller.setId(2L);

        when(userRepository.findById(2L))
                .thenReturn(Optional.of(seller));

        when(sellerRatingRepository.getAverageRating(2L))
                .thenReturn(4.5);

        when(sellerRatingRepository.getRatingCount(2L))
                .thenReturn(8L);

        var response =
                sellerRatingService.getSellerAverageRating(2L);

        assertEquals(4.5,response.getAverageRating());
        assertEquals(8,response.getRatingCount());
    }

    @Test
    void getSellerRatings_ShouldReturnList() {

        User seller = new User();
        seller.setId(2L);

        User buyer = new User();
        buyer.setId(1L);

        Advertisement advertisement = new Advertisement();
        advertisement.setId(11L);

        SellerRating rating =
                new SellerRating(seller,buyer,advertisement,5,"Perfect");

        when(userRepository.findById(2L))
                .thenReturn(Optional.of(seller));

        when(sellerRatingRepository.findBySeller(seller))
                .thenReturn(List.of(rating));

        List<SellerRatingResponse> list =
                sellerRatingService.getSellerRatings(2L);

        assertEquals(1,list.size());
        assertEquals(5,list.get(0).getScore());
    }

    @Test
    void rateSeller_ShouldThrow_WhenBuyerNotFound() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        RateSellerRequest request = new RateSellerRequest();
        request.setScore(5);

        assertThrows(ResourceNotFoundException.class,
                ()->sellerRatingService.rateSeller(1L,10L,request));
    }

    @Test
    void rateSeller_ShouldThrow_WhenAdvertisementNotFound() {

        User buyer = new User();
        buyer.setId(1L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(buyer));

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.empty());

        RateSellerRequest request = new RateSellerRequest();
        request.setScore(5);

        assertThrows(ResourceNotFoundException.class,
                ()->sellerRatingService.rateSeller(1L,10L,request));
    }

    @Test
    void deleteRating_ShouldThrow_WhenRatingNotFound() {

        User buyer = new User();
        buyer.setId(1L);

        Advertisement advertisement = new Advertisement();
        advertisement.setId(10L);

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(buyer));

        when(advertisementRepository.findById(10L))
                .thenReturn(Optional.of(advertisement));

        when(sellerRatingRepository.findByBuyerAndAdvertisement(
                buyer,advertisement))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                ()->sellerRatingService.deleteRating(1L,10L));
    }
}