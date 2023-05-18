package com.example.musicapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musicapp.DTO.UserDTO;
import com.example.musicapp.R;
import com.example.musicapp.Service_Local.SharedPrefManager;

public class AcountActivity extends AppCompatActivity {

    ImageButton ivback,ivSetting;
    TextView tvInfo,tvChagePass,tvDangxuat;
    TextView tvUsername;
    UserDTO user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        anhXa();
        user = SharedPrefManager.getInstance(this).getUser();
        tvUsername.setText(user.getName());
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
                String id="6463c2dfd455b9497b4c746c";
                // Chuyển sang Activity mới
                Intent intent = new Intent(AcountActivity.this, InforPersonActivity.class);
                intent.putExtra("userId", id);
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
        tvUsername = findViewById(R.id.tvUsernameAccount);
    }
}
