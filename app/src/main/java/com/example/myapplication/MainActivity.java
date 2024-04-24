package com.example.myapplication;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.myapplication.weather.ApiClient;
import com.example.myapplication.weather.WeatherData;
import com.example.myapplication.weather.WeatherService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import im.delight.android.location.SimpleLocation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// This is the main activity class for the application.
// It extends AppCompatActivity and acts as the entry point for the app.
// It sets up the user interface and handles user interactions
// such as button clicks. In this case, it likely contains code
// to interact with UI components from activity_main.xml,
// processing user input, and possibly integrating with DBHelper to save and retrieve data.
public class MainActivity extends AppCompatActivity {
    private WeatherService.OpenWeatherMapService openWeatherMapService;
    private String apiKey = "d2c3e8ab3abfc00f1c05a80e15805c01";

    MyFirebase fb = MyFirebase.getInstance();

    TextView tvBMI, tvTemp;


    TextView weatherTev;
    ValueEventListener listener;

    TextView cupsTv;

    SimpleLocation simpleLocation;

    ActivityResultLauncher<String[]> permissionsLocation =registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), new ActivityResultCallback<Map<String, Boolean>>() {
        @Override
        public void onActivityResult(Map<String, Boolean> o) {
            if(!o.containsValue(false)) {
                updateWeatherData();
            }
        }
    });

    private void updateWeatherData() {
        Log.d("updateWeatherData", "Started");
        simpleLocation.beginUpdates();
        double lat = simpleLocation.getLatitude();
        double lng = simpleLocation.getLongitude();
        openWeatherMapService.getCurrentWeatherData(lat, lng, apiKey)
                .enqueue(new Callback<WeatherData.WeatherRoot>() {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onResponse(Call<WeatherData.WeatherRoot> call, Response<WeatherData.WeatherRoot> response) {
                       Log.d("getCurrentWeatherData", "OnResponse");
                        if(response.isSuccessful()) {
                            WeatherData.WeatherRoot data = response.body();
                            Log.d("getCurrentWeatherData", String.valueOf(response.code()));
                            if(data == null) return;
                            runOnUiThread(() -> {
                                if(data.main != null) {
                                    data.main.temp_max -=273.15;
                                    data.main.temp_min -=273.15;
                                    double celcius = ((data.main.temp_max + data.main.temp_min)) /2.0f;
                                    weatherTev.setText(
                                            String.format("City name: %s, Weather: %.2f,\n %s",
                                                    data.name, celcius,Weather.detailsForTemp(celcius))
                                    );
                                    Log.d("getWeatherData:", data.toString());
                                }else {
                                    Log.d("getWeatherData:", data.toString());
                                }
                            });

                        }
                        else {
                            try {
                                Log.d("Faulted on request", response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<WeatherData.WeatherRoot> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        simpleLocation = new SimpleLocation(this);
        weatherTev = findViewById(R.id.weathertext);
        openWeatherMapService = ApiClient.getInstance()
                .create(WeatherService.OpenWeatherMapService.class);

        tvBMI = findViewById(R.id.tvBMI);
        tvTemp = findViewById(R.id.tvTemp);
        cupsTv = findViewById(R.id.cups);

        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED
            || checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {

            permissionsLocation.launch(new String[] {
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION

            });

        }

        else {
            updateWeatherData();
        }


    }


    @Override
    protected void onPause() {
        super.onPause();
        if(listener != null) {
            fb.getUserRef().removeEventListener(listener);
            listener = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(listener != null) {
            fb.getUserRef().removeEventListener(listener);
        }
        listener = fb.getUserRef().addValueEventListener(new ValueEventListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user==null) return;
                tvBMI.setText(String.format("Your BMI is %.2f", user.getBmi()));

                int cupsToDrink = new BMI().waterCupsFor(user.getBmi());
                cupsTv.setText(String.format("You need to drink %d cups of water", cupsToDrink));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Water app")
                    .setMessage("Are you sure you want to logout?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseAuth.getInstance().signOut();
                            finish();
                            startActivity(new Intent(MainActivity.this, SignUpActivity.class));
                        }
                    })
                    .setNegativeButton("Cancel",null)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showErrorToast() {
        Toast.makeText(MainActivity.this, "Failed to load weather data", Toast.LENGTH_SHORT).show();
    }

}


