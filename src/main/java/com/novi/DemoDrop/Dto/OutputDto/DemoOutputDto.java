package com.novi.DemoDrop.Dto.OutputDto;

public class DemoOutputDto {
    private Long id;

    private String artistName;
    private String songName;
    private String email;
    private String fileName;
    private String songElaboration;
    private Long replyToDemoId;
    private Long DjId;

    public DemoOutputDto() {
    }

    public Long getId() {
        return id;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getSongName() {
        return songName;
    }

    public String getEmail() {
        return email;
    }


    public String getSongElaboration() {
        return songElaboration;
    }

    public Long getReplyToDemoId() {
        return replyToDemoId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setSongElaboration(String songElaboration) {
        this.songElaboration = songElaboration;
    }

    public void setReplyToDemoId(Long replyToDemoId) {
        this.replyToDemoId = replyToDemoId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getDjId() {
        return DjId;
    }

    public void setDjId(Long djId) {
        DjId = djId;
    }
}
