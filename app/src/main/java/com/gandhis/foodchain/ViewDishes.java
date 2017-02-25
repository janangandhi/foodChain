package com.gandhis.foodchain;


import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class ViewDishes extends ListFragment
{


    private DishesSource dishSource;
    private SQLiteDatabase db;
    private static Cursor cursor;
    private static CursorAdapter list;
    private boolean emptyListflag=false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


         //return super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_viewdishes, container, false);
    }



    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setAdapter();
        TextView tv = (TextView) getActivity().findViewById(R.id.empty);
        if(tv ==null)
            Log.w("String","not null");
        //getListView().setEmptyView(tv);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        if(emptyListflag)
            Toast.makeText(getActivity(), "Please add some dishes first!!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }

    public void setAdapter()
    {
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
            if(cursor.getCount()==0)
                emptyListflag=true;


        }
        catch(Exception e) {
            e.printStackTrace();
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        setListAdapter(list);
        ListView lv = getListView();
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lv.setMultiChoiceModeListener(new ModeCallback());
        TextView tv = (TextView)getActivity().findViewById(R.id.empty);
        lv.setEmptyView(tv);

    }

    @Override
    public void onStop() {
        super.onStop();
    }


    private class ModeCallback implements ListView.MultiChoiceModeListener
    {
        private Dish bufferDish;
        private DishesSource dishSource;
        FoodChainDatabaseHelper dbhelper = new FoodChainDatabaseHelper(getActivity());



        public boolean onCreateActionMode(ActionMode mode, Menu menu)
        {
           dishSource=new DishesSource(getActivity());
            try
            {
                dishSource.open();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
                Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
                toast.show();
            }
            mode.getMenuInflater().inflate(R.menu.view_dishes, menu);
            mode.setTitle("Delete dishes");
            return true;
        }


        public boolean onPrepareActionMode(ActionMode mode, Menu menu)
        {

            return true;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item)
        {

            SparseBooleanArray selectedDishes;
            Cursor temp;
            Dish dish;
            switch (item.getItemId())
            {
                case R.id.delete_items:
                Log.w("at","delete button ");
                selectedDishes=getListView().getCheckedItemPositions();
                Log.w("Selected",String.valueOf(selectedDishes));
                for(int i=0;i<selectedDishes.size();i++)
                {
                    Log.w(String.valueOf(selectedDishes),String.valueOf(selectedDishes.valueAt(i)));
                    if (selectedDishes.valueAt(i))
                    {
                        temp = (Cursor) getListView().getItemAtPosition(selectedDishes.keyAt(i));
                        dish = dishSource.cursorToDish(temp);
                        Log.w("Deleting:", dish.getName());
                        dishSource.delteDish(dish);
                    }
                }
                Toast.makeText(getActivity(), "Dish deleted!", Toast.LENGTH_SHORT).show();
                ViewDishes.cursor=db.query(dbhelper.TABLE_NAME,
                        new String[]{dbhelper.COLUMN_ID, dbhelper.COULUMN_NAME},
                        null,
                        null,
                        null,
                        null,
                        null);
                ViewDishes.list.swapCursor(ViewDishes.cursor);
                ViewDishes.list.notifyDataSetChanged();
                break;

                case R.id.select_all_items:
                    Log.w(String.valueOf(getListView().getCheckedItemCount()), String.valueOf(getListView().getChildCount()));
                    if(getListView().getCheckedItemCount()== getListView().getChildCount())
                    {
                        Log.w("True", "condition");
                        for ( int i=0; i < getListView().getChildCount(); i++)
                        {
                            getListView().setItemChecked(i, false);
                        }
                    }
                    else
                        for ( int i=0; i < getListView().getChildCount(); i++)
                        {
                            getListView().setItemChecked(i, true);
                        }
                    break;

            }

            return true;
        }

        public void onDestroyActionMode(ActionMode mode)
        {
            dishSource.close();
        }

        public void onItemCheckedStateChanged(ActionMode mode,int position, long id, boolean checked)
        {
            final int checkedCount = getListView().getCheckedItemCount();
            switch (checkedCount)
            {
                case 0:
                    mode.setSubtitle(null);
                    break;
                case 1:
                    mode.setSubtitle("1 dish selected");
                    break;
                default:
                    mode.setSubtitle("" + checkedCount + " dishes selected");
                    break;
            }
        }


    }
}