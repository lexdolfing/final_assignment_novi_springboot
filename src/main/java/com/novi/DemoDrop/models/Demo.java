package com.novi.DemoDrop.models;


import com.novi.DemoDrop.Dto.OutputDto.FileUploadResponse;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Parameter;

//TO-DO
// 1. voeg oneToMany relatie toe met DJ
// 2. oneToOne relatie met ReplyToDemo
@Entity
@Table(name= "demos")
public class Demo {

    @Id
    @GeneratedValue(generator = "sequence-generator")
    @GenericGenerator(
            name = "sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "user_sequence"),
                    @Parameter(name = "initial_value", value = "3"),
                    @Parameter(name = "increment_size", value = "1")
            }
    )
    private Long id;
    @Column(name= "artist_name")
    private String artistName;
    @Column(name= "song_name")
    private String songName;
    private String email;
    @Column(name= "mp3_file")
    private String fileName;
    @Column(name= "song_elaboration")
    private String songElaboration;

    @OneToOne(cascade = CascadeType.PERSIST)
    private ReplyToDemo replyToDemo;
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private DJ dj;
    @ManyToOne
    @LazyCollection(LazyCollectionOption.FALSE)
    private TalentManager talentManager;




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



    public void setSongElaboration(String songElaboration) {
        this.songElaboration = songElaboration;
    }

    public void setReplyToDemo(ReplyToDemo replyToDemo) {
        this.replyToDemo = replyToDemo;
    }

    public DJ getDj() {
        return dj;
    }

    public void setDj(DJ dj) {
        this.dj = dj;
    }

    public TalentManager getTalentManager() {
        return talentManager;
    }

    public void setTalentManager(TalentManager talentManager) {
        this.talentManager = talentManager;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
