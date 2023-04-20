package com.novi.DemoDrop.models;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

//TO-DO
// 1. voeg oneToMany relatie toe met DJ
// 2. afhankelijk van UML diagram besluit: oneToOne relatie met ReplyToDemo
@Entity
@Table (name= "demos")
public class Demo {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name= "artist_name", nullable = false)
    private String artistName;
    @Column(name= "song_name", nullable = false)
    private String songName;
    @Column(name= "email", nullable = false)
    private String email;
    @Column(name= "mp3_file", nullable = false)
    private byte[] mp3File;
    private String songElaboration;

    public Demo() {}

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
