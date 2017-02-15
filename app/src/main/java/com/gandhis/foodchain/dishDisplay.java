package com.gandhis.foodchain;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class dishDisplay extends Fragment {

    private String dishName;
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
        return inflater.inflate(R.layout.fragment_dish_display, container, false);
    }

    public void findDish(View view)
    {

    }

}
