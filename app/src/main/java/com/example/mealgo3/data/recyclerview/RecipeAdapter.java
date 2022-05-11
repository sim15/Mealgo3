package com.example.mealgo3.data.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mealgo3.R;
import com.example.mealgo3.data.Recipe;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;


public class RecipeAdapter extends FirestoreRecyclerAdapter<Recipe, RecipeAdapter.RecipeHolder> {
    private OnItemClickListener listener;


    public RecipeAdapter(@NonNull FirestoreRecyclerOptions<Recipe> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecipeHolder holder, int position, @NonNull Recipe model) {
        String toSet = model.getName();
        // Display capitalized name
        if (toSet.length() > 0) {
            toSet = toSet.substring(0, 1).toUpperCase() + toSet.substring(1);
        }

        holder.RecipeName.setText(toSet);
        holder.RecipeDescription.setText(model.getRecipeCategory());

        if (model.getImages().size() > 0) {
            Picasso
                    .get()
                    .load(model.getImages().get(0))
                    .placeholder(R.drawable.progress_animation)
                    .fit()
                    .centerCrop()
                    .error(holder.RecipePreview.getContext().getResources().getDrawable(R.drawable.ic_baseline_broken_image_24))
                    .into(holder.RecipePreview);
        }
    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recipe_result, parent, false);
        return new RecipeHolder(v);
    }

    public class RecipeHolder extends RecyclerView.ViewHolder {
        View view;

        TextView RecipeName;
        TextView RecipeDescription;
        ImageView RecipePreview;


        public RecipeHolder(@NonNull View itemView) {
            super(itemView);
            RecipeName = itemView.findViewById(R.id.recipeName);
            RecipeDescription = itemView.findViewById(R.id.recipeDescription);
            RecipePreview = itemView.findViewById(R.id.recipePreview);

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

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
