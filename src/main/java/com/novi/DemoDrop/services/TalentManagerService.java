package com.novi.DemoDrop.services;

import com.novi.DemoDrop.Dto.InputDto.TalentManagerInputDto;
import com.novi.DemoDrop.Dto.OutputDto.DemoOutputDto;
import com.novi.DemoDrop.Dto.OutputDto.TalentManagerOutputDto;
import com.novi.DemoDrop.exceptions.BadRequestException;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
import com.novi.DemoDrop.models.Demo;
import com.novi.DemoDrop.models.TalentManager;
import com.novi.DemoDrop.models.User;
import com.novi.DemoDrop.repositories.DemoRepository;
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
    private final DemoService demoService;

    private final DemoRepository demoRepository;

    public TalentManagerService(TalentManagerRepository talentManagerRepository, UserRepository userRepository, DemoService demoService, DemoRepository demoRepository) {
        this.talentManagerRepository = talentManagerRepository;
        this.userRepository = userRepository;

        this.demoService = demoService;
        this.demoRepository = demoRepository;
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

    public List<DemoOutputDto> getDemosByTalentManager(Long talentManagerId) {
        Optional<TalentManager> optionalTalentManager = talentManagerRepository.findById(talentManagerId);
        if (optionalTalentManager.isPresent()){
            TalentManager talentManager = optionalTalentManager.get();
            Iterable<Demo> allDemos = demoRepository.findAllDemosByTalentManager(talentManager);
            List<DemoOutputDto> allDemosDto = new ArrayList<>();
            for (Demo d : allDemos) {
                DemoOutputDto demoOutputDto;
                demoOutputDto = demoService.makeTheDto(d);
                allDemosDto.add(demoOutputDto);
            }
            return allDemosDto;
        } else {
            throw new RecordNotFoundException("No talent manager with this id found");
        }


    }

    public TalentManagerOutputDto createManager(TalentManagerInputDto talentManagerInputDto) {
        if (!talentManagerInputDto.getEmail().contains("@elevaterecords")) {
            throw new BadRequestException("Not allowed to create admin account with this e-mail address");
        }
        TalentManager t = new TalentManager();
        t = setOrUpdateTalentManagerObject(talentManagerInputDto, t);
        User u = userRepository.findByEmail(talentManagerInputDto.getEmail());
        t.setUser(u);
        try {
            talentManagerRepository.save(t);
            return makeTheDto(t);
        } catch (DataIntegrityViolationException e) {
            throw new RecordNotFoundException("Error saving demo to database");
        }

    }

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
        Optional<TalentManager> optionalTalentManager = talentManagerRepository.findByUserId(userId);
        if(optionalTalentManager.isEmpty()) {
            throw new RecordNotFoundException("No TalentManager found with this id");
        }
        TalentManager t = optionalTalentManager.get();
        return makeTheDto(t);
    }


}
