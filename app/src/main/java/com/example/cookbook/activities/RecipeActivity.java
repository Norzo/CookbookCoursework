package com.example.cookbook.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.cookbook.R;
import com.example.cookbook.entities.Recipe;
import com.example.cookbook.viewmodels.RecipeActivityViewModel;

public class RecipeActivity extends AppCompatActivity
{
    private RecipeActivityViewModel mRecipeActivityModel;
    private TextView mTextRecipeName, mTextRecipeInstructions, mTextRecipeIngredients;
    private EditText mEditRating;
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        mRecipeActivityModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(RecipeActivityViewModel.class);
        mTextRecipeName = findViewById(R.id.text_recipe_name);
        mTextRecipeInstructions = findViewById(R.id.text_recipe_instructions);
        mTextRecipeIngredients = findViewById(R.id.text_recipe_ingredients);
        mEditRating = findViewById(R.id.text_recipe_rating);

        mRecipeActivityModel.getRecipe(getIntent().getIntExtra("recipeid", 0)).observe(this, new Observer<Recipe>()
        {
            @Override
            public void onChanged(Recipe recipe)
            {
                if (recipe != null)
                {
                    mRecipe = recipe;
                    mTextRecipeName.setText(mRecipe.getName());
                    Log.d("recipeactivity", mRecipe.getName());
                    mTextRecipeInstructions.setText(mRecipe.getInstructions());
                    mTextRecipeIngredients.setText(mRecipe.getIngredients());
                    int rating = mRecipe.getRating();
                    mEditRating.setText(Integer.toString(rating));
                }
            }
        });
    }

    public void updateRating(View view)
    {
        int newRating = Integer.parseInt(mEditRating.getText().toString());
        mRecipe.setRating(newRating);
        mRecipeActivityModel.update(mRecipe);
    }

    public void closeRecipe(View view)
    {
        finish();
    }
}