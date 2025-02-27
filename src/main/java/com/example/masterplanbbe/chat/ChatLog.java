package com.example.masterplanbbe.chat;

import com.example.masterplanbbe.chat.dto.ChatMessageDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_logs")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatLog {

    @Id
    private Long id;

    @Column(name = "spec_id", nullable = false)
    private Long specId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "content", nullable = false, length = 255)
    private String content;

    @Column(name = "send_at", nullable = false)
    private LocalDateTime sendAt;

    @JsonCreator
    public static ChatLog fromJson(@JsonProperty("id") Long id,
                                   @JsonProperty("specId") Long specId,
                                   @JsonProperty("memberId") Long memberId,
                                   @JsonProperty("content") String content,
                                   @JsonProperty("sendAt") LocalDateTime sendAt) {
        return ChatLog.builder()
                .id(id)
                .specId(specId)
                .memberId(memberId)
                .content(content)
                .sendAt(sendAt)
                .build();
    }

    public static ChatLog from(ChatMessageDTO dto) {
        return new ChatLog(dto.getId(),
                dto.getSpecId(),
                dto.getMemberId(),
                dto.getContent(),
                dto.getSendAt());
    }
}
