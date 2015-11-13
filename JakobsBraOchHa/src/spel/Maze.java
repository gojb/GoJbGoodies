package spel;

import static gojb.GoJbGoodies.Bild;
import static gojb.GoJbGoodies.fönsterIcon;
import static gojb.GoJbGoodies.spelaLjud;
import static java.awt.Color.BLACK;
import static java.awt.Color.CYAN;
import static java.awt.Color.RED;
import static java.awt.Color.white;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public
class Maze extends JPanel implements ActionListener, MouseMotionListener{

	JFrame  level1 = new JFrame("Level 1");

	static JFrame startframe = new JFrame("Börja");

	JButton börja = new JButton("Start");


	int x = 3;
	int y = 600;	
	public Maze(){

		startframe.setVisible(true);
		startframe.add(börja);
		startframe.setSize(80, 80);
		startframe.setLocation(740, 290);
		startframe.setResizable(false);
		startframe.setDefaultCloseOperation(3);
		startframe.setIconImage(fönsterIcon);

		börja.addActionListener(this);

		level1.setBackground(BLACK);
		level1.setSize(418, 430);
		level1.setLocationRelativeTo(null);
		level1.setResizable(false);
		level1.add(this);	       
		level1.addMouseMotionListener(this);
		level1.repaint();
		level1.revalidate();
		level1.setResizable(false);
		level1.setDefaultCloseOperation(3);
		level1.setIconImage(fönsterIcon);

	}

	public void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(white);
		g2.fillRect(0, 350, 350, 50);

		g2.fillRect(0, 0, 200, 40);

		g2.fillRect(0, 0, 50, 2000);

		g2.fillRect(300, 50, 50, 300);

		g2.fillRect(170, 50, 170, 50);

		g2.fillRect(140, 50, 50, 190);

		g2.setColor(CYAN);
		g2.fillRect(150, 200, 30, 30);   

		g2.setColor(RED);
		g2.fillRect(x, y, 25, 25);

	}

	public void actionPerformed(ActionEvent arg0) {


		if (arg0.getSource() == börja){
			level1.setVisible(true);
			startframe.dispose();
		}

	}

	public void mouseDragged(MouseEvent arg0) {

	}

	public void mouseMoved(MouseEvent e) {

		x = e.getX() - 17;
		y = e.getY() - 45;
		System.out.println("Musen rör sig på: " + x  + ", " + y);
		level1.repaint();

		if (e.getY()> 60 &&  e.getY()< 95 && e.getX()> 42 && e.getX()< 470){
			level1.dispose();
			startframe.setVisible(true);
		}
		if (x > 25 && x < 140 && y < 350 && y > 60){
			level1.dispose();
			startframe.setVisible(true);
		}

		if (y < 350 && y > 215 && x > 25 && x < 300){
			level1.dispose();
			startframe.setVisible(true);
		}

		if (y < 350 && y > 75 && x < 300 && x > 165){
			level1.dispose();
			startframe.setVisible(true);	
		}

		if (x > 325){
			level1.dispose();
			startframe.setVisible(true);
		}
		if (x > 175 && y < 50){
			level1.dispose();
			startframe.setVisible(true);
		}

		if (y > 195 && y < 205 && x > 60 && x < 190){
			level1.dispose();
			new level2();
		}
	}
	class level2 extends JPanel implements MouseMotionListener{
		int y, x;
		JFrame level2 = new JFrame("Level 2");



		public level2(){



			level2.setBackground(BLACK);
			level2.setSize(418, 430);
			level2.setLocationRelativeTo(null);
			level2.setResizable(false);
			level2.addMouseMotionListener(this);
			level2.repaint();
			level2.revalidate();
			level2.setVisible(true);
			level2.add(this);
			level2.setResizable(false);
			level2.setDefaultCloseOperation(3);
			level2.setIconImage(fönsterIcon);


		}

		public void paintComponent (Graphics g) {
			Graphics2D g2 = (Graphics2D) g;

			g2.setColor(white);
			g2.fillRect(140, 190, 45, 45);

			g2.fillRect(145, 195, 35, 180);

			g2.fillRect(150, 340, 250, 35);

			g2.fillRect(365, 341, 35, -100);

			g2.fillRect(230, 241, 150, 35);

			g2.fillRect(230, 100, 35, 171);

			g2.fillRect(230, 100, 169, 35);

			g2.fillRect(364, 10, 35, 105);

			g2.setColor(CYAN);
			g2.fillRect(369, 17, 25, 25);   

			g2.setColor(RED);
			g2.fillRect(x, y, 20, 20);



		}


		public void mouseDragged(MouseEvent arg0) {}

		public void mouseMoved(MouseEvent e) {


			x = e.getX() - 17;
			y = e.getY() - 45;
			System.out.println("Musen rör sig på: " + x  + ", " + y);
			level2.repaint();


			if (y > 355 || x > 380 || y > 256 && y < 340 && x > 165 && x < 365||
					y < 339 && x > 165 && x < 230|| y < 241 && y > 115 && x > 245|| x < 364 && y < 100||
					x < 140||x < 145 && y > 215||y > 215 && y < 340 && x > 160 && x < 230||y < 190 && x < 230){
				Maze.startframe.setVisible(true);
				level2.dispose();
			}
			if (y < 22 && x > 363 && x < 379){
				level2.dispose();
				new level3();
			}


		}
	}
	class level3 extends JPanel implements MouseMotionListener{
		int y, x;
		JFrame level3 = new JFrame("Level 3");


		public level3(){

			level3.setBackground(BLACK);
			level3.setSize(418, 430);
			level3.setLocationRelativeTo(null);
			level3.setResizable(false);
			level3.addMouseMotionListener(this);
			level3.repaint();
			level3.revalidate();
			level3.setVisible(true);
			level3.add(this);
			level3.setResizable(false);
			level3.setDefaultCloseOperation(3);
			level3.setIconImage(fönsterIcon);

		}

		public void paintComponent (Graphics g) {
			Graphics2D g2 = (Graphics2D) g;

			g2.setColor(white);
			g2.fillRect(270, 5, 137, 37);

			g2.fillRect(270, 5, 32, 130);

			g2.fillRect(70, 135, 232, 27);

			g2.fillRect(70, 135, 23, 100);

			g2.fillRect(70, 235, 300, 23);

			g2.fillRect(353, 235, 23, 68);

			g2.fillRect(163, 370, 30, 50);

			g2.fillRect(172, 283, 200, 20);

			g2.fillRect(170, 283, 17, 150);

			g2.setColor(CYAN);
			g2.fillRect(170, 375, 17, 17);   

			g2.setColor(RED);
			g2.fillRect(x, y, 12, 12);



		}

		public void mouseDragged(MouseEvent arg0) {

		}

		public void mouseMoved(MouseEvent e) {

			x = e.getX() - 17;
			y = e.getY() - 45;
			System.out.println("Musen rör sig på: " + x  + ", " + y);
			level3.repaint();

			if (x > 290 && y < 235 && y > 30||y < 235 && y > 150 && x > 82||
					x > 395|| x > 364 && y > 30|| y < 310 && y > 291 && x > 173||y > 246 && 
					y < 283 && x < 353|| x < 170 && y > 246 && y < 310||x < 270 && y < 135){

				level3.dispose();
				Maze.startframe.setVisible(true);
			}

			if(y > 310){

				level3.dispose();
				new Mål();
			}
		}
	}

	class Mål{

		JFrame frame = new JFrame("Haha");

		public Mål(){
			frame.add(new JLabel(Bild("/images/Bild.jpg")));
			frame.pack();
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(3);
			frame.setIconImage(fönsterIcon);

			spelaLjud("/images/Ljud.wav");

		}
	}
}