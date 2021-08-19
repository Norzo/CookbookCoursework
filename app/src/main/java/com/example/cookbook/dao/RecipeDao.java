package com.example.cookbook.dao;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cookbook.entities.Recipe;

import java.util.List;

@Dao
public interface RecipeDao
{
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Recipe recipe);

    @Query("SELECT * FROM " + Recipe.TABLE_NAME + " ORDER BY name ASC")
    LiveData<List<Recipe>> getAllRecipes();

    @Query("SELECT * FROM " + Recipe.TABLE_NAME + " WHERE _id =:recipeId")
    LiveData<Recipe> getRecipe(int recipeId);

    @Query("SELECT * FROM " + Recipe.TABLE_NAME + " ORDER BY rating ASC")
    LiveData<List<Recipe>> getAllRecipesSortedByRating();

    @Query("SELECT * FROM " + Recipe.TABLE_NAME)
    Cursor getAllRecipesContent();

    @Update
    void update(Recipe recipe);

    @Query("DELETE FROM " + Recipe.TABLE_NAME)
    void deleteAll();
}
