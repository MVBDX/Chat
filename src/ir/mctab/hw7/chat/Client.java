package ir.mctab.hw7.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static boolean exit = false;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean loginSuccess = false;
        Socket socket;
        BufferedReader echoes = null;
        PrintWriter stringToEcho = null;

        do {
            System.out.println("Enter host name or IP:");
            String host = scanner.nextLine();
            System.out.println("Enter port:");
            int port = Integer.parseInt(scanner.nextLine());
            try {
                socket = new Socket(host, port);
                System.out.println("Connected to server.");
                echoes = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                stringToEcho = new PrintWriter(socket.getOutputStream(), true);
                System.out.println("Enter user name:");
                String userName = scanner.nextLine();
                System.out.println("Enter pasword:");
                String password = scanner.nextLine();
                stringToEcho.println(userName + ";" + password);
                String userPassMessage = echoes.readLine();
                if (userPassMessage.equals("Success!")) {
                    loginSuccess = true;
                    System.out.println("Login success! Start your chat with server:");
                } else {
                    System.out.println("Wrong username or password! Try again.");
                }
            } catch (IOException e) {
                System.out.println("Client Error: " + e.getMessage());
            }
        } while (!loginSuccess);

        // readMessage thread
        BufferedReader finalEchoes = echoes;
        Thread readMessage = new Thread(() -> {
            while (!exit) {
                try {
                    String msg = finalEchoes.readLine();
                    System.out.println(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        readMessage.start();

        String echoString;
        do {
            echoString = scanner.nextLine();
            stringToEcho.println(echoString);
            if (echoString.equals("exit")) exit = true;
        } while (!exit);

    }
}