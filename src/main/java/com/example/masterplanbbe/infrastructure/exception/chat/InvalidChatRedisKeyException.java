package com.example.masterplanbbe.infrastructure.exception.chat;

import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class InvalidChatRedisKeyException extends BaseException {
    public InvalidChatRedisKeyException() {
        super(ErrorCode.CHAT_REDIS_INVALID_KEY);
    }
}