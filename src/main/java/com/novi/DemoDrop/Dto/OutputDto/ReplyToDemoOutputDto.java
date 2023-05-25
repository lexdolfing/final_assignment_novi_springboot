package com.novi.DemoDrop.Dto.OutputDto;

public class ReplyToDemoOutputDto {
    private Long id;
    private String adminDecision;
    private String adminComments;
    private boolean hasBeenRepliedTo;

    private Long demoId;

    private Long talentManagerId;

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

    public Long getDemoId() {
        return demoId;
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

    public void setDemoId(Long demoId) {
        this.demoId = demoId;
    }

    public Long getTalentManagerId() {
        return talentManagerId;
    }

    public void setTalentManagerId(Long talentManagerId) {
        this.talentManagerId = talentManagerId;
    }
}
