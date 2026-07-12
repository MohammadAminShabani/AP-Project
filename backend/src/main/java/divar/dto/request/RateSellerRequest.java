package divar.dto.request;

public class RateSellerRequest {

    private int score;

    private String comment;

    public RateSellerRequest() {
    }

    public RateSellerRequest(int score, String comment) {
        this.score = score;
        this.comment = comment;
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