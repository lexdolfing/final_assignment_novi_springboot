package com.novi.DemoDrop.Dto.OutputDto;

import com.novi.DemoDrop.models.Demo;

public class ReplyToDemoOutputDto {
    private Long id;
    private String adminDecision;
    private String adminComments;
    private boolean hasBeenRepliedTo;
    private Demo demo;

    public ReplyToDemoOutputDto() {
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
