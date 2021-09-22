package com.example.buildingblock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buildingblock.Model.Property;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class PropertyDetailsActivity extends AppCompatActivity {
    private ImageView propertyImage;
    private TextView propertyCategory, propertyDescription, propertyPrice, propertyType;
    private String propertyID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);
        propertyID = getIntent().getStringExtra("pid");
        propertyImage = (ImageView) findViewById(R.id.property_image_details);
        propertyDescription = (TextView) findViewById(R.id.property_description_details);
        propertyCategory = (TextView) findViewById(R.id.property_category_details);
        propertyPrice = (TextView) findViewById(R.id.property_price_details);
        propertyType = (TextView) findViewById(R.id.property_type_details);

        getPropertyDetails(propertyID);

    }

    private void getPropertyDetails(String propertyID) {
        DatabaseReference propertyRef = FirebaseDatabase.getInstance().getReference().child("Property");

        propertyRef.child(propertyID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    Property property = snapshot.getValue(Property.class);
                    propertyType.setText(property.getPtype());
                    propertyDescription.setText(property.getDescription());
                    propertyCategory.setText(property.getCategory());
                    propertyPrice.setText(property.getPrice());
                    Picasso.get().load(property.getImage()).into(propertyImage);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}