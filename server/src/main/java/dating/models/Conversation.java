package dating.models;

import java.time.LocalDateTime;
import java.util.List;

public class Conversation {

    // fields
    private int conversationId;
    private String name;
    private List<Message> messages;
    private List<String> participants;
    private String type;
    private LocalDateTime timestamp;


    // constructors
    public Conversation(int conversationId, String name, List<Message> messages, List<String> participants, String type, LocalDateTime timestamp) {
        this.conversationId = conversationId;
        this.name = name;
        this.messages = messages;
        this.participants = participants;
        this.type = type;
        this.timestamp = timestamp;
    }

    public Conversation() {
        // empty constructor
    }


    // getters and setters
    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
