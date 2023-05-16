package com.example.musicapp.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.musicapp.Fragment.FragmentDiaNhac;
import com.example.musicapp.Fragment.FragmentSongInformation;
import com.example.musicapp.Fragment.FragmentLyricsNhac;
//import com.example.musicapp.Fragment.Fragment_Banner;

import java.util.ArrayList;

    public class ViewPagerPlayListNhac extends FragmentPagerAdapter {
        public final ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
        private static final int NUM_PAGES = 3;
        public static FragmentSongInformation mFragment1;
        public static FragmentDiaNhac mFragment2;
        public static FragmentLyricsNhac mFragment3;
        public ViewPagerPlayListNhac(@NonNull FragmentManager fm) {
            super(fm);
            mFragment1 = new FragmentSongInformation();
            mFragment2 = new FragmentDiaNhac();
            mFragment3 = new FragmentLyricsNhac();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return mFragment1;
                case 1:
                    return mFragment2;
                case 2:
                    return mFragment3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
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
        public FragmentSongInformation getFragment1() {
            return mFragment1;
        }
        public FragmentDiaNhac getFragment2() {
            return mFragment2;
        }
        public FragmentLyricsNhac getFragment3() {
            return mFragment3;
        }
        public void AddFragment(Fragment fragment){
            fragmentArrayList.add(fragment);
        }
    }
