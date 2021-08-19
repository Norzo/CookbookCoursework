package com.example.cookbook.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cookbook.entities.Ingredient;
import com.example.cookbook.entities.Recipe;
import com.example.cookbook.entities.RecipeIngredient;
import com.example.cookbook.repositories.IngredientRepository;
import com.example.cookbook.repositories.RecipeIngredientRepository;
import com.example.cookbook.repositories.RecipeRepository;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel
{
    private RecipeRepository mRecRepo;
    private IngredientRepository mIngRepo;
    private RecipeIngredientRepository mRecIngRepo;

    private LiveData<List<Recipe>> mAllRecipes;
    private LiveData<List<Ingredient>> mAllIngredients;

    public MainActivityViewModel(@NonNull Application application)
    {
        super(application);
        mRecRepo = new RecipeRepository(application);
        mIngRepo = new IngredientRepository(application);
        mRecIngRepo = new RecipeIngredientRepository(application);
        mAllRecipes = mRecRepo.getAllRecipes();
        mAllIngredients = mIngRepo.getAllIngredients();
    }

    public LiveData<List<Recipe>> getAllRecipes() { return mAllRecipes; }
    public LiveData<List<Recipe>> getAllRecipesSortedByRating() { return mRecRepo.getAllRecipesSortedBy(); }
    public LiveData<List<Ingredient>> getAllIngredients() { return mAllIngredients; }
    public String getIngredientByName(String ingredientName) { return mIngRepo.getIngredientByName(ingredientName); }
    public int getRecipePK() { return (int) mRecRepo.getRecipePK(); }
    public long[] getIngredientPKs() { return mIngRepo.getIngredientPKs(); }

    public void insertRecipe(Recipe recipe) { mRecRepo.insert(recipe); }
    public void insertIngredient(Ingredient[] ingredient) { mIngRepo.insert(ingredient); }
    public void insertRecipeIngredient(RecipeIngredient recipeIngredient) { mRecIngRepo.insert(recipeIngredient); }
}
