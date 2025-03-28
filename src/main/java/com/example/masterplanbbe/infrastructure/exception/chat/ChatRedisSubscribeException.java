package com.example.masterplanbbe.infrastructure.exception.chat;

import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class ChatRedisSubscribeException extends BaseException {
    public ChatRedisSubscribeException() {
        super(ErrorCode.CHAT_REDIS_SUBSCRIBE_FAIL);
    }
}