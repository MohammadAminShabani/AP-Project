package divar.dto.request;

public class CreateMessageRequest {

    private Long conversationId;
    private String content;

    public CreateMessageRequest() {
    }

    public CreateMessageRequest(Long conversationId,
                                String content) {
        this.conversationId = conversationId;
        this.content = content;
    }

    public Long getConversationId() {
        return conversationId;
    }

    public void setConversationId(Long conversationId) {
        this.conversationId = conversationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}