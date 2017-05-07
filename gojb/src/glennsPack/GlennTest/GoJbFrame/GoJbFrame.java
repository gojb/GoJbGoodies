package glennsPack.GlennTest.GoJbFrame;
import java.awt.event.*;

import javax.swing.*;

/**
 * GoJbFrame är en vanlig JFrame fast med några inställningar förkonfigurerade 
 * bl.a. 
 * {@code
 * setSize,
 * setLocationRelativeTo,
 * setDefaultCloseOperation,
 * setVisible,
 * setIconImage.
 * }
 * @author GoJb - Glenn Olsson & Jakob Björns
 * 
 * @see <a href="http://gojb.bl.ee/">http://gojb.bl.ee/</a>
 * @version 1.0
 */

public class GoJbFrame extends JFrame implements WindowListener{

	static Timer timer = new Timer(1000*60*2, e -> { /*System.exit(3)*/});

	private static final long serialVersionUID = 168746L;


	public GoJbFrame() {
		this("", true,3);
	}
	public GoJbFrame(String title){
		
		this(title,true,3);
	}
	public GoJbFrame(String title,Boolean visible, int close){
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(close);
		setVisible(visible);
		setTitle(title);
		addWindowListener(this);
		try {
			setIconImage(new ImageIcon(getClass().getResource("/images/Java-icon.png")).getImage());
		} catch (Exception e) {
			System.err.println("Ikon saknas");
		}
	}
	@Override
	public void windowActivated(WindowEvent e) {
		timer.stop();

	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void windowDeactivated(WindowEvent e) {

		timer.start();

	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		timer.stop();
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		timer.start();

	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		timer.stop();
	}
}
class Standard{
	public Standard(JFrame frame) {
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);

		try {
			frame.setIconImage(new ImageIcon(getClass().getResource("/images/Java-icon.png")).getImage());
		} catch (Exception e) {
			System.err.println("Ikon saknas");
		}
	}
}
