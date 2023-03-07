package com.pjay.securityjwt.modules.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjay.securityjwt.config.dummy.DummyObject;
import com.pjay.securityjwt.modules.user.domain.UserRepository;
import com.pjay.securityjwt.modules.user.dto.request.JoinReqDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class UserControllerTest extends DummyObject {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper om;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach // 모든 메서드가 실행되기 직전에
    public void setUp(){
        dataSetting();
    }

    @Test
    public void join_success_test() throws Exception{
        // given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("pjay");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("pjay@gmail.com");
        joinReqDto.setFullname("피제이");

        String requestBody = om.writeValueAsString(joinReqDto);

        // when
        ResultActions resultActions = mvc.perform(post("/api/join").content(requestBody).contentType(MediaType.APPLICATION_JSON));
//        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println("테스트 : "+responseBody);
        // then
        resultActions.andExpect(status().isCreated());

    }

    @Test
    public void join_fail_test() throws Exception{
        // given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("test");
        joinReqDto.setPassword("1234");
        joinReqDto.setEmail("test@gmail.com");
        joinReqDto.setFullname("테스트");

        String requestBody = om.writeValueAsString(joinReqDto);

        // when
        ResultActions resultActions = mvc.perform(post("/api/join").content(requestBody).contentType(MediaType.APPLICATION_JSON));
//        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
//        System.out.println("테스트 : "+responseBody);
        // then
        resultActions.andExpect(status().isBadRequest());

    }

    private void dataSetting(){
        userRepository.save(newUser("test", "테스트"));
    }
}
