package gojb;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import GoJbFrame.GoJbFrame;

public class ThreadTest {

	public ThreadTest() {
		// TODO Auto-generated constructor stub
		GoJbFrame frame = new GoJbFrame();
		JButton button = new JButton("ny tråd");
		frame.add(button);
		button.addActionListener(e->{
			new Thread(){
				public void run() {
					JOptionPane.showMessageDialog(new GoJbFrame(), "Hejsan!");
				}
			}
			.start();
		});
	}
	public static void main(String[] args) {
		new ThreadTest();
	}

}
