/*
 * Copyright 2017 GoJb Development
 *
 * Permission is hereby granted, free of charge, to any
 * person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to
 *  use, copy, modify, merge, publish, distribute, sublicense, and/or
 *  sell copies of the Software, and to permit persons to whom
 *  the Software is furnished to do so, subject to the following
 *  conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF
 * ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
 * SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package spel;

import static gojb.GoJbGoodies.fönsterIcon;
import static gojb.GoJbGoodies.random;
import static java.awt.Color.black;
import static java.awt.Color.green;
import static java.awt.Color.red;
import static java.awt.Toolkit.getDefaultToolkit;
import static javax.swing.JOptionPane.showInputDialog;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public
class Pongspel extends JPanel implements ActionListener,KeyListener,WindowListener,MouseMotionListener{

	private int x,y,VänsterX=0,VänsterY,HögerX,HögerY,RektBredd=10,RektHöjd=100,
			bredd=20,höjd=30,hastighet,c, d,PoängVänster=0,PoängHöger=0,py=10,px=10;
	private JFrame frame = new JFrame("Spel");
	private Timer timer = new Timer(10, this);
	private Boolean GameOver=false,hupp=false,hner=false,vupp=false,vner=false;
	private String PoängTill,SpelareVänster,SpelareHöger;

	public Pongspel() {
		SpelareVänster = showInputDialog("Spelare till vänster:");
		if (SpelareVänster == null) {
			return;
		}
		else if (SpelareVänster.equals("")) {
			SpelareVänster = "Spelare 1";
		}
		SpelareHöger = showInputDialog("Spelare till höger:");
		if (SpelareHöger == null) {
			return;
		}
		else if (SpelareHöger.equals("")) {
			SpelareHöger = "Spelare 2";
		}


		addMouseMotionListener(this);
		setForeground(red);
		setPreferredSize(new Dimension(700, 500));
		setOpaque(true);	
		setBackground(black.brighter());

		frame.addMouseMotionListener(this);
		frame.setIconImage(fönsterIcon);
		frame.addWindowListener(this);
		frame.addKeyListener(this);
		frame.add(this);
		frame.pack();
		frame.repaint();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		HögerY = getHeight()/2;
		VänsterY = getHeight()/2;
		HögerX=getWidth()-bredd-1;
		StartaOm();

	}
	private void StartaOm(){
		x = getWidth()/2;
		y = random.nextInt(getHeight());
		hastighet = 2;
		c = hastighet;
		d = hastighet;
		timer.start();

	}
	private void GameOver(String PoängTillVänsterEllerHöger) {
		timer.stop();
		System.out.println("Game over!");
		getDefaultToolkit().beep();

		if (PoängTillVänsterEllerHöger=="Vänster"){
			PoängVänster++;
			PoängTill = SpelareVänster;
		}
		else if (PoängTillVänsterEllerHöger=="Höger") {
			PoängHöger++;
			PoängTill = SpelareHöger;
		}

		GameOver=true;

		frame.repaint();
	}
	public void keyPressed(KeyEvent e) {
		if(KeyEvent.getKeyText(e.getKeyCode()) == "Upp"){
			hupp = true;
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()) == "Nedpil"){
			hner = true;
		}
		else if (e.getKeyCode() == 87 ) {
			vupp = true;
		}
		else if (e.getKeyCode() == 83) {
			vner = true;
		}
		else if (KeyEvent.getKeyText(e.getKeyCode()) == "Mellanslag") {
			GameOver = false;
			frame.repaint();
			StartaOm();
		}
		else if (KeyEvent.getKeyText(e.getKeyCode()) == "Esc") {
			frame.dispose();
		}

	}

	public void keyReleased(KeyEvent e) {
		if(KeyEvent.getKeyText(e.getKeyCode()) == "Upp"){
			hupp = false;
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()) == "Nedpil"){
			hner = false;
		}
		else if (e.getKeyCode() == 87 ) {
			vupp = false;
		}
		else if (e.getKeyCode() == 83) {
			vner = false;
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==timer){
			if (hupp && HögerY>0) {
				HögerY=HögerY-5;
			}
			if (hner && HögerY+RektHöjd<getHeight()){
				HögerY=HögerY+5;
			}
			if (vupp && VänsterY>0){
				VänsterY=VänsterY-5;
			}
			if (vner && VänsterY+RektHöjd<getHeight()){
				VänsterY=VänsterY+5;
			}
			frame.repaint();
			if (x+bredd>=HögerX) {
				if (y>=HögerY&&y<=HögerY+RektHöjd) {
					c= -hastighet;

				}
				else{
					GameOver("Vänster");
				}
			}
			else if (x<=VänsterX+RektBredd) {
				if (y>=VänsterY&&y<=VänsterY+RektHöjd) {
					hastighet++;
					c=hastighet;

				}
				else{
					GameOver("Höger");
				}

			}
			else if (y+höjd>=getHeight()) {
				d=-hastighet;
			}
			else if(y<=0){
				d=hastighet;
			}
			x=x+c;
			y=y+d;
			frame.repaint();
			HögerX=getWidth()-RektBredd-1;

		}
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (GameOver) {
			g2.setFont(new Font("", Font.BOLD, 50));
			g2.setColor(green);
			g2.drawString("Poäng till " + PoängTill, getWidth()/2-200, getHeight()/2);
			g2.drawString(Integer.toString(PoängVänster) + " - " + Integer.toString(PoängHöger), getWidth()/2-70, 40);
			g2.drawString(Integer.toString(PoängVänster) + " - " + Integer.toString(PoängHöger), px,py);
		}
		else {

			g2.drawOval(x, y, bredd, höjd);
			g2.fillOval(x, y, bredd, höjd);

			g2.drawRect(VänsterX, VänsterY, RektBredd, RektHöjd);
			g2.fillRect(VänsterX, VänsterY, RektBredd, RektHöjd);

			g2.drawRect(HögerX, HögerY, RektBredd, RektHöjd);
			g2.fillRect(HögerX, HögerY, RektBredd, RektHöjd);

			g2.setColor(green);
			g2.setFont(new Font("", Font.BOLD, 50));
			g2.drawString(Integer.toString(PoängVänster) + " - " + Integer.toString(PoängHöger), getWidth()/2-80, 40);

			g2.drawString(SpelareVänster, 0, 40);
			g2.drawString(SpelareHöger, getWidth()-250, 40);

		}
	}
	public void mouseDragged(MouseEvent e) {
		px=e.getX();
		py=e.getY();
		repaint();
		frame.repaint();

	}
	public void windowClosing(WindowEvent e) {
		timer.stop();
	}
	public void keyTyped(KeyEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void mouseMoved(MouseEvent e) {}
}
