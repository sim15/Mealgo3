package com.example.mealgo3.rating;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;



import static com.example.mealgo3.R.*;

//fix menu fragment, button in menu fragment should open up quiz activity

public class Choose extends AppCompatActivity  {
    RatingBar stars;
    Button click;
    TextView food;
    Button skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(layout.activity_choose);
        stars = findViewById(id.simpleRatingBar2);
        click = findViewById(id.submitButton2);
        food = findViewById(id.food2);
        String[] names = food.getResources().getStringArray(array.nomes);
        skip = findViewById(id.buttonskip2);

        click.setOnClickListener(v -> {

            String tell = String.valueOf(stars.getRating());
            Toast.makeText(getApplicationContext(), tell + "STAR", Toast.LENGTH_SHORT).show();

            Random r=new Random();
            int randomName=r.nextInt(names.length);
            food.setText(names[randomName]);

            String amount = String.valueOf(stars.getRating());
            System.out.print(amount);
            stars.setRating(0F);
        });

        skip.setOnClickListener(v -> {
            Random r=new Random();
            int randomName=r.nextInt(names.length);
            food.setText(names[randomName]);
        });}


}
