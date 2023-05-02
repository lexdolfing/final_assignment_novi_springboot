package com.novi.DemoDrop.models;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class DJ extends Account{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //dit werkt niet? :P

    private String artistName;
    @OneToOne(mappedBy = "dj")
    private User user;

    @OneToMany(mappedBy="dj")
    private List<Demo> listOfDemos;

    public DJ() {
        Role role = new Role();
        role.setRoleName("ROLE_USER");
    }

    public Long getId() {
        return id;
    }

    public String getArtistName() {
        return artistName;
    }

    public User getUser() {
        return user;
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

    public void setUser(User user) {
        this.user = user;
    }

    public void setListOfDemos(List<Demo> listOfDemos) {
        this.listOfDemos = listOfDemos;
    }
}
