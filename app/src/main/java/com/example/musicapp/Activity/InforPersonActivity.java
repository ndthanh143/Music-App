package com.example.musicapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.musicapp.DTO.UserDTO;
import com.example.musicapp.R;
import com.example.musicapp.Service_Local.SharedPrefManager;

public class InforPersonActivity extends AppCompatActivity {
    ImageButton btn_back_info,btnAcount;
    TextView tvUsername,tvEmail,tvPhone;
    ImageView ivAvatar;
    UserDTO user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_person);
        anhXa();
        user = SharedPrefManager.getInstance(this).getUser();
        tvUsername.setText(user.getName());
        tvEmail.setText(user.getEmail());
        tvPhone.setText(user.getPhone());
        Glide.with(InforPersonActivity.this)
                        .load(user.getAvatar())
                        .into(ivAvatar);

        btn_back_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InforPersonActivity.this, AccountActivity.class);
                startActivity(intent);
            }
        });
        btnAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InforPersonActivity.this, ChangeInfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void anhXa() {
        btn_back_info=findViewById(R.id.btn_back_info);
        btnAcount=findViewById(R.id.btnAcount);
        tvUsername=findViewById(R.id.tvTentaikhoan);
        tvEmail=findViewById(R.id.tvEmail);
        tvPhone=findViewById(R.id.tvPhone);
        ivAvatar=findViewById(R.id.ivAvatarInforPerson);
    }
}
