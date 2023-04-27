package com.novi.DemoDrop.controllers;

import com.novi.DemoDrop.Dto.InputDto.DemoInputDto;
import com.novi.DemoDrop.Dto.OutputDto.DemoOutputDto;
import com.novi.DemoDrop.services.DemoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("demos")
@RestController
public class DemoController {

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping()
    public ResponseEntity<List<DemoOutputDto>> getAllDemos() {
        List<DemoOutputDto> demoOutputDtos = demoService.getAllDemos();
        return ResponseEntity.ok(demoOutputDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DemoOutputDto> getDemoById(@PathVariable Long id) {
        DemoOutputDto demoOutputDto = new DemoOutputDto();
        demoOutputDto = demoService.getDemoById(id);
        return ResponseEntity.ok(demoOutputDto);
    }

    @PostMapping()
    public ResponseEntity<Object> createDemo(@RequestBody DemoInputDto demoInputDto) {
        DemoOutputDto demoOutputDto = demoService.createDemo(demoInputDto);
        return ResponseEntity.ok(demoOutputDto);
    }

    // Nog toe te voegen requests:
    // Delete demo request
    // update demo request
    // get demo by id
    // get demo info (id, artistname and songname and email - voor de reactie)
}
