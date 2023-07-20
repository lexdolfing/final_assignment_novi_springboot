package com.novi.DemoDrop.services;

import com.novi.DemoDrop.Dto.InputDto.ReplyToDemoInputDto;
import com.novi.DemoDrop.Dto.OutputDto.ReplyToDemoOutputDto;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
import com.novi.DemoDrop.models.DJ;
import com.novi.DemoDrop.models.Demo;
import com.novi.DemoDrop.models.ReplyToDemo;
import com.novi.DemoDrop.models.TalentManager;
import com.novi.DemoDrop.repositories.DemoRepository;
import com.novi.DemoDrop.repositories.ReplyToDemoRepository;
import com.novi.DemoDrop.repositories.TalentManagerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.DataIntegrityViolationException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReplyToDemoServiceTest {

    @Mock
    private ReplyToDemoRepository replyToDemoRepository;

    @Mock
    private DemoRepository demoRepository;

    @Mock
    private TalentManagerRepository talentManagerRepository;

    @InjectMocks
    private ReplyToDemoService replyToDemoService;

    @Captor
    ArgumentCaptor<ReplyToDemo> captor;
    Demo demo1;
    Demo demo2;
    DJ dJ1;
    TalentManager talentManager1;
    TalentManager talentManager2;
    ReplyToDemo replyToDemo1;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dJ1 = new DJ();
        dJ1.setId(101L);



        talentManager1 = new TalentManager();
        talentManager1.setManagerName("Jerney Kaagman");
        talentManager1.setFirstName("Jerney");
        talentManager1.setLastName("Kaagman");
        talentManager1.setId(101L);

        talentManager2 = new TalentManager();
        talentManager2.setManagerName("Henkjan Smits");
        talentManager2.setFirstName("Henkjan");
        talentManager2.setLastName("Smits");
        talentManager2.setId(102L);


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

    @Test
    void getReplyById_ValidId_ReturnsOutputDto() {
        // Arrange
        Long replyId = 1L;
        ReplyToDemo replyToDemo = new ReplyToDemo();
        replyToDemo.setId(replyId);
        when(replyToDemoRepository.findById(replyId)).thenReturn(Optional.of(replyToDemo));

        // Act
        ReplyToDemoOutputDto result = replyToDemoService.getReplyById(replyId);

        // Assert
        assertNotNull(result);
        assertEquals(replyId, result.getId());
        verify(replyToDemoRepository, times(1)).findById(replyId);
    }

    @Test
    void getReplyById_InvalidId_ThrowsRecordNotFoundException() {
        // Arrange
        Long replyId = 1L;
        when(replyToDemoRepository.findById(replyId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> replyToDemoService.getReplyById(replyId));
        verify(replyToDemoRepository, times(1)).findById(replyId);
    }

    @Test
    void createReply_ValidInputDto_ReturnsOutputDto() {
        // Arrange
        ReplyToDemoInputDto inputDto = new ReplyToDemoInputDto();
        ReplyToDemo replyToDemo = new ReplyToDemo();
        when(replyToDemoRepository.save(any(ReplyToDemo.class))).thenReturn(replyToDemo);

        // Act
        ReplyToDemoOutputDto result = replyToDemoService.createReply(inputDto);

        // Assert
        assertNotNull(result);
        verify(replyToDemoRepository, times(1)).save(any(ReplyToDemo.class));
    }

    @Test
    void createReply_DataIntegrityViolation_ThrowsRecordNotFoundException() {
        // Arrange
        ReplyToDemoInputDto inputDto = new ReplyToDemoInputDto();
        when(replyToDemoRepository.save(any(ReplyToDemo.class))).thenThrow(DataIntegrityViolationException.class);

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> replyToDemoService.createReply(inputDto));
        verify(replyToDemoRepository, times(1)).save(any(ReplyToDemo.class));
    }

    @Test
    void createAndAssignReply_ValidIdAndInputDto_ReturnsOutputDto() {
        // Arrange
        ReplyToDemoInputDto inputDto = new ReplyToDemoInputDto();
        inputDto.setTalentManagerId(talentManager1.getId());
        ReplyToDemo replyToDemo = new ReplyToDemo();
        replyToDemo.setTalentManager(talentManager1);
        when(replyToDemoRepository.save(any(ReplyToDemo.class))).thenReturn(replyToDemo);
        when(demoRepository.findById(demo1.getId())).thenReturn(Optional.of(demo1));
        when(talentManagerRepository.findById(talentManager1.getId())).thenReturn(Optional.of(talentManager1));

        // Act
        ReplyToDemoOutputDto result = replyToDemoService.createAndAssignReply(demo1.getId(), inputDto);

        // Assert
        assertNotNull(result);
        verify(replyToDemoRepository, times(1)).save(any(ReplyToDemo.class));
        verify(demoRepository, times(1)).findById(demo1.getId());
        verify(talentManagerRepository, times(1)).findById(talentManager1.getId());
        ReplyToDemo replyToDemo2 = demo1.getReplyToDemo();
        assertEquals(replyToDemo.getTalentManager(), replyToDemo2.getTalentManager());
    }

    @Test
    void createAndAssignReply_InvalidDemoId_ThrowsRecordNotFoundException() {
        // Arrange
        Long demoId = 1L;
        Long talentManagerId = 1L;
        ReplyToDemoInputDto inputDto = new ReplyToDemoInputDto();
        inputDto.setTalentManagerId(talentManagerId);
        when(demoRepository.findById(demoId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> replyToDemoService.createAndAssignReply(demoId, inputDto));
        verify(demoRepository, times(1)).findById(demoId);
    }

    @Test
    void createAndAssignReply_InvalidTalentManagerId_ThrowsRecordNotFoundException() {
        // Arrange
        Long demoId = 1L;
        Long talentManagerId = 1L;
        ReplyToDemoInputDto inputDto = new ReplyToDemoInputDto();
        inputDto.setTalentManagerId(talentManager1.getId());
        when(demoRepository.findById(demo1.getId())).thenReturn(Optional.of(new Demo()));
        when(talentManagerRepository.findById(talentManager1.getId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> replyToDemoService.createAndAssignReply(demo1.getId(), inputDto));
        verify(demoRepository, times(1)).findById(demo1.getId());
        verify(talentManagerRepository, times(1)).findById(talentManager1.getId());
        verifyNoMoreInteractions(replyToDemoRepository);
    }

    @Test
    void updateReply_ValidIdAndInputDto_ReturnsOutputDto() {
        // Arrange

        ReplyToDemoInputDto inputDto = new ReplyToDemoInputDto();
        inputDto.setAdminDecision("Geupdate");
        inputDto.setHasBeenRepliedTo(true);
        inputDto.setAdminComments("Geupdate comments");
        inputDto.setDemoId(demo1.getId());
        ReplyToDemo replyToDemo = new ReplyToDemo();
        when(replyToDemoRepository.findById(replyToDemo1.getId())).thenReturn(Optional.of(replyToDemo));
        when(replyToDemoRepository.save(any(ReplyToDemo.class))).thenReturn(replyToDemo);
        when(demoRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(demo1));

        // Act
        ReplyToDemoOutputDto result = replyToDemoService.updateReply(replyToDemo1.getId(), inputDto);

        // Assert
        assertNotNull(result);
        verify(replyToDemoRepository, times(1)).findById(replyToDemo1.getId());
        verify(replyToDemoRepository, times(1)).save(any(ReplyToDemo.class));
    }

    @Test
    void updateReply_throwsException_whenIDNotPresent() {
        ReplyToDemoInputDto replyToDemoInputDto = new ReplyToDemoInputDto();
        assertThrows(RecordNotFoundException.class, () -> replyToDemoService.updateReply(1001L, replyToDemoInputDto));

    }

    @Test
    void deleteReply_ExistingId_DeletesRecordAndReturnsTrue() {
        // Arrange
        Long id = 1L;
        when(replyToDemoRepository.findById(id)).thenReturn(Optional.of(new ReplyToDemo()));

        // Act
        boolean result = replyToDemoService.deleteReply(id);

        // Assert
        assertTrue(result);
        verify(replyToDemoRepository).deleteById(id);
    }

    @Test
    void deleteReply_NonExistingId_ThrowsRecordNotFoundException() {
        // Arrange
        Long id = 1L;
        when(replyToDemoRepository.findById(id)).thenReturn(Optional.empty());

        // Act and assert
        assertThrows(RecordNotFoundException.class, () -> replyToDemoService.deleteReply(id));
        verify(replyToDemoRepository, never()).deleteById(id);
    }
}


