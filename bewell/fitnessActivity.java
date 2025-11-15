package com.example.bewell;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class fitnessActivity extends AppCompatActivity {

    private ListView listview1;
    private CustomAdapter adapter;
    private SearchView searchView;

    private final String[] names = new String[]{"Lunges(easy)", "Pushups(hard)", "Squats(easy)", "Pull-Up(hard)", "Side-Planks(hard)", "Planks(hard)", "Glute bridge(easy)", "Jumping Jack(easy)", "Crunches(hard)", "Sit Up(hard)"};
    private final String[] times = {"3sets 10reps", "3sets", "3sets 20reps", "3sets 7reps", "3sets of 10", "2-3sets 30sec", "3sets 10reps", "3sets of 25", "3sets of 20", "2sets of 10"};
    private final int[] images = {R.drawable.lunges, R.drawable.pushup, R.drawable.squat, R.drawable.pullup, R.drawable.sideplank, R.drawable.plank, R.drawable.glutebridge, R.drawable.jumpingjack, R.drawable.crunches, R.drawable.situp};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness);

        listview1 = findViewById(R.id.listview1);
        searchView = findViewById(R.id.searchView);

        adapter = new CustomAdapter(this, names, times, images);
        listview1.setAdapter(adapter);
        listview1.setOnItemClickListener((adapterView, view, i, l) -> Toast.makeText(fitnessActivity.this, "selected item:- " + names[i], Toast.LENGTH_SHORT).show());
        setupSearchView();
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<String> filteredData = getFilteredData(newText);

                adapter.updateNames(filteredData);

                listview1.setAdapter(adapter);
                return true;
            }

            private List<String> getFilteredData(String searchQuery) {

                List<String> filteredData = new ArrayList<>();

                for (String name : names) {
                    if (name.toLowerCase().contains(searchQuery.toLowerCase())) {
                        filteredData.add(name);
                    }
                }
                return filteredData;
            }
        });
    }
}
