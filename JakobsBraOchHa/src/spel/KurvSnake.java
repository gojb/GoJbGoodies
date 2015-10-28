package spel;

import static gojb.GoJbsBraOchHa.fönsterIcon;
import static gojb.GoJbsBraOchHa.prop;
import static gojb.GoJbsBraOchHa.sparaProp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import GoJbFrame.GoJbFrame;
import gojb.GoJbsBraOchHa;

public class KurvSnake {
	private GoJbFrame start = new GoJbFrame("Start",false,2),frame = new GoJbFrame("KurvSnake",false,2),highFrame=new GoJbFrame("Tetris Highscore",false,JFrame.EXIT_ON_CLOSE);
	private JButton local = new JButton("Play two on this computer"),
			online = new JButton("Play online"),
			one = new JButton("Single Player");
	private Timer timer = new Timer(10, e -> update());
	private ArrayList<Pixel> pixels = new ArrayList<>();
	private double riktning,x,y;
	int längd=0;
	boolean höger,vänster;
	Pixel plupp;
	Random random = new Random();
	private ArrayList<String> highscore=new ArrayList<>();
	private JLabel label=new JLabel(){

		private static final long serialVersionUID = 1L;

		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(new Color(61,177,223));
			g2.fillRect(0,0, getWidth(), getHeight());
			g2.setColor(Color.red);
			plupp.draw(g2);
			g2.setColor(Color.black);
			for (Pixel pixel : pixels) {
				pixel.draw(g2);
			}
		};
	};
	public KurvSnake() {
		for (int i = 1; i < 6; i++) {
			highscore.add(prop.getProperty("KurvSnake"+i,"0"));
		}
		frame.add(label);
		frame.setLayout(new GridLayout(1, 0));
		frame.addKeyListener(key);
		frame.setResizable(false);
		frame.addComponentListener(new ComponentListener() {
			@Override
			public void componentMoved(ComponentEvent e) {
				highFrame.setLocation(frame.getX()-highFrame.getWidth(),frame.getY());
			}
			@Override
			public void componentShown(ComponentEvent e) {}
			@Override
			public void componentResized(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
		frame.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {}
			
			@Override
			public void windowIconified(WindowEvent e) {}
			
			@Override
			public void windowDeiconified(WindowEvent e) {}
			
			@Override
			public void windowDeactivated(WindowEvent e) {}
			
			@Override
			public void windowClosing(WindowEvent e) {
				highFrame.dispose();
			}
			@Override
			public void windowActivated(WindowEvent e) {}

			@Override
			public void windowClosed(WindowEvent e) {}
		});
		start.setSize(240,140);
		start.setLayout(new GridLayout(0,1));
		start.setLocationRelativeTo(null);
		start.add(one);
		start.add(local);
		start.add(online);
		start.setVisible(true);
		highFrame.add(scorepanel);
		highFrame.setSize(230,frame.getHeight());
		highFrame.setIconImage(fönsterIcon);
		highFrame.setUndecorated(true);
		highFrame.setLocation(frame.getX()-highFrame.getWidth(),frame.getY());
		one.addActionListener(e -> {
			start.dispose();
			highFrame.setVisible(true);
			frame.setVisible(true);
			restart();
		});
	}
	public static void main(String[] args) {
		GoJbsBraOchHa.main("spel.KurvSnake");
	}
	
	private JPanel scorepanel = new JPanel(){
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			setBackground(Color.white);
			Graphics2D g2 = (Graphics2D)g;
			int pos = 50;

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(Color.red);
			g2.setFont(new Font(null, 0, 25));
			g.drawString("Highscore:",10 , pos);

			for (String string : highscore) {
				pos+=25;
				g.drawString(string,10 , pos);
			}
			g2.setColor(Color.green);
			g2.setFont(GoJbsBraOchHa.typsnitt);
			g2.drawString("Poäng: "+längd, 10, pos+100);
		}
	};
	
	private void update(){
		if (höger) riktning +=Math.PI/75;
		if (vänster) riktning -=Math.PI/75;
		x += Math.cos(riktning)	;
		y += Math.sin(riktning);
		pixels.add(new Pixel(x,y));
		frame.repaint();
		highFrame.repaint();
		while (pixels.size()-längd*80-300>=0) {
			pixels.remove(0);
		}
		for (int i = 0; i < pixels.size()-10; i++) {
			if (pixels.get(pixels.size()-1).touch(pixels.get(i))) {
				System.err.println("Game Over");
				timer.stop();
				Scanner scanner = new Scanner(highscore.get(4));
				int hs = scanner.nextInt();
				scanner.close();
				if (längd > hs) {
					highscore.set(4, längd + " " + JOptionPane.showInputDialog("Skriv ditt namn"));


					Collections.sort(highscore,new Comparator<String>() {
						public int compare(String o1, String o2) {
							Scanner scanner = new Scanner(o1);
							Scanner scanner2 = new Scanner(o2);
							int a=scanner.nextInt(),b=scanner2.nextInt();
							scanner.close();
							scanner2.close();
							return a > b ? -1 : a == b ? 0 : 1;
						}
					});
					for (int j = 0; j < highscore.size(); j++) {
						prop.setProperty("KurvSnake"+(j+1), highscore.get(j));
					}
					sparaProp("Highscore i KurvSnake");
				}
				highFrame.repaint();
				break;
			}
		}
		if (pixels.get(pixels.size()-1).touch(plupp)) {
			längd++;
			plupp();
		}
		if (x<0-pixels.get(0).diameter/2) {
			x=frame.getWidth()-pixels.get(0).diameter/2;
		}
		if (x>frame.getWidth()) {
			x=0-pixels.get(0).diameter/2;
		}
		if (y<0-pixels.get(0).diameter/2) {
			y=frame.getHeight()-pixels.get(0).diameter/2;
		}
		if (y>frame.getHeight()) {
			y=0-pixels.get(0).diameter/2;
		}
	}
	void plupp(){
		plupp=new Pixel(random.nextInt((frame.getWidth()-5*20)+40), random.nextInt((frame.getHeight()-5*20)+40), 20);
		for (Pixel pixel : pixels) {
			if (plupp.touch(pixel)) {
				plupp();
			}
		}
	}
	private void restart() {
		x=0;
		y=0;
		pixels.clear();
		längd=0;
		riktning=Math.PI/8;
		plupp();
		timer.start();
		
	}
	KeyListener key= new KeyListener() {

		@Override
		public void keyTyped(KeyEvent e) {}

		@Override
		public void keyReleased(KeyEvent e) {	
			if (e.getKeyCode()==KeyEvent.VK_LEFT) {
				vänster=false;
			}
			else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
				höger=false;
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_LEFT) {
				vänster=true;
			}
			else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
				höger=true;
			}
			else if (e.getKeyCode()==KeyEvent.VK_R) {
				restart();
			}
		}
	};
	public class Pixel{
		private double x,y;
		private int diameter;
		public Pixel(double x,double y) {
			this(x,y,8);
		}
		public Pixel(double x,double y, int diameter) {
			this.x=x;
			this.y=y;
			this.diameter=diameter;
		}
		void draw(Graphics2D g){
			g.fillOval((int)Math.round(x), (int)Math.round(y), diameter,diameter);
		}
		boolean touch(Pixel pixel){
			
			return Math.sqrt(Math.pow(x-pixel.x,2)+Math.pow(y-pixel.y,2))<=diameter/2+pixel.diameter/2;
		}
	}
}
