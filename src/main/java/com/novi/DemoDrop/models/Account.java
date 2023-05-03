package com.novi.DemoDrop.models;

import jakarta.persistence.*;
@MappedSuperclass
public class Account {
    private String firstName;
    private String lastName;

    @OneToOne
    private User user;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
