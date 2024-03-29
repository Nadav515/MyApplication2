package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UploadActivity extends AppCompatActivity {
    private EditText waterIntakeEditText;
    private Button submitWaterIntakeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upload);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

            waterIntakeEditText = findViewById(R.id.waterIntakeEditText);
            submitWaterIntakeButton = findViewById(R.id.submitWaterIntakeButton);

            submitWaterIntakeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitWaterIntake();
                }
            });
        }

        private void submitWaterIntake() {
            String intakeString = waterIntakeEditText.getText().toString();
            double intake = Double.parseDouble(intakeString);
            // Handle the water intake value
        }
    }