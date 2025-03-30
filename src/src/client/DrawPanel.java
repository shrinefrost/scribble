package src.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class DrawPanel extends JPanel {
    private List<ColoredPoint> points;
    private Color brushColor = Color.BLACK; // Default brush color
    private boolean drawingEnabled = false;
    private int brushSize = 5; // Default brush size

    public DrawPanel() {
        points = new ArrayList<>();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (drawingEnabled) {
                    drawPoint(e.getX(), e.getY());
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (drawingEnabled) {
                    drawPoint(e.getX(), e.getY());
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (ColoredPoint p : points) {
            g.setColor(p.color);
            g.fillOval(p.x, p.y, brushSize, brushSize);
        }
    }

    /** ✅ Allows the player to draw points on the canvas */
    public void drawPoint(int x, int y) {
        points.add(new ColoredPoint(x, y, brushColor));
        repaint();
    }

    /** ✅ Enables or disables drawing */
    public void setDrawingEnabled(boolean enabled) {
        this.drawingEnabled = enabled;
    }

    /** ✅ Clears the entire drawing board */
    public void clearCanvas() {
        points.clear();
        repaint();
    }

    /** ✅ Changes the brush color */
    public void setBrushColor(Color color) {
        this.brushColor = color;
    }

    /** ✅ Changes the brush size */
    public void setBrushSize(int size) {
        this.brushSize = size;
    }
}

/** ✅ Class to store points with colors */
class ColoredPoint {
    int x, y;
    Color color;

    public ColoredPoint(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }
}
