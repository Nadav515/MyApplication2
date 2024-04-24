package com.example.myapplication.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherService {
//?lat={lat}&lon={lon}&
    public interface OpenWeatherMapService {
        @GET("weather")
        Call<WeatherData.WeatherRoot> getCurrentWeatherData(
                @Query("lat") double lat,
                @Query("lon") double lon,
                @Query("appid") String apiKey);
    }

}
