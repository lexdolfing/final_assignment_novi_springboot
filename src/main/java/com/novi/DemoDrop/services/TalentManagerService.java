package com.novi.DemoDrop.services;

import com.novi.DemoDrop.Dto.InputDto.TalentManagerInputDto;
import com.novi.DemoDrop.Dto.InputDto.UserInputDto;
import com.novi.DemoDrop.Dto.OutputDto.DJAccountOutputDto;
import com.novi.DemoDrop.Dto.OutputDto.TalentManagerOutputDto;
import com.novi.DemoDrop.controllers.UserController;
import com.novi.DemoDrop.exceptions.BadRequestException;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
import com.novi.DemoDrop.models.DJ;
import com.novi.DemoDrop.models.TalentManager;
import com.novi.DemoDrop.models.User;
import com.novi.DemoDrop.repositories.RoleRepository;
import com.novi.DemoDrop.repositories.TalentManagerRepository;
import com.novi.DemoDrop.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TalentManagerService {
    private final TalentManagerRepository talentManagerRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserController userController;
    public TalentManagerService(TalentManagerRepository talentManagerRepository, UserRepository userRepository, RoleRepository roleRepository, UserController userController) {
        this.talentManagerRepository = talentManagerRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userController = userController;
    }

    public List<TalentManagerOutputDto> getAllManagers(){
        Iterable<TalentManager> allTalentManagers = talentManagerRepository.findAll();
        List<TalentManagerOutputDto> talentManagerOutputDtos = new ArrayList<>();

        for (TalentManager t : allTalentManagers) {
            TalentManagerOutputDto talentManagerOutputDto;
            talentManagerOutputDto = makeTheDto(t);
            talentManagerOutputDtos.add(talentManagerOutputDto);
        }

        return talentManagerOutputDtos;
    }

    public TalentManagerOutputDto getManagerById(Long id) {
        Optional<TalentManager> optionalTalentManager = talentManagerRepository.findById(id);
        if (optionalTalentManager.isEmpty()) {
            throw new RecordNotFoundException("No Talent Manager found with this id");
        }
        TalentManager t = optionalTalentManager.get();
        return makeTheDto(t);
    }

    public TalentManagerOutputDto createManager(TalentManagerInputDto talentManagerInputDto) {
        if (!talentManagerInputDto.getEmail().contains("@elevaterecords")) {
            throw new BadRequestException("Not allowed to create admin account with this e-mail address");
        }
        TalentManager t = new TalentManager();
        t = setOrUpdateTalentManagerObject(talentManagerInputDto, t);
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setEmail(talentManagerInputDto.getEmail());
        userInputDto.setPassword(talentManagerInputDto.getPassword());
        userController.createUser(userInputDto);
        User u = userRepository.findByEmail(userInputDto.getEmail());
        t.setUser(u);
        try {
            talentManagerRepository.save(t);
            return makeTheDto(t);
        } catch (DataIntegrityViolationException e) {
            throw new RecordNotFoundException("Error saving demo to database");
        }

    }


// To-DO : hier nog informatie uit User aan toevoegen, zoals e-mailadres? of nooit nodig?
    public TalentManagerOutputDto makeTheDto(TalentManager t) {
        TalentManagerOutputDto talentManagerOutputDto = new TalentManagerOutputDto();
        talentManagerOutputDto.setId(t.getId());
        talentManagerOutputDto.setManagerName(t.getManagerName());
        talentManagerOutputDto.setFirstName(t.getFirstName());
        talentManagerOutputDto.setLastName(t.getLastName());
        return talentManagerOutputDto;
    }

    public TalentManager setOrUpdateTalentManagerObject (TalentManagerInputDto talentManagerInputDto, TalentManager t) {
        t.setManagerName(talentManagerInputDto.getManagerName());
        t.setFirstName(talentManagerInputDto.getFirstName());
        t.setLastName(talentManagerInputDto.getLastName());
        return t;
    }

    public TalentManagerOutputDto getTalentManagerByUserId(Long userId) {
        Optional<TalentManager> optionalTalentManager = Optional.ofNullable(talentManagerRepository.findByUserId(userId));
        if(optionalTalentManager.isEmpty()) {
            throw new RecordNotFoundException("No TalentManager found with this id");
        }
        TalentManager t = optionalTalentManager.get();
        return makeTheDto(t);
    }
}
