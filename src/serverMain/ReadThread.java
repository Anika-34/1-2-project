package serverMain;

import javafx.application.Platform;
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
                        main.setUserName(loginDTO.getUserName());
                        System.out.println(loginDTO.isStatus());
                        // the following is necessary to update JavaFX UI components from user created threads
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if (loginDTO.isStatus()) {
                                    try {
                                        // System.out.println("home");
                                        // for(Restaurant r :  server.Server.restaurants)
                                        // {
                                        //     System.out.println(r);
                                        // }
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
                    if(o instanceof List)
                    {
                        // System.out.println("List in read thread");
                        List<Restaurant> list = (List<Restaurant>) o;
                        main.setRestaurants(list);
                        // for(Restaurant r :  list)
                        // {
                        //     System.out.println(r);
                        // }
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



