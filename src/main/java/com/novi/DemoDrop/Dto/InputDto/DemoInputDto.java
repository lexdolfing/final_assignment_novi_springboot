package com.novi.DemoDrop.Dto.InputDto;

import jakarta.validation.constraints.Email;
import org.jetbrains.annotations.NotNull;

public class DemoInputDto {

    @NotNull
    private String artistName;
    @NotNull
    private String songName;
    @NotNull
    @Email
    private String email;
    private String fileName;
    private String songElaboration;
    @NotNull
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
}
