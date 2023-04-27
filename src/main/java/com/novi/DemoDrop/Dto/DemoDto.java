package com.novi.DemoDrop.Dto;

import jakarta.persistence.Column;

public class DemoDto {
    private Long id;

    private String artistName;
    private String songName;
    private String email;
    private byte[] mp3File;
    private String songElaboration;

    public DemoDto() {
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

    public byte[] getMp3File() {
        return mp3File;
    }

    public String getSongElaboration() {
        return songElaboration;
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

    public void setMp3File(byte[] mp3File) {
        this.mp3File = mp3File;
    }

    public void setSongElaboration(String songElaboration) {
        this.songElaboration = songElaboration;
    }
}
