package com.example.cookbook.activities;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.cookbook.entities.Ingredient;
import com.example.cookbook.viewmodels.IngredientsActivityViewModel;
import com.example.cookbook.adapters.IngredientsListAdapter;
import com.example.cookbook.R;

import java.util.List;

public class AllIngredientsActivity extends AppCompatActivity
{
    private RecyclerView mRecyclerView;
    private IngredientsActivityViewModel mIngredientsActivityViewModel;
    private LiveData<List<Ingredient>> mAllIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_ingredients);

        mRecyclerView = findViewById(R.id.recyclerview_ingredients);
        final IngredientsListAdapter adapter = new IngredientsListAdapter(this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mIngredientsActivityViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(IngredientsActivityViewModel.class);
        mAllIngredients = mIngredientsActivityViewModel.getAllUniqueIngredients();
        mIngredientsActivityViewModel.getAllUniqueIngredients().observe(this, new Observer<List<Ingredient>>()
        {
            @Override
            public void onChanged(List<Ingredient> ingredients)
            {
                adapter.setIngredients(ingredients);
            }
        });
    }

    public void exit(View view)
    {
        finish();
    }
}