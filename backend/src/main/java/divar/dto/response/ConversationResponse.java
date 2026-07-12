package divar.dto.response;

import java.time.LocalDateTime;

public class ConversationResponse {

    private Long id;

    private String advertisementTitle;

    private String otherUser;

    private String lastMessage;

    private LocalDateTime lastMessageTime;

    public ConversationResponse() {
    }

    public ConversationResponse(Long id, String advertisementTitle, String otherUser,
                                String lastMessage, LocalDateTime lastMessageTime) {
        this.id = id;
        this.advertisementTitle = advertisementTitle;
        this.otherUser = otherUser;
        this.lastMessage = lastMessage;
        this.lastMessageTime = lastMessageTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdvertisementTitle() {
        return advertisementTitle;
    }

    public void setAdvertisementTitle(String advertisementTitle) {
        this.advertisementTitle = advertisementTitle;
    }

    public String getOtherUser() {
        return otherUser;
    }

    public void setOtherUser(String otherUser) {
        this.otherUser = otherUser;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public LocalDateTime getLastMessageTime() {
        return lastMessageTime;
    }

    public void setLastMessageTime(LocalDateTime lastMessageTime) {
        this.lastMessageTime = lastMessageTime;
    }
}
