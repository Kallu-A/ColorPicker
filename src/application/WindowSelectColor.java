package application;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.*;
import java.awt.event.ActionEvent;
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

    /** show the value rgb and be a input*/
    private JTextField valueRGBdisplay;

    /** show the value hex of the color*/
    private JLabel valueHex;

    /** show the value hexa and can be a input*/
    private JTextField valueHexDisplay;

    /** the cursor for select color*/
    private final Cursor cursorSelect = new Cursor(Cursor.CROSSHAIR_CURSOR);

    /** the cursor normal*/
    private final Cursor cursorDefault = new Cursor(Cursor.DEFAULT_CURSOR);

    public WindowSelectColor() throws HeadlessException {
        super("Select your color");

        //set
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
                mouseOnMove(e);
            }
        });

        contentPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mouseOnMove(e);
            }
        });

        createComponent();

        setInfoColor(new Color(180,80,20));
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
    private void mouseOnMove(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        Rectangle rectangle = contentPane.getBounds();
        BufferedImage pixel = new BufferedImage(rectangle.width,rectangle.height, BufferedImage.TYPE_INT_ARGB);
        contentPane.paintAll(pixel.createGraphics());
        Color color;
        //make sur the dragged doesn't create an error if the mouse is out of the JFrame
        try {
            color =new Color(pixel.getRGB(x,y), true);
        } catch (Exception error){
            return;
        }

        if (cordInArray(x, y)) {
            actionInArray(color);
        } else {
            if (cordInLine(x, y)){
                actionInLine(color);
            }
        }
    }


    /** do the action when it's the line move*/
    private void actionInLine(Color color){
        arrayColor.setValueOfColor(color);
        setInfoColor(color);
        arrayColor.paint(arrayColor.getGraphics());
    }

    /** do the action on the array*/
    private void actionInArray(Color color){
        setInfoColor(color);
    }

    /**do the action when a hex is input*/
    private void actionInInputHex(ActionEvent event){
        String value = valueHexDisplay.getText().replace(" ", "");
        Color color;
        try {
            color = Color.decode(value);
        } catch (Exception e){
            valueHexDisplay.setForeground(Color.decode("#ff001b"));
            return;
        }
        setInfoColor(color);
        arrayColor.setValueOfColor(color);
        arrayColor.paint(arrayColor.getGraphics());
    }

    /** do the actiion when rgb is input*/
    private void actionInInputRGB(ActionEvent event){
        String value = valueRGBdisplay.getText().replace(" ", "");
        String[] eachData = value.split(",");
       if (eachData.length < 3) {
           valueRGBdisplay.setForeground(Color.decode("#ff001b"));
           return;
       }
       Color color;
       try {
           color = new Color(Integer.parseInt(eachData[0]), Integer.parseInt(eachData[1]), Integer.parseInt(eachData[2]));
       } catch (Exception error){
           valueRGBdisplay.setForeground(Color.decode("#ff001b"));
           return;
       }
       setInfoColor(color);
       arrayColor.setValueOfColor(color);
       arrayColor.paint(arrayColor.getGraphics());
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
        valueHex = new JLabel("Value hexadecimal ");
        valueHexDisplay = new JTextField("       ");
        valueRGBdisplay = new JTextField("             ");
        valueRGBdisplay.addActionListener(this::actionInInputRGB);
        valueHexDisplay.addActionListener(this::actionInInputHex);

        pane.add(colorSelect);
        pane.add(valueRGB);
        pane.add(valueRGBdisplay);
        pane.add(valueHex);
        pane.add(valueHexDisplay);

        return pane;
    }

    /** actualise all the date of the new pixel chose*/
    private void setInfoColor(Color color){
        colorSelect.setBackground(color);
        valueRGB.setText("Value RGB :");
        valueRGBdisplay.setForeground(Color.BLACK);
        valueRGBdisplay.setText( color.getRed() + ", " + color.getGreen() + ", " + color.getBlue());
        valueHex.setText("Value hexadecimal :");
        valueHexDisplay.setForeground(Color.BLACK);
        valueHexDisplay.setText("#"+Integer.toHexString(color.getRGB()).substring(2));
    }

    /** return if the cord is in the array for select the color*/
    private boolean cordInArray(int x, int y){
        return  (contentPane.getComponentAt(x, y) instanceof  ArrayColorSelector);
    }

    /** return if the cord is in the line selector */
    private boolean cordInLine(int x, int y){
        return (contentPane.getComponentAt(x, y) instanceof  LineSelectColor);
    }
}
