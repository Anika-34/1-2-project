package Database;

import java.io.Serializable;

public class Food implements Serializable{
    int RestaurantId;
    String Category;
    String Name;
    double Price;

    public Food(int RestaurantId, String Category, String Name, double Price) {
        this.RestaurantId = RestaurantId;
        this.Category = Category;
        this.Name = Name;
        this.Price = Price;
    }

    public Food() {
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public void setRestaurantId(int RestaurantId) {
        this.RestaurantId = RestaurantId;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }

    public String getName() {
        return Name;
    }

    public double getPrice() {
        return Price;
    }

    public String getCategory() {
        return Category;
    }

    public int getRestaurantId() {
        return RestaurantId;
    }

    @Override
    public String toString() {
        String fd = "Id: " + getRestaurantId() + "\nCategory: " + getCategory() + "\nName: " + getName() + "\nPrice: "
                + getPrice();
        return fd;
    }
}
