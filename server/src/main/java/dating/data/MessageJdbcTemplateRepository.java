package dating.data;

import dating.data.mappers.MessageMapper;
import dating.models.Message;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class MessageJdbcTemplateRepository implements MessageRepository {

    // Field
    private final JdbcTemplate jdbcTemplate;

    // Constructor
    public MessageJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method: Find a message by ID.
    @Override
    public Message getById(int messageId) {
        final String sql = "select "
                + "m.message_id, "
                + "m.conversation_id, "
                + "u.user_id, "
                + "u.username, "
                + "m.text_content, "
                + "m.time_sent "
                + "from messages m " +
                "inner join users u on m.sender_id = u.user_id " +
                "where m.message_id = ?;";

        Message message = jdbcTemplate.query(sql, new MessageMapper(), messageId)
                .stream()
                .findAny()
                .orElse(null); //returns null if no messages found

        return message;
    }

    // Method: Find a message by conversation ID.
    @Override
    public List<Message> getByConversationId(int conversationId) {
        return null;
    }

    // Method: Create a new message.
    @Override
    public Message create(Message message) {
        return null;
    }
}
