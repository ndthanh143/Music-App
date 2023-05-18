package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.Adapter.PlaylistSongAdapter;
import com.example.musicapp.Model.Playlist;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.ApiService;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistAcitivity extends AppCompatActivity {
    private TextView tvPlaylistName, tvPlaylistSize;
    private Button btnAddSong, btnRandomPlay;
    private ImageButton btnAddSong2,ivback;
    private Playlist playlist;
    private ImageView ivPlaylisThumbnail;
    private ConstraintLayout layoutNoSongInPlaylist;
    private ConstraintLayout layoutHasSongInPlaylist;
    private RecyclerView rcListSong;
    private PlaylistSongAdapter playlistSongAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_acitivity);
        anhxa();
        Intent intent = getIntent();
        String playlistId = intent.getStringExtra("playlistId");

        CallApiGetPlaylist("64650aaf55d2e4242140219d");

        btnAddSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenAddSongActivity();
            }
        });
        btnAddSong2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenAddSongActivity();
            }
        });
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlaylistAcitivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnRandomPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();

                // Tạo số ngẫu nhiên trong khoảng từ 0 đến 100
                int randomNumber = random.nextInt(playlist.getListSongs().size()-1) + 0;
                String songId = playlist.getListSongs().get(randomNumber).getId();
                Intent intent = new Intent(PlaylistAcitivity.this, PlayMusicActivity.class);
                Bundle bundle = new Bundle();
                ArrayList<String> listSong= new ArrayList<>();
                for(int i = 0; i < playlist.getListSongs().size(); i++) {
                    listSong.add(playlist.getListSongs().get(i).getId());
                }
                bundle.putStringArrayList("listSong", listSong);
                bundle.putString("songId", songId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }

    private void OpenAddSongActivity() {
        Intent intent = new Intent(PlaylistAcitivity.this, AddSongToPlaylistActivity.class);
        intent.putExtra("playlistId", playlist.getId());
        startActivity(intent);
    }


    private void CallApiGetPlaylist(String playlistId) {
        System.out.println(playlistId);
        ApiService.apiService.getPlaylist(playlistId).enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                playlist = response.body();
                tvPlaylistName.setText(playlist.getName());
                if(playlist.getListSongs() != null) {
                    tvPlaylistSize.setText(playlist.getListSongs().size() + " Bài hát");
                    Glide.with(PlaylistAcitivity.this)
                            .load(playlist.getThumbnail())
                            .into(ivPlaylisThumbnail);
                    if(playlist.getListSongs().size() == 0) {
                        layoutHasSongInPlaylist.setVisibility(View.GONE);
                        layoutNoSongInPlaylist.setVisibility(View.VISIBLE);
                    } else {
                        layoutNoSongInPlaylist.setVisibility(View.GONE);
                        layoutHasSongInPlaylist.setVisibility(View.VISIBLE);
                        playlistSongAdapter = new PlaylistSongAdapter(PlaylistAcitivity.this, playlist.getListSongs());
                        rcListSong.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1);
                        rcListSong.setLayoutManager(layoutManager);
                        rcListSong.setAdapter(playlistSongAdapter);
                        playlistSongAdapter.notifyDataSetChanged();
                    }
                } else {
                    tvPlaylistSize.setText("");
                    layoutHasSongInPlaylist.setVisibility(View.GONE);
                    layoutNoSongInPlaylist.setVisibility(View.VISIBLE);
                }


            }

            @Override
            public void onFailure(Call<Playlist> call, Throwable t) {
                Toast.makeText(PlaylistAcitivity.this, "Call API Error" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhxa() {
        tvPlaylistName = (TextView) findViewById(R.id.tvPlaylistName);
        tvPlaylistSize = (TextView) findViewById(R.id.tvPlaylistSize);
        btnAddSong = (Button) findViewById(R.id.btnAddSong);
        btnAddSong2 = (ImageButton) findViewById(R.id.btnAddSongToPlaylist2);
        ivPlaylisThumbnail = (ImageView) findViewById(R.id.ivPlaylistDetailThumbnail);
        layoutNoSongInPlaylist = (ConstraintLayout) findViewById(R.id.layoutNoSongInPlaylist);
        layoutHasSongInPlaylist = (ConstraintLayout) findViewById(R.id.layoutHasSongInPlaylist);
        rcListSong = (RecyclerView) findViewById(R.id.rcListSongInPlaylist);
        ivback = findViewById(R.id.ivbackplaylist);
        btnRandomPlay = findViewById(R.id.button7);
    }
}