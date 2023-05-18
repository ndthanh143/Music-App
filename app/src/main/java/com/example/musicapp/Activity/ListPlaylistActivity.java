package com.example.musicapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.Adapter.ListPlaylistAdapter;
import com.example.musicapp.Adapter.MusicTypeAdapter;
import com.example.musicapp.Adapter.PlaylistSongAdapter;
import com.example.musicapp.DTO.SongDTO;
import com.example.musicapp.Model.MusicType;
import com.example.musicapp.Model.Playlist;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPlaylistActivity extends AppCompatActivity {
    private List<Playlist> mListPlaylist;

    private RecyclerView rcPlaylist;

    private RecyclerView.Adapter adapter, adapter2;
    ApiService apiService;
    private ImageButton ivbackplaylist1;
    private Button btnaddPlaylist;
    Playlist playlist;
    LinearLayout layoutHasPlaylist;
    ConstraintLayout layoutNoPlaylist;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_playlist);
        ivbackplaylist1 = findViewById(R.id.ivbackplaylist1);
        btnaddPlaylist =findViewById(R.id.btnaddPlaylist);
        layoutNoPlaylist = findViewById(R.id.layoutNoPlaylist);
        layoutHasPlaylist = findViewById(R.id.LayoutHasPlaylist);
        ivbackplaylist1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListPlaylistActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        btnaddPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListPlaylistActivity.this,CreatePlaylistActivity.class);
                startActivity(intent);
            }
        });
        CallApiGetAllPlaylist();
    }

    private void CallApiGetAllPlaylist() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcPlaylist = findViewById(R.id.rc_list_playlist);
        rcPlaylist.setLayoutManager(linearLayoutManager);
        ApiService.apiService.getAllPlaylist().enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response) {

//                rcPlaylist.setHasFixedSize(true);
//                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
//                rcPlaylist.setLayoutManager(layoutManager);
                if(response.isSuccessful()) {
                    mListPlaylist = response.body();
//                    int i =playlist.getListSongs().size();
                    int i=0;
                    if(i == 0) {
                        layoutHasPlaylist.setVisibility(View.GONE);
                        layoutNoPlaylist.setVisibility(View.VISIBLE);
                    }
                    else{
                        layoutHasPlaylist.setVisibility(View.VISIBLE);
                        layoutNoPlaylist.setVisibility(View.GONE);
                    ListPlaylistAdapter playlistAdapter = new ListPlaylistAdapter(ListPlaylistActivity.this,mListPlaylist);
                    rcPlaylist.setAdapter(playlistAdapter);
                    playlistAdapter.notifyDataSetChanged();
                    System.out.println("Get dc playlist");}
                }else{
                    System.out.println("Khong get duoc playlist");
                }
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t) {
                Log.d("logg",t.getMessage());
                System.out.println("Khong get duoc playlist");
            }
        });
    }
}
