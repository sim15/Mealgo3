package com.example.mealgo3.quiz;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


import androidx.core.app.ComponentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.example.mealgo3.R;
import com.example.mealgo3.databinding.ActivityChooseBinding;
import com.example.mealgo3.domain.MainActivity;
import com.example.mealgo3.nav.MenuFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import static com.example.mealgo3.R.*;

//fix menu fragment, button in menu fragment should open up quiz activity

public class ChooseActivity extends AppCompatActivity  {
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

        click.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String tell = String.valueOf(stars.getRating());
                Toast.makeText(getApplicationContext(), tell + "STAR", Toast.LENGTH_SHORT).show();

                Random r=new Random();
                int randomName=r.nextInt(names.length);
                food.setText(names[randomName]);

                String amount = String.valueOf(stars.getRating());
                System.out.print(amount);
                stars.setRating(0F);
            }});

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r=new Random();
                int randomName=r.nextInt(names.length);
                food.setText(names[randomName]);
                System.out.println("skipped");
            }

        });}


}

