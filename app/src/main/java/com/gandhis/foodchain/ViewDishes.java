package com.gandhis.foodchain;


import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.sql.SQLException;

public class ViewDishes extends ListFragment {


    private DishesSource dishSource;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FoodChainDatabaseHelper dbhelper = new FoodChainDatabaseHelper(getActivity());
        db=dbhelper.getWritableDatabase();
        if(db.isOpen())
            Log.w("Status of db","Open" );
        try
        {
            cursor= db.query(dbhelper.TABLE_NAME,
                    new String[]{dbhelper.COLUMN_ID, dbhelper.COULUMN_NAME},
                    null,
                    null,
                    null,
                    null,
                    null);
            Log.w("String","Reached here");
            CursorAdapter list = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{dbhelper.COULUMN_NAME },
                    new int[]{android.R.id.text1},
                    0);
            setListAdapter(list);

        }
        catch(Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }




        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStop() {
        super.onStop();
        cursor.close();
    }
}