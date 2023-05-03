package com.novi.DemoDrop.services;

import com.novi.DemoDrop.repositories.TalentManagerRepository;
import com.novi.DemoDrop.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class TalentManagerService {
    private final TalentManagerRepository talentManagerRepository;
    private final UserRepository userRepository;
    public TalentManagerService(TalentManagerRepository talentManagerRepository, UserRepository userRepository) {
        this.talentManagerRepository = talentManagerRepository;
        this.userRepository = userRepository;
    }


}
