package com.example.masterplanbbe.application.dto;

import com.example.masterplanbbe.presentation.request.ChatRequest;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ChatRedisDto(
        Long id,
        Long specId,
        Long parentId,
        Long memberId,
        String nickname,
        String content,
        LocalDateTime sendAt,
        String parentContent,
        String parentNickname
) implements Serializable {

    @JsonCreator
    public ChatRedisDto(
            @JsonProperty("id") Long id,
            @JsonProperty("specId") Long specId,
            @JsonProperty("parentId") Long parentId,
            @JsonProperty("memberId") Long memberId,
            @JsonProperty("nickname") String nickname,
            @JsonProperty("content") String content,
            @JsonProperty("sendAt") LocalDateTime sendAt,
            @JsonProperty("parentContent") String parentContent,
            @JsonProperty("parentNickname") String parentNickname) {
        this.id = id;
        this.specId = specId;
        this.parentId = parentId;
        this.memberId = memberId;
        this.nickname = nickname;
        this.content = content;
        this.sendAt = sendAt;
        this.parentContent = parentContent;
        this.parentNickname = parentNickname;
    }

    public static ChatRedisDto from(Long snowflakeId, ChatRequest request) {
        return new ChatRedisDto(
                snowflakeId,
                request.specId(),
                request.parentId(),
                request.memberId(),
                request.nickname(),
                request.content(),
                request.sendAt(),
                request.parentContent(),
                request.parentNickname()
        );
    }
}