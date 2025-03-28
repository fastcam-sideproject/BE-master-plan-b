package com.example.masterplanbbe.infrastructure.exception.chat;

import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class ChatDeleteForbiddenException extends BaseException {
    public ChatDeleteForbiddenException() {
        super(ErrorCode.CHAT_DELETE_FORBIDDEN);
    }
}