package spel;

import static java.awt.Color.black;
import static java.awt.Color.blue;
import static java.awt.Color.cyan;
import static java.awt.Color.green;
import static java.awt.Color.magenta;
import static java.awt.Color.orange;
import static java.awt.Color.red;
import static java.awt.Color.white;
import static java.awt.Color.yellow;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;

import GoJbFrame.GoJbFrame;

public class Memory implements MouseInputListener{

	GoJbFrame frame = new GoJbFrame();

	JLabel label1 = new JLabel(),
			label2 = new JLabel(),
			label3 = new JLabel(),
			label4 = new JLabel(),
			label5 = new JLabel(),
			label6 = new JLabel(),
			label7 = new JLabel(),
			label8 = new JLabel(),
			label9 = new JLabel();


	public Memory(){

		frame.addMouseListener(this);

		frame.setLayout(new GridLayout(3,3));	

		label1.addMouseListener(this);
		label2.addMouseListener(this);
		label3.addMouseListener(this);
		label4.addMouseListener(this);
		label5.addMouseListener(this);
		label6.addMouseListener(this);
		label7.addMouseListener(this);
		label8.addMouseListener(this);
		label9.addMouseListener(this);

		frame.add(label1);
		frame.add(label2);
		frame.add(label3);
		frame.add(label4);
		frame.add(label5);
		frame.add(label6);
		frame.add(label7);
		frame.add(label8);
		frame.add(label9);

		label1.setBackground(blue);
		label1.setOpaque(true);

		label2.setBackground(red);
		label2.setOpaque(true);

		label3.setBackground(green);
		label3.setOpaque(true);

		label4.setBackground(cyan);
		label4.setOpaque(true);

		label5.setBackground(black);
		label5.setOpaque(true);

		label6.setBackground(white);
		label6.setOpaque(true);

		label7.setBackground(orange);
		label7.setOpaque(true);

		label8.setBackground(magenta);
		label8.setOpaque(true);

		label9.setBackground(yellow);
		label9.setOpaque(true);


	}


	@Override
	public void mouseClicked(MouseEvent e) {

		if(e.getSource()==label1){
			System.err.println("1");
		}
		if(e.getSource()==label2){
			System.err.println("2");
		}
		if(e.getSource()==label3){
			System.err.println("3");
		}
		if(e.getSource()==label4){
			System.err.println("4");
		}
		if(e.getSource()==label5){
			System.err.println("5");
		}
		if(e.getSource()==label6){
			System.err.println("6");
		}
		if(e.getSource()==label7){
			System.err.println("7");
		}
		if(e.getSource()==label8){
			System.err.println("8");
		}
		if(e.getSource()==label9){
			System.err.println("9");
		}

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

