package com.example.masterplanbbe.infrastructure.exception.chat;

import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class InvalidChatQueryParameterException extends BaseException {
    public InvalidChatQueryParameterException() {
        super(ErrorCode.CHAT_INVALID_QUERY_PARAMETER);
    }
}