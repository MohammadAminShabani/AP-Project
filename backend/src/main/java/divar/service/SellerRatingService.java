package divar.service;

import divar.dto.request.RateSellerRequest;
import divar.dto.response.SellerRatingAverageResponse;
import divar.dto.response.SellerRatingResponse;

import java.util.List;

public interface SellerRatingService {

    SellerRatingResponse rateSeller(Long buyerId,
                                    Long advertisementId,
                                    RateSellerRequest request);

    List<SellerRatingResponse> getSellerRatings(Long sellerId);

    SellerRatingAverageResponse getSellerAverageRating(Long sellerId);

    void deleteRating(Long buyerId,
                      Long advertisementId);
}