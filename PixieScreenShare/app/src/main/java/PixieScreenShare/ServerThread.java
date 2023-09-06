package PixieScreenShare;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.net.*;
import java.io.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ServerThread extends Thread{
    private Socket socket;
    private ArrayList<ServerThread> threadList;
    private PrintWriter output;
    private ObjectOutputStream out;
    private ObjectInputStream oIn;

    public ServerThread(Socket socket, ArrayList<ServerThread> threads) {
        this.socket = socket;
        this.threadList = threads;
    }

    @Override
    public void run() {
        try {
            // Read input from Client
            //InputStreamReader in = new InputStreamReader(socket.getInputStream());
            InputStream inputStream = socket.getInputStream();
            oIn = new ObjectInputStream(inputStream);
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            //BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

            output = new PrintWriter(socket.getOutputStream());
            output.flush();
            
            while(true) {
                
                Object inObj = oIn.readObject();
                String inStr;
                
                if (inObj instanceof String) {
                    
                    inStr = (String)inObj;
                    if(inStr.equals("Exit")) break;
                    
                    printToAllClients(inStr);
                    System.out.println("Server received: " + inStr);
                    
                } else if (inObj instanceof byte[]) {
                    
                    sendFileToAllClients((byte[])inObj);
                    
                }
                
                //String outputString = input.readLine();
                
//                if(inStr != null) {
//                    
//                    if(inStr.equals("Exit")){
//                        break;
//                    }
//                    
//                    printToAllClients(inStr);
//                    
//                    System.out.println("Server received: " + inStr);
//                    
//                }

            }

        } catch (Exception e) {
            System.out.println("Error in ServerThread: ");
            e.printStackTrace();
        }

    }
    
    private void sendFileToAllClients(byte[] outputData) {
        try {
            for (ServerThread sT: threadList) {
                //sT.output.println(outputString);
                //sT.output.flush();
                sT.out.writeObject((byte[])outputData);
                sT.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error in sendFileToAllClients: ");
            e.printStackTrace();
        }
    }

    private void printToAllClients(String outputString) {
        
        try {
            for (ServerThread sT: threadList) {
                //sT.output.println(outputString);
                //sT.output.flush();
                sT.out.writeObject(outputString);
                sT.out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error in printToAllClients: ");
            e.printStackTrace();
        }
        
    }

}

