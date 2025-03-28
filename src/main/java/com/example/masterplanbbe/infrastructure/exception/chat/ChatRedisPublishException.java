package com.example.masterplanbbe.infrastructure.exception.chat;

import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class ChatRedisPublishException extends BaseException {
    public ChatRedisPublishException() {
        super(ErrorCode.CHAT_REDIS_PUBLISH_FAIL);
    }
}