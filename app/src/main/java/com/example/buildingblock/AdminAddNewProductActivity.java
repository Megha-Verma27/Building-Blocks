package com.example.buildingblock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String CategoryName, Description, Price, Ptype, saveCurrentDate, saveCurrentTime;
    private Button AddNewPropertyButton;
    private ImageView InputPropertyImage;
    private EditText InputPropertyType, InputPropertyDescription, InputPropertyPrice;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String propertyRandomKey, downloadImageUrl;
    private StorageReference PropertyImageRef;
    private DatabaseReference PropertyRef;

    private ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);

        CategoryName = getIntent().getExtras().get("category").toString();
        PropertyImageRef = FirebaseStorage.getInstance().getReference().child("Property Images");
        PropertyRef = FirebaseDatabase.getInstance().getReference().child("Property");
        AddNewPropertyButton = (Button) findViewById(R.id.add_new_property);
        InputPropertyImage = (ImageView) findViewById(R.id.select_property_image);
        InputPropertyType = (EditText) findViewById(R.id.property_type);
        InputPropertyDescription = (EditText) findViewById(R.id.property_description);
        InputPropertyPrice = (EditText) findViewById(R.id.property_price);
        loadingBar = new ProgressDialog(this);


        InputPropertyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        AddNewPropertyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidatePropertyData();
            }
        });
    }

    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick && resultCode==RESULT_OK && data!=null)
        {
            ImageUri = data.getData();
            InputPropertyImage.setImageURI(ImageUri);
        }
    }

    private void ValidatePropertyData()
    {
        Description = InputPropertyDescription.getText().toString();
        Price = InputPropertyPrice.getText().toString();
        Ptype = InputPropertyType.getText().toString();
        
        
        if(ImageUri == null)
        {
            Toast.makeText(this, "Property Image is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Please Write property Description", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Price))
        {
            Toast.makeText(this, "Please write property price", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Ptype))
        {
            Toast.makeText(this, "Please write property type", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StorePropertyInformation();
        }
    }

    private void StorePropertyInformation()
    {
        loadingBar.setTitle("Add new Property");
        loadingBar.setMessage("Please wait, while we are adding the new property.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calender = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(calender.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calender.getTime());

        propertyRandomKey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = PropertyImageRef.child(ImageUri.getLastPathSegment() + propertyRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String message = e.toString();
                Toast.makeText(AdminAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(AdminAddNewProductActivity.this, "Property Image Uploaded successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }
                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "got property image url successfully...", Toast.LENGTH_SHORT).show();

                            SavePropertyInfoToDatabase();
                        }
                    }
                });

            }
        });
    }

    private void  SavePropertyInfoToDatabase()
    {
        HashMap<String, Object>propertyMap = new HashMap<>();
        propertyMap.put("pid", propertyRandomKey);
        propertyMap.put("date", saveCurrentDate);
        propertyMap.put("time", saveCurrentTime);
        propertyMap.put("description", Description);
        propertyMap.put("image", downloadImageUrl);
        propertyMap.put("category", CategoryName);
        propertyMap.put("price", Price);
        propertyMap.put("ptype", Ptype);

        PropertyRef.child(propertyRandomKey).updateChildren(propertyMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(AdminAddNewProductActivity.this, AdminCategoryActivity.class);
                            startActivity(intent);
                            loadingBar.dismiss();

                            Toast.makeText(AdminAddNewProductActivity.this, "Property is added successfully..", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            loadingBar.dismiss();

                            String message = task.getException().toString();
                            Toast.makeText(AdminAddNewProductActivity.this, "Error:" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}