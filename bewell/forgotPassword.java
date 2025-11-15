package com.example.bewell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class forgotPassword extends AppCompatActivity {

    private FirebaseAuth auth;
    private String email;
    private EditText forgotPasswordEmail;
    private TextView forgotPasswordBack;
    private Button forgotPasswordButton;

    @SuppressLint({"WrongViewCast", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        auth=FirebaseAuth.getInstance();
        forgotPasswordEmail=findViewById(R.id.forgotPassword_email);
        forgotPasswordBack=findViewById(R.id.forgotPassword_back);
        forgotPasswordBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(forgotPassword.this,loginActivity.class));
            }
        });
        forgotPasswordButton=findViewById(R.id.forgotPassword_button);
        forgotPasswordButton.setOnClickListener(view -> {
            validateData();
        });
    }

            private void validateData() {
                email = forgotPasswordEmail.getText().toString();
                if (email.isEmpty()){
                    forgotPasswordEmail.setError("Required");
                } else{
                    forgotPass();
                }
            }

            private void forgotPass() {
                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(forgotPassword.this, "Checkout your Email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(forgotPassword.this, loginActivity.class));
                                finish();
                            } else {
                                Toast.makeText(forgotPassword.this, "Error :" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
    }

    }