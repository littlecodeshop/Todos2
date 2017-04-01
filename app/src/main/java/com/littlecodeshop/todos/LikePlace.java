package com.littlecodeshop.todos;

/**
 * Created by ribier on 01/04/2017.
 */

public class LikePlace {

    private String name;
    private String id;

    public LikePlace(String id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }


}
