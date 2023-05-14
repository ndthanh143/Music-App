package com.example.musicapp.Service_API;

import com.example.musicapp.Model.MusicType;
import com.example.musicapp.Model.Song;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface ApiService {
    String BASE_URL = "http://192.168.1.7:8081/api/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    public static DataService getService(){
        return RetrofitClient.getClient(BASE_URL).create(DataService.class);
    }

    @GET("music-type/all")
    Call<List<MusicType>> getListMusicTypes();
    @GET("song/all")
    Call<List<Song>> getListMusicSong();
    @GET("song/random")
    Call<List<Song>> getListRandomSong();

}
