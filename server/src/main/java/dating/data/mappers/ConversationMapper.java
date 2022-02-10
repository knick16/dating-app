package dating.data.mappers;

import dating.models.Conversation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ConversationMapper implements RowMapper<Conversation> {

    @Override
    public Conversation mapRow(ResultSet resultSet, int i) throws SQLException {
        Conversation conversation = new Conversation();
        conversation.setConversationId(resultSet.getInt("conversation_id"));
        conversation.setName(resultSet.getString("conversation_name"));
        conversation.setType(resultSet.getString("conversation_type"));
        conversation.setTimestamp(resultSet.getTimestamp("time_created").toLocalDateTime());
        return conversation;
    }
}

