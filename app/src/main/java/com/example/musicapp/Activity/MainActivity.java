package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapp.Adapter.BaiHatAdapter;
import com.example.musicapp.Adapter.MusicTypeAdapter;
import com.example.musicapp.DTO.UserDTO;
import com.example.musicapp.Model.MusicType;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.ApiService;
import com.example.musicapp.Service_Local.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<MusicType> mListMusicTypes;
    private List<Song> mListSong = new ArrayList<>();

    private RecyclerView rcMusicType,rcSong;

    private RecyclerView.Adapter adapter, adapter2;
    private UserDTO user;

    TextView search_input;
    ImageView ivAddMore,ivFavourite,ivExplore,ivAccount,ivHome;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = SharedPrefManager.getInstance(this).getUser();
        anhXa();
        xuLyBtn();
        mListMusicTypes = new ArrayList<>();
        CallApiMusicType();
        RecyclerViewListSong();
    }



    private void anhXa() {
        search_input = findViewById(R.id.search_input);
        ivExplore = findViewById(R.id.ivExplore);
        ivAccount = findViewById(R.id.ivAccount);
        ivAddMore = findViewById(R.id.ivAddMore);
        ivFavourite = findViewById(R.id.ivFavourite);
        ivHome = findViewById(R.id.ivHome);

        rcSong = findViewById(R.id.rc_recent_song);
        rcMusicType = findViewById(R.id.rc_music_type);
    }
    private void xuLyBtn() {
        search_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Áp dụng animation
                Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.slide_up);
                search_input.startAnimation(animation);

                Intent intent = new Intent(MainActivity.this, MusicSearchActivity.class);
                startActivity(intent);
            }
        });
        ivAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                overridePendingTransition(R.anim.slide_to_left, R.anim.slide_to_right);
                if (SharedPrefManager.getInstance(MainActivity.this).getUser().getId() == null) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, CreatePlaylistActivity.class);
                    startActivity(intent);
                }
            }
        });
        ivExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ListPlaylistActivity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_to_left, R.anim.slide_to_right);
            }
        });
        ivAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                search_input.startAnimation(animation);
                Intent intent;
                if (user.getId() == null) {
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, AccountActivity.class);
                }
                startActivity(intent);
            }
        });
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerViewListSong();
            }
        });

    }

    private void RecyclerViewListSong() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcSong.setLayoutManager(linearLayoutManager);
        //Get API
        ApiService.apiService.getListMusicSong().enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if(response.isSuccessful()){
                    mListSong=response.body();
                    System.out.println("Size ----------------------" + mListSong.size());
                    adapter2 = new BaiHatAdapter(MainActivity.this, mListSong);
                    rcSong.setAdapter(adapter2);
                }else{
                    int statusCode=response.code();
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Log.d("logg",t.getMessage());
                System.out.println("No Database");
            }
        });

    }

    private void CallApiMusicType() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcMusicType.setLayoutManager(linearLayoutManager);
        ApiService.apiService.getListMusicTypes().enqueue(new Callback<List<MusicType>>() {
            @Override
            public void onResponse(Call<List<MusicType>> call, Response<List<MusicType>> response) {
                if(response.isSuccessful()) {
                    mListMusicTypes = response.body();
                    adapter = new MusicTypeAdapter(MainActivity.this, mListMusicTypes);
                    rcMusicType.setAdapter(adapter);
                } else {
                    int statusCode = response.code();
                }
            }

            @Override
            public void onFailure(Call<List<MusicType>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Call api Music type error", Toast.LENGTH_SHORT).show();
                System.out.println("Call api error " + t.getMessage());
            }
        });
    }

}