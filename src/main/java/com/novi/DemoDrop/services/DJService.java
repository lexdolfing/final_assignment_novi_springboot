package com.novi.DemoDrop.services;

import com.novi.DemoDrop.Dto.InputDto.DJAccountInputDto;
import com.novi.DemoDrop.Dto.OutputDto.DJAccountOutputDto;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
import com.novi.DemoDrop.models.DJ;
import com.novi.DemoDrop.models.Role;
import com.novi.DemoDrop.models.User;
import com.novi.DemoDrop.repositories.DJRepository;
import com.novi.DemoDrop.repositories.RoleRepository;
import com.novi.DemoDrop.repositories.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DJService {

    private final DJRepository djRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public DJService(DJRepository djRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.djRepository = djRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<DJAccountOutputDto> getAllDJs() {
        Iterable<DJ> allDjs = djRepository.findAll();
        List<DJAccountOutputDto> djAccountOutputDtos = new ArrayList<>();

        for (DJ d : allDjs) {
            DJAccountOutputDto djAccountOutputDto;
            djAccountOutputDto = makeTheDto(d);
            djAccountOutputDtos.add(djAccountOutputDto);
        }
        return djAccountOutputDtos;
    }

    public DJAccountOutputDto getDJById(Long id) {
        Optional<DJ> optionalDJ = djRepository.findById(id);
        if(optionalDJ.isEmpty()) {
            throw new RecordNotFoundException("No DJ found with this id");
        }
        DJ d = optionalDJ.get();
        return makeTheDto(d);
    }

    public DJAccountOutputDto createDJ(DJAccountInputDto djAccountInputDto) {
        DJ d = new DJ();
        User u = new User();
        Role r = new Role();
        r.setRoleName("ROLE_USER");
        d = setOrUpdateDJObject(djAccountInputDto, d);
        u = setOrUpdateUserObject(djAccountInputDto, u, r);
        d.setUser(u);
        try {
            roleRepository.save(r);
            userRepository.save(u);
            djRepository.save(d);
            return makeTheDto(d);
        } catch (DataIntegrityViolationException e) {
            throw new RecordNotFoundException("Error saving demo to database");
        }
    }

    public DJAccountOutputDto makeTheDto (DJ d) {
        DJAccountOutputDto djAccountOutputDto = new DJAccountOutputDto();
        djAccountOutputDto.setId(d.getId());
        djAccountOutputDto.setFirstName(d.getFirstName());
        djAccountOutputDto.setLastName(d.getLastName());
        djAccountOutputDto.setArtistName(d.getArtistName());
        return djAccountOutputDto;
    }

    public DJ setOrUpdateDJObject (DJAccountInputDto djAccountInputDto, DJ d) {
        d.setArtistName(djAccountInputDto.getArtistName());
        d.setFirstName(djAccountInputDto.getFirstName());
        d.setLastName(djAccountInputDto.getLastName());
        return d;
    }

    public User setOrUpdateUserObject (DJAccountInputDto djAccountInputDto, User u, Role r) {
        u.setEmail(djAccountInputDto.getEmail());
        u.setPassword(djAccountInputDto.getPassword());
        u.setRole(r);
        return u;
    }


}
