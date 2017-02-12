package com.gandhis.foodchain;

/**
 * Created by janan on 09-02-2017.
 */
import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
class FoodChainDatabaseHelper extends SQLiteOpenHelper {


    public static final String DB_NAME = "FoodChain"; // the name of our database
    public static final int DB_VERSION = 1; // the version of the database
    public static final String TABLE_NAME= "dishes";
    public static final String COLUMN_ID= "_id";
    public static final String COULUMN_NAME = "NAME"; // the version of the database
    public static final String COULUMN_FAVORITE = "FAVORITE"; // the version of the database
    public static final String DATABASE_CREATE = "Create table "+TABLE_NAME+"("
            +COLUMN_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COULUMN_NAME+" TEXT, "
            + COULUMN_FAVORITE+" BOOLEAN NOT NULL DEFAULT false);";

    FoodChainDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_NAME);
        onCreate(db);
    }
    /*
    private static void insertDish(SQLiteDatabase db, String name,
                                    int favorite) {
        ContentValues dishValues = new ContentValues();
        dishValues.put("NAME", name);
        dishValues.put("FAVORITE", favorite);
        db.insert("foodDishes", null, dishValues);
    }

    public static void dishDetails(String name, int favorite)
    {
        SQLiteDatabase db = FoodChainDatabaseHelper.getWritableDatabase();
        insertDish(db,name,favorite);
    }*/

}
