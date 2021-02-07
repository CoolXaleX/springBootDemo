package com.example.demo.service;

import com.example.demo.controller.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface MessageService {
    String addMessage(MessageDto message);

    MessageDto getMessage(String uuid);
}
