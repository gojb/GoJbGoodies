package test;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import GoJbFrame.GoJbFrame;

public class Kurvtest {
	private GoJbFrame frame = new GoJbFrame("KurvSnake",false,3);
	private Timer timer = new Timer(10, e -> update());
	private double x,y;
	private Pixel pixels = new Pixel(x, y);
	int riktning,längd=0,wait;
	boolean höger,vänster;
	Random random = new Random();
	int ax=200,ay=200;
	boolean a,s,d,w;
	private JLabel label=new JLabel(){

		private static final long serialVersionUID = 1L;

		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(new Color(61,177,223));
			g2.fillRect(0,0, getWidth(), getHeight());
			g2.setColor(Color.red);
			g2.drawRect(frame.getWidth()+10/2, 40, 10, 2);
			g2.setColor(Color.black);
				pixels.draw(g2);
			g2.drawRect(ax, ay, 100, 10);
		};
	};
	public Kurvtest() {
		
		
		frame.add(label);
		frame.setLayout(new GridLayout(1, 0));
		frame.addKeyListener(key);
		frame.setResizable(false);
//		highFrame.setVisible(true);
		frame.setVisible(true);
		restart();
	}
	public static void main(String[] args) {
		new Kurvtest();
	}

	private void update(){
		if (höger) riktning +=1;
		if (vänster) riktning -=1;
		x += Math.cos(Math.PI*riktning/80);
		y += Math.sin(Math.PI*riktning/80);
		pixels=new Pixel(x,y);
		frame.repaint();
		//		highFrame.repaint();
		if (a) {
			ax-=1;
		}
		if (s) {
			ay+=1;
		}
		if (d) {
			ax+=1;
		}
		if (w) {
			ay-=1;
		}
		
//		System.out.println(riktning);
		if (x-15<0) {
			riktning=80-riktning;
			System.err.println("1");
		}
		if (x+15>label.getWidth()) {
			riktning=80-riktning;
			System.err.println("2");
		}
		if (y-15<0) {
			riktning=-riktning;
		}
		if (y+15>label.getHeight()) {
			riktning=-riktning;
			System.err.println("4");
		}
		if (y+pixels.diameter/2>=ay&&y-pixels.diameter/2<=ay+10&&x>ax&&x<ax+100) {
			if (wait==0) {
				riktning=160-riktning-(((int)(ax-x)+50))*40/50;
			System.out.println(((int)(x-ax)+50));
			wait=20;
			}
			
		}
		else{
			wait=0;
		}
	}
	
	private void restart() {
		x=30;
		y=30;
		längd=0;
		riktning=20;
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
			else if (e.getKeyCode()==KeyEvent.VK_A) {
				a=false;
			}
			else if (e.getKeyCode()==KeyEvent.VK_S) {
				s=false;
			}
			else if (e.getKeyCode()==KeyEvent.VK_D) {
				d=false;
			}
			else if (e.getKeyCode()==KeyEvent.VK_W) {
				w=false;
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
			else if (e.getKeyCode()==KeyEvent.VK_A) {
				a=true;
			}
			else if (e.getKeyCode()==KeyEvent.VK_S) {
				s=true;
			}
			else if (e.getKeyCode()==KeyEvent.VK_D) {
				d=true;
			}
			else if (e.getKeyCode()==KeyEvent.VK_W) {
				w=true;
			}
		}
	};
	public class Pixel{
		private double x,y;
		private int diameter;
		public Pixel(double x,double y) {
			this(x,y,30);
		}
		public Pixel(double x,double y, int diameter) {
			this.x=x;
			this.y=y;
			this.diameter=diameter;
		}
		void draw(Graphics2D g){
			g.fillOval((int)Math.round(x)-diameter/2, (int)Math.round(y)-diameter/2, diameter,diameter);
		}
		boolean nuddar(Pixel pixel){
			return Math.sqrt(Math.pow(x-pixel.x,2)+Math.pow(y-pixel.y,2))<=diameter/2+pixel.diameter/2;
		}
	}
}
