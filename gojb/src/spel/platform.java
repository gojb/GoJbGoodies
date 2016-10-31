package spel;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.JLabel;
import javax.swing.Timer;

import GoJbFrame.GoJbFrame;

public class platform implements KeyListener {

	GoJbFrame frame = new GoJbFrame("Spel", true, 3);

	JLabel label = new JLabel(), ground = new JLabel("");

	Timer timer, timerHoppa, timerFaller;

	boolean flyttar, hoppar, hopparUpp, faller;

	public static void main(String[] args) {
		// FIXME Auto-generated method stub
		new platform();
	}
	public platform() {
		// FIXME Auto-generated constructor stub

		frame.add(label);
		frame.add(ground);
		frame.setLayout(null);
		frame.setResizable(false);
		frame.addKeyListener(this);

		label.setOpaque(true);
		label.setBackground(Color.blue);
		label.setSize(50, 50);
		label.setLocation(225, 225);

		ground.setOpaque(true);
		ground.setBackground(Color.green);
		ground.setSize(1000, 300);
		ground.setLocation(-20, 275);



	}

	public void flyttaMark(Integer i) {
		//i är antingen -1 (högerpil), eller 1 (vänsterpil)
		timer = new Timer(10, e->{
			ground.setLocation((int)ground.getLocation().getX()+(3*i), 275);
			if(ground.getX()>(label.getX()+(label.getWidth()/2-5))){
				faller();
			}
			else if(ground.getX()+ground.getWidth()<(label.getX()+(label.getWidth()/2+5))){
				faller();
			}
		});
		timer.start();
	}
	private void hoppa(){
		timerHoppa = new Timer(10, e->{
			if(hopparUpp){
				label.setLocation(225,(int)label.getLocation().getY()-(2));
				if((int)label.getLocation().getY()==125){
					hopparUpp=false;
				}
			}
			else{
				label.setLocation(225,(int)label.getLocation().getY()+(2));
				if((int)label.getLocation().getY()==225){
					timerHoppa.stop();
					hoppar=false;
				}
			}
		});
		timerHoppa.start();
	}
	public void faller(){
		if(!faller){
			timerFaller = new Timer(10, e->{	
				label.setLocation(225,(int)label.getLocation().getY()+(2));
			});
			timerFaller.start();
			flyttar=true;
			faller=true;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// FIXME Auto-generated method stub

	}
	@Override
	public void keyPressed(KeyEvent e) {
		// FIXME Auto-generated method stub

		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			if(!flyttar&&!faller){		
				flyttaMark(1);
				flyttar=true;
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			if(!flyttar&&!faller){
				flyttaMark(-1);
				flyttar=true;
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_UP){
			if(!hoppar){
				hopparUpp=true;
				hoppa();
				hoppar=true;
			}
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		// FIXME Auto-generated method stub
		if(e.getKeyCode() == KeyEvent.VK_LEFT||e.getKeyCode() == KeyEvent.VK_RIGHT){
			timer.stop();
			flyttar=false;
		}
	}


}
