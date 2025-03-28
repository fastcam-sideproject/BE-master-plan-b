package com.example.masterplanbbe.infrastructure.exception.chat;

import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class InvalidChatDeleteRequestException extends BaseException {
    public InvalidChatDeleteRequestException() {
        super(ErrorCode.CHAT_DELETE_INVALID_REQUEST);
    }
}