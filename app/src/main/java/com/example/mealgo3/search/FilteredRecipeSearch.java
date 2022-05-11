package com.example.mealgo3.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.example.mealgo3.R;
import com.example.mealgo3.data.Ingredient;
import com.example.mealgo3.data.Recipe;
import com.example.mealgo3.data.recyclerview.IngredientAdapter;
import com.example.mealgo3.data.recyclerview.RecipeAdapter;
import com.example.mealgo3.databinding.ActivityFilteredRecipeSearchBinding;
import com.example.mealgo3.databinding.ActivityMainBinding;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FilteredRecipeSearch extends AppCompatActivity {
    private ActivityFilteredRecipeSearchBinding binding;
    Bundle mainFilter;
    private Map<String, Map<String, ArrayList<String>>> ingredientCategories;



    private RecyclerView recyclerView;
    private CollectionReference db;
    private String searchBarValue;
    private RecipeAdapter recipeAdapter;

    private FirestoreRecyclerOptions<Recipe> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFilteredRecipeSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mainFilter = getIntent().getExtras();
        System.out.println(mainFilter.getStringArrayList("selected_categories").toString());
        System.out.println(mainFilter.getStringArrayList("selected_include_ingredients").toString());
        System.out.println(mainFilter.getStringArrayList("selected_exclude_ingredients").toString());

        try {
            loadCategories();
        } catch (IOException e) {
            e.printStackTrace();
            FilteredRecipeSearch.this.finish();
        }

        loadRecycler();

    }


    private void loadCategories() throws IOException {
        ingredientCategories =
                new ObjectMapper().readValue(getAssets().open("categories.json"), HashMap.class);
    }

    private void loadRecycler() {





        recyclerView = binding.recipeResults;
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new
                LinearLayoutManager(binding.getRoot().getContext())
        );

        db = FirebaseFirestore.getInstance().collection("recipes");
        
        options =
                new FirestoreRecyclerOptions.Builder<Recipe>()
                        .setQuery(db.whereArrayContains("ingredients", "mayonnaise").limit(500), Recipe.class)
                        .build();

        recipeAdapter = new RecipeAdapter(options);

        recipeAdapter.startListening();
        recyclerView.setAdapter(recipeAdapter);

        recipeAdapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Recipe clicked = documentSnapshot.toObject(Recipe.class);
                System.out.println(clicked.getName());
            }
        });
        
    }
}