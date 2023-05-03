package com.novi.DemoDrop.models;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roleName;

    @OneToOne(mappedBy = "role")
    private User user;

    public Role() {

    }

    public Long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
