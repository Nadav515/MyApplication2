package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    TextView tvLogin;
    EditText email, password, etUsername, etHeight, etWeight;
    Button signUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        tvLogin = findViewById(R.id.tvLogin);
        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        signUp = findViewById(R.id.btnSignUp);
        etUsername = findViewById(R.id.etUsername);
        etHeight = findViewById(R.id.etHeight);
        etWeight = findViewById(R.id.etWeight);

        signUp.setOnClickListener(this :: onClickSignUp);
        tvLogin.setOnClickListener(this :: OnClickLogin);

    }
    private void onClickSignUp(View v)
    {
        Log.e("XXX", "line56 signup = " );
        createAccount(email.getText().toString(), password.getText().toString());
    }

    private void OnClickLogin(View v) {
        setResult(RESULT_CANCELED);
        finish();
    }



    private void createAccount(String email, String password) {
        String username = etUsername.getText().toString();
        int height = Integer.parseInt(etHeight.getText().toString());
        double weight = Double.parseDouble(etWeight.getText().toString());
        if (Objects.equals(email, "") || Objects.equals(password, "") || username.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Please fill all fields.", Toast.LENGTH_SHORT).show();
            Log.e("XXX", "line73 signup = " );
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(SignUpActivity.this, "Authentication success.",
                                Toast.LENGTH_SHORT).show();

                        User u = new User(username, height, weight);
                        MyFirebase f = new MyFirebase();
                        f.addUser(u);
                        setResult(RESULT_OK);
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Log.e("XXX", "line 88 signup = " );
                        Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private User createUser(FirebaseUser userFromRegistration, String username, int height, double weight) {
        String userId = userFromRegistration.getUid();

        User user = new User(username, weight, height);
        mDatabase.child(userId).setValue(user);
        mDatabase.push();

        return user;
    }


}