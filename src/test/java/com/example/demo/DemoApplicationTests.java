package com.example.demo;

import com.example.demo.controller.MessageDto;
import com.example.demo.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
@ActiveProfiles("local")
class DemoApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private MessageService service;

	@Test
	void saveMessage200() throws Exception {
		String uuid = UUID.randomUUID().toString();
		when(service.addMessage(any())).thenReturn(uuid);
		MessageDto dto = new MessageDto();
		dto.setText("text");

		mockMvc.perform(post("/saveMessage")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(uuid));
	}

	@Test
	void saveMessage400() throws Exception {
		MessageDto dto = new MessageDto();

		mockMvc.perform(post("/saveMessage")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
				.andDo(print())
				.andExpect(status().isBadRequest());
		verify(service, never()).addMessage(any());
	}

	@Test
	void getMessage200() throws Exception {
		MessageDto dto = new MessageDto();
		dto.setText("text");
		when(service.getMessage(anyString())).thenReturn(dto);

		mockMvc.perform(get("/getMessage")
				.param("id", UUID.randomUUID().toString()))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.text", is("text")));
		verify(service).getMessage(any());
	}

	@Test
	void getSetting200() throws Exception {
		mockMvc.perform(get("/getSetting"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.setting", is("value2")));
	}

}
