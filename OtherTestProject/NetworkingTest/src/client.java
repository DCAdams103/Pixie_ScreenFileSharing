import java.net.*;
import java.io.*;
import java.util.Scanner;

public class client {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 5000);
        // 192.168.1.151
        PrintWriter pr = new PrintWriter(s.getOutputStream());
        pr.println("is it working");
        pr.flush();

        InputStreamReader in = new InputStreamReader(s.getInputStream());
        BufferedReader bf = new BufferedReader(in);
        System.out.println("test");
        String str = bf.readLine();

        while(str != null) {

            System.out.println("server: " + str);

            Scanner scanner = new Scanner(System.in);
            System.out.print("Say smth: ");
            String fromUser = scanner.nextLine();

            if(fromUser != null){
                pr.println(fromUser);
                pr.flush();
            }

            if(fromUser.equals("Exit"))
                break;

            str = bf.readLine();

        }



    }
}
