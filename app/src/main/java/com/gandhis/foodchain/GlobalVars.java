package com.gandhis.foodchain;
import android.app.Application;
import java.util.ArrayList;

public class GlobalVars extends Application {

    public static ArrayList<String> dishes;

    private GlobalVars()
    {
        dishes = new ArrayList<String>();
    }

    private static GlobalVars instance;

    public static GlobalVars getInstance() {
        if (instance == null)
            instance = new GlobalVars();
        return instance;
    }
}