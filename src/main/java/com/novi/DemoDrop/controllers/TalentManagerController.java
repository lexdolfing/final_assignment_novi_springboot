package com.novi.DemoDrop.controllers;

import com.novi.DemoDrop.Dto.InputDto.TalentManagerInputDto;
import com.novi.DemoDrop.Dto.InputDto.UserInputDto;
import com.novi.DemoDrop.Dto.OutputDto.DemoOutputDto;
import com.novi.DemoDrop.Dto.OutputDto.TalentManagerOutputDto;
import com.novi.DemoDrop.models.Demo;
import com.novi.DemoDrop.services.TalentManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RequestMapping("talentmanagers")
@RestController
public class TalentManagerController {
    private final TalentManagerService talentManagerService;
    private final UserController userController;

    public TalentManagerController(TalentManagerService talentManagerService, UserController userController) {
        this.talentManagerService = talentManagerService;
        this.userController = userController;
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

    @GetMapping("/{talentManagerId}/assigned-demos")
    public ResponseEntity<List<DemoOutputDto>> getDemosByTalentManager(@PathVariable Long talentManagerId){
        List<DemoOutputDto> demoOutputDtos = talentManagerService.getDemosByTalentManager(talentManagerId);
        return ResponseEntity.ok(demoOutputDtos);
    }


    @PostMapping
    public ResponseEntity<TalentManagerOutputDto> createManager(@RequestBody TalentManagerInputDto talentManagerInputDto) {
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setEmail(talentManagerInputDto.getEmail());
        userInputDto.setPassword(talentManagerInputDto.getPassword());
        userController.createUser(userInputDto);
        TalentManagerOutputDto talentManagerOutputDto = talentManagerService.createManager(talentManagerInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(talentManagerOutputDto);
    }

}
