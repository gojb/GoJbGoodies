package GoJbFrame;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


@SuppressWarnings("serial")
public class GoJbFrame extends JFrame{

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
