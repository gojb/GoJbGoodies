package GoJbFrame;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

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
private static final long serialVersionUID = 1L;
	
	
	public GoJbFrame() {
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(3);
		setVisible(true);
		
		try {
			setIconImage(new ImageIcon(getClass().getResource("/images/Java-icon.png")).getImage());
		} catch (Exception e) {
			System.err.println("Ikon saknas");
		}
	}
	public GoJbFrame(String title){
		this();
		setTitle(title);
		
	}
}
