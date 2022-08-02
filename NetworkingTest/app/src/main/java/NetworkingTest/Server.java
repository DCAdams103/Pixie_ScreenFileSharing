/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NetworkingTest;

import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) throws IOException {

        ArrayList<ServerThread> threadList = new ArrayList<>();

        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            while(true){
                Socket s = serverSocket.accept();
                ServerThread serverThread = new ServerThread(s, threadList);

                threadList.add(serverThread);
                serverThread.start();

            }
        }

//        ServerSocket ss = new ServerSocket(5000);
//        System.out.println("Start");
//
//
//        System.out.println("Client connected");
//
//
//
//        String str = bf.readLine();
//        System.out.println("Client: " + str);

    }
}
