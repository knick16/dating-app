package dating.models;

import java.time.LocalDateTime;

public class Message {

    // fields
    private int messageId;
    private int conversationId;
    private MinimalUser sender;
    private String text;
    private String image;
    private LocalDateTime timestamp;


    // constructors
    public Message(int messageId, int conversationId, MinimalUser sender, String text, String image, LocalDateTime timestamp) {
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.sender = sender;
        this.text = text;
        this.image = image;
        this.timestamp = timestamp;
    }

    public Message() {
        // empty constructor
    }


    // getters and setters
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public MinimalUser getSender() {
        return sender;
    }

    public void setSender(MinimalUser sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}

