package com.novi.DemoDrop.services;

import com.novi.DemoDrop.Dto.InputDto.DJAccountInputDto;
import com.novi.DemoDrop.Dto.OutputDto.DJAccountOutputDto;
import com.novi.DemoDrop.Dto.OutputDto.DemoOutputDto;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
import com.novi.DemoDrop.models.DJ;
import com.novi.DemoDrop.models.Demo;
import com.novi.DemoDrop.models.User;
import com.novi.DemoDrop.repositories.DJRepository;
import com.novi.DemoDrop.repositories.DemoRepository;
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

    private final DemoService demoService;

    private final DemoRepository demoRepository;


    public DJService(DJRepository djRepository, UserRepository userRepository, DemoService demoService, DemoRepository demoRepository) {
        this.djRepository = djRepository;
        this.userRepository = userRepository;

        this.demoService = demoService;
        this.demoRepository = demoRepository;
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

    public DJAccountOutputDto getDJByUserId(Long id) {
        Optional<DJ> optionalDJ = djRepository.findByUserId(id);
        if(optionalDJ.isEmpty()) {
            throw new RecordNotFoundException("No DJ found with this id");
        }
        DJ d = optionalDJ.get();
        return makeTheDto(d);
    }

    public List<DemoOutputDto> getAllMyDemos(Long djId) {
        Optional<DJ> optionalDj = djRepository.findById(djId);
        List<DemoOutputDto> allMyDemosDto = new ArrayList<>();
        if(optionalDj.isPresent()) {
            DJ dj = optionalDj.get();
            List<Demo> myDemos = demoRepository.findAllDemosByDj(dj);
            for (Demo d : myDemos) {
                DemoOutputDto demoOutputDto;
                demoOutputDto = demoService.makeTheDto(d);
                allMyDemosDto.add(demoOutputDto);
            }
            return allMyDemosDto;
        } else {
            throw new RecordNotFoundException("No DJ found with this ID");
        }
    }

    public DJAccountOutputDto createDJ(DJAccountInputDto djAccountInputDto) {
        DJ d = new DJ();
        d = setOrUpdateDJObject(djAccountInputDto, d);
        User u = userRepository.findByEmail(djAccountInputDto.getEmail());
        d.setUser(u);
        try {
            djRepository.save(d);
            return makeTheDto(d);
        } catch (DataIntegrityViolationException e) {
            throw new RecordNotFoundException("Error saving demo to database");
        }
    }
// To-DO : hier nog informatie uit User aan toevoegen, zoals e-mailadres? of nooit nodig?

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


}
