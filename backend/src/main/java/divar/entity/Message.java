package divar.entity;

import java.time.LocalDateTime;

public class Message {

    private Long id;
    private Conversation conversation;
    private User sender;
    private String text;
    private LocalDateTime sentAt;

    public Message() {
        this.sentAt = LocalDateTime.now();
    }

    public Message(Conversation conversation, User sender, String text) {
        this.conversation = conversation;
        this.sender = sender;
        this.text = text;
        this.sentAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }

    @Override
    public String toString() {
        return sender.getUsername() + " : " + text;
    }
}