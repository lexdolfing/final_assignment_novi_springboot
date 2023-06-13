package com.novi.DemoDrop.controllers;

import com.novi.DemoDrop.Dto.InputDto.DJAccountInputDto;
import com.novi.DemoDrop.Dto.OutputDto.DJAccountOutputDto;
import com.novi.DemoDrop.services.DJService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RequestMapping("dj")
@RestController
public class DJController {

    private final DJService djService;

    public DJController(DJService djService) {
        this.djService = djService;
    }

    @GetMapping
    public ResponseEntity<List<DJAccountOutputDto>> getAllDJs() {
        List<DJAccountOutputDto> djAccountOutputDtos = djService.getAllDJs();
        return ResponseEntity.ok(djAccountOutputDtos);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<DJAccountOutputDto> getDJByUserId(@PathVariable Long userId) {
        DJAccountOutputDto djAccountOutputDto = djService.getDJByUserId(userId);
        return ResponseEntity.ok(djAccountOutputDto);
    }

    @PostMapping
    public ResponseEntity<DJAccountOutputDto> createDJ(@RequestBody DJAccountInputDto djAccountInputDto) {
        DJAccountOutputDto djAccountOutputDto = djService.createDJ(djAccountInputDto);
        return ResponseEntity.ok(djAccountOutputDto);
    }

}
