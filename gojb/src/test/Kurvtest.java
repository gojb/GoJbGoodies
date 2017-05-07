package test;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.MouseInputListener;

import GoJbFrame.GoJbFrame;

public class Kurvtest {
	private GoJbFrame frame = new GoJbFrame("KurvSnake",false,3);
	private Timer timer = new Timer(10, e -> update());
	private Boll boll;
	ArrayList<Kloss> klossar=new ArrayList<Kloss>();
	int riktning,wait;
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
			g2.drawRect(ax, ay, 100, 10);
			boll.draw(g2);
			for (Kloss kloss : klossar) {
				kloss.draw(g2);
			}

		};
	};
	public Kurvtest() {


		frame.add(label);
		frame.setLayout(new GridLayout(1, 0));
		frame.addKeyListener(key);
		frame.setResizable(false);
		frame.setVisible(true);
		restart();
		label.addMouseMotionListener(new MouseInputListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				ax=e.getX()-50;
				ay=e.getY()-5;
			}

			@Override
			public void mouseDragged(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseClicked(MouseEvent e) {}
		});
	}
	public static void main(String[] args) {
		new Kurvtest();
	}

	private void update(){
		if (höger) boll.riktning +=1;
		if (vänster) boll.riktning -=1;

		boll.move();

		frame.repaint();
		//		highFrame.repaint();
		if (a) {
			ax-=	2;
		}
		if (s) {
			ay+=2;
		}
		if (d) {
			ax+=2;
		}
		if (w) {
			ay-=2;
		}

		//		System.out.println(riktning);

	}

	private void restart() {
		boll=new Boll(ay, ax);
		klossar.clear();
		klossar.add(new Kloss(0,0));
		klossar.add(new Kloss(1,0));
		klossar.add(new Kloss(2,0));
		klossar.add(new Kloss(3,0));
		klossar.add(new Kloss(4,0));
		klossar.add(new Kloss(0,1));
		klossar.add(new Kloss(1,1));
		klossar.add(new Kloss(2,1));
		klossar.add(new Kloss(3,1));
		klossar.add(new Kloss(4,1));
		klossar.add(new Kloss(0,2));
		klossar.add(new Kloss(1,2));
		klossar.add(new Kloss(2,2));
		klossar.add(new Kloss(3,2));
		klossar.add(new Kloss(4,2));
		klossar.add(new Kloss(0,3)); 
		klossar.add(new Kloss(1,3)); 
		klossar.add(new Kloss(2,3)); 
		klossar.add(new Kloss(3,3)); 
		klossar.add(new Kloss(4,3)); 
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
	public class Boll{
		private double x,y;
		private int radie,riktning=20,speed=7;
		public Boll(double x,double y) {
			this(x,y,15);
		}
		public Boll(double x,double y, int radie) {
			this.x=x;
			this.y=y;
			this.radie=radie;
		}
		void draw(Graphics2D g){
			g.fillOval((int)Math.round(x)-radie, (int)Math.round(y)-radie, 2*radie,2*radie);
		}
		boolean nuddar(Boll pixel){
			return Math.sqrt(Math.pow(x-pixel.x,2)+Math.pow(y-pixel.y,2))<=radie+pixel.radie;
		}
		boolean nuddarKloss(Kloss kloss){
			return (int)y-radie<=(kloss.y+1)*kloss.höjd&&(int)y+radie>=kloss.y*kloss.höjd&&
					(int)x-radie<=(kloss.x+1)*kloss.bredd&&(int)x+radie>=kloss.x*kloss.bredd;
		}
		void move(){
			x += speed*Math.cos(Math.PI*riktning/80);
			y += speed*Math.sin(Math.PI*riktning/80);
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
				restart();
			}
			if (y+boll.radie>=ay&&y-boll.radie<=ay+10&&x>ax&&x<ax+100) {
				if (wait==0) {
					riktning=160-riktning-(((int)(ax-x)+50))*40/50;
					System.out.println(((int)(x-ax)+50));
					wait=20;
				}

			}
			else{
				wait=0;
			}
			ArrayList<Kloss>remlist=new ArrayList<>();
			for (Kloss kloss : klossar) {
				if (nuddarKloss(kloss)) {
					remlist.add(kloss);
					System.err.println(12345678);
					
				}
			}


			if (remlist.isEmpty()==false) {
				riktning=-riktning;
				for (Kloss kloss : remlist) {
					klossar.remove(kloss);
				}
				remlist.clear();
			}
		}
	}
	class Kloss{
		int höjd=30,bredd=100;
		Color färg;
		int x,y;
		public Kloss(int LocX, int LocY){
			x=LocX;
			y=LocY;
			färg=new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
		}

		public void draw(Graphics2D g2) {
			g2.setColor(Color.black);
			g2.drawRect(x*bredd, y*höjd, bredd, höjd);
			g2.setColor(färg);
			g2.fillRect(x*bredd+1, y*höjd+1, bredd-1, höjd-1);

		}
	}
}