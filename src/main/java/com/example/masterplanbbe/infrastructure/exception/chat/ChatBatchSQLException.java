package com.example.masterplanbbe.infrastructure.exception.chat;

import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class ChatBatchSQLException extends BaseException {
    public ChatBatchSQLException() {
        super(ErrorCode.CHAT_BATCH_SQL_ERROR);
    }
}