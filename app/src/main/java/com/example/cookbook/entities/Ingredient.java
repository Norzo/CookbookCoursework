package com.example.cookbook.entities;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = Ingredient.TABLE_NAME)
public class Ingredient
{
    public static final String TABLE_NAME = "ingredient_table";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    @NonNull
    private int id;

    @ColumnInfo(name = "ingredientname")
    @NonNull
    private String ingredientName;

    public Ingredient(String ingredientName)
    {
        this.ingredientName = ingredientName;
    }

    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    public String getIngredientName() { return ingredientName; }
}
