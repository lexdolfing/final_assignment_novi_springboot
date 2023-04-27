package com.novi.DemoDrop.services;

import com.novi.DemoDrop.Dto.OutputDto.DemoOutputDto;
import com.novi.DemoDrop.repositories.DemoRepository;
import com.novi.DemoDrop.Dto.InputDto.DemoInputDto;
import com.novi.DemoDrop.models.Demo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DemoService {

    private final DemoRepository demoRepository;

    public DemoService(DemoRepository demoRepository) {
        this.demoRepository = demoRepository;
    }

    public List<DemoOutputDto> getAllDemos() {
        Iterable<Demo> allDemos = demoRepository.findAll();
        List<DemoOutputDto> allDemosDto = new ArrayList<>();

        for (Demo d : allDemos) {
            DemoOutputDto demoOutputDto;
            demoOutputDto = makeTheDto(d);
            allDemosDto.add(demoOutputDto);
        }
        return allDemosDto;
    }
    public DemoOutputDto getDemoById(Long id) {
        Optional<Demo> demoOptional = demoRepository.findById(id);

        if(demoOptional.isEmpty()) {
            // TO-DO: throw an exception
        }
        Demo d = demoOptional.get();
        return makeTheDto(d);
    }

    public DemoOutputDto createDemo(DemoInputDto demoInputDto) {
        Demo d = new Demo();
        d = setOrUpdateDemoObject(demoInputDto, d);
        demoRepository.save(d);
        return makeTheDto(d);

    }

    public DemoOutputDto makeTheDto (Demo d) {
        DemoOutputDto demoOutputDto = new DemoOutputDto();
        demoOutputDto.setId(d.getId());
        demoOutputDto.setArtistName(d.getArtistName());
        demoOutputDto.setEmail(d.getEmail());
        demoOutputDto.setSongName(d.getSongName());
        demoOutputDto.setMp3File(d.getMp3File());
        demoOutputDto.setSongElaboration(d.getSongElaboration());
        return demoOutputDto;
    }

    public Demo setOrUpdateDemoObject (DemoInputDto demoInputDto, Demo d) {
        d.setArtistName(demoInputDto.getArtistName());
        d.setEmail(demoInputDto.getEmail());
        d.setSongName(demoInputDto.getSongName());
        d.setSongElaboration(demoInputDto.getSongElaboration());
        d.setMp3File(demoInputDto.getMp3File());
      return d;
    }


}
