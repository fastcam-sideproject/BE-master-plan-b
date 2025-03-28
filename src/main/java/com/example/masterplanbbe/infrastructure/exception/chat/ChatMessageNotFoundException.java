package com.example.masterplanbbe.infrastructure.exception.chat;

import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class ChatMessageNotFoundException extends BaseException {
    public ChatMessageNotFoundException() {
        super(ErrorCode.CHAT_NOT_FOUND);
    }
}