package com.example.myapplication;
// This Java class represents the user entity in the application.
// It typically contains properties like username, weight, height,
// and methods related to the user, such as calculating the BMI
// (Body Mass Index) based on the user's weight and height.
// This class is used to create User objects that hold user-specific data.
public class User {
    private String username;
    private double weight; // in kilograms
    private double height; // in meters

    private double bmi;

    // Constructor
    public User(String username, double weight, double height) {
        this.username = username;
        this.weight = weight;
        this.height = height;
        this.bmi = weight / (height * height);
    }

    public User() {}

    public double getBmi() {
        return bmi;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
