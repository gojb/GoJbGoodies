package gojb;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

class Temp {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}
		new Mailkorg();
//		new Snake();
//		new Ping("192.168.2.131");
//		new Merit();
	}
}
