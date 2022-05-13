package com.example.mealgo3.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import com.example.mealgo3.data.Recipe;
import com.example.mealgo3.data.recyclerview.RecipeAdapter;
import com.example.mealgo3.databinding.ActivityFilteredRecipeSearchBinding;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FilteredRecipeSearch extends AppCompatActivity {
    private ActivityFilteredRecipeSearchBinding binding;
    Bundle mainFilter;
    private Map<String, Map<String, ArrayList<String>>> ingredientCategories;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFilteredRecipeSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // load information from inclusion/selection of user
        mainFilter = getIntent().getExtras();

        // load categories from external JSON. Checked exception.
        try {
            loadCategories();
        } catch (IOException e) {
            e.printStackTrace();
            FilteredRecipeSearch.this.finish();
        }

        // load the list of recipes
        loadRecycler();

    }


    private void loadCategories() throws IOException {
        ingredientCategories =
                new ObjectMapper().readValue(getAssets().open("categories.json"), HashMap.class);
    }

    private void loadRecycler() {


        RecyclerView recyclerView = binding.recipeResults;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new
                LinearLayoutManager(binding.getRoot().getContext())
        );

        CollectionReference db = FirebaseFirestore.getInstance().collection("recipes");
        FirestoreRecyclerOptions<Recipe> options;

        // if no included ingredients are selected, load first N recipes
        // otherwise, filter up to 10 included ingredients
        // NOTE: this 10 item limit is a restriction of firebase, unfortunately.
        //       currently a limit of 1000 items in place-- can be adjusted for performance.
        if (mainFilter.getStringArrayList("selected_include_ingredients").size() == 0) {
            options = new FirestoreRecyclerOptions.Builder<Recipe>()
                    .setQuery(db.limit(1000), Recipe.class)
                    .build();
        } else {
            options = new FirestoreRecyclerOptions.Builder<Recipe>()
                    .setQuery(db.whereArrayContainsAny("ingredients", mainFilter.getStringArrayList("selected_include_ingredients")).limit(1000), Recipe.class)
                    .build();
        }



        ArrayList<Map<String, ArrayList<String>>> selectedCategories = new ArrayList<>();

        for (String cat : mainFilter.getStringArrayList("selected_categories")) {
            selectedCategories.add(ingredientCategories.get(cat));
        }

        RecipeAdapter recipeAdapter = new RecipeAdapter(options, selectedCategories, mainFilter.getStringArrayList("selected_exclude_ingredients"));

        recipeAdapter.startListening();
        recyclerView.setAdapter(recipeAdapter);

        // open a recipe when clicked
        recipeAdapter.setOnItemClickListener((documentSnapshot, position) -> {
            Recipe clicked = documentSnapshot.toObject(Recipe.class);
            assert clicked != null;
            System.out.println(clicked.getName());

            Intent intent = new Intent(FilteredRecipeSearch.this, details.class);

            intent.putExtra("RECIPE", clicked);
            startActivity(intent);
        });
        
    }
}