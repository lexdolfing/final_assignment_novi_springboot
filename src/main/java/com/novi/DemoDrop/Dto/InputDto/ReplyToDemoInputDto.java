package com.novi.DemoDrop.Dto.InputDto;

import com.novi.DemoDrop.models.Demo;
import com.novi.DemoDrop.models.ReplyToDemo;

public class ReplyToDemoInputDto {
    private String adminDecision;
    private String adminComments;
    private boolean hasBeenRepliedTo;


    private Demo demo;

    public ReplyToDemoInputDto() {
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
