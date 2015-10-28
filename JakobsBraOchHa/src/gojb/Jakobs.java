package gojb;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.text.DefaultCaret;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import GoJbFrame.GoJbFrame;
import spel.Snake;

import static gojb.GoJbsBraOchHa.*;
import static javax.swing.SwingConstants.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static java.awt.Color.*;
import static javax.swing.JFrame.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.util.glu.GLU.gluPerspective;


public class Jakobs implements ActionListener,MouseInputListener,KeyListener,WindowListener{

	private JFrame huvudfönster = new JFrame("Jakob Hej Hej :D"), 
			händelsefönster = new JFrame("Händelser"),
			hastighetsfönster =  new JFrame("Ändra hastighet"),
			om = new JFrame("Om"),
			laddfönster = new JFrame("Startar..."),
			avslutningsfönster = new JFrame("Avslutar...");

	JMenuItem avslutaItem = new JMenuItem("Avsluta"), 
			omItem = new JMenuItem("Om"),
			visaItem = new JMenuItem("Visa"),
			döljItem = new JMenuItem("Dölj"),
			textByteItem = new JMenuItem("Ändra text på remsa"),
			grönItem = new JMenuItem("Grön"),
			rödItem = new JMenuItem("Röd"),
			blåItem = new JMenuItem("Blå"),
			gulItem = new JMenuItem("Gul"),
			hastighetItem = new JMenuItem("Ändra hastighet på piltangenterna"),
			händelseItem = new JMenuItem("Visa Händelsefönster");
	
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

	static JTextArea text = new JTextArea();
	static boolean autoscroll = true;

	public static int antalFönster = 0;

	Jakobs(){
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

		autoscrollknapp.addActionListener(this);
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

		omItem.addActionListener(e -> om.setVisible(true));
		gulItem.addActionListener(e -> mittPanel.setForeground(YELLOW));
		rödItem.addActionListener(e -> mittPanel.setForeground(RED));
		grönItem.addActionListener(e -> mittPanel.setForeground(GREEN));
		blåItem.addActionListener(e -> mittPanel.setForeground(BLUE));
		hastighetItem.addActionListener(e -> hastighetsfönster.setVisible(true));
		rensKnapp.addActionListener(e -> text.setText(null));

		menu(arkivMeny, "Nytt", e -> new Jakobs());
		menu(arkivMeny, "Öppna RörandeMojäng", Bild("/images/Nopeliten.png"), e -> {new RörandeMojäng();huvudfönster.dispose();});;
		menu(arkivMeny, "Öppna Studsande Objekt", e -> new Studsa());
		menu(arkivMeny, "Öppna Miniräknare", e -> new Räknare());
		menu(arkivMeny, "Spela Pong", e -> new spel.Pongspel());
		menu(arkivMeny, "Spela Snake", e -> new Snake());
		menu(arkivMeny, "Simulator till riksdagsmandat", e -> new Mandat());
		menu(arkivMeny, "Träna på glosor", e -> new Glosor());
		menu(arkivMeny, "Spela FlappyGojb",  e -> new spel.FlappyGoJb());
		menu(arkivMeny, "3d", e -> new OpenGLTest());
		menu(arkivMeny, "Kurve", e -> new spel.Kurve());
		menu(arkivMeny, "GoJbs Fondkoll", e -> new FondKoll());
		menu(arkivMeny, "Tetris", e -> new Tetris());
		arkivMeny.addSeparator();
		menu(arkivMeny, "Logga ut", e -> {
			Login.logout();
			((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.asterisk")).run();
			JOptionPane.showMessageDialog(huvudfönster, "Utloggad!");
			huvudfönster.dispose();
		});
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
	public void menu(JMenu menu,String text ,Icon icon, ActionListener a){
		JMenuItem item = new JMenuItem(text,icon);
		item.addActionListener(a);
		menu.add(item);
	}
	public void menu(JMenu menu,String text, ActionListener a){
		menu(menu, text, null, a);
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
		if(arg0.getKeyCode()==KeyEvent.VK_ESCAPE){
			System.exit(0);
		}
		else if(arg0.getKeyCode()==KeyEvent.VK_LEFT){
			posX = posX - flyttHastighet;
		}
		else if(arg0.getKeyCode()==KeyEvent.VK_RIGHT){
			posX = posX + flyttHastighet;
		}
		else if(arg0.getKeyCode()==KeyEvent.VK_UP){
			posY = posY - flyttHastighet;
		}
		else if(arg0.getKeyCode()==KeyEvent.VK_DOWN){
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
		String Text = JOptionPane.showInputDialog("Ändra text på dragbar remsa");
		setTexten(Text);

	}
	public void setTexten(String Text){
		if(Text == null){
			Text = "Dra eller använd piltangenterna";
		}
		texten = Text;
		System.out.println("Texten ändrad till: " + texten);

	}
	static void skrivError(int b){
		try {
			text.append(String.valueOf((char)b));
			((DefaultCaret) text.getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
			if (autoscroll == true) {
				text.setCaretPosition(text.getDocument().getLength());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
class Mandat{
	private JFrame frame = new JFrame("Mandatsimulator för riksdagen");
	private String[] partiNamn = {
			"",
			"Socialdemokraterna",
			"Vänsterpartiet",
			"Miljöpartiet",
			"Moderaterna",
			"Centerpartiet",
			"Folkpartiet",
			"Kristdemokraterna",
			"Sverigedemokraterna",
			"Feministiskt initiativ",
			"Övriga"
	};
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
							int partFilled  = (int) Math.round(antalmandat[j]*1.0315186246418338108882521489971);    
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
class Ping{
	public Ping(String string){
		GoJbFrame frame = new GoJbFrame("Ping",true,JFrame.DISPOSE_ON_CLOSE);
		JTextArea textArea = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(textArea);

		frame.add(scrollPane);
		frame.addWindowListener(new WindowListener() {
			@Override
			public void windowClosed(WindowEvent e) {
				try {
					Runtime.getRuntime().exec("taskkill /f /im ping.exe");
				} catch (IOException e1) {
					e1.printStackTrace();

				}
				System.exit(1);
			}
			public void windowOpened(WindowEvent e) {}public void windowIconified(WindowEvent e) {}public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}public void windowClosing(WindowEvent e){}public void windowActivated(WindowEvent arg0) {};
		});
		for(int i = 0;i<100;i++){
			try {
				System.err.println(i);
				new Thread(){
					public void run() {

						BufferedReader inputStream = null;
						try {
							inputStream = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("ping " + string + " -4 -l 65500 -n 1000").getInputStream()));

						} catch (IOException e) {
							e.printStackTrace();
						}
						String s;
						try {
							while ((s = inputStream.readLine()) != null) {
								System.out.println(s);
							}
						} catch (IOException e) {
							e.printStackTrace();

						}

					};
				}.start();

			} catch (Exception e) {e.printStackTrace();}
		}
		BufferedReader inputStream = null;
		try {
			inputStream = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec("ping " + string + " -l 65500 -n 1000").getInputStream()));
		} catch (IOException e1) {
			e1.printStackTrace();

		}

		String s;
		// reading output stream of the command
		try {
			while ((s = inputStream.readLine()) != null) {
				System.out.println(s);
				textArea.append(s);
			}
		} catch (IOException e) {
			e.printStackTrace();

		}
	}
}



class OpenGLTest {
	private double xx,zz,rx,ry,rz;
	private int fps;
	long lastFPS;

	public OpenGLTest() {
		System.out.println("Starting");
		thread.start();
	}
	private Thread thread = new Thread(){
		public void run() {
			System.out.println("Running");
			try {
				unZip();

				try {
					if (getClass().getResource("/" + getClass().getName().replace('.','/') + ".class").toString().startsWith("jar:")){
						System.setProperty("org.lwjgl.librarypath", new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\GoJbsBraOchHa\\windows_dll").getAbsolutePath());
					}
					Display.setDisplayMode(new DisplayMode(800, 600));
					Display.setTitle("GoJbGame");
					Display.create();
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
				lastFPS= getTime();
				gameLoop();
				Display.destroy();
			} catch (Exception e) {
				e.printStackTrace();
				try {
					thread.wait();
				} catch (InterruptedException e1) {
					e1.printStackTrace();

				}
			}
		};
	};
	private void unZip(){
		try {
			System.err.println(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			ZipInputStream zipIn = new ZipInputStream(getClass().getProtectionDomain().getCodeSource().getLocation().openStream());
			ZipEntry entry = zipIn.getNextEntry();
			while (entry != null) {
				if (entry.toString().startsWith("windows_dll/")) {
					String filePath =  System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\GoJbsBraOchHa"+File.separator + entry.getName();
					System.err.println(entry);
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
	}
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	private void gameLoop(){
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(70,(float)Display.getWidth()/Display.getHeight(),0.3f,1000);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_DEPTH_TEST);
		double x=0,y=0,z=0;
		final double SPEED = 0.1;
		while(!Display.isCloseRequested()){
			if (Keyboard.isKeyDown(Keyboard.KEY_F))
				setDisplayMode(800, 600, !Display.isFullscreen());
			if (Keyboard.isKeyDown(Keyboard.KEY_W))
				move(0.01,1);
			if (Keyboard.isKeyDown(Keyboard.KEY_S))
				move(-0.01,1);
			if (Keyboard.isKeyDown(Keyboard.KEY_A))
				move(0.01,0);
			if (Keyboard.isKeyDown(Keyboard.KEY_D))
				move(-0.01,0);
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
				ry -= SPEED;
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
				ry += SPEED;
			if (Keyboard.isKeyDown(Keyboard.KEY_UP))
				rx -= SPEED;
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
				rx += SPEED;
			if (Keyboard.isKeyDown(Keyboard.KEY_Z)) 
				x+=0.5;
			if (Keyboard.isKeyDown(Keyboard.KEY_X)) 
				y+=0.5;
			if (Keyboard.isKeyDown(Keyboard.KEY_C)) 
				z+=0.5;
			if (Keyboard.isKeyDown(Keyboard.KEY_V)) 
				x-=0.5;
			if (Keyboard.isKeyDown(Keyboard.KEY_B))
				y-=0.5;
			if (Keyboard.isKeyDown(Keyboard.KEY_N))
				z-=0.5;

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			glLoadIdentity();

			glRotated(rx,1,0,0);
			glRotated(ry,0,1,0);
			glRotated(rz,0,0,1);
			glTranslated(xx,0,zz);

			//			glPushMatrix();

			draw3d(x,y,z,2,2,2);

			//			draw3d(x,y,z,2,2,2);

			//			glPopMatrix();
			Display.update();
			updateFPS();
			Display.sync(200);


		}
	}
	private void draw3d(double rotX,double rotY,double rotZ,int längd,int höjd,int bredd){

		längd/=2;
		höjd/=2;
		bredd/=2;

		glTranslated(0,0,-10);

		glRotated(rotX,1,0,0);
		glRotated(rotY,0,1,0);
		glRotated(rotZ,0,0,1);

		glBegin(GL_QUADS);

		//Fram
		glColor3d(1,0.5,0);

		glVertex3d(-bredd,-höjd,längd);
		glVertex3d(-bredd,höjd,längd);
		glVertex3d(bredd,höjd,längd);
		glVertex3d(bredd,-höjd,längd);

		//Bak
		glColor3d(0,1,0);
		glVertex3d(-bredd,-höjd,-längd);
		glVertex3d(-bredd,höjd,-längd);
		glVertex3d(bredd,höjd,-längd);
		glVertex3d(bredd,-höjd,-längd);

		//Under
		glColor3d(0,0,1);
		glVertex3d(-bredd,-höjd,längd);
		glVertex3d(-bredd,-höjd,-längd);
		glVertex3d(bredd,-höjd,-längd);
		glVertex3d(bredd,-höjd,längd);

		//Över
		glColor3d(1,1,0);
		glVertex3d(-bredd,höjd,-längd);
		glVertex3d(-bredd,höjd,längd);
		glVertex3d(bredd,höjd,längd);
		glVertex3d(bredd,höjd,-längd);

		//LeftFace
		glColor3d(0,1,1);
		glVertex3d(-bredd,-höjd,längd);
		glVertex3d(-bredd,-höjd,-längd);
		glVertex3d(-bredd,höjd,-längd);
		glVertex3d(-bredd,höjd,längd);

		//		//Right Face
		glColor3d(1,0,1);
		glVertex3d(bredd,-höjd,längd);
		glVertex3d(bredd,höjd,längd);
		glVertex3d(bredd,höjd,-längd);
		glVertex3d(bredd,-höjd,-längd);

		glEnd();
	}

	/**
	 * Set the display mode to be used 
	 * 
	 * @param width The width of the display required
	 * @param height The height of the display required
	 * @param fullscreen True if we want fullscreen mode
	 */

	private void setDisplayMode(int width, int height, boolean fullscreen) {

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
			System.out.println("Eror "+width+"x"+height+" fullscreen="+fullscreen + e);
		}
	}
	private void move(double amt, double dir){
		zz += amt * Math.sin(Math.toRadians(ry + 90 * dir));
		xx += amt * Math.cos(Math.toRadians(ry + 90 * dir));
	}
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