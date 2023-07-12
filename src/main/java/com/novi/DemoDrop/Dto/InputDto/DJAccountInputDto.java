package com.novi.DemoDrop.Dto.InputDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.jetbrains.annotations.NotNull;

public class DJAccountInputDto {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String artistName;
    @NotNull
    @Size(min = 6)
    private String password;
    @NotNull
    @Email
    private String email;

    public DJAccountInputDto() {
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

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
