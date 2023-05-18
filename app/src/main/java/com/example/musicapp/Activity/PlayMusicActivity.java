package com.example.musicapp.Activity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.musicapp.Adapter.BaiHatAdapter;
import com.example.musicapp.Adapter.ViewPagerPlayListNhac;
import com.example.musicapp.DTO.SongDTO;
import com.example.musicapp.Fragment.FragmentDiaNhac;
import com.example.musicapp.Fragment.FragmentSongInformation;
import com.example.musicapp.Fragment.FragmentLyricsNhac;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.ApiService;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlayMusicActivity extends AppCompatActivity {
    private TabLayout mTabLayout;
    private Toolbar toolbarplaynhac;
    private TextView txtTimesong, txtTotaltimesong;
    private SeekBar sktime;
    private ImageButton imgplay, btnRepeat, btnNext, btnPrev, btnRandom;
    private ViewPager viewPagerplaynhac;
    public static ViewPagerPlayListNhac adapternhac;
    private Song song;
    private FragmentSongInformation fragmentSongInformation;
    private FragmentLyricsNhac fragmentLyricsNhac;
    private FragmentDiaNhac fragmentDiaNhac;
    public MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private boolean isRepeat = false;
    private boolean isShufle = false;
    private BaiHatAdapter baiHatAdapter;
    int position;
    public static ArrayList<String> mListSongId = new ArrayList<>();
    public static String songId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        anhXa();
        Intent intent = getIntent();
        mListSongId = intent.getStringArrayListExtra("listSong");
        songId = getIntent().getExtras().getString("songId");

        LoadSongData(songId);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextSong();
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousSong();
            }
        });
        btnRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRepeat = !isRepeat;
                if(isRepeat) {
                    btnRepeat.setBackground(getDrawable(R.drawable.ic_baseline_repeat_active_24));
                } else {
                    btnRepeat.setBackground(getDrawable(R.drawable.ic_baseline_repeat_24));
                }
            }
        });
        btnRandom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isShufle = !isShufle;
                if(isShufle) {
                    btnRandom.setBackground(getDrawable(R.drawable.ic_baseline_shuffle_active_24));
                } else {
                    btnRandom.setBackground(getDrawable(R.drawable.ic_baseline_shuffle_24));
                }
            }
        });
    }

    private void previousSong() {
        if(isShufle) {
            Random random = new Random();
            int randomNumber = random.nextInt(mListSongId.size() - 1) + 0;
            while(mListSongId.get(randomNumber).equals(songId) ) {
                randomNumber = random.nextInt(mListSongId.size() - 1) + 0;
            }
            songId = mListSongId.get(randomNumber);
            LoadSongData(songId);
        } else {
            for (int i = 0; i < mListSongId.size(); i++) {
                String id = mListSongId.get(i);
                if (id.equals(songId)) {
                    if(i==0) {
                        songId = mListSongId.get(mListSongId.size()-1);
                    } else {
                        songId = mListSongId.get(i-1);
                    }
                    LoadSongData(songId);
                    break;
                }
            }
        }
    }

    private void nextSong() {
        if(isShufle) {
            Random random = new Random();
            int randomNumber = random.nextInt(mListSongId.size() - 1) + 0;
            while(mListSongId.get(randomNumber).equals(songId) ) {
                randomNumber = random.nextInt(mListSongId.size() - 1) + 0;
            }
            songId = mListSongId.get(randomNumber);
            LoadSongData(songId);
        } else {
            for (int i = 0; i < mListSongId.size(); i++) {
                String id = mListSongId.get(i);
                if (id.equals(songId)) {
                    if(i==mListSongId.size() - 1) {
                        songId = mListSongId.get(0);
                    } else {
                        songId = mListSongId.get(i+1);
                    }
                    LoadSongData(songId);
                    break;
                }
            }
        }

    }

    private void anhXa() {
        mTabLayout = findViewById(R.id.tab_layout);
        viewPagerplaynhac = findViewById((R.id.viewpagerplaynhac));
        viewPagerplaynhac.setAdapter(adapternhac);
        mTabLayout.setupWithViewPager(viewPagerplaynhac);
        toolbarplaynhac = findViewById(R.id.toolbarplaynhac);
        txtTimesong = findViewById(R.id.textviewtimesong);
        txtTotaltimesong = findViewById(R.id.textviewtotaltimsong);
        sktime = findViewById(R.id.seekbarsong);
        btnRepeat = findViewById(R.id.imagebuttonrepeat);
        btnNext = findViewById(R.id.imagebuttonnext);
        btnRandom = findViewById(R.id.imagebuttonsuffle);
        btnPrev = findViewById(R.id.imagebuttonpreview);
        imgplay = findViewById(R.id.imagebuttonplay);


//        Init fragment
        adapternhac = new ViewPagerPlayListNhac(getSupportFragmentManager());
        fragmentSongInformation = adapternhac.getFragment1();
        fragmentDiaNhac= adapternhac.getFragment2();
        fragmentLyricsNhac=adapternhac.getFragment3();
        adapternhac.AddFragment(new FragmentDiaNhac());
        adapternhac.AddFragment(new FragmentSongInformation());
        adapternhac.AddFragment(new FragmentLyricsNhac());
        viewPagerplaynhac.setAdapter(adapternhac);
        viewPagerplaynhac.setCurrentItem(1);
        viewPagerplaynhac.setOffscreenPageLimit(3);
    }

    private void LoadSongData(String songId) {
        System.out.println("songid: " +songId);
        ApiService.apiService.getSongId(songId).enqueue(new Callback<Song>() {
            @Override
            public void onResponse(Call<Song> call, Response<Song> response) {
                if (response.isSuccessful() && response.body() != null) {
                    song = response.body();
                    fragmentDiaNhac.onPause();
                    String url=song.getSongUrl();
                    eventClick(url);
                    String lyric = song.getLyrics();
                    fragmentDiaNhac.Playnhac(song.getImageSongUrl());
                    fragmentSongInformation.LoadInformation(song);
                    fragmentLyricsNhac.LoadLyrics(song.getLyrics());
                    khoiTaoMedia(song.getSongUrl());
                }
            }

            @Override
            public void onFailure(Call<Song> call, Throwable t) {
                Log.e("=====", "Call fail");
                Log.e("=====", t.getMessage());
            }
        });
    }
    private void eventClick(String url)
    {
        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPlaying) {
                    mediaPlayer.pause();
                    isPlaying = false;
                    imgplay.setImageResource(R.drawable.iconplay);
                    fragmentDiaNhac.onPause();
                } else {
                    mediaPlayer.start();
                    isPlaying = true;
                    imgplay.setImageResource(R.drawable.baseline_pause_circle_outline_24);
                    fragmentDiaNhac.onResume();
                }
            }
        });
        sktime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

    }
    private void Updatetime(){
        final Handler handler=new Handler();
        handler.postDelayed(()->{
            SimpleDateFormat time = new SimpleDateFormat("mm:ss");
            txtTimesong.setText(time.format(mediaPlayer.getCurrentPosition()));
            sktime.setProgress(mediaPlayer.getCurrentPosition());
            //Kiểm tra bài hát nếu kết thúc dừng lại
            mediaPlayer.setOnCompletionListener((mp)->{
                if (isRepeat) {
                    mediaPlayer.seekTo(0);
                    mediaPlayer.start();
                } else {
                    nextSong();
                    SetTimeTotal();
                    Updatetime();
                }

            });
            handler.postDelayed(this::Updatetime,500);
        },100);
    }

    private void SetTimeTotal() {
        SimpleDateFormat timeTotal = new SimpleDateFormat("mm:ss");
        txtTotaltimesong.setText(timeTotal.format(mediaPlayer.getDuration()));
        sktime.setMax(mediaPlayer.getDuration());
    }
    private void khoiTaoMedia(String url) {
        if (mediaPlayer == null)
        {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(url);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();
                    isPlaying = true;
                    imgplay.setImageResource(R.drawable.baseline_pause_circle_outline_24);
                    SetTimeTotal();
                    Updatetime();
                    fragmentDiaNhac.onResume();
                }
            });
        } else {
            mediaPlayer.stop();
            mediaPlayer = new MediaPlayer();
            System.out.println("Media playing");
            try {
                mediaPlayer.setDataSource(url);
            } catch (IOException e) {
                throw new RuntimeException();
            }
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    isPlaying = true;
                    imgplay.setImageResource(R.drawable.baseline_pause_circle_outline_24);
                    SetTimeTotal();
                    Updatetime();
                    fragmentDiaNhac.onResume();
                }
            });
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            isPlaying = false;
            // Cập nhật trạng thái của giao diện ngừng phát nhạc (nếu có)
        }
    }



}