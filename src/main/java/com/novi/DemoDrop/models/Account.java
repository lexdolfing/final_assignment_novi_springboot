package com.novi.DemoDrop.models;

import jakarta.persistence.*;
@MappedSuperclass
public class Account {
    protected String firstName;
    protected String lastName;

    @OneToOne
    User user;


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public User getUser() {
        return user;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
