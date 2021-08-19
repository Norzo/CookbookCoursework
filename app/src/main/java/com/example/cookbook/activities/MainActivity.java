package com.example.cookbook.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.cookbook.entities.Ingredient;
import com.example.cookbook.entities.RecipeIngredient;
import com.example.cookbook.viewmodels.MainActivityViewModel;
import com.example.cookbook.R;
import com.example.cookbook.entities.Recipe;
import com.example.cookbook.adapters.RecipeListAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeListAdapter.ItemClickListener
{

    public static final int NEW_RECIPE_ACTIVITY_REQUEST_CODE = 1;
    private RecyclerView mRecyclerView;
    private MainActivityViewModel mMainActivityViewModel;
    private LiveData<List<Ingredient>> mIngredients;
    private LiveData<List<Recipe>> mRecipes;
    private RecipeListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(MainActivity.this, NewRecipeActivity.class);
                startActivityForResult(intent, NEW_RECIPE_ACTIVITY_REQUEST_CODE);
            }
        });

        mRecyclerView = findViewById(R.id.recyclerview);
        final RecipeListAdapter adapter = new RecipeListAdapter(this, this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mMainActivityViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainActivityViewModel.class);
        mIngredients = mMainActivityViewModel.getAllIngredients();
        mRecipes = mMainActivityViewModel.getAllRecipes();
        mMainActivityViewModel.getAllRecipes().observe(this, new Observer<List<Recipe>>()
        {
            @Override
            public void onChanged(List<Recipe> recipes)
            {
                adapter.setRecipes(recipes);
            }
        });
        this.mAdapter = adapter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, final Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_RECIPE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK)
        {
            final boolean[] flag = {true}; // flag to make sure that the observer doesnt run twice, avoids the application for adding recipe and ingredients twice
            final String[] newIngredients = data.getStringArrayExtra("newrecing"); // string array of ingredient names
            Ingredient[] ingredients = new Ingredient[newIngredients.length]; // array of ingredients to insert to the database
            String ingToRec = ""; // specific string to have all the ingredients in one string, in order to add that to the recipe table
            for (int i = 0; i < newIngredients.length; i++)
            {
                Ingredient tempIngredient = new Ingredient(newIngredients[i]); // create the ingredient object
                ingredients[i] = tempIngredient; // add the ingredient object to the array of ingredients
                if (i != newIngredients.length - 1)
                    ingToRec += newIngredients[i] + ", "; // add the name of the ingredient to a string to end up with e.g. "Salt, Milk, Chicken"
                else
                    ingToRec += newIngredients[i]; // last entry shouldn't have a ,
            }
            mMainActivityViewModel.insertIngredient(ingredients); // insert all of the ingredients to the database
            // create the recipe object
            final Recipe recipe = new Recipe(data.getStringExtra("newrecname"), data.getStringExtra("newrecins"), ingToRec, data.getIntExtra("newrecrat", 0));
            mMainActivityViewModel.insertRecipe(recipe); // add the recipe object to the recipe table

            mRecipes.observe(this, new Observer<List<Recipe>>()
            {
                @Override
                public void onChanged(List<Recipe> recipes)
                {
                    if (mMainActivityViewModel.getRecipePK() > 0 && mMainActivityViewModel.getIngredientPKs() != null && flag[0]) // a double check to make sure that the primary keys arent null
                    {
                        long[] ingredientPKs = mMainActivityViewModel.getIngredientPKs(); // gets the array of ids from the view model
                        for (int i = 0; i < ingredientPKs.length; i++)
                        {
                            Log.d("ingredient pk", ingredientPKs[i] + " recipe pk " + mMainActivityViewModel.getRecipePK());
                            mMainActivityViewModel.insertRecipeIngredient(new RecipeIngredient(mMainActivityViewModel.getRecipePK(), (int) ingredientPKs[i]));
                        }
                        Log.d("recipeingredient for loop over", "");
                        flag[0] = false;
                    }
                }
            });
        }
        else if (resultCode != RESULT_CANCELED)
            Toast.makeText(getApplicationContext(), R.string.empty_input, Toast.LENGTH_LONG).show();
    }

    public void sortRVByRating(View view)
    {
        mMainActivityViewModel.getAllRecipesSortedByRating().observe(this, new Observer<List<Recipe>>()
        {
            @Override
            public void onChanged(List<Recipe> recipes)
            {
                mAdapter.setRecipes(recipes);
            }
        });
    }

    public void sortRVByName(View view)
    {
        mMainActivityViewModel.getAllRecipes().observe(this, new Observer<List<Recipe>>()
        {
            @Override
            public void onChanged(List<Recipe> recipes)
            {
                mAdapter.setRecipes(recipes);
            }
        });
    }

    public void navToIngredientActivity(View view)
    {
        Intent intent = new Intent(this, AllIngredientsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClicked(int position)
    {
        int recId = mAdapter.getRecipes().get(position).getId();
        Log.d("recycleritem click check", recId + "");
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra("recipeid", recId);
        startActivity(intent);
    }
}