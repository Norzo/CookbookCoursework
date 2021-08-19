package com.example.cookbook.repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.cookbook.dao.IngredientDao;
import com.example.cookbook.MainRoomDB;
import com.example.cookbook.dao.RecipeDao;
import com.example.cookbook.dao.RecipeIngredientDao;
import com.example.cookbook.entities.RecipeIngredient;

import java.util.List;

public class RecipeIngredientRepository
{

    private RecipeIngredientDao recipeIngredientDao;

    private LiveData<List<RecipeIngredient>> mAllRecipeIngredients;

    public RecipeIngredientRepository(Application application)
    {
        MainRoomDB db = MainRoomDB.getDatabase(application);

        recipeIngredientDao = db.recIngDao();

        mAllRecipeIngredients = recipeIngredientDao.getAllRecipeIngredients();
    }

    public void insert(final RecipeIngredient recipeIngredient)
    {
        MainRoomDB.databaseWriteExecutor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                recipeIngredientDao.insert(recipeIngredient);
            }
        });
    }
}
