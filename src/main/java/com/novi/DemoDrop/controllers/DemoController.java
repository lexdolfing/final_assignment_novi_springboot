package com.novi.DemoDrop.controllers;

import com.novi.DemoDrop.Dto.InputDto.DemoInputDto;
import com.novi.DemoDrop.Dto.InputDto.IdInputDto;
import com.novi.DemoDrop.Dto.OutputDto.DemoOutputDto;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
import com.novi.DemoDrop.services.DemoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping
    public ResponseEntity<Object> createDemo(@RequestBody DemoInputDto demoInputDto) {
        DemoOutputDto demoOutputDto = demoService.createDemo(demoInputDto);
        return ResponseEntity.ok(demoOutputDto);
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
    @PutMapping("/{id}/reply-to-demo")
    public void assignRemoteControllerToTelevision (@PathVariable("id") Long id, @RequestBody IdInputDto input) {
        demoService.assignReplyToDemo(id, input.id);
    }


    // Nog toe te voegen requests:
    // update demo request (maar echt?)
    // get demo info (id, artistname and songname and email - voor de reactie) Maar misschien moet deze in de ReplyToDemoKlasse?
}
