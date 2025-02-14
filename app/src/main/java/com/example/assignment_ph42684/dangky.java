package com.example.assignment_ph42684;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class dangky extends AppCompatActivity {
    private FirebaseAuth mauth;
    private TextInputEditText edemail, edpassword, edrppassword;
    private Button btnsignup;
    private TextView txtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dangky);
        edemail = findViewById(R.id.edemail);
        edpassword = findViewById(R.id.edpassword);
        edrppassword = findViewById(R.id.edrppassword);
        btnsignup = findViewById(R.id.btnsignup);
        txtLogin = findViewById(R.id.txtLogin);
        mauth = FirebaseAuth.getInstance();
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edemail.getText().toString();
                String password = edpassword.getText().toString();
                String rppassword = edrppassword.getText().toString();
                if (email.equals("") || password.equals("") || rppassword.isEmpty()) {
                    Toast.makeText(dangky.this, "vui lòng nhập đầy đủ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.equals(rppassword)) {
                    Toast.makeText(dangky.this, "mật khẩu không khớp nhau!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isValidEmail(email)) {
                    Toast.makeText(dangky.this, "Địa chỉ email không hợp lệ!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.length() < 6 || !Character.isUpperCase(password.charAt(0))) {
                    Toast.makeText(dangky.this, "Mật khẩu phải có ít nhất 6 kí tự và viết hoa chữ cái đầu tiên!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mauth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(dangky.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = mauth.getCurrentUser();
                                    Intent in = new Intent(dangky.this, login.class);
                                    in.putExtra("email", email);
                                    in.putExtra("password", password);
                                    startActivity(in);
                                    Toast.makeText(dangky.this, "Đăng Kí Thành Công!",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(dangky.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(dangky.this, login.class);
                startActivity(in);
            }
        });

    }

    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}