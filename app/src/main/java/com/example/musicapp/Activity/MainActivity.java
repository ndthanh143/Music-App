package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.musicapp.Adapter.BaiHatAdapter;
import com.example.musicapp.Adapter.MusicTypeAdapter;
import com.example.musicapp.DTO.SongDTO;
import com.example.musicapp.Model.MusicType;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.RetrofitClient;
import com.example.musicapp.Service_API.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<MusicType> mListMusicTypes;
    private List<SongDTO> mListSong=new ArrayList<>();

    private RecyclerView rcMusicType,rcSong;

    private MusicTypeAdapter musicTypeAdapter;
    private RecyclerView.Adapter adapter, adapter2;
    ApiService apiService;

    Handler handler=new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListMusicTypes = new ArrayList<>();
        CallApiMusicType();
        RecyclerViewListSong();
//        Test
    }

    private void RecyclerViewListSong() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rcSong = findViewById(R.id.rc_recent_song);
        rcSong.setLayoutManager(linearLayoutManager);
        //Get API
        ApiService.apiService.getListMusicSong().enqueue(new Callback<List<SongDTO>>() {
            @Override
            public void onResponse(Call<List<SongDTO>> call, Response<List<SongDTO>> response) {
                if(response.isSuccessful()){
                    mListSong=response.body();
                    adapter2 = new BaiHatAdapter(MainActivity.this, mListSong);
                    rcSong.setAdapter(adapter2);
                }else{
                    int statusCode=response.code();
                }
            }

            @Override
            public void onFailure(Call<List<SongDTO>> call, Throwable t) {
                Log.d("logg",t.getMessage());
                System.out.println("No Database");
            }
        });

    }

    private void CallApiMusicType() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcMusicType = findViewById(R.id.rc_music_type);
        rcMusicType.setLayoutManager(linearLayoutManager);
        ApiService.apiService.getListMusicTypes().enqueue(new Callback<List<MusicType>>() {
            @Override
            public void onResponse(Call<List<MusicType>> call, Response<List<MusicType>> response) {
                if(response.isSuccessful()) {
                    mListMusicTypes = response.body();
                    adapter = new MusicTypeAdapter(MainActivity.this, mListMusicTypes);
                    rcMusicType.setAdapter(adapter);
//                    System.out.println(mListMusicTypes.get(1).getThumbnaiUrll());
//                    musicTypeAdapter = new MusicTypeAdapter(MainActivity.this, mListMusicTypes);
//                    rcMusicType.setHasFixedSize(true);
//                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
//                    rcMusicType.setLayoutManager(layoutManager);
//                    rcMusicType.setAdapter(musicTypeAdapter);
//                    musicTypeAdapter.notifyDataSetChanged();
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