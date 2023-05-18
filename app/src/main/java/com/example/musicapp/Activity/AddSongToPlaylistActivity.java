package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.musicapp.Adapter.AddSongToPlaylistAdapter;
import com.example.musicapp.Adapter.SearchAdapter;
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
    private ImageButton btnClose;
    private EditText searchview;
    private Runnable apiRunnable;
    private Handler handler = new Handler();
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
        btnClose =findViewById(R.id.btnCloseActivityAddSongToPlaylist);
        searchview = findViewById(R.id.seachView1);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang Activity mới
                Intent intent = new Intent(AddSongToPlaylistActivity.this, PlaylistAcitivity.class);
                intent.putExtra("playlistId", playlistId);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_to_right, R.anim.slide_to_left);
            }
        });
        searchview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Không cần thực hiện gì trước khi thay đổi
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Hủy bỏ việc gọi API trước đó nếu có
                if (apiRunnable != null) {
                    handler.removeCallbacks(apiRunnable);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Gọi API sau một khoảng thời gian trễ
                apiRunnable = new Runnable() {
                    @Override
                    public void run() {
                        String query = searchview.getText().toString();
                        RecyclerViewListSong(query);
                    }
                };

                // Thực hiện gọi API sau 1 giây (hoặc khoảng thời gian mong muốn)
                handler.postDelayed(apiRunnable, 400);
            }
        });

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
    private void RecyclerViewListSong(String query) {
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        rcSong = findViewById(R.id.songsList);
//        rcSong.setLayoutManager(linearLayoutManager);
        //Get API
        ApiService.apiService.findSongByName(query).enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if(response.isSuccessful()){
                    mListSong=response.body();
                    mListSong = response.body();
                    songAdapter = new AddSongToPlaylistAdapter(AddSongToPlaylistActivity.this,R.layout.viewholder_song_addition_display, mListSong, playlistId);
//                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
//                lvListSong.setLayoutManager(layoutManager);
                    lvListSong.setAdapter(songAdapter);
                    songAdapter.notifyDataSetChanged();
                }else{
                    int statusCode=response.code();
                    System.out.println("-----Ko tim dc bai hat-------"+statusCode+query);

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