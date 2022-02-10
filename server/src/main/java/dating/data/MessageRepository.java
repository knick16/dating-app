package dating.data;

import dating.models.Message;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository {

    Message getById(int messageId);

    List<Message> getByConversationId(int conversationId);

    Message create(Message message);
}
