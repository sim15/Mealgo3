package com.example.mealgo3.domain;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<Map<String, ArrayList<String>>> ingredientCategories;

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
        loadChipGroupSearchFragment(R.id.includeIngredientsSearch, "SEARCH_FRAGMENT", binding.includeIngredientsChips, binding.includeIngredientsScroll);
        loadChipGroupSearchFragment(R.id.excludeIngredientsSearch, "SEARCH_FRAGMENT", binding.excludeIngredientsChips, binding.excludeIngredientsScroll);
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

}