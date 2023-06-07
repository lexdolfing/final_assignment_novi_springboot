package com.novi.DemoDrop.Dto.OutputDto;

import com.novi.DemoDrop.models.DJ;
import com.novi.DemoDrop.models.Role;
import com.novi.DemoDrop.models.TalentManager;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;

public class UserOutputDto {

    Long id;
    private String email;
    private String jwtToken;
    private String password;


    private String roleName;


    public UserOutputDto() {
    }

    public Long getId() {
        return id;
    }


    public String getEmail() {
        return email;
    }

    public String getRoleName() {
        return roleName;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
