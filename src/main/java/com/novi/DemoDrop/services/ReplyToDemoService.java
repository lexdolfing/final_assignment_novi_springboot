package com.novi.DemoDrop.services;

import com.novi.DemoDrop.Dto.InputDto.ReplyToDemoInputDto;
import com.novi.DemoDrop.Dto.OutputDto.ReplyToDemoOutputDto;
import com.novi.DemoDrop.exceptions.RecordNotFoundException;
import com.novi.DemoDrop.models.Demo;
import com.novi.DemoDrop.models.ReplyToDemo;
import com.novi.DemoDrop.repositories.DemoRepository;
import com.novi.DemoDrop.repositories.ReplyToDemoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ReplyToDemoService {

    private final ReplyToDemoRepository replyToDemoRepository;
    private final DemoRepository demoRepository;

    public ReplyToDemoService(ReplyToDemoRepository replyToDemoRepository, DemoRepository demoRepository) {
        this.replyToDemoRepository = replyToDemoRepository;
        this.demoRepository = demoRepository;
    }


    public ReplyToDemoOutputDto getReplyById(Long id) {
        Optional<ReplyToDemo> replyToDemoOptional = replyToDemoRepository.findById(id);

        if (replyToDemoOptional.isEmpty()) {
            throw new RecordNotFoundException("No record found with this id");
        }
        ReplyToDemo r = replyToDemoOptional.get();
        return makeTheDto(r);
    }

    public ReplyToDemoOutputDto createReply(ReplyToDemoInputDto replyToDemoInputDto) {
        ReplyToDemo r = new ReplyToDemo();
        r = setOrUpdateReplyObject(replyToDemoInputDto, r);
        try {
            replyToDemoRepository.save(r);
            return makeTheDto(r);
        } catch (DataIntegrityViolationException e) {
            throw new RecordNotFoundException("Error saving demo to database");
        }
    }


//
//    public ReplyToDemoOutputDto createAndAssignReply(Long id, ReplyToDemoInputDto replyToDemoInputDto) {
//        ReplyToDemo r = new ReplyToDemo();
//        r = setOrUpdateReplyObject(replyToDemoInputDto, r);
//        Optional<Demo> optionalDemo = (demoRepository.findById(id));
//        if (optionalDemo.isPresent()) {
//            Demo d = optionalDemo.get();
//            replyToDemoRepository.save(r);
//            d.setReplyToDemo(r);
//            demoRepository.save(d);
//            return makeTheDto(r);
//        } else {
//            throw new RecordNotFoundException("No demo with this id found, cannot assign reply");
//        }
//    }

    public ReplyToDemoOutputDto updateReply(Long id, ReplyToDemoInputDto replyToDemoInputDto) {
        Optional<ReplyToDemo> replyToDemoOptional = replyToDemoRepository.findById(id);
        if (replyToDemoOptional.isPresent()) {
            ReplyToDemo r = replyToDemoOptional.get();
            r = setOrUpdateReplyObject(replyToDemoInputDto, r);
            replyToDemoRepository.save(r);
            return makeTheDto(r);
        } else {
            throw new RecordNotFoundException("No reply with this id was found");
        }
    }

    public boolean deleteReply(Long id) {
        if (replyToDemoRepository.findById(id).isPresent()) {
            replyToDemoRepository.deleteById(id);
            return true;
        } else {
            throw new RecordNotFoundException("No record found with this id");
        }

    }

    public ReplyToDemoOutputDto makeTheDto (ReplyToDemo r) {
        ReplyToDemoOutputDto replyToDemoOutputDto = new ReplyToDemoOutputDto();
        replyToDemoOutputDto.setId(r.getId());
        replyToDemoOutputDto.setHasBeenRepliedTo(r.isHasBeenRepliedTo());
        replyToDemoOutputDto.setAdminComments(r.getAdminComments());
        replyToDemoOutputDto.setAdminDecision(r.getAdminDecision());
        replyToDemoOutputDto.setDemo(r.getDemo());
        return replyToDemoOutputDto;
    }

    public ReplyToDemo setOrUpdateReplyObject (ReplyToDemoInputDto replyToDemoInputDto, ReplyToDemo r) {
        r.setAdminDecision(replyToDemoInputDto.getAdminDecision());
        r.setHasBeenRepliedTo(replyToDemoInputDto.isHasBeenRepliedTo());
        r.setAdminComments(replyToDemoInputDto.getAdminComments());
        r.setDemo(replyToDemoInputDto.getDemo());
        return r;
    }
}
