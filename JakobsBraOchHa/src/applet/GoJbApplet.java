package applet;

import javax.swing.JApplet;

import GoJbsBraOchHa.Mouse;

@SuppressWarnings("serial")
public class GoJbApplet extends JApplet{
	public static void main(String[] args) {
		new GoJbApplet();
	}
	public GoJbApplet() {
		Mouse.main(null);
	}
}
