package com.example.musicapp.Service_API;

import com.example.musicapp.DTO.PlaylistDTO;
import com.example.musicapp.DTO.SongDTO;
import com.example.musicapp.DTO.UserDTO;
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
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
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

    String BASE_URL = "http://192.168.82.254:8080/api/";

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
    Call<List<Song>> getListMusicSong();

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
    @GET("playlist/all")
    Call<List<Playlist>> getAllPlaylist();
    @GET("playlist/all/{userId}")
    Call<List<Playlist>> getPlaylistOfUser(@Path("userId") String userId);
    @POST("playlist")
    Call<Playlist> createPlaylist(@Body PlaylistDTO playlistDTO, @Query("userId") String userId);
    @PUT("playlist/{id}/add-song")
    Call<Playlist> addSongToPlaylist(@Path("id") String playlistId, @Body Song song);
    @PUT("playlist/{id}/remove-song")
    Call<Playlist> removeSongFromPlaylist(@Path("id") String playlistId, @Body Song song);
    @PUT("playlist/{id}")
    Call<Playlist> update(@Path("id") String playlistId, PlaylistDTO playlistDTO);
    @DELETE("playlist/{id}")
    Call<Playlist> deletePlaylist(@Path("id") String id);

    // Authentication
    @POST("auth/signup")
    Call<User> signUp(@Body UserSignupDTO userSignupDTO);
    @POST("auth/{email}/verify")
    Call<String> verifyOtp(@Path("email") String email, @Query("otp") String otp);

    @POST("auth/login")
    Call<User> login(@Query("email") String email, @Query("password") String password);

    //User
    @PUT("user/{id}")
    Call<User> updateName(@Path("id") String userId, @Body UserSignupDTO user);

    @PUT("user/update-password/{id}")
    Call<User> changePassword(@Path("id") String userId, @Query("oldPassword") String oldPass, @Query("newPassword") String newPass);
}
