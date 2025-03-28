package com.example.masterplanbbe.infrastructure.exception.chat;

import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class ChatBatchSaveException extends BaseException {
    public ChatBatchSaveException() {
        super(ErrorCode.CHAT_BATCH_SAVE_FAIL);
    }
}