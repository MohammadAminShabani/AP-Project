package divar.dto.request;

public class RateSellerRequest {

    private Integer score;
    private String comment;

    public RateSellerRequest() {
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}