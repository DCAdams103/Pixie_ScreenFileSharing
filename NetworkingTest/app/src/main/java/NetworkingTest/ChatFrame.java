/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package NetworkingTest;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Files;
import java.util.Scanner;
import javax.swing.JFileChooser;

/**
 *
 * @author Test Account
 */
public class ChatFrame extends javax.swing.JFrame {

    static String userName;
    static PrintWriter output;
    static ObjectOutputStream oOut;
    String chatText;
    static String text = "";
    static String test = "";
    ClientThread clientThread;
    
    /**
     * Creates new form ChatFrame
     */
    public ChatFrame() {
        initComponents();
    }
    
    public void setUserName(String name){
        this.userName = name;
        userNameLabel.setText(userName);
    }
    
    private PrintWriter getPW() {
        return output;
    }
    
    private void setPW(PrintWriter out) {
        this.output = out;
    }
    
    private void setOOut(ObjectOutputStream oOut) {
        this.oOut = oOut;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jLabel1 = new javax.swing.JLabel();
        userNameLabel = new javax.swing.JLabel();
        msgTextField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        attachButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        chatTextPane = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("You're logged in as: ");

        msgTextField.setToolTipText("Type your Message");
        msgTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                msgTextFieldActionPerformed(evt);
            }
        });

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        attachButton.setText("Attach File");
        attachButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                attachButtonActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(chatTextPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(232, 232, 232)
                        .addComponent(userNameLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(332, 332, 332)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(167, 167, 167)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(msgTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 339, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(attachButton, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(sendButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(193, 193, 193))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(userNameLabel)
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(sendButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(attachButton))
                    .addComponent(msgTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void startChat(){
        
        msgTextField.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                sendButtonActionPerformed(e);
            }
            
        });
        
        try {
            System.out.println("create");
            Socket socket = new Socket("localhost", 5000);
            System.out.println("created");
            // 107.134.20.114
            //192.168.1.151
            // Input from server
            //BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            //output = new PrintWriter(socket.getOutputStream(), true);
            ObjectOutputStream objOut = new ObjectOutputStream(socket.getOutputStream());
            setOOut(objOut);
            //setPW(output);
            
            clientThread = new ClientThread(socket, chatTextPane, userName, output, oOut);
            
            clientThread.start();
            
            //output.println(userName + " connected.");
            //output.flush();
            
            objOut.writeObject(userName + " connected.");
            objOut.flush();
            
            //startReading(input);
            //startWriting(socket, output);
            
//            while(!text.equals("Exit")) {
//                text = input.readLine();
//                msgTextField.setText(msgTextField.getText() + "\n" + userName + ": " + text);
//            }

        } catch (Exception e) {
            System.out.println("Error in connection: ");
            e.printStackTrace();
        }
    }
    
    public void startReading(BufferedReader bf) {
        Runnable r1 = () -> {
            System.out.println("Reader Started...");
            
            try {
                while(true) {
                    
                    String msg = bf.readLine();
                    //updateTextbox(msg);
                    
                }
            } catch(Exception e) {
                System.out.println("Error in startReading: ");
                e.printStackTrace();
            }
            
        };
        
        new Thread(r1).start();
        
    }
    
    public String getInput() {
        return text;
    }
    
    private void setInput(String newText) {
        this.text = newText;
    }
    
    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
          this.clientThread.setMsg(msgTextField.getText());
          this.clientThread.sendMsg();
          
//          setInput(msgTextField.getText());
//          PrintWriter output = getPW();
//          output.println(userName + ": " + getInput());
//          output.flush();

          msgTextField.setText("");
        
    }//GEN-LAST:event_sendButtonActionPerformed

    private void msgTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_msgTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_msgTextFieldActionPerformed

    private void attachButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_attachButtonActionPerformed
        
        try {
            JFileChooser fc = jFileChooser1;
        
            int returnVal = fc.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();

                byte[] content = Files.readAllBytes(file.toPath());
                oOut.writeObject(content);
            }
            
        } catch (Exception e) {
            System.out.println("Error in ChatFrame attachBtn: ");
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_attachButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatFrame().setVisible(true);
            }
        });
        
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton attachButton;
    private javax.swing.JTextPane chatTextPane;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private static javax.swing.JTextField msgTextField;
    private javax.swing.JButton sendButton;
    private javax.swing.JLabel userNameLabel;
    // End of variables declaration//GEN-END:variables
}
