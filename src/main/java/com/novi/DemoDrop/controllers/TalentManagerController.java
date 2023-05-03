package com.novi.DemoDrop.controllers;

import com.novi.DemoDrop.services.TalentManagerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("talentmanager")
@RestController
public class TalentManagerController {
    private final TalentManagerService talentManagerService;

    public TalentManagerController(TalentManagerService talentManagerService) {
        this.talentManagerService = talentManagerService;
    }
}
