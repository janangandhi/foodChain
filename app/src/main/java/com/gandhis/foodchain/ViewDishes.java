package com.gandhis.foodchain;


import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ViewDishes extends ListFragment {


    private DishesSource dishSource;
    private SQLiteDatabase db;
    private Cursor cursor;
    private CursorAdapter list;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FoodChainDatabaseHelper dbhelper = new FoodChainDatabaseHelper(getActivity());
        db=dbhelper.getWritableDatabase();
        try {
            cursor = db.query(dbhelper.TABLE_NAME,
                    new String[]{dbhelper.COLUMN_ID, dbhelper.COULUMN_NAME},
                    null,
                    null,
                    null,
                    null,
                    null);
            list = new SimpleCursorAdapter(getActivity(),
                    android.R.layout.simple_list_item_activated_1,
                    cursor,
                    new String[]{dbhelper.COULUMN_NAME },
                    new int[]{android.R.id.text1},
                    0);


        }
        catch(Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setAdapter();

    }

    public void setAdapter()
    {
        setListAdapter(list);
        Log.w("adapter", "set");
        ListView lv = getListView();
        if(lv != null)
            Log.w("ListView", "loaded");
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        Log.w("ListView", String.valueOf(lv.getChoiceMode()));

        lv.setMultiChoiceModeListener(new ModeCallback());

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return super.onCreateView(inflater, container, savedInstanceState);
    }



    @Override
    public void onStop() {
        super.onStop();
        cursor.close();
    }


    private class ModeCallback implements ListView.MultiChoiceModeListener
    {
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.home_page, menu);
        mode.setTitle("Select Items");
        return true;
    }

    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return true;
    }

    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return true;
    }

    public void onDestroyActionMode(ActionMode mode) {
    }

    public void onItemCheckedStateChanged(ActionMode mode,
                                          int position, long id, boolean checked) {
        final int checkedCount = getListView().getCheckedItemCount();
        Log.w("checked", "in");
        switch (checkedCount) {
            case 0:
                mode.setSubtitle(null);
                break;
            case 1:
                mode.setSubtitle("One item selected");
                break;
            default:
                mode.setSubtitle("" + checkedCount + " items selected");
                break;
        }
    }
    }
}