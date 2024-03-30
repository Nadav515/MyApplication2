package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ActivityResultLauncher<Intent> activityLauncher;
    TextView tvSignUp;
    EditText email, password;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        tvSignUp = findViewById(R.id.tvSignUp);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        login = findViewById(R.id.btnLogin);

        activityLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                o -> {
                    setResult(o.getResultCode());
                    if (o.getResultCode() == RESULT_OK) finish();
                });

        login.setOnClickListener(this :: onClickLogin);
        tvSignUp.setOnClickListener(this :: OnClickSignUp);
    }

    private void onClickLogin(View v) {
        login(email.getText().toString(), password.getText().toString());
    }

    private void OnClickSignUp(View v) {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        activityLauncher.launch(intent);
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Wrong Email Or Password!", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}