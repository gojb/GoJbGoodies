
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
			new Mouse();
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
class Mouse implements ActionListener,MouseInputListener,KeyListener,WindowListener{

	private JFrame huvudfönster = new JFrame("Hej Hej :D"), 
			händelsefönster = new JFrame("Händelser"),
			hastighetsfönster =  new JFrame("Ändra hastighet"),
			om = new JFrame("Om"),
			laddfönster = new JFrame("Startar..."),
			avslutningsfönster = new JFrame("Avslutar...");

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
			hjälpMeny = new JMenu("Hjälp"),
			redigeraMeny = new JMenu("Redigera"),
			färgbyteMeny = new JMenu("Byt bakgrundsfärg"),
			textFärgByte = new JMenu("Byt Textfärg");

	private JMenuItem avslutaItem = new JMenuItem("Avsluta"), 
			omItem = new JMenuItem("Om"),
			visaItem = new JMenuItem("Visa"),
			döljItem = new JMenuItem("Dölj"),
			nyttItem = new JMenuItem("Nytt"),
			textByteItem = new JMenuItem("Ändra text på remsa"),
			grönItem = new JMenuItem("Grön"),
			rödItem = new JMenuItem("Röd"),
			blåItem = new JMenuItem("Blå"),
			gulItem = new JMenuItem("Gul"),
			hastighetItem = new JMenuItem("Ändra hastighet på piltangenterna"),
			händelseItem = new JMenuItem("Visa Händelsefönster"),
			räknaItem = new JMenuItem("Öppna Miniräknare"),
			pongItem = new JMenuItem("Spela Pong"),
			rörandeItem = new JMenuItem("Öppna RörandeMojäng", Bild("/images/Nopeliten.png")),
			studsItem = new JMenuItem("Öppna Studsande Objekt"),
			snakeItem = new JMenuItem("Spela Snake"),
			loggaUtItem = new JMenuItem("Logga ut"),
			mandatItem = new JMenuItem("Simulator till riksdagsmandat"),
			glosItem = new JMenuItem("Träna på glosor"),
			flappyItem = new JMenuItem("Spela FlappyGojb"),
			glItem = new JMenuItem("3d"),
			kurveItem = new JMenuItem("Kurve");

	private JButton knapp1 = new JButton("Blå"),
			knapp2 = new JButton("Grön"),
			knapp3 = new JButton("Röd"),
			knapp4 = new JButton("Gul"),
			ok = new JButton("Klar"),
			autoscrollknapp = new JButton("Stäng av autoscroll"),
			rensKnapp = new JButton("Rensa");

	private JScrollPane jahaPane = new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	private JProgressBar laddstapelStart = new JProgressBar(0,100),
			laddstapelAvslut = new JProgressBar(0, 100);

	private JLabel omtext = new JLabel("<html>Hallåj! Det här programmet är skapat av GoJbs Javaprogrammering"),
			laddtext = new JLabel("Startar program..."),
			avslutningstext = new JLabel("Avslutar program...");

	private JSlider	slider = new JSlider(HORIZONTAL,0,100,10);

	private Timer startTimer = new Timer(2, this),
			slutTimer = new Timer(2, this);

	private int	flyttHastighet = 10,posX = 125, posY = 75, textbredd;
	private Color färg = new Color(0, 0, 255);
	private String texten = "Dra eller använd piltangenterna";

	private static JTextArea text = new JTextArea();
	private static boolean autoscroll = true;
	public static int antalFönster = 0;

	Mouse(){
		laddtext.setFont(typsnitt);
		laddtext.setHorizontalAlignment(CENTER);
		laddfönster.setDefaultCloseOperation(EXIT_ON_CLOSE);
		laddfönster.setLayout(new BorderLayout(10,10));	
		laddfönster.add(laddstapelStart,BorderLayout.CENTER);
		laddfönster.add(laddtext,BorderLayout.NORTH);
		laddfönster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.EAST);
		laddfönster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.WEST);
		laddfönster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.SOUTH);
		laddfönster.setSize(400, 100);
		laddfönster.setAlwaysOnTop(true);
		laddfönster.setResizable(false);
		laddfönster.setLocationRelativeTo(null);
		laddfönster.setIconImage(fönsterIcon);
		laddfönster.getContentPane().setBackground(yellow);
		laddfönster.setUndecorated(true);
		laddfönster.setVisible(true);		

		omItem.addActionListener(e -> om.setVisible(true));
		nyttItem.addActionListener(e -> new Mouse());
		gulItem.addActionListener(e -> mittPanel.setForeground(YELLOW));
		rödItem.addActionListener(e -> mittPanel.setForeground(RED));
		grönItem.addActionListener(e -> mittPanel.setForeground(GREEN));
		blåItem.addActionListener(e -> mittPanel.setForeground(BLUE));
		hastighetItem.addActionListener(e -> hastighetsfönster.setVisible(true));
		räknaItem.addActionListener(e -> new Räknare());
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
		rörandeItem.addActionListener(this);
		ok.addActionListener(this);
		visaItem.addActionListener(this);
		döljItem.addActionListener(this);
		avslutaItem.addActionListener(this);
		knappPanel.addMouseListener(this);
		textByteItem.addActionListener(this);
		händelseItem.addActionListener(this);

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
		arkivMeny.add(rörandeItem);
		arkivMeny.add(studsItem);
		arkivMeny.add(räknaItem);
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

		redigeraMeny.add(färgbyteMeny);
		redigeraMeny.add(textByteItem);
		redigeraMeny.add(textFärgByte);
		redigeraMeny.add(hastighetItem);
		redigeraMeny.add(händelseItem);

		hjälpMeny.add(omItem);

		färgbyteMeny.add(döljItem);
		färgbyteMeny.add(visaItem);

		textFärgByte.add(rödItem);
		textFärgByte.add(grönItem);
		textFärgByte.add(blåItem);
		textFärgByte.add(gulItem);

		menyBar.add(arkivMeny);
		menyBar.add(redigeraMeny);
		menyBar.add(hjälpMeny);

		mittPanel.setOpaque(true);
		mittPanel.setBackground(färg);
		mittPanel.setForeground(YELLOW);
		mittPanel.addMouseMotionListener(this);
		mittPanel.addMouseListener(this);

		händelsefönster.setSize(500,500);
		händelsefönster.setLayout(new BorderLayout());
		händelsefönster.add(autoscrollknapp,BorderLayout.NORTH);
		händelsefönster.add(jahaPane,BorderLayout.CENTER);
		händelsefönster.add(rensKnapp,BorderLayout.SOUTH);
		händelsefönster.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		händelsefönster.setAlwaysOnTop(true);
		händelsefönster.setResizable(false);
		händelsefönster.setIconImage(fönsterIcon);

		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(10);
		slider.setMinorTickSpacing(1);

		hastighetsfönster.setSize(500,200);
		hastighetsfönster.setLayout(new BorderLayout());
		hastighetsfönster.add(slider,BorderLayout.NORTH);
		hastighetsfönster.add(ok,BorderLayout.CENTER);
		hastighetsfönster.add(Box.createRigidArea(new Dimension(100,100)),BorderLayout.WEST);
		hastighetsfönster.add(Box.createRigidArea(new Dimension(100,100)),BorderLayout.EAST);
		hastighetsfönster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.SOUTH);
		hastighetsfönster.setLocationRelativeTo(null);
		hastighetsfönster.setResizable(false);
		hastighetsfönster.revalidate();
		hastighetsfönster.setIconImage(fönsterIcon);

		knappPanel.add(knapp1);
		knappPanel.add(knapp2);
		knappPanel.add(knapp3);
		knappPanel.add(knapp4);

		omtext.setVerticalTextPosition(1);
		omtext.setFont(typsnitt);
		omtext.addMouseListener(this);
		om.setSize(400,300);
		om.add(omtext);
		om.setIconImage(fönsterIcon);
		om.setLocationRelativeTo(huvudfönster);

		huvudfönster.setJMenuBar(menyBar);
		huvudfönster.setSize(SKÄRM_SIZE.width/2,(int)(SKÄRM_SIZE.height/2*1.5));
		huvudfönster.setLayout(new BorderLayout());
		huvudfönster.setMinimumSize(new Dimension(400,400));
		huvudfönster.addKeyListener(this);
		huvudfönster.addWindowListener(this);
		huvudfönster.setIconImage(fönsterIcon);
		huvudfönster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.NORTH);
		huvudfönster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.WEST);
		huvudfönster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.EAST);
		huvudfönster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.SOUTH);
		huvudfönster.add(mittPanel,BorderLayout.CENTER);
		huvudfönster.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		huvudfönster.setLocationRelativeTo(null);		
		huvudfönster.revalidate();
		huvudfönster.repaint();

		avslutningstext.setFont(typsnitt);
		avslutningstext.setHorizontalAlignment(CENTER);

		avslutningsfönster.setLayout(new BorderLayout(10,10));
		avslutningsfönster.add(laddstapelAvslut,BorderLayout.CENTER);
		avslutningsfönster.add(avslutningstext,BorderLayout.NORTH);
		avslutningsfönster.setUndecorated(true);
		avslutningsfönster.setSize(laddfönster.getSize());
		avslutningsfönster.setResizable(false);
		avslutningsfönster.setAlwaysOnTop(true);
		avslutningsfönster.setIconImage(fönsterIcon);
		avslutningsfönster.setLocationRelativeTo(null);
		avslutningsfönster.setDefaultCloseOperation(EXIT_ON_CLOSE);
		avslutningsfönster.getContentPane().setBackground(yellow);
		avslutningsfönster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.EAST);
		avslutningsfönster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.WEST);
		avslutningsfönster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.SOUTH);

		laddstapelAvslut.setValue(100);

		antalFönster++;
		System.out.println(antalFönster);
		text.setEditable(false);
		skrivHändelsetext("Välkommen!");

		startTimer.start();

	}

	public void actionPerformed(ActionEvent knapp) {
		skrivHändelsetext(knapp.getSource().toString());
		if (knapp.getSource()== startTimer){

			if(laddstapelStart.getValue()==100){
				startTimer.stop();
				laddfönster.dispose();
				huvudfönster.setVisible(true);
				robot.mouseMove(huvudfönster.getX() + huvudfönster.getWidth()/2,
						huvudfönster.getY() + huvudfönster.getHeight()/2);
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
			avslutningsfönster.setVisible(true);
			slutTimer.start();
		}
		else if(knapp.getSource() == knapp1){
			färg = blue;
			mittPanel.setBackground(färg);
			knapp1.setEnabled(false);
			knapp2.setEnabled(true);
			knapp3.setEnabled(true);
			knapp4.setEnabled(true);
		}
		else if(knapp.getSource() == knapp2){
			färg = GREEN;
			mittPanel.setBackground(färg);
			knapp1.setEnabled(true);
			knapp2.setEnabled(false);
			knapp3.setEnabled(true);
			knapp4.setEnabled(true);
		}
		else if(knapp.getSource() == knapp3){
			färg = red;
			mittPanel.setBackground(färg);
			knapp1.setEnabled(true);
			knapp2.setEnabled(true);
			knapp3.setEnabled(false);
			knapp4.setEnabled(true);
		}	
		else if(knapp.getSource() == knapp4){
			färg = yellow;
			mittPanel.setBackground(färg);
			knapp1.setEnabled(true);
			knapp2.setEnabled(true);
			knapp3.setEnabled(true);
			knapp4.setEnabled(false);
		}
		else if(knapp.getSource() == döljItem){
			huvudfönster.remove(knappPanel);
			huvudfönster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.SOUTH);
		}
		else if (knapp.getSource() == textByteItem){
			setTexten();
		}
		else if (knapp.getSource() == händelseItem){
			händelsefönster.setVisible(true);
			huvudfönster.toFront();
		}
		else if (knapp.getSource() == ok){
			flyttHastighet = slider.getValue();
			hastighetsfönster.dispose();
		}
		else if (knapp.getSource() == autoscrollknapp){
			if (autoscroll == true) {
				autoscroll = false;
				autoscrollknapp.setText("Slå på autoscroll");
				skrivHändelsetext("Autoscroll avstängt");
			}
			else{
				autoscroll = true;
				autoscrollknapp.setText("Stäng av autoscroll");
				skrivHändelsetext("Autoscroll påslaget");
			}			
		}
		else if (knapp.getSource()==visaItem) {
			huvudfönster.add(knappPanel,BorderLayout.SOUTH);
		}
		else if (knapp.getSource()==rörandeItem) {
			new RörandeMojäng();
			huvudfönster.dispose();
		}
		else if (knapp.getSource()==loggaUtItem) {
			Login.logout();
			((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.asterisk")).run();
			showMessageDialog(huvudfönster, "Utloggad!");
			huvudfönster.dispose();
		}
		huvudfönster.revalidate();
		huvudfönster.repaint();

	}
	public void mouseDragged(MouseEvent e) {
		skrivHändelsetext("Du drog musen: " + e.getX() + ", " + e.getY());
		posX = e.getX()-textbredd/2;
		posY = e.getY();
		huvudfönster.repaint();
	}

	public void mouseMoved(MouseEvent e) {
		skrivHändelsetext("Du rörde musen: " + e.getX() + ", " + e.getY());
		if(e.getX() == 50 && e.getY() == 50){
			System.exit(1);
		}
	}
	public void mouseEntered(MouseEvent e) {
		mittPanel.setBackground(färg);
	}

	public void mouseExited(MouseEvent e) {
		mittPanel.setBackground(gray);
	}

	public void keyPressed(KeyEvent arg0) {
		skrivHändelsetext("Du tryckte på: " + KeyEvent.getKeyText(arg0.getKeyCode()));
		if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Esc"){
			System.exit(0);
		}
		else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Vänsterpil"){
			posX = posX - flyttHastighet;
		}
		else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Högerpil"){
			posX = posX + flyttHastighet;
		}
		else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Upp"){
			posY = posY - flyttHastighet;
		}
		else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Nedpil"){
			posY = posY + flyttHastighet;
		}
		huvudfönster.repaint();
	}

	public void keyReleased(KeyEvent arg0) {
		skrivHändelsetext("Du släppte : " + KeyEvent.getKeyText(arg0.getKeyCode()));
	}
	public void windowClosed(WindowEvent e) {
		antalFönster--;
		System.out.println(antalFönster);
		händelsefönster.dispose();
		hastighetsfönster.dispose();
		om.dispose();
		if (antalFönster == 0) {

			avslutningsfönster.setVisible(true);
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

	public static void skrivHändelsetext(String Händlsetext){
		text.append(Händlsetext + "\n");
		((DefaultCaret) text.getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		if (autoscroll == true) {
			text.setCaretPosition(text.getDocument().getLength());
		}
	}
	public void setTexten(){
		String Text = showInputDialog("Ändra text på dragbar remsa");
		setTexten(Text);

	}
	public void setTexten(String Text){
		if(Text == null){
			Text = "Dra eller använd piltangenterna";
		}
		texten = Text;
		System.out.println("Texten ändrad till: " + texten);

	}

}
class Update implements Runnable{
	public synchronized void run(){
		vänta(10000);
		if (getClass().getResource("/" + getClass().getName().replace('.','/') + ".class").toString().startsWith("jar:")) {
			try {
				URLConnection connection = new URL("http://gojb.bl.ee/GoJb.jar").openConnection();
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
