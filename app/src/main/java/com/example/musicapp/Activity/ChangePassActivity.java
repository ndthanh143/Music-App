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

import com.example.musicapp.Model.User;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.ApiService;
import com.example.musicapp.Service_Local.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassActivity extends AppCompatActivity {
    ImageButton btn_back_pass;
    EditText edtOldPass, edtNewPass, edtNewConfirmPass;
    Button btnUpdate;
    String userId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_matkhau);
        anhXa();
        userId = SharedPrefManager.getInstance(this).getUser().getId();
        btn_back_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChangePassActivity.this, AcountActivity.class);
                startActivity(intent);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtNewPass.equals(edtNewConfirmPass)) {
                    Toast.makeText(ChangePassActivity.this, "Mật khẩu nhập lại không trùng khớp", Toast.LENGTH_SHORT).show();
                } else {
                    CallApiUpdatePassword(edtOldPass.getText().toString(), edtNewPass.getText().toString());
                }
            }
        });

    }

    private void CallApiUpdatePassword(String oldPass, String newPass) {
        ApiService.apiService.changePassword(userId, oldPass, newPass).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(ChangePassActivity.this, "Password changed", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ChangePassActivity.this, AcountActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(ChangePassActivity.this, "Password changed fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhXa() {
        btn_back_pass = findViewById(R.id.btn_back_pass);
        edtOldPass = findViewById(R.id.edtOldPassword);
        edtNewPass = findViewById(R.id.edtNewPassword);
        edtNewConfirmPass = findViewById(R.id.edtNewConfirmPassword);
        btnUpdate = findViewById(R.id.btnUpdate);
    }
}
