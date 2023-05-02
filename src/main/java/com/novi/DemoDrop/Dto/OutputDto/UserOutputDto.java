package com.novi.DemoDrop.Dto.OutputDto;

import com.novi.DemoDrop.models.DJ;
import com.novi.DemoDrop.models.Role;
import com.novi.DemoDrop.models.TalentManager;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;

public class UserOutputDto {

    Long id;
    private String password;
    private String email;


    private String roleName;

    private Long djId;
    private Long talentManagerId;

    public UserOutputDto() {
    }

    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getRoleName() {
        return roleName;
    }

    public Long getDjId() {
        return djId;
    }

    public Long getTalentManagerId() {
        return talentManagerId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setDjId(Long djId) {
        this.djId = djId;
    }

    public void setTalentManagerId(Long talentManagerId) {
        this.talentManagerId = talentManagerId;
    }
}
