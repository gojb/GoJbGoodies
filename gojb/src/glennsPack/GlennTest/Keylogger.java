package glennsPack.GlennTest;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextArea;

import GoJbFrame.GoJbFrame;

public class Keylogger implements KeyListener{

	 GoJbFrame frame = new GoJbFrame("");
	 JTextArea area = new JTextArea();
	 
	public static void main(String[] args) {
		new Keylogger();
	}
	public Keylogger() {

		 frame.add(area);
		 area.addKeyListener(this);
		 area.requestFocus();
		 frame.revalidate();
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// FIXME Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// FIXME Auto-generated method stub
		System.out.println(e.getModifiersEx());
		frame.setTitle(e.getKeyText(e.getKeyCode()));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// FIXME Auto-generated method stub
		
	}

}
