package com.gridstudentpractice.chatservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gridstudentpractice.chatservice.model.Message;
import com.gridstudentpractice.chatservice.service.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;



@RunWith(SpringRunner.class)
@WebMvcTest(controllers = MessageRestController.class)
class ResponseBodyTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean(name = "DBMessageServiceImpl")
    private MessageService messageService;


    private Message invalidMessage () {
        Message message = new Message();
        message.setSender("");
        message.setBody("");
        message.setTimestamp(LocalDateTime.now());
        return message;
    }

    private Message validMessage() {
        Message message = new Message();
        message.setSender("Mock");
        message.setBody("test");
        message.setTimestamp(LocalDateTime.now());
        return message;
    }

//    @Test
//    public void whenMessageIsInvalid_thenReturnStatus400() throws Exception {
//        Message message = invalidMessage();
//        String body = objectMapper.writeValueAsString(message);
//        Mockito.when(messageService.sendMessage(Mockito.any(Message.class)))
//                .thenReturn(message);
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/restChat")
//                .accept(MediaType.APPLICATION_JSON)
//                .content(body)
//                .contentType(MediaType.APPLICATION_JSON);
//        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
//        MockHttpServletResponse response = mvcResult.getResponse();
//        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
//    }
//
//    @Test
//    public void whenMessageIsValid_thenReturnStatus200() throws Exception {
//        Message message = validMessage();
//        String body = objectMapper.writeValueAsString(message);
//        Mockito.when(messageService.sendMessage(Mockito.any(Message.class)))
//                .thenReturn(message);
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post("/restChat")
//                .accept(MediaType.APPLICATION_JSON)
//                .content(body)
//                .contentType(MediaType.APPLICATION_JSON);
//        MvcResult mvcResult = mvc.perform(requestBuilder).andReturn();
//        MockHttpServletResponse response = mvcResult.getResponse();
//        assertEquals(HttpStatus.OK.value(), response.getStatus());
//    }
}
