package com.novi.DemoDrop.Dto.InputDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.NotNull;

public class TalentManagerInputDto {
    @NotNull
    private String managerName;
    @NotNull
    protected String firstName;
    @NotNull
    protected String lastName;
    @NotNull
    @Email
    private String email;
    @NotNull
    @Size(min = 6)
    private String password;

    public TalentManagerInputDto() {
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
