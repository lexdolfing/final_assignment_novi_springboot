package com.novi.DemoDrop.models;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    private String password;
    private String email;

    @OneToOne(mappedBy = "user")
    private Role role;

    @OneToOne(cascade = CascadeType.PERSIST)
    private DJ dj;
    @OneToOne(cascade = CascadeType.PERSIST)
    private TalentManager talentManager;


    public User() {
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

    public Role getRole() {
        return role;
    }

    public DJ getDj() {
        return dj;
    }

    public TalentManager getTalentManager() {
        return talentManager;
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

    public void setRole(Role role) {
        this.role = role;
    }

    public void setDj(DJ dj) {
        this.dj = dj;
    }

    public void setTalentManager(TalentManager talentManager) {
        this.talentManager = talentManager;
    }
}
