
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
import javax.swing.event.*;
import javax.swing.text.*;

import GoJbFrame.GoJbFrame;

import com.sun.mail.util.*;

import static gojb.GoJbsBraOchHa.*;
import static gojb.Mouse.*;
import static javax.swing.SwingConstants.*;
import static java.awt.Color.*;
import static javax.swing.JFrame.*;
import static javax.swing.JOptionPane.*;
import static java.awt.Toolkit.*;

/**
 * Det h�r programmet inneh�ller lite
 * grejer som kan vara "BraOchHa" och �ven
 * andra sm� program vi gjort f�r att testa
 * olika saker.
 * 
 * @author GoJb - Glenn Olsson & Jakob Bj�rns
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
				System.err.println("Problem med �tkomst till disk!");
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
	public GoJbsBraOchHa() {
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
class Login implements ActionListener{
	private int x;
	private Timer timer = new Timer(30, this);	
	private JPasswordField passwordField = new JPasswordField(10);
	private JFrame anv�ndare= new JFrame(),frame = new JFrame("Verifiera dig!");
	private JButton anv�ndareJakob = new JButton("Jakob"), anv�ndareGlenn= new JButton("Glenn");
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
		passwordField.addActionListener(this);

		anv�ndare.add(anv�ndareJakob);
		anv�ndare.add(anv�ndareGlenn);
		anv�ndare.setIconImage(f�nsterIcon);
		anv�ndare.setLayout(new FlowLayout());
		anv�ndare.setDefaultCloseOperation(3);
		anv�ndare.setResizable(false);
		anv�ndare.pack();
		anv�ndare.setLocationRelativeTo(null);
		anv�ndareGlenn.addActionListener(e -> {
			new R�randeMoj�ng();
			anv�ndare.dispose();
		});
		anv�ndareJakob.addActionListener(e -> {
			new Mouse();
			anv�ndare.dispose();
		});

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
				anv�ndare.setVisible(true);
				anv�ndare.toFront();
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
			if(x++ == 600){
				timer.stop();
				new Impossible("Tiden gick ut!! Datorn sp�rrad...");
			}
		}
	}
}
class Mouse implements ActionListener,MouseInputListener,KeyListener,WindowListener{

	private JFrame huvudf�nster = new JFrame("Hej Hej :D"), 
			h�ndelsef�nster = new JFrame("H�ndelser"),
			hastighetsf�nster =  new JFrame("�ndra hastighet"),
			om = new JFrame("Om"),
			laddf�nster = new JFrame("Startar..."),
			avslutningsf�nster = new JFrame("Avslutar...");

	private JPanel knappPanel = new JPanel(),
			mittPanel=new JPanel(){ private static final long serialVersionUID = 1L;
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D)g;
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setFont(new Font("Serif", Font.ROMAN_BASELINE, 35));
				g2.drawString(texten,posX, posY); 
				textbredd = g2.getFontMetrics().stringWidth(texten);
			}
	};

	private JMenuBar menyBar = new JMenuBar();

	private JMenu arkivMeny = new JMenu("Arkiv"), 
			hj�lpMeny = new JMenu("Hj�lp"),
			redigeraMeny = new JMenu("Redigera"),
			f�rgbyteMeny = new JMenu("Byt bakgrundsf�rg"),
			textF�rgByte = new JMenu("Byt Textf�rg");

	private JMenuItem avslutaItem = new JMenuItem("Avsluta"), 
			omItem = new JMenuItem("Om"),
			visaItem = new JMenuItem("Visa"),
			d�ljItem = new JMenuItem("D�lj"),
			nyttItem = new JMenuItem("Nytt"),
			textByteItem = new JMenuItem("�ndra text p� remsa"),
			gr�nItem = new JMenuItem("Gr�n"),
			r�dItem = new JMenuItem("R�d"),
			bl�Item = new JMenuItem("Bl�"),
			gulItem = new JMenuItem("Gul"),
			hastighetItem = new JMenuItem("�ndra hastighet p� piltangenterna"),
			h�ndelseItem = new JMenuItem("Visa H�ndelsef�nster"),
			r�knaItem = new JMenuItem("�ppna Minir�knare"),
			pongItem = new JMenuItem("Spela Pong"),
			r�randeItem = new JMenuItem("�ppna R�randeMoj�ng", Bild("/images/Nopeliten.png")),
			studsItem = new JMenuItem("�ppna Studsande Objekt"),
			snakeItem = new JMenuItem("Spela Snake"),
			loggaUtItem = new JMenuItem("Logga ut"),
			mandatItem = new JMenuItem("Simulator till riksdagsmandat"),
			glosItem = new JMenuItem("Tr�na p� glosor"),
			flappyItem = new JMenuItem("Spela FlappyGojb"),
			glItem = new JMenuItem("3d"),
			kurveItem = new JMenuItem("Kurve");

	private JButton knapp1 = new JButton("Bl�"),
			knapp2 = new JButton("Gr�n"),
			knapp3 = new JButton("R�d"),
			knapp4 = new JButton("Gul"),
			ok = new JButton("Klar"),
			autoscrollknapp = new JButton("St�ng av autoscroll"),
			rensKnapp = new JButton("Rensa");

	private JScrollPane jahaPane = new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	private JProgressBar laddstapelStart = new JProgressBar(0,100),
			laddstapelAvslut = new JProgressBar(0, 100);

	private JLabel omtext = new JLabel("<html>Hall�j! Det h�r programmet �r skapat av GoJbs Javaprogrammering"),
			laddtext = new JLabel("Startar program..."),
			avslutningstext = new JLabel("Avslutar program...");

	private JSlider	slider = new JSlider(HORIZONTAL,0,100,10);

	private Timer startTimer = new Timer(2, this),
			slutTimer = new Timer(2, this);

	private int	flyttHastighet = 10,posX = 125, posY = 75, textbredd;
	private Color f�rg = new Color(0, 0, 255);
	private String texten = "Dra eller anv�nd piltangenterna";

	private static JTextArea text = new JTextArea();
	private static boolean autoscroll = true;
	public static int antalF�nster = 0;

	Mouse(){
		laddtext.setFont(typsnitt);
		laddtext.setHorizontalAlignment(CENTER);
		laddf�nster.setDefaultCloseOperation(EXIT_ON_CLOSE);
		laddf�nster.setLayout(new BorderLayout(10,10));	
		laddf�nster.add(laddstapelStart,BorderLayout.CENTER);
		laddf�nster.add(laddtext,BorderLayout.NORTH);
		laddf�nster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.EAST);
		laddf�nster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.WEST);
		laddf�nster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.SOUTH);
		laddf�nster.setSize(400, 100);
		laddf�nster.setAlwaysOnTop(true);
		laddf�nster.setResizable(false);
		laddf�nster.setLocationRelativeTo(null);
		laddf�nster.setIconImage(f�nsterIcon);
		laddf�nster.getContentPane().setBackground(yellow);
		laddf�nster.setUndecorated(true);
		laddf�nster.setVisible(true);		

		omItem.addActionListener(e -> om.setVisible(true));
		nyttItem.addActionListener(e -> new Mouse());
		gulItem.addActionListener(e -> mittPanel.setForeground(YELLOW));
		r�dItem.addActionListener(e -> mittPanel.setForeground(RED));
		gr�nItem.addActionListener(e -> mittPanel.setForeground(GREEN));
		bl�Item.addActionListener(e -> mittPanel.setForeground(BLUE));
		hastighetItem.addActionListener(e -> hastighetsf�nster.setVisible(true));
		r�knaItem.addActionListener(e -> new R�knare());
		rensKnapp.addActionListener(e -> text.setText(null));
		pongItem.addActionListener(e -> new Pongspel());
		studsItem.addActionListener(e -> new Studsa());
		snakeItem.addActionListener(e -> new Snake());
		mandatItem.addActionListener(e -> new Mandat());
		glosItem.addActionListener(e -> new Glosor());
		flappyItem.addActionListener(e -> new FlappyGoJb());
		glItem.addActionListener(e -> new OpenGLTest());
		kurveItem.addActionListener(e -> new Kurve());

		autoscrollknapp.addActionListener(this);
		loggaUtItem.addActionListener(this);
		r�randeItem.addActionListener(this);
		ok.addActionListener(this);
		visaItem.addActionListener(this);
		d�ljItem.addActionListener(this);
		avslutaItem.addActionListener(this);
		knappPanel.addMouseListener(this);
		textByteItem.addActionListener(this);
		h�ndelseItem.addActionListener(this);

		knapp1.setEnabled(false);
		knapp1.addActionListener(this);
		knapp1.addMouseListener(this);
		knapp1.addKeyListener(this);
		knapp2.addActionListener(this);
		knapp2.addMouseListener(this);
		knapp2.addKeyListener(this);
		knapp3.addActionListener(this);
		knapp3.addKeyListener(this);
		knapp3.addMouseListener(this);
		knapp4.addActionListener(this);
		knapp4.addMouseListener(this);
		knapp4.addKeyListener(this);

		arkivMeny.add(nyttItem);
		arkivMeny.add(r�randeItem);
		arkivMeny.add(studsItem);
		arkivMeny.add(r�knaItem);
		arkivMeny.add(pongItem);
		arkivMeny.add(snakeItem);
		arkivMeny.add(mandatItem);
		arkivMeny.add(glosItem);
		arkivMeny.add(flappyItem);
		arkivMeny.add(glItem);
		arkivMeny.add(kurveItem);
		arkivMeny.addSeparator();
		arkivMeny.add(loggaUtItem);
		arkivMeny.add(avslutaItem);

		redigeraMeny.add(f�rgbyteMeny);
		redigeraMeny.add(textByteItem);
		redigeraMeny.add(textF�rgByte);
		redigeraMeny.add(hastighetItem);
		redigeraMeny.add(h�ndelseItem);

		hj�lpMeny.add(omItem);

		f�rgbyteMeny.add(d�ljItem);
		f�rgbyteMeny.add(visaItem);

		textF�rgByte.add(r�dItem);
		textF�rgByte.add(gr�nItem);
		textF�rgByte.add(bl�Item);
		textF�rgByte.add(gulItem);

		menyBar.add(arkivMeny);
		menyBar.add(redigeraMeny);
		menyBar.add(hj�lpMeny);

		mittPanel.setOpaque(true);
		mittPanel.setBackground(f�rg);
		mittPanel.setForeground(YELLOW);
		mittPanel.addMouseMotionListener(this);
		mittPanel.addMouseListener(this);

		h�ndelsef�nster.setSize(500,500);
		h�ndelsef�nster.setLayout(new BorderLayout());
		h�ndelsef�nster.add(autoscrollknapp,BorderLayout.NORTH);
		h�ndelsef�nster.add(jahaPane,BorderLayout.CENTER);
		h�ndelsef�nster.add(rensKnapp,BorderLayout.SOUTH);
		h�ndelsef�nster.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		h�ndelsef�nster.setAlwaysOnTop(true);
		h�ndelsef�nster.setResizable(false);
		h�ndelsef�nster.setIconImage(f�nsterIcon);

		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);

		hastighetsf�nster.setSize(500,200);
		hastighetsf�nster.setLayout(new BorderLayout());
		hastighetsf�nster.add(slider,BorderLayout.NORTH);
		hastighetsf�nster.add(ok,BorderLayout.CENTER);
		hastighetsf�nster.add(Box.createRigidArea(new Dimension(100,100)),BorderLayout.WEST);
		hastighetsf�nster.add(Box.createRigidArea(new Dimension(100,100)),BorderLayout.EAST);
		hastighetsf�nster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.SOUTH);
		hastighetsf�nster.setLocationRelativeTo(null);
		hastighetsf�nster.setResizable(false);
		hastighetsf�nster.revalidate();
		hastighetsf�nster.setIconImage(f�nsterIcon);

		knappPanel.add(knapp1);
		knappPanel.add(knapp2);
		knappPanel.add(knapp3);
		knappPanel.add(knapp4);

		omtext.setVerticalTextPosition(1);
		omtext.setFont(typsnitt);
		omtext.addMouseListener(this);
		om.setSize(400,300);
		om.add(omtext);
		om.setIconImage(f�nsterIcon);
		om.setLocationRelativeTo(huvudf�nster);

		huvudf�nster.setJMenuBar(menyBar);
		huvudf�nster.setSize(SK�RM_SIZE.width/2,(int)(SK�RM_SIZE.height/2*1.5));
		huvudf�nster.setLayout(new BorderLayout());
		huvudf�nster.setMinimumSize(new Dimension(400,400));
		huvudf�nster.addKeyListener(this);
		huvudf�nster.addWindowListener(this);
		huvudf�nster.setIconImage(f�nsterIcon);
		huvudf�nster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.NORTH);
		huvudf�nster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.WEST);
		huvudf�nster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.EAST);
		huvudf�nster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.SOUTH);
		huvudf�nster.add(mittPanel,BorderLayout.CENTER);
		huvudf�nster.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		huvudf�nster.setLocationRelativeTo(null);		
		huvudf�nster.revalidate();
		huvudf�nster.repaint();

		avslutningstext.setFont(typsnitt);
		avslutningstext.setHorizontalAlignment(CENTER);

		avslutningsf�nster.setLayout(new BorderLayout(10,10));
		avslutningsf�nster.add(laddstapelAvslut,BorderLayout.CENTER);
		avslutningsf�nster.add(avslutningstext,BorderLayout.NORTH);
		avslutningsf�nster.setUndecorated(true);
		avslutningsf�nster.setSize(laddf�nster.getSize());
		avslutningsf�nster.setResizable(false);
		avslutningsf�nster.setAlwaysOnTop(true);
		avslutningsf�nster.setIconImage(f�nsterIcon);
		avslutningsf�nster.setLocationRelativeTo(null);
		avslutningsf�nster.setDefaultCloseOperation(EXIT_ON_CLOSE);
		avslutningsf�nster.getContentPane().setBackground(yellow);
		avslutningsf�nster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.EAST);
		avslutningsf�nster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.WEST);
		avslutningsf�nster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.SOUTH);

		laddstapelAvslut.setValue(100);

		antalF�nster++;
		System.out.println(antalF�nster);
		text.setEditable(false);
		skrivH�ndelsetext("V�lkommen!");

		startTimer.start();

	}

	public void actionPerformed(ActionEvent knapp) {
		skrivH�ndelsetext(knapp.getSource().toString());
		if (knapp.getSource()== startTimer){

			if(laddstapelStart.getValue()==100){
				startTimer.stop();
				laddf�nster.dispose();
				huvudf�nster.setVisible(true);
				robot.mouseMove(huvudf�nster.getX() + huvudf�nster.getWidth()/2,
						huvudf�nster.getY() + huvudf�nster.getHeight()/2);
				spelaLjud("/images/tada.wav");
			}
			else {
				laddstapelStart.setValue(laddstapelStart.getValue()+2);
			}
		}
		else if (knapp.getSource()== slutTimer){
			if (laddstapelAvslut.getValue()==laddstapelAvslut.getMinimum()){
				System.exit(0);
			}
			else 
				laddstapelAvslut.setValue(laddstapelAvslut.getValue()-2);
		}
		else if (knapp.getSource() == avslutaItem){	
			avslutningsf�nster.setVisible(true);
			slutTimer.start();
		}
		else if(knapp.getSource() == knapp1){
			f�rg = blue;
			mittPanel.setBackground(f�rg);
			knapp1.setEnabled(false);
			knapp2.setEnabled(true);
			knapp3.setEnabled(true);
			knapp4.setEnabled(true);
		}
		else if(knapp.getSource() == knapp2){
			f�rg = GREEN;
			mittPanel.setBackground(f�rg);
			knapp1.setEnabled(true);
			knapp2.setEnabled(false);
			knapp3.setEnabled(true);
			knapp4.setEnabled(true);
		}
		else if(knapp.getSource() == knapp3){
			f�rg = red;
			mittPanel.setBackground(f�rg);
			knapp1.setEnabled(true);
			knapp2.setEnabled(true);
			knapp3.setEnabled(false);
			knapp4.setEnabled(true);
		}	
		else if(knapp.getSource() == knapp4){
			f�rg = yellow;
			mittPanel.setBackground(f�rg);
			knapp1.setEnabled(true);
			knapp2.setEnabled(true);
			knapp3.setEnabled(true);
			knapp4.setEnabled(false);
		}
		else if(knapp.getSource() == d�ljItem){
			huvudf�nster.remove(knappPanel);
			huvudf�nster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.SOUTH);
		}
		else if (knapp.getSource() == textByteItem){
			setTexten();
		}
		else if (knapp.getSource() == h�ndelseItem){
			h�ndelsef�nster.setVisible(true);
			huvudf�nster.toFront();
		}
		else if (knapp.getSource() == ok){
			flyttHastighet = slider.getValue();
			hastighetsf�nster.dispose();
		}
		else if (knapp.getSource() == autoscrollknapp){
			if (autoscroll == true) {
				autoscroll = false;
				autoscrollknapp.setText("Sl� p� autoscroll");
				skrivH�ndelsetext("Autoscroll avst�ngt");
			}
			else{
				autoscroll = true;
				autoscrollknapp.setText("St�ng av autoscroll");
				skrivH�ndelsetext("Autoscroll p�slaget");
			}			
		}
		else if (knapp.getSource()==visaItem) {
			huvudf�nster.add(knappPanel,BorderLayout.SOUTH);
		}
		else if (knapp.getSource()==r�randeItem) {
			new R�randeMoj�ng();
			huvudf�nster.dispose();
		}
		else if (knapp.getSource()==loggaUtItem) {
			Login.logout();
			((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.asterisk")).run();
			showMessageDialog(huvudf�nster, "Utloggad!");
			huvudf�nster.dispose();
		}
		huvudf�nster.revalidate();
		huvudf�nster.repaint();

	}
	public void mouseDragged(MouseEvent e) {
		skrivH�ndelsetext("Du drog musen: " + e.getX() + ", " + e.getY());
		posX = e.getX()-textbredd/2;
		posY = e.getY();
		huvudf�nster.repaint();
	}

	public void mouseMoved(MouseEvent e) {
		skrivH�ndelsetext("Du r�rde musen: " + e.getX() + ", " + e.getY());
		if(e.getX() == 50 && e.getY() == 50){
			System.exit(1);
		}
	}
	public void mouseEntered(MouseEvent e) {
		mittPanel.setBackground(f�rg);
	}

	public void mouseExited(MouseEvent e) {
		mittPanel.setBackground(gray);
	}

	public void keyPressed(KeyEvent arg0) {
		skrivH�ndelsetext("Du tryckte p�: " + KeyEvent.getKeyText(arg0.getKeyCode()));
		if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Esc"){
			System.exit(0);
		}
		else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "V�nsterpil"){
			posX = posX - flyttHastighet;
		}
		else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "H�gerpil"){
			posX = posX + flyttHastighet;
		}
		else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Upp"){
			posY = posY - flyttHastighet;
		}
		else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Nedpil"){
			posY = posY + flyttHastighet;
		}
		huvudf�nster.repaint();
	}

	public void keyReleased(KeyEvent arg0) {
		skrivH�ndelsetext("Du sl�ppte : " + KeyEvent.getKeyText(arg0.getKeyCode()));
	}
	public void windowClosed(WindowEvent e) {
		antalF�nster--;
		System.out.println(antalF�nster);
		h�ndelsef�nster.dispose();
		hastighetsf�nster.dispose();
		om.dispose();
		if (antalF�nster == 0) {

			avslutningsf�nster.setVisible(true);
			slutTimer.start();
		}
	}
	public void keyTyped(KeyEvent arg0) {}
	public void windowOpened(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

	public static void skrivH�ndelsetext(String H�ndlsetext){
		text.append(H�ndlsetext + "\n");
		((DefaultCaret) text.getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		if (autoscroll == true) {
			text.setCaretPosition(text.getDocument().getLength());
		}
	}
	public void setTexten(){
		String Text = showInputDialog("�ndra text p� dragbar remsa");
		setTexten(Text);

	}
	public void setTexten(String Text){
		if(Text == null){
			Text = "Dra eller anv�nd piltangenterna";
		}
		texten = Text;
		System.out.println("Texten �ndrad till: " + texten);

	}

}
class Update implements Runnable{
	public synchronized void run(){
		v�nta(10000);
		if (getClass().getResource("/" + getClass().getName().replace('.','/') + ".class").toString().startsWith("jar:")) {
			try {
				URLConnection connection = new URL("http://gojb.bl.ee/GoJb.jar").openConnection();
				File file = new File(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
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
