package com.example.masterplanbbe.infrastructure.exception.chat;

import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class ChatDeleteFailedException extends BaseException {
    public ChatDeleteFailedException() {
        super(ErrorCode.CHAT_DELETE_FAILED);
    }
}