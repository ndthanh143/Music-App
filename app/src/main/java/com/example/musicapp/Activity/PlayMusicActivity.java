package com.example.musicapp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.AsyncPlayer;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.musicapp.Adapter.ViewPagerPlayListNhac;
import com.example.musicapp.Fragment.FragmentDiaNhac;
import com.example.musicapp.Fragment.FragmentListSongPlay;
import com.example.musicapp.Fragment.FragmentLyricsNhac;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class PlayMusicActivity extends AppCompatActivity {
    TabLayout mTabLayout;
    Toolbar toolbarplaynhac;
    TextView txtTimesong, txtTotaltimesong;
    SeekBar sktime;
    ImageButton imgplay, imgrepeat, imgnext,imgrandom;
    ImageButton imgpre;
    ViewPager viewPagerplaynhac;
    public static ViewPagerPlayListNhac adapternhac;
    public  static ArrayList<Song> mangbaihat= new ArrayList<>();
    FragmentDiaNhac fragmentDiaNhac;
    FragmentListSongPlay fragmentListSongPlay;
    FragmentLyricsNhac fragmentLyricsNhac;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mTabLayout = findViewById(R.id.tab_layout);
        viewPagerplaynhac = findViewById((R.id.viewpagerplaynhac));
//        adapternhac = new ViewPagerPlayListNhac(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerplaynhac.setAdapter(adapternhac);
        mTabLayout.setupWithViewPager(viewPagerplaynhac);
        GetDataFromIntent();
        init();
        eventClick();
    }

    private void eventClick() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (adapternhac.getItem(1)!=null){
                    if (mangbaihat.size()>0){
                        fragmentDiaNhac.Playnhac(mangbaihat.get(0).getImageSongUrl());
                        handler.removeCallbacks(this);
                    }
                    else {
                        handler.postDelayed(this,300);
                    }
                }
            }
        },500);
        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imgplay.setImageResource(R.drawable.iconplay);
                }
                else {
                    mediaPlayer.start();
                    imgplay.setImageResource(R.drawable.baseline_pause_circle_24);
                }
            }
        });
    }

    private void GetDataFromIntent() {
//            playlistId => call api lay ra cac bai hat trong playlist
//        songId => lay ra bai hat duoc chon
        Intent intent =getIntent();
        mangbaihat.clear();
        if(intent != null){
            if (intent.hasExtra("song")) {
                Song song = intent.getParcelableExtra("song");
                mangbaihat.add(song);
            }
            if (intent.hasExtra("songs")){
                ArrayList<Parcelable> list = intent.getParcelableArrayListExtra("songs");
                ArrayList<Song> songList = new ArrayList<>();
                for (Parcelable p : list) {
                    songList.add((Song) p);
                }
                mangbaihat = songList;
                System.out.println("So luong bai hat " + mangbaihat.size());
            }
        }
    }


    private void init(){
         toolbarplaynhac = findViewById(R.id.toolbarplaynhac);
         txtTimesong = findViewById(R.id.textviewtimesong);
         txtTotaltimesong = findViewById(R.id.textviewtotaltimsong);
         sktime = findViewById(R.id.seekbarsong);
         imgplay = findViewById(R.id.imagebuttonplay);
         imgrepeat = findViewById(R.id.imagebuttonrepeat);
         imgnext = findViewById(R.id.imagebuttonnext);
         imgrandom = findViewById(R.id.imagebuttonsuffle);
         imgpre = findViewById(R.id.imagebuttonpreview);


         setSupportActionBar(toolbarplaynhac);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         toolbarplaynhac.setNavigationOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 finish();
             }
         });
         toolbarplaynhac.setTitleTextColor(Color.WHITE);
         fragmentDiaNhac = new FragmentDiaNhac();
         //fragment_play_danh_sach_cac_bai_hat =....
         adapternhac = new ViewPagerPlayListNhac(getSupportFragmentManager());
         adapternhac.AddFragment(fragmentListSongPlay);
         adapternhac.AddFragment(fragmentDiaNhac);
         adapternhac.AddFragment(fragmentLyricsNhac);
         viewPagerplaynhac.setAdapter(adapternhac);
         fragmentDiaNhac =(FragmentDiaNhac) adapternhac.getItem(1);
         if(mangbaihat.size()>0){
             getSupportActionBar().setTitle(mangbaihat.get(0).getName());
//             new playMP3().execute(mangbaihat.get(0).getSongUrl());
             new playMP3().execute();

             imgplay.setImageResource(R.drawable.baseline_pause_circle_24);
         }
    }
    class playMP3 extends AsyncTask<String,Void,String>{
        @Override
        protected String doInBackground(String... strings) {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String song) {
            super.onPostExecute(song);
            try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                }
            });
                mediaPlayer.setDataSource(song);
                mediaPlayer.prepare();
            } catch (IOException e) {
//                throw new RuntimeException(e);
                e.printStackTrace();
            }
            mediaPlayer.start();
            TimeSong();
        }
    }

    private void TimeSong() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        txtTotaltimesong.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        sktime.setMax(mediaPlayer.getDuration());
    }
}