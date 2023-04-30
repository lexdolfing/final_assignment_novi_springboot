package com.novi.DemoDrop.models;

import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

//TO-DO
// 1. voeg oneToMany relatie toe met DJ
// 2. oneToOne relatie met ReplyToDemo
@Entity
@Table (name= "demos")
public class Demo {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name= "artist_name")
    private String artistName;
    @Column(name= "song_name")
    private String songName;
    @Column(name= "email")
    private String email;
    @Column(name= "mp3_file")
    private byte[] mp3File;
    @Column(name= "song_elaboration")
    private String songElaboration;

    @OneToOne(cascade = CascadeType.PERSIST)
    private ReplyToDemo replyToDemo;

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

    public ReplyToDemo getReplyToDemo() {
        return replyToDemo;
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

    public void setReplyToDemo(ReplyToDemo replyToDemo) {
        this.replyToDemo = replyToDemo;
    }
}
