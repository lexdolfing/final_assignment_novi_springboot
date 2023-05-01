package com.novi.DemoDrop.controllers;

import com.novi.DemoDrop.Dto.InputDto.ReplyToDemoInputDto;
import com.novi.DemoDrop.Dto.OutputDto.ReplyToDemoOutputDto;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
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

    // The method below creates a Reply and assigns it immediately to the demo with id in the url
    @PostMapping("/{id}")
    public ResponseEntity<Object> createAndAssignReply(@PathVariable Long id, @RequestBody ReplyToDemoInputDto replyToDemoInputDto) {
        ReplyToDemoOutputDto replyToDemoOutputDto = replyToDemoService.createAndAssignReply(id, replyToDemoInputDto);
        return ResponseEntity.ok(replyToDemoOutputDto);
    }

    // This method assigns an existing reply to an existing demo.
    @PutMapping("/{id}")
    public ResponseEntity<ReplyToDemoOutputDto> updateReply(@PathVariable Long id, @RequestBody ReplyToDemoInputDto replyToDemoInputDto) {
        ReplyToDemoOutputDto replyToDemoOutputDto = replyToDemoService.updateReply(id, replyToDemoInputDto);
        return ResponseEntity.ok().body(replyToDemoOutputDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteReply(@PathVariable Long id) {
        boolean isDeleted = replyToDemoService.deleteReply(id);
        if (isDeleted) {
            return  ResponseEntity.ok().body("Element is deleted");
        } else {
            throw new RecordNotFoundException("No record found with this id");
        }
    }



    //Welke requests gaan hiervoor nodig zijn?
    // 1. v get request voor de DJ (en voor admin?)
    // 2. v Post request voor admin
    // 3. v update request voor admin
    // 4. v delete request voor admin
    // 5. Moet er een algemene get request komen? Volgens mij niet


}
