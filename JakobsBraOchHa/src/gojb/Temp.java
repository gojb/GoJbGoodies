package gojb;

import javax.swing.UIManager;

class Temp {
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e){}
//		new Mailkorg();
//		new Snake();
//		new Ping("192.168.2.131");
//		new Merit();
		new Kurve();
	}
}
