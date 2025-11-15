package com.example.bewell;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.bewell.databinding.ActivityDietBinding;

import java.util.ArrayList;

public class dietActivity extends AppCompatActivity {

    TextView diet_category;
    ActivityDietBinding binding;
    ListAdapter listAdapter;
    ArrayList<ListData> dataArrayList = new ArrayList<>();
    ListData listData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDietBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        diet_category=findViewById(R.id.diet_category);
        String result=getIntent().getStringExtra("result");
        diet_category.setText(result);
        String[] words = result.split(" ");
        for (String word : words) {
            switch (word) {
                case "Underweight":
                    getUnderweight();
                    break;
                case "Normal":
                    getNormalweight();
                    break;
                case "Overweight":
                case "Obese":
                    getObese();
                    break;
            }
        }
        }

    private void getObese() {
        int[] imageList = {R.drawable.cheela, R.drawable.lime, R.drawable.bean, R.drawable.nicoise};
        int[] ingredientList = {R.string.cheelaIngredients, R.string.limeIngredients, R.string.beanIngredients, R.string.nicoiseIngredients};
        int[] descList = {R.string.cheelaDesc, R.string.limeDesc, R.string.beanDesc, R.string.nicoiseDesc};
        String[] nameList = {"Oats and Matar Cheela", "Coconut Lime Quinoa Salad", "Black Bean Soup", "Nicoise Salad"};
        String[] timeList = {"20 mins", "15 mins", "10 mins", "5 mins"};
        for (int i = 0; i < imageList.length; i++) {
            listData = new ListData(nameList[i], timeList[i], ingredientList[i], descList[i], imageList[i]);
            dataArrayList.add(listData);
        }
        listAdapter = new ListAdapter(dietActivity.this, dataArrayList);
        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(dietActivity.this, DetailedActivity.class);
            intent.putExtra("name", nameList[i]);
            intent.putExtra("time", timeList[i]);
            intent.putExtra("ingredients", ingredientList[i]);
            intent.putExtra("desc", descList[i]);
            intent.putExtra("image", imageList[i]);
            startActivity(intent);
        });
    }

    private void getNormalweight() {
        int[] imageList = {R.drawable.tomato, R.drawable.wrap, R.drawable.toast, R.drawable.smoothie};
        int[] ingredientList = {R.string.tomatoIngredients, R.string.wrapIngredients, R.string.toastIngredients, R.string.smoothieIngredients};
        int[] descList = {R.string.tomatoDesc, R.string.wrapDesc, R.string.toastDesc, R.string.smoothieDesc};
        String[] nameList = {"Tomato & Burrata Sandwich", "Cucumber & Avocado Wrap", "Ricotta-Tomato Toast", "Fruit & Yogurt Smoothie"};
        String[] timeList = {"10 mins", "5 mins", "10 mins", "5 mins"};
        for (int i = 0; i < imageList.length; i++) {
            listData = new ListData(nameList[i], timeList[i], ingredientList[i], descList[i], imageList[i]);
            dataArrayList.add(listData);
        }
        listAdapter = new ListAdapter(dietActivity.this, dataArrayList);
        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(dietActivity.this, DetailedActivity.class);
            intent.putExtra("name", nameList[i]);
            intent.putExtra("time", timeList[i]);
            intent.putExtra("ingredients", ingredientList[i]);
            intent.putExtra("desc", descList[i]);
            intent.putExtra("image", imageList[i]);
            startActivity(intent);
        });
    }

    private void getUnderweight() {
        int[] imageList = {R.drawable.oatmeal, R.drawable.sandwich, R.drawable.tofu, R.drawable.avacado, R.drawable.shake};
        int[] ingredientList = {R.string.oatmealIngredients, R.string.sandwichIngredients, R.string.tofuIngredients, R.string.avacadoIngredients, R.string.shakeIngredients};
        int[] descList = {R.string.oatmealDesc, R.string.sandwichDesc, R.string.tofuDesc, R.string.avacadoDesc, R.string.shakeDesc};
        String[] nameList = {"Oatmeal Peanut Porridge", "Peanut Butter And Jam Sandwich", "Tofu Scramble", "Avocado And Egg Sandwich", "Vegan Protein Shake"};
        String[] timeList = {"10 mins", "5 mins", "10 mins", "5 mins", "10 mins"};
        for (int i = 0; i < imageList.length; i++) {
            listData = new ListData(nameList[i], timeList[i], ingredientList[i], descList[i], imageList[i]);
            dataArrayList.add(listData);
        }
        listAdapter = new ListAdapter(dietActivity.this, dataArrayList);
        binding.listview.setAdapter(listAdapter);
        binding.listview.setClickable(true);
        binding.listview.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(dietActivity.this, DetailedActivity.class);
            intent.putExtra("name", nameList[i]);
            intent.putExtra("time", timeList[i]);
            intent.putExtra("ingredients", ingredientList[i]);
            intent.putExtra("desc", descList[i]);
            intent.putExtra("image", imageList[i]);
            startActivity(intent);
        });
    }
}