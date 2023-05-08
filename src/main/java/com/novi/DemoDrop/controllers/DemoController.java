package com.novi.DemoDrop.controllers;

import com.novi.DemoDrop.Dto.InputDto.DemoInputDto;
import com.novi.DemoDrop.Dto.InputDto.IdInputDto;
import com.novi.DemoDrop.Dto.OutputDto.DemoOutputDto;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
import com.novi.DemoDrop.Dto.OutputDto.FileUploadResponse;
import com.novi.DemoDrop.services.DemoService;
import org.springframework.core.io.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@CrossOrigin
@RequestMapping("demos")
@RestController
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
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

        try{
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }

    @PostMapping
    public ResponseEntity<Object> createDemo(@RequestBody DemoInputDto demoInputDto) {
        DemoOutputDto demoOutputDto = demoService.createDemo(demoInputDto);
        return ResponseEntity.ok(demoOutputDto);
    }

    @PostMapping("/mp3file")
    public FileUploadResponse mp3FileUpload(@RequestParam("file") MultipartFile file, @RequestParam Long demoId) {
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();

        String contentType = file.getContentType();

        String fileName = demoService.storeMP3File(file, demoId);

        return new FileUploadResponse(fileName, contentType, uri);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> removeDemo (@PathVariable Long id) {
        boolean isDeleted = demoService.deleteDemo(id);
        if (isDeleted) {
            return ResponseEntity.ok().body("Element is deleted");
        } else {
            throw new RecordNotFoundException("No record found with this id");
        }

    }
    // TO-DO: voeg nog @Valid toe voor @RequestBody als validation dependency geinjecteerd is
    // this method assigns an existing reply to a demo
    // MAAR: deze methode mag weg want ga ik niet gebruiken? Reply wordt altijd direct aan demo gekoppeld.
    @PutMapping("/{id}/reply-to-demo")
    public void assignReplyToDemo(@PathVariable("id") Long id, @RequestBody IdInputDto input) {
        demoService.assignReplyToDemo(id, input.id);
    }


    // Nog toe te voegen requests:
    // update demo request (maar echt?)
    // get demo info (id, artistname and songname and email - voor de reactie) Maar misschien moet deze in de ReplyToDemoKlasse?
}
