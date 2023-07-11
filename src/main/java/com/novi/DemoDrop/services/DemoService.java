package com.novi.DemoDrop.services;

import com.novi.DemoDrop.Dto.OutputDto.DemoOutputDto;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
import com.novi.DemoDrop.models.*;
import com.novi.DemoDrop.repositories.*;
import com.novi.DemoDrop.Dto.InputDto.DemoInputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class DemoService {

    private  DemoRepository demoRepository;
    private  ReplyToDemoRepository replyToDemoRepository;

    private  TalentManagerRepository talentManagerRepository;
    private  DJRepository djRepository;
    private Path fileStoragePath;
    private String fileStorageLocation;

    @Autowired
    public DemoService(DemoRepository demoRepository, ReplyToDemoRepository replyToDemoRepository, TalentManagerRepository talentManagerRepository, DJRepository djRepository, @Value("uploads") String fileStorageLocation) {
        this.demoRepository = demoRepository;
        this.replyToDemoRepository = replyToDemoRepository;
        this.talentManagerRepository = talentManagerRepository;
        this.djRepository = djRepository;
        this.fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageLocation = fileStorageLocation;
        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Issue in creating file directory");
        }
    }

    public DemoService(DemoRepository demoRepository, ReplyToDemoRepository replyToDemoRepository, TalentManagerRepository talentManagerRepository, DJRepository djRepository) {
        this.demoRepository = demoRepository;
        this.replyToDemoRepository = replyToDemoRepository;
        this.talentManagerRepository = talentManagerRepository;
        this.djRepository = djRepository;
        this.fileStoragePath = Paths.get("uploads").toAbsolutePath().normalize();
        this.fileStorageLocation = "uploads";
        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Issue in creating file directory");
        }
    }

    public DemoService() {
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
            d = demoRepository.save(d);
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
        Optional<Demo> demoOptional = demoRepository.findById(DemoId);
        Optional<ReplyToDemo> replyToDemoOptional = replyToDemoRepository.findById(ReplyId);

        if (demoOptional.isPresent() && replyToDemoOptional.isPresent()) {
            Demo d = demoOptional.get();
            ReplyToDemo r = replyToDemoOptional.get();
            d.setReplyToDemo(r);
            r.setDemo(d);
            demoRepository.save(d);
            replyToDemoRepository.save(r);
        } else {
            throw new RecordNotFoundException("No demo and/or reply with this id were found");
        }

    }
    public DemoOutputDto makeTheDto(Demo d) {
        DemoOutputDto demoOutputDto = new DemoOutputDto();
        demoOutputDto.setId(d.getId());
        demoOutputDto.setArtistName(d.getArtistName());
        demoOutputDto.setEmail(d.getEmail());
        demoOutputDto.setSongName(d.getSongName());
        demoOutputDto.setSongElaboration(d.getSongElaboration());
        if (d.getDj() != null) {
            demoOutputDto.setDjId(d.getDj().getId());
        }
        if (d.getReplyToDemo() != null) {
            demoOutputDto.setReplyToDemoId(d.getReplyToDemo().getId());
        }
        if (d.getFileName() != null) {
            demoOutputDto.setFileName(d.getFileName());
        }

        return demoOutputDto;
    }

    public Demo setOrUpdateDemoObject(DemoInputDto demoInputDto, Demo d) {
        d.setArtistName(demoInputDto.getArtistName());
        d.setEmail(demoInputDto.getEmail());
        d.setSongName(demoInputDto.getSongName());
        d.setSongElaboration(demoInputDto.getSongElaboration());
        return d;
    }

    public Demo assignDemoToTalentManager(Demo d) {
        List<TalentManager> talentManagerList = talentManagerRepository.findAll();
        if (talentManagerList.size() == 1) {
            TalentManager t = talentManagerList.get(0);
            d.setTalentManager(t);
        } else if (talentManagerList.size() > 1) {
            int talentManagerIndex = (getRandomNumber(talentManagerList.size()-1));
            TalentManager t = talentManagerList.get(talentManagerIndex);
            d.setTalentManager(t);
        } else {
            throw new RecordNotFoundException("No Talent Managers found");
        }
        return d;
    }

    public int getRandomNumber(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }


    public Demo assignDemoToDJ(Demo d, DemoInputDto demoInputDto) {
        if (demoInputDto.getDjId() != null) {
            Optional<DJ> optionalDJ = djRepository.findById(demoInputDto.getDjId());
            if (optionalDJ.isEmpty()) {
                throw new RecordNotFoundException("No DJ with this id found");
            }
            DJ dj = optionalDJ.get();
            d.setDj(dj);
        }
        return d;
    }

    public String storeMP3File(MultipartFile mp3File, Long id) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(mp3File.getOriginalFilename()));

        // path for mac and Linux version:
        Path filePath = Paths.get(fileStoragePath + "/" + fileName);

        // path for Windows version:
//        Path filePath = Paths.get(fileStoragePath + "\\" + fileName);

        try {
            Files.copy(mp3File.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Issue in storing the file", e);
        }

        Optional<Demo> optionalDemo = demoRepository.findById(id);
        if(optionalDemo.isEmpty()) {
            throw new RecordNotFoundException("No demo found with this id");

        } else {
            Demo d = optionalDemo.get();
            d.setFileName(fileName);
            demoRepository.save(d);
        }

        return fileName;
    }

    public Resource downloadFile(Long id) {

        //demo ophalen ahv id, filenamer eruit, file
        Optional <Demo> optionalDemo = demoRepository.findById(id);
        if (optionalDemo.isEmpty()) {
            throw new RecordNotFoundException("No demo found with this id");
        }
        Demo d = optionalDemo.get();
        String fileName = d.getFileName();

        Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);

        Resource resource;

        try {
            resource = new UrlResource(path.toUri());
        } catch (MalformedURLException e) {
            throw new RuntimeException("Issue in reading the file", e);
        }

        if(resource.exists()&& resource.isReadable()) {
            return resource;
        } else {
            throw new RuntimeException("the file doesn't exist or not readable");
        }
    }

    public void setFileStorageLocation(String fileStorageLocation) {
        this.fileStorageLocation = fileStorageLocation;
    }


}
