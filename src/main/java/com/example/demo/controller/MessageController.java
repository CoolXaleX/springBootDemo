package com.example.demo.controller;

import com.example.demo.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Value("${test.my.value}")
    private String myValue;

    @PostMapping(value = "saveMessage")
    public String saveMessage(@Valid @RequestBody MessageDto message) {
        return messageService.addMessage(message);
    }

    @GetMapping("getMessage")
    public @ResponseBody MessageDto getMessage(@RequestParam("id") String uuid) {
        return messageService.getMessage(uuid);
    }

    @GetMapping("getSetting")
    public Map<String, String> getSetting() {
        return Map.of("setting", myValue);
    }
}
