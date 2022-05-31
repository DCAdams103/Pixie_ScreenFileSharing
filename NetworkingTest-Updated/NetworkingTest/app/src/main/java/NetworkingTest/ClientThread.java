/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NetworkingTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.io.*;

public class ClientThread extends Thread {

    private Socket socket;
    private BufferedReader input;
    static PrintWriter output;
    private javax.swing.JTextArea chatTextArea;
    private String userName;

    public ClientThread(Socket s, javax.swing.JTextArea jTA, String userName, PrintWriter output) throws IOException {
        this.socket = s;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = output;
        this.chatTextArea = jTA;
        this.userName = userName;
    }

    @Override
    public void run() {
        try {
            while(true) {
                    
                    String response = input.readLine();
                    updateTextbox(response);
                    
                
            }
        } catch (IOException e) {
            System.out.println("Error in ClientThread1: ");
            e.printStackTrace();
        }
//        finally {
//            try {
//                input.close();
//            } catch (Exception e) {
//                System.out.println("Error in ClientThread2: ");
//                e.printStackTrace();
//            }
//        }
    }
    
    private void updateTextbox(String msg) {
        
        chatTextArea.setText(chatTextArea.getText() + "\n" + msg);
        
    }

}
