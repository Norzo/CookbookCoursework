package com.example.cookbook.contentprovider;

import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cookbook.R;

public class ContentProviderUser extends AppCompatActivity 
{

    SimpleCursorAdapter dataAdapter;

    Handler h = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_user);

        queryIngredients();
        queryRecipes();

        getContentResolver().
                registerContentObserver(
                        CookbookProviderContract.RECIPE_URI,
                        true,
                        new ChangeObserver(h));
    }

    class ChangeObserver extends ContentObserver
    {

        public ChangeObserver(Handler handler)
        {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange)
        {
            this.onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri)
        {
            queryRecipes();
            queryIngredients();
        }
    }

    public void queryRecipes()
    {

        String[] projection = new String[]
                {
                CookbookProviderContract.RECIPE_ID,
                CookbookProviderContract.RECIPE_NAME,
                CookbookProviderContract.RECIPE_INSTRUCTIONS,
                CookbookProviderContract.RECIPE_RATING
                };

        String colsToDisplay [] = new String[]
                {
                CookbookProviderContract.RECIPE_NAME,
                CookbookProviderContract.RECIPE_INSTRUCTIONS,
                CookbookProviderContract.RECIPE_RATING
                };

        int[] colResIds = new int[]
                {
                R.id.value1,
                R.id.value2,
                R.id.value3
                };

        Cursor cursor = getContentResolver().query(CookbookProviderContract.RECIPE_URI, projection, null, null, null);

        dataAdapter = new SimpleCursorAdapter(
                this,
                R.layout.item_layout,
                cursor,
                colsToDisplay,
                colResIds,
                0);

        ListView listView = (ListView) findViewById(R.id.recipeListView);
        listView.setAdapter(dataAdapter);
    }

    public void queryIngredients()
    {

        String[] projection = new String[] {
                CookbookProviderContract.INGREDIENT_ID,
                CookbookProviderContract.INGREDIENT_NAME
        };

        String colsToDisplay [] = new String[] {
                CookbookProviderContract.INGREDIENT_ID,
                CookbookProviderContract.INGREDIENT_NAME
        };

        int[] colResIds = new int[]
                {
                R.id.value1,
                R.id.value2,
                };

        Cursor cursor = getContentResolver().query(CookbookProviderContract.INGREDIENT_URI, projection, null, null, null);

        dataAdapter = new SimpleCursorAdapter(
                this,
                R.layout.item_layout,
                cursor,
                colsToDisplay,
                colResIds,
                0);

        ListView listView = (ListView) findViewById(R.id.ingredientListView);
        listView.setAdapter(dataAdapter);
    }

    public void add(View v)
    {

        final EditText nameField = (EditText) findViewById(R.id.editText1);
        String name = nameField.getText().toString();

        final EditText instructionsField = (EditText) findViewById(R.id.editText2);
        String instructions = instructionsField.getText().toString();

        final EditText ratingField = (EditText) findViewById(R.id.editText3);
        String rating = ratingField.getText().toString();

        ContentValues newValues = new ContentValues();
        newValues.put(CookbookProviderContract.RECIPE_NAME, name);
        newValues.put(CookbookProviderContract.RECIPE_RATING, rating);
        newValues.put(CookbookProviderContract.RECIPE_INSTRUCTIONS, instructions);

        getContentResolver().insert(CookbookProviderContract.RECIPE_URI, newValues);
    }
}
