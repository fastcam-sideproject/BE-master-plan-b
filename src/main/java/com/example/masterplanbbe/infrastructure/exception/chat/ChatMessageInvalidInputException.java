package com.example.masterplanbbe.infrastructure.exception.chat;

import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class ChatMessageInvalidInputException extends BaseException {
    public ChatMessageInvalidInputException() {
        super(ErrorCode.CHAT_MESSAGE_INVALID_INPUT);
    }
}