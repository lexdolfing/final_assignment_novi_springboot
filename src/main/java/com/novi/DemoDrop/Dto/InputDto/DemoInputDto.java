package com.novi.DemoDrop.Dto.InputDto;

import com.novi.DemoDrop.models.ReplyToDemo;

public class DemoInputDto {

    private String artistName;
    private String songName;
    private String email;
    private String fileName;
    private String songElaboration;
    private Long djId;



    public DemoInputDto() {
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

    public Long getDjId() {
        return djId;
    }

    public void setDjId(Long djId) {
        this.djId = djId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
