package com.example.musicapp.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.musicapp.Fragment.FragmentDiaNhac;
import com.example.musicapp.Fragment.FragmentListSongPlay;
import com.example.musicapp.Fragment.FragmentLyricsNhac;
import com.example.musicapp.Fragment.Fragment_Banner;

import java.util.ArrayList;

public class ViewPagerPlayListNhac extends FragmentPagerAdapter {

//    public ViewPagerPlayListNhac(@NonNull FragmentManager fm, int behavior) {
//        super(fm, behavior);
//    }
    public final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    public ViewPagerPlayListNhac(FragmentManager fm) {
        super(fm);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentListSongPlay();
            case 1:
                return new FragmentDiaNhac();
            case 2:
                return new FragmentLyricsNhac();
            default:  return new FragmentDiaNhac();
        }
    }

    @Override
    public int getCount() {
        return fragmentArrayList.size();
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title="";
        if (position==0)
            title = "Thông tin bài hát";
        if (position==1)
            title = "Bài hát";
        if (position==2)
            title = "Lời bài hát";
        return title;
    }
    public void AddFragment(Fragment fragment){
        fragmentArrayList.add(fragment);
    }
}
