package com.example.musicapp.Model;

import java.util.List;

public class Playlist {
    private String id, name, thumbnail;
    private List<Song> listSongs;
    private User user;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public List<Song> getListSongs() {
        return listSongs;
    }

    public void setListSongs(List<Song> listSongs) {
        this.listSongs = listSongs;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Playlist(String id, String name, String thumbnail, List<Song> listSongs, User user) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
        this.listSongs = listSongs;
        this.user = user;
    }
}
