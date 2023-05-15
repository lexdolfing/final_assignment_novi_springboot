package com.novi.DemoDrop.services;

import com.novi.DemoDrop.Dto.InputDto.DemoInputDto;
import com.novi.DemoDrop.Dto.OutputDto.DemoOutputDto;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
import com.novi.DemoDrop.models.DJ;
import com.novi.DemoDrop.models.Demo;
import com.novi.DemoDrop.models.ReplyToDemo;
import com.novi.DemoDrop.models.TalentManager;
import com.novi.DemoDrop.repositories.DJRepository;
import com.novi.DemoDrop.repositories.DemoRepository;
import com.novi.DemoDrop.repositories.ReplyToDemoRepository;
import com.novi.DemoDrop.repositories.TalentManagerRepository;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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
    DemoService demoService;
    @Captor
    ArgumentCaptor<Demo> captor;
    Demo demo1;
    Demo demo2;
    DJ dJ1;
    TalentManager talentManager1;
    TalentManager talentManager2;
    ReplyToDemo replyToDemo1;

    @BeforeEach
    void setUp() {

        demoService = new DemoService(demoRepository, replyToDemoRepository, replyToDemoService, talentManagerRepository, djRepository);
        dJ1 = new DJ();
        dJ1.setId(101L);
        dJ1.setListOfDemos(new ArrayList<>());
        demoService.setFileStorageLocation("uploads");


        talentManager1 = new TalentManager();
        talentManager1.setManagerName("Jerney Kaagman");
        talentManager1.setFirstName("Jerney");
        talentManager1.setLastName("Kaagman");
        talentManager1.setId(101L);
        talentManager1.setListOfReplies(new ArrayList<>());
        talentManager1.setAssignedDemos(new ArrayList<>());

        talentManager2 = new TalentManager();
        talentManager2.setManagerName("Henkjan Smits");
        talentManager2.setFirstName("Henkjan");
        talentManager2.setLastName("Smits");
        talentManager2.setId(102L);
        talentManager2.setListOfReplies(new ArrayList<>());
        talentManager2.setAssignedDemos(new ArrayList<>());


        replyToDemo1 = new ReplyToDemo();
        replyToDemo1.setTalentManager(talentManager1);
        replyToDemo1.setId(101L);
        replyToDemo1.setHasBeenRepliedTo(false);
        replyToDemo1.setAdminDecision("We nemen contact met je op");
        replyToDemo1.setAdminComments("Was echt een lekker nummer");

        demo1 = new Demo();
        demo1.setId(101L);
        demo1.setArtistName("DJ Lex");
        demo1.setSongName("song 1");
        demo1.setEmail("test1@email.com");
        demo1.setFileName("test.mp3");
        demo1.setSongElaboration("Mijn eigen lievelings");
        demo1.setDj(dJ1);

        demo2 = new Demo();
        demo2.setId(102L);
        demo2.setArtistName("DJ Novi");
        demo2.setSongName("song 2");
        demo2.setEmail("test2@email.com");
        demo2.setFileName("song2.mp3");
        demo2.setSongElaboration("Deze is toch iets minder sorry");
        demo2.setDj(dJ1);
        demo2.setReplyToDemo(replyToDemo1);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("should return all demo's in database")
//    @Disabled
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
    @DisplayName("should return one demo found by Id")
    void getDemoById() {
        //Arrange
        when(demoRepository.findById(102L)).thenReturn(Optional.of(demo2));

        //Act
        DemoOutputDto demoOutputDto = demoService.getDemoById(102L);

        //Assert
        assertEquals(demo2.getArtistName(), demoOutputDto.getArtistName());
    }

    @Test
    @DisplayName("empty demo should trigger RecordNotFoundException")
    void getDemoByIdNotFound () {
        when(demoRepository.findById(demo1.getId())).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> demoService.getDemoById(demo1.getId()));

    }

    @Test
    @DisplayName("DemoInputDto should be returned as DemoOutputDto")
    void createDemo() {
        //Arrange (demo1, demoInputDto1)
        DemoInputDto demoInputDto1 = new DemoInputDto();
        demoInputDto1.setArtistName("Dj Tiesto");
        demoInputDto1.setEmail("email");
        demoInputDto1.setSongElaboration("Ik vind mooi");
        demoInputDto1.setSongName("10:35");
        demoInputDto1.setDjId(101L);

        when(demoRepository.save(demo1)).thenReturn(demo1);
        when(djRepository.findById(101L)).thenReturn(Optional.of(dJ1));

        //Act (maak outputDto)
        demoService.createDemo(demoInputDto1);
        verify(demoRepository, times(1)).save(captor.capture());
        Demo demo = captor.getValue();

        //Assert
        assertEquals(demoInputDto1.getArtistName(), demo.getArtistName());
        assertEquals(demoInputDto1.getDjId(), demo.getDj().getId());
        assertEquals(demoInputDto1.getFileName(), demo.getFileName());
        assertEquals(demoInputDto1.getSongName(), demo.getSongName());
        assertEquals(demoInputDto1.getSongElaboration(), demo.getSongElaboration());
        assertEquals(demoInputDto1.getEmail(), demo.getEmail());
    }

    @Test
    void deleteDemo() {
        // Arrange
        when(demoRepository.findById(101L)).thenReturn(Optional.ofNullable(demo1));

        // Act
        demoService.deleteDemo(101L);

        //Assert
        verify(demoRepository).deleteById(101L);
    }

    @Test
    @DisplayName("empty demo should trigger RecordNotFoundException")
    void deleteDemoByIdNotFound () {
        when(demoRepository.findById(demo1.getId())).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> demoService.deleteDemo(demo1.getId()));
    }

    @Test
    @DisplayName("Should add a replyToDemo to Demo")
    void assignReplyToDemo() {
        //Arrange
        when(demoRepository.findById(101L)).thenReturn(Optional.ofNullable(demo1));
        when(replyToDemoRepository.findById(101L)).thenReturn(Optional.ofNullable(replyToDemo1));
        when(demoRepository.save(demo1)).thenReturn(demo1);
        when(replyToDemoRepository.save(replyToDemo1)).thenReturn(replyToDemo1);

        ArgumentCaptor<Demo> demoCaptor = ArgumentCaptor.forClass(Demo.class);
        ArgumentCaptor<ReplyToDemo> replyCaptor = ArgumentCaptor.forClass(ReplyToDemo.class);
        //Act
        demoService.assignReplyToDemo(demo1.getId(), replyToDemo1.getId());
        verify(demoRepository, times(1)).save(demoCaptor.capture());
        Demo capturedDemo = demoCaptor.getValue();
        verify(replyToDemoRepository, times(1)).save(replyCaptor.capture());
        ReplyToDemo capturedReply = replyCaptor.getValue();
        //Assert
        assertEquals(demo1.getArtistName(), capturedDemo.getArtistName());
        assertEquals(replyToDemo1.getAdminComments(), capturedReply.getAdminComments());
        assertEquals(replyToDemo1, capturedDemo.getReplyToDemo());

    }

    @Test
    @DisplayName("empty demo should trigger RecordNotFoundException")
    void assignReplyToDemoByIdNotFound () {
        when(demoRepository.findById(demo1.getId())).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> demoService.assignReplyToDemo(demo1.getId(), replyToDemo1.getId()));
        when(replyToDemoRepository.findById(replyToDemo1.getId())).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> demoService.assignReplyToDemo(demo1.getId(), replyToDemo1.getId()));
    }

    @Test
    @DisplayName("Should map demo to demoOutPutDto, onlu .setReplyToDemoId has to be tested")
    void makeTheDto() {
        DemoOutputDto demoOutputDto1 = new DemoOutputDto();
        demoOutputDto1.setReplyToDemoId(replyToDemo1.getId());

        demoService.makeTheDto(demo2);

        assertEquals(demoOutputDto1.getReplyToDemoId(), demo2.getReplyToDemo().getId());

    }

    @Test
    @Disabled
        // TO-DO check of deze nodig is voor line coverage maar hij wordt al helemaal getest lijkt het
    void setOrUpdateDemoObject() {
    }

    @Test
    @DisplayName("should return demo with talent manager if there is one or multiple talent managers")
    void assignDemoToTalentManager() {
        // Part 1: one talent manager
        when(talentManagerRepository.findAll()).thenReturn(Collections.singletonList(talentManager1));

        Demo returnedDemo = demoService.assignDemoToTalentManager(demo1);

        assertEquals(talentManager1, returnedDemo.getTalentManager());

        // part 2: multiple talent managers
        List<TalentManager> talentManagers = Arrays.asList(talentManager1, talentManager2);
        when(talentManagerRepository.findAll()).thenReturn(talentManagers);

        Demo returnedDemo2 = demoService.assignDemoToTalentManager(demo1);

        assertTrue(returnedDemo2.getTalentManager() == talentManager1 || returnedDemo2.getTalentManager() == talentManager2);
    }


    @Test
    void getRandomNumber() {
        int number1 = 1;
        int number2 = 2;

        int returnedNumber = demoService.getRandomNumber(number1);
        assertTrue(returnedNumber == number1 || returnedNumber == 0);

        int returnedNumber2 = demoService.getRandomNumber(number2);
        assertTrue(returnedNumber2 == number1 || returnedNumber2 == number2 || returnedNumber2 == 0);    }

    @Test
    @Disabled
        // TO-DO check of deze nodig is voor line coverage maar hij wordt al helemaal getest lijkt het

    void addDemoToTalentManager() {
    }

    @Test
    void testAddDemoToTalentManager_DataIntegrityViolationException() {
        // Set up the mock to throw a DataIntegrityViolationException when talentManagerRepository.save() is called
        doThrow(DataIntegrityViolationException.class).when(talentManagerRepository).save(talentManager1);

        assertThrows(RecordNotFoundException.class, () -> demoService.addDemoToTalentManager(talentManager1, demo1));

        // Verify that talentManagerRepository.save() was called
        verify(talentManagerRepository, times(1)).save(talentManager1);
    }

    @Test
    @DisplayName("should throw exception when djRepository.findDjById returns empty")
    void assignDemoToDJThrowsException() {
        DemoInputDto demoInputDto = new DemoInputDto();
        demoInputDto.setDjId(101L);
        when(djRepository.findById(101L)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> demoService.assignDemoToDJ(demo1, demoInputDto));
    }

    @Test
    @DisplayName("should store MP3 file and update demo with the file name")
    void storeMP3File() throws IOException {
        MultipartFile mp3FileMock = mock(MultipartFile.class);
        String originalFilename = "test.mp3";

        when(mp3FileMock.getOriginalFilename()).thenReturn(originalFilename);
        when(mp3FileMock.getInputStream()).thenReturn(mock(InputStream.class));
        when(demoRepository.findById(demo1.getId())).thenReturn(Optional.of(demo1));
        when(demoRepository.save(demo1)).thenReturn(demo1);

        String fileName = demoService.storeMP3File(mp3FileMock, demo1.getId());

        ArgumentCaptor<Demo> demoCaptor = ArgumentCaptor.forClass(Demo.class);
        verify(demoRepository).save(demoCaptor.capture());
        Demo savedDemo = demoCaptor.getValue();
        assertEquals(fileName, savedDemo.getFileName());

        // Test the method - Exception: IOException
        doThrow(IOException.class).when(mp3FileMock).getInputStream();
        assertThrows(RuntimeException.class, () -> demoService.storeMP3File(mp3FileMock, demo1.getId()));

    }

    @Test
    @DisplayName("should throw RecordNotFoundException when demo is not found")
    void storeMP3File_RecordNotFoundException() throws IOException {
        MultipartFile mp3FileMock = mock(MultipartFile.class);
        String originalFilename = "test.mp3";

        when(mp3FileMock.getOriginalFilename()).thenReturn(originalFilename);
        when(mp3FileMock.getInputStream()).thenReturn(mock(InputStream.class));
        when(demoRepository.findById(demo1.getId())).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> demoService.storeMP3File(mp3FileMock, demo1.getId()));
    }

    @Test
    void downloadFile() {
        // Arrange
        when(demoRepository.findById(demo1.getId())).thenReturn(Optional.ofNullable(demo1));

        // Act
        Resource foundResource = demoService.downloadFile(demo1.getId());


        String expectedResource = "URL [file:///Users/lexdolfing/Documents/Novi/Eindopdracht/Spring%20Boot/DemoDrop/uploads/test.mp3]";

        //Assert
        assertEquals(foundResource.toString(), expectedResource);

    }

    @Test
    @DisplayName("empty demo should trigger RecordNotFoundException")
    void downloadFileDemoByIdNotFound () {
        when(demoRepository.findById(demo1.getId())).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> demoService.downloadFile(demo1.getId()));
    }
}