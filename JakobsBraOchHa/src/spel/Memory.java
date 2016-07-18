package spel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;

import GoJbFrame.GoJbFrame;

public class Memory implements MouseInputListener{

	GoJbFrame frame = new GoJbFrame();
	Random random = new Random();
	JLabel[] label;
	public static void main(String[] args) {
		new Memory();
	}

	public Memory(){

		label = new JLabel[64];
		frame.setLayout(new GridLayout((int)(Math.sqrt(label.length)),(int)(Math.sqrt(label.length)),2,2));	
		for(int i = 0; i<label.length;i++){
			label[i]=new JLabel();
			label[i].addMouseListener(this);
			frame.add(label[i]);
			label[i].setBackground(Color.green);
			label[i].setOpaque(true);

			frame.revalidate();
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {

				System.out.println(e.getSource().getClass().getDeclaredClasses());

	}


	@Override
	public void mousePressed(MouseEvent e) {
		// FIXME Auto-generated method stub

	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// FIXME Auto-generated method stub

	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// FIXME Auto-generated method stub

	}


	@Override
	public void mouseExited(MouseEvent e) {
		// FIXME Auto-generated method stub

	}


	@Override
	public void mouseDragged(MouseEvent e) {
		// FIXME Auto-generated method stub

	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// FIXME Auto-generated method stub

	}

}

