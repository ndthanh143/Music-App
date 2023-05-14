package com.example.musicapp.DTO;

import com.example.musicapp.Model.User;

public class PlaylistDTO {
    private String name;

    private User user;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
