/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NetworkingTest;

import java.net.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ServerThread extends Thread{
    private Socket socket;
    private ArrayList<ServerThread> threadList;
    private PrintWriter output;

    public ServerThread(Socket socket, ArrayList<ServerThread> threads) {
        this.socket = socket;
        this.threadList = threads;
    }

    @Override
    public void run() {
        try {
            // Read input from Client
            InputStreamReader in = new InputStreamReader(socket.getInputStream());
            BufferedReader input = new BufferedReader(in);

            output = new PrintWriter(socket.getOutputStream());
            output.flush();
            
            while(true) {
                String outputString = input.readLine();
                
                if(outputString != null) {
                    
                    if(outputString.equals("Exit")){
                        break;
                    }
                    
                    printToAllClients(outputString);
                    
                    System.out.println("Server received: " + outputString);
                    
                }

            }

        } catch (Exception e) {
            System.out.println("Error in ServerThread: ");
            e.printStackTrace();
        }

    }

    private void printToAllClients(String outputString) {
        for (ServerThread sT: threadList) {
            sT.output.println(outputString);
            sT.output.flush();
        }
    }

}

