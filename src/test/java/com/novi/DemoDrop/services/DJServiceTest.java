package com.novi.DemoDrop.services;

import static org.junit.jupiter.api.Assertions.*;
import com.novi.DemoDrop.Dto.InputDto.DJAccountInputDto;
import com.novi.DemoDrop.Dto.OutputDto.DJAccountOutputDto;
import com.novi.DemoDrop.Dto.OutputDto.DemoOutputDto;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
import com.novi.DemoDrop.models.DJ;
import com.novi.DemoDrop.models.Demo;
import com.novi.DemoDrop.models.Role;
import com.novi.DemoDrop.models.User;
import com.novi.DemoDrop.repositories.DJRepository;
import com.novi.DemoDrop.repositories.DemoRepository;
import com.novi.DemoDrop.repositories.RoleRepository;
import com.novi.DemoDrop.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class DJServiceTest {
    @Mock
    private DJRepository djRepository;

    @Mock
    private DemoRepository demoRepository;

    @Mock
    private DemoService demoService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private DJService djService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllDJs_ReturnsListOfDJs() {
        // Arrange
        List<DJ> djList = new ArrayList<>();
        djList.add(new DJ());
        given(djRepository.findAll()).willReturn(djList);

        // Act
        List<DJAccountOutputDto> result = djService.getAllDJs();

        // Assert
        assertEquals(djList.size(), result.size());
    }

    @Test
    void getDJById_ValidId_ReturnsDJAccountOutputDto() {
        // Arrange
        Long id = 1L;
        DJ dj = new DJ();
        dj.setId(id);
        Optional<DJ> optionalDJ = Optional.of(dj);

        when(djRepository.findByUserId(any(Long.TYPE))).thenReturn(Optional.of(dj));

        // Act
        DJAccountOutputDto result = djService.getDJByUserId(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
    }

    @Test
    void getDJById_InvalidId_ThrowsRecordNotFoundException() {
        // Arrange
        Long id = 1L;
        given(djRepository.findById(id)).willReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> djService.getDJByUserId(id));
    }

    @Test
    void createDJ_ValidInput_ReturnsDJAccountOutputDto() {
        // Arrange
        DJAccountInputDto inputDto = new DJAccountInputDto();
        DJ dj = new DJ();
        User user = new User();
        Role role = new Role();
        given(djRepository.save(any(DJ.class))).willReturn(dj);
        given(userRepository.save(any(User.class))).willReturn(user);
        given(roleRepository.save(any(Role.class))).willReturn(role);

        // Act
        DJAccountOutputDto result = djService.createDJ(inputDto);

        // Assert
        assertNotNull(result);
    }

    @Test
    void createDJ_DuplicateData_ThrowsRecordNotFoundException() {
        // Arrange
        DJAccountInputDto inputDto = new DJAccountInputDto();
        inputDto.setEmail("duplicate_email@example.com");

        // Mock the userRepository to return null for the given email
        given(userRepository.findByEmail(inputDto.getEmail())).willReturn(null);

        // Mock the djRepository to throw DataIntegrityViolationException
        given(djRepository.save(any(DJ.class))).willThrow(DataIntegrityViolationException.class);

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> djService.createDJ(inputDto));
        verify(userRepository, times(1)).findByEmail(inputDto.getEmail());
        verify(djRepository, times(1)).save(any(DJ.class));
    }

    @Test
    void getAllMyDemos_ValidDjId_ReturnsListOfDemoOutputDto() {
        // Arrange
        Long djId = 1L;
        DJ dj = new DJ();
        dj.setId(djId);
        DemoOutputDto demoOutputDto = new DemoOutputDto();

        // Create a list of demos associated with the DJ
        List<Demo> demoList = new ArrayList<>();
        demoList.add(new Demo());
        demoList.add(new Demo());
        given(djRepository.findById(djId)).willReturn(Optional.of(dj));
        given(demoRepository.findAllDemosByDj(dj)).willReturn(demoList);
        when(demoService.makeTheDto(any(Demo.class))).thenReturn(demoOutputDto);

        // Act
        List<DemoOutputDto> result = djService.getAllMyDemos(djId);

        // Assert
        assertEquals(demoList.size(), result.size());
    }

    @Test
    void getAllMyDemos_InvalidDjId_ThrowsRecordNotFoundException() {
        // Arrange
        Long djId = 1L;
        given(djRepository.findById(djId)).willReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> djService.getAllMyDemos(djId));
    }

    @Test
    void getAllMyDemos_NoDemosForDj_ReturnsEmptyList() {
        // Arrange
        Long djId = 1L;
        DJ dj = new DJ();
        dj.setId(djId);

        // DJ exists, but there are no demos associated with this DJ
        List<Demo> demoList = new ArrayList<>();
        given(djRepository.findById(djId)).willReturn(Optional.of(dj));
        given(demoRepository.findAllDemosByDj(dj)).willReturn(demoList);

        // Act
        List<DemoOutputDto> result = djService.getAllMyDemos(djId);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.size());
    }

}