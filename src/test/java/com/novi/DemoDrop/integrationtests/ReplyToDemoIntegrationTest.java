package com.novi.DemoDrop.integrationtests;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.novi.DemoDrop.Dto.InputDto.ReplyToDemoInputDto;
import com.novi.DemoDrop.Dto.OutputDto.ReplyToDemoOutputDto;
import com.novi.DemoDrop.models.*;
import com.novi.DemoDrop.repositories.DJRepository;
import com.novi.DemoDrop.repositories.DemoRepository;
import com.novi.DemoDrop.repositories.ReplyToDemoRepository;
import com.novi.DemoDrop.repositories.TalentManagerRepository;
import com.novi.DemoDrop.services.ReplyToDemoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.novi.DemoDrop.controllers.ReplyToDemoControllerTest.asJsonString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class ReplyToDemoIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ReplyToDemoService replyToDemoService;
    @Autowired
    private ReplyToDemoRepository replyToDemoRepository;
    @Autowired
    private DemoRepository demoRepository;
    @Autowired
    TalentManagerRepository talentManagerRepository;
    @Autowired
    DJRepository djRepository;


    ReplyToDemo replyToDemo1;
    ReplyToDemo replyToDemo2;
    ReplyToDemoInputDto replyToDemoInputDto1;
    ReplyToDemoInputDto replyToDemoInputDto2;
    ReplyToDemoOutputDto replyToDemoOutputDto1;

    TalentManager talentManager1;
    Demo demo1;
    DJ dJ1;

    @BeforeEach
    void setUp() {
        dJ1 = new DJ();
        dJ1.setListOfDemos(new ArrayList<>());

        talentManager1 = new TalentManager();
        talentManager1.setManagerName("Jerney Kaagman");
        talentManager1.setFirstName("Jerney");
        talentManager1.setLastName("Kaagman");
        talentManager1.setListOfReplies(new ArrayList<>());
        talentManager1.setAssignedDemos(new ArrayList<>());

        demo1 = new Demo();
        demo1.setArtistName("DJ Lex");
        demo1.setSongName("song 1");
        demo1.setEmail("test1@email.com");
        demo1.setFileName("test.mp3");
        demo1.setSongElaboration("Mijn eigen lievelings");
        demo1.setDj(dJ1);

        replyToDemo1 = new ReplyToDemo();
        replyToDemo1.setTalentManager(talentManager1);
        replyToDemo1.setHasBeenRepliedTo(true);
        replyToDemo1.setAdminDecision("We nemen contact met je op");
        replyToDemo1.setAdminComments("Was echt een lekker nummer");
        replyToDemo1.setDemo(demo1); // Set demo1 after initializing replyToDemo1

        replyToDemoInputDto1 = new ReplyToDemoInputDto();
        replyToDemoInputDto1.setAdminDecision("We nemen contact met je op");
        replyToDemoInputDto1.setHasBeenRepliedTo(true);
        replyToDemoInputDto1.setAdminComments("Was echt een lekker nummer");
        replyToDemoInputDto1.setDemoId(demo1.getId());

        replyToDemoInputDto2 = new ReplyToDemoInputDto();
        replyToDemoInputDto2.setAdminDecision("We komen er toch op terug");
        replyToDemoInputDto2.setAdminComments("Want het was toch niet goed");


        talentManager1 = talentManagerRepository.save(talentManager1);
        dJ1 = djRepository.save(dJ1);
        demo1 = demoRepository.save(demo1);
        replyToDemo1 = replyToDemoRepository.save(replyToDemo1);
        replyToDemoInputDto1.setTalentManagerId(talentManager1.getId());
    }

    @Test
    void getReplyById() throws Exception {

        mockMvc.perform(get("/reply-to-demo/" + replyToDemo1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("adminDecision").value("We nemen contact met je op"))
                .andExpect(jsonPath("hasBeenRepliedTo").value("true"))
                .andExpect(jsonPath("adminComments").value("Was echt een lekker nummer"))
                .andExpect(jsonPath("talentManagerId").value(replyToDemo1.getTalentManager().getId().toString()));

    }

    @Test
    void createAndAssignReply() throws Exception {
        mockMvc.perform(put("/reply-to-demo/" + replyToDemo1.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(replyToDemoInputDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("adminDecision").value(replyToDemoInputDto1.getAdminDecision()))
                .andExpect(jsonPath("hasBeenRepliedTo").value(replyToDemoInputDto1.isHasBeenRepliedTo()))
                .andExpect(jsonPath("adminComments").value(replyToDemoInputDto1.getAdminComments()))
                .andExpect(jsonPath("talentManagerId").value(replyToDemo1.getTalentManager().getId().toString()));
    }

    @Test
    void updateReply() throws Exception {
        mockMvc.perform(put("/reply-to-demo/" + replyToDemo1.getId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(replyToDemoInputDto2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("adminDecision").value("We komen er toch op terug"))
                .andExpect(jsonPath("hasBeenRepliedTo").value("true"))
                .andExpect(jsonPath("adminComments").value("Want het was toch niet goed"))
                .andExpect(jsonPath("talentManagerId").value(replyToDemo1.getTalentManager().getId().toString()));
    }

    @Test
    void deleteReply() throws Exception {
        mockMvc.perform(delete("/reply-to-demo/" + replyToDemo1.getId().toString()))
                .andExpect(status().isOk());

    }

}
