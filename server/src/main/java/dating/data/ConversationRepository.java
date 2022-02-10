package dating.data;

import dating.models.Conversation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ConversationRepository {

    @Transactional
    List<Conversation> findByUsername(String username);

    @Transactional
    Conversation findById(int conversationId);

    Conversation create(List<Integer> participantIds, String conversationName);
}
