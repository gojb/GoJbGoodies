package test;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

import GoJbFrame.GoJbFrame;

public class negativeImage {

	GoJbFrame frame = new GoJbFrame("Negativ bild",false);

	static BufferedImage img;

	JFrame frame1=new JFrame();

	File filePath;

	public static void main(String[] args) {
		// FIXME Auto-generated method stub
		new negativeImage();
	}
	public negativeImage() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
		}

		JFileChooser chooser = new JFileChooser(System.getProperty("user.home") + "/Pictures");

			if (chooser.showOpenDialog(frame1) == JFileChooser.APPROVE_OPTION) {
				// do something
				System.out.println(chooser.getSelectedFile());
				filePath=chooser.getSelectedFile();
			}

		try {
			img = ImageIO.read(filePath);
			//			img = ImageIO.read(GoJbGoodies.class.getResource("/images/swe.jpg"));
		} catch (Exception e) {
			// FIXME Auto-generated catch block
			System.exit(3);
		}

		frame.setSize(img.getWidth(),img.getHeight());
		frame.setUndecorated(true);

		JLabel label = new JLabel(){
			private static final long serialVersionUID = 1L;
			@Override
			protected void paintComponent(Graphics g) {
				// FIXME Auto-generated method stub
				super.paintComponent(g);
				g.setColor(Color.blue);
				for(int i=0;i<img.getWidth()-1;i++){
					for(int i1=0;i1<img.getHeight()-1;i1++){
						g.setColor(new Color(new Color(255-img.getRGB(i, i1)).getRed(), 255-new Color(img.getRGB(i, i1)).getGreen(), 
								255-new Color(img.getRGB(i, i1)).getBlue()));
						g.drawRect(i, i1, 1, 1);
					}
				}
			}
		};

		frame.add(label);
		label.repaint();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}
}
