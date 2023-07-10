package com.novi.DemoDrop.repositories;

import com.novi.DemoDrop.models.TalentManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TalentManagerRepository extends JpaRepository <TalentManager, Long> {
    Optional<TalentManager> findByUserId(Long userId);
}
