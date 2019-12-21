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

        try (Socket socket = new Socket("localhost", 5000)) {

            System.out.println("Connected to server. Type your messages:");
            BufferedReader echoes = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter stringToEcho = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            // readMessage thread
            Thread readMessage = new Thread(() -> {
                while (!exit) {
                    try {
                        String msg = echoes.readLine();
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
            } while (!echoString.equals("exit"));


        } catch (IOException e) {
            System.out.println("Client Error: " + e.getMessage());
        }
    }
}
