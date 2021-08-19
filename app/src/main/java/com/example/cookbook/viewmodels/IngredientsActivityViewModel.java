package com.example.cookbook.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cookbook.entities.Ingredient;
import com.example.cookbook.repositories.IngredientRepository;

import java.util.List;

public class IngredientsActivityViewModel extends AndroidViewModel
{
    private IngredientRepository mIngRepo;

    private LiveData<List<Ingredient>> mAllIngredients;

    public IngredientsActivityViewModel(@NonNull Application application)
    {
        super(application);
        mIngRepo = new IngredientRepository(application);
        mAllIngredients = mIngRepo.getAllIngredients();
    }

    public LiveData<List<Ingredient>> getAllIngredients() { return mAllIngredients; }
    public LiveData<List<Ingredient>> getAllUniqueIngredients() { return mIngRepo.getAllUniqueIngredients(); }
}
