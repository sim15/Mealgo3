package com.example.mealgo3.domain;

import static java.util.Map.entry;

import com.example.mealgo3.search.FilteredRecipeSearch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.example.mealgo3.R;
import com.example.mealgo3.data.Ingredient;
import com.example.mealgo3.databinding.ActivityMainBinding;
import com.example.mealgo3.databinding.ActivitySignInBinding;
import com.example.mealgo3.login.SignUpActivity;
import com.example.mealgo3.nav.MenuFragment;
import com.example.mealgo3.search.SearchFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Map<String,Map<String, ArrayList<String>>> ingredientCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setListeners();
        loadSearch();
        try {
            loadCategoryChips();
        } catch (IOException e) {
            System.out.println("File not found");
        }
    }

    private void setListeners() {
        binding.buttonMenu.setOnClickListener(v -> openMenu());
        binding.filterButton.setOnClickListener(v ->
                filterRecipes()
                );
    }

    private void openMenu() {
//        https://www.youtube.com/watch?v=BMTNaPcPjdw
        MenuFragment fragment = MenuFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_from_left, R.anim.enter_from_left, R.anim.exit_from_left);
        transaction.addToBackStack(null);
        transaction.add(R.id.menuFragment, fragment, "MENU_FRAGMENT").commit();

        fragment.setOnSelectedMenu(new MenuFragment.OnSelectedMenuItem() {
            @Override
            public void onSelectedMenu(String menuButton) {
                System.out.print(menuButton);
            }
        });


    }

    private void loadSearch() {
        loadChipGroupSearchFragment(R.id.includeIngredientsSearch, "SEARCH_FRAGMENT", binding.includeIngredientsChips, binding.includeIngredientsScroll);
        loadChipGroupSearchFragment(R.id.excludeIngredientsSearch, "SEARCH_FRAGMENT", binding.excludeIngredientsChips, binding.excludeIngredientsScroll);
    }

    public void OnFragmentInteraction(String sendBackText) {
        System.out.println(sendBackText);
    }


    public SearchFragment loadSearchFragment(int containerViewId, String fragmentTag) {
        SearchFragment fragment = SearchFragment.newInstance(new ArrayList<String>(), new ArrayList<String>());
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.addToBackStack(null);

        transaction.add(containerViewId, fragment, fragmentTag).commit();
        return fragment;
    }

    public void loadChipGroupSearchFragment(int containerViewId, String fragmentTag, ChipGroup cg, HorizontalScrollView chipScrollbar) {
        loadSearchFragment(containerViewId, fragmentTag)
                .setOnSelectedItem(new SearchFragment.OnSelectedItem() {
                    @Override
                    public void onSelectedItem(Ingredient selectedIngredient) {
                        Chip ingChip = new Chip(
                               cg.getContext()
                        );


                        ingChip.setText(selectedIngredient.getIngredientName());
                        ingChip.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                cg.removeView(ingChip);
                            }
                        });

                        for (int i = 0; i < cg.getChildCount(); i++) {
                            if (((Chip) cg.getChildAt(i)).getText().toString()
                                    .equals(selectedIngredient.getIngredientName())) {

                                cg.removeView(cg.getChildAt(i));
                            }
                        }

                        cg.addView(
                                ingChip
                        );

                        // Remove duplicate chips


                        chipScrollbar.fullScroll(HorizontalScrollView.FOCUS_RIGHT);

                        // Update horizontal scroll on following UI-refresh iteration.
                        chipScrollbar.post(new Runnable() {
                            public void run() {
                                chipScrollbar.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                            }
                        });

                    }
                });
    }

    public void loadCategoryChips() throws IOException {
//        Map<String, Map<String, ArrayList<String>>> categorySet = ;

//        InputStream getCategoryJson = new FileInputStream("categories.json");

        ingredientCategories =
                new ObjectMapper().readValue(getAssets().open("categories.json"), HashMap.class);

//        System.out.println(ingredientCategories.get("vegan").get("exclude"));

        for (String category : ingredientCategories.keySet()) {
            Chip categoryChip = (Chip) getLayoutInflater().inflate(R.layout.category_chip, binding.categoryChips, false);
            categoryChip.setText(category);
            binding.categoryChips.addView(categoryChip);
        }
    }

    public void filterRecipes() {
        Intent resultActivity = new Intent(getApplicationContext(), FilteredRecipeSearch.class);

        ArrayList<String> selectedCategories = new ArrayList<String>();
        for (Integer id : binding.categoryChips.getCheckedChipIds()) {
            selectedCategories.add(((Chip) binding.categoryChips.findViewById(id)).getText().toString());
        }

        ArrayList<String> selectedIncludeIngredients = new ArrayList<String>();
        for (int i = 0; i < binding.includeIngredientsChips.getChildCount(); i++) {
            selectedIncludeIngredients.add(
                    ((Chip) binding.includeIngredientsChips.getChildAt(i)).getText().toString()
            );
        }

        ArrayList<String> selectedExcludeIngredients = new ArrayList<String>();
        for (int i = 0; i < binding.excludeIngredientsChips.getChildCount(); i++) {
            selectedExcludeIngredients.add(
                    ((Chip) binding.excludeIngredientsChips.getChildAt(i)).getText().toString()
            );
        }

        resultActivity.putExtra("selected_categories", selectedCategories);
        resultActivity.putExtra("selected_include_ingredients", selectedIncludeIngredients);
        resultActivity.putExtra("selected_exclude_ingredients", selectedExcludeIngredients);

        startActivity(resultActivity);
    }



}