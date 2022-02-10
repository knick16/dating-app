package dating.domain;

import dating.data.AppUserRepository;
import dating.data.ConversationRepository;
import dating.data.MessageRepository;
import dating.models.Conversation;
import dating.models.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConversationService {

    // Fields
    private final ConversationRepository conversationRepository;
    private final AppUserRepository appUserRepository;
    private final MessageRepository messageRepository;

    // Constructor
    public ConversationService(ConversationRepository conversationRepository,
                               AppUserRepository appUserRepository,
                               MessageRepository messageRepository) {
        this.conversationRepository = conversationRepository;
        this.appUserRepository = appUserRepository;
        this.messageRepository = messageRepository;
    }

    // Method: Find conversations for a user to load in their conversation list.
    public List<Conversation> findByUsername(String username) {
        return conversationRepository.findByUsername(username);
    }

    // Method: Find a specific conversation to load the attached content.
    public Conversation findByConversationId(String requesterUsername, int conversationId) {

        Conversation conversation = conversationRepository.findById(conversationId);

        if (conversation != null
                && conversation.getParticipants() != null
                && conversation.getParticipants().contains(requesterUsername)) {
            for (Message message : conversation.getMessages()) {
                message = messageRepository.getById(message.getMessageId());
            }

            return conversation;
        }

        return null;
    }
}
