package com.example.mealgo3.data.recyclerview;

import android.annotation.SuppressLint;
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
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;


public class RecipeAdapter extends FirestoreRecyclerAdapter<Recipe, RecipeAdapter.RecipeHolder> {
    private OnItemClickListener listener;
    private final ArrayList<Map<String, ArrayList<String>>> categories;
    private final ArrayList<String> toExclude;

    public RecipeAdapter(@NonNull FirestoreRecyclerOptions<Recipe> options, ArrayList<Map<String, ArrayList<String>>> categories, ArrayList<String> exclusionFilter) {
        super(options);
        this.categories = categories;
        toExclude = exclusionFilter;
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onBindViewHolder(@NonNull RecipeHolder holder, int position, @NonNull Recipe model) {
        String toSet = model.getName();
        // Display capitalized name
        if (toSet.length() > 0) {
            toSet = toSet.substring(0, 1).toUpperCase() + toSet.substring(1);
        }

        holder.RecipeName.setText(toSet);
        holder.RecipeDescription.setText(model.getRecipeCategory());

        ArrayList<String> matches = returnMatchingCategories(model);

        // load only ingredients that fall into a provided category filter
        if (matches.size() == 0) {
            holder.view.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));
            return;
        } else {
            // This conditional branch is required due to the recycling of view holders
            // otherwise, other views will continue to be hidden when recycled.
            holder.itemView.setVisibility(View.VISIBLE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        // clear ingredients when loading stops. this is required to prevent duplicate chips at
        // refresh time.
        holder.MatchedCategories.removeAllViews();

        // load chips for matched recipe categories
        for (String matchedCat : matches) {
            Chip categoryChip = new Chip(holder.MatchedCategories.getContext());
            categoryChip.setText(matchedCat);
            categoryChip.setCheckable(false);
            categoryChip.setClickable(false);
            holder.MatchedCategories.addView(categoryChip);
        }

        // load recipe image (take the first available image)
        // if no image is available, load a default
        // if the URL is not functional, load a broken image icon
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
        ChipGroup MatchedCategories;


        public RecipeHolder(@NonNull View itemView) {
            super(itemView);
            RecipeName = itemView.findViewById(R.id.recipeName);
            RecipeDescription = itemView.findViewById(R.id.recipeDescription);
            RecipePreview = itemView.findViewById(R.id.recipePreview);
            MatchedCategories = itemView.findViewById(R.id.matchedCategories);


            view = itemView;

            itemView.setOnClickListener(v -> {
                int position = getAbsoluteAdapterPosition();
                // Error handling in the case when the item is clicked during  removal.
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.onItemClick(getSnapshots().getSnapshot(position), position);
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



    // return ArrayList of all category names that match the recipe
    // must include at least one of the 'include' ingredients
    // must NOT include a single one of the 'exclude' ingredients
    private ArrayList<String> returnMatchingCategories(Recipe r) {

        ArrayList<String> matches = new ArrayList<>();

        if ((toExclude.size() > 0) && !Collections.disjoint(r.getIngredients(), toExclude)) {
            return matches;
        }



        for (Map<String, ArrayList<String>> category : categories) {

            if ((Objects.requireNonNull(category.get("include")).size() == 0) ||
                    !Collections.disjoint(r.getIngredients(), Objects.requireNonNull(category.get("include")))) {

                if ((Objects.requireNonNull(category.get("exclude")).size() < 1) ||
                        Collections.disjoint(r.getIngredients(), Objects.requireNonNull(category.get("exclude")))) {
                    matches.add(Objects.requireNonNull(category.get("categoryName")).get(0));
                }
            }
        }

        return matches;

    }

//    public void filter(String text) {
//        items.clear();
//        if(text.isEmpty()){
//            items.addAll(itemsCopy);
//        } else{
//            text = text.toLowerCase();
//            for(PhoneBookItem item: itemsCopy){
//                if(item.name.toLowerCase().contains(text) || item.phone.toLowerCase().contains(text)){
//                    items.add(item);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }

}
