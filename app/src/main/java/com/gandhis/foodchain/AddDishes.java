package com.gandhis.foodchain;



import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class AddDishes extends AppCompatActivity {

    private DishesSource dishSource;
    private boolean favStatus = false; // favorite status of dish
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dishes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        dishSource = new DishesSource(getApplicationContext());

    }

    public void dishInsert(View view)throws SQLException
    {
        TextView dishName = (TextView) findViewById(R.id.dishname);
        String name = String.valueOf(dishName.getText());
        dishSource.open();
        dishSource.createDish(name, favStatus);
        dishSource.close();
        Toast toast = Toast.makeText(this,"Dish added to favorites",Toast.LENGTH_SHORT);
        toast.show();
    }


    public void favStatusSet(View view)
    {
        CharSequence text;
        favStatus = ((Switch) view).isChecked();
        if(favStatus)
          text= "Dish added to favorites";
        else
           text="Dish removed from favorites";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }


}
