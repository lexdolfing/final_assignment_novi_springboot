package com.novi.DemoDrop.models;

import jakarta.persistence.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import java.util.List;
@Entity
public class DJ extends Account{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String artistName;


    public DJ() {
    }

    public Long getId() {
        return id;
    }

    public String getArtistName() {
        return artistName;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }


}
