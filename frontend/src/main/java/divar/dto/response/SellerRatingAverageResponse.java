package divar.dto.response;

public class SellerRatingAverageResponse {

    private Double averageRating;
    private Long ratingCount;

    public SellerRatingAverageResponse() {
    }

    public SellerRatingAverageResponse(Double averageRating,
                                       Long ratingCount) {

        this.averageRating = averageRating;
        this.ratingCount = ratingCount;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public Long getRatingCount() {
        return ratingCount;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public void setRatingCount(Long ratingCount) {
        this.ratingCount = ratingCount;
    }
}