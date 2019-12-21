package ir.mctab.hw7.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler extends Thread {
    private boolean exit;
    private Socket socket;
    private String clientName;

    public ClientHandler(Socket socket, String clientName) {
        this.socket = socket;
        this.clientName = clientName;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Read message thread
            Thread readMessage = new Thread(() -> {
                while (!exit) {
                    try {
                        String msg = input.readLine();
                        if (msg.equals("exit")) {
                            exit = true;
                            break;
                        }
                        System.out.println(clientName + ": " + msg);
                        MessageSender.lastMessageClientSocket = this.socket;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            readMessage.start();

        } catch (IOException e) {
            System.out.println("Oops: " + e.getMessage());
        }
//        } finally {
//            try {
//                socket.close();
//                System.out.println("Closing connection");
//            } catch (IOException e) {
//                System.out.println("Error:" + e.getMessage());
//            }
//        }
    }
}
