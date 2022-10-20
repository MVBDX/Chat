package ir.mvbdx.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MessageSender extends Thread {

    public static Socket lastMessageClientSocket;

    public MessageSender(Socket socket) {
        lastMessageClientSocket = socket;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                new PrintWriter(lastMessageClientSocket.getOutputStream(), true).println(scanner.nextLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}