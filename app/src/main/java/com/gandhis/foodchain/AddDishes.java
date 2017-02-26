package com.gandhis.foodchain;



import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

public class AddDishes extends AppCompatActivity {

    private DishesSource dishSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("Activity","addDish");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dishes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Dishes");
        dishSource = new DishesSource(getApplicationContext());
        try
        {
            dishSource.open();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            Toast.makeText(this,"Database unavailable",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       Log.w("Selected",String.valueOf(item.getItemId()));
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void dishInsert(View view)
    {
        TextView dishName = (TextView) findViewById(R.id.dishname);
        String name = String.valueOf(dishName.getText());
        Log.w("length", String.valueOf(name.trim().length()));

        if(name.trim().length()== 0)
        {
            Toast.makeText(this, "Please enter a dish name", Toast.LENGTH_SHORT).show();
            dishName.setText("");
        }
            else if(dishSource.duplicateAddCheck(name))
            {
                dishSource.createDish(name.trim());
               Toast.makeText(this, "Dish added", Toast.LENGTH_SHORT).show();
                dishName.setText("");
            }
                else
                    Toast.makeText(this,"Dish already exists",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy()
    {
        Log.w("Activity","addDish ended");
        super.onDestroy();
    }



}
