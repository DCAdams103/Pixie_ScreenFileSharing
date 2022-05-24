import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame("Pixie - Screen and File Sharing");
        JPanel panel = new JPanel();



        // Setup components
        JLabel label = new JLabel("Type your name: ");
        JLabel label2 = new JLabel("Type your name: ");
        JLabel label3 = new JLabel("Type your name: ");
        JButton button = new JButton();
        button.setText("Login");

        // Setup Layout
        panel.setLayout(new FlowLayout());

        // Add components to panel
        panel.add(label);
        panel.add(button);

        // Setup frame
        frame.setSize(450, 475);
        frame.setLocation(0, 0);
        frame.setLocationRelativeTo(null);
        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);



        try (Socket socket = new Socket("localhost", 5000)) {
            // Input from server
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            // User input
            Scanner scanner = new Scanner(System.in);
            String clientName = "empty";
            String userInput;
            ClientThread clientThread = new ClientThread(socket);
            clientThread.start();

            do {
                if(clientName.equals("empty")) {
                    System.out.println("Enter your name: ");
                    userInput = scanner.nextLine();
                    clientName = userInput;
                    output.println(userInput);
                    if (userInput.equals("exit")) {
                        break;
                    }
                } else {
                    String msg = ( "(" + clientName + ")" + " message: ");
                    System.out.println(msg);
                    userInput = scanner.nextLine();
                    output.println(msg + " " + userInput);
                    if (userInput.equals("exit")) {
                        break;
                    }

                }
            } while (!userInput.equals("exit"));

        } catch (Exception e) {
            System.out.println("Error in main: " + e.getStackTrace());
        }
    }

}