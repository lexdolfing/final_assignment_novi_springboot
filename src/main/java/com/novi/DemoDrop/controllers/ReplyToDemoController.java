package com.novi.DemoDrop.controllers;

import com.novi.DemoDrop.Dto.InputDto.ReplyToDemoInputDto;
import com.novi.DemoDrop.Dto.OutputDto.ReplyToDemoOutputDto;
import com.novi.DemoDrop.services.ReplyToDemoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("reply-to-demo")
@RestController
public class ReplyToDemoController {

    private final ReplyToDemoService replyToDemoService;

    public ReplyToDemoController(ReplyToDemoService replyToDemoService) {
        this.replyToDemoService = replyToDemoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReplyToDemoOutputDto> getReplyById(@PathVariable Long id){
        ReplyToDemoOutputDto replyToDemoOutputDto = new ReplyToDemoOutputDto();
        replyToDemoOutputDto = replyToDemoService.getReplyById(id);
        return ResponseEntity.ok(replyToDemoOutputDto);
    }

    @PostMapping
    public ResponseEntity<Object> createReply(@RequestBody ReplyToDemoInputDto replyToDemoInputDto) {
        ReplyToDemoOutputDto replyToDemoOutputDto = replyToDemoService.createReply(replyToDemoInputDto);
        return ResponseEntity.ok(replyToDemoOutputDto);
    }

    //Welke requests gaan hiervoor nodig zijn?
    // 1. v get request voor de DJ (en voor admin?)
    // 2. Post request voor admin
    // 3. update request voor admin
    // 4. delete request voor admin
    // 5. Moet er een algemene get request komen? Volgens mij niet


}