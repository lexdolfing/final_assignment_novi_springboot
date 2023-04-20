package com.novi.DemoDrop.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// To-Do:
// 1. Voeg oneToMany Relatie toe met DJ, of oneToOne met Demo
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
}
