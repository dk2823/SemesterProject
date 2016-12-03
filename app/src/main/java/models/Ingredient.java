package models;

/**
 * Created by DK on 11/8/16.
 */
public class Ingredient {
    private long ingredientId;
    private String name;
    private int cookTime;
    private long restaurantId;
    private double price;
    private int imageId;
    private String quantity;


    public Ingredient(){

    }

    public Ingredient(long ingredientId, String name, int cookTime, long restaurantId,
                      double price, int imageId) {
        this.ingredientId = ingredientId;
        this.name = name;
        this.cookTime = cookTime;
        this.restaurantId = restaurantId;
        this.price = price;
        this.imageId = imageId;
    }

    public long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
