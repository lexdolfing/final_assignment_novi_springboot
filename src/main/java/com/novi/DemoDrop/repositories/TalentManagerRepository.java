package com.novi.DemoDrop.repositories;

import com.novi.DemoDrop.models.Demo;
import com.novi.DemoDrop.models.TalentManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TalentManagerRepository extends JpaRepository <TalentManager, Long> {
    Optional<TalentManager> findByUserId(Long userId);
}
