package com.example.musicapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.musicapp.R;
import com.example.musicapp.Service_Local.SharedPrefManager;

public class AccountActivity extends AppCompatActivity {

    ImageButton ivback,ivSetting;
    TextView tvInfo,tvChagePass,tvDangxuat,tvUsernameAccount;
    ImageView ivAddMore,ivFavourite,ivExplore,ivAccount,ivHome;
    ImageButton avatarUser;
    boolean isDoubleClicked=false;
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
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_to_right, R.anim.slide_to_left);
            }
        });
        tvInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang Activity mới
                Intent intent = new Intent(AccountActivity.this, InforPersonActivity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_to_right, R.anim.slide_to_left);
            }
        });
        tvChagePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang Activity mới
                Intent intent = new Intent(AccountActivity.this, ChangePassActivity.class);
                startActivity(intent);
//                overridePendingTransition(R.anim.slide_to_right, R.anim.slide_to_left);
            }
        });
        ivAddMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SharedPrefManager.getInstance(AccountActivity.this).getUser().getId() == null) {
                    Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(AccountActivity.this, CreatePlaylistActivity.class);
                    startActivity(intent);
                }
            }
        });
        ivExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển sang Activity mới
                Intent intent = new Intent(AccountActivity.this, ListPlaylistActivity.class);
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
                Intent intent = new Intent(AccountActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        tvDangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(AccountActivity.this).logout();
                Toast.makeText(AccountActivity.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
            }
        });
        avatarUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, UploadImageActivity.class));
            }
        });
        Glide.with(this).load(SharedPrefManager.getInstance(this).getUser().getAvatar())
                .circleCrop()
                .into(avatarUser);
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
        avatarUser = findViewById(R.id.avatarUser);
    }
}
