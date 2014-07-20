import java.awt.*;
import java.awt.event.*;
import javax.sound.sampled.*;
import javax.swing.*;


public class Morse implements KeyListener,ActionListener, MouseListener {
	
	JFrame frame = new JFrame();
	String Filnamn = "/images/iMorse.wav";
	JLabel button = new JLabel("Pip");
	Clip clip;
	Timer timer = new Timer(300, this);
	int x,y;
	public static void main(String[] args) {
		new Morse();
	}
	public Morse(){
		
		button.setFont(GoJbsBraOchHa.Mouse.Typsnitt);
		button.addMouseListener(this);
		button.addKeyListener(this);
		button.setBackground(Color.black);
		button.setForeground(Color.white);
		button.setOpaque(true);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		frame.addKeyListener(this);
		frame.add(button);
		frame.setDefaultCloseOperation(3);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		

		try {

			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(getClass().getResource(Filnamn)));
			
		} catch (Exception e) {
			((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
			JOptionPane.showMessageDialog(null, "Filen: \"" + Filnamn + "\" hittades inte", "Ljud", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		
		timer.start();

		clip.loop(9*999);
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
			
		
		if (x == 0){
			System.err.println(".");
		}
		
		x = 0;
		
		timer.stop();
		GoJbsBraOchHa.Mouse.Vänta(100);

		clip.close();
		try {

			
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(getClass().getResource(Filnamn)));
			
		} catch (Exception e) {
			((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
			JOptionPane.showMessageDialog(null, "Filen: \"" + Filnamn + "\" hittades inte", "Ljud", JOptionPane.ERROR_MESSAGE);
		}
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	
		if (x == 0){
			timer.stop();
			x = 1;
			System.err.println("-");
		}
	
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	@Override
	public void mousePressed(MouseEvent arg0) {

	
		timer.start();
		
		clip.loop(9999);
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {

		if (x == 0){
			System.err.println(".");
		}
			x = 0;
			
			timer.stop();
			GoJbsBraOchHa.Mouse.Vänta(100);

			clip.close();
			try {

				
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(getClass().getResource(Filnamn)));
				
			} catch (Exception e) {
				((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
				JOptionPane.showMessageDialog(null, "Filen: \"" + Filnamn + "\" hittades inte", "Ljud", JOptionPane.ERROR_MESSAGE);
			}
	}
}
