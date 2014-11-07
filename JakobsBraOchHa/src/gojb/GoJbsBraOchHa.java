package gojb;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.text.*;
import java.util.*;
import java.util.zip.*;

import javax.crypto.*;
import javax.crypto.spec.*;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.*;
import javax.swing.text.*;

import org.lwjgl.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.*;
import org.lwjgl.opengl.DisplayMode;

import com.sun.mail.util.*;

import GoJbFrame.GoJbFrame;
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
			if (argString.equals("Glosor")) {
				new Glosor();
				return;
			}
			else if (argString.equals("Mail")) {
				new Mailkorg();
				return;
			}
		} catch (Exception e) {argString ="";}
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
					key = key + "\0";
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
		glItem.addActionListener(e -> new FullscreenExample().start());
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
class Mandat{
	private JFrame frame = new JFrame("Mandatsimulator för riksdagen");
	private String[] partiNamn = {	"",
			"Socialdemokraterna",
			"Vänsterpartiet",
			"Miljöpartiet",
			"Moderaterna",
			"Centerpartiet",
			"Folkpartiet",
			"Kristdemokraterna",
			"Sverigedemokraterna",
			"Feministiskt initiativ",
	"Övriga"};
	private final int i = partiNamn.length;
	private JTextField[] värden = new JTextField[i];
	private JLabel[] mandat = new JLabel[i],
			parti = new JLabel[i],
			procentLabels = new JLabel[i];
	private JLabel summaLabel = new JLabel(),
			mellanrum = new JLabel(),
			mellanrum2 = new JLabel();
	private double[] tal = new double[i],
			uddatal = new double[i],
			procent = new double[i];
	private int[] antalmandat = new int[i];
	private JButton button = new JButton("Öppna jämförelser");
	private Color[] färger = {white,red,red.darker(),green,blue,green.darker(),blue.darker(),blue.darker().darker(),yellow,magenta,gray};
	private JFrame[] jämförelseFrames = new JFrame[20];
	private int nr;
	private long s = 0;
	Mandat() {

		JLabel label = new JLabel("Parti:");
		JLabel label2 = new JLabel("Röster:");
		JLabel label3 = new JLabel("Mandat i riksdagen:");
		JLabel label4 = new JLabel("Procent av röster");

		frame.add(label);
		frame.add(label2);
		frame.add(label4);
		frame.add(label3);

		label.setOpaque(true);
		label2.setOpaque(true);
		label3.setOpaque(true);
		label4.setOpaque(true);
		label.setBackground(white);
		label2.setBackground(white);
		label3.setBackground(white);
		label4.setBackground(white);
		summaLabel.setOpaque(true);
		summaLabel.setBackground(white);
		for (int i = 1; i < mandat.length; i++) {
			parti[i]=new JLabel(partiNamn[i]);
			parti[i].setIcon(Bild("/images/Partier/" + partiNamn[i] + ".png"));
			parti[i].setBackground(white);
			parti[i].setOpaque(true);
			värden[i]=new JTextField();
			värden[i].addCaretListener(e -> uppdaterasumma());
			mandat[i]=new JLabel();
			mandat[i].setOpaque(true);
			mandat[i].setBackground(white);
			procentLabels[i]=new JLabel();
			procentLabels[i].setOpaque(true);
			procentLabels[i].setBackground(white);
			frame.add(parti[i]);
			frame.add(värden[i]);
			frame.add(procentLabels[i]);
			frame.add(mandat[i]);
			uddatal[i]=1.4;
		}
		mellanrum.setOpaque(true);
		mellanrum.setBackground(white);
		mellanrum2.setOpaque(true);
		mellanrum2.setBackground(white);
		button.addActionListener(e -> jämför());
		frame.add(button);
		frame.add(summaLabel);
		frame.add(mellanrum);
		frame.add(mellanrum2);
		frame.setLayout(new GridLayout(i+1,4,1,2));
		frame.setIconImage(fönsterIcon);
		frame.getContentPane().setBackground(black);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void uppdaterasumma() {
		s=0;
		for (int i = 1; i < mandat.length; i++) {
			try {
				s = s + Long.parseLong(värden[i].getText());
			} catch (NumberFormatException e) {}
		}
		for (int i = 1; i < mandat.length; i++) {
			try {
				procentLabels[i].setText(Double.toString(procent[i]=(double) Math.round(((double)Long.parseLong(värden[i].getText()))/((double)s)*10000)/100)+"%");
			} catch (Exception e) {
				procentLabels[i].setText("0%");
			}
		}
		summaLabel.setText("Totalt: " + Long.toString(s));
		beräkna();
	}
	private void beräkna(){

		for (int i = 1; i < antalmandat.length; i++) {
			antalmandat[i] = 0;
			uddatal[i] = 1.4;
		}
		if (s!=0) {
			for (int i1 = 1; i1 < 350; i1++) {
				ArrayList<Double> list = new ArrayList<Double>(i);

				for (int i = 1; i < mandat.length; i++) {

					if (procent[i] >= 4.0) {
						try {
							tal[i] = Double
									.parseDouble(värden[i].getText())
									/ uddatal[i];
						} catch (NumberFormatException e) {
							tal[i] = 0;
						}
					} else {
						tal[i] = 0;
					}
					list.add(tal[i]);
				}
				for (int i = 1; i < mandat.length; i++) {
					if (Collections.max(list) == tal[i]) {
						antalmandat[i]++;
						if (uddatal[i] == 1.4) {
							uddatal[i] = 3;
						} else {
							uddatal[i] = uddatal[i] + 2;
						}
						break;
					}
				}
			}
		}
		Integer a = 0;
		for (int i = 1; i < mandat.length; i++) {
			mandat[i].setText(Integer.toString(antalmandat[i]));
			a = a + antalmandat[i];
		}
		mellanrum2.setText(a.toString());
		for (int i = 1; i < nr+1; i++) {
			try {
				jämförelseFrames[i].repaint();
			} catch (Exception e) {}
		}
	}
	private void jämför(){
		nr++;
		JCheckBox[] checkBoxes = new JCheckBox[i];
		JFrame frame = jämförelseFrames[nr] = new JFrame();
		JLabel summa = new JLabel(),summa2 = new JLabel();
		JPanel panel = new JPanel();
		@SuppressWarnings("serial")
		class Diagram extends JPanel{
			public void paintComponent(Graphics g1) { 
				Graphics2D g = (Graphics2D) g1;
				setBackground(white);
				Insets i = getInsets();
				g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				int w = getWidth()-i.left-i.right,
						h = getHeight()-i.top-i.bottom,
						diam = Math.min(h,w),
						x = i.left + (w-diam)/2,  
						y = i.top  + (h-diam)/2,  a = 90,alla = 0,allaandra = 0;
				boolean ja = false;
				for (int j = 1; j < antalmandat.length; j++) {
					if (antalmandat[j]!=0) {
						ja=true;
						if (checkBoxes[j].isSelected()) {
							g.setColor(färger[j]);
							int partFilled  = (int) (Math.round(antalmandat[j]*1.0315186246418338108882521489971));    
							g.fillArc(x, y, diam, diam, a, partFilled);
							g.drawArc(x, y, diam, diam, a, partFilled);
							a=a+partFilled;
							allaandra++;
						}
						alla++;
					}
				}
				if (alla==allaandra&&ja) {
					while (a-90<=360) {
						g.fillArc(x, y, diam, diam, a, 1);
						g.drawArc(x, y, diam, diam, a, 1);
						a++;
					}
				}
				int summan=0;
				for (int i1 = 1; i1 < checkBoxes.length; i1++) {
					if (checkBoxes[i1].isSelected()) {
						summan=summan+antalmandat[i1];
					}
				}
				summa.setText("Mandat: " + Integer.toString(summan));
				summa2.setText("Procent: " + Double.toString((double)Math.round(summan/0.349)/10) + "%");
			}
		}
		for (int i = 1; i < checkBoxes.length; i++) {
			checkBoxes[i]=new JCheckBox(partiNamn[i]);
			panel.add(checkBoxes[i]);
			checkBoxes[i].addActionListener(e -> {
				frame.repaint();
			});
		}
		panel.setLayout(new GridLayout(i+1, 1));
		panel.add(summa);
		panel.add(summa2);
		frame.setLayout(new GridLayout(1,2,0,1));
		frame.add(panel);
		frame.add(new Diagram());
		frame.setIconImage(fönsterIcon);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
class Räknare implements ActionListener{

	JFrame 	frame = new JFrame("Miniräknare");

	JPanel 	RäknarKnappar = new JPanel(),
			RäknarPanel = new JPanel();

	JButton[] sifferButtons = new JButton[10];

	JButton	MiniränkarknappPlus = new JButton("+"),
			MiniränkarknappMinus = new JButton("-"),
			MiniränkarknappGånger = new JButton("*"),
			MiniränkarknappDelat = new JButton("/"),
			MiniränkarknappLikamed = new JButton("=");

	JLabel	Summa = new JLabel(""),
			Räknesätt = new JLabel();

	JTextArea Räknartext = new JTextArea();

	boolean nyräkning = false;

	JButton C = new JButton("C"),
			Punkt = new JButton(".");

	double 	a = 0,
			b = 0;

	public Räknare() {

		RäknarKnappar.setLayout(new GridLayout(5,5,5,5));

		for (int i = 1; i < sifferButtons.length; i++) {
			sifferButtons[i]=new JButton(Integer.toString(i));
			sifferButtons[i].addActionListener(this);
			RäknarKnappar.add(sifferButtons[i]);
			if (i==3) {
				RäknarKnappar.add(MiniränkarknappPlus);
			}
			else if (i==6) {
				RäknarKnappar.add(MiniränkarknappMinus);
			}
			else if (i==9) {
				sifferButtons[0] = new JButton("0");
				sifferButtons[0].setPreferredSize(new Dimension(120,100));
				sifferButtons[0].addActionListener(this);
				RäknarKnappar.add(MiniränkarknappGånger);
				RäknarKnappar.add(Punkt);
				RäknarKnappar.add(sifferButtons[0]);
				RäknarKnappar.add(MiniränkarknappLikamed);	
				RäknarKnappar.add(MiniränkarknappDelat);
				RäknarKnappar.add(C);
				RäknarKnappar.setBackground(white);

			}
		}
		MiniränkarknappGånger.addActionListener(this);
		MiniränkarknappDelat.addActionListener(this);
		MiniränkarknappMinus.addActionListener(this);
		MiniränkarknappPlus.addActionListener(this);
		MiniränkarknappLikamed.addActionListener(this);
		C.addActionListener(this);
		Punkt.addActionListener(this);

		RäknarPanel.add(Summa);
		RäknarPanel.add(Räknesätt);
		RäknarPanel.add(Räknartext);
		RäknarPanel.setBackground(white);

		Räknartext.setFont(typsnitt);
		Summa.setFont(typsnitt);
		Räknesätt.setFont(typsnitt);

		frame.setLayout(new BorderLayout());
		frame.add(RäknarPanel,BorderLayout.NORTH);
		frame.add(RäknarKnappar,BorderLayout.CENTER);
		frame.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.WEST);
		frame.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.EAST);
		frame.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.SOUTH);
		frame.setBackground(WHITE);
		frame.pack();
		frame.setIconImage(fönsterIcon);
		frame.getContentPane().setBackground(white);
		frame.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == MiniränkarknappDelat){ 
			if (Räknesätt.getText()==("")) {
				Räknesätt.setText("del");
			}
			RäknaUt();
			Räknesätt.setText("/");
			nyräkning = false;
		}
		else if (e.getSource() == MiniränkarknappGånger){ 
			RäknaUt();
			Räknesätt.setText("*");
			nyräkning = false;
		}
		else if (e.getSource() == MiniränkarknappMinus){ 
			RäknaUt();
			Räknesätt.setText("-");
			nyräkning = false;
		}
		else if (e.getSource() == MiniränkarknappPlus){ 
			RäknaUt();
			Räknesätt.setText("+");
			nyräkning = false;

		}
		else if (e.getSource()==C) {
			Räknesätt.setText(null);
			Summa.setText(null);
			Räknartext.setText(null);
		}

		if (nyräkning){
			nyräkning = false;
			C.doClick();

		}
		if (e.getSource() == MiniränkarknappLikamed){
			RäknaUt();
			Räknesätt.setText("");
			nyräkning = true;
		}
		else {
			for (int i = 0; i < sifferButtons.length; i++) {
				if (e.getSource()==sifferButtons[i]) {
					Räknartext.append(Integer.toString(i));
					return;
				}
			}
		}
		if (e.getSource() == Punkt) {
			Räknartext.append(".");
		}
	}
	public void RäknaUt() {
		try {
			a = Double.parseDouble(Summa.getText());
		} catch (Exception e) {
			a = 0;
		}
		try {
			b = Double.parseDouble(Räknartext.getText());
		} catch (Exception e) {
			b = 0;
		}
		if (Räknesätt.getText() == "+"){
			Summa.setText(Double.toString(a+b));
		}
		else if (Räknesätt.getText() == "-") {
			Summa.setText(Double.toString(a-b));
		}
		else if (Räknesätt.getText() == "*") {
			Summa.setText(Double.toString(a*b));
		}
		else if (Räknesätt.getText() == "/") {
			Summa.setText(Double.toString(a/b));
		}
		else if (Räknesätt.getText() == "del") {
			Summa.setText(Double.toString(a+b));
		}
		else {
			Summa.setText(Double.toString(a+b));
		}
		Räknartext.setText(null);
	}	
}
@SuppressWarnings("serial")
class Pongspel extends JPanel implements ActionListener,KeyListener,WindowListener,MouseMotionListener{

	private int x,y,VänsterX=0,VänsterY,HögerX,HögerY,RektBredd=10,RektHöjd=100,
			bredd=20,höjd=30,hastighet,c, d,PoängVänster=0,PoängHöger=0,py=10,px=10;
	private JFrame frame = new JFrame("Spel");
	private Timer timer = new Timer(10, this);
	private Boolean GameOver=false,hupp=false,hner=false,vupp=false,vner=false;
	private String PoängTill,SpelareVänster,SpelareHöger;

	public Pongspel() {
		SpelareVänster = showInputDialog("Spelare till vänster:");
		if (SpelareVänster == null) {
			return;
		}
		else if (SpelareVänster.equals("")) {
			SpelareVänster = "Spelare 1";
		}
		SpelareHöger = showInputDialog("Spelare till höger:");
		if (SpelareHöger == null) {
			return;
		}
		else if (SpelareHöger.equals("")) {
			SpelareHöger = "Spelare 2";
		}


		addMouseMotionListener(this);
		setForeground(red);
		setPreferredSize(new Dimension(700, 500));
		setOpaque(true);	
		setBackground(black.brighter());

		frame.addMouseMotionListener(this);
		frame.setIconImage(fönsterIcon);
		frame.addWindowListener(this);
		frame.addKeyListener(this);
		frame.add(this);
		frame.pack();
		frame.repaint();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		HögerY = getHeight()/2;
		VänsterY = getHeight()/2;
		HögerX=getWidth()-bredd-1;
		StartaOm();

	}
	private void StartaOm(){
		x = getWidth()/2;
		y = random.nextInt(getHeight());
		hastighet = 2;
		c = hastighet;
		d = hastighet;
		timer.start();

	}
	private void GameOver(String PoängTillVänsterEllerHöger) {
		timer.stop();
		System.out.println("Game over!");
		getDefaultToolkit().beep();

		if (PoängTillVänsterEllerHöger=="Vänster"){
			PoängVänster++;
			PoängTill = SpelareVänster;
		}
		else if (PoängTillVänsterEllerHöger=="Höger") {
			PoängHöger++;
			PoängTill = SpelareHöger;
		}

		GameOver=true;

		frame.repaint();
	}
	public void keyPressed(KeyEvent e) {
		if(KeyEvent.getKeyText(e.getKeyCode()) == "Upp"){
			hupp = true;
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()) == "Nedpil"){
			hner = true;
		}
		else if (e.getKeyCode() == 87 ) {
			vupp = true;
		}
		else if (e.getKeyCode() == 83) {
			vner = true;
		}
		else if (KeyEvent.getKeyText(e.getKeyCode()) == "Mellanslag") {
			GameOver = false;
			frame.repaint();
			StartaOm();
		}
		else if (KeyEvent.getKeyText(e.getKeyCode()) == "Esc") {
			frame.dispose();
		}

	}

	public void keyReleased(KeyEvent e) {
		if(KeyEvent.getKeyText(e.getKeyCode()) == "Upp"){
			hupp = false;
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()) == "Nedpil"){
			hner = false;
		}
		else if (e.getKeyCode() == 87 ) {
			vupp = false;
		}
		else if (e.getKeyCode() == 83) {
			vner = false;
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==timer){
			if (hupp && HögerY>0) {
				HögerY=HögerY-5;
			}
			if (hner && HögerY+RektHöjd<getHeight()){
				HögerY=HögerY+5;
			}
			if (vupp && VänsterY>0){
				VänsterY=VänsterY-5;
			}
			if (vner && VänsterY+RektHöjd<getHeight()){
				VänsterY=VänsterY+5;
			}
			frame.repaint();
			if (x+bredd>=HögerX) {
				if (y>=HögerY&&y<=HögerY+RektHöjd) {
					c= -hastighet;

				}
				else{
					GameOver("Vänster");
				}
			}
			else if (x<=VänsterX+RektBredd) {
				if (y>=VänsterY&&y<=VänsterY+RektHöjd) {
					hastighet++;
					c=hastighet;

				}
				else{
					GameOver("Höger");
				}

			}
			else if (y+höjd>=getHeight()) {
				d=-hastighet;
			}
			else if(y<=0){
				d=hastighet;
			}
			x=x+c;
			y=y+d;
			frame.repaint();
			HögerX=getWidth()-RektBredd-1;

		}
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (GameOver) {
			g2.setFont(new Font("", Font.BOLD, 50));
			g2.setColor(green);
			g2.drawString("Poäng till " + PoängTill, getWidth()/2-200, getHeight()/2);
			g2.drawString(Integer.toString(PoängVänster) + " - " + Integer.toString(PoängHöger), getWidth()/2-70, 40);
			g2.drawString(Integer.toString(PoängVänster) + " - " + Integer.toString(PoängHöger), px,py);
		}
		else {

			g2.drawOval(x, y, bredd, höjd);
			g2.fillOval(x, y, bredd, höjd);

			g2.drawRect(VänsterX, VänsterY, RektBredd, RektHöjd);
			g2.fillRect(VänsterX, VänsterY, RektBredd, RektHöjd);

			g2.drawRect(HögerX, HögerY, RektBredd, RektHöjd);
			g2.fillRect(HögerX, HögerY, RektBredd, RektHöjd);

			g2.setColor(green);
			g2.setFont(new Font("", Font.BOLD, 50));
			g2.drawString(Integer.toString(PoängVänster) + " - " + Integer.toString(PoängHöger), getWidth()/2-80, 40);

			g2.drawString(SpelareVänster, 0, 40);
			g2.drawString(SpelareHöger, getWidth()-250, 40);

		}
	}
	public void mouseDragged(MouseEvent e) {
		px=e.getX();
		py=e.getY();
		repaint();
		frame.repaint();

	}
	public void windowClosing(WindowEvent e) {
		timer.stop();
	}
	public void keyTyped(KeyEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void mouseMoved(MouseEvent e) {}
}
class FlappyGoJb extends JPanel implements KeyListener,WindowListener{
	private static final long serialVersionUID = 1L;

	private JFrame frame = new JFrame("FlappyGoJb");
	private int x,y,a,poäng=-1;
	private final int bredd=35;
	private Timer timer = new Timer(20, e -> check());
	private boolean mellanslag;

	FlappyGoJb(){

		setBackground(new Color(255, 255, 255));
		frame.addKeyListener(this);
		frame.setIconImage(fönsterIcon);
		frame.add(this);
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addWindowListener(this);
		timer.start();
		skapaHinder();
	}
	private void gameover(){
		timer.stop();
		y=getHeight()/2;
		mellanslag=false;
		if (showConfirmDialog(null, "Game over! Vill du spela igen?","Du förlorade!",YES_NO_OPTION,ERROR_MESSAGE)!=YES_OPTION) {
			frame.dispose();
			return;
		}
		poäng=-1;
		skapaHinder();
	}
	private void check() {
		int i =20;
		if (y+64<getHeight()) {
			y=y+5;
		}
		else 
			gameover();
		if (x+bredd<=0) {
			skapaHinder();
		}
		if (y<a+getHeight()&&i+64>x&&i<x+bredd||y+64>a+164+getHeight()&&i+64>x&&i<x+bredd) {
			gameover();
		}
		frame.repaint();
		if (mellanslag) {
			if (y>0) {
				y=y-15;
			}
			else {
				gameover();
			}
		}

		x=x-8;
		frame.repaint();
	}
	private void skapaHinder(){

		timer.start();
		a=random.nextInt(getHeight());
		if (a<getHeight()*0.1 || a+164>getHeight()*0.9) {
			skapaHinder();
			return;
		}
		poäng++;
		a=a-getHeight();
		x=getWidth();
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		g2.drawImage(fönsterIcon, 20,y,null);
		g2.fillRect(x, a, bredd,getHeight());
		g2.drawRect(x, a, bredd,getHeight());
		g2.fillRect(x, a+164+getHeight(), bredd,getHeight());
		g2.drawRect(x, a+164+getHeight(), bredd,getHeight());
		g2.setFont(typsnitt);
		g2.setColor(green);
		g2.drawString(Integer.toString(poäng), getWidth()/2, 50);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_SPACE) {
			mellanslag=true;
		}
	}
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_SPACE) {
			mellanslag=false;
		}
	}
	public void windowClosed(WindowEvent e) {
		timer.stop();
	}
	public void keyTyped(KeyEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {}
}
@SuppressWarnings("serial")
class Studsa extends JPanel implements ActionListener{
	JFrame frame = new JFrame("Studsa");
	Timer timer = new Timer(1, this),
			timer2 = new Timer(5, this);
	int x=1,y=1,a=5,b=5,c=2,d=2,r=100,g=255,bl=25;
	public Studsa(){

		frame.setSize(1250,1000);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(fönsterIcon);
		frame.add(this);
		frame.setUndecorated(true);
		frame.getContentPane().setBackground(white);
		frame.setVisible(true);
		timer.start();
		timer2.start();
	}
	public void paintComponent(Graphics ag){
		Graphics2D g2 = (Graphics2D)ag;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


		g2.setColor(new Color(r, g, bl));

		g2.drawOval(x, y, a, b);
		g2.fillOval(x, y, a, b);

	}

	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == timer2){
			r=random.nextInt(255);
			g=random.nextInt(255);
			bl=random.nextInt(255);
			repaint();
		}
		if (e.getSource()==timer) {


			if (x+a>=frame.getWidth()) {
				c=-c;
			}
			if (x<=0) {
				c=-c;
			}
			if (y+b>=frame.getHeight()) {
				d=-d;
			}
			if (y<=0) {
				d=-d;
			}
			x=x+c;
			y=y+d;
			repaint();
		}
	}
}
class Ping{
	int x;
	public Ping(String string){
		GoJbFrame frame = new GoJbFrame();
		JTextArea textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);

		frame.add(scrollPane);
		for(int i = 10;0<i;i--){
			try {

				new Thread(){
					public void run() {

						System.err.println(x++);

						try {
							Runtime.getRuntime().exec("ping " + string + " -l 65500 -n 1000").getInputStream();

						} catch (IOException e) {
							e.printStackTrace();
						}

					};
				}.start();

				////			BufferedReader inputStream = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("ping " + string + " -l 65500 -n 1000").getInputStream()));
				//
				//			String s;
				//			// reading output stream of the command
				//			while ((s = inputStream.readLine()) != null) {
				//				System.out.println(s);
				//			}

			} catch (Exception e) {e.printStackTrace();}
		}
	}
}
class Glosor{
	private GoJbFrame frame = new GoJbFrame("Glosor"),frame2 = new GoJbFrame("Ställ in",false,3);
	private JLabel label = new JLabel(),rättLabel = new JLabel(),felLabel = new JLabel(),label2 = new JLabel();
	private JTextField textField = new JTextField();
	private JMenuBar bar = new JMenuBar();
	private JMenu instMenu = new JMenu("Inställningar");
	private JMenuItem instItem = new JMenuItem("Ställ in glosor");
	private JCheckBoxMenuItem ljudItem = new JCheckBoxMenuItem("Ljud", Bild("/images/Sound.jpg"),Boolean.valueOf(prop.getProperty("glosljud", "true")));
	private JButton button = new JButton("Spara"),button2 = new JButton("Byt plats"),button3 = new JButton("Rensa"),button4 = new JButton("Starta om");
	private String[] språk1 = new String[28],
			språk2 = new String[språk1.length];
	private JTextField[] ettFields = new JTextField[språk1.length],
			tvåFields = new JTextField[språk1.length];
	private int index,fel,rätt;
	private ArrayList<String> ettList,tvåList;
	private JPanel panel = new JPanel(new BorderLayout()),restartPanel = new JPanel(new FlowLayout());
	private Timer timer = new Timer(2000, e -> {stoppatimer();label2.setText("");});
	private void stoppatimer(){timer.stop();}

	Glosor() {
		ljudItem.addActionListener(e -> {prop.setProperty("glosljud", Boolean.toString(ljudItem.isSelected()));sparaProp("Ljud");});
		frame.setSize(frame.getWidth(), 300);
		frame.setJMenuBar(bar);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(panel,BorderLayout.NORTH);
		frame.add(label,BorderLayout.CENTER);
		frame.add(textField,BorderLayout.SOUTH);
		frame2.add(new JLabel("Språk1:"));
		frame2.add(new JLabel("Språk2:"));
		for (int i = 0; i < språk1.length; i++) {
			språk1[i]=prop.getProperty("språk1[" + i + "]");
			språk2[i]=prop.getProperty("språk2[" + i + "]");
			ettFields[i]=new JTextField();
			tvåFields[i]=new JTextField();
			frame2.add(ettFields[i]);
			frame2.add(tvåFields[i]);
			ettFields[i].setPreferredSize(new Dimension(60, ettFields[i].getPreferredSize().height));
			ettFields[i].setText(språk1[i]);
			tvåFields[i].setText(språk2[i]);
			ettFields[i].addFocusListener(f); 
			tvåFields[i].addFocusListener(f);
		}	
		frame2.setLocation(frame2.getX(), 5);
		frame2.setUndecorated(true);
		frame2.add(button2);
		frame2.add(button);
		frame2.add(button3);
		frame2.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		frame2.setLayout(new GridLayout(språk1.length+3, 2));
		frame2.pack();
		panel.add(rättLabel,BorderLayout.WEST);
		panel.add(restartPanel,BorderLayout.CENTER);
		panel.add(felLabel,BorderLayout.EAST);
		panel.add(label2,BorderLayout.SOUTH);
		label2.setVerticalAlignment(BOTTOM);
		label2.setHorizontalAlignment(CENTER);
		label2.setFont(typsnitt);
		label2.setForeground(RED);
		rättLabel.setFont(typsnitt);
		felLabel.setFont(typsnitt);
		rättLabel.setText("Rätt: 0");
		felLabel.setText("Fel: 0");
		label.setFont(typsnitt);
		label.setVerticalAlignment(BOTTOM);
		label.setHorizontalAlignment(CENTER);
		textField.setFont(typsnitt);
		restartPanel.add(Box.createHorizontalGlue());
		restartPanel.add(Box.createHorizontalGlue());
		restartPanel.add(button4);
		bar.add(instMenu);
		instMenu.add(instItem);
		instMenu.add(ljudItem);
		instItem.addActionListener(e -> {frame2.setVisible(true);frame.setEnabled(false);});
		button.addActionListener(e -> spara());
		button2.addActionListener(e -> byt());
		button3.addActionListener(e -> rens());
		button4.addActionListener(e -> spara());
		textField.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e){}public void keyReleased(KeyEvent e){}public void keyPressed(KeyEvent e){
				if (e.getKeyCode()==10)kolla(); if (e.getKeyCode()==82&&e.getModifiersEx()==128)spara();
			}});
		frame.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {}public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {}public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {
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
		});
		blanda();
		sätt();
	}
	private FocusListener f = new FocusListener() {
		public void focusLost(FocusEvent e) {}
		public void focusGained(FocusEvent e) {
			((JTextComponent) e.getSource()).selectAll();
		}
	};
	private void byt() {
		String[] strings = new String[ettFields.length];
		for (int i = 0; i < ettFields.length; i++) {
			strings[i]=ettFields[i].getText();
			ettFields[i].setText(tvåFields[i].getText());
			tvåFields[i].setText(strings[i]);
		}
		sätt();
	}
	private void spara() {
		for (int i = 0; i < ettFields.length; i++) {
			språk1[i]=ettFields[i].getText();
			språk2[i]=tvåFields[i].getText();
			prop.setProperty("språk1[" + i + "]", språk1[i]);
			prop.setProperty("språk2[" + i + "]", språk2[i]);
		}
		sparaProp("Glosor");
		frame2.dispose();
		frame.setEnabled(true);
		frame.toFront();
		textField.setText("");
		blanda();
		sätt();
	}
	private void rens() {
		if (showConfirmDialog(null, "Är du säker på att du vill rensa allt?","Rensa",YES_NO_OPTION,WARNING_MESSAGE)==YES_OPTION) {
			for (int i = 0; i < ettFields.length; i++) {
				ettFields[i].setText("");
				tvåFields[i].setText("");
			}
		}
	}
	private void kolla() {
		if (textField.getText().equals(tvåList.get(index))){
			rätt++;
			rättLabel.setText("Rätt: " + rätt);
			if (ljudItem.isSelected()) {
				spelaLjud("/images/tada.wav");
			}
			ettList.set(index, "");
		}
		else {
			if (ljudItem.isSelected()) {
				spelaLjud("/images/Wrong.wav");
			}
			fel++;
			felLabel.setText("Fel: " + fel);
			label2.setText(tvåList.get(index));
			timer.start();
		}
		index++;
		textField.setText("");
		sätt();
	}
	private void sätt() {
		System.out.println(index);
		if (index==språk1.length) {
			if (rätt==0&&fel==0) {
				return;
			}
			label.setText("");
			if (fel!=0) {
				String[] strings = {"Starta om","Öva på felaktiga"};
				JOptionPane optionPane = new JOptionPane("Rätt: " + rätt + "\nFel: " + fel, INFORMATION_MESSAGE, DEFAULT_OPTION, Bild("/images/Java-icon.png"), strings, strings[1]);
				JDialog dialog = optionPane.createDialog("Resultat");
				dialog.setIconImage(fönsterIcon);
				dialog.setLocation(frame.getX()+50, frame.getY()-50);
				dialog.setVisible(true);
				if (optionPane.getValue().equals(optionPane.getOptions()[1])) {
					index=0;
					fel=0;
					felLabel.setText("Fel: " + fel);
				}
				else {
					blanda();
				}
			}
			else {
				showMessageDialog(frame, "Alla rätt!","Resultat", INFORMATION_MESSAGE, Bild("/images/Done.jpg"));
				blanda();
			}
		}
		if (!ettList.get(index).equals("")&&!ettList.get(index).equals(null)) {
			label.setText(ettList.get(index));
			frame.repaint();
		}
		else {
			index++;
			sätt();
		}
	}
	private void blanda(){
		index=0;
		rätt=0;
		fel=0;
		rättLabel.setText("Rätt: 0");
		felLabel.setText("Fel: 0");
		ettList = new ArrayList<String>(Arrays.asList(språk1)); 
		tvåList = new ArrayList<String>(Arrays.asList(språk2)); 
		Long seed = System.currentTimeMillis();
		Collections.shuffle(ettList,new Random(seed));
		Collections.shuffle(tvåList,new Random(seed));
	}
}

@SuppressWarnings("serial")
class RörandeMojäng extends JPanel implements MouseMotionListener, WindowListener, KeyListener, ActionListener{


	JFrame frame = new JFrame("Det här är försök  " + qq),
			Vinst = new JFrame("Grattis!");

	JLabel textlabel = new JLabel();

	JTextArea textruta = new JTextArea();

	int r,g,b;

	JMenu menu = new JMenu("Arkiv"),
			menu1 = new JMenu("Redigera"),
			menu2 = new JMenu("Hjälp"),
			ÖppnaProgram = new JMenu("Öppna Program");
	JMenuItem item = new JMenuItem("Om"),
			item1 = new JMenuItem("Source kod"),
			item2 = new JMenuItem("Hjälp"),
			Minirknare = new JMenuItem("Miniräknare"),
			Betyg = new JMenuItem("Betyg"),
			OrginalFönster = new JMenuItem("RörandeMojäng"),
			Återställ = new JMenuItem("Återställ kvadrat"),
			Pong = new JMenuItem("Pong"),
			Maze = new JMenuItem("Maze"),
			Snake = new JMenuItem("Snake"),
			Mouse = new JMenuItem("Mouse"),
			impossible = new JMenuItem("Impossible"),
			ticTacToe = new JMenuItem("Tic Tac Toe"),
			lösenord = new JMenuItem("Lösenord"),
			färg = new JMenuItem("Skapa färg"),
			avsluta = new JMenuItem("Avsluta"),
			morse = new JMenuItem("Morse"),
			Random = new JMenuItem("Random"),
			klocka = new JMenuItem("Klocka"),
			binära = new JMenuItem("Binär omvandlare"),
			draOchSläpp = new JMenuItem("Dra & Släpp"),
			sök = new JMenuItem("Sök"),
			reggplåtar = new JMenuItem("Reggplåtar");
	JMenuBar bar = new JMenuBar();

	Clip clip;
	static int qq = 1;
	static int x = 800;
	static int yy = 900;
	static int y = 300;
	static int ii = 0;

	public RörandeMojäng(){
		antalFönster++;

		Vinst.setLocationRelativeTo(null);
		Vinst.setSize(190, 100);
		Vinst.add(textruta);

		textruta.setFont(new Font("",Font.BOLD, 20));
		textruta.setText("Grattis! Du vann \nefter " + qq + " försök! :D");
		textruta.setEditable(false);
		textlabel.setOpaque(false);

		bar.add(ÖppnaProgram);
		bar.add(menu);
		bar.add(menu2);
		bar.add(menu1);
		menu1.add(Återställ);
		menu2.add(item);
		menu2.add(item1);
		menu2.add(item2);

		ÖppnaProgram.add(Mouse);
		ÖppnaProgram.add(Minirknare);
		ÖppnaProgram.add(Betyg);
		ÖppnaProgram.add(OrginalFönster);
		ÖppnaProgram.add(Pong);
		ÖppnaProgram.add(Maze);
		ÖppnaProgram.add(impossible);
		ÖppnaProgram.add(ticTacToe);
		ÖppnaProgram.add(lösenord);
		ÖppnaProgram.add(färg);
		ÖppnaProgram.add(avsluta);
		ÖppnaProgram.add(morse);
		ÖppnaProgram.add(Random);
		ÖppnaProgram.add(klocka);
		ÖppnaProgram.add(binära);
		ÖppnaProgram.add(draOchSläpp);
		ÖppnaProgram.add(sök);
		ÖppnaProgram.add(reggplåtar);
		ÖppnaProgram.add(Snake);


		Mouse.addActionListener(this);
		Pong.addActionListener(this);
		Återställ.addActionListener(this);
		item1.addActionListener(this);
		Minirknare.addActionListener(this);
		Betyg.addActionListener(this);
		OrginalFönster.addActionListener(this);
		Maze.addActionListener(this);

		impossible.addActionListener(this);
		ticTacToe.addActionListener(this);
		lösenord.addActionListener(this);
		färg.addActionListener(this);
		avsluta.addActionListener(this);
		morse.addActionListener(this);
		Random.addActionListener(this);
		klocka.addActionListener(this);
		Snake.addActionListener(e -> {new Snake();frame.dispose();});
		binära.addActionListener(e -> {new ToBinary();frame.setVisible(false);});
		draOchSläpp.addActionListener(e -> {new DraOchSläpp();frame.dispose();});		
		sök.addActionListener(e -> {new Sök();frame.dispose();});
		reggplåtar.addActionListener(e -> {new ReggPlåtar();frame.dispose();});

		frame.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		frame.setBackground(gray);
		frame.setForeground(pink);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setIconImage(fönsterIcon);
		frame.setSize(1845, 800);
		frame.addMouseMotionListener(this);
		frame.addKeyListener(this);
		frame.setResizable(false);
		frame.setJMenuBar(bar);
		frame.addWindowListener(this);
		frame.getContentPane().add(new RörandeMojäng3());
		frame.setVisible(true);

		robot.mouseMove(200, 100);
	}

	public void mouseMoved(MouseEvent e) {

		x = e.getX() -3;
		y = e.getY() -80;

		if (x < 150 && y > 425){if (ii == 0){
			new GameOver();
			frame.dispose();
		}}
		if (x > 950 && x < 1100 && y > 150 && y < 550){if (ii == 0){
			new GameOver();
			frame.dispose();
		}}

		if (x > 1106 && x < 1264 && y < 495){
			new GameOver();
			frame.dispose();
		}

		if (y > 500 && y < 651 && x > 155 && x < 1205){
			new GameOver();
			frame.dispose();
		}

		if (y < -500){if (ii == 0){
			new GameOver();frame.dispose();
		}}
		if (y > 697){ if (ii == 0){
			new GameOver();
			frame.dispose();
		}}

		if (x > 1200 && y < 5){
			new GameOver();
			frame.dispose();
		}

		if (x < 450 && y > 375 && y < 495 ){
			new GameOver();
			frame.dispose();
		}
		System.out.println("Musen rör sig på: " + e.getX()  + ", " + e.getY());
		if ( x == 50) {
			if (y == 50){

				System.exit(0);	
			}
		}
	}

	public void keyTyped(KeyEvent e){}

	public void keyPressed(KeyEvent e) { 


		if (KeyEvent.getKeyText(e.getKeyCode()) == "Vänsterpil") {
			x = x - 1;
			System.out.println("1 pixel till vänster");
			frame.repaint();}
		if (KeyEvent.getKeyText(e.getKeyCode()) == "Högerpil") {
			x = x + 1;
			System.out.println("1 pixel till höger");
			frame.repaint();}
		if (KeyEvent.getKeyText(e.getKeyCode()) == "Upp") {
			y = y - 1;
			System.out.println("1 pixel upp");
			frame.repaint();}
		if (KeyEvent.getKeyText(e.getKeyCode()) == "Nedpil") {
			y = y + 1;
			System.out.println("1 pixel ned");
			frame.repaint();}

		System.out.println(y + " , " + x);
		if (x < 150&&ii == 0){
			new GameOver();frame.dispose();
		}
		if (x > 1840&&ii == 0){
			new GameOver();frame.dispose();
		}
		if (y < 0&&ii == 0){
			new GameOver();frame.dispose();
		}
		if (y > 700&&ii == 0){
			new GameOver();frame.dispose();
		}
	}

	public void keyReleased(KeyEvent arg0) {

	}

	public void windowActivated(WindowEvent arg0) {

	}

	public void windowClosed(WindowEvent arg0) {
		antalFönster--;

	}

	public void windowClosing(WindowEvent arg0) { 

	}

	public void windowDeactivated(WindowEvent arg0) { 

	}

	public void windowDeiconified(WindowEvent arg0) {
		System.exit(3);
	}

	public void windowIconified(WindowEvent arg0) { 
		System.exit(3);
	}

	public void windowOpened(WindowEvent arg0) {

	}

	public void mouseDragged(MouseEvent arg0) { 

		x = arg0.getX() -18;
		y = arg0.getY() -72;

		System.out.println("Musen drar på:" + x + " , " + y);

		frame.repaint();

		if (x < 150 && y > 374){if (ii == 0){
			new GameOver();frame.dispose();
		}}
		if (x > 950 && x < 1100 && y > 150 && y < 550){if (ii == 0){
			new GameOver();frame.dispose();
		}}
		if (x > 650 && x < 790 && y > 550){

			spelaLjud("/images/tada.wav");

			Vinst.setVisible(true);
			frame.dispose();
		}
		if (x > 1106 && x < 1264 && y < 495){
			new GameOver();
			frame.dispose();
		}

		if (y > 500 && y < 651 && x > 155 && x < 1205){
			new GameOver();
			frame.dispose();
		}

		if (y < -500){if (ii == 0){
			new GameOver();frame.dispose();
		}}
		if (y > 697){ if (ii == 0){
			new GameOver();
			frame.dispose();
		}}

		if (x < 450 && y > 375 && y < 495 ){
			new GameOver();
			frame.dispose();
		}
	}

	public void actionPerformed(ActionEvent arg0) {


		if (arg0.getSource() == Mouse){
			frame.dispose();
			new Mouse();
		}

		else if (arg0.getSource() == Återställ){
			x = 300;
			y = 800;
			frame.revalidate();
			frame.repaint();
			Container contentPane = frame.getContentPane();
			contentPane.add(new RörandeMojäng3());

		}


		else if (arg0.getSource() == färg){
			new SkapaFärg();
			frame.dispose();
		}

		else if (arg0.getSource() == impossible){
			frame.dispose();
			new Impossible("HAHA!");
		}

		else if (arg0.getSource() == Maze){
			new Maze();
			frame.dispose();
		}

		else if (arg0.getSource() == avsluta){
			new Avsluta();
			frame.dispose();
		}
		else if (arg0.getSource() == morse) {
			frame.dispose();
			new Morse();
		}

		else if (arg0.getSource() == Random){
			new Randoms();
			frame.dispose();
		}
		else if (arg0.getSource() == klocka){
			new Klocka();
			frame.dispose();
		}

		else if (arg0.getSource() == Pong){
			new Pong();
			frame.dispose();

		}
		else if (arg0.getSource() == Snake){
			new Snake();
		}

		else if (arg0.getSource() == Minirknare){
			new Miniräknare();
		}

		else if (arg0.getSource() == ticTacToe){
			new TicTacToe();
			frame.dispose();
		}

		else if (arg0.getSource() == lösenord){
			new Login();
			frame.dispose();
		}

		else if (arg0.getSource() == Betyg){
			new Merit();
		}

		else if (arg0.getSource() == OrginalFönster){
			new RörandeMojäng();
		}

		else if (arg0.getSource() == item1){
			try
			{
				Runtime.getRuntime().exec("notepad.exe C:\\Users\\Glenn\\GoJb.java\\SourceKod.txt");
			} catch (Exception ex)
			{
				ex.printStackTrace();

			}
		}

	}


	class RörandeMojäng3 extends JPanel{
		int[] röd=new int[91],grön=new int[91],blå=new int[91];
		public void paintComponent (Graphics gr) {
			Graphics2D g2 = (Graphics2D) gr;

			g2.setColor(BLUE);
			g2.fillRect(0, 425, 150, 1000);

			g2.setColor(GREEN);
			gr.fill3DRect(1000, 200, 100, 350, false);

			g2.setColor(ORANGE);
			gr.fill3DRect(1155, 0, 110, 495, true);

			g2.setColor(MAGENTA);
			gr.fillRect(205, 550, 1000, 100);

			g2.setColor(WHITE);
			gr.drawRect(700, 650, 90, 90);

			g2.setColor(new Color(233,5,6));
			g2.fill3DRect(150, 425, 300, 70, true);

			g2.setColor(BLACK);
			g2.setFont(new Font("", Font.ROMAN_BASELINE,20));
			g2.drawString("Dra genom labyrinten till den \nfärgglada kvadraten för att vinna.\n Lycka till! :D", 300, 150);

			röd[röd.length-1] = random.nextInt(255);
			grön[röd.length-1] = random.nextInt(255);
			blå[röd.length-1] = random.nextInt(255);

			for (int i = 1; i <= röd.length-2; i++) {
				röd[i] = röd[i+1];
				grön[i] = grön[i+1];
				blå[i] = blå[i+1];

			}
			boolean h = true;
			int e=700,s=650;
			for (int i = röd.length-2; i >= 1; i--) {

				if (h) {
					h=false;

					g2.setColor(new Color(röd[i],grön[i],blå[i]));
					g2.drawRect(e, s, i, i);
					e++;
					s++;
				}
				else h=true;

			}

			g2.setColor(cyan);
			g2.fillRect(x, y, 50,50);
		}
	}
	static class GameOver implements ActionListener{
		JButton b1 = new JButton("Spela igen");
		JButton b2 = new JButton("Avsluta");
		JFrame ram = new JFrame("GAME OVER");
		public GameOver(){

			ram.setIconImage(fönsterIcon);
			ram.add(b1);
			ram.add(b2);
			ram.setVisible(true);
			ram.setSize(500, 500);
			ram.setLayout(new GridLayout(2, 1));

			RörandeMojäng.ii = 1;

			b1.addActionListener(this);
			b2.addActionListener(this);
			ram.setDefaultCloseOperation(3);
		}

		public void actionPerformed(ActionEvent arg0) {

			if (arg0.getSource() == b1){
				RörandeMojäng.qq = RörandeMojäng.qq + 1;
				RörandeMojäng.ii = 0;
				new RörandeMojäng();
				ram.dispose();
				RörandeMojäng.x = 800;
				RörandeMojäng.y = 300;

			}
			if (arg0.getSource() == b2) {
				System.exit(3);
			}
		}
	}
}


class Miniräknare implements ActionListener, KeyListener{

	JButton b1 = new JButton("1"),
			b2 = new JButton("2"),
			b3 = new JButton("3"),
			b4 = new JButton("4"),
			b5 = new JButton("5"),
			b6 = new JButton("6"),
			b7 = new JButton("7"),
			b8 = new JButton("8"),
			b9 = new JButton("9"),
			b0 = new JButton("0"),
			b10 = new JButton("+"),
			b11 = new JButton("-"),
			b12 = new JButton("*"),
			b13 = new JButton("/"),
			b14 = new JButton("=");

	JTextArea textruta = new JTextArea();

	JLabel räknesätt = new JLabel(),
			summa = new JLabel();


	public Miniräknare(){

		JFrame frame = new JFrame();

		frame.setLayout(new BorderLayout(5,5));
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);

		JPanel fönster = new JPanel();

		frame.add(textruta,BorderLayout.NORTH);
		b0.addActionListener(this);
		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);
		b5.addActionListener(this);
		b6.addActionListener(this);
		b7.addActionListener(this);
		b8.addActionListener(this);
		b9.addActionListener(this);
		b10.addActionListener(this);
		b11.addActionListener(this);
		b12.addActionListener(this);
		b13.addActionListener(this);
		b14.addActionListener(this);

		fönster.setLayout(new GridLayout(5,3,5,5));
		frame.addKeyListener(this);
		fönster.add(b1);
		fönster.add(b2);
		fönster.add(b3);
		fönster.add(b4);
		fönster.add(b5);
		fönster.add(b6);
		fönster.add(b7);
		fönster.add(b8);
		fönster.add(b9);
		fönster.add(b10);
		fönster.add(b0);
		fönster.add(b11);
		fönster.add(b12);
		fönster.add(b13);
		fönster.add(b14);

		textruta.setEditable(true);


		frame.add(fönster,BorderLayout.CENTER);
		frame.pack();

		frame.add(summa);
		frame.add(räknesätt);


	}
	public void actionPerformed(ActionEvent e) {


		if (e.getSource()==b0){textruta.append("0");}
		if (e.getSource()==b1){textruta.append("1");}
		if (e.getSource()==b2){textruta.append("2");}
		if (e.getSource()==b3){textruta.append("3");}
		if (e.getSource()==b4){textruta.append("4");}
		if (e.getSource()==b5){textruta.append("5");}
		if (e.getSource()==b6){textruta.append("6");}
		if (e.getSource()==b7){textruta.append("7");}
		if (e.getSource()==b8){textruta.append("8");}
		if (e.getSource()==b9){textruta.append("9");}
		if (e.getSource()==b10){
			RäknaUt();
			räknesätt.setText("+");
		}
		if (e.getSource()==b11){textruta.append("-");}
		if (e.getSource()==b12){textruta.append("*");}
		if (e.getSource()==b13){textruta.append("/");}
		if (e.getSource()==b14){textruta.append("=");}

		int a = 0;System.out.println(a);
		try {
			a = Integer.parseInt(textruta.getText());
		} catch (Exception e1) {
			a = 0;
		}

	}

	private void RäknaUt() {
		int a,b;
		try {
			a = Integer.parseInt(summa.getText());
		} catch (Exception e) {
			a = 0;
		}

		try {
			b = Integer.parseInt(textruta.getText());
		} catch (Exception e) {

			b = 0;
		}

		if (räknesätt.getText() == "+"){
			summa.setText(Integer.toString(a+b));
		}
		else if (räknesätt.getText() == "-") {
			summa.setText(Integer.toString(a-b));
		}

		else if (räknesätt.getText() == "*") {
			summa.setText(Integer.toString(a*b));
		}
		else if (räknesätt.getText() == "/") {
			summa.setText(Double.toString(a/b));
		}
		else {
			summa.setText(Double.toString(a+b));
		}
		textruta.setText(null);

	}

	public void keyPressed(KeyEvent  e) {

		System.out.println(e.getKeyCode());
		String fj = String.valueOf(e.getKeyChar());
		System.out.println(fj);
		if (e.getKeyCode() == 49||
				e.getKeyCode() == 50||
				e.getKeyCode() == 48||
				e.getKeyCode() == 49||
				e.getKeyCode() == 51||
				e.getKeyCode() == 52||
				e.getKeyCode() == 53||
				e.getKeyCode() == 54||
				e.getKeyCode() == 55||
				e.getKeyCode() == 56||
				e.getKeyCode() == 57||
				e.getKeyCode() == 58||
				e.getKeyCode() == 59||
				e.getKeyCode() == 521||
				e.getKeyCode() == 45||
				e.getKeyCode() == 222||
				e.getKeyCode() == 97||
				e.getKeyCode() == 98||
				e.getKeyCode() == 99||
				e.getKeyCode() == 100|
				e.getKeyCode() == 101||
				e.getKeyCode() == 102||
				e.getKeyCode() == 103||
				e.getKeyCode() == 104||
				e.getKeyCode() == 105||
				e.getKeyCode() == 107||
				e.getKeyCode() == 111||
				e.getKeyCode() == 106||
				e.getKeyCode() == 109){
			textruta.append(fj);
		}
		if (e.getKeyCode() == 8){
			String text = textruta.getText();
			textruta.setText(text.substring(0, text.length() - 1));
		}
		if (e.getKeyCode() == 10){
			textruta.append("=");
		}

	}

	public void keyReleased(KeyEvent arg0) {

	}

	public void keyTyped(KeyEvent e) {

	}

}

class Merit implements  ActionListener{

	String [] ämnen ={"Biologi", "Fysik", "Kemi", "Teknik","Matte", 
			"Historia", "Geografi", "Sammhällskunskap", "Religon", 
			"Slöjd", "ModernaSpråk", "Idrott", "HemOchKonsumentkunskap",
			"Musik", "Bild","Svenska","Enelska"};

	JFrame frame = new JFrame(),Resultat = new JFrame("Resultat"),betygFrame  = new JFrame();

	JLabel resultatlabel = new JLabel("",CENTER);

	JButton tillbaka = new JButton("Tillbaka"),
			Börja = new JButton("Börja"),
			Avsluta = new JButton("Avsluta"),
			A = new JButton("A"),
			B = new JButton("B"),
			C = new JButton("C"),
			D = new JButton("D"),
			E = new JButton("E"),
			F = new JButton("F");

	double x = 0;

	int i;


	public Merit(){

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setSize(200,200);
		frame.setIconImage(fönsterIcon);

		A.setSize(30, 30);
		B.setSize(30, 30);
		C.setSize(30,30);
		D.setSize(30,30);
		E.setSize(30,30);
		F.setSize(30,30);

		Börja.setPreferredSize(new Dimension(30, 30));

		A.addActionListener(this);
		B.addActionListener(this);
		C.addActionListener(this);
		D.addActionListener(this);
		E.addActionListener(this);
		F.addActionListener(this);

		frame.add(Börja);

		Börja.addActionListener(e -> {betygFrame.setVisible(true);frame.dispose();});

		betygFrame.add(A);
		betygFrame.add(B);
		betygFrame.add(C);
		betygFrame.add(D);
		betygFrame.add(E);
		betygFrame.add(F);
		betygFrame.setLayout(new GridLayout(3,2));
		betygFrame.setSize(300, 250);
		betygFrame.setLocationRelativeTo(null);
		betygFrame.setIconImage(fönsterIcon);

		betygFrame.setTitle(ämnen[i++]);
	}
	public void actionPerformed(ActionEvent e) { 
		if (e.getSource() == A){
			x = x + 20;
		}
		else if (e.getSource() == B){
			x = x + 17.5;
		}
		else if (e.getSource() == C){
			x = x + 15;

		}
		else if (e.getSource() == D){
			x = x + 12.5;

		}
		else if (e.getSource() == E){
			x = x + 10;

		}
		else if (e.getSource() == F){
			x = x + 0;
		}
		try {
			betygFrame.setTitle(ämnen[i++]);
		} catch (ArrayIndexOutOfBoundsException e2) {
			betygFrame.dispose();
			Resultat.setVisible(true);
			Resultat.setLayout(new BorderLayout());
			Resultat.add(resultatlabel,BorderLayout.CENTER);
			Resultat.setSize(betygFrame.getSize());
			Resultat.setLocationRelativeTo(null);
			Resultat.setIconImage(fönsterIcon);

			resultatlabel.setFont(new Font("fsgadh",Font.BOLD,45));
			resultatlabel.setHorizontalTextPosition(RIGHT);
			resultatlabel.setText(String.valueOf(x));

			if (x < 100){	
				spelaLjud("/images/dusuger.wav");
			}
			else {
				spelaLjud("/images/tada.wav");
			}
		}
	}
}


@SuppressWarnings("serial")
class Pong extends JPanel implements ActionListener{
	Timer timer = new Timer(5,this);
	JFrame frame = new JFrame();
	int x,y,a=1,b,c=0;
	public Pong(){

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setSize(200, 200);
		frame.add(this);
		frame.setIconImage(fönsterIcon);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		timer.start();

	}

	public void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(BLUE);
		g2.fillRect(x, y, 25, 25);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer){

			if (x +43== frame.getWidth()){
				a = -1;
				b = 0;
			}

			if (y==0&& x +43 == frame.getWidth()){
				a = 0;
				b = -1;



			}
			if (y==0&&x==0){
				a=1;
				b=0;
				System.err.println("öiuds");
			}

			y = y + a;
			x = x + b;

			frame.repaint();
			if (y+72 == frame.getHeight()){
				a = 0 ;
				b =1;

			}
		}

	}
}


@SuppressWarnings("serial")
class Maze extends JPanel implements ActionListener, MouseMotionListener{

	JFrame  level1 = new JFrame("Level 1");

	static JFrame startframe = new JFrame("Börja");

	JButton börja = new JButton("Start");


	int x = 3;
	int y = 600;	
	public Maze(){

		startframe.setVisible(true);
		startframe.add(börja);
		startframe.setSize(80, 80);
		startframe.setLocation(740, 290);
		startframe.setResizable(false);
		startframe.setDefaultCloseOperation(3);
		startframe.setIconImage(fönsterIcon);

		börja.addActionListener(this);

		level1.setBackground(BLACK);
		level1.setSize(418, 430);
		level1.setLocationRelativeTo(null);
		level1.setResizable(false);
		level1.add(this);	       
		level1.addMouseMotionListener(this);
		level1.repaint();
		level1.revalidate();
		level1.setResizable(false);
		level1.setDefaultCloseOperation(3);
		level1.setIconImage(fönsterIcon);

	}

	public void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		g2.setColor(white);
		g2.fillRect(0, 350, 350, 50);

		g2.fillRect(0, 0, 200, 40);

		g2.fillRect(0, 0, 50, 2000);

		g2.fillRect(300, 50, 50, 300);

		g2.fillRect(170, 50, 170, 50);

		g2.fillRect(140, 50, 50, 190);

		g2.setColor(CYAN);
		g2.fillRect(150, 200, 30, 30);   

		g2.setColor(RED);
		g2.fillRect(x, y, 25, 25);

	}

	public void actionPerformed(ActionEvent arg0) {


		if (arg0.getSource() == börja){
			level1.setVisible(true);
			startframe.dispose();
		}

	}

	public void mouseDragged(MouseEvent arg0) {

	}

	public void mouseMoved(MouseEvent e) {

		x = e.getX() - 17;
		y = e.getY() - 45;
		System.out.println("Musen rör sig på: " + x  + ", " + y);
		level1.repaint();

		if (e.getY()> 60 &&  e.getY()< 95 && e.getX()> 42 && e.getX()< 470){
			level1.dispose();
			startframe.setVisible(true);
		}
		if (x > 25 && x < 140 && y < 350 && y > 60){
			level1.dispose();
			startframe.setVisible(true);
		}

		if (y < 350 && y > 215 && x > 25 && x < 300){
			level1.dispose();
			startframe.setVisible(true);
		}

		if (y < 350 && y > 75 && x < 300 && x > 165){
			level1.dispose();
			startframe.setVisible(true);	
		}

		if (x > 325){
			level1.dispose();
			startframe.setVisible(true);
		}
		if (x > 175 && y < 50){
			level1.dispose();
			startframe.setVisible(true);
		}

		if (y > 195 && y < 205 && x > 60 && x < 190){
			level1.dispose();
			new level2();
		}
	}
	class level2 extends JPanel implements MouseMotionListener{
		int y, x;
		JFrame level2 = new JFrame("Level 2");



		public level2(){



			level2.setBackground(BLACK);
			level2.setSize(418, 430);
			level2.setLocationRelativeTo(null);
			level2.setResizable(false);
			level2.addMouseMotionListener(this);
			level2.repaint();
			level2.revalidate();
			level2.setVisible(true);
			level2.add(this);
			level2.setResizable(false);
			level2.setDefaultCloseOperation(3);
			level2.setIconImage(fönsterIcon);


		}

		public void paintComponent (Graphics g) {
			Graphics2D g2 = (Graphics2D) g;

			g2.setColor(white);
			g2.fillRect(140, 190, 45, 45);

			g2.fillRect(145, 195, 35, 180);

			g2.fillRect(150, 340, 250, 35);

			g2.fillRect(365, 341, 35, -100);

			g2.fillRect(230, 241, 150, 35);

			g2.fillRect(230, 100, 35, 171);

			g2.fillRect(230, 100, 169, 35);

			g2.fillRect(364, 10, 35, 105);

			g2.setColor(CYAN);
			g2.fillRect(369, 17, 25, 25);   

			g2.setColor(RED);
			g2.fillRect(x, y, 20, 20);



		}


		public void mouseDragged(MouseEvent arg0) {}

		public void mouseMoved(MouseEvent e) {


			x = e.getX() - 17;
			y = e.getY() - 45;
			System.out.println("Musen rör sig på: " + x  + ", " + y);
			level2.repaint();


			if (y > 355 || x > 380 || y > 256 && y < 340 && x > 165 && x < 365||
					y < 339 && x > 165 && x < 230|| y < 241 && y > 115 && x > 245|| x < 364 && y < 100||
					x < 140||x < 145 && y > 215||y > 215 && y < 340 && x > 160 && x < 230||y < 190 && x < 230){
				Maze.startframe.setVisible(true);
				level2.dispose();
			}
			if (y < 22 && x > 363 && x < 379){
				level2.dispose();
				new level3();
			}


		}
	}
	class level3 extends JPanel implements MouseMotionListener{
		int y, x;
		JFrame level3 = new JFrame("Level 3");


		public level3(){

			level3.setBackground(BLACK);
			level3.setSize(418, 430);
			level3.setLocationRelativeTo(null);
			level3.setResizable(false);
			level3.addMouseMotionListener(this);
			level3.repaint();
			level3.revalidate();
			level3.setVisible(true);
			level3.add(this);
			level3.setResizable(false);
			level3.setDefaultCloseOperation(3);
			level3.setIconImage(fönsterIcon);

		}

		public void paintComponent (Graphics g) {
			Graphics2D g2 = (Graphics2D) g;

			g2.setColor(white);
			g2.fillRect(270, 5, 137, 37);

			g2.fillRect(270, 5, 32, 130);

			g2.fillRect(70, 135, 232, 27);

			g2.fillRect(70, 135, 23, 100);

			g2.fillRect(70, 235, 300, 23);

			g2.fillRect(353, 235, 23, 68);

			g2.fillRect(163, 370, 30, 50);

			g2.fillRect(172, 283, 200, 20);

			g2.fillRect(170, 283, 17, 150);

			g2.setColor(CYAN);
			g2.fillRect(170, 375, 17, 17);   

			g2.setColor(RED);
			g2.fillRect(x, y, 12, 12);



		}

		public void mouseDragged(MouseEvent arg0) {

		}

		public void mouseMoved(MouseEvent e) {

			x = e.getX() - 17;
			y = e.getY() - 45;
			System.out.println("Musen rör sig på: " + x  + ", " + y);
			level3.repaint();

			if (x > 290 && y < 235 && y > 30||y < 235 && y > 150 && x > 82||
					x > 395|| x > 364 && y > 30|| y < 310 && y > 291 && x > 173||y > 246 && 
					y < 283 && x < 353|| x < 170 && y > 246 && y < 310||x < 270 && y < 135){

				level3.dispose();
				Maze.startframe.setVisible(true);
			}

			if(y > 310){

				level3.dispose();
				new Mål();
			}
		}
	}

	class Mål{

		JFrame frame = new JFrame("Haha");

		public Mål(){
			frame.add(new JLabel(Bild("/images/Bild.jpg")));
			frame.pack();
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(3);
			frame.setIconImage(fönsterIcon);

			spelaLjud("/images/Ljud.wav");

		}
	}
}
@SuppressWarnings("serial")
class Impossible extends JPanel implements ActionListener,KeyListener, MouseInputListener,MouseWheelListener{

	JFrame frame = new JFrame();
	Timer timer = new Timer(1, this),
			timer200 = new Timer(200, this);
	String string = new String();
	int x,y,r,g,b,textbredd=100,texthöjd=50;
	String a;
	public Impossible(String textString){

		Image image = Bild("/images/Nope.png").getImage();

		Cursor c = getDefaultToolkit().createCustomCursor(
				image , new Point(frame.getX(),frame.getY()), "img");
		a=textString;

		frame.setIconImage(fönsterIcon);
		frame.setCursor(c);
		frame.setSize(getDefaultToolkit().getScreenSize());
		frame.add(this);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.add(this);
		frame.setDefaultCloseOperation(0);
		frame.addMouseMotionListener(this);
		frame.addKeyListener(this);
		frame.addMouseListener(this);
		frame.addMouseWheelListener(this);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);

		timer.start();
		timer200.start();

	}

	public void mouseDragged(MouseEvent arg0) {

		robot.mouseMove(frame.getWidth()/2, frame.getHeight()/2);
	}


	public void mouseMoved(MouseEvent e) {


		robot.mouseMove(frame.getWidth()/2, frame.getHeight()/2);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == timer){

			frame.toFront();

			//			if(frame.isVisible()){
			//				robot.mouseMove(frame.getWidth()/2, frame.getHeight()/2);
			//			}

		}

		if (arg0.getSource() == timer200){

			x = random.nextInt(frame.getWidth()-textbredd);
			y = random.nextInt(frame.getHeight()-texthöjd);
			r = random.nextInt(255);
			g = random.nextInt(255);
			b = random.nextInt(255);
		}
	}


	public void keyPressed(KeyEvent arg0) {}
	public void keyReleased(KeyEvent arg0) {}

	int nr;
	public void keyTyped(KeyEvent arg0) {
		if (nr==0) {

			if(arg0.getKeyChar() == 'Å'){
				nr++;

			}
		}
		else if (nr==1) {
			if(arg0.getKeyChar() == 'o'){
				nr++;

			}
			else {
				nr=0;
			}

		}
		else if (nr==2) {
			if(arg0.getKeyChar() == 'Ö'){
				System.exit(3);

			}
			else {
				nr=0;
			}
		}
		repaint();

	}

	public void mouseClicked(MouseEvent arg0) {
		repaint();
	}

	public void mousePressed(MouseEvent arg0) {
		repaint();
	}

	public void mouseReleased(MouseEvent arg0) {
		repaint();
	}

	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void paintComponent (Graphics gr) {
		Graphics2D g2 = (Graphics2D) gr;

		frame.setBackground(WHITE);

		g2.setColor(new Color(r,g,b));
		g2.setFont(new Font("dhghdg", Font.ITALIC, 30));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawString(a, x, y);
		textbredd = g2.getFontMetrics().stringWidth(a);
		texthöjd = g2.getFontMetrics().getHeight();

	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		repaint();
	}

}

class TicTacToe implements MouseInputListener, KeyListener, ActionListener{

	Timer timer = new Timer(50, this);

	JFrame frame = new JFrame("Tic Tac Toe"),
			Vinst = new JFrame(),
			tur = new JFrame();

	String sträng = new String();

	JLabel[] label = new JLabel[10];
	JLabel vinstlabel = new JLabel(),
			turLabel = new JLabel();

	ImageIcon 	o = Bild("/images/O.png"),
			x = Bild("/images/X.png");

	int a,å;

	public TicTacToe(){

		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridLayout(3,3,3,3));
		timer.start();

		for(int i = 1;i < label.length; i++){
			label[i] = new JLabel();
			label[i].setBackground(WHITE);
			label[i].setOpaque(true);
			label[i].addMouseListener(this);
			frame.add(label[i]);
		}
		frame.getContentPane().setBackground(BLACK);

		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(3);

		frame.addKeyListener(this);
		Vinst.addKeyListener(this);

		Vinst.setUndecorated(true);

		vinstlabel.setSize(500, 100);

		tur.setLocation(frame.getWidth() + 205, frame.getHeight()/2 - 65);
		tur.setSize(500, 75);
		tur.setAlwaysOnTop(true);

		turLabel.setHorizontalAlignment(JLabel.CENTER);
		turLabel.setFont(new Font("dslf",Font.ROMAN_BASELINE,50));
		tur.add(turLabel);
		turLabel.setForeground(WHITE);
		turLabel.setOpaque(true);

		turLabel.setBackground(BLACK);

		tur.setUndecorated(true);

		tur.setVisible(true);

		Vinst.setLocation(frame.getWidth()+205,frame.getHeight()+270);
		Vinst.setSize(500, 100);

		vinstlabel.setHorizontalAlignment(JLabel.CENTER);
		vinstlabel.setFont(new Font("dslf",Font.ROMAN_BASELINE,30));
		Vinst.add(vinstlabel);
		vinstlabel.setForeground(BLACK);

		vinstlabel.setOpaque(true);

	}

	public void mouseClicked(MouseEvent e) {
		for (int i = 1; i < label.length; i++) {
			if(e.getSource() == label[i]&&label[i].getIcon() == null){
				if (a==0) {
					X(i);
				}
				else if (a==1) {
					O(i);
				}
			}
		}
	}

	public void X(int intlabel) {
		label[intlabel].setIcon(x);
		//		repaint();
		a = 1;
	}

	public void O(int intlabelO) {
		label[intlabelO].setIcon(o);
		//		repaint();
		a = 0;
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseDragged(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == timer){

			if (a == 2){
				turLabel.setText("Tryck r för restart");	
			}

			if (a == 0){
				turLabel.setText("Nu spelar X");
			}

			if (a == 1){
				turLabel.setText("Nu spelar O");
			}


			frame.setAlwaysOnTop(true);

			if(label[1].getIcon() == o||label[1].getIcon() == x){
				if(label[2].getIcon() == o||label[2].getIcon() == x){
					if(label[3].getIcon() == o||label[3].getIcon() == x){
						if(label[4].getIcon() == o||label[4].getIcon() == x){
							if(label[5].getIcon() == o||label[5].getIcon() == x){
								if(label[6].getIcon() == o||label[6].getIcon() == x){
									if(label[7].getIcon() == o||label[7].getIcon() == x){
										if(label[8].getIcon() == o||label[8].getIcon() == x){
											if(label[9].getIcon() == o||label[9].getIcon() == x){

												Vinst.setVisible(true);
												//												repaint();
												vinstlabel.setHorizontalAlignment(JLabel.CENTER);
												vinstlabel.setText("Det blev lika");

												a = 2;
											}
										}
									}
								}
							}
						}
					}
				}
			}		
			frame.setLocationRelativeTo(null);

			if (label[4].getIcon() == o){
				if (label[5].getIcon() == o){
					if(label[6].getIcon() == o){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("O vann");
						å = 2;
					}

				}
			}

			if (label[2].getIcon() == o){
				if (label[5].getIcon() == o){
					if (label[8].getIcon() == o){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("O vann");
						å = 2;
					}
				}
			}


			if (label[3].getIcon() == o){
				if(label[6].getIcon() == o){
					if (label[9].getIcon() == o){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("O vann");
						å = 2;
					}
				}
			}


			if (label[7].getIcon() == o){
				if(label[5].getIcon() == o){
					if (label[3].getIcon() == o){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("O vann");
						å = 2;
					}
				}
				if (label[8].getIcon() == o){
					if (label[9].getIcon() == o){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("O vann");
						å = 2;
					}
				}
			}


			if (label[1].getIcon() == o){
				if (label[5].getIcon() == o){
					if (label[9].getIcon() == o){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("O vann");
						å = 2;
					}
				}
				if (label[4].getIcon() == o){
					if (label[7].getIcon()== o){
						Vinst.setVisible(true);
						//						repaint();
						a = 2;
						vinstlabel.setText("O vann");
						å = 2;
					}
				}
				if(label[2].getIcon() == o){
					if(label[3].getIcon() == o){


						Vinst.setVisible(true);
						//						repaint();
						vinstlabel.setText("O vann");

						å = 2;
						a = 2;

					}
				}
			}	
			if (label[4].getIcon() == x){
				if (label[5].getIcon() == x){
					if(label[6].getIcon() == x){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("X vann");
						å = 1;
					}

				}
			}

			if (label[2].getIcon() == x){
				if (label[5].getIcon() == x){
					if (label[8].getIcon() == x){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("X vann");
						å = 1;
					}
				}
			}


			if (label[3].getIcon() == x){
				if(label[6].getIcon() == x){
					if (label[9].getIcon() == x){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("X vann");
						å = 1;
					}
				}
			}


			if (label[7].getIcon() == x){
				if(label[5].getIcon() == x){
					if (label[3].getIcon() == x){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("X vann");
						å = 1;
					}
				}
				if (label[8].getIcon() == x){
					if (label[9].getIcon() == x){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("X vann");
						å = 1;
					}
				}
			}


			if (label[1].getIcon() == x){
				if (label[5].getIcon() == x){
					if (label[9].getIcon() == x){
						Vinst.setVisible(true);
						//					repaint();
						a = 2;
						vinstlabel.setText("X vann");
						å = 1;
					}
				}
				if (label[4].getIcon() == x){
					if (label[7].getIcon()== x){
						Vinst.setVisible(true);
						//						repaint();
						a = 2;
						vinstlabel.setText("X vann");
						å = 1;
					}
				}
				if(label[2].getIcon() == x){
					if(label[3].getIcon() == x){


						Vinst.setVisible(true);
						//						repaint();
						vinstlabel.setText("X vann");
						å = 1;
						a = 2;

					}
				}
			}	
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyPressed(KeyEvent e) {

		System.err.println(e.getKeyCode() + "   " + e.getKeyChar());

		if(e.getKeyCode() == 82){


			label[9].setIcon(null);
			label[8].setIcon(null);
			label[7].setIcon(null);
			label[6].setIcon(null);
			label[5].setIcon(null);
			label[4].setIcon(null);
			label[3].setIcon(null);
			label[2].setIcon(null);
			label[1].setIcon(null);
			if (å == 2){
				a = 0;
			}
			else if (å == 1){
				a = 1;
			}

			Vinst.setVisible(false);

			frame.repaint();

		}

	}

	public void keyReleased(KeyEvent e) {}
}
@SuppressWarnings("serial")
class SkapaFärg extends JPanel implements ActionListener{

	JFrame frame = new JFrame("Skapa en egen färg");

	int xb,xg,xr;

	JPanel Panel = new JPanel(),
			panel = new JPanel(),
			paneliPanel = new JPanel();

	JLabel red = new JLabel("Red"),
			green = new JLabel("Green"),
			blue = new JLabel("Blue");

	JSlider r,
	g,
	b;

	Timer timer = new Timer(1, this);

	Color y;

	public  SkapaFärg() {

		xr = random.nextInt(255);
		xg = random.nextInt(255);
		xb = random.nextInt(255);

		r = new JSlider(JSlider.HORIZONTAL,0,255,xr);
		g = new JSlider(JSlider.HORIZONTAL,0,255,xg);
		b = new JSlider(JSlider.HORIZONTAL,0,255,xb);

		y = new Color(22,123,213);

		r.setPaintTicks(true);
		r.setPaintLabels(true);
		r.setMajorTickSpacing(40);
		r.setMinorTickSpacing(5);

		g.setPaintTicks(true);
		g.setPaintLabels(true);
		g.setMajorTickSpacing(40);
		g.setMinorTickSpacing(5);

		b.setPaintTicks(true);
		b.setPaintLabels(true);
		b.setMajorTickSpacing(40);
		b.setMinorTickSpacing(5);

		setForeground(BLUE);

		setOpaque(true);

		red.setFont(new Font("luyya",Font.BOLD,40));
		green.setFont(new Font("luyya",Font.BOLD,40));
		blue.setFont(new Font("luyya",Font.BOLD,40));

		red.setForeground(RED);
		green.setForeground(GREEN);
		blue.setForeground(BLUE);

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(red);
		panel.add(r);
		panel.add(green);
		panel.add(g);
		panel.add(blue);
		panel.add(b);

		Panel.setLayout(new BorderLayout());
		Panel.setPreferredSize(new Dimension(250, 300));
		Panel.add(paneliPanel,BorderLayout.CENTER);
		Panel.add(Box.createRigidArea(new Dimension(25,25)),BorderLayout.NORTH);
		Panel.add(Box.createRigidArea(new Dimension(25,25)),BorderLayout.SOUTH);
		Panel.add(Box.createRigidArea(new Dimension(25,25)),BorderLayout.EAST);
		Panel.add(Box.createRigidArea(new Dimension(25,25)),BorderLayout.WEST);

		paneliPanel.setBackground(new Color(r.getValue(),g.getValue(), b.getValue()));
		paneliPanel.setOpaque(true);

		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		frame.add(panel);
		frame.add(Panel);
		frame.pack();
		frame.setIconImage(fönsterIcon);
		frame.setDefaultCloseOperation(3);
		frame.repaint();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		timer.start();

	}

	public void actionPerformed(ActionEvent e) {

		if (timer == e.getSource()){
			paneliPanel.setBackground(new Color(r.getValue(),g.getValue(), b.getValue()));
			String hexColour = Integer.toHexString(paneliPanel.getBackground().getRGB() & 0xffffff);
			hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
			System.out.println("#" + hexColour);
			System.out.println(Color.decode("#" + hexColour));
		}	
	}
}

class Avsluta implements ActionListener{

	JButton b1 = new JButton("Stäng av", Bild("/images/icon.png"));
	JButton b2 = new JButton("Logga ut", Bild("/images/icon2.png"));
	JButton b3 = new JButton("Starta om", Bild("/images/icon3.png"));
	JButton b4 = new JButton("Viloläge", Bild("/images/icon4.png"));
	JSlider s1 = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);

	JFrame f1 = new JFrame("GoJbs Shutdown");

	JPanel p1 = new JPanel();

	public Avsluta(){	

		s1.setPaintTicks(true);
		s1.setPaintLabels(true);
		s1.setMajorTickSpacing(10);
		s1.setMinorTickSpacing(1);

		p1.setLayout(new GridLayout(2,2));
		p1.add(b1);
		p1.add(b2);
		p1.add(b3);
		p1.add(b4);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
		b4.addActionListener(this);

		b1.setHorizontalTextPosition(JButton.CENTER);
		b2.setHorizontalTextPosition(JButton.CENTER);
		b3.setHorizontalTextPosition(JButton.CENTER);
		b4.setHorizontalTextPosition(JButton.CENTER);

		b1.setFont(new Font("Hej", Font.BOLD, 40));
		b2.setFont(new Font("Hej", Font.BOLD, 40));
		b3.setFont(new Font("Hej", Font.BOLD, 40));
		b4.setFont(new Font("Hej", Font.BOLD, 40));

		b1.setToolTipText("Stänger av datorn");

		f1.add(p1);
		f1.add(s1);
		f1.setResizable(true);
		f1.setAlwaysOnTop(true);
		f1.setDefaultCloseOperation(3);
		f1.getContentPane().setLayout(new BoxLayout(f1.getContentPane(),BoxLayout.Y_AXIS));
		f1.setIconImage(fönsterIcon);
		f1.pack();
		f1.setLocationRelativeTo(null);
		f1.setVisible(true);
	}


	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1){ 
			try {
				Runtime.getRuntime().exec("C:\\windows\\system32\\shutdown.exe -s -t " + s1.getValue() + " -c \"Hejdå\"");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.exit(0);	
		}

		else if (e.getSource() == b2){

			try {
				Runtime.getRuntime().exec("C:\\windows\\system32\\shutdown.exe -l");
			} catch (Exception e1) {

				e1.printStackTrace();
			}
			System.exit(0);	
		}
		else if (e.getSource() == b3){
			try {
				Runtime.getRuntime().exec("C:\\windows\\system32\\shutdown.exe -r -t " + s1.getValue() + " -c \"Hejdå\"");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			System.exit(0);	
		}
		else if (e.getSource() == b4){
			try {
				Runtime.getRuntime().exec("C:\\windows\\system32\\shutdown.exe -h");
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}	
	}
}
class Morse implements KeyListener,ActionListener, MouseListener {

	JFrame frame = new JFrame("Morse");
	String Filnamn = "/images/iMorse.wav";
	JLabel button = new JLabel("Pip");
	Clip clip;
	Timer timer = new Timer(300, this);
	int x,y;
	public Morse(){

		button.setFont(typsnitt);
		button.addMouseListener(this);
		button.addKeyListener(this);
		button.setBackground(black);
		button.setForeground(white);
		button.setOpaque(true);
		button.setHorizontalAlignment(CENTER);
		frame.setIconImage(fönsterIcon);
		frame.addKeyListener(this);
		frame.add(button);
		frame.pack();
		frame.setSize(frame.getWidth()+50, frame.getHeight());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);


		try {

			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(getClass().getResource(Filnamn)));

		} catch (Exception e) {
			((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
			showMessageDialog(null, "Filen: \"" + Filnamn + "\" hittades inte", "Ljud", ERROR_MESSAGE);
		}

	}

	public void keyPressed(KeyEvent arg0) {

		timer.start();

		clip.loop(9*999);
	}

	public void keyReleased(KeyEvent arg0) {


		if (x == 0){
			System.err.println(".");
		}

		x = 0;

		timer.stop();
		vänta(100);

		clip.close();
		try {


			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(getClass().getResource(Filnamn)));

		} catch (Exception e) {
			((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
			showMessageDialog(null, "Filen: \"" + Filnamn + "\" hittades inte", "Ljud", ERROR_MESSAGE);
		}
	}

	public void keyTyped(KeyEvent arg0) {
	}

	public void actionPerformed(ActionEvent e) {

		if (x == 0){
			timer.stop();
			x = 1;
			System.err.println("-");
		}

	}

	public void mouseClicked(MouseEvent arg0) {
	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {


		timer.start();

		clip.loop(9999);

	}

	public void mouseReleased(MouseEvent arg0) {

		if (x == 0){
			System.err.println(".");
		}
		x = 0;

		timer.stop();
		vänta(100);

		clip.close();
		try {


			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(getClass().getResource(Filnamn)));

		} catch (Exception e) {
			((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
			showMessageDialog(null, "Filen: \"" + Filnamn + "\" hittades inte", 
					"Ljud", ERROR_MESSAGE);
		}

	}
}

class Randoms implements ActionListener{

	JLabel label = new JLabel();

	JPanel panel1 = new JPanel(),
			panel2 = new JPanel();

	JButton button = new JButton("Start");

	GoJbFrame frame = new GoJbFrame("Random");

	JSlider slider = new JSlider();

	String tid = new SimpleDateFormat("ss : MM").format(new Date());


	long z,x,i;

	Timer timer = new Timer(1, this);



	public Randoms(){

		frame.setLayout(new BorderLayout());

		panel1.setPreferredSize(new Dimension(250,250));
		panel2.setPreferredSize(new Dimension(250,250));

		frame.add(panel1,BorderLayout.NORTH);
		frame.add(panel2,BorderLayout.SOUTH);

		panel1.setLayout(new BorderLayout());
		panel1.add(slider,BorderLayout.WEST);
		panel1.add(button,BorderLayout.CENTER);

		panel2.setLayout(new BorderLayout());
		panel2.add(label,BorderLayout.CENTER);
		label.setVerticalTextPosition(CENTER);
		label.setHorizontalAlignment(CENTER);
		label.setFont(typsnitt);
		button.addActionListener(this);

		timer.start();

		System.out.println(tid);

	}
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource() == button){
			ranidom();
		}
		if (arg0.getSource() == timer){
			System.err.println(System.currentTimeMillis());
		}
	}
	public void ranidom() {
		long y = System.currentTimeMillis();

		if (i == 0){
			z = System.currentTimeMillis();
		}
		else if (i == 1) {
			z = 14065;
		}
		else if (i == 2) {
			z = 465656;
		}
		else if (i == 3) {
			z = 856746;
		}
		else if (i == 4) {
			z = 12876575;
		}
		else if (i == 5) {
			z = 177657690;
			i = 0;
		}

		x = y-z;
		i++;


		label.setText(Long.toString(x));
	}

}
class Klocka implements ActionListener{

	Timer timer = new Timer(100 ,this),
			timer2 = new Timer(1, this);

	GoJbFrame frame = new GoJbFrame("Klocka");

	JLabel label = new JLabel(),
			label2 = new JLabel();

	int milli,sek,min;

	String string;

	public Klocka() {

		frame.setLayout(new GridLayout(2,1));

		frame.add(label);
		frame.add(label2);

		timer.start();
		timer2.start();

		label.setFont(typsnitt);
		label2.setFont(typsnitt);

		label.setHorizontalAlignment(CENTER);
		label.setVerticalAlignment(CENTER);

		label2.setHorizontalAlignment(CENTER);
		label2.setVerticalAlignment(CENTER);

		string = "0";

	}
	@Override
	public void actionPerformed(ActionEvent arg0) {

		if (arg0.getSource() == timer){
			milli++;
			if(milli == 10){
				milli=0;
				sek++;
			}
			if(sek == 60){
				sek=0;
				min++;
				string = "0";
			}
			if(sek == 10){
				string = "";
			}

			label.setText("<html>" + Integer.toString(min) + " : " + string + Integer.toString(sek) + " : " + Integer.toString(milli));
		}
		if (arg0.getSource() == timer2){
			String tid = new SimpleDateFormat("HH:mm:ss").format(new Date());
			label2.setText(tid);
		}
	}
}
class PainInTheAss{
	int a;
	public PainInTheAss(){

		for(int i = 0; i < 5; i++){
			a++;
			new Timer(1, e -> System.err.println("ft  " + a)).start();
			new JFrame().setVisible(true);

			i = 0;
		}

	}

}
class ToBinary implements ActionListener{

	GoJbFrame frame = new GoJbFrame();

	JSlider slider = new JSlider(0, 2147483647, 1);

	JTextField text = new JTextField(3);

	JButton button = new JButton();

	Timer timer = new Timer(3, this);

	int foo = Integer.parseInt("111111111", 2);

	public ToBinary(){

		frame.setLayout(new GridLayout(3,1));

		frame.add(slider);
		frame.add(text);
		frame.add(button);

		text.setText("0");

		timer.start();

		button.addActionListener(e -> System.out.println(Integer.toBinaryString(Integer.parseInt(text.getText()))));

		System.out.println(foo);
		System.err.println(Integer.MAX_VALUE);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {



		try {
			Integer.parseInt(text.getText());
			text.setForeground(black);
			text.setBackground(white);

		} catch (NumberFormatException e) {
			text.setForeground(red);
			text.setBackground(black);
		}

		button.setText(Integer.toString(slider.getValue()));
		System.out.println(Integer.toBinaryString(slider.getValue()));
		frame.revalidate();
		frame.repaint();
		button.revalidate();
		button.repaint();

	}
}

class DraOchSläpp extends JPanel implements MouseInputListener{

	/**
	 * 
	 */

	private static final long serialVersionUID = -2889156010528648564L;
	GoJbFrame frame = new GoJbFrame();
	JLayeredPane layeredPane = new JLayeredPane();
	JLabel label1 = new JLabel(),
			label2 = new JLabel(),
			label3 = new JLabel();

	int x,a;

	public DraOchSläpp(){
		frame.setLayeredPane(layeredPane);

		frame.setBackground(white);

		layeredPane.add(label1);
		layeredPane.add(label2);
		layeredPane.add(this);
		setSize(frame.getSize());
		layeredPane.setLayer(this, 10);
		frame.setLayout(new BorderLayout());
		//		frame.getGlassPane().add(this);
		label1.setOpaque(true);
		label1.setBackground(blue);
		label1.setSize(70,70);
		label1.setLocation(50,50);

		label2.setOpaque(true);
		label2.setBackground(red);
		label2.setSize(70,70);

		frame.addMouseMotionListener(this);
		label1.addMouseMotionListener(this);
		label2.addMouseMotionListener(this);

		frame.addMouseListener(this);
		label1.addMouseListener(this);
		label2.addMouseListener(this);
		System.out.println("ddf");
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getSource()==label1){
			if(SwingUtilities.isRightMouseButton(e)){
				System.err.println("redbfer");

				layeredPane.setLayer(label1, 25);
				layeredPane.setLayer(label2, 0);

				frame.revalidate();
				frame.repaint();

			}
		}

		if (e.getSource()==label2){
			if(SwingUtilities.isRightMouseButton(e)){
				System.err.println("bluee3");

				layeredPane.setLayer(label2, 25);
				layeredPane.setLayer(label1, 0);

				frame.revalidate();
				frame.repaint();

			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {


	}

	@Override
	public void mouseExited(MouseEvent e) {


	}

	@Override
	public void mousePressed(MouseEvent arg0) {


	}

	@Override
	public void mouseReleased(MouseEvent e) {

		//		if(a==1&&)

	}

	@Override
	public void mouseDragged(MouseEvent arg0) {

		//		if (arg0.getX() < label1.getX()+label1.getWidth() && arg0.getX() > label1.getX() && arg0.getY() > label1.getY()
		//				&& arg0.getY() < label1.getY()+label1.getHeight()){
		//		label1.setLocation(arg0.getX() - 25, arg0.getY() - 50);
		//		System.err.println("ssd");
		//		}

		if (arg0.getSource() == label1){
			label1.setLocation(arg0.getXOnScreen()-750,arg0.getYOnScreen()-330);
			frame.repaint();
			frame.revalidate();
		}
		if (arg0.getSource() == label2){
			label2.setLocation(arg0.getXOnScreen()-750,arg0.getYOnScreen()-330);
			frame.repaint();
			frame.revalidate();
		}

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}
	@Override
	protected void paintComponent(Graphics g) {
		//		super.paintComponent(g);

		g.drawLine(label1.getX(), label1.getY(), label2.getX(), label2.getY());
		g.drawLine(label1.getX()+label1.getWidth(), label1.getY(), label2.getX()+label2.getWidth(), label2.getY());
		g.drawLine(label1.getX(), label1.getY()+label1.getHeight(), label2.getX(), label2.getY()+label2.getHeight());
		g.drawLine(label1.getX()+label1.getWidth(), label1.getY()+label1.getHeight(), label2.getX()+label2.getWidth(), label2.getY()+label2.getHeight());

	}
}

class FullscreenExample {

	/** position of quad */
	float x = 400, y = 300;
	/** angle of quad rotation */
	float rotation = 0;

	/** time at last frame */
	long lastFrame;

	/** frames per second */
	int fps;
	/** last fps time */
	long lastFPS;

	/** is VSync Enabled */
	boolean vsync;

	public void start() {
		try {
			System.err.println(getClass().getProtectionDomain().getCodeSource().getLocation().toURI()+"windows_dll");
			ZipInputStream zipIn = new ZipInputStream(getClass().getProtectionDomain().getCodeSource().getLocation().openStream());
			ZipEntry entry = zipIn.getNextEntry();
			while (entry != null) {
				String filePath =  System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\GoJbsBraOchHa"+File.separator + entry.getName();
				if (entry.toString().startsWith("windows_dll/")) {
					System.err.println(entry.toString());
					if (!entry.isDirectory()) {
						
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						byte[] bytesIn = new byte[4096];
						int read = 0;
						while ((read = zipIn.read(bytesIn)) != -1) {
							out.write(bytesIn, 0, read);
						}
						FileOutputStream fos = new FileOutputStream(filePath);
						fos.write(out.toByteArray());
						fos.close();
					}
					else {
						File dir = new File(filePath);
						dir.mkdir();
					}
				}
				zipIn.closeEntry();
				entry = zipIn.getNextEntry();
	
			}
			zipIn.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	
		try {
			if (getClass().getResource("/" + getClass().getName().replace('.','/') + ".class").toString().startsWith("jar:"))
				System.setProperty("org.lwjgl.librarypath", new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\GoJbsBraOchHa\\windows_dll").getAbsolutePath());
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setTitle("_GoJbGame");
			//			Display.setIcon(new ByteBuffer[20]);
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		initGL(); // init OpenGL
		getDelta(); // call once before loop to initialise lastFrame
		lastFPS = getTime(); // call before loop to initialise fps timer

		while (!Display.isCloseRequested()) {
			int delta = getDelta();

			update(delta);
			renderGL();

			Display.update();
			Display.sync(60); // cap fps to 60fps
		}

		Display.destroy();
	}

	public void update(int delta) {
		// rotate quad
		rotation += 0.15f * delta;

		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) x -= 0.35f * delta;
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) x += 0.35f * delta;

		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) y += 0.35f * delta;
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) y -= 0.35f * delta;

		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_F) {
					setDisplayMode(800, 600, !Display.isFullscreen());
				}
				else if (Keyboard.getEventKey() == Keyboard.KEY_V) {
					vsync = !vsync;
					Display.setVSyncEnabled(vsync);
				}
			}
		}

		// keep quad on the screen
		if (x < 0) x = 0;
		if (x > 800) x = 800;
		if (y < 0) y = 0;
		if (y > 600) y = 600;

		updateFPS(); // update FPS Counter
	}

	/**
	 * Set the display mode to be used 
	 * 
	 * @param width The width of the display required
	 * @param height The height of the display required
	 * @param fullscreen True if we want fullscreen mode
	 */
	public void setDisplayMode(int width, int height, boolean fullscreen) {

		// return if requested DisplayMode is already set
		if ((Display.getDisplayMode().getWidth() == width) && 
				(Display.getDisplayMode().getHeight() == height) && 
				(Display.isFullscreen() == fullscreen)) {
			return;
		}

		try {
			DisplayMode targetDisplayMode = null;

			if (fullscreen) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i=0;i<modes.length;i++) {
					DisplayMode current = modes[i];

					if ((current.getWidth() == width) && (current.getHeight() == height)) {
						if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}

						// if we've found a match for bpp and frequence against the 
						// original display mode then it's probably best to go for this one
						// since it's most likely compatible with the monitor
						if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
								(current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else {
				targetDisplayMode = new DisplayMode(width,height);
			}

			if (targetDisplayMode == null) {
				System.out.println("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
				return;
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);

		} catch (LWJGLException e) {
			System.out.println("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
		}
	}

	/** 
	 * Calculate how many milliseconds have passed 
	 * since last frame.
	 * 
	 * @return milliseconds passed since last frame 
	 */
	public int getDelta() {
		long time = getTime();
		int delta = (int) (time - lastFrame);
		lastFrame = time;

		return delta;
	}

	/**
	 * Get the accurate system time
	 * 
	 * @return The system time in milliseconds
	 */
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}

	/**
	 * Calculate the FPS and set it in the title bar
	 */
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			//			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}

	public void initGL() {
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, 800, 0, 600, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	public void renderGL() {
		// Clear The Screen And The Depth Buffer
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

		// R,G,B,A Set The Color To Blue One Time Only
		GL11.glColor3f(0.5f, 0.5f, 1.0f);

		// draw quad
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glRotatef(rotation, 0f, 0f, 1f);
		GL11.glTranslatef(-x, -y, 0);

		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(x - 50, y - 50);
		GL11.glVertex2f(x + 50, y - 50);
		GL11.glVertex2f(x + 50, y + 50);
		GL11.glVertex2f(x - 50, y + 50);
		GL11.glEnd();
		GL11.glPopMatrix();
	}
}

class Sök implements ActionListener{

	GoJbFrame frame2 = new GoJbFrame("Sökruta",false,3),
			frame = new GoJbFrame("Sökresultat");

	JLabel label1 = new JLabel("Hej"),
			label2 = new JLabel("Hallå"),
			label3 = new JLabel("Tjenare");

	Timer timer = new Timer(10, this);

	int x = 4;

	JTextField text = new JTextField(5);

	public Sök(){

		frame2.setSize(300, 20);
		frame2.setUndecorated(true);
		frame2.setVisible(true);
		frame2.setLocation(frame.getX(), frame.getY() - 20);
		frame2.add(text);

		frame.setLayout(new GridLayout(x,1));
		frame.add(label1);
		frame.add(label2);
		frame.add(label3);

		label1.setBackground(blue);
		label2.setBackground(green);
		label3.setBackground(red);

		label1.setOpaque(true);
		label2.setOpaque(true);
		label3.setOpaque(true);

		timer.start();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		label1.setVisible(true);
		label2.setVisible(true);
		label3.setVisible(true);

		x = 4;

		if(!label1.getText().toLowerCase().contains(text.getText().toLowerCase())){
			label1.setVisible(false);
			x--;
			frame.repaint();
			frame.revalidate();
		}
		if(!label2.getText().toLowerCase().contains(text.getText().toLowerCase())){
			label2.setVisible(false);
			x--;
			frame.repaint();
			frame.revalidate();
		}
		if(!label3.getText().toLowerCase().contains(text.getText().toLowerCase())){
			label3.setVisible(false);
			x--;
			frame.repaint();
			frame.revalidate();
		}
	}
}

class Mailkorg implements ActionListener{

	static GoJbFrame frame = new GoJbFrame();

	static JTextField Mottagare = new JTextField("gojb@gojb.bl.ee"),
			Ämne = new JTextField();

	static JTextArea Innehåll = new JTextArea();

	static JLabel label1 = new JLabel("Till"),
			label2 = new JLabel("Ämne"),
			label3 = new JLabel("Innehåll");

	static JButton SkickaKnapp = new JButton("Skicka");

	JButton HämtaKnapp = new JButton("Hämta");

	JButton button3 = new JButton("Skicka");

	public Mailkorg(){

		frame.setLayout(new GridLayout(3,0));
		frame.add(HämtaKnapp);
		frame.add(button3);

		label1.setMinimumSize(new Dimension(30000,50));
		label2.setMinimumSize(new Dimension(30000,50));
		label3.setMinimumSize(new Dimension(30000,50));

		Mottagare.setMaximumSize(new Dimension(300000, 500));
		Ämne.setMaximumSize(new Dimension(30000,500));
		Innehåll.setPreferredSize(new Dimension(300, 500));
		Innehåll.setLineWrap(true);
		Innehåll.setWrapStyleWord(true);
		SkickaKnapp.addActionListener(this);
		HämtaKnapp.addActionListener(e -> {GoJbMail.Starta("Hämta");});
		button3.addActionListener(e -> {SkickaFönster("gojb@gojb.bl.ee","",false);});


		frame.revalidate();

	}
	public static void SkickaFönster(String from, String toWhom, Boolean focus){

		GoJbFrame skapa = new GoJbFrame("",true,1);

		Mailkorg.Mottagare.setText(from);
		Mailkorg.Ämne.setText(toWhom);
		Mailkorg.Innehåll.setText("");

		skapa.setLayout(new BoxLayout(skapa.getContentPane(), BoxLayout.Y_AXIS));
		skapa.add(label1);
		skapa.add(Mottagare);
		skapa.add(label2);
		skapa.add(Ämne);
		skapa.add(label3);
		skapa.add(Innehåll);
		skapa.add(SkickaKnapp);

		skapa.revalidate();
		if(focus==true){
			Innehåll.requestFocusInWindow();
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==SkickaKnapp){

			System.err.println("isuahodhfzc  fdgxgf");


			GoJbMail.Starta("Skicka");

		}

	}
}

class ReggPlåtar implements ActionListener{


	Random random1 = new Random(),
			r = new Random();

	Timer timer = new Timer(1,this);

	Boolean TimerKör = false;

	JButton button = new JButton("Start");

	int Low = 0;
	int High = 999;

	GoJbFrame frame = new GoJbFrame();


	char string;	

	public ReggPlåtar(){

		frame.add(button);

		button.addActionListener(this);

		System.err.println("");
	}

	public void Kör(){

		int ran = random1.nextInt((High-Low)-Low);

		char c = (char)(r.nextInt(26) + 'a');
		char c2 = (char)(r.nextInt(26) + 'a');
		char c3 = (char)(r.nextInt(26) + 'a');

		if (c == 'i'||c == 'v'||c == 'q') {
		}
		if (c == 'i'||c == 'v'||c == 'q') {
		}
		if (c == 'i'||c == 'v'||c == 'q') {
		}
		else {

			if (Integer.toString(ran).length()==2){
				System.err.println((c + "" + c2 + c3).toUpperCase() + " - "+ (ran)+"0");
				System.out.println("0");
			}
			else if (Integer.toString(ran).length()==1){
				System.err.println((c + "" + c2 + c3).toUpperCase() + " - "+ (ran)+"00");
				System.out.println("00");
			}
			else if (Integer.toString(ran).length()==3){
				System.err.println((c + "" + c2 + c3).toUpperCase() + " - "+ (ran));
				System.out.println("-");
			}

		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource()==button&&timer.isRunning()==false){
			button.setText("Stop");
			timer.start();
			System.err.println("d");
		}
		else if(e.getSource()==button&&timer.isRunning()==true){
			button.setText("Start");
			timer.stop();
		}

		if (e.getSource()==timer){
			Kör();
		}
	}
}
@SuppressWarnings("serial")
class Snake extends JPanel implements KeyListener, ActionListener, ComponentListener{
	private enum Spelläge {ONE,TWO,CLIENT,SERVER};
	Spelläge spelläge;
	private final int MAXIMUM = 101;
	private JFrame frame = new JFrame("Snake"), start = new JFrame("Start"),väntframe= new JFrame(),highFrame=new JFrame("Highscore");
	private final int startlängd=3;
	private final int pixelstorlek=Math.round(SKÄRM_SIZE.width/140);
	private int[] x=new int[MAXIMUM],y=new int[MAXIMUM],z=new int[MAXIMUM],q=new int[MAXIMUM];
	private int snakelängdx,snakelängdz=-1,pluppX,pluppY,s=1,a=1,svartPoäng,cyanPoäng,yLoc;
	private Timer timer = new Timer(100, this);
	private static String riktning = "ner",riktningz = "upp";
	private BufferedReader in;
	private PrintWriter out;
	private boolean förlust, paused=false,gameover;
	private JButton local = new JButton("Play two on this computer"),
			online = new JButton("Play online"),
			one = new JButton("Single Player");
	private String[] highscore = new String[6];
	private Socket socket;

	public Snake(){
		setOpaque(true);
		setBackground(white);
		setPreferredSize(new Dimension(pixelstorlek*50, pixelstorlek*50));
		setMaximumSize(new Dimension(pixelstorlek*50, pixelstorlek*50));
		setMinimumSize(new Dimension(pixelstorlek*50, pixelstorlek*50));

		frame.setLayout(new BorderLayout());
		frame.add(this,BorderLayout.CENTER);		
		frame.setIconImage(fönsterIcon);
		frame.setResizable(false);		
		frame.addKeyListener(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(autoListener);
		frame.getContentPane().setBackground(black);
		frame.addComponentListener(this);	
		frame.setDefaultCloseOperation(2);
		frame.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {}public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}public void windowDeactivated(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}public void windowClosed(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				try {
					socket.close();
				} catch (Exception e1) {}
			}
		});

		start.setSize(240,140);
		start.setLayout(new GridLayout(0,1));
		start.setLocationRelativeTo(null);
		start.add(one);
		start.add(local);
		start.add(online);
		start.setDefaultCloseOperation(2);
		start.setIconImage(fönsterIcon);
		start.setVisible(true);

		local.addActionListener(e -> {
			spelläge=Spelläge.TWO;
			start.dispose();
			frame.setVisible(true);
			paused=true;Restart();
			timer.stop();
			frame.revalidate();
			frame.repaint();
		});
		one.addActionListener(e -> {
			spelläge=Spelläge.ONE;
			start.dispose();
			frame.setVisible(true);
			Restart();
			highscore[0]= "";
			highscore[1]= prop.getProperty("Score1","0");
			highscore[2]= prop.getProperty("Score2","0");
			highscore[3]= prop.getProperty("Score3","0");
			highscore[4]= prop.getProperty("Score4","0");
			highscore[5]= prop.getProperty("Score5","0");
			highFrame.add(Scorepanel);
			highFrame.setSize(frame.getWidth()/2 ,frame.getHeight());
			highFrame.setIconImage(fönsterIcon);
			highFrame.setUndecorated(true);
			highFrame.setLocation(frame.getX()-highFrame.getWidth(),frame.getY());
			highFrame.setVisible(true);
			frame.toFront();
		});
		online.addActionListener(e -> online());

		frame.revalidate();
		frame.repaint();

	}
	void online(){

		String[]strings={"Server", "Klient"};
		int i = showOptionDialog(frame, "Server eller klient?", "Multiplayer", DEFAULT_OPTION, QUESTION_MESSAGE, null, strings, 0);
		if (i==0) {
			spelläge=Spelläge.SERVER;

			start.dispose();
			väntframe.add(new JLabel("Väntar på anslutning...",Bild("/images/loading.gif"),CENTER));
			väntframe.setLocationRelativeTo(null);
			väntframe.setAlwaysOnTop(true);
			väntframe.repaint();
			väntframe.getContentPane().setBackground(white);
			väntframe.setIconImage(fönsterIcon);
			väntframe.pack();
			väntframe.addWindowListener(autoListener);
			väntframe.setDefaultCloseOperation(2);
			väntframe.setVisible(true);
			new Thread(){
				@Override
				public void run() {
					try {
						@SuppressWarnings("resource")
						ServerSocket listener = new ServerSocket(11622);
						Socket socket = listener.accept();
						in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						out = new PrintWriter(socket.getOutputStream(), true);
					} catch (IOException e) {
						e.printStackTrace();
					}
					väntframe.dispose();
					paused=true;
					Restart();
					timer.stop();
					frame.setVisible(true);	
					frame.revalidate();
					frame.repaint();
					while (true) {
						try {
							String string = in.readLine();
							if(a==1){
								if(string.equals("vänster")){
									if (riktningz!="höger"&&riktningz!="vänster"){
										riktningz="vänster";
										a=0;
									}
								}
								else if(string.equals("höger")){
									if (riktningz!="höger"&&riktningz!="vänster"){
										riktningz="höger";
										a=0;
									}
								}
								else if(string.equals("upp")){
									if (riktningz!="ner"&&riktningz!="upp"){
										riktningz="upp";
										a=0;
									}
								}
								else if(string.equals("ner")){
									if (riktningz!="ner"&&riktningz!="upp"){
										riktningz="ner";
										a=0;
									}
								}
							}
							System.out.println(riktningz);

						} catch (Exception e) {
							System.err.println("Frånkopplad");
							break;
						}
					}
				}
			}.start();
		}
		else if (i==1) {
			start.dispose();
			spelläge=Spelläge.CLIENT;
			try {
				socket = new Socket(showInputDialog("Adress till server"), 11622);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			frame.setVisible(true);
			paused=false;
			förlust=false;
			new Thread(){
				public void run() {
					Scanner scanner = null;
					while (true) {
						try {
							scanner = new Scanner(in.readLine());
							String string = scanner.next();
							if (string.equals("B")) {
								snakelängdx=0;
							}
							else if (string.equals("C")) {
								snakelängdz=0;
							}
							while (scanner.hasNext()) {
								if (string.equals("B")) {
									x[++snakelängdx]=Integer.parseInt(scanner.next())*pixelstorlek;
									y[snakelängdx]=Integer.parseInt(scanner.next())*pixelstorlek;
								}
								else if (string.equals("C")) {
									z[++snakelängdz]=Integer.parseInt(scanner.next())*pixelstorlek;
									q[snakelängdz]=Integer.parseInt(scanner.next())*pixelstorlek;
								}
								else if (string.equals("P")) {
									pluppX=Integer.parseInt(scanner.next())*pixelstorlek;
									pluppY=Integer.parseInt(scanner.next())*pixelstorlek;
								}
							}
							synchronized (frame) {
								frame.repaint();
							}						
						}
						catch (Exception e) {
							System.err.println("Frånkopplad");
							break;
						}
					}
				}
			}.start();
		}
	}
	private void GameOver(){
		timer.stop();
		frame.repaint();
		frame.revalidate();
		förlust = true;

		gameover = true;

		((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();

		if (spelläge==Spelläge.ONE) {
			Scanner scanner = new Scanner(highscore[5]);
			int hs = scanner.nextInt();
			scanner.close();
			if (snakelängdx > hs) {
				highscore[5] = Integer.toString(snakelängdx) + " "
						+ showInputDialog("Skriv ditt namn");
				if (snakelängdx < 10) {
					highscore[5] = "0" + highscore[5];
				}

				Arrays.sort(highscore);
				String[] tillfälligscore = new String[6];
				tillfälligscore[1] = highscore[1];
				tillfälligscore[2] = highscore[2];
				tillfälligscore[3] = highscore[3];
				tillfälligscore[4] = highscore[4];
				tillfälligscore[5] = highscore[5];

				highscore[1] = tillfälligscore[5];
				highscore[2] = tillfälligscore[4];
				highscore[3] = tillfälligscore[3];
				highscore[4] = tillfälligscore[2];
				highscore[5] = tillfälligscore[1];

				prop.setProperty("Score1", (highscore[1]));
				prop.setProperty("Score2", (highscore[2]));
				prop.setProperty("Score3", (highscore[3]));
				prop.setProperty("Score4", (highscore[4]));
				prop.setProperty("Score5", (highscore[5]));
				highFrame.repaint();
			}
			sparaProp("Highscore i Snakespel");
			skrivHändelsetext(highscore[1]);
			skrivHändelsetext(highscore[2]);
			skrivHändelsetext(highscore[3]);
			skrivHändelsetext(highscore[4]);
			skrivHändelsetext(highscore[5]);
		}
		else {
			if(svartPoäng==10){
				JOptionPane.showMessageDialog(null, "Svart vann!");
				frame.dispose();
			}
			else if (cyanPoäng==10){
				JOptionPane.showMessageDialog(null, "Cyan vann!");
				frame.dispose();
			}
		}
	}
	private void Restart() {
		gameover=false;
		for (int i = 0; i < x.length; i++) {
			x[i]=0;
			q[i]=0;
			y[i]=0;
			z[i]=0;
		}
		repaint();

		int posx = random.nextInt(getWidth()/pixelstorlek)*pixelstorlek;
		int posy = random.nextInt(getHeight()/pixelstorlek)*pixelstorlek;

		int posyz = random.nextInt(getWidth()/pixelstorlek)*pixelstorlek;
		int posyq = random.nextInt(getHeight()/pixelstorlek)*pixelstorlek;

		if (posx>getWidth()*0.8||posx<getWidth()*0.2||posy>getHeight()*0.8||posy<getHeight()*0.2) {
			Restart();
		}
		else if (posyz>getWidth()*0.8||posyz<getWidth()*0.2||posyq>getHeight()*0.8||posyq<getHeight()*0.2) {
			Restart();
		}
		else{		
			String [] arr = {"upp", "ner", "höger", "vänster"};

			riktning=arr[random.nextInt(arr.length)];
			snakelängdx = startlängd;
			x[1]=posx;
			y[1]=posy;
			s=1;
			for (int i = 2; i < MAXIMUM; i++) {
				x[i]=-2;
				q[i]=-2;
				y[i]=-2;
				z[i]=-2;
			}
			if (spelläge!=Spelläge.ONE) {
				riktningz=arr[random.nextInt(arr.length)];
				snakelängdz = startlängd;
				z[1] = posyz;
				q[1] = posyq;
				a=1;
			}
			else {
				z[1]=-2;
			}
			PlaceraPlupp();
			förlust= false;
			repaint();
			timer.start();

		}
	}
	private void PlaceraPlupp(){

		pluppX = random.nextInt(getWidth()/pixelstorlek)*pixelstorlek;
		pluppY = random.nextInt(getHeight()/pixelstorlek)*pixelstorlek;
	}
	public void paintComponent(Graphics g1){
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D)g1;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if(paused){
			g.setColor(blue);
			g.setFont(new Font(null, 0, 25));
			g.drawString("Spelet pausat. Tryck på mellanslag för att fortsätta.", 10, getHeight()/2);
		}
		if (förlust) {
			g.setColor(red);
			g.setFont(new Font(null, 0, 25));
			g.drawString("Du förlorade!",10 , getHeight()/2-25);
			g.drawString("Tryck F2 eller \"R\" för \natt spela igen",10 , getHeight()/2);
		}
		g.setColor(red);
		g.drawOval(pluppX, pluppY, pixelstorlek-2, pixelstorlek-2);
		g.fillOval(pluppX, pluppY, pixelstorlek-2, pixelstorlek-2);
		if (spelläge==Spelläge.CLIENT) {
			for (int i = 1; i <= snakelängdx; i++) {
				g.setColor(black);

				g.drawRect(x[i], y[i], pixelstorlek-2, pixelstorlek-2);
				g.fillRect(x[i], y[i], pixelstorlek-2, pixelstorlek-2);
			}
			for (int i = 1; i <= snakelängdz; i++) {
				g.setColor(cyan);

				g.drawRect(z[i], q[i], pixelstorlek-2, pixelstorlek-2);
				g.fillRect(z[i], q[i], pixelstorlek-2, pixelstorlek-2);
			}
			return;
		}
		else if (spelläge==Spelläge.SERVER) {
			try {
				out.print("B ");
				for (int i = 1; i <= snakelängdx; i++) {
					out.print(x[i] / pixelstorlek + " " + y[i] / pixelstorlek
							+ " ");
				}
				out.println();
				out.print("C ");
				for (int i = 1; i <= snakelängdz; i++) {
					out.print(z[i] / pixelstorlek + " " + q[i] / pixelstorlek
							+ " ");
				}
				out.println();
				out.println("P " + pluppX / pixelstorlek + " " + pluppY
						/ pixelstorlek);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		g.setColor(black);
		g.drawRect(x[1], y[1], pixelstorlek-2, pixelstorlek-2);
		g.fillRect(x[1], y[1], pixelstorlek-2, pixelstorlek-2);
		for (int i = snakelängdx+1; i >= 2; i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
			g.drawRect(x[i], y[i], pixelstorlek-2, pixelstorlek-2);
			g.fillRect(x[i], y[i], pixelstorlek-2, pixelstorlek-2);
		}
		s=1;
		if (spelläge!=Spelläge.ONE) {
			g.setColor(cyan);
			g.drawRect(z[1], q[1], pixelstorlek - 2, pixelstorlek - 2);
			g.fillRect(z[1], q[1], pixelstorlek - 2, pixelstorlek - 2);
			for (int u = snakelängdz + 1; u >= 2; u--) {
				g.setColor(cyan);
				z[u] = z[u - 1];
				q[u] = q[u - 1];
				g.drawRect(z[u], q[u], pixelstorlek - 2, pixelstorlek - 2);
				g.fillRect(z[u], q[u], pixelstorlek - 2, pixelstorlek - 2);
			}
			a = 1;
		}
		else {
			if(y[1] < 45) {
				yLoc = y[1] + 40;
			}
			if (y[1] > 45){
				yLoc = y[1] - 20;
			}
			g.setColor(GREEN);
			g.setFont(typsnitt);
			g.drawString(Integer.toString(snakelängdx), x[1], yLoc);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==timer){
			if (x[1]==pluppX&&y[1]==pluppY) {
				PlaceraPlupp();
				snakelängdx++;
				((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.asterisk")).run();
				System.err.println(snakelängdx);
			}
			if (spelläge!=Spelläge.ONE) {
				if (z[1] == pluppX && q[1] == pluppY) {
					PlaceraPlupp();
					snakelängdz++;
					((Runnable) getDefaultToolkit().getDesktopProperty(
							"win.sound.asterisk")).run();
					System.err.println(snakelängdz);
				}
				if (riktningz == "ner") {
					q[1] = q[1] + pixelstorlek;
				} else if (riktningz == "upp") {
					q[1] = q[1] - pixelstorlek;
				} else if (riktningz == "höger") {
					z[1] = z[1] + pixelstorlek;
				} else if (riktningz == "vänster") {
					z[1] = z[1] - pixelstorlek;
				}
			}
			if (riktning=="ner") {
				y[1]=y[1]+pixelstorlek;
			}
			else if (riktning=="upp") {
				y[1]=y[1]-pixelstorlek;
			}
			else if (riktning=="höger") {
				x[1]=x[1]+pixelstorlek;
			}
			else if (riktning=="vänster") {
				x[1]=x[1]-pixelstorlek;
			}

			boolean körd = false;
			for (int i = 1; i <= snakelängdx; i++) {
				if ((x[1]==z[i]&&y[1]==q[i])||(i>1&&x[1]==x[i]&&y[1]==y[i])) {
					System.out.println("GameOver");
					GameOver();
					cyanPoäng++;
					System.err.println("Poäng till Cyan. (C) "+cyanPoäng+" - "+svartPoäng+" (S)");
					System.out.println("1");
					körd=true;
				}
			}
			for (int i = 1; i <= snakelängdz; i++) {
				if (körd) {
					break;
				}
				if (z[1]==x[i]&&q[1]==y[i]) {
					System.out.println("GameOver");
					GameOver();
					svartPoäng++;
					System.err.println("Poäng till Svart. (C) "+cyanPoäng+" - "+svartPoäng+" (S)");
					System.out.println("2");
				}
			}
			if (x[1]<0||x[1]+pixelstorlek>getWidth()||y[1]<0||y[1]+pixelstorlek>getHeight()) {
				cyanPoäng++;
				System.err.println("Poäng till Cyan. (C) "+cyanPoäng+" - "+svartPoäng+" (S)");
				GameOver();
			}
			if ((z[1]<0||z[1]+pixelstorlek>getWidth()||q[1]<0||q[1]+pixelstorlek>getHeight())&&spelläge!=Spelläge.ONE) {
				svartPoäng++;
				System.err.println("Poäng till Svart. (C) "+cyanPoäng+" - "+svartPoäng+" (S)");
				GameOver();
			}
			frame.repaint();
			frame.revalidate();
		}
	}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void componentResized(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}
	public void componentHidden(ComponentEvent e) {}
	public void keyPressed(KeyEvent e) {
		if (spelläge==Spelläge.CLIENT) {
			System.err.println(riktningz);
			System.err.println(e.getKeyCode());
			if(e.getKeyCode() == KeyEvent.VK_LEFT){
				out.println("vänster");
			}
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT){
				out.println("höger");
			}
			else if(e.getKeyCode() == KeyEvent.VK_UP){
				out.println("upp");
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN){
				out.println("ner");
			}
			return;
		}
		if (s==1) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT&&riktning!="höger"&&riktning!="vänster"){
				riktning="vänster";
				s=0;
			}
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT&&riktning!="höger"&&riktning!="vänster"){
				riktning="höger";
				s=0;
			}
			else if(e.getKeyCode() == KeyEvent.VK_UP&&riktning!="ner"&&riktning!="upp"){
				riktning="upp";
				s=0;
			}
			else if(e.getKeyCode() == KeyEvent.VK_DOWN&&riktning!="upp"&&riktning!="ner"){
				riktning="ner";
				s=0;
			}
		}
		if (a == 1&&spelläge==Spelläge.TWO) {
			if (e.getKeyCode() ==  KeyEvent.VK_A&&riktningz != "höger"&&riktningz!="vänster") {
				riktningz = "vänster";
				a = 0;
			}
			else if (e.getKeyCode() == KeyEvent.VK_D&&riktningz != "vänster"&&riktningz!="höger") {
				riktningz = "höger";
				a = 0;
			}
			else if (e.getKeyCode() == KeyEvent.VK_W&&riktningz != "ner"&&riktningz!="upp") {
				riktningz = "upp";
				a = 0;
			}
			else if (e.getKeyCode() == KeyEvent.VK_S&&riktningz != "upp"&&riktningz!="ner") {
				riktningz = "ner";
				a = 0;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_F2&&förlust==true||e.getKeyCode() == KeyEvent.VK_R&&förlust==true||
				e.getKeyCode() == KeyEvent.VK_F2&&paused==true||e.getKeyCode() == KeyEvent.VK_R&&paused==true){
			if (!timer.isRunning()) {
				Restart();
				paused=false;
			}
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE&&paused==true){
			if(!gameover){
				timer.start();
				paused=false;
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE&&paused==false){
			if(!gameover){
				timer.stop();
				paused=true;
			}
		}
	}
	public void componentMoved(ComponentEvent e) {
		highFrame.setLocation(frame.getX()-highFrame.getWidth(),frame.getY());
	}
	private JPanel Scorepanel = new JPanel(){
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			setBackground(Color.white);
			Graphics2D g2 = (Graphics2D)g;
			int pos = 50;

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(red);
			g2.setFont(new Font(null, 0, 25));
			g.drawString("Highscore:",10 , pos);

			for (int i = 1; i < highscore.length; i++) {
				pos=pos+25;
				g.drawString(highscore[i],10 , pos);
			}
		}
	};
}
@SuppressWarnings("serial")
class Kurve implements ActionListener,KeyListener{
	private ArrayList<Pixel> pixels = new ArrayList<Pixel>();
	private final int PIXEL = 10;
	private double x=20,y=20,riktning;
	private boolean höger,vänster;
	private Timer timer = new Timer(10, this);
	private GoJbFrame frame = new GoJbFrame("Kurve",true,DISPOSE_ON_CLOSE);
	Kurve() {
		frame.add(label);
		timer.start();
		frame.revalidate();
		frame.addKeyListener(this);
		frame.addWindowListener(autoListener);
		label.setOpaque(true);
		label.setBackground(black);
	}
	JLabel label = new JLabel(){
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2=(Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);		
			g2.setColor(red);
			for (Pixel pixel : pixels) {
				pixel.draw(g2);
			}
		}
	};
	int i;
	@Override
	public void actionPerformed(ActionEvent e) {
		if (höger) riktning +=9;
		if (vänster) riktning -=9;
		System.err.println(riktning/180);
		double b = riktning/180*Math.atan(1);
		x += Math.cos(b);
		y += Math.sin(b);
		if (i++<200) {
			pixels.add(new Pixel(x,y));
		}
		else if (i>230) {
			i=0;
		}

		frame.repaint();
	}
	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_LEFT) {
			vänster=true;
		}
		else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
			höger=true;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_LEFT) {
			vänster=false;
		}
		else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
			höger=false;
		}

		else if (e.getKeyCode()==KeyEvent.VK_2) {
			riktning+=27;
		}
		else if (e.getKeyCode()==KeyEvent.VK_1) {
			riktning-=27;
		}

	}
	private class Pixel{
		private double x,y;
		Pixel(double x,double y) {
			this.x=x;
			this.y=y;
		}
		void draw(Graphics2D g){
			g.fillOval((int)x, (int)y, PIXEL,PIXEL);
		}
	}
}

