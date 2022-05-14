package com.example.mealgo3.search;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mealgo3.R;
import com.example.mealgo3.data.Recipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class details extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        TextView nameView = findViewById(R.id.recipeName);
        ImageView imageView= findViewById(R.id.recipeImage);
        ListView ingredientView = findViewById(R.id.ingredientList);
        TextView ingredientTitle = findViewById(R.id.ingredientTitle);

        Intent i = getIntent();
        Recipe recipe = (Recipe)i.getSerializableExtra("RECIPE");


        // set all information to screen
        nameView.setText(recipe.getName());

        if(!recipe.getImages().isEmpty()) {
            Picasso
                    .get()
                    .load(recipe.getImages().get(0))
                    .into(imageView);
        }

        ingredientTitle.setText("Ingredients");



        ArrayList<String> ingredientlist = (ArrayList<String>) recipe.getIngredients();
        System.out.println(ingredientlist);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, R.layout.activity_listview, R.id.textView, ingredientlist);
        ingredientView.setAdapter(arrayAdapter);

    }


}