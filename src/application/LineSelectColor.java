package application;

import javax.swing.*;
import java.awt.*;

/** the line selector*/
public class LineSelectColor extends JPanel {

    private final static int WIDTH = 765;
    private final static int RATIO = 127;

    public LineSelectColor() throws HeadlessException {
        super();
        setSize(WIDTH, 15);
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.WHITE);

        for (int i= 0 ; i < WIDTH; i++){
            if (i < 127) g.setColor(new Color(255, (i % RATIO)*2, 0));
            else if ( i < 254) g.setColor(new Color(255 - (i % RATIO) * 2, 255, 0));
            else if ( i < 381) g.setColor(new Color(0, 255, (i % RATIO) *2 ));
            else if ( i < 508) g.setColor(new Color(0, 255 - (i % RATIO) *2, 255));
            else if ( i < 635) g.setColor(new Color((i % RATIO) *2, 0, 255));
            else g.setColor(new Color(255, 0 ,255 - (i % RATIO) *2));

            g.fillRect(i,0,1,15);
        }
    }


}
