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

    public ArrayColorSelector() {
        setSize(WIDTH, HEIGHT);
        setVisible(true);
        FlowLayout layout = new FlowLayout();
        layout.setAlignment(FlowLayout.CENTER);
        setLayout(layout);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);

        // paint the array and affect the color
        for (int i = 0; i < WIDTH/ RATIO; i++){
            for (int j = 0; j < HEIGHT/ RATIO; j++){
                g.setColor(new Color(j, i,50));
                g.fillRect(i*RATIO, j*RATIO, RATIO, RATIO);
            }
        }

    }
}
