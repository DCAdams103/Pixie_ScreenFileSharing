package PixieScreenShare;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

    public ClientThread(Socket s, javax.swing.JTextPane jTP, String userName, PrintWriter output, ObjectOutputStream oOut, ChatFrame cf) throws IOException {
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
                            
                            Object inObj = oIn.readObject();
                            String inStr = null;

                            if(inObj instanceof String)  {
                                inStr = (String)inObj;
                            } else if (inObj instanceof byte[]) {
                                updatePaneWithImage((byte[])inObj);
                            } else {
                                inStr = "Error";
                            }

                             if(inStr != null) {
                                 updateTextbox(inStr);
                             }

                        }       
                    } catch (Exception e) {
                        System.out.println("Error in ClientThread1: ");
                        e.printStackTrace();
                    }
                    
                }
            
            });
            
            msgRec.start();
            
    }
    
    public void sendMsg(String msg, String user, String opt) {

        try {
            oOut.writeObject(user + opt + ": " + msg);
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
            
            int img_width = img.getWidth(chatTextPane);
            int img_height = img.getHeight(chatTextPane);
            double ratio = 16.0 * img_width / img_height;
            
            //oOut.writeObject(this.userName + " sent a file:");
            chatTextPane.getDocument().insertString(chatTextPane.getDocument().getLength(), "\n" + this.userName + " sent a file: ", null);
            
            Image newImg = img.getScaledInstance((int)(img_width/ratio), (int)(img_height/ratio), java.awt.Image.SCALE_SMOOTH);
            chatTextPane.getDocument().insertString(chatTextPane.getDocument().getLength(), "\n", null);
            chatTextPane.setSelectionStart(chatTextPane.getDocument().getLength());
            chatTextPane.insertIcon(new ImageIcon(newImg));
            
        } catch (Exception e) {
            System.out.println("Error in ClientThread1 updatePaneWithImage: ");
            e.printStackTrace();
        }
        
        
    }

}
