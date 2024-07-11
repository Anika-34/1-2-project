package Database;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Serializable{
    String Name;
    int Id;
    double Score;
    String Price;
    String zipCode;
    String[] categories = new String[3];
    
    List<Food> foodMenu = new ArrayList<>();

    public Restaurant(int Id, String Name, double Score, String Price, String zipCode, String[] categories) {
        this.Name = Name;
        this.Id = Id;
        this.Score = Score;
        this.Price = Price;
        this.zipCode = zipCode;
        this.categories = categories;
    }

    public Restaurant() {
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setScore(double Score) {
        this.Score = Score;
    }

    public void setPrice(String Price) {
        this.Price = Price;
    }

    public void setZip(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setCategory(String[] categories) {
        this.categories = categories;
    }

    public String getName() {
        return Name;
    }

    public String getPrice() {
        return Price;
    }

    public double getScore() {
        return Score;
    }

    public int getId() {
        return Id;
    }

    public String getZip() {
        return zipCode;
    }

    public String[] getCategories() {
        return categories;
    }

    public List<Food> getMenuList() {
        return foodMenu;
    }

    @Override
    public String toString() {

        String res = "Id: " + getId() + "\nName: " + getName() + "\nScore: " + getScore() + "\nPrice: " + getPrice()
                + "\nzipCode: " + getZip() + "\nCatagories: ";
        String cat = new String(getCategories()[0] + ",");
        for (int i = 1; i < 3; i++) {
            if (getCategories()[i] != null) {
                cat = cat + getCategories()[i];
                if (i != 2)
                    cat = cat + ",";
            }
        }
        res = res + cat;
        return res;
    }
}