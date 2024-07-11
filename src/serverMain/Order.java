package serverMain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    String userName;
    // String restaurantName;
    List <CartItem> orderList = new ArrayList<>();

    public void setUserName(String userName)
    {
        this.userName = userName;
        // this.restaurantName = restaurantName;
    }

    public void setOrderedFoods(List <CartItem> orderList)
    {
        this.orderList = orderList;
    }

    public String getUserName()
    {
        return userName;
    }

    // public String getRestaurantName()
    // {
    //     return restaurantName;
    // }

    public List<CartItem> getOrderedFoods()
    {
        return orderList;
    }
}
