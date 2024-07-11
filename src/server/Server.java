package server;

import Database.*;

import util.NetworkUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server {

    private ServerSocket serverSocket;
    public static HashMap<String, String> userMap1;
    public static HashMap<String, String> userMap2;
    public HashMap<String, NetworkUtil> clientMap;
    public List<Restaurant> restaurants = new ArrayList<>();
    private static final String RESTAURANT_FILE_NAME = "C:\\Users\\anika\\Desktop\\FolderDesktop\\AllJavaFxPractices\\Practice2\\src\\resInfo.txt";
    private static final String CUSTOMER_FILE_NAME = "C:\\Users\\anika\\Desktop\\FolderDesktop\\AllJavaFxPractices\\Practice2\\src\\UserInfo.txt";
    File file;

    Server() {
        clientMap = new HashMap<>();
        try {
            file = new File();
            readUser1();
            readUser2();
            restaurants = file.getRestaurant();
            serverSocket = new ServerSocket(33333);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                serve(clientSocket);
            }
        } catch (Exception e) {
            System.out.println("Server starts:" + e);
        }
    }

    public void serve(Socket clientSocket) throws IOException, ClassNotFoundException {
        
        NetworkUtil networkUtil = new NetworkUtil(clientSocket);
        String client = (String)networkUtil.read();
        //clientMap.put(client, networkUtil);
        if (client.equals("R")) {
            new ReadThreadServer(restaurants,clientMap,userMap2, networkUtil);
            // networkUtil.write(restaurants);
        } else {
            
            // System.out.println("in server");
            // for(Restaurant r : restaurants)
            // {
            // System.out.println(r);
            // }
            new ReadThreadServer(restaurants, clientMap,userMap1, networkUtil);
            networkUtil.write(restaurants);
        }
        // new InfoReadThread(restaurants, networkUtil);
        // new ReadThreadServer(userMap1, networkUtil);
    }

    public static void readUser2() {
        userMap2 = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(RESTAURANT_FILE_NAME))) {
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] array = line.split(",", -1);

                userMap2.put(array[0], array[1]);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readUser1() {
        userMap1 = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMER_FILE_NAME))) {
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                String[] array = line.split(",", -1);

                userMap1.put(array[0], array[1]);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new Server();
        // readUser1();
        // readUser2();
    }
}
