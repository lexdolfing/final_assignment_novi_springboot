package com.novi.DemoDrop.services;

import com.novi.DemoDrop.Dto.OutputDto.DemoOutputDto;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
import com.novi.DemoDrop.models.DJ;
import com.novi.DemoDrop.models.ReplyToDemo;
import com.novi.DemoDrop.models.TalentManager;
import com.novi.DemoDrop.repositories.DJRepository;
import com.novi.DemoDrop.repositories.DemoRepository;
import com.novi.DemoDrop.Dto.InputDto.DemoInputDto;
import com.novi.DemoDrop.models.Demo;
import com.novi.DemoDrop.repositories.ReplyToDemoRepository;
import com.novi.DemoDrop.repositories.TalentManagerRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class DemoService {

    private final DemoRepository demoRepository;
    private final ReplyToDemoRepository replyToDemoRepository;
    private final ReplyToDemoService replyToDemoService;
    private final TalentManagerRepository talentManagerRepository;
    private final DJRepository djRepository;

    public DemoService(DemoRepository demoRepository, ReplyToDemoRepository replyToDemoRepository, ReplyToDemoService replyToDemoService, TalentManagerRepository talentManagerRepository, DJRepository djRepository) {
        this.demoRepository = demoRepository;
        this.replyToDemoRepository = replyToDemoRepository;
        this.replyToDemoService = replyToDemoService;
        this.talentManagerRepository = talentManagerRepository;
        this.djRepository = djRepository;
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
            d = assignDemoToTalentManager(d);
            d = assignDemoToDJ(d, demoInputDto);
            demoRepository.save(d);
            return makeTheDto(d);
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
        Optional<Demo> demoOptional = demoRepository.findById(DemoId);
        // haal Reply object op
        Optional<ReplyToDemo> replyToDemoOptional = replyToDemoRepository.findById(ReplyId);

        // Als die allenbei bestaan dan
        if (demoOptional.isPresent() && replyToDemoOptional.isPresent()) {
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

    public DemoOutputDto makeTheDto(Demo d) {
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

    public Demo setOrUpdateDemoObject(DemoInputDto demoInputDto, Demo d) {
        d.setArtistName(demoInputDto.getArtistName());
        d.setEmail(demoInputDto.getEmail());
        d.setSongName(demoInputDto.getSongName());
        d.setSongElaboration(demoInputDto.getSongElaboration());
        d.setMp3File(demoInputDto.getMp3File());
        return d;
    }

    public Demo assignDemoToTalentManager(Demo d) {
        List<TalentManager> talentManagerList = talentManagerRepository.findAll();
        if (talentManagerList.size() > 0) {
            TalentManager t = talentManagerList.get(0);
            d.setTalentManager(t);
            addDemoToTalentManager(t, d);
        }
        if (talentManagerList.size() > 1) {
            int talentManagerIndex = (getRandomNumber(talentManagerList.size()) - 1);
            TalentManager t = talentManagerList.get(talentManagerIndex);
            d.setTalentManager(t);
            addDemoToTalentManager(t, d);
        }
        return d;
    }

    public int getRandomNumber(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    public void addDemoToTalentManager(TalentManager t, Demo d) {
        try {
            t.addDemoToListOfAssignedDemos(d);
            talentManagerRepository.save(t);
        } catch (DataIntegrityViolationException e) {
            throw new RecordNotFoundException("Error saving demo to database");
        }
    }

    public Demo assignDemoToDJ(Demo d, DemoInputDto demoInputDto) {
        if (demoInputDto.getDjId() != null) {
            Optional<DJ> optionalDJ = djRepository.findById(demoInputDto.getDjId());
            if (optionalDJ.isEmpty()) {
                throw new RecordNotFoundException("No DJ with this id found");
            }
            DJ dj = optionalDJ.get();
            dj.addDemoToListOfDemos(d);
            djRepository.save(dj);
            d.setDj(dj);

        }
        return d;
    }

}
