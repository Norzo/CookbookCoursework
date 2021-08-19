package com.example.cookbook.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.cookbook.dao.IngredientDao;
import com.example.cookbook.MainRoomDB;
import com.example.cookbook.entities.Ingredient;

import java.util.List;

public class IngredientRepository
{
    private IngredientDao ingredientDao;
    private LiveData<List<Ingredient>> mAllIngredients;
    private int ingPointer = 0;
    private long[] id;

    public IngredientRepository(Application application)
    {
        MainRoomDB db = MainRoomDB.getDatabase(application);
        ingredientDao = db.ingredientDao();
        mAllIngredients = ingredientDao.getAllIngredients();
    }


    public void insert(final Ingredient[] ingredient)
    {
        MainRoomDB.databaseWriteExecutor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                id = ingredientDao.insert(ingredient);
            }
        });
    }

    public String getIngredientByName(String ingredientName) { return ingredientDao.getIngredientByName(ingredientName); }

    public LiveData<List<Ingredient>> getAllIngredients() { return mAllIngredients; }
    public LiveData<List<Ingredient>> getAllUniqueIngredients() { return ingredientDao.getAllUniqueIngredients(); }

    public long[] getIngredientPKs()
    {
        return this.id;
    }
}
