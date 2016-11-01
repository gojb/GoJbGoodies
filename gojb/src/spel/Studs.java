package spel;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.Timer;

import GoJbFrame.GoJbFrame;

public class Studs implements KeyListener{

	GoJbFrame frame = new GoJbFrame("Studs", true, 3);

	int xBlock=150, yBoll=50;

	boolean höger,vänster;

	Timer timer, faller;

	JLabel label = new JLabel(){
		protected void paintComponent(java.awt.Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.blue);
			g.fillRoundRect(xBlock, 400, 200, 20,10,10);

			g.setColor(Color.red);
			g.fillOval(245, yBoll, 10, 10);

		}
	};

	public static void main(String[] args) {
		new Studs();
	}
	public Studs() {
		// FIXME Auto-generated constructor stub

		frame.add(label);
		frame.addKeyListener(this);


		faller = new Timer(10, e->{
			yBoll=yBoll+2;
			if(yBoll+10==400){
				faller.stop();
			}
			frame.repaint();
			frame.revalidate();
		});
		faller.start();
		timer = new Timer(10, e->{
			int i=0;
			if (höger) {
				i+=1;
			}
			if (vänster) {
				i-=1;
			}
			if(xBlock+(3*i)>=0&&(xBlock+(3*i)+210)<=500){
				xBlock=xBlock+(3*i);
				frame.revalidate();
				frame.repaint();
			}
		});
		timer.start();
		frame.repaint();
		frame.revalidate();
	}


	@Override
	public void keyTyped(KeyEvent e) {
		// FIXME Auto-generated method stub

	}
	@Override
	public void keyPressed(KeyEvent e) {
		// FIXME Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			vänster=true;
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			höger=true;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			vänster=false;
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			höger=false;
		}

	}
}
