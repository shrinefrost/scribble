package src.client;

import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private GameWindow gameWindow;
    private String username;

    public Client(String serverAddress, GameWindow gameWindow, String username) {
        this.gameWindow = gameWindow;
        this.username = username;

        try {
            socket = new Socket(serverAddress, 12345);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            sendMessage("JOIN:" + username);

            new Thread(this::listenForMessages).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        if (out != null) {
            out.println(message);
        }
    }

    private void listenForMessages() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                if (message.startsWith("ROLE:DRAWER")) {
                    gameWindow.updateTurn(true);
                } else if (message.startsWith("ROLE:GUESSER")) {
                    gameWindow.updateTurn(false);
                } else if (message.startsWith("WORD_SELECTION:")) {
                    String[] words = message.replace("WORD_SELECTION:", "").split(",");
                    gameWindow.showWordSelection(words);
                } else if (message.startsWith("DRAW:")) {
                    String[] coordinates = message.replace("DRAW:", "").split(",");
                    int x = Integer.parseInt(coordinates[0]);
                    int y = Integer.parseInt(coordinates[1]);
                    gameWindow.getCanvas().drawPoint(x, y);  // ✅ FIX: Removed unnecessary `getClient()`
                } else {
                    gameWindow.processServerMessage(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameWindow getGameWindow() {
        return gameWindow;
    }
}
