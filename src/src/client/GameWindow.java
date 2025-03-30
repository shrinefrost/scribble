package src.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GameWindow extends JFrame {
    private DrawPanel canvas;
    private JLabel wordLabel;
    private JButton blackButton, redButton, blueButton, eraserButton, clearButton;
    private String selectedWord = "???"; // Default hidden word

    public GameWindow(String playerName, String role) {
        setTitle("Scribble Game - " + playerName);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ✅ Create canvas
        canvas = new DrawPanel();
        add(canvas, BorderLayout.CENTER);

        // ✅ Display selected word (Only for the drawer)
        wordLabel = new JLabel("Word: " + selectedWord);
        wordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(wordLabel, BorderLayout.NORTH);

        // ✅ Create a panel for tools (colors & buttons)
        JPanel toolsPanel = new JPanel();
        toolsPanel.setLayout(new FlowLayout());

        // ✅ Black Brush
        blackButton = new JButton("Black");
        blackButton.addActionListener(e -> canvas.setBrushColor(Color.BLACK));

        // ✅ Red Brush
        redButton = new JButton("Red");
        redButton.addActionListener(e -> canvas.setBrushColor(Color.RED));

        // ✅ Blue Brush
        blueButton = new JButton("Blue");
        blueButton.addActionListener(e -> canvas.setBrushColor(Color.BLUE));

        // ✅ Eraser (White Brush)
        eraserButton = new JButton("Eraser");
        eraserButton.addActionListener(e -> canvas.setBrushColor(Color.WHITE));

        // ✅ Clear Canvas
        clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> canvas.clearCanvas());

        // ✅ Add buttons to the tools panel
        toolsPanel.add(blackButton);
        toolsPanel.add(redButton);
        toolsPanel.add(blueButton);
        toolsPanel.add(eraserButton);
        toolsPanel.add(clearButton);

        // ✅ Add tools panel at the bottom
        add(toolsPanel, BorderLayout.SOUTH);

        // ✅ If the player is the drawer, show word selection
        if (role.equalsIgnoreCase("DRAWER")) {
            showWordSelection();
        }

        setVisible(true);
    }

    // ✅ Show word selection dialog
    private void showWordSelection() {
        String[] words = {"Apple", "Car", "House"}; // Example words
        selectedWord = (String) JOptionPane.showInputDialog(
                this,
                "Choose a word to draw:",
                "Word Selection",
                JOptionPane.PLAIN_MESSAGE,
                null,
                words,
                words[0]
        );

        if (selectedWord != null) {
            wordLabel.setText("Word: " + selectedWord);
        } else {
            selectedWord = "???"; // If no word is chosen
        }
    }

    public static void main(String[] args) {
        new GameWindow("player1", "DRAWER"); // Start as drawer (Test mode)
    }
}
