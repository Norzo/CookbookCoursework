package com.example.cookbook.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.cookbook.MainRoomDB;
import com.example.cookbook.dao.RecipeDao;
import com.example.cookbook.entities.Recipe;

import java.util.List;

public class RecipeRepository
{
    private RecipeDao recipeDao;
    private LiveData<List<Recipe>> mAllRecipes;

    private LiveData<Recipe> mRecipe;
    private long id;

    public RecipeRepository(Application application)
    {
        MainRoomDB db = MainRoomDB.getDatabase(application);
        recipeDao = db.recipeDao();
        mAllRecipes = recipeDao.getAllRecipes();
    }

    public LiveData<List<Recipe>> getAllRecipes()
    {
        return mAllRecipes;
    }

    public LiveData<List<Recipe>> getAllRecipesSortedBy() { return recipeDao.getAllRecipesSortedByRating(); }

    public long getRecipePK()
    {
        return this.id;
    }

    public long insert(final Recipe recipe)
    {
        MainRoomDB.databaseWriteExecutor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                id = recipeDao.insert(recipe);
            }
        });
        return this.id;
    }

    public LiveData<Recipe> getRecipe(int id)
    {
        mRecipe = recipeDao.getRecipe(id);
        return this.mRecipe;
    }

    public void update(final Recipe recipe)
    {
        MainRoomDB.databaseWriteExecutor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                recipeDao.update(recipe);
            }
        });
    }
}
