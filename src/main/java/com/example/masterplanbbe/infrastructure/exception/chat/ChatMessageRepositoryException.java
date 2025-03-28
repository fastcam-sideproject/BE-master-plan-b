package com.example.masterplanbbe.infrastructure.exception.chat;

import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class ChatMessageRepositoryException extends BaseException {
    public ChatMessageRepositoryException() {
        super(ErrorCode.CHAT_MESSAGE_REPO_FAIL);
    }
}