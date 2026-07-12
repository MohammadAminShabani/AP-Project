package divar.dto.response;

public class SellerRatingAverageResponse {

    private double averageRating;

    private long ratingCount;

    public SellerRatingAverageResponse() {
    }

    public SellerRatingAverageResponse(double averageRating,
                                       long ratingCount) {
        this.averageRating = averageRating;
        this.ratingCount = ratingCount;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public long getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(long ratingCount) {
        this.ratingCount = ratingCount;
    }
}