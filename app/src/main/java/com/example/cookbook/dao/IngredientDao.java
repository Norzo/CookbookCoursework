package com.example.cookbook.dao;
import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cookbook.entities.Ingredient;

import java.util.List;

@Dao
public interface IngredientDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insert(Ingredient[] ingredient);

    @Query("SELECT * FROM " + Ingredient.TABLE_NAME)
    LiveData<List<Ingredient>> getAllIngredients();

    @Query("SELECT ingredientname FROM " + Ingredient.TABLE_NAME + " WHERE ingredientname =:ingName")
    String getIngredientByName(String ingName);

    @Query("SELECT DISTINCT * FROM " + Ingredient.TABLE_NAME + " GROUP BY ingredientname")
    LiveData<List<Ingredient>> getAllUniqueIngredients();

    @Query("SELECT * FROM " + Ingredient.TABLE_NAME)
    Cursor getAllIngredientsContent();

    @Query("DELETE from " + Ingredient.TABLE_NAME)
    void deleteAll();
}
