/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package NetworkingTest;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.io.*;
import javax.swing.ImageIcon;

public class ClientThread extends Thread {

    private Socket socket;
    private BufferedReader input;
    static PrintWriter output;
    private javax.swing.JTextPane chatTextPane;
    private String userName;
    private String message;
    
    private ObjectInputStream oIn;
    private ObjectOutputStream oOut;

    public ClientThread(Socket s, javax.swing.JTextPane jTP, String userName, PrintWriter output, ObjectOutputStream oOut) throws IOException {
        this.socket = s;
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.output = output;
        this.oOut = oOut;
        this.chatTextPane = jTP;
        this.userName = userName;
    }

    @Override
    public void run() {
        
            Thread msgRec = new Thread(new Runnable() {
                
            
                String response;
                @Override
                public void run() {
                    
                    try {
                        
                        oIn = new ObjectInputStream(socket.getInputStream());
                        //oOut = new ObjectOutputStream(socket.getOutputStream());
                        
                        while(true) {
                            try {
                                Object inObj = oIn.readObject();
                                String inStr;
                                
                                if(inObj instanceof String)  {
                                    System.out.println("HMM: " + inObj.getClass().getName());
                                    inStr = (String)inObj;
                                } else if (inObj instanceof byte[]) {
                                    updatePaneWithImage((byte[])inObj);
                                } else {
                                    inStr = "Error";
                                }
                                 
                                 //response = input.readLine();
                                 if(inStr) {
                                     updateTextbox(inStr);
                                 }
                                 
                                 
                            } catch(Exception e) {
                                System.out.println("Error in ClientThread1 while loop: ");
                                e.printStackTrace();
                            }

                        }       
                    } catch (IOException e) {
                        System.out.println("Error in ClientThread1: ");
                        e.printStackTrace();
                    }
                    
                }
            
            });
            
            msgRec.start();
        
//        finally {
//            try {
//                input.close();
//            } catch (Exception e) {
//                System.out.println("Error in ClientThread2: ");
//                e.printStackTrace();
//            }
//        }
    }
    
    public void sendMsg() {
        //output.println(this.userName + ": " + this.message);
        try {
            oOut.writeObject(this.userName + ": " + this.message);
        } catch (Exception e) {
            System.out.println("Error in ClientThread1 sendMsg: ");
            e.printStackTrace();
        }
        
    }
    
    public void setMsg(String msg) {
        this.message = msg;
    }
    
    private String getMsg() {
        return this.message;
    }
    
    private void updateTextbox(String msg) {
        
        try { 
                if (chatTextPane.getText().equals("")) {
                chatTextPane.setText(msg);
            }
            else {
                //chatTextPane.setText(chatTextPane.getText() + "\n" + msg);
                chatTextPane.getDocument().insertString(chatTextPane.getDocument().getLength(), "\n" + msg, null);
            }
        } catch (Exception e) {
            System.out.println("Error in ClientThread1 updateTextbox: ");
            e.printStackTrace();
        }
        
    }
    
    private void updatePaneWithImage(byte[] data) {
        
        try {
            
            ImageIcon imgIcon = new ImageIcon(data);
            Image img = imgIcon.getImage();
            Image newImg = img.getScaledInstance(120, 120, java.awt.Image.SCALE_SMOOTH);
            chatTextPane.getDocument().insertString(chatTextPane.getDocument().getLength(), "\n", null);
            chatTextPane.setSelectionStart(chatTextPane.getDocument().getLength());
            chatTextPane.insertIcon(new ImageIcon(newImg));
            
        } catch (Exception e) {
            System.out.println("Error in ClientThread1 updatePaneWithImage: ");
            e.printStackTrace();
        }
        
        
    }

}
