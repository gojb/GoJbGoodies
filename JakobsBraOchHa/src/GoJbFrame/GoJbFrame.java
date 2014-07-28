package GoJbFrame;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


@SuppressWarnings("serial")
public class GoJbFrame extends JFrame{
	public GoJbFrame() {
		set();
	}
	public GoJbFrame(String title){
		set();
		setTitle(title);
	}
	private void set() {
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(3);
		setVisible(true);
		setIconImage(new ImageIcon(getClass().getResource("/images/Java-icon.png")).getImage());
	}

}
