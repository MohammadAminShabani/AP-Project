package divar.dto.request;

public class RateSellerRequest {

    private Long sellerId;
    private int score;
    private String comment;

    public RateSellerRequest() {
    }

    public RateSellerRequest(Long sellerId,
                             int score,
                             String comment) {
        this.sellerId = sellerId;
        this.score = score;
        this.comment = comment;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
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