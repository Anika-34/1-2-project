package Database;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class File implements Serializable {
    public List<Restaurant> restaurant = new ArrayList<>();
    public List<String> allCategories = new ArrayList<>();
    public List<String> allcat = new ArrayList<>();

    private static final String INPUT_FILE_NAME = "C:\\Users\\anika\\Desktop\\FolderDesktop\\AllJavaFxPractices\\Practice2\\src\\restaurant.txt";
    private static final String OUTPUT_FILE_NAME = "C:\\Users\\anika\\Desktop\\FolderDesktop\\AllJavaFxPractices\\Practice2\\src\\restaurant.txt";
    private static final String INPUT_FOOD_FILE_NAME = "C:\\Users\\anika\\Desktop\\FolderDesktop\\AllJavaFxPractices\\Practice2\\src\\menu.txt";
    private static final String OUTPUT_FOOD_FILE_NAME = "C:\\Users\\anika\\Desktop\\FolderDesktop\\AllJavaFxPractices\\Practice2\\src\\menu.txt";

    public List<Restaurant> getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(List<Restaurant> r) {
        this.restaurant = r;
    }

    // public void returnAllcatwithRes()
    // {
    // for(Restaurant R : restaurant)
    // {
    // if(R.getCategories()[0] != null) addNewCategory(R.getCategories()[0]);
    // if(R.getCategories()[1] != null) addNewCategory(R.getCategories()[1]);
    // if(R.getCategories()[2] != null) addNewCategory(R.getCategories()[2]);
    // }

    // }

    public List<String> getAllCategories() {
        return allCategories;
    }

    public boolean addRestaurant(Restaurant r) {
        int flag = 0;
        for (Restaurant R : restaurant) {
            if ((R.getName().equalsIgnoreCase(r.getName()))) {
                flag = 1;
                break;
            }
        }
        if (flag == 1)
            return false;
        else {
            restaurant.add(r);
            return true;
        }
    }

    public boolean addNewRestaurant(Restaurant r) {
        int flag = 0;
        for (Restaurant R : restaurant) {
            if ((R.getName().equalsIgnoreCase(r.getName()))) {
                flag = 1;
                break;
            }
        }
        if (flag == 1)
            return false;
        else {
            restaurant.add(r);
            if (r.getCategories()[0] != null && r.getCategories()[0].length() != 0)
                addNewCategory(r.getCategories()[0]);
            if (r.getCategories()[1] != null && r.getCategories()[1].length() != 0)
                addNewCategory(r.getCategories()[1]);
            if (r.getCategories()[2] != null && r.getCategories()[2].length() != 0)
                addNewCategory(r.getCategories()[2]);
            return true;
        }
    }

    public void addNewCategory(String cat) {
        // System.out.println("ADD NEW CATEGORY");
        int flag = 0;
        for (String c : allCategories) {
            if (c.equalsIgnoreCase(cat)) {
                flag = 1;
                break;
            }
        }
        if (flag != 1)
            allCategories.add(cat);
    }

    public void addCategory(String cat) {
        int flag = 0;
        for (String c : allCategories) {
            if (c.equalsIgnoreCase(cat)) {
                flag = 1;
                break;
            }
        }
        if (flag != 1)
            allCategories.add(cat);
    }

    public boolean addFood(Food f) {
        Restaurant rest = null;
        for (Restaurant R : restaurant) {
            if (R.getId() == f.getRestaurantId()) {
                rest = R;
                break;
            }
        }
        List<Food> menu = rest.getMenuList();
        int flag = 0;
        for (Food F : menu) {
            if ((F.getName().equalsIgnoreCase(f.getName())) && (F.getRestaurantId() == f.getRestaurantId())
                    && (F.getCategory().equalsIgnoreCase(f.getCategory()))) {
                flag = 1;
                break;
            }
        }

        if (flag == 1)
            return false;
        else {
            menu.add(f);
            return true;
        }
    }

    public void readFromInputFile() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FILE_NAME));
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            addRestaurantFromLine(line);
        }
        br.close();
    }

    public void readFromInputFile2() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(INPUT_FOOD_FILE_NAME));
        while (true) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            addFoodFromLine(line);
        }
        br.close();
    }

    public void addRestaurantFromLine(String line) {
        Restaurant r = new Restaurant();
        String[] array = line.split(",(?!\\s)", -1);
        int i = 0;
        r.setId(Integer.parseInt(array[i++]));
        r.setName(array[i++]);
        r.setScore(Double.parseDouble(array[i++]));
        r.setPrice(array[i++]);
        r.setZip(array[i++]);

        int j = 0;
        while (j < 3) {
            if (array[i].length() != 0)
                addCategory(array[i]);
            r.categories[j++] = array[i++];
        }
        addRestaurant(r);

    }

    public void addFoodFromLine(String line) {
        Food f = new Food();
        String[] array = line.split(",(?!\\s)", -1);
        int i = 0;
        int id = Integer.parseInt(array[i++]);
        f.setRestaurantId(id);
        f.setCategory(array[i++]);
        f.setName(array[i++]);
        f.setPrice(Double.parseDouble(array[i++]));
        addFood(f);
    }

    public void writeToInputFile() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        for (Restaurant r : restaurant) {
            bw.write(getRestaurantLine(r));
            bw.write(System.lineSeparator());
        }
        bw.close();
    }

    public void writeToInputFile2() throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FOOD_FILE_NAME));
        for (Restaurant r : restaurant) {
            List<Food> menu = r.getMenuList();
            for (Food f : menu) {
                bw.write(getFoodLine(f));
                bw.write(System.lineSeparator());
            }
        }
        bw.close();
    }

    public String getRestaurantLine(Restaurant r) {
        String text = new String(
                r.getId() + "," + r.getName() + "," + r.getScore() + "," + r.getPrice() + "," + r.getZip() +
                        ",");
        String cat = new String(r.getCategories()[0] + ",");
        for (int i = 1; i < 3; i++) {
            if (r.getCategories()[i] != null) {
                cat = cat + r.getCategories()[i];

            }
            if (i != 2)
                cat = cat + ",";
        }
        text = text + cat;
        return text;
    }

    public String getFoodLine(Food f) {
        String text = new String(f.getRestaurantId() + "," + f.getCategory() + "," + f.getName() + "," + f.getPrice());
        return text;
    }

    // constructor
    public File() {
        try {
            readFromInputFile();
            readFromInputFile2();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // other constructor
    public File(List<Restaurant> r) {
        this.restaurant = r;
        System.out.println("constructor");
        // for (Restaurant R : restaurant) {
        // System.out.println(R);
        // }
        for (Restaurant R : restaurant) {
            if (R.getCategories()[0] != null)
                addNewCategory(R.getCategories()[0]);
            if (R.getCategories()[1] != null)
                addNewCategory(R.getCategories()[1]);
            if (R.getCategories()[2] != null)
                addNewCategory(R.getCategories()[2]);
        }
        System.out.println("constructor end");
    }

    public int searchRestaurantName(String name) {
        int index = -1;
        for (int i = 0; i < restaurant.size(); i++) {
            if (restaurant.get(i).getName().equalsIgnoreCase(name)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public List<Restaurant> searchRestaurantbyName(String name) {
        List<Restaurant> res = new ArrayList<>();
        for (int i = 0; i < restaurant.size(); i++) {
            if ((restaurant.get(i).getName().toLowerCase()).contains(name.toLowerCase())) {
                res.add(restaurant.get(i));
            }
        }
        return res;
    }

    public List<Restaurant> searchRestaurantbyScore(double score1, double score2) {
        List<Restaurant> res = new ArrayList<>();
        for (int i = 0; i < restaurant.size(); i++) {
            Restaurant r = restaurant.get(i);
            if (r.getScore() >= score1 && r.getScore() <= score2) {
                res.add(r);
            }
        }
        return res;
    }

    public List<Restaurant> searchRestaurantbyCategory(String category) {
        List<Restaurant> res = new ArrayList<>();
        for (int i = 0; i < restaurant.size(); i++) {
            Restaurant r = restaurant.get(i);
            if ((r.getCategories()[0].toLowerCase()).contains(category.toLowerCase())
                    || (r.getCategories()[1].toLowerCase()).contains(category.toLowerCase())
                    || (r.getCategories()[2].toLowerCase()).contains(category.toLowerCase())) {
                res.add(r);
            }
        }
        return res;
    }

    public List<Restaurant> searchRestaurantbyPrice(String Price) {
        List<Restaurant> res = new ArrayList<>();
        for (int i = 0; i < restaurant.size(); i++) {
            if (restaurant.get(i).getPrice().equalsIgnoreCase(Price)) {
                res.add(restaurant.get(i));
            }
        }
        return res;
    }

    public List<Restaurant> searchRestaurantbyZip(String zip) {
        // Restaurant r = null;
        List<Restaurant> r = new ArrayList<>();
        for (int i = 0; i < restaurant.size(); i++) {
            if (restaurant.get(i).getZip().equalsIgnoreCase(zip)) {
                r.add(restaurant.get(i));
                break;
            }
        }
        return r;
    }

    public List<Food> searchFoodbyName(String name) {
        List<Food> ret_foods = new ArrayList<>();
        for (Restaurant r : restaurant) {
            List<Food> fd = r.getMenuList();
            for (Food f : fd) {
                if (f.getName().toLowerCase().contains(name.toLowerCase())) {
                    ret_foods.add(f);
                }
            }
        }
        return ret_foods;
    }

    public List<Food> searchFoodWithRestaurantName(String name, String r_name) {
        List<Food> ret_fd = new ArrayList<>();
        Restaurant R = null;
        for (Restaurant r : restaurant) {
            if ((r.getName().toLowerCase()).contains(r_name.toLowerCase())) {
                R = r;
                break;
            }
        }
        List<Food> fd = R.getMenuList();
        for (int i = 0; i < fd.size(); i++) {
            if (fd.get(i).getRestaurantId() == R.getId()
                    && (fd.get(i).getName().toLowerCase()).contains(name.toLowerCase())) {
                ret_fd.add(fd.get(i));
            }
        }
        return ret_fd;
    }

    public List<Food> searchFoodWithCategory(String category) {
        List<Food> fd = new ArrayList<>();
        for (Restaurant R : restaurant) {
            List<Food> fds = R.getMenuList();
            for (Food f : fds) {
                if ((f.getCategory().toLowerCase()).contains(category.toLowerCase())) {
                    fd.add(f);
                }
            }
        }
        return fd;
    }

    public List<Food> searchFoodWithCategoryinRestaurant(String category, String r_name) {
        List<Food> ret_fd = new ArrayList<>();
        for (Restaurant r : restaurant) {
            if (r.getName().equalsIgnoreCase(r_name.toLowerCase())) {
                List<Food> fd = r.getMenuList();
                for (Food f : fd) {
                    if (f.getRestaurantId() == r.getId()
                            && (f.getCategory().toLowerCase()).contains(category.toLowerCase())) {
                        ret_fd.add(f);
                    }
                }
                break;
            }
        }

        return ret_fd;
    }

    public List<Food> searchFoodbyPrice(double price1, double price2) {

        List<Food> ret_fd = new ArrayList<>();
        for (Restaurant R : restaurant) {
            List<Food> fd = R.getMenuList();
            for (Food f : fd) {
                if (f.getPrice() >= price1 && f.getPrice() <= price2) {
                    ret_fd.add(f);
                }
            }
        }
        return ret_fd;
    }

    public List<Food> searchFoodbyPriceinRestaurant(double price1, double price2, String r_name) {
        List<Food> ret_fd = new ArrayList<>();
        Restaurant r = null;
        for (Restaurant R : restaurant) {
            if (R.getName().equalsIgnoreCase(r_name)) {
                r = R;
                break;
            }
        }
        List<Food> fd = r.getMenuList();
        for (Food f : fd) {
            if (f.getPrice() >= price1 && f.getPrice() <= price2) {
                ret_fd.add(f);
            }
        }
        return ret_fd;
    }

    public List<Food> GetCostliestFood(String r_name) {
        int index = searchRestaurantName(r_name);
        Restaurant r = restaurant.get(index);
        double max = -1;
        List<Food> foods = r.getMenuList();

        for (Food f : foods) {
            if (f.getPrice() > max) {
                max = f.getPrice();
            }
        }
        List<Food> fd = new ArrayList<>();
        // System.out.println("Costliest Food Items in " + r_name + " are: ");

        for (Food f : foods) {
            if (f.getPrice() == max) {
                fd.add(f);
            }
        }

        return fd;
    }

    public int[] showAllRestaurants() {
        int[] cnt = new int[restaurant.size()];
        for (Restaurant r : restaurant) {
            cnt[r.getId() - 1] = r.getMenuList().size();
        }
        return cnt;
    }

    public List<String> showCategoryWise() {

        // String[] ret = new String[allCategories.size()];
        List<String> ret = new ArrayList<>();
        // int index = 0;
        for (String s : allCategories) {
            String str = new String();
            boolean flag = false;
            if (s != null && !s.equals("")) {
                str = s + ": ";
                for (int i = 0; i < restaurant.size(); i++) {
                    Restaurant r = restaurant.get(i);
                    if ((r.getCategories()[0] != null && r.getCategories()[0].equalsIgnoreCase(s))
                            || (r.getCategories()[1] != null
                                    && r.getCategories()[1].equalsIgnoreCase(s))
                            || (r.getCategories()[2] != null
                                    && r.getCategories()[2].equalsIgnoreCase(s))) {

                        if (flag == true) {
                            str += ", ";
                        }
                        flag = true;
                        str += r.getName();
                    }
                }
            }
            // ret[index++] = str;
            ret.add(str);
        }
        return ret;
    }

    public Restaurant ResWithExactName(String Name) {
        Restaurant ret = new Restaurant();
        for (Restaurant R : restaurant) {
            if (R.getName().equalsIgnoreCase(Name)) {
                ret = R;
                break;
            }
        }
        return ret;
    }

    public Restaurant ResWithID(int id) {
        Restaurant ret = new Restaurant();
        for (Restaurant R : restaurant) {
            if (R.getId() == id) {
                ret = R;
                break;
            }
        }
        return ret;
    }

}