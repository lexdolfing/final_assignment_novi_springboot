package com.novi.DemoDrop.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class TalentManager extends Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String managerName;

    @OneToMany(mappedBy = "talentManager")
    private List<Demo> assignedDemos;

    @OneToOne(mappedBy = "talentManager")
    private User user;

    public TalentManager() {
    }

    public Long getId() {
        return id;
    }

    public String getManagerName() {
        return managerName;
    }

    public List<Demo> getAssignedDemos() {
        return assignedDemos;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public void setAssignedDemos(List<Demo> assignedDemos) {
        this.assignedDemos = assignedDemos;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
