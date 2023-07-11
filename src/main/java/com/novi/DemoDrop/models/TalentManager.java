package com.novi.DemoDrop.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="talentmanagers")
public class TalentManager extends Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String managerName;

    public TalentManager() {
    }

    public Long getId() {
        return id;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

}
