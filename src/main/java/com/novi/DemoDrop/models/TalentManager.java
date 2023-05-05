package com.novi.DemoDrop.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="talentmanagers")
public class TalentManager extends Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String managerName;

    @OneToMany(mappedBy = "talentManager")
    private List<Demo> assignedDemos;

    @OneToMany(mappedBy="talentManager")
    private List<ReplyToDemo> listOfReplies;

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

    public List<ReplyToDemo> getListOfReplies() {
        return listOfReplies;
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

    public void setListOfReplies(List<ReplyToDemo> listOfReplies) {
        this.listOfReplies = listOfReplies;
    }

    public void addReplyToListOfReplies(ReplyToDemo replyToDemo) {
        this.listOfReplies.add(replyToDemo);
    }

    public void addDemoToListOfAssignedDemos(Demo demo) {
        this.assignedDemos.add(demo);
    }
}
