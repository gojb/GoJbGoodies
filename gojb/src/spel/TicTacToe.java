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

package spel;

import static gojb.GoJbGoodies.Bild;
import static java.awt.Color.BLACK;
import static java.awt.Color.WHITE;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

public class TicTacToe implements MouseInputListener, KeyListener, ActionListener{

	Timer timer = new Timer(50, this);

	JFrame frame = new JFrame("Tic Tac Toe"),
			Vinst = new JFrame(),
			tur = new JFrame();

	String sträng = new String();

	JLabel[] label = new JLabel[10];
	JLabel vinstlabel = new JLabel(),
			turLabel = new JLabel();

	ImageIcon 	o = Bild("/images/O.png"),
			x = Bild("/images/X.png");

	int a,å;

	public TicTacToe(){

		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridLayout(3,3,3,3));
		timer.start();

		for(int i = 1;i < label.length; i++){
			label[i] = new JLabel();
			label[i].setBackground(WHITE);
			label[i].setOpaque(true);
			label[i].addMouseListener(this);
			frame.add(label[i]);
		}
		frame.getContentPane().setBackground(BLACK);

		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(3);

		frame.addKeyListener(this);
		Vinst.addKeyListener(this);

		Vinst.setUndecorated(true);

		vinstlabel.setSize(500, 100);

		tur.setLocation(frame.getWidth() + 205, frame.getHeight()/2 - 65);
		tur.setSize(500, 75);
		tur.setAlwaysOnTop(true);

		turLabel.setHorizontalAlignment(JLabel.CENTER);
		turLabel.setFont(new Font("dslf",Font.ROMAN_BASELINE,50));
		tur.add(turLabel);
		turLabel.setForeground(WHITE);
		turLabel.setOpaque(true);

		turLabel.setBackground(BLACK);

		tur.setUndecorated(true);

		tur.setVisible(true);

		Vinst.setLocation(frame.getWidth()+205,frame.getHeight()+270);
		Vinst.setSize(500, 100);

		vinstlabel.setHorizontalAlignment(JLabel.CENTER);
		vinstlabel.setFont(new Font("dslf",Font.ROMAN_BASELINE,30));
		Vinst.add(vinstlabel);
		vinstlabel.setForeground(BLACK);

		vinstlabel.setOpaque(true);

	}

	public void mouseClicked(MouseEvent e) {
		for (int i = 1; i < label.length; i++) {
			if(e.getSource() == label[i]&&label[i].getIcon() == null){
				if (a==0) {
					X(i);
				}
				else if (a==1) {
					O(i);
				}
			}
		}
	}

	public void X(int intlabel) {
		label[intlabel].setIcon(x);
		//		repaint();
		a = 1;
	}

	public void O(int intlabelO) {
		label[intlabelO].setIcon(o);
		//		repaint();
		a = 0;
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == timer){

			if (a == 2){
				turLabel.setText("Tryck r för restart");	
			}

			if (a == 0){
				turLabel.setText("Nu spelar X");
			}

			if (a == 1){
				turLabel.setText("Nu spelar O");
			}


			frame.setAlwaysOnTop(true);

			if(label[1].getIcon() == o||label[1].getIcon() == x){
				if(label[2].getIcon() == o||label[2].getIcon() == x){
					if(label[3].getIcon() == o||label[3].getIcon() == x){
						if(label[4].getIcon() == o||label[4].getIcon() == x){
							if(label[5].getIcon() == o||label[5].getIcon() == x){
								if(label[6].getIcon() == o||label[6].getIcon() == x){
									if(label[7].getIcon() == o||label[7].getIcon() == x){
										if(label[8].getIcon() == o||label[8].getIcon() == x){
											if(label[9].getIcon() == o||label[9].getIcon() == x){

												Vinst.setVisible(true);
												//												repaint();
												vinstlabel.setHorizontalAlignment(JLabel.CENTER);
												vinstlabel.setText("Det blev lika");

												a = 2;
											}
										}
									}
								}
							}
						}
					}
				}
			}		
			frame.setLocationRelativeTo(null);

			if (label[4].getIcon() == o){
				if (label[5].getIcon() == o){
					if(label[6].getIcon() == o){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("O vann");
						å = 2;
					}

				}
			}

			if (label[2].getIcon() == o){
				if (label[5].getIcon() == o){
					if (label[8].getIcon() == o){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("O vann");
						å = 2;
					}
				}
			}


			if (label[3].getIcon() == o){
				if(label[6].getIcon() == o){
					if (label[9].getIcon() == o){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("O vann");
						å = 2;
					}
				}
			}


			if (label[7].getIcon() == o){
				if(label[5].getIcon() == o){
					if (label[3].getIcon() == o){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("O vann");
						å = 2;
					}
				}
				if (label[8].getIcon() == o){
					if (label[9].getIcon() == o){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("O vann");
						å = 2;
					}
				}
			}


			if (label[1].getIcon() == o){
				if (label[5].getIcon() == o){
					if (label[9].getIcon() == o){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("O vann");
						å = 2;
					}
				}
				if (label[4].getIcon() == o){
					if (label[7].getIcon()== o){
						Vinst.setVisible(true);
						//						repaint();
						a = 2;
						vinstlabel.setText("O vann");
						å = 2;
					}
				}
				if(label[2].getIcon() == o){
					if(label[3].getIcon() == o){


						Vinst.setVisible(true);
						//						repaint();
						vinstlabel.setText("O vann");

						å = 2;
						a = 2;

					}
				}
			}	
			if (label[4].getIcon() == x){
				if (label[5].getIcon() == x){
					if(label[6].getIcon() == x){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("X vann");
						å = 1;
					}

				}
			}

			if (label[2].getIcon() == x){
				if (label[5].getIcon() == x){
					if (label[8].getIcon() == x){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("X vann");
						å = 1;
					}
				}
			}


			if (label[3].getIcon() == x){
				if(label[6].getIcon() == x){
					if (label[9].getIcon() == x){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("X vann");
						å = 1;
					}
				}
			}


			if (label[7].getIcon() == x){
				if(label[5].getIcon() == x){
					if (label[3].getIcon() == x){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("X vann");
						å = 1;
					}
				}
				if (label[8].getIcon() == x){
					if (label[9].getIcon() == x){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("X vann");
						å = 1;
					}
				}
			}


			if (label[1].getIcon() == x){
				if (label[5].getIcon() == x){
					if (label[9].getIcon() == x){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("X vann");
						å = 1;
					}
				}
				if (label[4].getIcon() == x){
					if (label[7].getIcon()== x){
						Vinst.setVisible(true);
						//						repaint();
						a = 2;
						vinstlabel.setText("X vann");
						å = 1;
					}
				}
				if(label[2].getIcon() == x){
					if(label[3].getIcon() == x){


						Vinst.setVisible(true);
						//						repaint();
						vinstlabel.setText("X vann");
						å = 1;
						a = 2;

					}
				}
			}	
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {

		System.err.println(e.getKeyCode() + "   " + e.getKeyChar());

		if(e.getKeyCode() == 82){


			label[9].setIcon(null);
			label[8].setIcon(null);
			label[7].setIcon(null);
			label[6].setIcon(null);
			label[5].setIcon(null);
			label[4].setIcon(null);
			label[3].setIcon(null);
			label[2].setIcon(null);
			label[1].setIcon(null);
			if (å == 2){
				a = 0;
			}
			else if (å == 1){
				a = 1;
			}

			Vinst.setVisible(false);

			frame.repaint();

		}

	}

	public void keyReleased(KeyEvent e) {}
}