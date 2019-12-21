package ir.mctab.hw7.chat;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Waiting for a client...");
            while (true) {
                new Echoer(serverSocket.accept()).start();
                System.out.println("New client accepted");
            }
        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }
}
