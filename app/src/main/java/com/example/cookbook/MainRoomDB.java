package com.example.cookbook;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.cookbook.dao.IngredientDao;
import com.example.cookbook.dao.RecipeDao;
import com.example.cookbook.dao.RecipeIngredientDao;
import com.example.cookbook.entities.Ingredient;
import com.example.cookbook.entities.Recipe;
import com.example.cookbook.entities.RecipeIngredient;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Ingredient.class, Recipe.class, RecipeIngredient.class}, version = 1, exportSchema = false)
public abstract class MainRoomDB extends RoomDatabase
{

    public abstract IngredientDao ingredientDao();
    public abstract RecipeDao recipeDao();
    public abstract RecipeIngredientDao recIngDao();

    private static MainRoomDB INSTANCE;

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(4);

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback()
    {
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db)
        {
            super.onOpen(db);

            databaseWriteExecutor.execute(new Runnable()
            {
                @Override
                public void run()
                {


                }
            });
        }
    };

    public static MainRoomDB getDatabase(final Context context)
    {
        if (INSTANCE == null)
            synchronized (MainRoomDB.class)
            {
                if (INSTANCE == null)
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MainRoomDB.class, "main_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
            }
        return INSTANCE;
    }
}
