package dating.data.mappers;

import dating.models.MinimalUser;
import dating.models.Message;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MessageMapper implements RowMapper<Message> {
    @Override
    public Message mapRow(ResultSet resultSet, int i) throws SQLException {
        Message message = new Message();

        MinimalUser sender = new MinimalUser();
        sender.setUserId(resultSet.getInt("user_id"));
        sender.setUsername(resultSet.getString("username"));

        message.setMessageId(resultSet.getInt("message_id"));
        message.setConversationId(resultSet.getInt("conversation_id"));
        message.setSender(sender);
        message.setText(resultSet.getString("text_content"));
        message.setTimestamp(resultSet.getTimestamp("time_sent").toLocalDateTime());

        return message;
    }
}
