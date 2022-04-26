package com.example.mealgo3.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealgo3.R;
import com.example.mealgo3.data.Ingredient;
import com.example.mealgo3.data.Nutrient;
import com.example.mealgo3.data.Recipe;
import com.example.mealgo3.data.recyclerview.IngredientsViewHolder;
import com.example.mealgo3.data.recyclerview.RandomNumListAdapter;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private DatabaseReference ingredientDatabase;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final ArrayList<String> QUERY_TYPE = new ArrayList<String>() {
        {
            add("ingredients");
        }
    };



    // TODO: Rename and change types of parameters
    private ArrayList<String> queryType;
    private ArrayList<Nutrient> selectedNutrients;
    private ArrayList<Ingredient> selectedIngredients;
    private ArrayList<Recipe> selectedRecipes;

    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String query1, String ... queries) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        ArrayList<String> queryParams = new ArrayList<String>() {
            {
                add(query1);
                for (String query : queries) {
                    add(query);
                }
            }
        };

        args.putStringArrayList(String.valueOf(QUERY_TYPE), queryParams);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        ingredientDatabase = FirebaseDatabase.getInstance().getReference("ingredient nutrition");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            queryType = getArguments().getStringArrayList(String.valueOf(QUERY_TYPE));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = view.findViewById(R.id.search_result_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new
                        LinearLayoutManager(view.getContext())
                );

        FirebaseRecyclerOptions<Ingredient> options =
                new FirebaseRecyclerOptions.Builder<Ingredient>()
                .setQuery(ingredientDatabase,Ingredient.class)
                .build();


        FirebaseRecyclerAdapter<Ingredient,IngredientsViewHolder> firebaseRecycler = new FirebaseRecyclerAdapter<Ingredient, IngredientsViewHolder>(options)
        {
            @Override
            protected void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position, @NonNull Ingredient model) {
                holder.setDetails(model.getIngredientName());
            }

            @NonNull
            @Override
            public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_search_item, parent, false);

                return new IngredientsViewHolder(view);
            }
        };
        firebaseRecycler.startListening();
        recyclerView.setAdapter(firebaseRecycler);


        return view;
    }

    public void sendBack(String sendBackText) {
        if (mListener != null) {
            mListener.onFragmentInteraction(sendBackText);
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String sendBackText);
    }
}