package com.novi.DemoDrop.integrationtests;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.novi.DemoDrop.Dto.InputDto.DJAccountInputDto;
import com.novi.DemoDrop.Dto.InputDto.ReplyToDemoInputDto;
import com.novi.DemoDrop.Dto.OutputDto.DJAccountOutputDto;
import com.novi.DemoDrop.Dto.OutputDto.ReplyToDemoOutputDto;
import com.novi.DemoDrop.models.*;
import com.novi.DemoDrop.repositories.*;
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
public class DJIntegrationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ReplyToDemoService replyToDemoService;
    @Autowired
    private ReplyToDemoRepository replyToDemoRepository;
    @Autowired
    private DemoRepository demoRepository;
    @Autowired
    private TalentManagerRepository talentManagerRepository;
    @Autowired
    private DJRepository djRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;


    ReplyToDemo replyToDemo1;
    TalentManager talentManager1;
    Demo demo1;
    DJ dJ1;
    DJ dJ2;
    User user1;
    User user2;
    Role role1;
    Role role2;
    DJAccountInputDto djAccountInputDto1;

    @BeforeEach
    void setUp() {
        role1 = new Role();
        role1.setRoleName("ROLE_USER");
        role2 = new Role();
        role2.setRoleName("ROlE_USER");

        user1 = new User();
        user1.setRole(role1);
        user1.setPassword("wachtwoord123");
        user1.setEmail("test1@email.com");

        user2 = new User();
        user2.setPassword("wachtwoord456");
        user2.setEmail("test2@email.com");
        user2.setRole(role2);


        dJ1 = new DJ();
        dJ1.setUser(user1);
        dJ1.setArtistName("DJ wowie");
        dJ1.setFirstName("Kees");
        dJ1.setLastName("Koos");

        dJ2 = new DJ();
        dJ2.setUser(user2);
        dJ2.setArtistName("DJ amazings");
        dJ2.setFirstName("Hansie");
        dJ2.setLastName("Van Ouden Den Hollander");

        talentManager1 = new TalentManager();
        talentManager1.setManagerName("Jerney Kaagman");
        talentManager1.setFirstName("Jerney");
        talentManager1.setLastName("Kaagman");

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

        djAccountInputDto1 = new DJAccountInputDto();
        djAccountInputDto1.setEmail("test3@email.com");
        djAccountInputDto1.setArtistName("DJ Kletskeboem");
        djAccountInputDto1.setFirstName("Charity");
        djAccountInputDto1.setLastName("Keizer");
        djAccountInputDto1.setPassword("sterkwachtwoord");


        role1 = roleRepository.save(role1);
        role2 = roleRepository.save(role2);

        user1 = userRepository.save(user1);
        user2 = userRepository.save(user2);


        talentManager1 = talentManagerRepository.save(talentManager1);
        dJ1 = djRepository.save(dJ1);
        dJ2 = djRepository.save(dJ2);
        demo1 = demoRepository.save(demo1);
        replyToDemo1 = replyToDemoRepository.save(replyToDemo1);
    }

    @Test
    void getAllDJs() throws Exception {
        mockMvc.perform(get("/djs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(dJ1.getId()))
                .andExpect(jsonPath("$[1].id").value(dJ2.getId()))
                .andExpect(jsonPath("$[0].artistName").value(dJ1.getArtistName().toString()))
                .andExpect(jsonPath("$[1].artistName").value(dJ2.getArtistName().toString()));

    }

    @Test
    void getDjById() throws Exception {
        mockMvc.perform(get("/djs/" + dJ1.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(dJ1.getId()))
                .andExpect(jsonPath("artistName").value(dJ1.getArtistName().toString()))
                .andExpect(jsonPath("firstName").value(dJ1.getFirstName().toString()))
                .andExpect(jsonPath("lastName").value(dJ1.getLastName().toString()));
    }

    @Test
    void createDJ() throws Exception {
        mockMvc.perform(post("/djs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(djAccountInputDto1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("firstName").value(djAccountInputDto1.getFirstName()))
                .andExpect(jsonPath("lastName").value(djAccountInputDto1.getLastName()))
                .andExpect(jsonPath("artistName").value(djAccountInputDto1.getArtistName()));
    }

}