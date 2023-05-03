package com.novi.DemoDrop.Dto.InputDto;

import com.novi.DemoDrop.models.ReplyToDemo;

public class DemoInputDto {

    private String artistName;
    private String songName;
    private String email;
    private byte[] mp3File;
    private String songElaboration;



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

    public byte[] getMp3File() {
        return mp3File;
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

    public void setMp3File(byte[] mp3File) {
        this.mp3File = mp3File;
    }

    public void setSongElaboration(String songElaboration) {
        this.songElaboration = songElaboration;
    }
}
