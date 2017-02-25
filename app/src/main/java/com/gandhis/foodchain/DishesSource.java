package com.gandhis.foodchain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

    public void createDish(String Name)
    {
        ContentValues values = new ContentValues();
        values.put(FoodChainDatabaseHelper.COULUMN_NAME, Name);
        database.insert(FoodChainDatabaseHelper.TABLE_NAME, null, values);

    }

    public boolean duplicateAddCheck(String name)
    {
        List<Dish> dishnames=getAllDishes();
        for(int i=0;i<dishnames.size();i++)
        {
            if(dishnames.get(i).getName().equalsIgnoreCase(name))
                return false;
        }
        return true;
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

    public void delteDish(Dish tobedead) {
        long id = tobedead.getId();
        database.delete(FoodChainDatabaseHelper.TABLE_NAME, FoodChainDatabaseHelper.COLUMN_ID
                + " = " + id, null);
        Log.w("Deleted:", tobedead.getName());
    }



    public Dish cursorToDish(Cursor cursor) {
        Dish dish = new Dish();
        dish.setId(cursor.getLong(0));
        dish.setName(cursor.getString(cursor.getColumnIndex(FoodChainDatabaseHelper.COULUMN_NAME)));
        return dish;
    }
}
