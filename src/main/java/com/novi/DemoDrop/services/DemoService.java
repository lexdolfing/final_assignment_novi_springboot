package com.novi.DemoDrop.services;

import com.novi.DemoDrop.repositories.DemoRepository;
import com.novi.DemoDrop.Dto.DemoDto;
import com.novi.DemoDrop.models.Demo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DemoService {

    private final DemoRepository demoRepository;

    public DemoService(DemoRepository demoRepository) {
        this.demoRepository = demoRepository;
    }

    public List<DemoDto> getAllDemos() {
        Iterable<Demo> allDemos = demoRepository.findAll();
        List<DemoDto> allDemosDto = new ArrayList<>();

        for (Demo d : allDemos) {
            DemoDto demoDto;
            demoDto = makeTheDto(d);
            allDemosDto.add(demoDto);
        }
        return allDemosDto;
    }

    public DemoDto makeTheDto (Demo d) {
        DemoDto demoDto = new DemoDto();
        demoDto.setId(d.getId());
        demoDto.setArtistName(d.getArtistName());
        demoDto.setEmail(d.getEmail());
        demoDto.setSongName(d.getSongName());
        demoDto.setMp3File(d.getMp3File());
        demoDto.setSongElaboration(d.getSongElaboration());
        return demoDto;
    }
}
