package com.example.musicapp.Service_API;

import com.example.musicapp.DTO.PlaylistDTO;
import com.example.musicapp.DTO.SongDTO;
import com.example.musicapp.DTO.UserSignupDTO;
import com.example.musicapp.Model.MusicType;
import com.example.musicapp.Model.Playlist;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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
import retrofit2.http.Query;
public interface ApiService {
    // Tạo OkHttpClient và thiết lập thời gian chờ
    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build();

    String BASE_URL = "http://192.168.1.156:8080/api/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
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
    @GET("song/search")
    Call<List<Song>> findSongByName(@Query("q") String query);
    @GET("song/all/{musicTypeId}")
    Call<List<Song>> listSongType(@Path("musicTypeId") String musicTypeId);


    //    Playlist
    @GET("playlist/{id}")
    Call<Playlist> getPlaylist(@Path("id") String playlistId);
    @POST("playlist")
    Call<Playlist> createPlaylist(@Body PlaylistDTO playlistDTO);
    @PUT("playlist/{id}/add-song")
    Call<Playlist> addSongToPlaylist(@Path("id") String playlistId, @Body Song song);

    // Authentication
    @POST("auth/signup")
    Call<User> signUp(@Body UserSignupDTO userSignupDTO);
    @POST("auth/{email}/verify")
    Call<String> verifyOtp(@Path("email") String email, @Query("otp") String otp);

}
