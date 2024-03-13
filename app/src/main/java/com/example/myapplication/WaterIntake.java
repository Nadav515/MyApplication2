package com.example.myapplication;
//representing the water intake entity.
// It usually includes properties such as the date of intake,
// and the amount of water consumed.
// This class is used to manage and record the user's
// daily water consumption details.
public class WaterIntake {
    private String date; // Consider using a Date type instead
    private double amount; // in liters

    // Constructor
    public WaterIntake(String date, double amount) {
        this.date = date;
        this.amount = amount;
    }

    // Getters and Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
