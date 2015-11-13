package applet;

import gojb.GoJbGoodies;

import java.awt.Color;

import javax.swing.JApplet;

@SuppressWarnings("serial")
public class GoJbApplet extends JApplet{
	public static void main(String[] args) {
		new GoJbApplet();
	}
	public GoJbApplet() {
		getContentPane().setBackground(Color.WHITE);
		new GoJbGoodies();
	}
}
