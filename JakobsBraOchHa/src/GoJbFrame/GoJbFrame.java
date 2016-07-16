package GoJbFrame;
import static gojb.GoJbGoodies.autoListener;

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

public class GoJbFrame extends JFrame{

	private static final long serialVersionUID = 168746L;


	public GoJbFrame() {
		this("", true,3);
	}
	public GoJbFrame(String title){
		
		this(title,true,3);
	}
	public GoJbFrame(Boolean visible){
		
		this("",visible,3);
	}
	public GoJbFrame(String title,Boolean visible, int close){
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(close);
		setVisible(visible);
		setTitle(title);
		System.out.println(222);
		if(close==3){
		addWindowListener(autoListener);
		}
		try {
			setIconImage(new ImageIcon(getClass().getResource("/images/Java-icon.png")).getImage());
		} catch (Exception e) {
			System.err.println("Ikon saknas");
		}
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
