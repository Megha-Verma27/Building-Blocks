package com.example.buildingblocks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Sign_Up extends AppCompatActivity {
    EditText txtEmail, txtPassword, txtConfirmPassword;
    Button btn_register;
    ProgressBar progressBar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        getSupportActionBar().setTitle("Create New Account");

        txtEmail = (EditText)findViewById(R.id.txt_email);
        txtPassword = (EditText)findViewById(R.id.txt_password);
        txtConfirmPassword = (EditText)findViewById(R.id.txt_confirm_password);
        btn_register = (Button)findViewById(R.id.buttonRegister);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_register.setOnClickListener(new View.OnClickListener() {


            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                String confirmPassword = txtConfirmPassword.getText().toString().trim();

                if(TextUtils.isEmpty(email))
                {
                    Toast.makeText(Sign_Up.this, "Please Enter Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password))
                {
                    Toast.makeText(Sign_Up.this, "Please Enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(confirmPassword))
                {
                    Toast.makeText(Sign_Up.this, "Please Confirm Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.length()<6)
                {
                    Toast.makeText(Sign_Up.this, "Weak Password", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.VISIBLE);

                if(password.equals(confirmPassword))
                {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(Sign_Up.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        startActivity(new Intent(getApplication(), Log_In.class));
                                        Toast.makeText(Sign_Up.this, "Registration Complete", Toast.LENGTH_SHORT ).show();
                                    } else {

                                        Toast.makeText(Sign_Up.this, "Authentication Failed", Toast.LENGTH_SHORT ).show();

                                    }

                                    // ...
                                }
                            });
                }
            }
        });
    }
}