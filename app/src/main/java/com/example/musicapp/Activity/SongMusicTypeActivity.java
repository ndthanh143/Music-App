package com.example.musicapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.Adapter.BaiHatAdapter;
import com.example.musicapp.Adapter.SongMusicTypeAdapter;
import com.example.musicapp.DTO.SongDTO;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SongMusicTypeActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter, adapter2;
    ApiService apiService;
    private List<Song> mListSong=new ArrayList<>();

    private RecyclerView rcMusicType,rcSong;
    private String musicTypeId;
    ImageView ivMusicTypeDetailThumbnail,iv_back;
    TextView tvMusicTypeName,tvMusicTypeSize;
    Song song;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_music_type);
        ivMusicTypeDetailThumbnail= findViewById(R.id.ivMusicTypeDetailThumbnail);
        tvMusicTypeName = findViewById(R.id.tvMusicTypeName);
        tvMusicTypeSize  = findViewById(R.id.tvMusicTypeSize);
        iv_back = findViewById(R.id.imageView12);
        Intent i = getIntent();
        musicTypeId = i.getStringExtra("musicTypeId");
        tvMusicTypeName.setText(i.getStringExtra("musicTypeName"));
        Glide.with(SongMusicTypeActivity.this)
                .load(i.getStringExtra("musicTypeImage"))
                .into(ivMusicTypeDetailThumbnail);
        RecyclerViewListSong(musicTypeId);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1= new Intent(SongMusicTypeActivity.this,MainActivity.class);
                startActivity(intent1);
            }
        });
    }
    private void RecyclerViewListSong(String id) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcSong = findViewById(R.id.rcListSongInMusicType);
        rcSong.setLayoutManager(linearLayoutManager);
        //Get API
        ApiService.apiService.listSongType(id).enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if(response.isSuccessful()){
                    mListSong=response.body();
                    adapter2 = new SongMusicTypeAdapter(SongMusicTypeActivity.this, mListSong);
                    tvMusicTypeSize.setText(mListSong.size() + " bài hát");
                    rcSong.setAdapter(adapter2);
                    System.out.println("Lay duoc respone trong song music");
                }else{
                    int statusCode=response.code();
                    System.out.println("Khong Lay duoc respone trong song music");

                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.d("logg",t.getMessage());
                System.out.println("No Database");
            }
        });

    }

}
