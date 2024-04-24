package com.example.myapplication;

public class Weather {


    public static String detailsForTemp(double temp) {

        if(temp > 27) {
            return "Its hot today, drink more";
        }
        else if(temp > 20 && temp < 27) {
            return "Its cool today and may be hotter, keep an eye and drink water";
        }
        else {
            return "Its cold today, no hydration risk.";
        }
    }
}
