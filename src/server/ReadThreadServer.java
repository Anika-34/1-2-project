package server;

import util.LoginDTO;
import util.NetworkUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Database.Restaurant;
import serverMain.CartItem;
import serverMain.Order;

public class ReadThreadServer implements Runnable {
    private final Thread thr;
    private final NetworkUtil networkUtil;
    public HashMap<String, String> userMap;
    public HashMap<String, NetworkUtil> RestMap;
    public List<Restaurant> allRestaurant = new ArrayList<>();

    public ReadThreadServer(List<Restaurant> allRestaurant, HashMap<String, NetworkUtil> RestMap,
            HashMap<String, String> map, NetworkUtil networkUtil) {
        this.allRestaurant = allRestaurant;
        this.RestMap = RestMap;
        this.userMap = map;
        this.networkUtil = networkUtil;
        this.thr = new Thread(this);
        thr.start();
    }

    public void run() {
        try {
            while (true) {
                Object o = networkUtil.read();
                if (o != null) {
                    if (o instanceof LoginDTO) {
                        // System.out.println("Hello");
                        LoginDTO loginDTO = (LoginDTO) o;
                        String password = userMap.get(loginDTO.getUserName());
                        loginDTO.setStatus(loginDTO.getPassword().equals(password));
                        if (loginDTO.isStatus()) {
                            RestMap.put(loginDTO.getUserName(), networkUtil);
                        }
                        networkUtil.write(loginDTO);
                    }
                    if (o instanceof Order) {
                        System.out.println("rest readthread server");
                        Order order = (Order) o;
                        List<CartItem> cart = order.getOrderedFoods();
                        // RestMap.get(order.getRestaurantName()).write(order);
                        int id = cart.get(0).getFood().getRestaurantId();
                        String name = null;
                        System.out.println("All res size "+allRestaurant.size());
                        for (Restaurant R : allRestaurant) {
                            System.out.println("order" + R);
                            if (R.getId() == id) {
                                name = R.getName();
                                break;
                            }
                        }
                        System.out.println("name in order " + name);
                        for (CartItem C : cart) {
                            System.out.println(C.getFood().getName() + " " + C.getQuantity());
                        }

                        for (String entry : RestMap.keySet()) {

                            System.out.println(entry);
                            System.out.println(RestMap.get(entry));
                        }
                        // RestMap.get(name).write(order);

                        for (String key : RestMap.keySet()) {
                            if (key.equals(name)) {
                                System.out.println("in res read thread"+key);
                                NetworkUtil connected = RestMap.get(key);
                                connected.write(order);
                            }
                        }

                        System.out.println("SENDING TO RESTAURANT THREAD");
                    }
                    if (o instanceof String) {
                        String received = (String) o;
                        System.out.println("res name in read thread server " + received);
                        Restaurant send = new Restaurant();
                        System.out.println("All res size for nother"+allRestaurant.size());
                        for (Restaurant r : allRestaurant) {

                            System.out.println(r);
                            if (r.getName().equals(received)) {
                                send = r;
                                break;
                            }
                        }
                        networkUtil.write(send);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                networkUtil.closeConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
