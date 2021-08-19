package com.example.cookbook.entities;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = Recipe.TABLE_NAME)
public class Recipe
{
    public static final String TABLE_NAME = "recipe_table";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    @NonNull
    private int id;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @NonNull
    @ColumnInfo(name = "instructions")
    private String instructions;
    @ColumnInfo(name = "recipe_ingredients")
    private String ingredients;
    @ColumnInfo(name = "rating")
    private int rating;

    public Recipe(String name, String instructions, String ingredients, int rating)
    {
        this.name = name;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.rating = rating;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }

    public String getName()
    {
        return this.name;
    }

    public String getInstructions()
    {
        return this.instructions;
    }

    public String getIngredients() { return this.ingredients; }

    public int getRating()
    {
        return this.rating;
    }

    public void setRating(int rating)
    {
        this.rating = rating;
    }

}
