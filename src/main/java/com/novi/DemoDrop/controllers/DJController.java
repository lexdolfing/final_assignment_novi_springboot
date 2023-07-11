package com.novi.DemoDrop.controllers;

import com.novi.DemoDrop.Dto.InputDto.DJAccountInputDto;
import com.novi.DemoDrop.Dto.InputDto.UserInputDto;
import com.novi.DemoDrop.Dto.OutputDto.DJAccountOutputDto;
import com.novi.DemoDrop.Dto.OutputDto.DemoOutputDto;
import com.novi.DemoDrop.services.DJService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RequestMapping("djs")
@RestController
public class DJController {

    private final DJService djService;
    private final UserController userController;

    public DJController(DJService djService, UserController userController) {
        this.djService = djService;
        this.userController = userController;
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

    @GetMapping("/{djId}/mydemos")
    public ResponseEntity<List<DemoOutputDto>> getAllMyDemos(@PathVariable Long djId) {
        List<DemoOutputDto> demoOutputDtos = djService.getAllMyDemos(djId);
        return ResponseEntity.ok(demoOutputDtos);

    }

    @PostMapping
    public ResponseEntity<DJAccountOutputDto> createDJ(@RequestBody DJAccountInputDto djAccountInputDto) {
        UserInputDto userInputDto = new UserInputDto();
        userInputDto.setEmail(djAccountInputDto.getEmail());
        userInputDto.setPassword(djAccountInputDto.getPassword());
        userController.createUser(userInputDto);
        DJAccountOutputDto djAccountOutputDto = djService.createDJ(djAccountInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(djAccountOutputDto);
    }

}
