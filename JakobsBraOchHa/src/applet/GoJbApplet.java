package applet;

import java.awt.Color;

import javax.swing.JApplet;
import GoJbsBraOchHa.Mouse;

@SuppressWarnings("serial")
public class GoJbApplet extends JApplet{
	public static void main(String[] args) {
		new GoJbApplet();
	}
	public GoJbApplet() {
		getContentPane().setBackground(Color.WHITE);
		Mouse.main(null);
	}
}
