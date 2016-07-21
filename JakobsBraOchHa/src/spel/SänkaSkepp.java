package spel;
//Gröna försvinner inte när man rör på den. Göra så att man kan vrida på skeppen. Kan bara lägga skäpp på planen, och där det
//inte redan är skepp. Fixa server och lista med spelande


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import GoJbFrame.GoJbFrame;

public class SänkaSkepp {

	enum båttyp {fem,fyra,treEtt,treTvå,två};

	GoJbFrame frame = new GoJbFrame(false);
	JLabel[] egnaLabels, annanLabels;
	JLabel egenLabel = new JLabel(), annanLabel = new JLabel(), egenText = new JLabel("Din ruta"),
			annanText = new JLabel("Motståndares ruta"), spelruta = new JLabel(), textruta = new JLabel(),
			inställningar = new JLabel() {
		protected void paintComponent(java.awt.Graphics g) {
			super.paintComponent(g);
			for (int i = 0; i < 22 * 5; i += 22) {
				g.setColor(new Color(200, 200, 200));
				if(bol5){
					g.fillRect(5 + i, 10, 20, 20);
				}
				if (i < 22 * 4&&bol4) {
					g.setColor(new Color(201, 200, 200));
					g.fillRect(5 + i, 60, 20, 20);
				}

				if (i < 22 * 3) {
					if(bol3){
						g.setColor(new Color(202, 200, 200));
						g.fillRect(5 + i, 110, 20, 20);
					}
					if(bol2){
						g.setColor(new Color(203, 200, 200));
						g.fillRect(5 + i, 160, 20, 20);
					}
				}
				if (i < 22 * 2&&bol1) {
					g.setColor(new Color(204, 200, 200));
					g.fillRect(5 + i, 210, 20, 20);
				}
			}

		};
	};

	Robot rb;

	boolean bol5 = true, bol4 = true, bol3 = true, bol2 = true, bol1 = true, stayInside, error;

	int last1, last2, antalRutor;

	public static void main(String[] args) {
		new SänkaSkepp();
	}

	public SänkaSkepp() {
		frame.setSize(1000, 780);
		frame.setLocationRelativeTo(null);
		// frame.setResizable(false);
		// ---------------------------------------------------------------------------
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		frame.setBackground(Color.blue);

		frame.add(textruta, BorderLayout.NORTH);
		textruta.setOpaque(true);
		frame.add(spelruta, BorderLayout.CENTER);
		spelruta.setOpaque(true);
		frame.add(inställningar, BorderLayout.SOUTH);
		inställningar.setOpaque(true);

		inställningar.setPreferredSize(new Dimension(1000, 250));

		textruta.setPreferredSize(new Dimension(1000, 50));
		textruta.setLayout(new GridLayout(1, 2));
		textruta.add(egenText);
		textruta.add(annanText);

		egenText.setHorizontalAlignment(SwingConstants.CENTER);
		egenText.setFont(new Font("", Font.BOLD, 27));
		annanText.setHorizontalAlignment(SwingConstants.CENTER);
		annanText.setFont(new Font("", Font.BOLD, 27));

		spelruta.setLayout(new GridLayout(1, 2, 0, 50));
		spelruta.setPreferredSize(new Dimension(1000, 500));
		spelruta.add(egenLabel);
		spelruta.add(annanLabel);
		spelruta.setBackground(new Color(122, 221, 232));

		egnaLabels = new JLabel[100];
		egenLabel.setLayout(new GridLayout(10, 10, 3, 3));

		annanLabels = new JLabel[100];
		annanLabel.setLayout(new GridLayout(10, 10, 3, 3));

		for (int i = 0; i < egnaLabels.length; i++) {
			egnaLabels[i] = new JLabel();
			annanLabels[i] = new JLabel();

			egnaLabels[i].setText(Integer.toString(i));
			egnaLabels[i].setFont(new Font("", 0, 0));

			annanLabels[i].setText(Integer.toString(i));
			annanLabels[i].setFont(new Font("", 0, 0));

			egnaLabels[i].setBackground(new Color(207, 217, 220));
			annanLabels[i].setBackground(new Color(145, 176, 223));

			egnaLabels[i].setOpaque(true);
			annanLabels[i].setOpaque(true);

			egenLabel.add(egnaLabels[i]);
			annanLabel.add(annanLabels[i]);

			// Mouselisteners
			egnaLabels[i].addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
				}

				public void mousePressed(MouseEvent e) {
					int clicked = Integer.parseInt(((JLabel) e.getSource()).getText());
					if(!error){
						for(int i = 0;i<antalRutor;i++){
							egnaLabels[clicked+(10*i)].setBackground(Color.green);
						}
					}
				}

				public void mouseExited(MouseEvent e) {
					// FIXME Auto-generated method stub
					egnaLabels[last1].setBackground(new Color(207, 217, 220));
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// FIXME Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// FIXME Auto-generated method stub

				}
			});
			egnaLabels[i].addMouseMotionListener(new MouseAdapter() {
				public void mouseMoved(MouseEvent e) {
					int clicked = Integer.parseInt(((JLabel) e.getSource()).getText());
					System.out.println(egnaLabels[last1].getBackground()+" --- "+Color.green);
					if(egnaLabels[last1].getBackground().getRGB()!=Color.green.getRGB()){
						egnaLabels[last1].setBackground(new Color(207, 217, 220));
						try {
							for(int i = 1;i<7;i++){
								egnaLabels[last1+(10*i)].setBackground(new Color(207, 217, 220));
							}
						} catch (Exception e2) {
							// FIXME: handle exception
						}
					}
					egnaLabels[clicked].setBackground(Color.black);
					last1 = clicked;

					for(int i = 1;i<antalRutor;i++){
						try {
							egnaLabels[clicked+(10*i)].setBackground(Color.black);
							error=false;
						} catch (Exception e2) {
							error=true;
						}

					}

				}

				@Override
				public void mouseDragged(MouseEvent e) {
				}
			});

			annanLabels[i].addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
				}

				public void mousePressed(MouseEvent e) {
				}

				public void mouseExited(MouseEvent e) {
					// FIXME Auto-generated method stub
					annanLabels[last2].setBackground(new Color(145, 176, 223));
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// FIXME Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// FIXME Auto-generated method stub

				}
			});
			annanLabels[i].addMouseMotionListener(new MouseAdapter() {
				public void mouseMoved(MouseEvent e) {
					int clicked = Integer.parseInt(((JLabel) e.getSource()).getText());
					annanLabels[last2].setBackground(new Color(145, 176, 223));
					annanLabels[clicked].setBackground(Color.black);
					last2 = clicked;

				}

				@Override
				public void mouseDragged(MouseEvent e) {
				}
			});
			spelruta.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// FIXME Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					// FIXME Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// FIXME Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// FIXME Auto-generated method stub

				}
			});

		}
		frame.revalidate();
		inställningar.repaint();
		frame.repaint();
		inställningar.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// FIXME Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				try {
					rb = new Robot();
				} catch (AWTException e1) {
					// FIXME Auto-generated catch block
					e1.printStackTrace();
				}
				if(!stayInside){
					Color color = rb.getPixelColor(e.getLocationOnScreen().x, e.getLocationOnScreen().y);
					if (color.getRed() == 238) {
						color = rb.getPixelColor(e.getLocationOnScreen().x + 10, e.getLocationOnScreen().y);
					}
					System.out.println(color.getRed());
					if (color.getRed() == 200) {
						System.err.println(5);
						bol5=false;
						placeraSkepp(båttyp.fem);
					}
					else if (color.getRed() == 201) {
						System.err.println(4);
						bol4=false;
						placeraSkepp(båttyp.fyra);
					}
					else if (color.getRed() == 202) {
						System.err.println(3);
						bol3=false;
						placeraSkepp(båttyp.treEtt);
					}
					else if (color.getRed() == 203) {
						System.err.println(3);
						bol2=false;
						placeraSkepp(båttyp.treTvå);
					}
					else if (color.getRed() == 204) {
						System.err.println(2);
						bol1=false;
						placeraSkepp(båttyp.två);
					}
					inställningar.repaint();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// FIXME Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// FIXME Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {}
		});

	}


	public void placeraSkepp(båttyp båt) {
		stayInside=true;
		if(båt==båttyp.fem){
			antalRutor=5;
		}


	}
}
