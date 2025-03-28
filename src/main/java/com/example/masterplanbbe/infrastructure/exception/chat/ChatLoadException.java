package com.example.masterplanbbe.infrastructure.exception.chat;

import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class ChatLoadException extends BaseException {
    public ChatLoadException() {
        super(ErrorCode.CHAT_LOAD_FAIL);
    }
}