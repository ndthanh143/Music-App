package com.example.musicapp.Adapter;

import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.musicapp.Fragment.FragmentDiaNhac;
import com.example.musicapp.Fragment.FragmentInfoNhac;
import com.example.musicapp.Fragment.FragmentLyricsNhac;

import java.util.ArrayList;

public class ViewPagerPlayListNhac extends FragmentStatePagerAdapter {

    public ViewPagerPlayListNhac(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentInfoNhac();
            case 1:
                return new FragmentDiaNhac();
            case 2:
                return new FragmentLyricsNhac();
            default:  return new FragmentInfoNhac();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title ="";
        switch (position){
            case 0: title = "Thông tin bài hát";
            case 1: title = "Bài hát";
            case 2: title = "Lời bài hát";
            break;
        }
        return title;
    }
}
