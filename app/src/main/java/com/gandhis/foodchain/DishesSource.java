package com.gandhis.foodchain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by janan on 11-02-2017.
 */
public class DishesSource {

    private SQLiteDatabase database;
    private FoodChainDatabaseHelper db_helper;

    public DishesSource(Context context)
    {
        db_helper= new FoodChainDatabaseHelper(context);
    }

    public void open()throws SQLException
    {
        database=db_helper.getWritableDatabase();
    }

    public void close()
    {
        db_helper.close();
    }

    public void createDish(String Name,boolean favoriteStatus)
    {
        ContentValues values = new ContentValues();
        values.put(FoodChainDatabaseHelper.COULUMN_NAME, Name);
        values.put(FoodChainDatabaseHelper.COULUMN_FAVORITE, favoriteStatus);
        database.insert(FoodChainDatabaseHelper.TABLE_NAME, null, values);

    }

    public List<Dish> getAllDishes() {
        List<Dish> dish = new ArrayList<Dish>();

        Cursor cursor = database.query(FoodChainDatabaseHelper.TABLE_NAME,
                null, null, null, null, null, FoodChainDatabaseHelper.COLUMN_ID+" desc");

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Dish dish1 = cursorToDish(cursor);
            dish.add(dish1);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return dish;
    }

    private Dish cursorToDish(Cursor cursor) {
        Dish dish = new Dish();
        dish.setId(cursor.getLong(0));
        dish.setName(cursor.getString(cursor.getColumnIndex(FoodChainDatabaseHelper.COULUMN_NAME)));
        boolean completed = cursor.getInt(cursor.getColumnIndex(FoodChainDatabaseHelper.COULUMN_FAVORITE))>0;
        dish.setFavoriteId(completed);
        return dish;
    }
}
