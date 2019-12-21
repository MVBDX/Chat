package ir.mctab.hw7.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Echoer extends Thread {
    private Socket socket;

    public Echoer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);

            String echoString;
            while (true) {
                echoString = input.readLine();
                if (echoString.equals("exit")) {
                    break;
                } else {
                    System.out.println(echoString);
                }
                Scanner scanner = new Scanner(System.in);
                echoString = scanner.nextLine();
                output.println(echoString);
            }

        } catch (IOException e) {
            System.out.println("Oops: " + e.getMessage());
        } finally {
            try {
                socket.close();
                System.out.println("Closing connection");
            } catch (IOException e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }
}
