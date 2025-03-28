package com.example.masterplanbbe.infrastructure.exception.chat;

import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class ChatRedisOperationException extends BaseException {
    public ChatRedisOperationException() {
        super(ErrorCode.CHAT_REDIS_OPERATION_FAILED);
    }
}