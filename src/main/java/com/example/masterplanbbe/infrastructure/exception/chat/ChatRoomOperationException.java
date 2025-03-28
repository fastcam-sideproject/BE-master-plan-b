package com.example.masterplanbbe.infrastructure.exception.chat;

import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class ChatRoomOperationException extends BaseException {
    public ChatRoomOperationException() {
        super(ErrorCode.CHAT_ROOM_OPERATION_FAILED);
    }
}