package com.example.mealgo3.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mealgo3.R;
import com.example.mealgo3.data.Recipe;
import com.squareup.picasso.Picasso;

import java.util.jar.Attributes;

public class details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView nameView = findViewById(R.id.recipeName);
        ImageView imageView= findViewById(R.id.recipeImage);
        RecyclerView recyclerView = findViewById(R.id.ingredientList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent i = getIntent();
        String Name = i.getStringExtra("NAME");
        Recipe recipe = (Recipe)i.getSerializableExtra("RECIPE");


        // set all information to screen
        nameView.setText(recipe.getName());

        if(!recipe.getImages().isEmpty()) {
            Picasso
                    .get()
                    .load(recipe.getImages().get(0))
                    .into(imageView);
        }


    }
}