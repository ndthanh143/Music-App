package com.example.musicapp.Activity;

import android.os.Bundle;
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
import com.example.musicapp.R;
import com.google.android.material.tabs.TabLayout;

public class PlayMusicActivity extends AppCompatActivity {
    TabLayout mTabLayout;
    Toolbar toolbarplaynhac;
    TextView txtTimesong, txtTotaltimesong;
    SeekBar sktime;
    ImageButton imgplay, imgrepeat, imgnext,imgrandom;
    ImageButton imgpre;
    ViewPager viewPagerplaynhac;
    public static ViewPagerPlayListNhac adapternhac;
    FragmentDiaNhac fragmentDiaNhac;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_nhac);
        mTabLayout = findViewById(R.id.tab_layout);
        viewPagerplaynhac = findViewById((R.id.viewpagerplaynhac));
        adapternhac = new ViewPagerPlayListNhac(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPagerplaynhac.setAdapter(adapternhac);
        mTabLayout.setupWithViewPager(viewPagerplaynhac);
//        init();
//        GetDataFromIntent();
    }

//    private void GetDataFromIntent() {
//        Intent intent = getIntent();
//        if (intent.hasExtra("ca khúc")){
//
//        }
//    }

//    private void init(){
//         toolbarplaynhac = findViewById(R.id.toolbarplaynhac);
//         txtTimesong = findViewById(R.id.textviewtimesong);
//         txtTotaltimesong = findViewById(R.id.textviewtotaltimsong);
//         sktime = findViewById(R.id.seekbarsong);
//         imgplay = findViewById(R.id.imagebuttonplay);
//         imgrepeat = findViewById(R.id.imagebuttonrepeat);
//         imgnext = findViewById(R.id.imagebuttonnext);
//         imgrandom = findViewById(R.id.imagebuttonsuffle);
//         imgpre = findViewById(R.id.imagebuttonpreview);
//
//
////         setSupportActionBar(toolbarplaynhac);
////         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
////         toolbarplaynhac.setNavigationOnClickListener(new View.OnClickListener() {
////             @Override
////             public void onClick(View view) {
////                 finish();
////             }
////         });
////         toolbarplaynhac.setTitleTextColor(Color.WHITE);
////         fragmentDiaNhac = new FragmentDiaNhac();
////         //fragment_play_danh_sach_cac_bai_hat =....
////         adapternhac = new ViewPagerPlayListNhac(getSupportFragmentManager());
//////         adapternhac.AddFragment(fragmentDiaNhac);
////         viewPagerplaynhac.setAdapter(adapternhac);
//
//    }
}