package com.novi.DemoDrop.controllers;

import com.novi.DemoDrop.Dto.InputDto.DJAccountInputDto;
import com.novi.DemoDrop.Dto.InputDto.TalentManagerInputDto;
import com.novi.DemoDrop.Dto.OutputDto.DJAccountOutputDto;
import com.novi.DemoDrop.Dto.OutputDto.TalentManagerOutputDto;
import com.novi.DemoDrop.services.TalentManagerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("talentmanager")
@RestController
public class TalentManagerController {
    private final TalentManagerService talentManagerService;

    public TalentManagerController(TalentManagerService talentManagerService) {
        this.talentManagerService = talentManagerService;
    }

    @GetMapping
    public ResponseEntity<List<TalentManagerOutputDto>> getAllManagers() {
        List<TalentManagerOutputDto> talentManagerOutputDtos = talentManagerService.getAllManagers();
        return ResponseEntity.ok(talentManagerOutputDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TalentManagerOutputDto> getManagerById(@PathVariable Long id) {
        TalentManagerOutputDto talentManagerOutputDto = talentManagerService.getManagerById(id);
        return ResponseEntity.ok(talentManagerOutputDto);
    }

    @GetMapping("/userid/{userId}")
    public ResponseEntity<TalentManagerOutputDto> getTalentManagerByUserId(@PathVariable Long userId) {
        TalentManagerOutputDto talentManagerOutputDto = talentManagerService.getTalentManagerByUserId(userId);
        return ResponseEntity.ok(talentManagerOutputDto);
    }

    @PostMapping
    public ResponseEntity<TalentManagerOutputDto> createManager(@RequestBody TalentManagerInputDto talentManagerInputDto) {
        TalentManagerOutputDto talentManagerOutputDto = talentManagerService.createManager(talentManagerInputDto);
        return ResponseEntity.ok(talentManagerOutputDto);
    }

}
