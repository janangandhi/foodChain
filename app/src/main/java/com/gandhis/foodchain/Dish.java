package com.gandhis.foodchain;

/**
 * Created by janan on 11-02-2017.
 */
public class Dish {
    private String Name;
    private long id;
    private boolean favoriteId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(boolean favoriteId) {
        this.favoriteId = favoriteId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



}