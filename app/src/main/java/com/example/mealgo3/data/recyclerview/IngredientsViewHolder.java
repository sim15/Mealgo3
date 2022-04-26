package com.example.mealgo3.data.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealgo3.R;

public class IngredientsViewHolder extends RecyclerView.ViewHolder {
    View view;
    public IngredientsViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView;
    }

    public void setDetails(String ingredientName) {
        TextView ingredient_name = (TextView) view.findViewById(R.id.randomText);
        ingredient_name.setText(ingredientName);
    }

//    public View getView(){
//        return view;
//    }
}
