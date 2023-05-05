package com.novi.DemoDrop.models;

import jakarta.persistence.*;

// To-Do:
// 1. Voeg oneToOne relatie toe met Demo
// 2. Voeg relatie toe met Admin (oneToMany)

@Entity
@Table(name="demo_replies")
public class ReplyToDemo {
    @Id
    @GeneratedValue
    private Long id;
    private String adminDecision;
    private String adminComments;
    private boolean hasBeenRepliedTo;

    @OneToOne(mappedBy = "replyToDemo")
    private Demo demo;

    @ManyToOne
    private TalentManager talentManager;


    public ReplyToDemo() {
    }

    public Long getId() {
        return id;
    }

    public String getAdminDecision() {
        return adminDecision;
    }

    public String getAdminComments() {
        return adminComments;
    }

    public boolean isHasBeenRepliedTo() {
        return hasBeenRepliedTo;
    }

    public Demo getDemo() {
        return demo;
    }

    public TalentManager getTalentManager() {
        return talentManager;
    }

    public void setTalentManager(TalentManager talentManager) {
        this.talentManager = talentManager;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setAdminDecision(String adminDecision) {
        this.adminDecision = adminDecision;
    }

    public void setAdminComments(String adminComments) {
        this.adminComments = adminComments;
    }

    public void setHasBeenRepliedTo(boolean hasBeenRepliedTo) {
        this.hasBeenRepliedTo = hasBeenRepliedTo;
    }

    public void setDemo(Demo demo) {
        this.demo = demo;
    }
}
