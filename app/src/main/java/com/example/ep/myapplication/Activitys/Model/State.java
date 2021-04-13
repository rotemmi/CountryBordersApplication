package com.example.ep.myapplication.Activitys.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by EP on 18/07/2017.
 */

public class State implements Serializable {

    private String name;
    private List<String> borders = null;
    private String nativeName;
    private String flag;

    public State (String name , String nativeName,String flag)
    {
        this.name = name;
        this.nativeName = nativeName;
        this.flag=flag;
    }

    public State(String name, List<String> borders, String nativeName , String flag) {
        this.name = name;
        this.borders = borders;
        this.nativeName = nativeName;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public List<String> getBorders() {
        return borders;
    }

    public String getNativeName() {
        return nativeName;
    }

    public String getFlag(){return flag;}
}
