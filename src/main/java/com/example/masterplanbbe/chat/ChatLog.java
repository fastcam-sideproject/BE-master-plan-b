package com.example.masterplanbbe.chat;

import com.example.masterplanbbe.common.domain.FullAuditEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_logs")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatLog extends FullAuditEntity {

    @Column(name = "exam_id", nullable = false)
    private Long examId;  // 시험 id (FK)

    @Column(name = "member_id", nullable = false)
    private Long memberId;  // 회원 레코드 id (FK)

    @Column(name = "content", nullable = false, length = 255)
    private String content;  // 채팅 내용

    @Column(name = "send_time", nullable = false)
    private LocalDateTime sendTime;
}
