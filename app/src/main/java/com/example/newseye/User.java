package com.example.newseye;

import android.content.Context;

import java.io.Serializable;

public class User implements Serializable {
    private static User user;
    String name,userDP,department;
    long artReads,artShared,favorites;

    public User() {}
    public User(String name, String userDP, String department, long artReads, long artShared, long favorites) {
        this.name = name;
        this.userDP = userDP;
        this.department = department;
        this.artReads = artReads;
        this.artShared = artShared;
        this.favorites = favorites;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserDP() {
        return userDP;
    }

    public void setUserDP(String userDP) {
        this.userDP = userDP;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public long getArtReads() {
        return artReads;
    }

    public void setArtReads(long artReads) {
        this.artReads = artReads;
    }

    public long getArtShared() {
        return artShared;
    }

    public void setArtShared(long artShared) {
        this.artShared = artShared;
    }

    public long getFavorites() {
        return favorites;
    }

    public void setFavorites(long favorites) {
        this.favorites = favorites;
    }
}
