package com.example.musicapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musicapp.Adapter.BaiHatAdapter;
import com.example.musicapp.DTO.SongDTO;
import com.example.musicapp.Model.Playlist;
import com.example.musicapp.Model.Song;
import com.example.musicapp.Model.User;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InforPersonActivity extends AppCompatActivity {
    ImageButton btn_back_info,btnAcount,btnPass;
    TextView tvTentaikhoan,tvPass,tvGmail,tvSdt;
    private User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_person);
        btn_back_info=findViewById(R.id.btn_back_info);
        btnAcount=findViewById(R.id.btnAcount);
        btnPass=findViewById(R.id.btnPass);
        tvTentaikhoan=findViewById(R.id.tvTentaikhoan);
        tvPass=findViewById(R.id.tvPass);
        tvGmail=findViewById(R.id.tvGmail);
        tvSdt=findViewById(R.id.tvSdt);

        btn_back_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InforPersonActivity.this, AcountActivity.class);
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
        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InforPersonActivity.this, ChangePassActivity.class);
                startActivity(intent);
            }
        });
        CallApi();
    }

    private void CallApi() {
        String UserId =getIntent().getStringExtra("userId");
        ApiService.apiService.getUserfromId(UserId).enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful() && response.body() != null){
                    user = response.body();
                    tvTentaikhoan.setText(user.getName());
                    tvPass.setText(user.getPassword());
                    tvGmail.setText(user.getEmail());
                    tvSdt.setText(user.getPhone());
                    System.out.println("Get duoc User");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("=====", "Call fail");
                Log.e("=====", t.getMessage());
                System.out.println("Khong Get duoc User");
            }
        });
    }
}
