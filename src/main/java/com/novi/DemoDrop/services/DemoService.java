package com.novi.DemoDrop.services;

import com.novi.DemoDrop.Dto.OutputDto.DemoOutputDto;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
import com.novi.DemoDrop.models.ReplyToDemo;
import com.novi.DemoDrop.repositories.DemoRepository;
import com.novi.DemoDrop.Dto.InputDto.DemoInputDto;
import com.novi.DemoDrop.models.Demo;
import com.novi.DemoDrop.repositories.ReplyToDemoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DemoService {

    private final DemoRepository demoRepository;
    private final ReplyToDemoRepository replyToDemoRepository;
    private final ReplyToDemoService replyToDemoService;

    public DemoService(DemoRepository demoRepository, ReplyToDemoRepository replyToDemoRepository, ReplyToDemoService replyToDemoService) {
        this.demoRepository = demoRepository;
        this.replyToDemoRepository = replyToDemoRepository;
        this.replyToDemoService = replyToDemoService;
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

        if (demoOptional.isEmpty()) {
            throw new RecordNotFoundException("No record found with this id");
        }
        Demo d = demoOptional.get();
        return makeTheDto(d);
    }

    public DemoOutputDto createDemo(DemoInputDto demoInputDto) {
        Demo d = new Demo();
        d = setOrUpdateDemoObject(demoInputDto, d);
        try {
            demoRepository.save(d);
            return makeTheDto(d);
        }catch (DataIntegrityViolationException e) {
            throw new RecordNotFoundException("Error saving demo to database");
        }
    }

    public boolean deleteDemo(Long id) {
        if (demoRepository.findById(id).isPresent()) {
            demoRepository.deleteById(id);
            return true;
        } else {
            throw new RecordNotFoundException("No record found with this id");
        }
    }

    public void assignReplyToDemo(Long DemoId, Long ReplyId) {
        // haal Demo object op
        Optional<Demo> demoOptional= demoRepository.findById(DemoId);
        // haal Reply object op
        Optional<ReplyToDemo> replyToDemoOptional = replyToDemoRepository.findById(ReplyId);

        // Als die allenbei bestaan dan
        if(demoOptional.isPresent() && replyToDemoOptional.isPresent()) {
            // stop ze in een normaal object
            Demo d = demoOptional.get();
            ReplyToDemo r = replyToDemoOptional.get();
            // set remote controller bij television. Dus nu heeft television object x een remotecontroller y in zijn tabel staan.
            d.setReplyToDemo(r);
            r.setDemo(d);
            // sla television op
            demoRepository.save(d);
            replyToDemoRepository.save(r);
        } else {
            throw new RecordNotFoundException();
        }

    }

    public DemoOutputDto makeTheDto (Demo d) {
        DemoOutputDto demoOutputDto = new DemoOutputDto();
        demoOutputDto.setId(d.getId());
        demoOutputDto.setArtistName(d.getArtistName());
        demoOutputDto.setEmail(d.getEmail());
        demoOutputDto.setSongName(d.getSongName());
        demoOutputDto.setMp3File(d.getMp3File());
        demoOutputDto.setSongElaboration(d.getSongElaboration());
        if (d.getReplyToDemo() != null) {
            demoOutputDto.setReplyToDemoId(d.getReplyToDemo().getId());
        }
        return demoOutputDto;
    }

    // Bij update van demo, moet hier een getReplyToDemo uit de inputDto komen?
    // dwz, als een reply aan een demo toegewezen wordt, moet hier de demo geupdate worden?

    public Demo setOrUpdateDemoObject (DemoInputDto demoInputDto, Demo d) {
        d.setArtistName(demoInputDto.getArtistName());
        d.setEmail(demoInputDto.getEmail());
        d.setSongName(demoInputDto.getSongName());
        d.setSongElaboration(demoInputDto.getSongElaboration());
        d.setMp3File(demoInputDto.getMp3File());
      return d;
    }



}
