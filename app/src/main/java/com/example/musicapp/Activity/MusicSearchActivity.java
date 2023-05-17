package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.musicapp.Adapter.BaiHatAdapter;
import com.example.musicapp.Adapter.SearchAdapter;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MusicSearchActivity extends AppCompatActivity {

    ImageButton btnBack,btnSearch;
    EditText txtSearch;
    private List<Song> mListSong=new ArrayList<>();

    private RecyclerView rcMusicType,rcSong;
    private RecyclerView.Adapter adapter;
    ApiService apiService;
    private Runnable apiRunnable;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_search);
        btnBack = findViewById(R.id.btn_back);
        btnSearch = findViewById(R.id.btn_search);
        txtSearch = findViewById(R.id.seachView);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MusicSearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(txtSearch.getText().toString());
                RecyclerViewListSong(txtSearch.getText().toString().trim());
            }
        });
        txtSearch.addTextChangedListener(new TextWatcher() {
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
                        String query = txtSearch.getText().toString();
                        RecyclerViewListSong(query);
                    }
                };

                // Thực hiện gọi API sau 1 giây (hoặc khoảng thời gian mong muốn)
                handler.postDelayed(apiRunnable, 400);
            }
        });
    }
    private void RecyclerViewListSong(String query) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcSong = findViewById(R.id.songsList);
        rcSong.setLayoutManager(linearLayoutManager);
        //Get API
        ApiService.apiService.findSongByName(query).enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if(response.isSuccessful()){
                    mListSong=response.body();
                    adapter = new SearchAdapter(MusicSearchActivity.this, mListSong);
                    rcSong.setAdapter(adapter);
                    System.out.println("-----Tim dc bai hat-------");
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