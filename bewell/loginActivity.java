package com.example.bewell;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText loginEmail,loginPassword;
    private TextView forgotPassword, signupRedirectText, deleteacc;
    private Button loginButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth=FirebaseAuth.getInstance();
        loginEmail=findViewById(R.id.login_email);
        String email=getIntent().getStringExtra("email");
        loginEmail.setText(email);
        loginPassword=findViewById(R.id.login_password);
        deleteacc=findViewById(R.id.deleteacc);
        forgotPassword=findViewById(R.id.forgotPassword);
        signupRedirectText=findViewById(R.id.signupRedirectText);
        loginButton=findViewById(R.id.login_button);

        loginButton.setOnClickListener(view -> {
            String emailid=loginEmail.getText().toString();
            String pass = loginPassword.getText().toString();

            if (!emailid.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailid).matches()){
                if (!pass.isEmpty()){
                    auth.signInWithEmailAndPassword(emailid, pass)
                            .addOnSuccessListener(authResult -> {
                                    Toast.makeText(loginActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(loginActivity.this, homePage.class));
                                    Intent emailId = new Intent(getApplicationContext(), homePage.class);
                                    emailId.putExtra("emailId", loginEmail.getText().toString());
                                    startActivity(emailId);
                            }).addOnFailureListener(e -> Toast.makeText(loginActivity.this, "login Failed", Toast.LENGTH_SHORT).show());
                }else{
                    loginPassword.setError("Password cannot be empty");
                }
            } else if(emailid.isEmpty()) {
                loginEmail.setError("Email cannot be empty");
            }else {
                loginEmail.setError("Please enter valid Emailid");
            }
        });
                forgotPassword.setOnClickListener(view -> loginActivity.this.startActivity(new Intent(loginActivity.this, forgotPassword.class)));
        deleteacc.setOnClickListener(view -> loginActivity.this.startActivity(new Intent(loginActivity.this, DeleteActivity.class)));
                signupRedirectText.setOnClickListener(view -> startActivity(new Intent(loginActivity.this,signupActivity.class)));
    }
}