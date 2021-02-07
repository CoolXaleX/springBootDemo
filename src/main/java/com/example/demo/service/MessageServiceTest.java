package com.example.demo.service;

import com.example.demo.controller.MessageDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@Profile("test")
public class MessageServiceTest implements MessageService {
    private final static Logger log = LoggerFactory.getLogger(MessageServiceTest.class);

    @Autowired
    private ObjectMapper om;

    private String file = "{}";

    @Override
    public String addMessage(MessageDto message) {
        var key = UUID.randomUUID().toString();
        log.info("saved {}:{}", key, message.getText());
        Map<String, String> map = null;
        try {
            map = om.readValue(file, Map.class);
            map.put(key, message.getText());
            file = om.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            log.error("Error get or save map");
        }
        return key;
    }

    @Override
    public MessageDto getMessage(String uuid) {
        MessageDto dto = new MessageDto();
        try {
            Map<String, String> map = om.readValue(file, Map.class);
            dto.setText(map.get(uuid));
        } catch (JsonProcessingException e) {
            log.error("Error get map");
        }
        return dto;
    }
}
