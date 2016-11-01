package spel;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JLabel;
import javax.swing.Timer;

import GoJbFrame.GoJbFrame;

public class Studs implements KeyListener{

	GoJbFrame frame = new GoJbFrame("Studs", true, 3);

	int xBlock=150, yBoll=50, xBoll = 245;

	boolean höger,vänster;

	Timer timer, faller;

	JLabel label = new JLabel(){
		protected void paintComponent(java.awt.Graphics g) {
			super.paintComponent(g);
			g.setColor(Color.blue);
			g.fillRoundRect(xBlock, 400, 200, 20,10,10);

			g.setColor(Color.red);
			g.fillOval(xBoll, yBoll, 10, 10);
			g.drawRect(500, 1, 1, 500);
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
				if(250-xBlock>-1&&250-xBlock<201){
					System.out.println("TRÄFF "+(150-xBlock));
					int xVar=150-xBlock;
					double koficient=1;
					if(xVar!=0){ koficient = (Math.sqrt((100*100)-(xVar*xVar)))/xVar;}
					System.err.println(koficient + " : "+xBoll + " : " + yBoll + " : "+ xVar);
					yBoll=(int)(xVar*koficient);
					
					if(koficient<0){
						xBoll=150;
					}
					else if(koficient>0){
						xBoll=350;
					}
				
					
					System.err.println(koficient + " : "+xBoll + " : " + yBoll);
					
				}
				else{
					System.err.println("MISS");

				}
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
			if(xBlock+(3*i)>=0&&(xBlock+(i)+200)<482){
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
