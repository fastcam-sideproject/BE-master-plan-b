package com.example.masterplanbbe.infrastructure.exception.chat;

import com.example.masterplanbbe.infrastructure.exception.BaseException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;

public class ChatMessageSaveException extends BaseException {
  public ChatMessageSaveException() {
    super(ErrorCode.CHAT_SAVE_FAIL);
  }
}
