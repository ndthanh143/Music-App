package com.example.musicapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musicapp.R;

public class AcountActivity extends AppCompatActivity {

    ImageButton ivback,ivSetting;
    TextView tvInfo,tvChagePass,tvDangxuat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_personal);
        anhXa();
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
    }

    private void anhXa() {
        ivback = findViewById(R.id.ivback);
        ivSetting = findViewById(R.id.ivsetting);
        tvInfo = findViewById(R.id.tvInfo);
        tvChagePass = findViewById(R.id.tvChagePass);
        tvDangxuat = findViewById(R.id.tvDangxuat);
    }
}
