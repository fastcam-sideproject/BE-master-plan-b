package com.example.masterplanbbe.infrastructure.exception.chat;

import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class ChatMessageQueryException extends BaseException {
    public ChatMessageQueryException() {
        super(ErrorCode.CHAT_MESSAGE_QUERY_FAIL);
    }
}