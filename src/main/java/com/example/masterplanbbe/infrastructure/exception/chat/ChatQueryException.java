package com.example.masterplanbbe.infrastructure.exception.chat;


import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class ChatQueryException extends BaseException {
    public ChatQueryException() {
        super(ErrorCode.CHAT_QUERY_FAIL);
    }
}