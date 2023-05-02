package com.novi.DemoDrop.repositories;

import com.novi.DemoDrop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {

}
