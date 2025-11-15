package com.example.bewell;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class signupActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;  
    private FirebaseAuth auth;
    private EditText signupUser,signupEmail,signupPassword,signupConfirmPassword;
    private Button signupButton;
    private TextView loginRedirectText;
    
    
    @SuppressLint(value = "MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth=FirebaseAuth.getInstance();
        signupUser=findViewById(R.id.signup_user);
        signupEmail=findViewById(R.id.signup_email);
        signupPassword=findViewById(R.id.signup_password);
        signupConfirmPassword=findViewById(R.id.signup_confirmpassword);
        signupButton=findViewById(R.id.signup_button);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("user_data");
        loginRedirectText=findViewById(R.id.loginRedirectText);
        signupButton.setOnClickListener(view -> {
            String name=signupUser.getText().toString().trim();
            String user= signupEmail.getText().toString().trim();
            String pass=signupPassword.getText().toString().trim();
            String confirm=signupConfirmPassword.getText().toString().trim();

            if (name.isEmpty()){
                signupUser.setError("Username cannot be empty");
            }
            if (user.isEmpty()){
                signupEmail.setError("Email cannot be empty");
            }
            if (pass.isEmpty()){
                signupPassword.setError("Password cannot be empty");
            }
            if (confirm.isEmpty() || !pass.equals(confirm)){
                signupConfirmPassword.setError("Enter correct Password");
            }
            else {
                auth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(this::onComplete);
                storeDataInFirebase(name, user, pass);
            }
        });
        loginRedirectText.setOnClickListener(view -> signupActivity.this.startActivity(new Intent(signupActivity.this, loginActivity.class)));
    }
    private void storeDataInFirebase(String name, String user, String pass) {
        DatabaseReference newDataRef = databaseReference.child(user.replace(".","_dot_"));
        newDataRef.child("name").setValue(name);
        newDataRef.child("user").setValue(user);
        newDataRef.child("pass").setValue(pass);
    }
    private void onComplete(Task<AuthResult> task) {
        if (task.isSuccessful()) {
            Toast.makeText(signupActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(signupActivity.this, loginActivity.class));
            Intent email = new Intent(getApplicationContext(), loginActivity.class);
            email.putExtra("email", signupEmail.getText().toString());
            startActivity(email);
            finish();
        } else {
            Toast.makeText(signupActivity.this, "SignUp Failed" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}