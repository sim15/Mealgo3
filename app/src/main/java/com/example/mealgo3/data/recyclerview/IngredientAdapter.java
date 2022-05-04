package com.example.mealgo3.data.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealgo3.R;
import com.example.mealgo3.data.Ingredient;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class IngredientAdapter extends FirestoreRecyclerAdapter<Ingredient, IngredientAdapter.IngredientHolder> {
    private OnItemClickListener listener;

    public IngredientAdapter(@NonNull FirestoreRecyclerOptions<Ingredient> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull IngredientHolder holder, int position, @NonNull Ingredient model) {
        holder.ingredientName.setText(model.getIngredientName());
    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_search_item, parent, false);
        return new IngredientHolder(v);
    }

    public class IngredientHolder extends RecyclerView.ViewHolder {
        View view;

        TextView ingredientName;


        public IngredientHolder(@NonNull View itemView) {
            super(itemView);
            ingredientName = itemView.findViewById(R.id.randomText);
//            view = itemView;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAbsoluteAdapterPosition();
                    // Error handling in the case when the item is clicked during  removal.
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }



    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
