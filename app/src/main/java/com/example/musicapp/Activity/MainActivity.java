package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.musicapp.Adapter.MusicTypeAdapter;
import com.example.musicapp.Model.MusicType;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<MusicType> mListMusicTypes;

    private RecyclerView rcMusicType;

    private MusicTypeAdapter musicTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();
        mListMusicTypes = new ArrayList<>();
        CallApiMusicType();
//        Test
    }

    private void CallApiMusicType() {
        ApiService.apiService.getListMusicTypes().enqueue(new Callback<List<MusicType>>() {
            @Override
            public void onResponse(Call<List<MusicType>> call, Response<List<MusicType>> response) {
                if(response.isSuccessful()) {
                    mListMusicTypes = response.body();
                    System.out.println(mListMusicTypes.get(0).getThumbnail());
                    musicTypeAdapter = new MusicTypeAdapter(MainActivity.this, mListMusicTypes);
                    rcMusicType.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
                    rcMusicType.setLayoutManager(layoutManager);
                    rcMusicType.setAdapter(musicTypeAdapter);
                    musicTypeAdapter.notifyDataSetChanged();
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

    private void anhXa() {
        rcMusicType = (RecyclerView) findViewById(R.id.rc_music_type);
    }
}