package com.example.demo.service;

import com.example.demo.controller.MessageDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Profile("local")
public class MessageServiceLocal implements MessageService {

    private final Map<String, MessageDto> map = new HashMap<>();

    @Override
    public String addMessage(MessageDto message) {
        var key = UUID.randomUUID().toString();
        map.put(key, message);
        return key;
    }

    @Override
    public MessageDto getMessage(String uuid) {
        return map.get(uuid);
    }
}
