package com.novi.DemoDrop.repositories;

import com.novi.DemoDrop.models.Demo;
import com.novi.DemoDrop.models.TalentManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemoRepository extends JpaRepository<Demo, Long> {
    List<Demo> findAllDemosByTalentManager(TalentManager talentManager);
}
