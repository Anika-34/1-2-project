package restaurant;

import javafx.application.Platform;
import serverMain.CartItem;
import serverMain.Order;
import util.LoginDTO;
// import util.*;

import java.io.IOException;
import java.util.List;

import Database.Restaurant;

public class ReadThread implements Runnable {
    private final Thread thr;
    private final Main main;
    // NetworkUtil networkUtil;

    public ReadThread(Main main2) {
        this.main = main2;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = main.getNetworkUtil().read();
                if (o != null) {
                    if (o instanceof LoginDTO) {
                        LoginDTO loginDTO = (LoginDTO) o;
                        System.out.println(loginDTO.getUserName());
                        System.out.println(loginDTO.isStatus());

                        main.setUserName(loginDTO.getUserName());
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (loginDTO.isStatus()) {
                                    try {
                                        main.getNetworkUtil().write(loginDTO.getUserName());
                                        main.showHomePage(loginDTO.getUserName());
                                        //networkUtil.write("hello");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    main.showAlert();
                                }

                            }
                        });
                    }

                    if(o instanceof Restaurant)
                    {
                        main.setRestaurant((Restaurant) o);
                    }
                    if(o instanceof Order)
                    {
                        Order order = (Order) o;
                        System.out.println("IN RESTAURANT READ THREAD SERVER ORDER");
                        List <CartItem> cart = order.getOrderedFoods();
                        // RestMap.get(order.getRestaurantName()).write(order);

                        for(CartItem C : cart)
                        {
                            System.out.println(C.getFood().getName() + " " + C.getQuantity());
                        }
                        main.setOrders(order);
                        System.out.println("okay");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                main.getNetworkUtil().closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



