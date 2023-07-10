package com.novi.DemoDrop.repositories;

import com.novi.DemoDrop.models.DJ;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DJRepository extends JpaRepository <DJ, Long> {
    Optional<DJ> findByUserId(Long userId);
}
