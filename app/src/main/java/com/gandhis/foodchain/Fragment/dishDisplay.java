package com.gandhis.foodchain.Fragment;

import com.gandhis.foodchain.Database.*;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gandhis.foodchain.Database.Dish;
import com.gandhis.foodchain.R;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class dishDisplay extends Fragment {

    private String dishName;
    private DishesSource dishSource;
    private List<Dish> DishesArray;
    public dishDisplay() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        dishName=getArguments().getString("dish name");
        Log.w("Fragment", "Disp Dish");
        View rootView = inflater.inflate(R.layout.fragment_dish_display, container,false);
        TextView dishNameText = (TextView) rootView.findViewById(R.id.dishSelected);
        dishNameText.setText(dishName);
        TextView dishNameHeader = (TextView) rootView.findViewById(R.id.header);
        String[] headers=getResources().getStringArray(R.array.Header_titles);
        dishNameHeader.setText(headers[new Random().nextInt(headers.length)]);
        return rootView;
    }

    public void findDish(View view)throws SQLException
    {
        dishSource.open();
        DishesArray=dishSource.getAllDishes();
        TextView tv= (TextView)getView().findViewById(R.id.dishSelected);
        tv.setText(DishesArray.get(new Random().nextInt(DishesArray.size())).getName());
        String[] headers=getResources().getStringArray(R.array.Header_titles);
        TextView dishNameHeader = (TextView) getView().findViewById(R.id.header);
        dishNameHeader.setText(headers[new Random().nextInt(headers.length)]);
    }

    @Override
    public void onDestroy() {
        Log.w("Fragment","Disp dish end");
        super.onDestroy();
    }
}
