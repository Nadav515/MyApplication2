package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
// This is the main activity class for the application.
// It extends AppCompatActivity and acts as the entry point for your app.
// It sets up the user interface and handles user interactions
// such as button clicks. In this case, it likely contains code
// to interact with UI components from activity_main.xml,
// processing user input, and possibly integrating with DBHelper to save and retrieve data.
public class MainActivity extends AppCompatActivity {

    private EditText waterIntakeEditText;
    private Button submitWaterIntakeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


