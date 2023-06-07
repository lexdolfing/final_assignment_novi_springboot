package com.novi.DemoDrop.services;

import static org.junit.jupiter.api.Assertions.*;
import com.novi.DemoDrop.Dto.InputDto.DJAccountInputDto;
import com.novi.DemoDrop.Dto.OutputDto.DJAccountOutputDto;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
import com.novi.DemoDrop.models.DJ;
import com.novi.DemoDrop.models.Role;
import com.novi.DemoDrop.models.User;
import com.novi.DemoDrop.repositories.DJRepository;
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
        given(djRepository.findById(id)).willReturn(optionalDJ);

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
        given(roleRepository.save(any(Role.class))).willThrow(DataIntegrityViolationException.class);

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> djService.createDJ(inputDto));
        verify(userRepository, never()).save(any(User.class));
        verify(djRepository, never()).save(any(DJ.class));
    }

}