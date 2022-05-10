package com.example.mealgo3.selectrecipes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.mealgo3.R;
import com.example.mealgo3.data.Recipe;
import com.example.mealgo3.domain.MainActivity;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class RecipeSelect extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Recipe> recipeArrayList;
    RecipeAdapter recipeAdapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_select);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();



        recyclerView = findViewById(R.id.recycleRecs);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        recipeArrayList = new ArrayList<Recipe>();
        recipeAdapter = new RecipeAdapter(RecipeSelect.this, recipeArrayList);

        recyclerView.setAdapter(recipeAdapter);

        EventChangeListener();

    }

    private void EventChangeListener() {

        db.collection("recipes").orderBy("textTitle", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error != null)   {
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestone error",error.getMessage());
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()) {

                            if (dc.getType() == DocumentChange.Type.ADDED) {

                                recipeArrayList.add(dc.getDocument().toObject(Recipe.class));
                            }

                            recipeAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();

                        }




                    }
                });
    }
}