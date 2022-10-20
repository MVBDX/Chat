package ir.mvbdx.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        boolean clientConnected = false;
        int i = 1;
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Waiting for a client...");
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(socket, "Client #" + i).start();
                System.out.println("New client accepted. Name set to: Client #" + i);
                i++;
                if (!clientConnected) {
                    MessageSender messageSender = new MessageSender(socket);
                    Thread threadMessageSender = new Thread(messageSender);
                    threadMessageSender.start();
                    clientConnected = true;
                }
            }
        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }
}
