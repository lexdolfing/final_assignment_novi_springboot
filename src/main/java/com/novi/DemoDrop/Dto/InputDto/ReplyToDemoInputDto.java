package com.novi.DemoDrop.Dto.InputDto;


import org.jetbrains.annotations.NotNull;

public class ReplyToDemoInputDto {
    @NotNull
    private String adminDecision;
    @NotNull
    private String adminComments;
    @NotNull
    private boolean hasBeenRepliedTo;
    @NotNull
    private Long talentManagerId;
    @NotNull
    private Long demoId;

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

    public Long getDemoId() {
        return demoId;
    }

    public void setDemoId(Long demoId) {
        this.demoId = demoId;
    }

    public Long getTalentManagerId() {
        return talentManagerId;
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


    public void setTalentManagerId(Long talentManagerId) {
        this.talentManagerId = talentManagerId;
    }
}
