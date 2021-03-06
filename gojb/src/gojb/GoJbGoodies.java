
/*
 * Copyright 2017 GoJb Development
 *
 * Permission is hereby granted, free of charge, to any
 * person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to
 *  use, copy, modify, merge, publish, distribute, sublicense, and/or
 *  sell copies of the Software, and to permit persons to whom
 *  the Software is furnished to do so, subject to the following
 *  conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF
 * ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
 * SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

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
import GoJbFrame.GoJbFrame;

import com.sun.mail.util.*;

import static gojb.GoJbGoodies.*;
import static gojb.Jakobs.*;
import static javax.swing.JFrame.*;
import static javax.swing.JOptionPane.*;
import static java.awt.Toolkit.*;

/**
 * Det h�r programmet inneh�ller lite
 * grejer som kan vara bra och �ven
 * andra sm� program vi gjort f�r att testa
 * olika saker.
 * 
 * @author GoJb - Glenn Olsson & Jakob Bj�rns
 * 
 * @see <a href="http://gojb.ml/">http://gojb.ml/</a>
 * @version 1.0
 */
public class GoJbGoodies{

	public static String	argString;
	public static Font 		typsnitt = new Font("Arial", 0, 40);
	public static Robot		robot;
	public static Random 	random = new Random();
	public static Properties prop = new Properties(){

		private static final long serialVersionUID = -8960247664212445472L;

		public synchronized Object setProperty(String key, String value) {
			if (prop.isEmpty()) {
				laddaProp();
			}
			return super.setProperty(key, value);
		};
		public String getProperty(String key, String defaultValue) {
			if (prop.isEmpty()) {
				laddaProp();
			}
			return super.getProperty(key,defaultValue);
		};
		public String getProperty(String key) {
			if (prop.isEmpty()) {
				laddaProp();
			}
			return super.getProperty(key);
		};
	};
	public static final Image 	f�nsterIcon = Bild("/images/Java-icon.png").getImage();
	public static final Dimension SK�RM_SIZE = new Dimension(getDefaultToolkit().getScreenSize());
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
		laddaProp();
		new Thread(new Update()).start();
		try {
			argString = arg[0];
			if (argString.equals("snabb")) {
				GoJbFrame frame = new GoJbFrame("Snabbstart",false,3);
				JComboBox<Class<?>> box = new JComboBox<Class<?>>();
				JButton button = new JButton("k�r");
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

		//		new Login();

		JFrame anv�ndare= new JFrame();
		JButton anv�ndareJakob = new JButton("Jakob"), anv�ndareGlenn= new JButton("Glenn");
		anv�ndare.add(anv�ndareJakob);
		anv�ndare.add(anv�ndareGlenn);
		anv�ndare.setIconImage(f�nsterIcon);
		anv�ndare.setLayout(new FlowLayout());
		anv�ndare.setDefaultCloseOperation(3);
		anv�ndare.setResizable(false);
		anv�ndare.pack();
		anv�ndare.setLocationRelativeTo(null);
		anv�ndare.setVisible(true);
		anv�ndare.toFront();
		anv�ndareGlenn.addActionListener(e -> {
			new R�randeMoj�ng();
			anv�ndare.dispose();
		});
		anv�ndareJakob.addActionListener(e -> {
			new Jakobs();
			anv�ndare.dispose();
		});
	}
	private static void laddaProp() {
		try {
			prop.load(new FileInputStream(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\GoJbsBraOchHa\\data.gojb"));
		} catch (Exception e) {
			System.err.println("Tidigare properties saknas");
		}
	}
	public static void spelaLjud(String filnamn){
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(GoJbGoodies.class.getResource(filnamn)));
			clip.start();

		} catch (Exception e) {
			((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
			showMessageDialog(null, "Filen: \"" + filnamn + "\" hittades inte", "Ljud", ERROR_MESSAGE);
		}
	}
	public static void sparaProp(String kommentar) {
		sparaEgenProp(prop, "data",kommentar);
	}
	public static void sparaEgenProp(Properties prop,String filnamn,String kommentar) {
		try {
			prop.store(new FileWriter(new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\GoJbsBraOchHa\\"+filnamn+".gojb")),kommentar);
		} catch (Exception e) {
			System.err.println("ga");
			try {
				new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\").mkdir();
				new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\GoJbsBraOchHa\\").mkdir();
				prop.store(new FileWriter(new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\GoJbsBraOchHa\\"+filnamn+".gojb")), kommentar);
			} catch (Exception e2) {
				e2.printStackTrace();
				System.err.println("Problem med �tkomst till disk!");
			}
		}  
	}
	public static ImageIcon Bild(String filnamn){
		ImageIcon icon = null;
		try {
			icon = new ImageIcon(GoJbGoodies.class.getResource(filnamn));
		} catch (Exception e) {
			((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
			showMessageDialog(null, "ImageIcon \""+filnamn+"\" hittades inte","Filfel",ERROR_MESSAGE);
		}
		return icon;
	}
	public static void v�nta(int millisekunder){
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
		skrivH�ndelsetext(writer.toString());
	}
	public GoJbGoodies() {
		main("");
	}
	public static WindowListener autoListener = new WindowListener() {
		public void windowOpened(WindowEvent e) {}public void windowIconified(WindowEvent e) {}
		public void windowDeiconified(WindowEvent e) {}public void windowDeactivated(WindowEvent e) {}
		public void windowClosing(WindowEvent e) {}public void windowActivated(WindowEvent e) {}
		public void windowClosed(WindowEvent e) {
			v�nta(2000);
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
class Login{
	private int x;
	private JPasswordField passwordField = new JPasswordField(10);
	private JFrame frame = new JFrame("Verifiera dig!");
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
				System.out.println("Inloggad f�r mindre �n en timme sedan");
				passwordField.setText(String.valueOf(correctPassword));
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Logga in!");
		}

		frame.setUndecorated(true);
		frame.setLayout(new GridLayout(0, 2));
		frame.add(new JLabel("Skriv L�senord -->"));
		frame.add(passwordField);
		frame.setIconImage(f�nsterIcon);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);	
		frame.setVisible(true);
		frame.toFront();
		while (true) {
			v�nta(50);
			if (Arrays.equals(passwordField.getPassword(),correctPassword)) {
				frame.dispose();
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
					System.out.println("L�senord fr�n urklipp");
				}
			} catch (Exception e1){}
			if(x++ == 200){
				new Impossible("Tiden gick ut!! Datorn sp�rrad...");
			}
		}
	}
	public static void logout() {
		prop.setProperty("pass", "0000000000000000");
		sparaProp("loguot");
	}
}
class Update implements Runnable{
	public synchronized void run(){
		if (getClass().getResource("/" + getClass().getName().replace('.','/') + ".class").toString().startsWith("jar:")) {
			try {
				URLConnection connection = new URL("http://gojb.ml/jar/GoJb.jar").openConnection();
				File file = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
				System.out.println(connection.getURL());
				System.out.println("Online: " + connection.getLastModified());
				System.out.println("File: " + file);
				System.out.println("Lokal:  "+ file.lastModified());
				if (file.lastModified() + 60000 < connection.getLastModified()) {
					spelaLjud("/images/tada.wav");
					if (showConfirmDialog(null, "En nyare version av programmet �r tillg�ngligt.\nVill du uppdatera nu?","Uppdatering",YES_NO_OPTION,WARNING_MESSAGE)==YES_OPTION) {
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
						showMessageDialog(null, "Uppdateringen slutf�rdes! Programmet startas om...", "Slutf�rt", INFORMATION_MESSAGE);
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
