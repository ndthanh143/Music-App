package com.example.musicapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musicapp.R;
import com.example.musicapp.Service_Local.SharedPrefManager;

public class AcountActivity extends AppCompatActivity {

    ImageButton ivback,ivSetting;
    TextView tvInfo,tvChagePass,tvDangxuat,tvUsernameAccount;
    ImageView ivAddMore,ivFavourite,ivExplore,ivAccount,ivHome;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        anhXa();
        tvUsernameAccount.setText(SharedPrefManager.getInstance(this).getUser().getName());
        ivback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang Activity mới
                Intent intent = new Intent(AcountActivity.this, MainActivity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_to_right, R.anim.slide_to_left);
            }
        });
        tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang Activity mới
                Intent intent = new Intent(AcountActivity.this, InforPersonActivity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_to_right, R.anim.slide_to_left);
            }
        });
        tvChagePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang Activity mới
                Intent intent = new Intent(AcountActivity.this, ChangePassActivity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_to_right, R.anim.slide_to_left);
            }
        });
        ivAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                overridePendingTransition(R.anim.slide_to_left, R.anim.slide_to_right);
                // Chuyển sang Activity mới
                Intent intent = new Intent(AcountActivity.this, CreatePlaylistActivity.class);
                startActivity(intent);
            }
        });
        ivExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang Activity mới
                Intent intent = new Intent(AcountActivity.this, ListPlaylistActivity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_to_left, R.anim.slide_to_right);
            }
        });
        ivAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang Activity mới
//                search_input.startAnimation(animation);
//                Intent intent = new Intent(AcountActivity.this, AcountActivity.class);
//                startActivity(intent);
            }
        });
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AcountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        tvDangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(AcountActivity.this).logout();
            }
        });
    }

    private void anhXa() {
        ivback = findViewById(R.id.ivback);
        ivSetting = findViewById(R.id.ivsetting);
        tvInfo = findViewById(R.id.tvInfo);
        tvChagePass = findViewById(R.id.tvChagePass);
        tvDangxuat = findViewById(R.id.tvDangxuat);
        ivExplore = findViewById(R.id.ivExplore);
        ivAccount = findViewById(R.id.ivAccount);
        ivAddMore = findViewById(R.id.ivAddMore);
        ivFavourite = findViewById(R.id.ivFavourite);
        ivHome = findViewById(R.id.ivHome);
        tvUsernameAccount = findViewById(R.id.tvUsernameAccount);
        tvDangxuat = findViewById(R.id.tvDangxuat);
    }
}
