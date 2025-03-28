package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.domain.entity.ChatMessage;
import com.example.masterplanbbe.domain.repository.ChatBatchRepositoryPort;
import com.example.masterplanbbe.infrastructure.exception.chat.ChatBatchDataFormatException;
import com.example.masterplanbbe.infrastructure.exception.chat.ChatBatchSQLException;
import com.example.masterplanbbe.infrastructure.exception.chat.ChatBatchSaveException;
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
public class ChatBatchRepositoryAdapter implements ChatBatchRepositoryPort {
    private static final int batchSize = 50;

    private final JdbcTemplate jdbcTemplate;

    public void saveAll(List<ChatMessage> messages) {
        List<ChatMessage> temp = new ArrayList<>();
        for (int count = 0; count < messages.size(); count++) {
            temp.add(messages.get(count));
            if (temp.size() == batchSize || count == messages.size() - 1) {
                try {
                    batchInsert(temp);
                } catch (SQLException e) {
                    log.error("배치 저장 실패: {}", e.getMessage(), e);
                    throw new ChatBatchSQLException();
                } catch (Exception e) {
                    log.error("배치 처리 중 오류 발생: {}", e.getMessage(), e);
                    throw new ChatBatchSaveException();
                }
                temp.clear();
            }
        }
    }

    private void batchInsert(List<ChatMessage> messages) throws SQLException {
        String sql = "INSERT INTO chat_messages (content, member_id, send_at, spec_id, id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ChatMessage message = messages.get(i);
                if (message.getContent() == null || message.getMemberId() == null) {
                    throw new ChatBatchDataFormatException(); // 데이터 형식 오류
                }
                ps.setString(1, message.getContent());
                ps.setLong(2, message.getMemberId());
                ps.setTimestamp(3, Timestamp.valueOf(message.getSendAt()));
                ps.setLong(4, message.getSpecId());
                ps.setLong(5, message.getId());
            }

            @Override
            public int getBatchSize() {
                return messages.size();
            }
        });
    }
}
