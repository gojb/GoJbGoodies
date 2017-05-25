/*
 * Copyright 2017 GoJb Development
 *
 * Permission is hereby granted, free of charge, to any
 * person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to
 *  use, copy, modify, merge, publish, distribute, sublicense, and/or
 *  sell copies of the Software, and to permit persons to whom
 *  the Software is furnished to do so, subject to the following
 *  conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF
 * ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
 * SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package GlennsPack.GlennTest.components;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
 
/*
 * RootLayeredPaneDemo.java requires images/dukeWaveRed.gif.
 */
@SuppressWarnings("serial")
public class RootLayeredPaneDemo extends JPanel
                                 implements ActionListener,
                                            MouseMotionListener {
    private int[] layers = {1, 2, 3};
    private String[] layerStrings = { "Yellow (-30000)",
                                      "Magenta (0)",
                                      "Cyan (301)" };
    private Color[] layerColors = { Color.yellow,
                                    Color.magenta,
                                    Color.cyan };
 
    private JLayeredPane layeredPane;
    private JLabel dukeLabel;
    private JCheckBox onTop;
    private JComboBox<?> layerList;
 
    //Action commands
    private static String ON_TOP_COMMAND = "ontop";
    private static String LAYER_COMMAND = "layer";
 
    //Adjustments to put Duke's toe at the cursor's tip.
    private static final int XFUDGE = 40;
    private static final int YFUDGE = 57;
 
    //Initial layer of dukeLabel.
    private static final int INITIAL_DUKE_LAYER_INDEX = 1;
 
    public RootLayeredPaneDemo(JLayeredPane layeredPane)    {
        super(new GridLayout(1,1));
        //Create and load the duke icon.
        final ImageIcon icon = createImageIcon("images/dukeWaveRed.gif");
 
        //Create and set up the layered pane.
        this.layeredPane = layeredPane;
        layeredPane.addMouseMotionListener(this);
 
        //This is the origin of the first label added.
        Point origin = new Point(10, 100);
 
        //This is the offset for computing the origin for the next label.
        int offset = 35;
 
        //Add several overlapping, colored labels to the layered pane
        //using absolute positioning/sizing.
        for (int i = 0; i < layerStrings.length; i++) {
            JLabel label = createColoredLabel(layerStrings[i],
                                              layerColors[i], origin);
            layeredPane.add(label, new Integer(layers[i]));
            origin.x += offset;
            origin.y += offset;
        }
 
        //Create and add the Duke label to the layered pane.
        dukeLabel = new JLabel(icon);
        if (icon != null) {
            dukeLabel.setBounds(15, 225,
                                icon.getIconWidth(),
                                icon.getIconHeight());
        } else {
            System.err.println("Duke icon not found; using black square instead.");
            dukeLabel.setBounds(15, 225, 30, 30);
            dukeLabel.setOpaque(true);
            dukeLabel.setBackground(Color.BLACK);
        }
        layeredPane.add(dukeLabel,
                        new Integer(layers[INITIAL_DUKE_LAYER_INDEX]),
                        0);
 
        //Add control pane to this JPanel.
        add(createControlPanel());
    }
 
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = RootLayeredPaneDemo.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
 
    //Create and set up a colored label.
    private JLabel createColoredLabel(String text,
                                      Color color,
                                      Point origin) {
        JLabel label = new JLabel(text);
        label.setVerticalAlignment(JLabel.TOP);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setOpaque(true);
        label.setBackground(color);
        label.setForeground(Color.black);
        label.setBorder(BorderFactory.createLineBorder(Color.black));
        label.setBounds(origin.x, origin.y, 140, 140);
        return label;
    }
 
    //Create the control pane for the top of the frame.
    private JPanel createControlPanel() {
        onTop = new JCheckBox("Top Position in Layer");
        onTop.setSelected(true);
        onTop.setActionCommand(ON_TOP_COMMAND);
        onTop.addActionListener(this);
 
        layerList = new JComboBox<Object>(layerStrings);
        layerList.setSelectedIndex(INITIAL_DUKE_LAYER_INDEX);
        layerList.setActionCommand(LAYER_COMMAND);
        layerList.addActionListener(this);
 
        JPanel controls = new JPanel();
        controls.add(layerList);
        controls.add(onTop);
        controls.setBorder(BorderFactory.createTitledBorder(
                                 "Choose Duke's Layer and Position"));
        return controls;
    }
 
    //Make Duke follow the cursor.
    public void mouseMoved(MouseEvent e) {
        dukeLabel.setLocation(e.getX()-XFUDGE, e.getY()-YFUDGE);
    }
    public void mouseDragged(MouseEvent e) {} //do nothing
 
    //Handle user interaction with the check box and combo box.
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
 
        if (ON_TOP_COMMAND.equals(cmd)) {
            if (onTop.isSelected())
                layeredPane.moveToFront(dukeLabel);
            else
                layeredPane.moveToBack(dukeLabel);
 
        } else if (LAYER_COMMAND.equals(cmd)) {
            int position = onTop.isSelected() ? 0 : -1;
            layeredPane.setLayer(dukeLabel,
                                 layers[layerList.getSelectedIndex()],
                                 position);
        }
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("RootLayeredPaneDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Create and set up the content pane.
        RootLayeredPaneDemo newContentPane = new RootLayeredPaneDemo(frame.getLayeredPane());
        frame.setContentPane(newContentPane);
 
        //Display the window.
        frame.setSize(new Dimension(300, 350));
        frame.setVisible(true);
    }
 
    public static void main(String[] args) {

    	createAndShowGUI();
    }
}