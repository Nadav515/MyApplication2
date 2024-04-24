package com.example.myapplication.weather;

import java.util.ArrayList;

public class WeatherData {
    public static class Clouds{
        public double all;
    }

    public static class Coord{
        public double lon;
        public double lat;
    }

    public class WeatherMain{
        public double temp;
        public double feels_like;
        public double temp_min;
        public double temp_max;
        public double pressure;
        public double humidity;
        public double sea_level;
        public double grnd_level;
    }

    public static class WeatherRoot {
        @Override
        public String toString() {
            return "WeatherRoot{" +
                    "coord=" + coord +
                    ", weather=" + weather +
                    ", base='" + base + '\'' +
                    ", main=" + main +
                    ", visibility=" + visibility +
                    ", wind=" + wind +
                    ", clouds=" + clouds +
                    ", dt=" + dt +
                    ", sys=" + sys +
                    ", timezone=" + timezone +
                    ", id=" + id +
                    ", name='" + name + '\'' +
                    ", cod=" + cod +
                    '}';
        }

        public Coord coord;
        public ArrayList<Weather> weather;
        public String base;
        public WeatherMain main;
        public double visibility;
        public Wind wind;
        public Clouds clouds;
        public double dt;
        public Sys sys;
        public double timezone;
        public double id;
        public String name;
        public double cod;
    }

    public static class Sys{
        public String country;
        public double sunrise;
        public double sunset;
    }

    public static class Weather{
        public double id;
        public String main;
        public String description;
        public String icon;
    }

    public static class Wind{
        public double speed;
        public double deg;
        public double gust;
    }
}
