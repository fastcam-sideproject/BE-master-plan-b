package com.example.masterplanbbe.domain.chat.repository;

import com.example.masterplanbbe.domain.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class BatchChatRepository {
    private static final int batchSize = 50;

    private final JdbcTemplate jdbcTemplate;

    public void saveAll(List<ChatMessage> messages) {
        List<ChatMessage> temp = new ArrayList<>();
        for (int count = 0; count < messages.size(); count++) {
            temp.add(messages.get(count));
            if (temp.size() == batchSize || count == messages.size() - 1) {
                batchInsert(temp);
                temp.clear();
            }
        }
    }

    private void batchInsert(List<ChatMessage> messages) {
        String sql = "INSERT INTO chat_messages (content, member_id, send_at, spec_id, id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, messages.get(i).getContent());
                        ps.setLong(2, messages.get(i).getMemberId());
                        ps.setTimestamp(3, Timestamp.valueOf(messages.get(i).getSendAt()));
                        ps.setLong(4, messages.get(i).getSpecId());
                        ps.setLong(5, messages.get(i).getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return messages.size();
                    }
                }
        );
    }
}
