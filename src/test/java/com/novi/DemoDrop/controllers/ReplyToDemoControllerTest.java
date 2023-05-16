package com.novi.DemoDrop.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novi.DemoDrop.Dto.InputDto.ReplyToDemoInputDto;
import com.novi.DemoDrop.Dto.OutputDto.ReplyToDemoOutputDto;
import com.novi.DemoDrop.models.Demo;
import com.novi.DemoDrop.models.ReplyToDemo;
import com.novi.DemoDrop.models.TalentManager;
import com.novi.DemoDrop.services.ReplyToDemoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReplyToDemoController.class)
class ReplyToDemoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReplyToDemoService replyToDemoService;

    TalentManager talentManager1;
    ReplyToDemo replyToDemo1;
    ReplyToDemoInputDto replyToDemoInputDto1;
    ReplyToDemoOutputDto replyToDemoOutputDto1;
    Demo demo1;

    @BeforeEach
    public void setUp() {
        talentManager1 = new TalentManager();
        talentManager1.setManagerName("Jerney Kaagman");
        talentManager1.setFirstName("Jerney");
        talentManager1.setLastName("Kaagman");
        talentManager1.setId(101L);
        talentManager1.setListOfReplies(new ArrayList<>());
        talentManager1.setAssignedDemos(new ArrayList<>());

        replyToDemo1 = new ReplyToDemo();
        replyToDemo1.setTalentManager(talentManager1);
        replyToDemo1.setId(101L);
        replyToDemo1.setHasBeenRepliedTo(false);
        replyToDemo1.setAdminDecision("We nemen contact met je op");
        replyToDemo1.setAdminComments("Was echt een lekker nummer");

        replyToDemoInputDto1 = new ReplyToDemoInputDto();
        replyToDemoInputDto1.setHasBeenRepliedTo(false);
        replyToDemoInputDto1.setAdminDecision("We nemen contact met je op");
        replyToDemoInputDto1.setAdminComments("Was echt een lekker nummer");
        replyToDemoInputDto1.setTalentManagerId(talentManager1.getId());

        replyToDemoOutputDto1 = new ReplyToDemoOutputDto();
        replyToDemoOutputDto1.setHasBeenRepliedTo(false);
        replyToDemoOutputDto1.setAdminDecision("We nemen contact met je op");
        replyToDemoOutputDto1.setAdminComments("Was echt een lekker nummer");
        replyToDemoOutputDto1.setTalentManagerId(talentManager1.getId());

        demo1 = new Demo();
        demo1.setId(101L);
        demo1.setArtistName("DJ Lex");
        demo1.setSongName("song 1");
        demo1.setEmail("test1@email.com");
        demo1.setFileName("test.mp3");
        demo1.setSongElaboration("Mijn eigen lievelings");
    }

    @Test
    void getReplyById() throws Exception {
        given(replyToDemoService.getReplyById(replyToDemo1.getId())).willReturn(replyToDemoOutputDto1);

        mockMvc.perform(get("/reply-to-demo/101")).andExpect(status().isOk())
                .andExpect(jsonPath("talentManagerId").value("101"))
                .andExpect(jsonPath("adminComments").value("Was echt een lekker nummer"))
                .andExpect(jsonPath("adminDecision").value("We nemen contact met je op"));
    }

    @Test
    void createAndAssignReply() throws Exception {
        given(replyToDemoService.createAndAssignReply(101L, replyToDemoInputDto1)).willReturn(replyToDemoOutputDto1);

        mockMvc.perform(post("/reply-to-demo/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(replyToDemoInputDto1)))
                .andExpect(jsonPath("talentManagerId").value("101"))
                .andExpect(jsonPath("adminComments").value("Was echt een lekker nummer"))
                .andExpect(jsonPath("adminDecision").value("We nemen contact met je op"));
    }

    @Test
    void updateReply() {
    }

    @Test
    void deleteReply() {
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}