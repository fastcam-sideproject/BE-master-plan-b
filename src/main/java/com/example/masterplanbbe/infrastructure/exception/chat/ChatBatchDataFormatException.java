package com.example.masterplanbbe.infrastructure.exception.chat;

import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class ChatBatchDataFormatException extends BaseException {
    public ChatBatchDataFormatException() {
        super(ErrorCode.CHAT_BATCH_DATA_FORMAT_ERROR);
    }
}