package com.example.musicapp.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.musicapp.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FragmentDiaNhac extends Fragment {
    private Animation rotateAnimation;
    View view;
    CircleImageView circleImageView;
    ObjectAnimator objectAnimator;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dia_nhac, container,false);
        circleImageView = view.findViewById(R.id.imageviewcircle);
//        objectAnimator = objectAnimator.ofFloat(circleImageView, "rotation",0f, 360f);
//        objectAnimator.setDuration(10000);
//        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
//        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
//        objectAnimator.setInterpolator(new LinearInterpolator());
//        RotateAnimation rotateAnimation = new RotateAnimation(0, 360,
//                Animation.RELATIVE_TO_SELF, 0.5f,
//                Animation.RELATIVE_TO_SELF, 0.5f);
//        rotateAnimation.setDuration(10000); // Đặt thời gian xoay (10 giây)
//        rotateAnimation.setRepeatCount(Animation.INFINITE); // Lặp vô hạn
//        rotateAnimation.setRepeatMode(Animation.RESTART); // Khởi động lại sau khi kết thúc
//
//        circleImageView.startAnimation(rotateAnimation);
        rotateAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        return view;
    }
    public void Playnhac(String hinhanh) {
        Picasso.with(getContext()).load(hinhanh).into(circleImageView);
    }
    @Override
    public void onResume() {
        super.onResume();
        circleImageView.startAnimation(rotateAnimation);
    }
    @Override
    public void onPause() {
        super.onPause();
        circleImageView.clearAnimation();
    }
}
