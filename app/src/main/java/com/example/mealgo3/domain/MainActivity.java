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

public class MainActivity extends AppCompatActivity {

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

        fragment.setOnSelectedItem(new SearchFragment.OnSelectedItem() {
            @Override
            public void onSelectedItem(Ingredient selectedIngredient) {
                Chip ingChip = new Chip(
                        binding.selectedChipGroup.getContext()
                );


                ingChip.setText(selectedIngredient.getIngredientName());
                ingChip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        binding.selectedChipGroup.removeView(ingChip);
                    }
                });

                for (int i = 0; i < binding.selectedChipGroup.getChildCount(); i++) {
                    if (((Chip) binding.selectedChipGroup.getChildAt(i)).getText().toString()
                            .equals(selectedIngredient.getIngredientName())) {

                        binding.selectedChipGroup.removeView(binding.selectedChipGroup.getChildAt(i));
                    }
                }

                binding.selectedChipGroup.addView(
                    ingChip
                );

                // Remove duplicate chips


                binding.ingredientChips.fullScroll(HorizontalScrollView.FOCUS_RIGHT);

                // Update horizontal scroll on following UI-refresh iteration.
                binding.ingredientChips.post(new Runnable() {
                    public void run() {
                        binding.ingredientChips.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                    }
                });

            }
        });


    }

}