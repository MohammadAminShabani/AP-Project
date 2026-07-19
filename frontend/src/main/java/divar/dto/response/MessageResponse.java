package divar.dto.response;

import java.time.LocalDateTime;

public class MessageResponse {

    private Long id;
    private String sender;
    private String content;
    private LocalDateTime sentAt;

    public MessageResponse() {
    }

    public MessageResponse(Long id,
                           String sender,
                           String content,
                           LocalDateTime sentAt) {

        this.id = id;
        this.sender = sender;
        this.content = content;
        this.sentAt = sentAt;
    }

    public Long getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
}