package application;

import javax.swing.*;
import java.awt.*;

/** it's create the array of color to pick*/
public class ArrayColorSelector extends JPanel {


    /** height of the JPanel*/
    private static final int HEIGHT = 765;
    /** width of the JPanel*/
    private static final int WIDTH = 765;
    /** ratio of the value HEIGHT/RATIO = 255*/
    private static final int RATIO = 3;

    private int valueOfRed = 180;
    private int valueOfGreen = 80;
    private int valueOfBlue = 20;

    /** the graphics*/
    private Graphics graphics;

    public ArrayColorSelector() {
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);
        graphics = g;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (int i = 0; i < WIDTH/ RATIO; i++){
            for (int j = 0; j < HEIGHT/ RATIO; j++){
                g.setColor(new Color( setNorme( valueOfRed-j+i ), setNorme( valueOfGreen-j+i), setNorme( valueOfBlue-j+i  )));
                g.fillRect(i*RATIO, j*RATIO, RATIO, RATIO);
            }
        }
    }

    /** set the color reference to the color*/
    public void setValueOfColor(Color color){
        this.valueOfRed = color.getRed();
        this.valueOfBlue = color.getBlue();
        this.valueOfGreen = color.getGreen();
    }

    /** set the value beetwen in 0 255*/
    private int setNorme(int value){
        if (value < 0) return 0;
        if (value > 255) return 255;
        return value;
    }
}
