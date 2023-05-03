package com.novi.DemoDrop.models;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class DJ extends Account{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String artistName;

    @OneToMany(mappedBy="dj")
    private List<Demo> listOfDemos;

    public DJ() {
    }

    public Long getId() {
        return id;
    }

    public String getArtistName() {
        return artistName;
    }

    public List<Demo> getListOfDemos() {
        return listOfDemos;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setListOfDemos(List<Demo> listOfDemos) {
        this.listOfDemos = listOfDemos;
    }

    public void setUser(User user) {
        super.setUser(user);
    }

}
