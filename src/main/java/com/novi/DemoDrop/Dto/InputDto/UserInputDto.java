package com.novi.DemoDrop.Dto.InputDto;

import com.novi.DemoDrop.models.DJ;
import com.novi.DemoDrop.models.Role;
import com.novi.DemoDrop.models.TalentManager;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;

public class UserInputDto {
    private String password;
    private String email;
    public String apikey;

    public UserInputDto() {
    }


    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }



    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }
}
