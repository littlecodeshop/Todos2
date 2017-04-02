package com.littlecodeshop.todos;

/**
 * Created by ribier on 01/04/2017.
 */

public class LikePlace {

    private String name;
    private String id;
    private float rating;

    public LikePlace(String id, String name, float rating) {
        this.name = name;
        this.id = id;
        this.rating = rating;

    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getRating() {
        return rating;
    }


}
