package com.novi.DemoDrop.repositories;

import com.novi.DemoDrop.models.DJ;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DJRepository extends JpaRepository <DJ, Long> {
    DJ findByUserId(Long userId);
}
