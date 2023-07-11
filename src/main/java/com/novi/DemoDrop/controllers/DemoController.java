package com.novi.DemoDrop.controllers;

import com.novi.DemoDrop.Dto.InputDto.DemoInputDto;
import com.novi.DemoDrop.Dto.OutputDto.DemoOutputDto;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
import com.novi.DemoDrop.Dto.OutputDto.FileUploadResponse;
import com.novi.DemoDrop.models.Demo;
import com.novi.DemoDrop.repositories.DemoRepository;
import com.novi.DemoDrop.services.DemoService;
import org.springframework.core.io.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@CrossOrigin
@RequestMapping("demos")
@RestController
public class DemoController {

    private final DemoService demoService;
    private final DemoRepository demoRepository;

    public DemoController(DemoService demoService, DemoRepository demoRepository) {
        this.demoService = demoService;
        this.demoRepository = demoRepository;
    }

    @GetMapping
    public ResponseEntity<List<DemoOutputDto>> getAllDemos() {
        List<DemoOutputDto> demoOutputDtos = demoService.getAllDemos();
        return ResponseEntity.ok(demoOutputDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DemoOutputDto> getDemoById(@PathVariable Long id) {
        DemoOutputDto demoOutputDto;
        demoOutputDto = demoService.getDemoById(id);
        return ResponseEntity.ok(demoOutputDto);
    }

    @GetMapping("/{demoId}/download")
    public ResponseEntity<Resource> downloadMp3File(@PathVariable Long demoId, HttpServletRequest request) {
        Resource resource = demoService.downloadFile(demoId);
        String mimeType;

        try {
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }

    @PostMapping
    public ResponseEntity<Object> createDemo(@RequestBody DemoInputDto demoInputDto) {
        DemoOutputDto demoOutputDto = demoService.createDemo(demoInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(demoOutputDto);
    }

    @PostMapping("/mp3file")
    public FileUploadResponse mp3FileUpload(@RequestParam("file") MultipartFile file, @RequestParam Long demoId) {
        Optional<Demo> demoOptional = demoRepository.findById(demoId);
        if (demoOptional.isPresent()) {
            Demo d = demoOptional.get();
            String uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();

            String contentType = file.getContentType();

            String fileName = demoService.storeMP3File(file, demoId);

            return new FileUploadResponse(fileName, contentType, uri);

        } else {
            throw new RecordNotFoundException("No demo found to add MP3 to");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeDemo(@PathVariable Long id) {

        Optional<Demo> demoOptional = demoRepository.findById(id);
        boolean isDeleted = false;
        if (demoOptional.isPresent()) {
            Demo d = demoOptional.get();
                isDeleted = demoService.deleteDemo(id);
        }
        if (isDeleted) {
            return ResponseEntity.noContent().build();
        } else {
            throw new RecordNotFoundException("No record found with this id");
        }

    }
}
