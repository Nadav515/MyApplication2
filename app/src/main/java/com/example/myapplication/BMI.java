package com.example.myapplication;

public class BMI {



    public int waterCupsFor(double bmi) {
        if(bmi > 0.2 && bmi < 0.4) {
            return 10;
        }
        if(bmi >= 0.1 && bmi <= 0.2) {
            return 6;
        }
        else if(bmi > 0.05 && bmi <= 0.1) {
            return 5;
        }
        else if(bmi >= 0.01 && bmi <= 0.05) {
            return 4;
        }
        else {
            if(bmi > 0.4) {
                return 15;
            }
            else {
                return 3;
            }
        }
    }
}
