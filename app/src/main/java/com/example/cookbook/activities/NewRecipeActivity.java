package com.example.cookbook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cookbook.R;

public class NewRecipeActivity extends AppCompatActivity
{
    public static final String EXTRA_REPLY = "com.example.android.roomrecipessample.REPLY";
    private EditText mEditNameView, mEditInstructionsView, mEditIngredientsView, mEditRatingView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);

        mEditNameView = findViewById(R.id.edit_recipe_name);
        mEditInstructionsView = findViewById(R.id.edit_recipe_instructions);
        mEditIngredientsView = findViewById(R.id.edit_recipe_ingredients);
        mEditRatingView = findViewById(R.id.edit_recipe_rating);

        final Button button = findViewById(R.id.button_add);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                Intent intentAddRec = new Intent();
                if (TextUtils.isEmpty(mEditNameView.getText()))
                    setResult(RESULT_CANCELED, intentAddRec);
                else
                {
                    String recName = mEditNameView.getText().toString();
                    String recInstructions = mEditInstructionsView.getText().toString();
                    String allIngredients = mEditIngredientsView.getText().toString();
                    String[] recIngredients = allIngredients.split("\\r?\\n");
                    String recRating = mEditRatingView.getText().toString();
                    int sendRecRating;
                    if (recRating.matches(""))
                        sendRecRating = 0;
                    else
                        sendRecRating = Integer.parseInt(recRating);
                    intentAddRec.putExtra("newrecname", recName);
                    intentAddRec.putExtra("newrecins", recInstructions);
                    intentAddRec.putExtra("newrecing", recIngredients);
                    intentAddRec.putExtra("newrecrat", sendRecRating);
                    setResult(RESULT_OK, intentAddRec);
                }
                finish();
            }
        });
    }

    public void closeRecipe(View view)
    {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}