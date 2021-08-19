package com.example.cookbook.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cookbook.adapters.RecipeListAdapter;
import com.example.cookbook.entities.Recipe;
import com.example.cookbook.repositories.RecipeRepository;

import java.util.List;

public class RecipeActivityViewModel extends AndroidViewModel
{
    private RecipeRepository recipeRepository;

    LiveData<List<Recipe>> mAllRecipes;

    public RecipeActivityViewModel(@NonNull Application application)
    {
        super(application);
        recipeRepository = new RecipeRepository(application);
        mAllRecipes = recipeRepository.getAllRecipes();
    }

    public LiveData<Recipe> getRecipe(int id) { return recipeRepository.getRecipe(id); }
    public void update(Recipe recipe) { recipeRepository.update(recipe); }
}
