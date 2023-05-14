package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.musicapp.Adapter.AddSongToPlaylistAdapter;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSongToPlaylistActivity extends AppCompatActivity {
    private AddSongToPlaylistAdapter songAdapter;
    private List<Song> mListSong;
    private ListView lvListSong;
    private String playlistId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_song_to_playlist);
        Intent i = getIntent();
        playlistId = i.getStringExtra("playlistId");
        anhXa();
        mListSong = new ArrayList<>();
        CallApiSong();

    }

    private void anhXa() {
        lvListSong = (ListView) findViewById(R.id.rcListSong);
    }

    private void CallApiSong() {
        ApiService.apiService.getAllSong().enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                mListSong = response.body();
                songAdapter = new AddSongToPlaylistAdapter(AddSongToPlaylistActivity.this,R.layout.viewholder_song_addition_display, mListSong, playlistId);
//                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
//                lvListSong.setLayoutManager(layoutManager);
                lvListSong.setAdapter(songAdapter);
                songAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Toast.makeText(AddSongToPlaylistActivity.this, "Call Api Error: "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}