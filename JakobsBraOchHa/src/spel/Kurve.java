package spel;

import static gojb.GoJbsBraOchHa.SK�RM_SIZE;
import static gojb.GoJbsBraOchHa.autoListener;
import static gojb.GoJbsBraOchHa.typsnitt;
import static java.awt.Color.BLACK;
import static java.awt.Color.GREEN;
import static java.awt.Color.MAGENTA;
import static java.awt.Color.red;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

import GoJbFrame.GoJbFrame;

@SuppressWarnings("serial")
public
class Kurve implements ActionListener,KeyListener{
	private Pixel[] pixels = new Pixel[100000];
	private int l�ngd,i,po�ng;
	private final int PIXEL = 5;
	private double x,y,riktning;
	private boolean h�ger,v�nster;
	private Timer timer = new Timer(10, this);
	boolean ritaom=true;
	public Kurve() {
		frame.add(label);
		frame.addKeyListener(this);
		frame.addWindowListener(autoListener);
		frame.setResizable(false);
		frame.setSize((int)(SK�RM_SIZE.width*0.60),(int)(SK�RM_SIZE.height*0.60));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		label.setOpaque(true);
		Restart();
	}
	private GoJbFrame  frame = new GoJbFrame("Kurve",false,JFrame.DISPOSE_ON_CLOSE){
		public void validate() {
			ritaom=true;
			super.validate();
			ritaom=true;
		}
		public void repaint() {
			ritaom=true;
			super.repaint();
			ritaom=true;
		};
	};
	private JLabel label = new JLabel(){
		protected void paintComponent(Graphics g) {
			Graphics2D g2=(Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			if (ritaom){
				g2.setColor(BLACK);
				g2.fillRect(0, 0, getWidth(), getHeight());
				ritaom=false;
				for (int i = 1; i < l�ngd; i++) {
					g2.setColor(red);
					pixels[i].draw(g2);
				}
				g2.setColor(GREEN);
				g2.drawString(po�ng+"", 50, getWidth()/2);
			}
			else {
				g2.setColor(red);
				try {
					pixels[l�ngd].draw(g2);
				} catch (Exception e) {}
			}
			if (!timer.isRunning()) {
				g2.setColor(MAGENTA);
				g2.setFont(typsnitt);
				g2.drawString("Game over!", label.getWidth()/2-g2.getFontMetrics().stringWidth("Game over!")/2, label.getHeight()/2);
			}

		}
	};
	@Override
	public void actionPerformed(ActionEvent e) {
		if (h�ger) riktning +=0.04;
		if (v�nster) riktning -=0.04;
		x += Math.cos(riktning);
		y += Math.sin(riktning);
		if (i++<200) {
			pixels[++l�ngd]=new Pixel(x,y);
			for (int i = 1; i < l�ngd-10; i++) {
				if (pixels[l�ngd].touch(pixels[i])) {
					GameOver();
					break;
				}
			}
			label.repaint();
		}
		else if (i>220) {
			i=0;
			ritaom=true;
			po�ng++;
		}
	}
	private void GameOver(){
		timer.stop();
		frame.repaint();
	}
	private void Restart() {
		po�ng=0;
		l�ngd=0;
		x=20;
		y=20;
		riktning=0;
		i=0;
		ritaom=true;
		frame.repaint();
		timer.start();
	}
	@Override 
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_LEFT) {
			v�nster=true;
		}
		else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
			h�ger=true;
		}
		else if (e.getKeyCode()==KeyEvent.VK_R) {
			Restart();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_LEFT) {
			v�nster=false;
		}
		else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
			h�ger=false;
		}
	}
	public class Pixel{
		private double x,y;
		Pixel(double x,double y) {
			this.x=x;
			this.y=y;
		}
		void draw(Graphics2D g){
			g.fillOval((int)Math.round(x), (int)Math.round(y), PIXEL,PIXEL);
		}
		boolean touch(Pixel pixel){
			return Math.sqrt(Math.pow(x-pixel.x,2)+Math.pow(y-pixel.y,2))<PIXEL;
		}
	}
}
