package com.novi.DemoDrop.services;

import com.novi.DemoDrop.Dto.InputDto.TalentManagerInputDto;
import com.novi.DemoDrop.Dto.OutputDto.TalentManagerOutputDto;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
import com.novi.DemoDrop.models.Role;
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
    public TalentManagerService(TalentManagerRepository talentManagerRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.talentManagerRepository = talentManagerRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
        TalentManager t = new TalentManager();
        User u = new User();
        Role r = new Role();
        r.setRoleName("ROLE_ADMIN");
        t = setOrUpdateTalentManagerObject(talentManagerInputDto, t);
        u = setOrUpdateUserObject(talentManagerInputDto, u, r);
        t.setUser(u);
        try {
            roleRepository.save(r);
            userRepository.save(u);
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

    public User setOrUpdateUserObject (TalentManagerInputDto talentManagerInputDto, User u, Role r) {
        u.setEmail(talentManagerInputDto.getEmail());
        u.setPassword(talentManagerInputDto.getPassword());
        u.setRole(r);
        return u;
    }

}
