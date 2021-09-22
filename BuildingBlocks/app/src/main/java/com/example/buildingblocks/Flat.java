package com.example.buildingblocks;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Flat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat);
        getSupportActionBar().setTitle("Flats");
    }
}