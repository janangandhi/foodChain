package com.gandhis.foodchain;


import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        Log.w("In dispDish", "reached here "+dishName);
        View rootView = inflater.inflate(R.layout.fragment_dish_display, container,false);
        TextView dishNameText = (TextView) rootView.findViewById(R.id.dishSelected);
        Log.w("Value of text before",String.valueOf(dishNameText.getText()));
        dishNameText.setText(dishName);
        Log.w("Value of text after", String.valueOf(dishNameText.getText()));
        return rootView;
    }

    public void findDish(View view)throws SQLException
    {
        dishSource.open();
        DishesArray=dishSource.getAllDishes();
        TextView tv= (TextView)getView().findViewById(R.id.dishSelected);
        tv.setText(DishesArray.get(new Random().nextInt(DishesArray.size())).getName());
    }

}
