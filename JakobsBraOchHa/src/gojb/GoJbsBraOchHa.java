
package gojb;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.Timer;
import GoJbFrame.GoJbFrame;

import com.sun.mail.util.*;

import static gojb.GoJbsBraOchHa.*;
import static gojb.Jakobs.*;
import static javax.swing.JFrame.*;
import static javax.swing.JOptionPane.*;
import static java.awt.Toolkit.*;

/**
 * Det här programmet innehåller lite
 * grejer som kan vara "BraOchHa" och även
 * andra små program vi gjort för att testa
 * olika saker.
 * 
 * @author GoJb - Glenn Olsson & Jakob Björns
 * 
 * @see <a href="http://gojb.bl.ee/">http://gojb.bl.ee/</a>
 * @version 1.0
 */
public class GoJbsBraOchHa{

	public static String	argString;
	public static Font 		typsnitt = new Font("Arial", 0, 40);
	public static Robot		robot;
	public static Random 	random = new Random();
	public static Properties prop = new Properties();
	public static final Image 	fönsterIcon = Bild("/images/Java-icon.png").getImage();
	public static final Dimension SKÄRM_SIZE = new Dimension(getDefaultToolkit().getScreenSize());
	public static void main(String... arg) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
			showMessageDialog(null, "Den angivna LookAndFeel hittades inte!","Error",ERROR_MESSAGE);
		}
		try {
			robot = new Robot();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			prop.load(new FileInputStream(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\GoJbsBraOchHa\\data.gojb"));
		} catch (Exception e) {
			System.err.println("Properties saknas");
		}
		new Thread(new Update()).start();
		try {
			argString = arg[0];
			if (argString.equals("snabb")) {
				GoJbFrame frame = new GoJbFrame("Snabbstart",false,3);
				JComboBox<Class<?>> box = new JComboBox<Class<?>>();
				JButton button = new JButton("kör");
				frame.setLayout(new GridLayout(0, 1));
				frame.add(box);
				frame.add(button);
				frame.pack();
				frame.setSize(500, frame.getHeight());
				button.addActionListener(e -> {
					frame.dispose();
					try {
						((Class<?>) box.getSelectedItem()).newInstance();
					} catch (Exception e1) {
						e1.printStackTrace();

					}});
				try {
					for (Class<?> class1 : ClassFinder.find("gojb")) {
						if (!class1.getName().contains("$")) {
							box.addItem(class1);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					box.setSelectedIndex(Integer.parseInt(prop.getProperty("temp")));
				} catch (Exception e2) {}
				box.addItemListener(e -> {
					box.addItemListener(e1 -> {
						prop.setProperty("temp", Integer.toString(box.getSelectedIndex()));
						sparaProp("123");
						frame.dispose();
						try {
							((Class<?>) e1.getItem()).newInstance();
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					});
				});
				frame.setVisible(true);
				return;
			}
			Class<?> cls = Class.forName(argString);
			try {
				cls.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return;
		} catch (Exception e) {
			argString ="";
		}
		new Login();
	}
	public static void spelaLjud(String filnamn){
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(GoJbsBraOchHa.class.getResource(filnamn)));
			clip.start();

		} catch (Exception e) {
			((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
			showMessageDialog(null, "Filen: \"" + filnamn + "\" hittades inte", "Ljud", ERROR_MESSAGE);
		}
	}
	public static void sparaProp(String kommentar) {
		try {
			prop.store(new FileWriter(new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\GoJbsBraOchHa\\data.gojb")),kommentar);
		} catch (Exception e) {
			System.err.println("ga");
			try {
				new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\").mkdir();
				new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\GoJbsBraOchHa\\").mkdir();
				prop.store(new FileWriter(new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\GoJbsBraOchHa\\data.gojb")), kommentar);
			} catch (Exception e2) {
				e2.printStackTrace();
				System.err.println("Problem med åtkomst till disk!");
			}
		}  
	}
	public static ImageIcon Bild(String filnamn){
		ImageIcon icon = null;
		try {
			icon = new ImageIcon(GoJbsBraOchHa.class.getResource(filnamn));
		} catch (Exception e) {
			((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
			showMessageDialog(null, "ImageIcon \""+filnamn+"\" hittades inte","Filfel",ERROR_MESSAGE);
		}
		return icon;
	}
	public static void vänta(int millisekunder){
		try {
			Thread.sleep(millisekunder);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	public static void errPrint(Exception e){
		Writer writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		e.printStackTrace();
		skrivHändelsetext(writer.toString());
	}
	public GoJbsBraOchHa() {
		main("");
	}
	public static WindowListener autoListener = new WindowListener() {
		public void windowOpened(WindowEvent e) {}public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}public void windowDeactivated(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {}public void windowActivated(WindowEvent e) {}
		public void windowClosed(WindowEvent e) {
			vänta(2000);
			boolean b = false;
			for (Frame frame : JFrame.getFrames()) {
				if (frame.isVisible()) {
					b = true;
				}
			}
			if (!b) {
				System.exit(0);
			}
		}
	};
}
class Login implements ActionListener{
	private int x;
	private Timer timer = new Timer(30, this);	
	private JPasswordField passwordField = new JPasswordField(10);
	private JFrame användare= new JFrame(),frame = new JFrame("Verifiera dig!");
	private JButton användareJakob = new JButton("Jakob"), användareGlenn= new JButton("Glenn");
	private char[] correctPassword = {'U','g','g','e','n','0','6','8','4'};
	private Cipher cipher;
	private String key = String.valueOf(correctPassword);
	private boolean b = true;
	Login() {
		try {
			while (true) {
				cipher = Cipher.getInstance("AES");
				try {
					cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"));
					break;
				} catch (Exception e) {
					key += "\0";
				}
			}
			if (System.currentTimeMillis()<=Long.parseLong(new String(cipher.doFinal(BASE64DecoderStream.decode(prop.getProperty("pass").getBytes())),"ISO-8859-1"))+3600000) {
				b = false;
				System.out.println("Inloggad för mindre än en timme sedan");
				passwordField.setText(String.valueOf(correctPassword));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Logga in!");
		}
		passwordField.addActionListener(this);

		användare.add(användareJakob);
		användare.add(användareGlenn);
		användare.setIconImage(fönsterIcon);
		användare.setLayout(new FlowLayout());
		användare.setDefaultCloseOperation(3);
		användare.setResizable(false);
		användare.pack();
		användare.setLocationRelativeTo(null);
		användareGlenn.addActionListener(e -> {
			new RörandeMojäng();
			användare.dispose();
		});
		användareJakob.addActionListener(e -> {
			new Jakobs();
			användare.dispose();
		});

		frame.setUndecorated(true);
		frame.setLayout(new GridLayout(0, 2));
		frame.add(new JLabel("Skriv Lösenord -->"));
		frame.add(passwordField);
		frame.setIconImage(fönsterIcon);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);	
		frame.setVisible(true);
		frame.toFront();
		timer.start();
	}
	public static void logout() {
		prop.setProperty("pass", "0000000000000000");
		sparaProp("loguot");
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (timer == e.getSource()) {
			if (Arrays.equals(passwordField.getPassword(),correctPassword)) {
				frame.dispose();
				timer.stop();
				användare.setVisible(true);
				användare.toFront();
				if (b) {
					spelaLjud("/images/Inloggad.wav");
				}
				try {
					cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(), "AES"));								
					prop.setProperty("pass",  new String(BASE64EncoderStream.encode(cipher.doFinal(Long.toString(System.currentTimeMillis()).getBytes("ISO-8859-1")))));
					sparaProp("login");
					return;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			try {
				if(Arrays.equals(getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString().toCharArray(),correctPassword)){
					passwordField.setText(String.valueOf(correctPassword));
					System.out.println("Lösenord från urklipp");
				}
			} catch (Exception e1){}
			if(x++ == 600){
				timer.stop();
				new Impossible("Tiden gick ut!! Datorn spärrad...");
			}
		}
	}
}
class Update implements Runnable{
	public synchronized void run(){
		vänta(10000);
		if (getClass().getResource("/" + getClass().getName().replace('.','/') + ".class").toString().startsWith("jar:")) {
			try {
				URLConnection connection = new URL("http://gojb.tk/jar/GoJb.jar").openConnection();
				File file = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
				System.out.println("Online: " + connection.getLastModified());
				System.out.println("File: " + file);
				System.out.println("Lokal:  "+ file.lastModified());
				if (file.lastModified() + 60000 < connection.getLastModified()) {
					spelaLjud("/images/tada.wav");
					if (showConfirmDialog(null, "En nyare version av programmet är tillgängligt.\nVill du uppdatera nu?","Uppdatering",YES_NO_OPTION,WARNING_MESSAGE)==YES_OPTION) {
						JProgressBar bar = new JProgressBar(0, connection.getContentLength());
						JFrame frame = new JFrame("Downloading update...");
						frame.add(bar);
						frame.setIconImage(new ImageIcon(getClass().getResource("/images/Java-icon.png")).getImage());
						frame.setLocationRelativeTo(null);
						frame.setSize(500,100);
						frame.setVisible(true);
						InputStream in = connection.getInputStream();
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						int n = 0;
						byte[] buf = new byte[1024];
						while (-1!=(n=in.read(buf))){
							out.write(buf, 0, n);
							bar.setValue(bar.getValue()+n);
						}
						frame.setTitle("Saving...");
						bar.setIndeterminate(true);
						FileOutputStream fos = new FileOutputStream(file);
						fos.write(out.toByteArray());
						fos.close();
						out.close();
						in.close();
						System.out.println("Klart!");
						frame.dispose();
						showMessageDialog(null, "Uppdateringen slutfördes! Programmet startas om...", "Slutfört", INFORMATION_MESSAGE);
						try {
							String string = "java -jar \"" + file.toString()+"\"";
							Runtime.getRuntime().exec(string);
							System.err.println(string);
						} catch (Exception e) {
							e.printStackTrace(); 
						}
						System.exit(0);
					}
				}
			} catch(Exception e){
				System.err.println("Ingen uppdatering hittades");
			}
		}
	}
}
