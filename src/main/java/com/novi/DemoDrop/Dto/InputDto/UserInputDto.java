package com.novi.DemoDrop.Dto.InputDto;

import com.novi.DemoDrop.models.DJ;
import com.novi.DemoDrop.models.Role;
import com.novi.DemoDrop.models.TalentManager;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;

public class UserInputDto {
    private String password;
    private String email;
    private String roleName;
    public String apikey;

    public UserInputDto() {
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


    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }
}
