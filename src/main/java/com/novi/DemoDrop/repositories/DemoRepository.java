package com.novi.DemoDrop.repositories;

import com.novi.DemoDrop.models.Demo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoRepository extends JpaRepository<Demo, Long> {
}
