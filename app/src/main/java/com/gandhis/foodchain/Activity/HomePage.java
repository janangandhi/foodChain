package com.gandhis.foodchain.Activity;

import com.gandhis.foodchain.Database.*;
import com.gandhis.foodchain.Fragment.HomePageFrag;
import com.gandhis.foodchain.R;
import com.gandhis.foodchain.Fragment.ViewDishes;
import com.gandhis.foodchain.Fragment.dishDisplay;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;


public class HomePage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private DishesSource dishSource;
    private List<Dish> DishesArray;
    private Dish BufferDish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("Activity","main");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        FragmentTransaction ft1 = getFragmentManager().beginTransaction();
        ft1.add(R.id.content_frame,new HomePageFrag()).commit();
        dishSource=new DishesSource(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        Log.w("ïn back pressed", "here");
        Fragment ft2= getFragmentManager().findFragmentById(R.id.content_frame);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(ft2!=null)
        {
            Log.w("ïn back pressed","in else condition");
            getFragmentManager().beginTransaction().remove(ft2).commit();

            super.onBackPressed();

        }
        else
            super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.add_menu) {
            Intent intent = new Intent(this,AddDishes.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment currFrag;

        switch (id)
        {
            case R.id.nav_dishes:
                currFrag = new ViewDishes();
                break;

            default:
                currFrag = new HomePageFrag();

        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, currFrag);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDestroy()
    {
        dishSource.close();
        Log.w("Activity", "main end");
        super.onDestroy();
    }

    public void findDish(View view)throws SQLException
    {
        Log.w("hi", "reached here");
        dishSource.open();

        DishesArray=dishSource.getAllDishes();
        if(DishesArray.isEmpty())
            Toast.makeText(this, "Add some Dishes first!!", Toast.LENGTH_LONG).show();
        else
        {
            BufferDish = DishesArray.get(new Random().nextInt(DishesArray.size()));
            Bundle dishBundle = new Bundle();
            dishBundle.putString("dish name", BufferDish.getName());
            Fragment dispCall = new dishDisplay();
            dispCall.setArguments(dishBundle);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, dispCall);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.commit();
        }
    }
}
