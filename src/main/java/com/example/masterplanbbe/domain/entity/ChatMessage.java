package com.example.masterplanbbe.domain.entity;

import com.example.masterplanbbe.application.dto.ChatRedisDto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatMessage {

    @Id
    private Long id;

    @Column(name = "spec_id", nullable = false)
    private Long specId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "send_at", nullable = false)
    private LocalDateTime sendAt;

    @Column(name = "parent_id")
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private ChatMessage parentMessage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @JsonCreator
    public static ChatMessage fromJson(
            @JsonProperty("id") Long id,
            @JsonProperty("specId") Long specId,
            @JsonProperty("memberId") Long memberId,
            @JsonProperty("content") String content,
            @JsonProperty("sendAt") LocalDateTime sendAt,
            @JsonProperty("parentId") Long parentId) {
        return ChatMessage.builder()
                .id(id)
                .specId(specId)
                .memberId(memberId)
                .content(content)
                .sendAt(sendAt)
                .parentId(parentId)
                .build();
    }

    public static ChatMessage from(ChatRedisDto chatRedisDto) {
        return ChatMessage.builder()
                .id(chatRedisDto.id())
                .specId(chatRedisDto.specId())
                .memberId(chatRedisDto.memberId())
                .content(chatRedisDto.content())
                .sendAt(chatRedisDto.sendAt())
                .parentId(chatRedisDto.parentId())
                .build();
    }

}
