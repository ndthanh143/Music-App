package com.example.musicapp.Service_API;

import com.example.musicapp.DTO.PlaylistDTO;
import com.example.musicapp.DTO.SongDTO;
import com.example.musicapp.Model.MusicType;
import com.example.musicapp.Model.Playlist;
import com.example.musicapp.Model.Song;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    String BASE_URL = "http://192.168.1.184:8080/api/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);


    @GET("music-type/all")
    Call<List<MusicType>> getListMusicTypes();
    @GET("song/all")
    Call<List<SongDTO>> getListMusicSong();
    @GET("song/random")
    Call<List<Song>> getListRandomSong();

    @GET("song/all")
    Call<List<Song>> getAllSong();
    @GET("song/{id}")
    Call<Song> getSongId(@Path("id") String songId);


    //    Playlist
    @GET("playlist/{id}")
    Call<Playlist> getPlaylist(@Path("id") String playlistId);
    @POST("playlist")
    Call<Playlist> createPlaylist(@Body PlaylistDTO playlistDTO);
    @PUT("playlist/{id}/add-song")
    Call<Playlist> addSongToPlaylist(@Path("id") String playlistId, @Body Song song);
}
