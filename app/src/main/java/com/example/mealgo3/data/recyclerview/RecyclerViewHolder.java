package com.example.mealgo3.data.recyclerview;
//package com.google.firebase.recyclerview;

import android.view.View;
import android.widget.TextView;
import com.example.mealgo3.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView view;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        view = itemView.findViewById(R.id.randomText);
    }

    public TextView getView(){
        return view;
    }
}