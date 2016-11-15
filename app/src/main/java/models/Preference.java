package models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DK on 11/8/16.
 */
public class Preference {

    public static final String SM = "small";
    public static final String MD = "medium";
    public static final String LG = "large";

    private long preferenceId;
    private String name;
    private ArrayList<Ingredient> list;
    private long restaurantId;
    private long userId;
    private HashMap<Integer, String> quantity;

    public Preference(){

    }

    public Preference(long preferenceId, String name, ArrayList<Ingredient> list
            , long restaurantId, long userId, HashMap<Integer, String> quantity) {
        this.preferenceId = preferenceId;
        this.name = name;
        this.list = list;
        this.restaurantId = restaurantId;
        this.userId = userId;
        this.quantity = quantity;
    }

    public long getPreferenceId() {
        return preferenceId;
    }

    public void setPreferenceId(long preferenceId) {
        this.preferenceId = preferenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getList() {
        return list;
    }

    public void setList(ArrayList<Ingredient> list) {
        this.list = list;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public HashMap<Integer, String> getQuantity() {
        return quantity;
    }

    public void setQuantity(HashMap<Integer, String> quantity) {
        this.quantity = quantity;
    }
}
