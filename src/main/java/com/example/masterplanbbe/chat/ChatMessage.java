package com.example.masterplanbbe.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatMessage {
    private Long examId;
    private Long memberId;
    private String memberName;
    private String content;
    private LocalDateTime sendTime;
}