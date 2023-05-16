package com.example.musicapp.Activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
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
import com.example.musicapp.Fragment.FragmentListSongPlay;
import com.example.musicapp.Fragment.FragmentLyricsNhac;
import com.example.musicapp.Model.Song;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.ApiService;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PlayMusicActivity extends AppCompatActivity {
    TabLayout mTabLayout;
    Toolbar toolbarplaynhac;
    TextView txtTimesong, txtTotaltimesong;
    SeekBar sktime;
    ImageButton imgplay, imgrepeat, imgnext,imgrandom;
    ImageButton imgpre;
    ViewPager viewPagerplaynhac;
    public static ViewPagerPlayListNhac adapternhac;
    private Song song;
    FragmentListSongPlay fragmentListSongPlay;
    FragmentLyricsNhac fragmentLyricsNhac;
    FragmentDiaNhac fragmentDiaNhac;
    MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    BaiHatAdapter baiHatAdapter;
    int position;

    ListView lvSong;
    public  static ArrayList<SongDTO> mangbaihat= new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        initbtn();
        initfragment();
        GetFunc();
        LoadSongData();

    }

    private void initfragment() {
        adapternhac = new ViewPagerPlayListNhac(getSupportFragmentManager());
        fragmentDiaNhac= adapternhac.getFragment2();
        fragmentLyricsNhac=adapternhac.getFragment3();
        adapternhac.AddFragment(new FragmentListSongPlay());
        adapternhac.AddFragment(new FragmentDiaNhac());
        adapternhac.AddFragment(new FragmentLyricsNhac());
        viewPagerplaynhac.setAdapter(adapternhac);
//        fragmentDiaNhac =(FragmentDiaNhac) adapternhac.getItem(1);

    }

    private void initbtn() {
        mTabLayout = findViewById(R.id.tab_layout);
        viewPagerplaynhac = findViewById((R.id.viewpagerplaynhac));
        viewPagerplaynhac.setAdapter(adapternhac);
        mTabLayout.setupWithViewPager(viewPagerplaynhac);
        toolbarplaynhac = findViewById(R.id.toolbarplaynhac);
        txtTimesong = findViewById(R.id.textviewtimesong);
        txtTotaltimesong = findViewById(R.id.textviewtotaltimsong);
        sktime = findViewById(R.id.seekbarsong);
        imgrepeat = findViewById(R.id.imagebuttonrepeat);
        imgnext = findViewById(R.id.imagebuttonnext);
        imgrandom = findViewById(R.id.imagebuttonsuffle);
        imgpre = findViewById(R.id.imagebuttonpreview);
        imgplay = findViewById(R.id.imagebuttonplay);
    }

    private void LoadSongData() {
        String songId =getIntent().getStringExtra("id");
        ApiService.apiService.getSongId(songId).enqueue(new Callback<Song>() {
            @Override
            public void onResponse(Call<Song> call, Response<Song> response) {
                if (response.isSuccessful() && response.body() != null) {
                    song = response.body();
                    SongDTO newsong=new SongDTO(song.getId(),song.getName(), song.getArtist(), song.getSongUrl(), song.getImageSongUrl(), song.getLyrics());
                    mangbaihat.add(newsong);
                    position=0;
//                    XuLyBaiHat(mangbaihat.get(position));
                    fragmentDiaNhac.onPause();
//                    String url="http://res.cloudinary.com/dk9jp2gb5/video/upload/v1683964892/audio/gtnxnlkaexnarn1tokh5.mp3";
                    System.out.println("Ten bai hat la:............."+song.getName());
                    String url=song.getSongUrl();
                    eventClick(url);
                    String lyric=song.getLyrics();
                    fragmentDiaNhac.Playnhac(song.getImageSongUrl());
                    if (fragmentLyricsNhac != null) {
//                        fragmentLyricsNhac.Getloibaihat(lyric);
                        System.out.println("Loi bai hat la:............."+song.getLyrics());
                    }
                    else {System.out.println("----------Khong co Fragment nao het!!!-----------------------/////////////////////");}
//                    System.out.println("Id cua bai hat dc chon la"+songId);
                    baiHatAdapter=new BaiHatAdapter(PlayMusicActivity.this,mangbaihat);
                    AddSong();
                }
            }

            @Override
            public void onFailure(Call<Song> call, Throwable t) {
                Log.e("=====", "Call fail");
                Log.e("=====", t.getMessage());
            }
        });
    }

    private void XuLyBaiHat(SongDTO songDTO) {

    }

    private void AddSong() {
        mangbaihat=new ArrayList<SongDTO>();
        mangbaihat.add(new SongDTO("1234","Chạm Gần thêm thương","Thu Hà",
                "https://zingmp3.vn/bai-hat/Cham-Gan-Them-Thuong-Truc-Nhan/Z6A9FUC6.html","http://res.cloudinary.com/dk9jp2gb5/image/upload/v1683964894/audio/ebcemqatd4wefqqozgqk.jpg",
                "Chạm tay chạm tay vuốt làn tóc mai\n" +
                        "Thấy thời gian in dấu trên bờ vai"));
        mangbaihat.add(new SongDTO("98645","Thưa mẹ con về","Vĩnh Quang",
                "https://zingmp3.vn/bai-hat/Thua-Me-Con-Ve-Truong-Thao-Nhi-Vu-Dang-Quoc-Viet/Z6AABZ8A.html","http://res.cloudinary.com/dk9jp2gb5/image/upload/v1683964894/audio/ebcemqatd4wefqqozgqk.jpg",
                "Em đâu biết rằng\n" +
                        "Thân cò nhỏ bé"));
    }

    private void eventClick(String url)
    {
        khoiTaoMedia(url);
        imgplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                 else
                 {
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
        imgnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//               position++;
//               if (position> mangbaihat.size()) {
//                   position = 0;
//               }
//               if (mediaPlayer.isPlaying()) mediaPlayer.stop();
//               khoiTaoMedia(mangbaihat.get(position).getSongUrl());
//               XuLyBaiHat(mangbaihat.get(position));
//                mediaPlayer.start();
//                imgplay.setImageResource(android.R.drawable.ic_media_pause);
            }
        });
    }

    private void GetFunc() {

    }
    private void Updatetime(){
        final Handler handler=new Handler();
        handler.postDelayed(()->{
            SimpleDateFormat time = new SimpleDateFormat("mm:ss");
            txtTimesong.setText(time.format(mediaPlayer.getCurrentPosition()));
            sktime.setProgress(mediaPlayer.getCurrentPosition());
            //Kiểm tra bài hát nếu kết thúc dừng lại
            mediaPlayer.setOnCompletionListener((mp)->{
                position++;
                if(position>=mangbaihat.size()){
                    position=0;
                }
                if(mediaPlayer.isPlaying()){mediaPlayer.stop();}
                khoiTaoMedia(mangbaihat.get(position).getSongUrl());
                mediaPlayer.start();
                imgplay.setImageResource(R.drawable.baseline_pause_circle_outline_24);
                SetTimeTotal();
                Updatetime();
            });
            handler.postDelayed(this::Updatetime,500);
        },100);
    }


//    class playMP3 extends AsyncTask<String,Void,String>{
//        @Override
//        protected String doInBackground(String... strings) {
//            return strings[0];
//        }
//
//        @Override
//        protected void onPostExecute(String song) {
//            super.onPostExecute(song);
//            try {
//            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mediaPlayer) {
//                    mediaPlayer.stop();
//                    mediaPlayer.reset();
//                }
//            });
//                mediaPlayer.setDataSource(song);
//                mediaPlayer.prepare();
//            } catch (IOException e) {
////                throw new RuntimeException(e);
//                e.printStackTrace();
//            }
//            mediaPlayer.start();
//            SetTimeTotal();
//        }
//    }
//
    private void SetTimeTotal() {
        SimpleDateFormat timeTotal = new SimpleDateFormat("mm:ss");
        txtTotaltimesong.setText(timeTotal.format(mediaPlayer.getDuration()));
        sktime.setMax(mediaPlayer.getDuration());
    }
    private  void khoiTaoMedia(String url){
        onPause();
        if (mediaPlayer == null)
        {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.pause();
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
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    return false;
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