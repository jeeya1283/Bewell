package com.example.bewell;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class homePage extends AppCompatActivity {

    private Button homepage_logout;
    private TextView homepage_bmi, welcome_home, homepage_result, homepage_diet, homepage_exercise;
    private ImageView homepage_water,homepage_step;

    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        homepage_logout=findViewById(R.id.homepage_logout);
        homepage_result=findViewById(R.id.homepage_result);
        homepage_water=findViewById(R.id.homepage_water);
        homepage_step=findViewById(R.id.homepage_step);
        String result=getIntent().getStringExtra("result");
        homepage_result.setText(result);
        homepage_diet=findViewById(R.id.homepage_diet);
        homepage_diet.setOnClickListener(v -> {
            if (homepage_result.getText().toString().isEmpty()){
                Toast.makeText(homePage.this, "Please Calculate your BMI First", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Diet plan according to your BMI Category!", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(homePage.this,dietActivity.class);
                intent.putExtra("result",result);
                startActivity(intent);
            }
        });
        homepage_exercise=findViewById(R.id.homepage_exercise);
        homepage_exercise.setOnClickListener(v -> {
            if (homepage_result.getText().toString().isEmpty()){
                Toast.makeText(homePage.this, "Please Calculate your BMI First", Toast.LENGTH_SHORT).show();
            }else{
                Intent intent=new Intent(homePage.this,fitnessActivity.class);
                startActivity(intent);
            }
        });
        welcome_home = findViewById(R.id.home_welcome);
        String emailId = getIntent().getStringExtra("emailId");
        String trimmedEmail = removeNumbersAndTextAfterAt(emailId);
        welcome_home.setText("Welcome, "+ trimmedEmail + "!");
        String emailId1 = getIntent().getStringExtra("emailId");
        String trimmedEmail1 = removeNumbersAndTextAfterAt(emailId1);
        welcome_home.setText("Welcome, "+ trimmedEmail1 + "!");
        homepage_bmi = findViewById(R.id.homepage_bmi);
        homepage_bmi.setOnClickListener(v -> {
            startActivity(new Intent(homePage.this, MainActivity.class));
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("emailId",emailId);
            intent.putExtra("result",result);
            startActivity(intent);
        });
        homepage_water.setOnClickListener(v -> Toast.makeText(homePage.this, "Have about 2.5 litres (9 glass) of fluids a day!", Toast.LENGTH_SHORT).show());
        homepage_step.setOnClickListener(v -> Toast.makeText(homePage.this, "A person should aim for 5000 steps per day!", Toast.LENGTH_SHORT).show());
        homepage_logout.setOnClickListener(v -> {
            Intent i = new Intent(homePage.this,loginActivity.class);
            startActivity(i);
        });
    }

    private String removeNumbersAndTextAfterAt(String emailId) {
        if (emailId != null) {
            String regex = "\\d+|@.*";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(emailId);
            return matcher.replaceAll("");
        } else {
            return "";
        }
    }
    }
