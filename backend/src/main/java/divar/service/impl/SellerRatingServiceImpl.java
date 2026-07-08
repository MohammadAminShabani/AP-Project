package divar.service.impl;

import divar.dto.request.RateSellerRequest;
import divar.dto.response.SellerRatingAverageResponse;
import divar.dto.response.SellerRatingResponse;
import divar.entity.Advertisement;
import divar.entity.SellerRating;
import divar.entity.User;
import divar.exception.BadRequestException;
import divar.exception.ResourceNotFoundException;
import divar.repository.AdvertisementRepository;
import divar.repository.SellerRatingRepository;
import divar.repository.UserRepository;
import divar.service.SellerRatingService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerRatingServiceImpl implements SellerRatingService {

    private final SellerRatingRepository sellerRatingRepository;
    private final UserRepository userRepository;
    private final AdvertisementRepository advertisementRepository;

    public SellerRatingServiceImpl(SellerRatingRepository sellerRatingRepository,
            UserRepository userRepository,
            AdvertisementRepository advertisementRepository) {

        this.sellerRatingRepository = sellerRatingRepository;
        this.userRepository = userRepository;
        this.advertisementRepository = advertisementRepository;
    }

    @Override
    public SellerRatingResponse rateSeller(Long buyerId, Long advertisementId,
                                           RateSellerRequest request) {

        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() -> new ResourceNotFoundException("Buyer not found"));

        Advertisement advertisement = advertisementRepository.findById(advertisementId)
                .orElseThrow(() -> new ResourceNotFoundException("Advertisement not found"));

        User seller = advertisement.getOwner();

        if (buyer.getId().equals(seller.getId())) {
            throw new BadRequestException("You cannot rate yourself");}

        SellerRating rating = sellerRatingRepository
                .findByBuyerAndAdvertisement(buyer, advertisement).orElse(null);

        if (rating == null) {

            rating = new SellerRating(seller, buyer, advertisement,
                    request.getScore(), request.getComment());

        } else {
            rating.setScore(request.getScore());
            rating.setComment(request.getComment());
        }

        sellerRatingRepository.save(rating);

        Double average = sellerRatingRepository.getAverageRating(seller.getId());

        Long count = sellerRatingRepository.getRatingCount(seller.getId());

        seller.setAverageRating(average == null ? 0 : average);

        seller.setRatingCount(count == null ? 0 : count.intValue());

        userRepository.save(seller);

        SellerRatingResponse response = new SellerRatingResponse();

        response.setId(rating.getId());
        response.setSellerId(seller.getId());
        response.setBuyerId(buyer.getId());
        response.setAdvertisementId(advertisement.getId());
        response.setScore(rating.getScore());
        response.setComment(rating.getComment());

        return response;
    }

}