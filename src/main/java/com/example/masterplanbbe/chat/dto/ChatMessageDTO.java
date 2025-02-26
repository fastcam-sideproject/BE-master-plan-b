package com.example.masterplanbbe.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {
    private Long id;
    private Long examId;
    private Long memberId;
    private String memberName;
    private String content;
    private LocalDateTime sendAt;
}