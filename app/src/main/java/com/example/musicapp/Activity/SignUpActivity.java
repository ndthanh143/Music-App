package com.example.musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.musicapp.DTO.UserSignupDTO;
import com.example.musicapp.Model.User;
import com.example.musicapp.R;
import com.example.musicapp.Service_API.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtUsername, edtEmail, edtPhone, edtPassword, edtRePassword;
    private String username, email, phone, password;
    private Button btnSignup;
    private ConstraintLayout layoutSignUp, layoutVerifyOtp;
    private EditText edtOtp;
    private Button btnVerifyOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);
        anhXa();

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = edtUsername.getText().toString();
                email = edtEmail.getText().toString();
                phone = edtPhone.getText().toString();
                password = edtPassword.getText().toString();
                if(username.trim().isEmpty() ||
                        username.trim().isEmpty()||
                        username.trim().isEmpty() ||
                        edtRePassword.getText().toString().trim().isEmpty() ||
                        phone.trim().isEmpty()) {
                    Toast.makeText(SignUpActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else if(!password.equals(edtPassword.getText().toString())){
                    Toast.makeText(SignUpActivity.this, "Mật khẩu nhập lại phải trùng khớp", Toast.LENGTH_SHORT).show();
                } else {
                    UserSignupDTO userSignupDTO = new UserSignupDTO();
                    userSignupDTO.setName(username);
                    userSignupDTO.setEmail(email);
                    userSignupDTO.setPassword(password);
                    userSignupDTO.setPhone(phone);
                    CallApiRegistration(userSignupDTO);
                }
            }
        });

    }

    private void CallApiRegistration(UserSignupDTO dto) {
        System.out.println(dto.getName() + dto.getEmail() + dto.getPassword() + dto.getPhone());
        ApiService.apiService.signUp(dto).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()) {
                    layoutVerifyOtp.setVisibility(View.VISIBLE);
                    btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String otp = edtOtp.getText().toString();
                            if(otp.trim().isEmpty()) {
                                Toast.makeText(SignUpActivity.this, "Vui lòng nhập mã xác thực OTP", Toast.LENGTH_SHORT).show();
                            } else {
                                CallApiVerifyOtp(email, otp);
                            }
                        }
                    });
                } else {
                    Toast.makeText(SignUpActivity.this, "Lỗi phát sinh", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(SignUpActivity.this, "Call API registration fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CallApiVerifyOtp(String email, String otp) {
        ApiService.apiService.verifyOtp(email, otp).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(SignUpActivity.this, "Đăng ký thành công, vui lòng đăng nhập!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println(t.getMessage());
                Toast.makeText(SignUpActivity.this, "OTP không hợp lệ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void anhXa() {
        edtUsername = findViewById(R.id.edtUsername);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhone);
        edtPassword = findViewById(R.id.edtPassword);
        edtRePassword = findViewById(R.id.edtRePassword);
        btnSignup = findViewById(R.id.btnSignUp);

        layoutSignUp = findViewById(R.id.layoutSignup);
        layoutVerifyOtp = findViewById(R.id.layoutVerifyOtp);

        edtOtp = findViewById(R.id.edtOtp);
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);
    }


}