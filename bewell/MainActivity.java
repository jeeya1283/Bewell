package com.example.bewell;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private EditText editTextWeight, editTextHeight, editTextAge;
    private RadioGroup radioGroupGender;
    private Button buttonCalculate, buttonNext;
    private TextView textViewResult;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextAge = findViewById(R.id.editTextAge);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonNext=findViewById(R.id.buttonNext);
        textViewResult = findViewById(R.id.textViewResult);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("user_data");
        buttonCalculate.setOnClickListener(v -> calculateBMI());
        buttonNext.setOnClickListener(v -> getCategory());
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    private void calculateBMI() {

        String weightStr = editTextWeight.getText().toString();
        String heightStr = editTextHeight.getText().toString();
        String ageStr = editTextAge.getText().toString();

        if (weightStr.isEmpty() || heightStr.isEmpty() || ageStr.isEmpty()) {
            textViewResult.setText("Please fill in all fields.");
            buttonNext.setEnabled(false);
            return;
        }

        double weight = Double.parseDouble(weightStr);
        double height= Double.parseDouble(heightStr);
        int age = Integer.parseInt(ageStr);

        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        if (selectedGenderId == -1) {
            textViewResult.setText("Please select a gender.");
            buttonNext.setEnabled(false);
            return;
        }

        if (age <= 18){
            editTextAge.setError("BMI calculation for age 18 years or less than 18 years is not accurate");
            textViewResult.setText(" ");
            buttonNext.setEnabled(false);
            return;
        }
        if (age >= 120){
            editTextAge.setError("Please Enter your appropriate Age");
            textViewResult.setText(" ");
            buttonNext.setEnabled(false);
            return;
        }
        if (height >= 210){
            editTextHeight.setError("Please Enter your appropriate Height");
            textViewResult.setText(" ");
            buttonNext.setEnabled(false);
            return;
        }
        if (weight >= 130){
            editTextWeight.setError("Please Enter your appropriate Weight");
            textViewResult.setText(" ");
            buttonNext.setEnabled(false);
            return;
        }
        RadioButton selectedGender = findViewById(selectedGenderId);
        String gender = selectedGender.getText().toString();
        double bmi = weight / ((height/100) * (height/100));

        if (bmi < 16) {
            category = "Severely Underweight";
        } else if (bmi < 18.5) {
            category = "Underweight";
        } else if (bmi < 25) {
            category = "Normal Weight";
        } else if (bmi < 30) {
            category = "Overweight";
        } else {
            category = "Obese";
        }

        @SuppressLint("DefaultLocale") String result;
        result = String.format("BMI: %.2f\nCategory: %s", bmi, category);
        textViewResult.setText(result);
        storeDataInFirebase(gender, age, height, weight, bmi);
        buttonNext.setEnabled(true);

    }
    private void getCategory() {
        String EMAIL=getIntent().getStringExtra("emailId");
        Intent intent=new Intent(MainActivity.this,homePage.class);
        intent.putExtra("emailId",EMAIL);
        intent.putExtra("result",textViewResult.getText().toString());
        startActivity(intent);
    }

    private void storeDataInFirebase(String gender, int age, double height, double weight, double bmi) {
        String EMAIL=getIntent().getStringExtra("emailId");
        DatabaseReference newDataRef = databaseReference.child(EMAIL.replace(".","_dot_"));
        newDataRef.child("gender").setValue(gender);
        newDataRef.child("age").setValue(age);
        newDataRef.child("height").setValue(height);
        newDataRef.child("weight").setValue(weight);
        newDataRef.child("bmi").setValue(bmi);
    }
}