package divar.dto.response;

public class SellerRatingResponse {

    private Long id;
    private Long sellerId;
    private Long buyerId;
    private Long advertisementId;
    private int score;
    private String comment;

    public SellerRatingResponse() {
    }

    public SellerRatingResponse(String comment, int score, Long advertisementId,
                                Long buyerId, Long sellerId, Long id) {
        this.comment = comment;
        this.score = score;
        this.advertisementId = advertisementId;
        this.buyerId = buyerId;
        this.sellerId = sellerId;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(Long advertisementId) {
        this.advertisementId = advertisementId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
