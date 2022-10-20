package ir.mvbdx.chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler extends Thread {
    private boolean exit;
    private final Socket socket;
    private final String clientName;
    private boolean loginSuccess;

    List<UserAccount> userAccounts = new ArrayList<>() {{
        add(new UserAccount("mohammad", "123"));
        add(new UserAccount("1", "1"));
        add(new UserAccount("m", "m"));
    }};

    public ClientHandler(Socket socket, String clientName) {
        this.socket = socket;
        this.clientName = clientName;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            String userNamePassword = input.readLine();
            String[] userPass = userNamePassword.split(";");
            UserAccount currentUser = new UserAccount(userPass[0], userPass[1]);

            for (UserAccount user : userAccounts) {
                if (currentUser.equals(user)) {
                    loginSuccess = true;
                    break;
                }
            }

            if (!loginSuccess) {
                output.println("Wrong username or password");
                socket.close();
                System.out.println("Client entered wrong user or pass. connection closed.");
            } else {
                output.println("Success!");
                System.out.println("Client logged in successfully!");
                MessageSender.lastMessageClientSocket = this.socket;
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
            }
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
