package com.example.cookbook.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.FileNotFoundException;

public class CookbookProvider extends ContentProvider 
{

    private DBHelper dbHelper = null;

    private static final UriMatcher uriMatcher;

    static
    {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(CookbookProviderContract.AUTHORITY, "recipe", 1);
        uriMatcher.addURI(CookbookProviderContract.AUTHORITY, "recipe/#", 2);
        uriMatcher.addURI(CookbookProviderContract.AUTHORITY, "ingredient", 3);
        uriMatcher.addURI(CookbookProviderContract.AUTHORITY, "ingredient/#", 4);
        uriMatcher.addURI(CookbookProviderContract.AUTHORITY, "*", 7);
    }

    @Override
    public boolean onCreate()
    {
        Log.d("g53mdp", "contentprovider oncreate");
        this.dbHelper = new DBHelper(this.getContext(), "mydb", null, 1);
        return true;
    }

    @Override
    public String getType(Uri uri)
    {

        String contentType;

        if (uri.getLastPathSegment()==null) {
            contentType = CookbookProviderContract.CONTENT_TYPE_MULTIPLE;
        } else {
            contentType = CookbookProviderContract.CONTENT_TYPE_SINGLE;
        }

        return contentType;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values)
    {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String tableName;

        switch(uriMatcher.match(uri))
        {
            case 1:
                tableName = "recipe";
                break;
            case 3:
                tableName = "ingredient";
                break;
            default:
                tableName = "people";
                break;
        }

        long id = db.insert(tableName, null, values);
        db.close();
        Uri nu = ContentUris.withAppendedId(uri, id);

        Log.d("g53mdp", nu.toString());

        getContext().getContentResolver().notifyChange(nu, null);

        return nu;
    }


    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Log.d("g53mdp", uri.toString() + " " + uriMatcher.match(uri));

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch(uriMatcher.match(uri)) {
            case 2:
                selection = "RECIPE_ID = " + uri.getLastPathSegment();
            case 1:
                return db.query("recipe", projection, selection, selectionArgs, null, null, sortOrder);
            case 4:
                selection = "INGREDIENT_ID = " + uri.getLastPathSegment();
            case 3:
                return db.query("ingredient", projection, selection, selectionArgs, null, null, sortOrder);
            default:
                return null;
        }
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs)
    {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        throw new UnsupportedOperationException("not implemented");
    }

    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException
    {
        throw new UnsupportedOperationException("not implemented");
    }
}