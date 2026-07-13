package divar.controller;

import divar.dto.request.RateSellerRequest;
import divar.dto.response.SellerRatingAverageResponse;
import divar.dto.response.SellerRatingResponse;
import divar.service.SellerRatingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class SellerRatingController {

    private final SellerRatingService sellerRatingService;

    public SellerRatingController(SellerRatingService sellerRatingService) {
        this.sellerRatingService = sellerRatingService;
    }

    @PostMapping
    public SellerRatingResponse rateSeller(
            @RequestParam("buyerId") Long buyerId,
            @RequestParam("advertisementId") Long advertisementId,
            @RequestBody RateSellerRequest request) {

        return sellerRatingService.rateSeller(
                buyerId,
                advertisementId,
                request);
    }

    @GetMapping("/{sellerId}")
    public List<SellerRatingResponse> getSellerRatings(
            @PathVariable("sellerId") Long sellerId) {

        return sellerRatingService.getSellerRatings(sellerId);
    }

    @GetMapping("/{sellerId}/average")
    public SellerRatingAverageResponse getAverageRating(
            @PathVariable("sellerId") Long sellerId) {

        return sellerRatingService.getSellerAverageRating(sellerId);
    }

    @DeleteMapping
    public void deleteRating(
            @RequestParam("buyerId") Long buyerId,
            @RequestParam("advertisementId") Long advertisementId) {

        sellerRatingService.deleteRating(
                buyerId,
                advertisementId);
    }
}