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

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistAcitivity extends AppCompatActivity {
    private TextView tvPlaylistName, tvPlaylistSize;
    private Button btnAddSong;
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

        CallApiGetPlaylist("645fc4e8c5607734a6853839");

        btnAddSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlaylistAcitivity.this, AddSongToPlaylistActivity.class);
                intent.putExtra("playlistId", playlist.getId());
                startActivity(intent);
            }
        });
    }

    private void CallApiGetPlaylist(String playlistId) {
        ApiService.apiService.getPlaylist(playlistId).enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(Call<Playlist> call, Response<Playlist> response) {
                playlist = response.body();
                tvPlaylistName.setText(playlist.getName());
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
        ivPlaylisThumbnail = (ImageView) findViewById(R.id.ivPlaylistDetailThumbnail);
        layoutNoSongInPlaylist = (ConstraintLayout) findViewById(R.id.layoutNoSongInPlaylist);
        layoutHasSongInPlaylist = (ConstraintLayout) findViewById(R.id.layoutHasSongInPlaylist);
        rcListSong = (RecyclerView) findViewById(R.id.rcListSongInPlaylist);
    }
}