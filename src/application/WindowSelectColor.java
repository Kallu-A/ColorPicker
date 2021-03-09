package application;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/** the Main class for the Window selector*/
public class WindowSelectColor extends JFrame {

    /** the height of the JFrame */
    public static final int HEIGHT = 850;

    /** the width of the JFrame*/
    public static final int WIDTH = 765;

    /** JPanel with all the color*/
    private ArrayColorSelector arrayColor;

    /** line of color for edit the arrayColor*/
    private LineSelectColor lineColor;

    /** the contentPane*/
    private final JPanel contentPane;

    /** the JPanel where all the date of the color will be*/
    private JPanel infoColor;

    /** show the color in big*/
    private JLabel colorSelect;

    /** show the value rgb of the color*/
    private JLabel valueRGB;

    /** show the value hexa of the color*/
    private JLabel valueHexa;

    public WindowSelectColor() throws HeadlessException {
        super("Select your color");

        setSize(WIDTH, HEIGHT );
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        setLookAndFeel();
        this.contentPane =  (JPanel) getContentPane();
        contentPane.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
        contentPane.setLayout(new BorderLayout(3,5));
        contentPane.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseMoved(e);
                MouseOnMove(e);
            }
        });

        contentPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                MouseOnMove(e);
            }
        });


        createComponent();

        setVisible(true);
    }

    /** set the look and feel*/
    private void setLookAndFeel(){
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    /** action when the mouse move*/
    private void MouseOnMove(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        Rectangle rectangle = contentPane.getBounds();
        BufferedImage pixel = new BufferedImage(rectangle.width,rectangle.height, BufferedImage.TYPE_INT_ARGB);
        contentPane.paintAll(pixel.createGraphics());
        if (coordInArray(x, y)) {
            Color color =new Color(pixel.getRGB(x,y), true);
            setInfoColor(color);
        } else {
            if (coordInLine(x, y)){
                Color color =new Color(pixel.getRGB(x,y), true);
                arrayColor.setValueOfColor(color);
                setInfoColor(color);
                arrayColor.paint(arrayColor.getGraphics());
            }
        }

    }

    /** create all the JPanel and add them*/
    private void createComponent(){
        arrayColor = new ArrayColorSelector();
        lineColor = new LineSelectColor();
        infoColor = createInfoColor();

        contentPane.add(arrayColor, BorderLayout.CENTER);
        contentPane.add(lineColor, BorderLayout.SOUTH);
        contentPane.add(infoColor, BorderLayout.NORTH);
    }


    /** create the JPanel who contains all the date of the color pick*/
    private JPanel createInfoColor(){
        JPanel pane = new JPanel();
        pane.setMinimumSize(new Dimension( 60, 60));
        pane.setMaximumSize(new Dimension( 60, 60));
        pane.setLayout(new FlowLayout());

        colorSelect = new JLabel("     ");
        colorSelect.setOpaque(true);

        valueRGB = new JLabel("Value RGB ");
        valueHexa = new JLabel("Value hexadecimal ");

        pane.add(colorSelect);
        pane.add(valueRGB);
        pane.add(valueHexa);

        return pane;
    }

    /** actualise all the date of the new pixel chose*/
    private void setInfoColor(Color color){
        colorSelect.setBackground(color);
        valueRGB.setText("Value RGB | R:" + color.getRed() + " G:" + color.getGreen() + " B:" + color.getBlue());
        valueHexa.setText("Value hexadecimal : #"+Integer.toHexString(color.getRGB()).substring(2));
    }

    /** return if the coord is in the array for select the color*/
    private boolean coordInArray(int x, int y){
        return  (contentPane.getComponentAt(x, y) instanceof  ArrayColorSelector);
    }

    /** return if the coord is in the line selector */
    private boolean coordInLine(int x, int y){
        return (contentPane.getComponentAt(x, y) instanceof  LineSelectColor);
    }
}
