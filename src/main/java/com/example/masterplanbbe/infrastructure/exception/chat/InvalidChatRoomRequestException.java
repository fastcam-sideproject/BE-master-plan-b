package com.example.masterplanbbe.infrastructure.exception.chat;

import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class InvalidChatRoomRequestException extends BaseException {
    public InvalidChatRoomRequestException() {
        super(ErrorCode.CHAT_ROOM_INVALID_REQUEST);
    }
}