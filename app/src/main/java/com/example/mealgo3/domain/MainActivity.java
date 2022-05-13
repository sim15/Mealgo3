package com.example.mealgo3.domain;


import com.example.mealgo3.search.FilteredRecipeSearch;
import com.fasterxml.jackson.databind.ObjectMapper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

import com.example.mealgo3.R;
import com.example.mealgo3.databinding.ActivityMainBinding;
import com.example.mealgo3.nav.MenuFragment;
import com.example.mealgo3.search.SearchFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    // primary view binding used to shorten some of the view accessing
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // load listeners. Primarily for navigation and general app options
        setListeners();

        // load all search box fragments for the search query
        loadSearch();

        // load all category chip groups to allow for selection of ingredients and categories
        try {
            loadCategoryChips();
        } catch (IOException e) {
            // handle improperly loaded category configuration file
            System.out.println("File not found");
        }
    }

    private void setListeners() {
        // upper left menu button
        binding.buttonMenu.setOnClickListener(v -> openMenu());

        // 'find recipes' button
        binding.filterButton.setOnClickListener(v ->
                filterRecipes()
                );
    }

    private void openMenu() {
        // initialize side nav menu fragment
        MenuFragment fragment = MenuFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_from_left, R.anim.enter_from_left, R.anim.exit_from_left);
        transaction.addToBackStack(null);
        transaction.add(R.id.menuFragment, fragment, "MENU_FRAGMENT").commit();

        fragment.setOnSelectedMenu(System.out::print);


    }

    private void loadSearch() {
        // 'include ingredients' ingredient search fragment
        loadChipGroupSearchFragment(R.id.includeIngredientsSearch, "SEARCH_FRAGMENT", binding.includeIngredientsChips, binding.includeIngredientsScroll);
        // 'exclude ingredients' ingredient search fragment
        loadChipGroupSearchFragment(R.id.excludeIngredientsSearch, "SEARCH_FRAGMENT", binding.excludeIngredientsChips, binding.excludeIngredientsScroll);
    }


    public SearchFragment loadSearchFragment(int containerViewId, String fragmentTag) {
        // load a single ingredient search fragment
        SearchFragment fragment = SearchFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // transaction.addToBackStack(null);

        transaction.add(containerViewId, fragment, fragmentTag).commit();
        return fragment;
    }

    // method to load a full search fragment, with chip group and search fragment
    public void loadChipGroupSearchFragment(int containerViewId, String fragmentTag, ChipGroup cg, HorizontalScrollView chipScrollbar) {
        loadSearchFragment(containerViewId, fragmentTag)
                .setOnSelectedItem(selectedIngredient -> {

                    if (cg.getChildCount() > 9 ) {return;}
                    // create new chip
                    Chip ingChip = new Chip(cg.getContext());

                    // add chip to chip group with the selected ingredient name
                    ingChip.setText(selectedIngredient.getIngredientName());

                    // remove the chip if it is clicked in the chip group
                    ingChip.setOnClickListener(view -> cg.removeView(ingChip));

                    // when a duplicate chip is added, add it to the front of the chip group
                    // and remove the duplicate.
                    // note that chip groups store views and thus cannot support dynamic updates
                    for (int i = 0; i < cg.getChildCount(); i++) {
                        if (((Chip) cg.getChildAt(i)).getText().toString()
                                .equals(selectedIngredient.getIngredientName())) {

                            cg.removeView(cg.getChildAt(i));

                        }
                    }

                    cg.addView(ingChip);

                    // Update horizontal scroll on the following UI-refresh iteration.
                    chipScrollbar.post(() -> chipScrollbar.fullScroll(HorizontalScrollView.FOCUS_RIGHT));

                });
    }

    public void loadCategoryChips() throws IOException {

        // load the categories from an external json file
        // map of ingredient categories and associated information
        Map<String, Map<String, ArrayList<String>>> ingredientCategories = new ObjectMapper().readValue(getAssets().open("categories.json"), HashMap.class);

        // add all chips to the chip view of recipe categories
        for (String category : ingredientCategories.keySet()) {
            Chip categoryChip = (Chip) getLayoutInflater().inflate(R.layout.category_chip, binding.categoryChips, false);
            categoryChip.setText(category);
            binding.categoryChips.addView(categoryChip);
        }
    }

    // fires when user clicks main search recipes button
    public void filterRecipes() {


        // store all selected recipe categories from chip group
        ArrayList<String> selectedCategories = new ArrayList<>();
        for (Integer id : binding.categoryChips.getCheckedChipIds()) {
            selectedCategories.add(((Chip) binding.categoryChips.findViewById(id)).getText().toString());
        }

        // store all selected included ingredients from chip group
        ArrayList<String> selectedIncludeIngredients = new ArrayList<>();
        for (int i = 0; i < binding.includeIngredientsChips.getChildCount(); i++) {
            selectedIncludeIngredients.add(
                    ((Chip) binding.includeIngredientsChips.getChildAt(i)).getText().toString()
            );
        }

        // store all selected excluded ingredients from chip group
        ArrayList<String> selectedExcludeIngredients = new ArrayList<>();
        for (int i = 0; i < binding.excludeIngredientsChips.getChildCount(); i++) {
            selectedExcludeIngredients.add(
                    ((Chip) binding.excludeIngredientsChips.getChildAt(i)).getText().toString()
            );
        }


        // check that at least one included ingredient is selected or at least one category
        if (selectedCategories.size() == 0 && selectedIncludeIngredients.size() == 0) {
            // popup/error message
            Toast toast = Toast.makeText(binding.getRoot().getContext(), "Please select at least one category or one included ingredient!", Toast.LENGTH_SHORT);
            toast.show();

            return;
        }

        // initialize new activity with filtered recipes
        Intent resultActivity = new Intent(getApplicationContext(), FilteredRecipeSearch.class);

        resultActivity.putExtra("selected_categories", selectedCategories);
        resultActivity.putExtra("selected_include_ingredients", selectedIncludeIngredients);
        resultActivity.putExtra("selected_exclude_ingredients", selectedExcludeIngredients);


        startActivity(resultActivity);
    }



}