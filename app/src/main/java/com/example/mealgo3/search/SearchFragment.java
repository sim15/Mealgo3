package com.example.mealgo3.search;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mealgo3.R;
import com.example.mealgo3.data.Ingredient;
import com.example.mealgo3.data.recyclerview.IngredientAdapter;
import com.example.mealgo3.domain.MainActivity;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

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
    private ProgressBar loadingProgressBar;
    private TextView noResults;

    private FirestoreRecyclerOptions<Ingredient> options;

    public SearchFragment() {
        // Required empty public constructor
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // ingredient database instance
        db = FirebaseFirestore.getInstance().collection("food-data");
        super.onCreate(savedInstanceState);

        // begin with empty substring search query
        options =
                new FirestoreRecyclerOptions.Builder<Ingredient>()
                        .setQuery(db.whereArrayContains("substrings", "").limit(50), Ingredient.class)
                        .build();




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        loadingProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        noResults = (TextView) view.findViewById(R.id.no_results);


        ingAdapter = new IngredientAdapter(options) {
            @Override
            public void onDataChanged() {

                loadingProgressBar.setVisibility(View.GONE);
                if (getItemCount() == 0) {
                    noResults.setVisibility(View.VISIBLE);
                }
            }

        };

        recyclerView = view.findViewById(R.id.search_result_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new
                LinearLayoutManager(view.getContext())
        );


        // create item click listener for clicked ingredient in search results
        // used to interface with MainActivity to store chip group of selected ingredients
        ingAdapter.setOnItemClickListener((documentSnapshot, position) -> {

            Ingredient clicked = documentSnapshot.toObject(Ingredient.class);

            if (listener != null) {
                listener.onSelectedItem(clicked);
            }

        });

        EditText searchBarBox = (EditText) view.findViewById(R.id.search_field);

        // text-search functionality
        searchBarBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            // request search query every time text is changed in input text box
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // convert to lowercase prior to completing a substring search
                searchBarValue = String.valueOf(charSequence).toLowerCase();
                Query newQuery = db.whereArrayContains("substrings", searchBarValue);

                updateRecyclerSearch(newQuery);
            }

            @Override
            public void afterTextChanged(Editable editable) {
//                ((ProgressBar) view.findViewById(R.id.progress_bar)).setVisibility(View.VISIBLE);
            }
        });

        // hide search bar suggestion box if the text box is not selected
        searchBarBox.setOnFocusChangeListener((view1, hasFocus) -> {
            if (hasFocus) {
                recyclerView.setClickable(false);
                recyclerView.setVisibility(View.VISIBLE);

            } else {
                recyclerView.setClickable(false);
                recyclerView.setVisibility(View.GONE);
                noResults.setVisibility(View.GONE);
            }
        });

        return view;
    }

    private void updateRecyclerSearch(Query searchQuery) {


        // adjustable query limit-- by default is at 50 items
        options =
                new FirestoreRecyclerOptions.Builder<Ingredient>()
                        .setQuery(searchQuery.limit(50), Ingredient.class)
                        .build();

        ingAdapter.updateOptions(options);

        ingAdapter.startListening();
        loadingProgressBar.setVisibility(View.VISIBLE);
        noResults.setVisibility(View.GONE);

        recyclerView.setAdapter(ingAdapter);



    }


    public interface OnSelectedItem {
        void onSelectedItem(Ingredient selectedIngredient);
    }

    public void setOnSelectedItem(SearchFragment.OnSelectedItem listener) {
        this.listener = listener;
    }

}