package com.example.mealgo3.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.mealgo3.R;
import com.example.mealgo3.data.Ingredient;
import com.example.mealgo3.data.Nutrient;
import com.example.mealgo3.data.Recipe;
import com.example.mealgo3.data.recyclerview.IngredientAdapter;
import com.example.mealgo3.data.recyclerview.IngredientsViewHolder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private SearchFragment.OnSelectedItem listener;

    private RecyclerView recyclerView;
    private CollectionReference db;
    private String searchBarValue;
    private IngredientAdapter ingAdapter;

    private FirestoreRecyclerOptions<Ingredient> options;

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
        db = FirebaseFirestore.getInstance().collection("food-data");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            queryType = getArguments().getStringArrayList(String.valueOf(QUERY_TYPE));
        }

        options =
                new FirestoreRecyclerOptions.Builder<Ingredient>()
                        .setQuery(db.whereArrayContains("substrings", "pizza").limit(50), Ingredient.class)
                        .build();

        ingAdapter = new IngredientAdapter(options);


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


//        searchBarValue = String.valueOf(charSequence);
        // TODO 4/27/22: DRY-- potentially too much re-computation going on here.

        EditText searchBarBox = (EditText) view.findViewById(R.id.search_field);
        searchBarBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchBarValue = String.valueOf(charSequence).toLowerCase();
                Query newQuery = db.whereArrayContains("substrings", searchBarValue);
//                System.out.println(searchBarValue);

                updateRecyclerSearch(newQuery);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

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

// https://www.youtube.com/watch?v=3WR4QAiVuCw&list=PLrnPJCHvNZuAXdWxOzsN5rgG2M4uJ8bH1&index=6
//    TODO: Simon: left off at 6:02
    private void updateRecyclerSearch(Query searchQuery) {

//        TODO: Decide limit for number of items returned.
        options =
                new FirestoreRecyclerOptions.Builder<Ingredient>()
                        .setQuery(searchQuery.limit(50), Ingredient.class)
                        .build();

        ingAdapter.updateOptions(options);

        ingAdapter.startListening();
        recyclerView.setAdapter(ingAdapter);
        ingAdapter.setOnItemClickListener(new IngredientAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                Ingredient clicked = documentSnapshot.toObject(Ingredient.class);
//                System.out.println(clicked.getIngredientName());
                if (listener != null) {
                    listener.onSelectedItem(clicked);
                }
            }
        });
    }


    public interface OnSelectedItem {
        void onSelectedItem(Ingredient selectedIngredient);
    }

    public void setOnSelectedItem(SearchFragment.OnSelectedItem listener) {
        this.listener = listener;
    }

}