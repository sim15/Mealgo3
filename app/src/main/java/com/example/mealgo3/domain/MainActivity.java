package com.example.mealgo3.domain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mealgo3.R;
import com.example.mealgo3.databinding.ActivityMainBinding;
import com.example.mealgo3.nav.MenuFragment;
import com.example.mealgo3.search.SearchFragment;
import com.example.mealgo3.selectrecipes.RecipeSelect;

public class MainActivity extends AppCompatActivity implements SearchFragment.OnFragmentInteractionListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setListeners();
        loadSearch();
    }

    private void setListeners() {
        binding.buttonMenu.setOnClickListener(v -> openMenu());

        Button button = (Button) findViewById(R.id.recipeRecs);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RecipeSelect.class));
            }
        });
    }

    private void openMenu() {
//        https://www.youtube.com/watch?v=BMTNaPcPjdw
        MenuFragment fragment = MenuFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_from_left, R.anim.enter_from_left, R.anim.exit_from_left);
        transaction.addToBackStack(null);
        transaction.add(R.id.menuFragment, fragment, "MENU_FRAGMENT").commit();
    }

    private void loadSearch() {
        SearchFragment fragment = SearchFragment.newInstance("nutrients", "ingredients", "recipes");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.addToBackStack(null);
        transaction.add(R.id.searchFragment, fragment, "SEARCH_FRAGMENT").commit();
        System.out.println("YESS!!!");
    }

    @Override
    public void onFragmentInteraction(String sendBackText) {
        System.out.println(sendBackText);
    }


}