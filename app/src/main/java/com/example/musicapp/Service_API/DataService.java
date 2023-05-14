package com.example.musicapp.Service_API;

import com.example.musicapp.Model.Song;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {
    @GET("song/random")
    Call<List<Song>> getListRandomSong();

}
