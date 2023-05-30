package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.musicapp.DTO.UserDTO;
import com.example.musicapp.Model.User;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.ApiService;
import com.example.musicapp.Service_Local.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edtEmail;
    EditText edtPassword;
    Button btnLogin;
    TextView tvGoToSignUp;
    private ProgressDialog mProgessDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        anhXa();
        mProgessDialog = new ProgressDialog(LoginActivity.this);
        mProgessDialog.setMessage("Please wait login ...");
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivities(new Intent[]{new Intent(this, MainActivity.class)});
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        tvGoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login() {
        mProgessDialog.show();
        final String email = edtEmail.getText().toString();
        final String password = edtPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mProgessDialog.dismiss();
            edtEmail.setError("Please enter your username");
            edtPassword.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            mProgessDialog.dismiss();
            edtPassword.setError("Please enter your password");
            edtPassword.requestFocus();
            return;
        }
        ApiService.apiService.login(email, password).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    mProgessDialog.dismiss();
                    try {
                        User user = response.body();
                        if (user != null) {
                            UserDTO dto = new UserDTO();
                            dto.setEmail(user.getEmail());
                            dto.setId(user.getId());
                            dto.setName(user.getName());
                            dto.setPhone(user.getPhone());
                            dto.setAvatar(user.getAvatar());


                            SharedPrefManager.getInstance(getApplicationContext()).userLogin(dto);
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                    } catch (RuntimeException e) {
                        e.printStackTrace();
                        System.out.println("Khong Dang nhap vao duoc");

                    }

                } else {
                    mProgessDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Tài khoản email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Tài khoản email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                mProgessDialog.dismiss();
            }
        });
    }

    private void anhXa() {
        edtEmail = findViewById(R.id.edtEmailLogin);
        edtPassword = findViewById(R.id.edtPasswordLogin);
        btnLogin = findViewById(R.id.btnLoginButton);
        tvGoToSignUp = findViewById(R.id.tvGoToSignUp);
    }
}