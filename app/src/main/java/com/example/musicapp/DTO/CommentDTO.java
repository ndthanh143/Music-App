package com.example.musicapp.DTO;

import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.User;

import java.util.Date;

public class CommentDTO {
    private String comment;
    private Song song;
    private UserDTO user;


    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

}
