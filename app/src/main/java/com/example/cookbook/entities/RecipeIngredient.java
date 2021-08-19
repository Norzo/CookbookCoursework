package com.example.cookbook.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity (tableName = RecipeIngredient.TABLE_NAME,
        //indices = @Index(value = {"ingredient_id"}, unique = false),
        primaryKeys = {"recipe_id", "ingredient_id"},
        foreignKeys = {
            @ForeignKey (entity = Recipe.class,
            parentColumns = "_id",
            childColumns =  "recipe_id"),
            @ForeignKey (entity = Ingredient.class,
            parentColumns = "_id",
            childColumns = "ingredient_id")
        })
public class RecipeIngredient
{
    public static final String TABLE_NAME = "recipe_ingredient_table";

    @ColumnInfo(name = "recipe_id")
    @NonNull
    public int recipeId;
    @ColumnInfo(name = "ingredient_id")
    @NonNull
    public int ingredientId;

    public RecipeIngredient(final int recipeId, final int ingredientId)
    {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
    }

    public long getRecipeId() { return this.recipeId; }
    public long getIngredientId() { return this.ingredientId; }
}
