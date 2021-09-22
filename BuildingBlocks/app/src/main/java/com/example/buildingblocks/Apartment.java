package com.example.buildingblocks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Apartment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment);
        getSupportActionBar().setTitle("Apartments ");
    }
}