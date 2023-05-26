package com.novi.DemoDrop.repositories;

import com.novi.DemoDrop.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository <Role, Long> {
    Role findByRoleName (String roleName);
}
