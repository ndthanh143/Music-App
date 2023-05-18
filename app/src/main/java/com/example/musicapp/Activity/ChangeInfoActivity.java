package com.example.musicapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.musicapp.DTO.UserSignupDTO;
import com.example.musicapp.Model.User;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.ApiService;
import com.example.musicapp.Service_Local.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangeInfoActivity extends AppCompatActivity {

    ImageButton btn_back;
    EditText editchangde;
    Button btnUpdate;
    private String userId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_taikhoan);
        btn_back = findViewById(R.id.btn_back_change_acount);
        editchangde = findViewById(R.id.edtOldPassword);
        btnUpdate = findViewById(R.id.btnUpdate);
        userId=SharedPrefManager.getInstance(getApplicationContext()).getUser().getId();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangeInfoActivity.this, AcountActivity.class);
                startActivity(intent);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userName = editchangde.getText().toString();
                UserSignupDTO dto = new UserSignupDTO();
                dto.setName(userName);
                UpdateNameAccount(userId,dto);
            }
        });
    }

    private void UpdateNameAccount(String userId, UserSignupDTO UserSignupDTO) {
        ApiService.apiService.updateName(userId,UserSignupDTO).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    Toast.makeText(ChangeInfoActivity.this, "Cập nhật tên thành công", Toast.LENGTH_SHORT).show();
                }
                else{
                    System.out.println("Ten thay doi khong duoc ...."+userId);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ChangeInfoActivity.this, "Cập nhật tên không thành công" , Toast.LENGTH_SHORT).show();

            }
        });
    }
}
