package gojb;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

import javax.swing.JLabel;

import GoJbFrame.GoJbFrame;

public class Pacman implements KeyListener{
	enum Riktning {UPP, NER, H�GER, V�NSTER,STOPP};
	Riktning riktning;
	int �ppning,pacX=1,pacY,pacSize=50;
	boolean �ka = true;
	GoJbFrame frame = new GoJbFrame("Pacman");

	
	JLabel label = new JLabel(){
		private static final long serialVersionUID = 1L;
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			int gap = 0;
			if (riktning==Riktning.UPP) {
				gap=90;
			}
			if (riktning==Riktning.V�NSTER) {
				gap=180;
			}
			if (riktning==Riktning.NER) {
				gap=270;
			}
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(Color.yellow);
			g2.fillArc(pacX, pacY, pacSize, pacSize, �ppning+gap, 360-(�ppning*2));
		}
	};
	Timer timer = new Timer(5, e-> {
		int speed=2;
		if (riktning==Riktning.UPP) {
			pacY -=speed;
		}
		else if (riktning==Riktning.NER) {
			pacY +=speed;
		}
		else if (riktning==Riktning.H�GER) {
			pacX +=speed;
		}
		else if (riktning==Riktning.V�NSTER) {
			pacX -=speed;
		}
		if (pacX<0){
			riktning=Riktning.STOPP;
			pacX=0;
		}
		if (pacY<0){
			riktning=Riktning.STOPP;
			pacY=0;
		}
		if(pacX+pacSize>label.getWidth()){
			riktning=Riktning.STOPP;
			pacX=label.getWidth()-pacSize;
		}
		if(pacY+pacSize>label.getHeight()){
			riktning=Riktning.STOPP;
			pacY=label.getHeight()-pacSize;
		}
		if(riktning!=Riktning.STOPP){
			if (�ka) {
				�ppning+=2;
				if (�ppning>38) {
					�ka = false;
				}
			}
			else {
				�ppning-=2;
				if (�ppning<1) {
					�ka=true;
				}
			}
			frame.repaint();
		}
		
	}); 
	public Pacman() {
		frame.add(label);
		frame.revalidate();
		timer.start();
		frame.addKeyListener(this);
		label.setOpaque(true);
		label.setBackground(Color.black);
	}
	public static void main(String[] args) {
		new Pacman();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
			riktning=Riktning.H�GER;
		}
		else if (e.getKeyCode()==KeyEvent.VK_LEFT) {
			riktning=Riktning.V�NSTER;
		}
		else if (e.getKeyCode()==KeyEvent.VK_UP) {
			riktning=Riktning.UPP;
		}
		else if (e.getKeyCode()==KeyEvent.VK_DOWN) {
			riktning=Riktning.NER;
		}

	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
