package src.server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static List<ClientHandler> clients = new ArrayList<>();
    private static ClientHandler drawer = null;  // ✅ Now it's static

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started on port 5000...");

        while (true) {
            Socket socket = serverSocket.accept();
            ClientHandler client = new ClientHandler(socket);
            clients.add(client);
            new Thread(client).start();
        }
    }

    public static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter out;
        private BufferedReader in;

        public ClientHandler(Socket socket) {
            this.socket = socket;
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }

        @Override
        public void run() {
            try {
                sendMessage("JOIN:" + socket.getInetAddress());

                // ✅ Assign the first player as the drawer
                if (drawer == null) {
                    drawer = this;
                    sendMessage("ROLE:DRAWER");
                    sendMessage("WORD_SELECTION:apple,car,house"); // Example words
                }

                String message;
                while ((message = in.readLine()) != null) {
                    if (message.startsWith("DRAW:")) {
                        broadcast(message, this);
                    } else if (message.startsWith("WORD_SELECTED:")) {
                        broadcast(message, this);
                    } else {
                        broadcast(message, null);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
