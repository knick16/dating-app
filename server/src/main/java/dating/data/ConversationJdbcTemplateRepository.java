package dating.data;

import dating.data.mappers.ConversationMapper;
import dating.models.Conversation;
import dating.models.Message;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ConversationJdbcTemplateRepository implements ConversationRepository {

    // Field
    private final JdbcTemplate jdbcTemplate;

    // Constructor
    public ConversationJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Method: Find conversations for a user to load in their conversation list.
    @Override
    @Transactional
    public List<Conversation> findByUsername(String username) {

        if (username == null) return null;

        final String sql = "select "
                + "c.conversation_id, "
                + "c.conversation_name, "
                + "conversation_type, "
                + "time_created "
                + "from conversations c "
                + "inner join users_conversations uc on c.conversation_id = uc.conversation_id "
                + "inner join users u on uc.user_id = u.user_id "
                + "where username = ?;";

        List<Conversation> conversations = jdbcTemplate.query(sql, new ConversationMapper(), username);

        if (!conversations.isEmpty()) {
            for (Conversation c : conversations) {
                attachConversationParticipants(c);
                //no need to attach the messages
            }
        }

        return conversations;
    }

    // Method: Find a specific conversation to load the attached content.
    @Override
    @Transactional
    public Conversation findById(int conversationId) {

        final String sql = "select "
                + "conversation_id, "
                + "conversation_name, "
                + "conversation_type, "
                + "time_created "
                + "from conversations "
                + "where conversation_id = ?;";

        Conversation conversation = jdbcTemplate.query(sql, new ConversationMapper(), conversationId)
                .stream()
                .findAny().orElse(null);

        if (conversation!= null) {
            attachConversationParticipants(conversation);
            attachConversationMessages(conversation);
        }

        return conversation;
    }

    // Method: Create a new conversation.
    @Override
    public Conversation create(List<Integer> participantIds, String conversationName) {
        return null;
    }

    // Method: Attach participants to a conversation.
    private void attachConversationParticipants(Conversation conversation) {

        final String sql = "select "
                + "u.username "
                + "from users_conversations uc "
                + "inner join users u on uc.user_id = u.user_id "
                + "where uc.conversation_id = ?;";

        List<String> participants = jdbcTemplate.query(sql,
                (rs, rowNum) -> {
                    String username = rs.getString("username");
                    return username;
                },
                conversation.getConversationId());

        conversation.setParticipants(participants);
    }

    // Method: Attach messages to a conversation (messages not hydrated).
    private void attachConversationMessages(Conversation conversation) {
        final String sql = "select "
                + "message_id, "
                + "time_sent "
                + "from messages "
                + "where conversation_id = ? "
                + "order by time_sent asc "
                + "limit 50;";

        List<Message> messages = jdbcTemplate.query(sql,
                (rs,rowNum) -> {
                    Message message = new Message();
                    message.setMessageId(rs.getInt("message_id"));
                    return message;
                },
                conversation.getConversationId());
        conversation.setMessages(messages);
    }
}
