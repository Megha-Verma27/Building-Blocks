package com.example.buildingblock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class AdminCategoryActivity extends AppCompatActivity {
    private ImageView apartmentBuy, houseBuy;
    private ImageView apartmentRent, houseRent;
    private ImageView apartmentAuction, houseAuction;
    private Button LogoutBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        LogoutBtn = (Button) findViewById(R.id.admin_logout_button);

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        apartmentBuy = (ImageView) findViewById(R.id.apartment_buy);
        houseBuy = (ImageView) findViewById(R.id.house_buy);

        apartmentRent = (ImageView) findViewById(R.id.apartment_rent);
        houseRent = (ImageView) findViewById(R.id.house_rent);

        apartmentAuction = (ImageView) findViewById(R.id.apartment_auction);
        houseAuction = (ImageView) findViewById(R.id.house_auction);



        apartmentBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Apartment Buy");
                startActivity(intent);
            }
        });

        houseBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "House Buy");
                startActivity(intent);
            }
        });

        apartmentRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Apartment Rent");
                startActivity(intent);
            }
        });

        houseRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "House Rent");
                startActivity(intent);
            }
        });

        apartmentAuction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "Apartment Auction");
                startActivity(intent);
            }
        });

        houseAuction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("category", "House Auction");
                startActivity(intent);
            }
        });

    }
}