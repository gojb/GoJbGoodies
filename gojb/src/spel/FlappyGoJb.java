package spel;

import static gojb.GoJbGoodies.Bild;
import static gojb.GoJbGoodies.fönsterIcon;
import static gojb.GoJbGoodies.random;
import static gojb.GoJbGoodies.typsnitt;
import static java.awt.Color.green;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class FlappyGoJb extends JPanel implements KeyListener,WindowListener{
	private static final long serialVersionUID = 1L;

	private JFrame frame = new JFrame("FlappyGoJb");
	private int x,y,a,poäng=-1;
	private final int bredd=35;
	private Timer timer = new Timer(20, e -> check());
	private boolean mellanslag;

	public FlappyGoJb(){

		setBackground(new Color(255, 255, 255));
		frame.addKeyListener(this);
		frame.setIconImage(fönsterIcon);
		frame.add(this);
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addWindowListener(this);
		timer.start();
		skapaHinder();
	}
	private void gameover(){
		timer.stop();
		y=getHeight()/2;
		mellanslag=false;
		if (showConfirmDialog(null, "Game over! Vill du spela igen?","Du förlorade!",YES_NO_OPTION,ERROR_MESSAGE)!=YES_OPTION) {
			frame.dispose();
			return;
		}
		poäng=-1;
		skapaHinder();
	}
	private void check() {
		int i =20;
		if (y+64<getHeight()) {
			y=y+5;
		}
		else 
			gameover();
		if (x+bredd<=0) {
			skapaHinder();
		}
		if (y<a+getHeight()&&i+64>x&&i<x+bredd||y+64>a+164+getHeight()&&i+64>x&&i<x+bredd) {
			gameover();
		}
		frame.repaint();
		if (mellanslag) {
			if (y>0) {
				y=y-15;
			}
			else {
				gameover();
			}
		}

		x=x-8;
		frame.repaint();
	}
	private void skapaHinder(){

		timer.start();
		a=random.nextInt(getHeight());
		if (a<getHeight()*0.1 || a+164>getHeight()*0.9) {
			skapaHinder();
			return;
		}
		poäng++;
		a=a-getHeight();
		x=getWidth();
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		g2.drawImage(Bild("/images/logga100.png").getImage(), 20,y,null);
		g2.fillRect(x, a, bredd,getHeight());
		g2.drawRect(x, a, bredd,getHeight());
		g2.fillRect(x, a+164+getHeight(), bredd,getHeight());
		g2.drawRect(x, a+164+getHeight(), bredd,getHeight());
		g2.setFont(typsnitt);
		g2.setColor(green);
		g2.drawString(Integer.toString(poäng), getWidth()/2, 50);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_SPACE) {
			mellanslag=true;
		}
	}
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_SPACE) {
			mellanslag=false;
		}
	}
	public void windowClosed(WindowEvent e) {
		timer.stop();
	}
	public void keyTyped(KeyEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {}
}

