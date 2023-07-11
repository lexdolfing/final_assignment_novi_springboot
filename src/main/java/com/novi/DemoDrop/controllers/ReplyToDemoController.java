package com.novi.DemoDrop.controllers;

import com.novi.DemoDrop.Dto.InputDto.ReplyToDemoInputDto;
import com.novi.DemoDrop.Dto.OutputDto.ReplyToDemoOutputDto;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
import com.novi.DemoDrop.services.ReplyToDemoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("replies-to-demos")
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

    @PostMapping("/{id}")
    public ResponseEntity<Object> createAndAssignReply(@PathVariable Long id, @RequestBody ReplyToDemoInputDto replyToDemoInputDto) {
        ReplyToDemoOutputDto replyToDemoOutputDto = replyToDemoService.createAndAssignReply(id, replyToDemoInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(replyToDemoOutputDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReplyToDemoOutputDto> updateReply(@PathVariable Long id, @RequestBody ReplyToDemoInputDto replyToDemoInputDto) {
        ReplyToDemoOutputDto replyToDemoOutputDto = replyToDemoService.updateReply(id, replyToDemoInputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(replyToDemoOutputDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReply(@PathVariable Long id) {
        boolean isDeleted = replyToDemoService.deleteReply(id);
        if (isDeleted) {
            return  ResponseEntity.noContent().build();
        } else {
            throw new RecordNotFoundException("No record found with this id");
        }
    }

}
