package com.novi.DemoDrop.controllers;

import com.novi.DemoDrop.Dto.DemoDto;
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
    public ResponseEntity<List<DemoDto>> getAllDemos() {
        List<DemoDto> demoDtos = demoService.getAllDemos();
        return ResponseEntity.ok(demoDtos);
    }

//    @PostMapping()
//    public ResponseEntity<Object> createDemo(@RequestBody DemoDto demoDto) {
//        // mee bezig
//    }
}
