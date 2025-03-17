package com.example.masterplanbbe.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

public record ChatRedisDto(
        @JsonProperty("id") Long id,
        @JsonProperty("specId") Long specId,
        @JsonProperty("parentId") Long parentId,
        @JsonProperty("memberId") Long memberId,
        @JsonProperty("nickname") String nickname,
        @JsonProperty("content") String content,
        @JsonProperty("sendAt") LocalDateTime sendAt,
        @JsonProperty("parentContent") String parentContent,
        @JsonProperty("parentNickname") String parentNickname
) implements Serializable {}
