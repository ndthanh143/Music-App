package com.example.musicapp.Model;

public class Song {
    private String id;

    private String name;

    private String artist;

    private String songUrl;

    private String imageSongUrl;

    private String lyrics;

    private MusicType type;

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

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getImageSongUrl() {
        return imageSongUrl;
    }

    public void setImageSongUrl(String imageSongUrl) {
        this.imageSongUrl = imageSongUrl;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public MusicType getType() {
        return type;
    }

    public void setType(MusicType type) {
        this.type = type;
    }
}
