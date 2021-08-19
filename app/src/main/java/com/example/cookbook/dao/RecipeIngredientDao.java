package com.example.cookbook.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cookbook.entities.Ingredient;
import com.example.cookbook.entities.Recipe;
import com.example.cookbook.entities.RecipeIngredient;

import java.util.List;

@Dao
public interface RecipeIngredientDao
{
    @Insert
    void insert (RecipeIngredient recipeIngredient);

    @Query("SELECT * FROM " + RecipeIngredient.TABLE_NAME)
    LiveData<List<RecipeIngredient>> getAllRecipeIngredients();

    @Query("SELECT * FROM " + Recipe.TABLE_NAME + " INNER JOIN " + RecipeIngredient.TABLE_NAME + " ON " + Recipe.TABLE_NAME + "._id = " + RecipeIngredient.TABLE_NAME + ".recipe_id WHERE " +
            RecipeIngredient.TABLE_NAME + ".ingredient_id =:ingredientID")
    LiveData<List<Recipe>> getRecipesWithIngredient(final int ingredientID);

    @Query("SELECT * FROM " + Ingredient.TABLE_NAME + " INNER JOIN " + RecipeIngredient.TABLE_NAME + " ON " + Ingredient.TABLE_NAME + "._id = " + RecipeIngredient.TABLE_NAME + ".recipe_id WHERE " +
            RecipeIngredient.TABLE_NAME + ".recipe_id =:recipeID")
    LiveData<List<Ingredient>> getIngredientsWithRecipe(final int recipeID);
}