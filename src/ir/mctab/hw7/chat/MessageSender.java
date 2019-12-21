package ir.mctab.hw7.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class MessageSender extends Thread {

    public static Socket lastMessageClientSocket;

    public MessageSender(Socket socket) {
        this.lastMessageClientSocket = socket;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        PrintWriter output;
        while (true) {
            try {
                String msg = scanner.nextLine();
                output = new PrintWriter(lastMessageClientSocket.getOutputStream(), true);
                output.println(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}