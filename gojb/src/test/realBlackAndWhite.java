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

package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;

import GoJbFrame.GoJbFrame;

public class realBlackAndWhite {

	GoJbFrame frame = new GoJbFrame(false);

	JFrame frame1=new JFrame();

	File filePath;

	static BufferedImage img;

	public static void main(String[] args) {
		// FIXME Auto-generated method stub
		new realBlackAndWhite();
	}

	public realBlackAndWhite() {
		// FIXME Auto-generated constructor stub

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
		}

		JFileChooser chooser = new JFileChooser();
		chooser.setCurrentDirectory(new File(System.getProperty("user.home") + "/Pictures"));
		chooser.setApproveButtonText("AYY");	
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setDragEnabled(true);
		chooser.setAcceptAllFileFilterUsed(false);

		chooser.addChoosableFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				// FIXME Auto-generated method stub
				return "Image files (.png, .jpg)";
			}

			@Override
			public boolean accept(File f) {
				// FIXME Auto-generated method stub
				String type=f.getName().substring(f.getName().length()-4);

				if(f.isDirectory()){
					return true;
				}
				else{
					if(type.equals(".png")||type.equals(".jpg")){
						return true;
					}
				}
				return false;
			}
		});

		chooser.addChoosableFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				// FIXME Auto-generated method stub
				return "Joint Photographic Experts Group (.jpg)";
			}

			@Override
			public boolean accept(File f) {
				// FIXME Auto-generated method stub
				String type=f.getName().substring(f.getName().length()-4);

				if(f.isDirectory()){
					return true;
				}
				else{
					if(type.equals(".jpg")||type.equals("jpeg")){
						return true;
					}
				}
				return false;
			}
		});
		chooser.addChoosableFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				// FIXME Auto-generated method stub
				return "Portable Network Graphics (.png)";
			}

			@Override
			public boolean accept(File f) {
				// FIXME Auto-generated method stub
				String type=f.getName().substring(f.getName().length()-4);

				if(f.isDirectory()){
					return true;
				}
				else{
					if(type.equals(".png")){
						return true;
					}
				}
				return false;
			}
		});



		chooser.setVisible(true);

		if (chooser.showOpenDialog(frame1) == JFileChooser.APPROVE_OPTION) {
			// do something
			System.out.println(chooser.getSelectedFile());
			filePath=chooser.getSelectedFile();
		}

		try {
			img = ImageIO.read(filePath);
			//						img = ImageIO.read(new File("C:\\Users\\Glenn\\Documents\\GD copy\\Annat skit\\Privat\\Bilder\\Rösjön.jpg"));
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
						int brightnes = (new Color(img.getRGB(i, i1)).getRed() + new Color(img.getRGB(i, i1)).getBlue()+
								new Color(img.getRGB(i, i1)).getGreen())/3;

						//Regular B&W
						g.setColor(new Color(brightnes,brightnes,brightnes));
						g.drawRect(i, i1, 1, 1);


						//Real B&W
						//						if(brightnes>=128){
						//							g.setColor(new Color(255,255,255));
						//							g.drawRect(i, i1, 1, 1);
						//						}
						//						else{
						//							g.setColor(new Color(0,0,0));
						//							g.drawRect(i, i1, 1, 1);
						//						}
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
