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

	private JFrame huvudf�nster = new JFrame("Jakob Hej Hej :D"), 
			h�ndelsef�nster = new JFrame("H�ndelser"),
			hastighetsf�nster =  new JFrame("�ndra hastighet"),
			om = new JFrame("Om"),
			laddf�nster = new JFrame("Startar..."),
			avslutningsf�nster = new JFrame("Avslutar...");

	JMenuItem avslutaItem = new JMenuItem("Avsluta"), 
			omItem = new JMenuItem("Om"),
			visaItem = new JMenuItem("Visa"),
			d�ljItem = new JMenuItem("D�lj"),
			textByteItem = new JMenuItem("�ndra text p� remsa"),
			gr�nItem = new JMenuItem("Gr�n"),
			r�dItem = new JMenuItem("R�d"),
			bl�Item = new JMenuItem("Bl�"),
			gulItem = new JMenuItem("Gul"),
			hastighetItem = new JMenuItem("�ndra hastighet p� piltangenterna"),
			h�ndelseItem = new JMenuItem("Visa H�ndelsef�nster");
	
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

	static JTextArea text = new JTextArea();
	static boolean autoscroll = true;

	public static int antalF�nster = 0;

	Jakobs(){
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

		autoscrollknapp.addActionListener(this);
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

		omItem.addActionListener(e -> om.setVisible(true));
		gulItem.addActionListener(e -> mittPanel.setForeground(YELLOW));
		r�dItem.addActionListener(e -> mittPanel.setForeground(RED));
		gr�nItem.addActionListener(e -> mittPanel.setForeground(GREEN));
		bl�Item.addActionListener(e -> mittPanel.setForeground(BLUE));
		hastighetItem.addActionListener(e -> hastighetsf�nster.setVisible(true));
		rensKnapp.addActionListener(e -> text.setText(null));

		menu(arkivMeny, "Nytt", e -> new Jakobs());
		menu(arkivMeny, "�ppna R�randeMoj�ng", Bild("/images/Nopeliten.png"), e -> {new R�randeMoj�ng();huvudf�nster.dispose();});;
		menu(arkivMeny, "�ppna Studsande Objekt", e -> new Studsa());
		menu(arkivMeny, "�ppna Minir�knare", e -> new R�knare());
		menu(arkivMeny, "Spela Pong", e -> new spel.Pongspel());
		menu(arkivMeny, "Spela Snake", e -> new Snake());
		menu(arkivMeny, "Simulator till riksdagsmandat", e -> new Mandat());
		menu(arkivMeny, "Tr�na p� glosor", e -> new Glosor());
		menu(arkivMeny, "Spela FlappyGojb",  e -> new spel.FlappyGoJb());
		menu(arkivMeny, "3d", e -> new OpenGLTest());
		menu(arkivMeny, "Kurve", e -> new spel.Kurve());
		menu(arkivMeny, "GoJbs Fondkoll", e -> new FondKoll());
		menu(arkivMeny, "Tetris", e -> new Tetris());
		arkivMeny.addSeparator();
		menu(arkivMeny, "Logga ut", e -> {
			Login.logout();
			((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.asterisk")).run();
			JOptionPane.showMessageDialog(huvudf�nster, "Utloggad!");
			huvudf�nster.dispose();
		});
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
	public void menu(JMenu menu,String text ,Icon icon, ActionListener a){
		JMenuItem item = new JMenuItem(text,icon);
		item.addActionListener(a);
		menu.add(item);
	}
	public void menu(JMenu menu,String text, ActionListener a){
		menu(menu, text, null, a);
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
		String Text = JOptionPane.showInputDialog("�ndra text p� dragbar remsa");
		setTexten(Text);

	}
	public void setTexten(String Text){
		if(Text == null){
			Text = "Dra eller anv�nd piltangenterna";
		}
		texten = Text;
		System.out.println("Texten �ndrad till: " + texten);

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
	private JFrame frame = new JFrame("Mandatsimulator f�r riksdagen");
	private String[] partiNamn = {
			"",
			"Socialdemokraterna",
			"V�nsterpartiet",
			"Milj�partiet",
			"Moderaterna",
			"Centerpartiet",
			"Folkpartiet",
			"Kristdemokraterna",
			"Sverigedemokraterna",
			"Feministiskt initiativ",
			"�vriga"
	};
	private final int i = partiNamn.length;
	private JTextField[] v�rden = new JTextField[i];
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
	private JButton button = new JButton("�ppna j�mf�relser");
	private Color[] f�rger = {white,red,red.darker(),green,blue,green.darker(),blue.darker(),blue.darker().darker(),yellow,magenta,gray};
	private JFrame[] j�mf�relseFrames = new JFrame[20];
	private int nr;
	private long s = 0;
	Mandat() {

		JLabel label = new JLabel("Parti:");
		JLabel label2 = new JLabel("R�ster:");
		JLabel label3 = new JLabel("Mandat i riksdagen:");
		JLabel label4 = new JLabel("Procent av r�ster");

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
			v�rden[i]=new JTextField();
			v�rden[i].addCaretListener(e -> uppdaterasumma());
			mandat[i]=new JLabel();
			mandat[i].setOpaque(true);
			mandat[i].setBackground(white);
			procentLabels[i]=new JLabel();
			procentLabels[i].setOpaque(true);
			procentLabels[i].setBackground(white);
			frame.add(parti[i]);
			frame.add(v�rden[i]);
			frame.add(procentLabels[i]);
			frame.add(mandat[i]);
			uddatal[i]=1.4;
		}
		mellanrum.setOpaque(true);
		mellanrum.setBackground(white);
		mellanrum2.setOpaque(true);
		mellanrum2.setBackground(white);
		button.addActionListener(e -> j�mf�r());
		frame.add(button);
		frame.add(summaLabel);
		frame.add(mellanrum);
		frame.add(mellanrum2);
		frame.setLayout(new GridLayout(i+1,4,1,2));
		frame.setIconImage(f�nsterIcon);
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
				s = s + Long.parseLong(v�rden[i].getText());
			} catch (NumberFormatException e) {}
		}
		for (int i = 1; i < mandat.length; i++) {
			try {
				procentLabels[i].setText(Double.toString(procent[i]=(double) Math.round(((double)Long.parseLong(v�rden[i].getText()))/((double)s)*10000)/100)+"%");
			} catch (Exception e) {
				procentLabels[i].setText("0%");
			}
		}
		summaLabel.setText("Totalt: " + Long.toString(s));
		ber�kna();
	}
	private void ber�kna(){

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
									.parseDouble(v�rden[i].getText())
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
				j�mf�relseFrames[i].repaint();
			} catch (Exception e) {}
		}
	}
	private void j�mf�r(){
		nr++;
		JCheckBox[] checkBoxes = new JCheckBox[i];
		JFrame frame = j�mf�relseFrames[nr] = new JFrame();
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
							g.setColor(f�rger[j]);
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
		frame.setIconImage(f�nsterIcon);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
class R�knare implements ActionListener{

	JFrame 	frame = new JFrame("Minir�knare");

	JPanel 	R�knarKnappar = new JPanel(),
			R�knarPanel = new JPanel();

	JButton[] sifferButtons = new JButton[10];

	JButton	Minir�nkarknappPlus = new JButton("+"),
			Minir�nkarknappMinus = new JButton("-"),
			Minir�nkarknappG�nger = new JButton("*"),
			Minir�nkarknappDelat = new JButton("/"),
			Minir�nkarknappLikamed = new JButton("=");

	JLabel	Summa = new JLabel(""),
			R�knes�tt = new JLabel();

	JTextArea R�knartext = new JTextArea();

	boolean nyr�kning = false;

	JButton C = new JButton("C"),
			Punkt = new JButton(".");

	double 	a = 0,
			b = 0;

	public R�knare() {

		R�knarKnappar.setLayout(new GridLayout(5,5,5,5));

		for (int i = 1; i < sifferButtons.length; i++) {
			sifferButtons[i]=new JButton(Integer.toString(i));
			sifferButtons[i].addActionListener(this);
			R�knarKnappar.add(sifferButtons[i]);
			if (i==3) {
				R�knarKnappar.add(Minir�nkarknappPlus);
			}
			else if (i==6) {
				R�knarKnappar.add(Minir�nkarknappMinus);
			}
			else if (i==9) {
				sifferButtons[0] = new JButton("0");
				sifferButtons[0].setPreferredSize(new Dimension(120,100));
				sifferButtons[0].addActionListener(this);
				R�knarKnappar.add(Minir�nkarknappG�nger);
				R�knarKnappar.add(Punkt);
				R�knarKnappar.add(sifferButtons[0]);
				R�knarKnappar.add(Minir�nkarknappLikamed);	
				R�knarKnappar.add(Minir�nkarknappDelat);
				R�knarKnappar.add(C);
				R�knarKnappar.setBackground(white);

			}
		}
		Minir�nkarknappG�nger.addActionListener(this);
		Minir�nkarknappDelat.addActionListener(this);
		Minir�nkarknappMinus.addActionListener(this);
		Minir�nkarknappPlus.addActionListener(this);
		Minir�nkarknappLikamed.addActionListener(this);
		C.addActionListener(this);
		Punkt.addActionListener(this);

		R�knarPanel.add(Summa);
		R�knarPanel.add(R�knes�tt);
		R�knarPanel.add(R�knartext);
		R�knarPanel.setBackground(white);

		R�knartext.setFont(typsnitt);
		Summa.setFont(typsnitt);
		R�knes�tt.setFont(typsnitt);

		frame.setLayout(new BorderLayout());
		frame.add(R�knarPanel,BorderLayout.NORTH);
		frame.add(R�knarKnappar,BorderLayout.CENTER);
		frame.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.WEST);
		frame.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.EAST);
		frame.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.SOUTH);
		frame.setBackground(WHITE);
		frame.pack();
		frame.setIconImage(f�nsterIcon);
		frame.getContentPane().setBackground(white);
		frame.setVisible(true);
	}
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == Minir�nkarknappDelat){ 
			if (R�knes�tt.getText()==("")) {
				R�knes�tt.setText("del");
			}
			R�knaUt();
			R�knes�tt.setText("/");
			nyr�kning = false;
		}
		else if (e.getSource() == Minir�nkarknappG�nger){ 
			R�knaUt();
			R�knes�tt.setText("*");
			nyr�kning = false;
		}
		else if (e.getSource() == Minir�nkarknappMinus){ 
			R�knaUt();
			R�knes�tt.setText("-");
			nyr�kning = false;
		}
		else if (e.getSource() == Minir�nkarknappPlus){ 
			R�knaUt();
			R�knes�tt.setText("+");
			nyr�kning = false;

		}
		else if (e.getSource()==C) {
			R�knes�tt.setText(null);
			Summa.setText(null);
			R�knartext.setText(null);
		}

		if (nyr�kning){
			nyr�kning = false;
			C.doClick();

		}
		if (e.getSource() == Minir�nkarknappLikamed){
			R�knaUt();
			R�knes�tt.setText("");
			nyr�kning = true;
		}
		else {
			for (int i = 0; i < sifferButtons.length; i++) {
				if (e.getSource()==sifferButtons[i]) {
					R�knartext.append(Integer.toString(i));
					return;
				}
			}
		}
		if (e.getSource() == Punkt) {
			R�knartext.append(".");
		}
	}
	public void R�knaUt() {
		try {
			a = Double.parseDouble(Summa.getText());
		} catch (Exception e) {
			a = 0;
		}
		try {
			b = Double.parseDouble(R�knartext.getText());
		} catch (Exception e) {
			b = 0;
		}
		if (R�knes�tt.getText() == "+"){
			Summa.setText(Double.toString(a+b));
		}
		else if (R�knes�tt.getText() == "-") {
			Summa.setText(Double.toString(a-b));
		}
		else if (R�knes�tt.getText() == "*") {
			Summa.setText(Double.toString(a*b));
		}
		else if (R�knes�tt.getText() == "/") {
			Summa.setText(Double.toString(a/b));
		}
		else if (R�knes�tt.getText() == "del") {
			Summa.setText(Double.toString(a+b));
		}
		else {
			Summa.setText(Double.toString(a+b));
		}
		R�knartext.setText(null);
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
	private void draw3d(double rotX,double rotY,double rotZ,int l�ngd,int h�jd,int bredd){

		l�ngd/=2;
		h�jd/=2;
		bredd/=2;

		glTranslated(0,0,-10);

		glRotated(rotX,1,0,0);
		glRotated(rotY,0,1,0);
		glRotated(rotZ,0,0,1);

		glBegin(GL_QUADS);

		//Fram
		glColor3d(1,0.5,0);

		glVertex3d(-bredd,-h�jd,l�ngd);
		glVertex3d(-bredd,h�jd,l�ngd);
		glVertex3d(bredd,h�jd,l�ngd);
		glVertex3d(bredd,-h�jd,l�ngd);

		//Bak
		glColor3d(0,1,0);
		glVertex3d(-bredd,-h�jd,-l�ngd);
		glVertex3d(-bredd,h�jd,-l�ngd);
		glVertex3d(bredd,h�jd,-l�ngd);
		glVertex3d(bredd,-h�jd,-l�ngd);

		//Under
		glColor3d(0,0,1);
		glVertex3d(-bredd,-h�jd,l�ngd);
		glVertex3d(-bredd,-h�jd,-l�ngd);
		glVertex3d(bredd,-h�jd,-l�ngd);
		glVertex3d(bredd,-h�jd,l�ngd);

		//�ver
		glColor3d(1,1,0);
		glVertex3d(-bredd,h�jd,-l�ngd);
		glVertex3d(-bredd,h�jd,l�ngd);
		glVertex3d(bredd,h�jd,l�ngd);
		glVertex3d(bredd,h�jd,-l�ngd);

		//LeftFace
		glColor3d(0,1,1);
		glVertex3d(-bredd,-h�jd,l�ngd);
		glVertex3d(-bredd,-h�jd,-l�ngd);
		glVertex3d(-bredd,h�jd,-l�ngd);
		glVertex3d(-bredd,h�jd,l�ngd);

		//		//Right Face
		glColor3d(1,0,1);
		glVertex3d(bredd,-h�jd,l�ngd);
		glVertex3d(bredd,h�jd,l�ngd);
		glVertex3d(bredd,h�jd,-l�ngd);
		glVertex3d(bredd,-h�jd,-l�ngd);

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
		frame.setIconImage(f�nsterIcon);
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