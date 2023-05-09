package com.novi.DemoDrop.services;

import com.novi.DemoDrop.Dto.OutputDto.DemoOutputDto;
import com.novi.DemoDrop.models.DJ;
import com.novi.DemoDrop.models.Demo;
import com.novi.DemoDrop.models.ReplyToDemo;
import com.novi.DemoDrop.models.TalentManager;
import com.novi.DemoDrop.repositories.DJRepository;
import com.novi.DemoDrop.repositories.DemoRepository;
import com.novi.DemoDrop.repositories.ReplyToDemoRepository;
import com.novi.DemoDrop.repositories.TalentManagerRepository;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class DemoServiceTest {

    @Mock
    DemoRepository demoRepository;
    @Mock
    ReplyToDemoRepository replyToDemoRepository;
    @Mock
    ReplyToDemoService replyToDemoService;
    @Mock
    TalentManagerRepository talentManagerRepository;
    @Mock
    DJRepository djRepository;


    @InjectMocks
    DemoService demoService;

    @Captor
    ArgumentCaptor<Demo> captor;
    Demo demo1;
    Demo demo2;


    @BeforeEach
    void setUp() {
        demo1 = new Demo();
        demo1.setId(101L);
        demo1.setArtistName("DJ Lex");
        demo1.setSongName("song 1");
        demo1.setEmail("test1@email.com");
        demo1.setFileName("song1.mp3");
        demo1.setSongElaboration("Mijn eigen lievelings");

        demo2 = new Demo();
        demo2.setId(102L);
        demo2.setArtistName("DJ Novi");
        demo2.setSongName("song 2");
        demo2.setEmail("test2@email.com");
        demo2.setFileName("song2.mp3");
        demo2.setSongElaboration("Deze is toch iets minder sorry");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @Disabled
    void getAllDemos() {
        //Arrange -> dit zou de uitkomst van de methode moeten zijn
        when(demoRepository.findAll()).thenReturn(List.of(demo1, demo2));

        //Act -> dit is wat de methode daadwerkelijk voor je vindt.
        List<DemoOutputDto> demosFound = demoService.getAllDemos();

        //Assert
        assertEquals(demo1.getArtistName(), demosFound.get(0).getArtistName());
        assertEquals(demo2.getArtistName(), demosFound.get(1).getArtistName());


    }

    @Test
    @Disabled
    void getDemoById() {
    }

    @Test
    @Disabled
    void createDemo() {
    }

    @Test
    @Disabled
    void deleteDemo() {
    }

    @Test
    @Disabled
    void assignReplyToDemo() {
    }

    @Test
    @Disabled
    void makeTheDto() {
    }

    @Test
    @Disabled
    void setOrUpdateDemoObject() {
    }

    @Test
    @Disabled
    void assignDemoToTalentManager() {
    }

    @Test
    @Disabled
    void getRandomNumber() {
    }

    @Test
    @Disabled
    void addDemoToTalentManager() {
    }

    @Test
    @Disabled
    void assignDemoToDJ() {
    }

    @Test
    @Disabled
    void storeMP3File() {
    }

    @Test
    @Disabled
    void downloadFile() {
    }
}