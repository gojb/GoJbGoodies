package GoJbsBraOchHa;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.event.*;
import javax.swing.text.*;

import GoJbFrame.GoJbFrame;
import static GoJbsBraOchHa.Mouse.*;

/**
 * Det h�r programmet inneh�ller lite
 * grejer som kan vara "BraOchHa" och �ven
 * andra sm� program vi gjort f�r att testa
 * olika saker.
 * 
 * @author GoJb - Glenn Olsson & Jakob Bj�rns
 * 
 * @see <a href="http://gojb.bl.ee/">http://gojb.bl.ee/</a>
 */

@SuppressWarnings("serial")
public class Mouse extends JPanel implements 	ActionListener,
												MouseInputListener,
												KeyListener,
												WindowListener{

	JFrame			HuvudF�nster = new JFrame("Hej Hej :D"), 
					H�ndelseF�nster = new JFrame("H�ndelser"),
					HastighetsF�nster =  new JFrame(),
					om = new JFrame("Om"),
					Laddf�nster = new JFrame("Startar..."),
					AvslutningsF�nster = new JFrame("Avslutar...");
			
	JPanel 			KnappPanel = new JPanel();

	JMenuBar 		MenyRad = new JMenuBar();
	
	JMenu 			Arkiv = new JMenu("Arkiv"), 
					Hj�lp = new JMenu("Hj�lp"),
					Redigera = new JMenu("Redigera"),
					F�rgbyte = new JMenu("Byt bakgrundsf�rg"),
					TextF�rgByte = new JMenu("Byt Textf�rg");

	JMenuItem 		Avsluta = new JMenuItem("Avsluta"), 
				  	Om = new JMenuItem("Om"),
				  	Visa = new JMenuItem("Visa"),
				  	D�lj = new JMenuItem("D�lj"),
				  	Nytt = new JMenuItem("Nytt"),
				  	TextByte = new JMenuItem("�ndra text p� remsa"),
				  	Gr�n = new JMenuItem("Gr�n"),
				  	R�d = new JMenuItem("R�d"),
				  	Bl� = new JMenuItem("Bl�"),
				  	Gul = new JMenuItem("Gul"),
				  	Hastighet = new JMenuItem("�ndra hastighet p� piltangenterna"),
				  	H�ndelse = new JMenuItem("Visa H�ndelsef�nster"),
				  	R�kna = new JMenuItem("�ppna Minir�knare"),
				  	Spelknapp = new JMenuItem("Spela Pong"),
				  	R�rande = new JMenuItem("�ppna R�randeMoj�ng",new ImageIcon(getClass().getResource("/images/Nopeliten.png"))),
				  	StudsItem = new JMenuItem("�ppna Studsande Objekt"),
				  	Snake = new JMenuItem("Spela Snake");
	
	JButton 		knapp1 = new JButton("Bl�"),
					knapp2 = new JButton("Gr�n"),
					knapp3 = new JButton("R�d"),
					knapp4 = new JButton("Gul"),
					OK = new JButton("Klar"),
					Autoscrollknapp = new JButton("St�ng av autoscroll"),
					RensKnapp = new JButton("Rensa");
	
	JScrollPane 	Jaha = new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
											JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	JProgressBar	LaddstapelStart = new JProgressBar(0,100),
				 	LaddstapelAvslut = new JProgressBar(0, 100);
	
	JLabel 			omtext = new JLabel("<html>Hall�j! Det h�r programmet �r skapat av GoJbs Javaprogrammering"),
					Laddtext = new JLabel("Startar program..."),
					AvslutningsText = new JLabel("Avslutar program...");
	
 	JSlider 		Slide = new JSlider(JSlider.HORIZONTAL,0,100,10);
 	

	static 			JTextArea text = new JTextArea();
 		
	static boolean 	autoscroll = true;
	
	int				FlyttHastighet = 10,posX = 125, posY = 75, textbredd;
	
	static int		AntalF�nster = 0;
	
	Timer 			StartTimer = new Timer(2, this),
					SlutTimer = new Timer(2, this);
	
	Robot			robot;
	
	Color			F�rg = new Color(0, 0, 255);
	
	public static 	Font 	Typsnitt = new Font("Arial", 0, 40);
	
	public static 	Image 	F�nsterIcon;
	
	String Texten = "Dra eller anv�nd piltangenterna";
	
	public Mouse(){
		
		Laddtext.setFont(Typsnitt);
		Laddtext.setHorizontalAlignment(JLabel.CENTER);
		Laddf�nster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Laddf�nster.setLayout(new BorderLayout(10,10));	
		Laddf�nster.add(LaddstapelStart,BorderLayout.CENTER);
		Laddf�nster.add(Laddtext,BorderLayout.NORTH);
		Laddf�nster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.EAST);
		Laddf�nster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.WEST);
		Laddf�nster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.SOUTH);
		Laddf�nster.setSize(400, 100);
		Laddf�nster.setAlwaysOnTop(true);
		Laddf�nster.setResizable(false);
		Laddf�nster.setLocationRelativeTo(null);
		Laddf�nster.setIconImage(F�nsterIcon);
		Laddf�nster.getContentPane().setBackground(Color.yellow);
		Laddf�nster.setUndecorated(true);
		Laddf�nster.setVisible(true);		
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		SkrivH�ndelsetext("V�lkommen!");
		Avsluta.addActionListener(this);
		Om.addActionListener(this);
		Visa.addActionListener(this);
		D�lj.addActionListener(this);
		Nytt.addActionListener(this);
		TextByte.addActionListener(this);
		Gul.addActionListener(this);
		R�d.addActionListener(this);
		Gr�n.addActionListener(this);
		Bl�.addActionListener(this);
		Hastighet.addActionListener(this);
		H�ndelse.addActionListener(this);
		OK.addActionListener(this);
		R�kna.addActionListener(this);
		Autoscrollknapp.addActionListener(this);
		RensKnapp.addActionListener(this);
		KnappPanel.addMouseListener(this);
		Spelknapp.addActionListener(this);
		R�rande.addActionListener(this);
		StudsItem.addActionListener(this);
		Snake.addActionListener(this);
		
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
		
		Arkiv.add(Nytt);
		Arkiv.add(R�rande);
		Arkiv.add(StudsItem);
		Arkiv.add(R�kna);
		Arkiv.add(Spelknapp);
		Arkiv.add(Snake);
		Arkiv.addSeparator();
		Arkiv.add(Avsluta);
		
		Redigera.add(F�rgbyte);
		Redigera.add(TextByte);
		Redigera.add(TextF�rgByte);
		Redigera.add(Hastighet);
		Redigera.add(H�ndelse);
		
		Hj�lp.add(Om);
		
		F�rgbyte.add(D�lj);
		F�rgbyte.add(Visa);
		
		TextF�rgByte.add(R�d);
		TextF�rgByte.add(Gr�n);
		TextF�rgByte.add(Bl�);
		TextF�rgByte.add(Gul);
		
		MenyRad.add(Arkiv);
		MenyRad.add(Redigera);
		MenyRad.add(Hj�lp);
		
		setOpaque(true);
		setBackground(F�rg);
		setForeground(Color.YELLOW);
		addMouseMotionListener(this);
		addMouseListener(this);
		setSize(10000,10000);

		text.setEditable(false);
		
		H�ndelseF�nster.setSize(500,500);
		H�ndelseF�nster.setLayout(new BorderLayout());
		H�ndelseF�nster.add(Autoscrollknapp,BorderLayout.NORTH);
		H�ndelseF�nster.add(Jaha,BorderLayout.CENTER);
		H�ndelseF�nster.add(RensKnapp,BorderLayout.SOUTH);
		H�ndelseF�nster.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		H�ndelseF�nster.setAlwaysOnTop(true);
		H�ndelseF�nster.setResizable(false);
		H�ndelseF�nster.setIconImage(F�nsterIcon);
		
		Slide.setPaintTicks(true);
		Slide.setPaintLabels(true);
		Slide.setMajorTickSpacing(10);
		Slide.setMinorTickSpacing(1);
		
		HastighetsF�nster.setSize(500,200);
		HastighetsF�nster.setLayout(new BorderLayout());
		HastighetsF�nster.add(Slide,BorderLayout.NORTH);
		HastighetsF�nster.add(OK,BorderLayout.CENTER);
		HastighetsF�nster.add(Box.createRigidArea(new Dimension(100,100)),BorderLayout.WEST);
		HastighetsF�nster.add(Box.createRigidArea(new Dimension(100,100)),BorderLayout.EAST);
		HastighetsF�nster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.SOUTH);
		HastighetsF�nster.setLocationRelativeTo(null);
		HastighetsF�nster.setResizable(false);
		HastighetsF�nster.revalidate();
		
		KnappPanel.add(knapp1);
		KnappPanel.add(knapp2);
		KnappPanel.add(knapp3);
		KnappPanel.add(knapp4);
				
		omtext.setVerticalTextPosition(1);
		omtext.setFont(Typsnitt);
		omtext.addMouseListener(this);
		om.setSize(400,300);
		om.add(omtext);
		om.setIconImage(F�nsterIcon);
		om.setLocationRelativeTo(HuvudF�nster);
		
		HuvudF�nster.setJMenuBar(MenyRad);
		HuvudF�nster.setSize((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2),(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2*1.5));
		HuvudF�nster.setLayout(new BorderLayout());
		HuvudF�nster.setMinimumSize(new Dimension(400,400));
		HuvudF�nster.addKeyListener(this);
		HuvudF�nster.addWindowListener(this);
		HuvudF�nster.setIconImage(F�nsterIcon);
		HuvudF�nster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.NORTH);
		HuvudF�nster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.WEST);
		HuvudF�nster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.EAST);
		HuvudF�nster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.SOUTH);
		HuvudF�nster.add(this,BorderLayout.CENTER);
		HuvudF�nster.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		HuvudF�nster.setLocationRelativeTo(null);		
		HuvudF�nster.revalidate();
		HuvudF�nster.repaint();
		
		
		
		AvslutningsText.setFont(Typsnitt);
		AvslutningsText.setHorizontalAlignment(JLabel.CENTER);
		
		AvslutningsF�nster.setLayout(new BorderLayout(10,10));
		AvslutningsF�nster.add(LaddstapelAvslut,BorderLayout.CENTER);
		AvslutningsF�nster.add(AvslutningsText,BorderLayout.NORTH);
		AvslutningsF�nster.setUndecorated(true);
		AvslutningsF�nster.setSize(Laddf�nster.getSize());
		AvslutningsF�nster.setResizable(false);
		AvslutningsF�nster.setAlwaysOnTop(true);
		AvslutningsF�nster.setIconImage(F�nsterIcon);
		AvslutningsF�nster.setLocationRelativeTo(null);
		AvslutningsF�nster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		AvslutningsF�nster.getContentPane().setBackground(Color.yellow);
		AvslutningsF�nster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.EAST);
		AvslutningsF�nster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.WEST);
		AvslutningsF�nster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.SOUTH);
		
		LaddstapelAvslut.setValue(100);
		
		AntalF�nster++;
		
		System.out.println(AntalF�nster);
		
		StartTimer.start();
		
	}

	public static void main(String[] Jakob) {
		try {
		      UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		    } catch (Exception e) {
		    	((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
		    	JOptionPane.showMessageDialog(null, "Den angivna LookAndFeel hittades inte!","Error",JOptionPane.ERROR_MESSAGE);
		    }
		
		class setImageIcon{
			public setImageIcon() {
				
				try {
					
					F�nsterIcon = new ImageIcon(getClass().getResource("/images/Java-icon.png")).getImage();
				} 
				catch (Exception e) {
					((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
					JOptionPane.showMessageDialog(null, "ImageIcon hittades inte","Filfel",JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		
		new setImageIcon();
		new Pass();
		
	}

	public void mouseDragged(MouseEvent e) {
		SkrivH�ndelsetext("Du drog musen: " + e.getX() + ", " + e.getY());
		posX = e.getX()-textbredd/2;
		posY = e.getY();
		HuvudF�nster.repaint();
	
	}

	public void mouseMoved(MouseEvent e) {
		
		SkrivH�ndelsetext("Du r�rde musen: " + e.getX() + ", " + e.getY());
		if(e.getX() == 50 && e.getY() == 50){
			System.exit(1);
		
		}
	
	}
	
	public void actionPerformed(ActionEvent knapp) {
//		System.out.println(knapp.getSource());
		SkrivH�ndelsetext(knapp.getSource().toString());

		if (knapp.getSource() == Avsluta){	
			AvslutningsF�nster.setVisible(true);
			
			SlutTimer.start();
			
		}
		else if(knapp.getSource() == Om){
			om.setVisible(true);
			
		}
		else if(knapp.getSource() == knapp1){
			F�rg = Color.blue;
			setBackground(F�rg);
			knapp1.setEnabled(false);
			knapp2.setEnabled(true);
			knapp3.setEnabled(true);
			knapp4.setEnabled(true);
		}
		else if(knapp.getSource() == knapp2){
			F�rg = Color.GREEN;
			setBackground(F�rg);
			knapp1.setEnabled(true);
			knapp2.setEnabled(false);
			knapp3.setEnabled(true);
			knapp4.setEnabled(true);
			
		}
		else if(knapp.getSource() == knapp3){
			F�rg = Color.red;
			setBackground(F�rg);
			knapp1.setEnabled(true);
			knapp2.setEnabled(true);
			knapp3.setEnabled(false);
			knapp4.setEnabled(true);
	
		}	
		else if(knapp.getSource() == knapp4){
			F�rg = Color.yellow;
			setBackground(F�rg);
			knapp1.setEnabled(true);
			knapp2.setEnabled(true);
			knapp3.setEnabled(true);
			knapp4.setEnabled(false);
		}
		else if(knapp.getSource() == Visa){
			HuvudF�nster.add(KnappPanel,BorderLayout.SOUTH);
		}
		else if(knapp.getSource() == D�lj){
			HuvudF�nster.remove(KnappPanel);
			HuvudF�nster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.SOUTH);
		}
		else if(knapp.getSource() == Nytt){
			new Mouse();
		}
		else if (knapp.getSource() == TextByte){
			setTexten();
			repaint();
		}
		else if (knapp.getSource() == Bl�){
			setForeground(Color.BLUE);
			text.append("Textf�rg �ndrad till Bl�");
		}
		else if (knapp.getSource() == R�d){
			setForeground(Color.RED);
		}
		else if (knapp.getSource() == Gr�n){
			setForeground(Color.GREEN);
		}
		else if (knapp.getSource() == Gul){
			setForeground(Color.YELLOW);
		}
		else if (knapp.getSource() == Hastighet){
			HastighetsF�nster.setVisible(true);
		}
		else if (knapp.getSource() == H�ndelse){
			H�ndelseF�nster.setVisible(true);
			HuvudF�nster.toFront();
		}
		else if (knapp.getSource() == OK){
			FlyttHastighet = Slide.getValue();
			HastighetsF�nster.dispose();
		}
		else if (knapp.getSource() == R�kna){
			new R�knare();
		}
		else if (knapp.getSource() == Autoscrollknapp){
			if (autoscroll == true) {
				autoscroll = false;
				Autoscrollknapp.setText("Sl� p� autoscroll");
				SkrivH�ndelsetext("Autoscroll avst�ngt");
			}
			else{
				autoscroll = true;
				Autoscrollknapp.setText("St�ng av autoscroll");
				SkrivH�ndelsetext("Autoscroll p�slaget");
			}			
		}
		
		else if (knapp.getSource()== SlutTimer){
			if (LaddstapelAvslut.getValue()==LaddstapelAvslut.getMinimum()){
				System.exit(0);
			}
			else 
				LaddstapelAvslut.setValue(LaddstapelAvslut.getValue()-1);
		}
		else if (knapp.getSource()== StartTimer){
			
				if(LaddstapelStart.getValue()==100){
					Laddf�nster.dispose();
					HuvudF�nster.setVisible(true);
					StartTimer.stop();
					
					robot.mouseMove(HuvudF�nster.getX() + HuvudF�nster.getWidth()/2,HuvudF�nster.getY() + HuvudF�nster.getHeight()/2);
					SpelaLjud("/images/tada.wav");
				}
				else {
					LaddstapelStart.setValue(LaddstapelStart.getValue()+1);
				}
		}
		else if (knapp.getSource()==RensKnapp){
			text.setText(null);
		}
		else if (knapp.getSource()==Spelknapp) {
			new Pongspel();
		}
		else if (knapp.getSource()==R�rande) {
			new R�randeMoj�ng();
			HuvudF�nster.dispose();
		}
		else if (knapp.getSource()==StudsItem) {
			new Studsa();
		}
		else if (knapp.getSource()==Snake) {
			new Snakespel();
		}
		
		HuvudF�nster.revalidate();
		HuvudF�nster.repaint();
		
	}
	
	public void mouseClicked(MouseEvent arg0) {
		
	}

	public void mouseEntered(MouseEvent e) {
		setBackground(F�rg);
		
	}

	public void mouseExited(MouseEvent e) {
		setBackground(Color.gray);
	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent arg0) {
		
	}

	public void keyPressed(KeyEvent arg0) {
		SkrivH�ndelsetext("Du tryckte p�: " + KeyEvent.getKeyText(arg0.getKeyCode()));
		if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Esc"){
			System.exit(0);
		}
		else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "V�nsterpil"){
			posX = posX - FlyttHastighet;
			repaint();
		}
		else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "H�gerpil"){
			posX = posX + FlyttHastighet;
			repaint();
		}
		else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Upp"){
			posY = posY - FlyttHastighet;
			repaint();
		}
		else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Nedpil"){
			posY = posY + FlyttHastighet;
			repaint();
		}
	}

	public void keyReleased(KeyEvent arg0) {
		SkrivH�ndelsetext("Du sl�ppte : " + KeyEvent.getKeyText(arg0.getKeyCode()));
	}

	public void keyTyped(KeyEvent arg0) {
		
	}
	
	public void windowOpened(WindowEvent e) {
		
	}

	public void windowClosing(WindowEvent e) {
		
	}

	public void windowClosed(WindowEvent e) {
		AntalF�nster--;
		System.out.println(AntalF�nster);
		H�ndelseF�nster.dispose();
		HastighetsF�nster.dispose();
		om.dispose();
		if (AntalF�nster == 0) {
		
			AvslutningsF�nster.setVisible(true);
			SlutTimer.start();
		}
	}

	public void windowIconified(WindowEvent e) {
		
	}

	public void windowDeiconified(WindowEvent e) {
		
	}

	public void windowActivated(WindowEvent e) {
		
	}

	public void windowDeactivated(WindowEvent e) {
		
	}
	public void SpelaLjud(String Filnamn){
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(getClass().getResource(Filnamn)));
			clip.start();
			
		} catch (Exception e) {
			((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
			JOptionPane.showMessageDialog(null, "Filen: \"" + Filnamn + "\" hittades inte", "Ljud", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	public static void SkrivH�ndelsetext(String H�ndlsetext){
		text.append(H�ndlsetext + "\n");
		((DefaultCaret) text.getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		if (autoscroll == true) {
			text.setCaretPosition(text.getDocument().getLength());
		}
	}
	public static void V�nta(int millisekunder){
		try {
			Thread.sleep(millisekunder);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void paintComponent(Graphics g){
		  
		super.paintComponent(g);
	    Graphics2D g2 = (Graphics2D)g;
	    
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setFont(new Font("Serif", Font.ROMAN_BASELINE, 35));
		g2.drawString(Texten,posX, posY); 
		
		textbredd = g2.getFontMetrics().stringWidth(Texten);
	  }
	  public void setTexten(){
		  String Text = JOptionPane.showInputDialog("�ndra text p� dragbar remsa");
		  setTexten(Text);
		  
	  }
	  public void setTexten(String Text){
		  if(Text == null){
			  Text = "Dra eller anv�nd piltangenterna";
		  }
		  Texten = Text;
		  System.out.println("Texten �ndrad till: " + Texten);
	  
	}
	
}
class R�knare implements ActionListener{
	
	JFrame 	R�knare = new JFrame("Minir�knare");
	
	JPanel 	R�knarKnappar = new JPanel(),
			R�knarPanel = new JPanel();
	
	JButton Minir�nkarknapp0 = new JButton("0"),
			Minir�nkarknapp1 = new JButton("1"),
			Minir�nkarknapp2 = new JButton("2"),
			Minir�nkarknapp3 = new JButton("3"),
			Minir�nkarknapp4 = new JButton("4"),
			Minir�nkarknapp5 = new JButton("5"),
			Minir�nkarknapp6 = new JButton("6"),
			Minir�nkarknapp7 = new JButton("7"),
			Minir�nkarknapp8 = new JButton("8"),
			Minir�nkarknapp9 = new JButton("9"),
			Minir�nkarknappPlus = new JButton("+"),
			Minir�nkarknappMinus = new JButton("-"),
			Minir�nkarknappG�nger = new JButton("*"),
			Minir�nkarknappDelat = new JButton("/"),
			Minir�nkarknappLikamed = new JButton("=");
	
	JLabel	Summa = new JLabel(""),
			R�knes�tt = new JLabel();
	
	JTextArea 		R�knartext = new JTextArea();

	boolean 		nyr�kning = false;
	
	JButton C = new JButton("C"),
			Punkt = new JButton(".");
	
	double 			a = 0,
					b = 0;
	
	public R�knare() {
		
		R�knarKnappar.setLayout(new GridLayout(5,5,5,5));
		R�knarKnappar.add(Minir�nkarknapp1);
		R�knarKnappar.add(Minir�nkarknapp2);
		R�knarKnappar.add(Minir�nkarknapp3);
		R�knarKnappar.add(Minir�nkarknappPlus);
		R�knarKnappar.add(Minir�nkarknapp4);
		R�knarKnappar.add(Minir�nkarknapp5);
		R�knarKnappar.add(Minir�nkarknapp6);
		R�knarKnappar.add(Minir�nkarknappMinus);
		R�knarKnappar.add(Minir�nkarknapp7);
		R�knarKnappar.add(Minir�nkarknapp8);
		R�knarKnappar.add(Minir�nkarknapp9);
		R�knarKnappar.add(Minir�nkarknappG�nger);
		R�knarKnappar.add(Punkt);
		R�knarKnappar.add(Minir�nkarknapp0);
		R�knarKnappar.add(Minir�nkarknappLikamed);	
		R�knarKnappar.add(Minir�nkarknappDelat);
		R�knarKnappar.add(C);
		R�knarKnappar.setBackground(Color.white);
		
		Minir�nkarknapp0.setPreferredSize(new Dimension(120,100));
		Minir�nkarknapp0.addActionListener(this);
		Minir�nkarknapp1.addActionListener(this);
		Minir�nkarknapp2.addActionListener(this);
		Minir�nkarknapp3.addActionListener(this);
		Minir�nkarknapp4.addActionListener(this);
		Minir�nkarknapp5.addActionListener(this);
		Minir�nkarknapp6.addActionListener(this);
		Minir�nkarknapp7.addActionListener(this);
		Minir�nkarknapp8.addActionListener(this);
		Minir�nkarknapp9.addActionListener(this);
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
		R�knarPanel.setBackground(Color.white);

		R�knartext.setFont(Typsnitt);
		Summa.setFont(Typsnitt);
		R�knes�tt.setFont(Typsnitt);
		

		R�knare.setLayout(new BorderLayout());
		R�knare.add(R�knarPanel,BorderLayout.NORTH);
		R�knare.add(R�knarKnappar,BorderLayout.CENTER);
		R�knare.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.WEST);
		R�knare.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.EAST);
		R�knare.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.SOUTH);
		R�knare.setBackground(Color.WHITE);
		R�knare.pack();
		R�knare.setIconImage(F�nsterIcon);
		R�knare.getContentPane().setBackground(Color.white);
		R�knare.setVisible(true);
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
		else if (e.getSource() == Minir�nkarknapp0){ 
			R�knartext.append("0");
			 
		}
		else if (e.getSource() == Minir�nkarknapp1){ 
			R�knartext.append("1");
			 
		}
		else if (e.getSource() == Minir�nkarknapp2){ 
			R�knartext.append("2");
			 
		}
		else if (e.getSource() == Minir�nkarknapp3){ 
			R�knartext.append("3");
			 
		}
		else if (e.getSource() == Minir�nkarknapp4){ 
			R�knartext.append("4");
			 
		}
		else if (e.getSource() == Minir�nkarknapp5){ 
			R�knartext.append("5");
			 
		}
		else if (e.getSource() == Minir�nkarknapp6){ 
			R�knartext.append("6");
			 
		}
		else if (e.getSource() == Minir�nkarknapp7){ 
			R�knartext.append("7");
			 
		}
		else if (e.getSource() == Minir�nkarknapp8){ 
			R�knartext.append("8");
			 
		}
		else if (e.getSource() == Minir�nkarknapp9){ 
			R�knartext.append("9");
			 
		}
		else if (e.getSource() == Punkt) {
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
@SuppressWarnings("serial")
class Pongspel extends JPanel implements ActionListener,KeyListener,WindowListener,MouseMotionListener{
	
	private int x,y,V�nsterX=0,V�nsterY,H�gerX,H�gerY,RektBredd=10,RektH�jd=100,
			bredd=20,h�jd=30,hastighet,c, d,Po�ngV�nster=0,Po�ngH�ger=0,py=10,px=10;
	private JFrame frame = new JFrame("Spel");
	private Timer timer = new Timer(10, this);
	private Boolean GameOver=false;
	private String Po�ngTill,SpelareV�nster,SpelareH�ger;
	
	public Pongspel() {
		SpelareV�nster = JOptionPane.showInputDialog("Spelare till v�nster:");
		if (SpelareV�nster == null) {
			return;
		}
		else if (SpelareV�nster.equals("")) {
			SpelareV�nster = "Spelare 1";
		}
		SpelareH�ger = JOptionPane.showInputDialog("Spelare till h�ger:");
		if (SpelareH�ger == null) {
			return;
		}
		else if (SpelareH�ger.equals("")) {
			SpelareH�ger = "Spelare 2";
		}
		
		addMouseMotionListener(this);
		setForeground(Color.red);
		setPreferredSize(new Dimension(700, 500));
		setOpaque(true);	
		setBackground(Color.black.brighter());

		H�gerY = getHeight()/2;
		V�nsterY = getHeight()/2;
		H�gerX=getWidth()-bredd-1;
		
		frame.addMouseMotionListener(this);
		frame.setIconImage(F�nsterIcon);
		frame.addWindowListener(this);
		frame.addKeyListener(this);
		frame.add(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		StartaOm();
	}
	private void StartaOm(){
		x = getWidth()/2;
		y = 5;
		hastighet = 2;
		c = hastighet;
		d = hastighet;
		V�nta(100);
		timer.start();
		
	}
	private void GameOver(String Po�ngTillV�nsterEllerH�ger) {
		timer.stop();
		System.out.println("Game over!");
		Toolkit.getDefaultToolkit().beep();
		
		if (Po�ngTillV�nsterEllerH�ger=="V�nster"){
			Po�ngV�nster++;
			Po�ngTill = SpelareV�nster;
		}
		else if (Po�ngTillV�nsterEllerH�ger=="H�ger") {
			Po�ngH�ger++;
			Po�ngTill = SpelareH�ger;
		}
		
		GameOver=true;
		
		frame.repaint();
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 87 ) {
			if (V�nsterY>0) {
				V�nsterY=V�nsterY-20;
			}
		}
		else if (e.getKeyCode() == 83) {
			if (V�nsterY+RektH�jd+60<frame.getHeight()) {
				V�nsterY=V�nsterY+20;
			}
		}
		if(KeyEvent.getKeyText(e.getKeyCode()) == "Upp"){
			if (H�gerY>0) {
				H�gerY=H�gerY-20;
			}
		
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()) == "Nedpil"){
			if (H�gerY+RektH�jd+60<frame.getHeight()) {
				H�gerY=H�gerY+20;
			}
			
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
		
	}
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==timer){
			if (x+bredd>=H�gerX) {
				if (y>=H�gerY&&y<=H�gerY+RektH�jd) {
					hastighet++;
					c= -hastighet;

				}
				else{
					GameOver("V�nster");
				}
			}
			else if (x<=V�nsterX+RektBredd) {
				if (y>=V�nsterY&&y<=V�nsterY+RektH�jd) {
					hastighet++;
					c=hastighet;
					
				}
				else{
					GameOver("H�ger");
				}
			
			}
			else if (y+h�jd>=getHeight()) {
				d=-hastighet;
			}
			else if(y<=0){
				d=hastighet;
			}
			x=x+c;
			y=y+d;
			frame.repaint();
			H�gerX=getWidth()-RektBredd-1;
			
		}
	}
	public void paintComponent(Graphics g){
	
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (GameOver==true) {
			g2.setFont(new Font("", Font.BOLD, 50));
			g2.setColor(Color.green);
			g2.drawString("Po�ng till " + Po�ngTill, getWidth()/2-200, getHeight()/2);
			g2.drawString(Integer.toString(Po�ngV�nster) + " - " + Integer.toString(Po�ngH�ger), getWidth()/2-70, 40);
			g2.drawString(Integer.toString(Po�ngV�nster) + " - " + Integer.toString(Po�ngH�ger), px,py);
		}
		else {
			
		g2.drawOval(x, y, bredd, h�jd);
		g2.fillOval(x, y, bredd, h�jd);
		
		g2.drawRect(V�nsterX, V�nsterY, RektBredd, RektH�jd);
		g2.fillRect(V�nsterX, V�nsterY, RektBredd, RektH�jd);
		
		g2.drawRect(H�gerX, H�gerY, RektBredd, RektH�jd);
		g2.fillRect(H�gerX, H�gerY, RektBredd, RektH�jd);
		
		g2.setColor(Color.green);
		g2.setFont(new Font("", Font.BOLD, 50));
		g2.drawString(Integer.toString(Po�ngV�nster) + " - " + Integer.toString(Po�ngH�ger), getWidth()/2-80, 40);
		
		g2.drawString(SpelareV�nster, 0, 40);
		g2.drawString(SpelareH�ger, getWidth()-250, 40);
		
		}
	}
	
	public void windowOpened(WindowEvent e) {
		
	}

	public void windowClosing(WindowEvent e) {
		timer.stop();
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
		
	}

	public void windowDeiconified(WindowEvent e) {
		
	}
	
	public void windowActivated(WindowEvent e) {
		
	}

	public void windowDeactivated(WindowEvent e) {
		
	}
	
	public void mouseDragged(MouseEvent e) {
		px=e.getX();
		py=e.getY();
		repaint();
		frame.repaint();
		
	}
	public void mouseMoved(MouseEvent e) {
	}
}
@SuppressWarnings("serial")
class Snakespel extends JPanel implements KeyListener, ActionListener,WindowListener{
	private JFrame frame = new JFrame("Snake"),highFrame = new JFrame("Highscore");
	final int L�ngd= 3,pixelstorlek=10;
	private int[] x=new int[50],y=new int[50];
	private String[] highscore= new String[6];
	private int snakel�ngd,posx=100,posy=100,pluppX,pluppY, stringy, s = 1;
	private Timer timer = new Timer(100, this);
	private String riktning = "ner";
	private Random random = new Random();
	private Properties prop = new Properties();
	private boolean f�rlust;
	
	public Snakespel() {

		try {
			prop.load(getClass().getResource("/images/SnakeScore.txt").openStream());
		} catch (Exception e) {
			
		}
		highscore[0]= "";
		highscore[1]= prop.getProperty("Score1","0");
		highscore[2]= prop.getProperty("Score2","0");
		highscore[3]= prop.getProperty("Score3","0");
		highscore[4]= prop.getProperty("Score4","0");
		highscore[5]= prop.getProperty("Score5","0");
		
		setBackground(Color.white);

		frame.add(this);		
		frame.setIconImage(F�nsterIcon);
		frame.setResizable(false);		
		frame.addKeyListener(this);
		frame.setSize(pixelstorlek*50, pixelstorlek*50);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addWindowListener(this);
		
		highFrame.add(new Scorepanel());
		highFrame.setSize(frame.getSize());
		highFrame.setUndecorated(true);
		highFrame.setLocation(frame.getX()-frame.getWidth(),frame.getY());
		highFrame.setVisible(true);
		
		frame.setVisible(true);
		Restart();
	}
	private void GameOver(){
		timer.stop();
		f�rlust = true;
		int hs;
		
		((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
		
		Scanner scanner = new Scanner(highscore[5]);
		hs= scanner.nextInt();
		scanner.close();
		if (snakel�ngd>hs) {
			String string = JOptionPane.showInputDialog("Skriv ditt namn");
			highscore[5]=Integer.toString(snakel�ngd) + " " + string;
			if (snakel�ngd<10) {
				highscore[5]="0"+highscore[5];
			}
			
			Arrays.sort(highscore);
			String[]tillf�lligscore = new String[6];
			tillf�lligscore[1] = highscore[1];
			tillf�lligscore[2] = highscore[2];
			tillf�lligscore[3] = highscore[3];
			tillf�lligscore[4] = highscore[4];
			tillf�lligscore[5] = highscore[5];
			
			highscore[1]= tillf�lligscore[5];
			highscore[2]= tillf�lligscore[4];
			highscore[3]= tillf�lligscore[3];
			highscore[4]= tillf�lligscore[2];
			highscore[5]= tillf�lligscore[1];
			
			prop.setProperty("Score1", (highscore[1]));
			prop.setProperty("Score2", (highscore[2]));
			prop.setProperty("Score3", (highscore[3]));
			prop.setProperty("Score4", (highscore[4]));
			prop.setProperty("Score5", (highscore[5]));
			highFrame.repaint();;
		}
		
		try {
			prop.store(new FileWriter(getClass().getResource("/images/SnakeScore.txt").getFile()),"Inst�llningar f�r Txt.java");
		} catch (Exception e) {}
		
		System.err.println(highscore[1]);
		System.err.println(highscore[2]);
		System.err.println(highscore[3]);
		System.err.println(highscore[4]);
		System.err.println(highscore[5]);
		
		SkrivH�ndelsetext(highscore[1]);
		SkrivH�ndelsetext(highscore[2]);
		SkrivH�ndelsetext(highscore[3]);
		SkrivH�ndelsetext(highscore[4]);
		SkrivH�ndelsetext(highscore[5]);
	}
	private void Restart() {
		posx = random.nextInt(getWidth()/pixelstorlek)*pixelstorlek;
		posy = random.nextInt(getHeight()/pixelstorlek)*pixelstorlek;
      
		if (	posx>frame.getWidth()*0.8||
				posx<frame.getWidth()*0.2||
				posy>frame.getHeight()*0.8||
				posy<frame.getHeight()*0.2) {
			
			System.out.println("R�knar om: " + posx);
			Restart();
		}
		else{		
			String [] arr = {"upp", "ner", "h�ger", "v�nster"};
		
			int select = random.nextInt(arr.length); 
			
			riktning=arr[select];
			snakel�ngd = L�ngd;
			x[1]=posx;
			y[1]=posy;
			PlaceraPlupp();
			timer.start();
			f�rlust = false;
			repaint();
		}
	}
	private void PlaceraPlupp(){
		
		pluppX = random.nextInt(getWidth()/pixelstorlek)*pixelstorlek;
		pluppY = random.nextInt(getHeight()/pixelstorlek)*pixelstorlek;
	}
	public void paintComponent(Graphics g1){
		super.paintComponent(g1);
		
		if(y[1] < 45) {
			stringy = y[1] + 40;
		}
		if (y[1] > 45){
			stringy = y[1] - 20;
		}
		
		Graphics2D g = (Graphics2D)g1;
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (f�rlust) {
			g.setColor(Color.red);
			g.setFont(new Font(null, 0, 25));
			g.drawString("Du f�rlorade! Tryck F2 f�r att spela igen",10 , frame.getHeight()/2);
		}
		g.setColor(Color.red);
		g.drawOval(pluppX, pluppY, 8, 8);
		g.fillOval(pluppX, pluppY, 8, 8);
		g.setColor(Color.black);
		g.drawRect(x[1], y[1], pixelstorlek, pixelstorlek);
		g.fillRect(x[1], y[1], pixelstorlek, pixelstorlek);
		g.setColor(Color.GREEN);
		g.setFont(Mouse.Typsnitt);
		g.drawString(Integer.toString(snakel�ngd), x[1], stringy);
		for (int i = snakel�ngd+1; i >= 2; i--) {
			
			g.setColor(Color.black);
			x[i]=x[i-1];
			y[i]=y[i-1];
			g.drawRect(x[i], y[i], pixelstorlek, pixelstorlek);
			g.fillRect(x[i], y[i], pixelstorlek, pixelstorlek);
			s=1;
		}
		
		
	}

	public void keyTyped(KeyEvent e) {
		
	}
	
	public void keyPressed(KeyEvent e) {
		if (s==1) {
			if(KeyEvent.getKeyText(e.getKeyCode()) == "V�nsterpil"){
				if (riktning!="h�ger"){
					riktning="v�nster";
					s=0;
				}
			}
			else if(KeyEvent.getKeyText(e.getKeyCode()) == "H�gerpil"){
				if (riktning!="v�nster"){
					riktning="h�ger";
					s=0;
				}
			}
			else if(KeyEvent.getKeyText(e.getKeyCode()) == "Upp"){
				if (riktning!="ner"){
					riktning="upp";
					s=0;
				}
			}
			else if(KeyEvent.getKeyText(e.getKeyCode()) == "Nedpil"){
				if (riktning!="upp"){
					riktning="ner";
					s=0;
				}
			}
		}
		if(KeyEvent.getKeyText(e.getKeyCode()) == "F2"){
			if (timer.isRunning()==false) {
				Restart();
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void actionPerformed(ActionEvent e) {
	
		if (e.getSource()==timer){
			
			if (x[1]==pluppX&&y[1]==pluppY) {
				PlaceraPlupp();
				snakel�ngd++;
				((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.asterisk")).run();
				System.err.println(snakel�ngd);
				
			}
			if (riktning=="ner") {
			y[1]=y[1]+pixelstorlek;
			}
			else if (riktning=="upp") {
			
				y[1]=y[1]-pixelstorlek;
			}
			else if (riktning=="h�ger") {
				x[1]=x[1]+pixelstorlek;
			
			}
			else if (riktning=="v�nster") {
				x[1]=x[1]-pixelstorlek;

			}
			for (int i = 2; i <= snakel�ngd; i++) {
				if (x[1]==x[i]&&y[1]==y[i]) {
					System.out.println("GameOver");
					GameOver();
				}
			
			}
			if (x[1]<0) {
				GameOver();
			}
			if (x[1]+pixelstorlek*2>frame.getWidth()) {
				GameOver();
			}
			if (y[1]<0) {
				GameOver();		
			}
			if (y[1]+pixelstorlek*5>frame.getHeight()) {
				GameOver();
			}
			
			frame.repaint();
		}
	}
	@Override
	public void windowOpened(WindowEvent e) {
	}
	@Override
	public void windowClosing(WindowEvent e) {
		timer.stop();
		highFrame.dispose();
	}
	@Override
	public void windowClosed(WindowEvent e) {		
	}
	@Override
	public void windowIconified(WindowEvent e) {
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
	}
	@Override
	public void windowActivated(WindowEvent e) {
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
	}
	private class Scorepanel extends JPanel{
		public Scorepanel() {
			setBackground(Color.white);
		}
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D)g;
			int pos = 50;
			
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(Color.red);
			g2.setFont(new Font(null, 0, 25));
			g.drawString("Highscore:",10 , pos);
			
			for (int i = 1; i < highscore.length; i++) {
				pos=pos+25;
				g.drawString(highscore[i],10 , pos);
			}
		}
	}
	
}
@SuppressWarnings("serial")
class Studsa extends JPanel implements ActionListener{
	JFrame frame = new JFrame("Studsa");
	Timer timer = new Timer(1, this),
			timer2 = new Timer(5, this);
	Random random = new Random();
	int x=1,y=1,a=5,b=5,c=2,d=2,r=100,g=255,bl=25;
	public Studsa(){
		
		frame.setSize(1250,1000);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(F�nsterIcon);
		frame.add(this);
		frame.setUndecorated(true);
		frame.getContentPane().setBackground(Color.white);
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

@SuppressWarnings("serial")
class R�randeMoj�ng extends JPanel implements MouseMotionListener, WindowListener, KeyListener, ActionListener{

	
	JFrame frame = new JFrame("Det h�r �r f�rs�k  " + qq),
		 Vinst = new JFrame("Grattis!");
 	
 	Timer timer = new Timer(1, this);
 
 	JLabel textlabel = new JLabel();
 
 	JTextArea textruta = new JTextArea();
 	
 	Random random = new Random();
 	
 	int r,g,b;
	
	JMenu menu = new JMenu("Arkiv"),
			menu1 = new JMenu("Redigera"),
			menu2 = new JMenu("Hj�lp"),
			�ppnaProgram = new JMenu("�ppna Program");
	JMenuItem item = new JMenuItem("Om"),
			item1 = new JMenuItem("Source kod"),
			item2 = new JMenuItem("Hj�lp"),
			Minirknare = new JMenuItem("Minir�knare"),
			Betyg = new JMenuItem("Betyg"),
			OrginalF�nster = new JMenuItem("R�randeMoj�ng"),
			�terst�ll = new JMenuItem("�terst�ll kvadrat"),
			Pong = new JMenuItem("Pong"),
			Maze = new JMenuItem("Maze"),
			Snake = new JMenuItem("Snake"),
			Mouse = new JMenuItem("Mouse"),
			impossible = new JMenuItem("Impossible"),
			ticTacToe = new JMenuItem("Tic Tac Toe"),
			l�senord = new JMenuItem("L�senord"),
			f�rg = new JMenuItem("Skapa f�rg"),
			avsluta = new JMenuItem("Avsluta"),
			morse = new JMenuItem("Morse"),
			Random = new JMenuItem("Random");
	JMenuBar bar = new JMenuBar();
	
	Robot robot;
	
	Clip clip;

	static int qq = 1;
	static int x = 800;
	static int yy = 900;
	static int y = 300;
	static int ii = 0;
	
	public R�randeMoj�ng(){
		GoJbsBraOchHa.Mouse.AntalF�nster++;
		
		Vinst.setLocationRelativeTo(null);
		Vinst.setSize(190, 100);
		Vinst.add(textruta);
		
		textruta.setFont(new Font("",Font.BOLD, 20));
		textruta.setText("Grattis! Du vann \nefter " + qq + " f�rs�k! :D");
		textruta.setEditable(false);
		textlabel.setOpaque(false);
		
		bar.add(�ppnaProgram);
		bar.add(menu);
		bar.add(menu2);
		bar.add(menu1);
		menu1.add(�terst�ll);
		menu2.add(item);
		menu2.add(item1);
		menu2.add(item2);
		
		�ppnaProgram.add(Mouse);
		�ppnaProgram.add(Minirknare);
		�ppnaProgram.add(Betyg);
		�ppnaProgram.add(OrginalF�nster);
		�ppnaProgram.add(Pong);
		�ppnaProgram.add(Maze);
		�ppnaProgram.add(Snake);
		�ppnaProgram.add(impossible);
		�ppnaProgram.add(ticTacToe);
		�ppnaProgram.add(l�senord);
		�ppnaProgram.add(f�rg);
		�ppnaProgram.add(avsluta);
		�ppnaProgram.add(morse);
		�ppnaProgram.add(Random);
		
		Mouse.addActionListener(this);
		Pong.addActionListener(this);
		�terst�ll.addActionListener(this);
		item1.addActionListener(this);
		Minirknare.addActionListener(this);
		Betyg.addActionListener(this);
		OrginalF�nster.addActionListener(this);
		Maze.addActionListener(this);
		Snake.addActionListener(this);
		impossible.addActionListener(this);
		ticTacToe.addActionListener(this);
		l�senord.addActionListener(this);
		f�rg.addActionListener(this);
		avsluta.addActionListener(this);
		morse.addActionListener(this);
		Random.addActionListener(this);
		
		timer.addActionListener(this);
		timer.start();
		
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		frame.setBackground(Color.gray);
		frame.setForeground(Color.pink);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setIconImage(F�nsterIcon);
		frame.setSize(1845, 800);
		frame.addMouseMotionListener(this);
		frame.addKeyListener(this);
		frame.setResizable(false);
		frame.setJMenuBar(bar);
		frame.addWindowListener(this);
		frame.getContentPane().add(new R�randeMoj�ng3());
		frame.setVisible(true);
		
		
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			
			e.printStackTrace();
		}
		robot.mouseMove(200, 100);
//		String [] names ={"Biologi", "Fysik", "Kemi", "Teknik", "Historia", "Geografi", "Sammh�llskunskap", "Religon", "Sl�jd", "ModernaSpr�k", "Idrott", "HemOchKonsumentkunskap", "Musik", "Bild"};
//	      for( String name : names ) {
//	         System.out.println(name + ".setLayout(new GridLayout(3,1));");
//	      }
		}

	public void mouseMoved(MouseEvent e) {
		
		x = e.getX() -3;
		y = e.getY() -80;
		
		if (x < 150 && y > 425){if (ii == 0){
			new GameOver();frame.dispose();
		}}
		if (x > 950 && x < 1100 && y > 150 && y < 550){if (ii == 0){
			new GameOver();frame.dispose();
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
			}
		
	}
		
		if (x > 1200 && y < 5){
			new GameOver();
			frame.dispose();
		}
		
		if (x < 450 && y > 375 && y < 495 ){
			new GameOver();
			frame.dispose();
		}

		
		System.out.println("Musen r�r sig p�: " + e.getX()  + ", " + e.getY());
//
//		if (x < 0){
//			System.exit(3);
//		}
//		if (x > 1851){
//			System.exit(3);
//		}
//		
//		if (y < 0){
//			System.exit(3);
//		}
//		if (y > 700){ 
//			System.exit(3);
//			}
//		

		if ( x == 50) {
						if (y == 50){
							
						System.exit(0);	
						}
			}
		
		}
	
	public void keyTyped(KeyEvent e){

	}
	
	public void keyPressed(KeyEvent e) { 
		
		
		if (KeyEvent.getKeyText(e.getKeyCode()) == "V�nsterpil") {
			x = x - 1;
			System.out.println("1 pixel till v�nster");
			frame.repaint();}
		if (KeyEvent.getKeyText(e.getKeyCode()) == "H�gerpil") {
			x = x + 1;
			System.out.println("1 pixel till h�ger");
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
		if (x < 150){if (ii == 0){
			new GameOver();frame.dispose();
		}}
		if (x > 1840){if (ii == 0){
			new GameOver();frame.dispose();
		}}
		
		if (y < 0){if (ii == 0){
			new GameOver();frame.dispose();
		}}
		if (y > 700){ if (ii == 0){
			new GameOver();frame.dispose();
}
		}}

	public void keyReleased(KeyEvent arg0) {
		
	}
	
	public void windowActivated(WindowEvent arg0) {
		
	}
	
	public void windowClosed(WindowEvent arg0) {
		GoJbsBraOchHa.Mouse.AntalF�nster--;
		
		
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
		
		
		
		System.out.println("Musen drar p�:" + x + " , " + y);
		
		frame.repaint();
		
		if (x < 150 && y > 374){if (ii == 0){
			new GameOver();frame.dispose();
		}}
		if (x > 950 && x < 1100 && y > 150 && y < 550){if (ii == 0){
			new GameOver();frame.dispose();
		}}
		if (x > 650 && x < 790 && y > 550){
			try {
				Clip clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(getClass().getResource("/images/tada.wav")));
				clip.start();
			} catch (Exception e) {
				((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
				JOptionPane.showMessageDialog(null, "Filen hittades inte", "Ljud", JOptionPane.ERROR_MESSAGE);
				
			}
		
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
			}
		
	}
		
		if (x < 450 && y > 375 && y < 495 ){
			new GameOver();
			frame.dispose();
		}
		}

	public void actionPerformed(ActionEvent arg0) {
		 
	
		if (arg0.getSource() == timer){
			 Container contentPane = frame.getContentPane();
			    contentPane.add(new R�randeMoj�ng3());
			   
			if (frame.isVisible() == false){
				timer.stop();
			}
	
		}
		
		if (arg0.getSource() == Mouse){
			frame.dispose();
			new Mouse();
		}
		
		if (arg0.getSource() == �terst�ll){
			x = 300;
			y = 800;
			frame.revalidate();
			frame.repaint();
			Container contentPane = frame.getContentPane();
		    contentPane.add(new R�randeMoj�ng3());
			
		}
		
		
		if (arg0.getSource() == f�rg){
			new SkapaF�rg();
			frame.dispose();
		}
		
		if (arg0.getSource() == impossible){
			frame.dispose();
			new Impossible("HAHA!");
		}
		
		if (arg0.getSource() == Maze){
			new Maze();
			frame.dispose();
		}
		
		if (arg0.getSource() == avsluta){
			new Avsluta();
			frame.dispose();
		}
		if (arg0.getSource() == morse) {
			frame.dispose();
			new Morse();
		}
		
		if (arg0.getSource() == Random){
			new random();
			frame.dispose();
		}
		
		if (arg0.getSource() == Pong){
			new Pong();
			frame.dispose();
			
		}
		
		if (arg0.getSource() == Snake){
			new Snake();
		}
		
		if (arg0.getSource() == Minirknare){
			new Minir�knare();
		}
		
		if (arg0.getSource() == ticTacToe){
			new TicTacToe();
			frame.dispose();
		}
		
		if (arg0.getSource() == l�senord){
			new Pass();
			frame.dispose();
		}
		
		if (arg0.getSource() == Betyg){
			new Merit();
		}
		
		if (arg0.getSource() == OrginalF�nster){
			new R�randeMoj�ng();
		}
		
		if (arg0.getSource() == item1){
			try
			{
			     Runtime.getRuntime().exec("notepad.exe C:\\Users\\Glenn\\GoJb.java\\SourceKod.txt");
			} catch (Exception ex)
			{
			     ex.printStackTrace();
		
	}}

}


class R�randeMoj�ng3 extends JPanel implements ActionListener {
	int[] r�d=new int[91],gr�n=new int[91],bl�=new int[91];
	public void paint (Graphics gr) {
		Graphics2D g2 = (Graphics2D) gr;

		g2.setColor(Color.BLUE);
		g2.fillRect(0, 425, 150, 1000);

		g2.setColor(Color.GREEN);
		gr.fill3DRect(1000, 200, 100, 350, false);

		g2.setColor(Color.ORANGE);
		gr.fill3DRect(1155, 0, 110, 495, true);

		g2.setColor(Color.MAGENTA);
		gr.fillRect(205, 550, 1000, 100);

		g2.setColor(Color.WHITE);
		gr.drawRect(700, 650, 90, 90);

		g2.setColor(new Color(233,5,6));
		g2.fill3DRect(150, 425, 300, 70, true);

		g2.setColor(Color.BLACK);
		g2.setFont(new Font("", Font.ROMAN_BASELINE,20));
		g2.drawString("Dra genom labyrinten till den \nf�rgglada kvadraten f�r att vinna.\n Lycka till! :D", 300, 150);

		r�d[r�d.length-1] = random.nextInt(255);
		gr�n[r�d.length-1] = random.nextInt(255);
		bl�[r�d.length-1] = random.nextInt(255);

		for (int i = 1; i <= r�d.length-2; i++) {
			r�d[i] = r�d[i+1];
			gr�n[i] = gr�n[i+1];
			bl�[i] = bl�[i+1];

		}
		boolean h = true;
		int e=700,s=650;
		for (int i = r�d.length-2; i >= 1; i--) {

			if (h) {
				h=false;

				g2.setColor(new Color(r�d[i],gr�n[i],bl�[i]));
				g2.drawRect(e, s, i, i);
				e++;
				s++;
			}
			else h=true;

		}

		g2.setColor(Color.cyan);
		g2.fillRect(x, y, 50,50);
}

	public void actionPerformed(ActionEvent arg0) {
	
	}
	
}
static class GameOver implements ActionListener, WindowListener{
	JButton b1 = new JButton("Spela igen");
	JButton b2 = new JButton("Avsluta");
	JFrame ram = new JFrame("GAME OVER");
	public GameOver(){
	
		ram.setIconImage(F�nsterIcon);
		ram.add(b1);
		ram.add(b2);
		ram.setVisible(true);
		ram.setSize(500, 500);
		ram.setLayout(new GridLayout(2, 1));
		
		R�randeMoj�ng.ii = 1;
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		ram.addWindowListener(this);
		ram.setDefaultCloseOperation(3);
	}
	
	public void actionPerformed(ActionEvent arg0) {
		
		if (arg0.getSource() == b1){
			R�randeMoj�ng.qq = R�randeMoj�ng.qq + 1;
			R�randeMoj�ng.ii = 0;
			new R�randeMoj�ng();
			ram.dispose();
			R�randeMoj�ng.x = 800;
			R�randeMoj�ng.y = 300;
			
		}
		if (arg0.getSource() == b2) {
			System.exit(3);
		}
	}

	public void windowOpened(WindowEvent e) {
		
	}
	
	public void windowClosing(WindowEvent e) {
		
	}
	
	public void windowIconified(WindowEvent e) {
		
	}
	
	
	public void windowDeiconified(WindowEvent e) {  
		
	}
	
	
	public void windowActivated(WindowEvent e) {
		
	}
	
	
	public void windowDeactivated(WindowEvent e) {
	
	}
	
	public void windowClosed(WindowEvent e) {
	}
	}
}


class Minir�knare implements ActionListener, KeyListener{
	
	
	
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
	
	JLabel r�knes�tt = new JLabel(),
			summa = new JLabel();


	public Minir�knare(){
	
		JFrame frame = new JFrame();

		frame.setLayout(new BorderLayout(5,5));
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		JPanel f�nster = new JPanel();
		
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
		
		f�nster.setLayout(new GridLayout(5,3,5,5));
		frame.addKeyListener(this);
		f�nster.add(b1);
		f�nster.add(b2);
		f�nster.add(b3);
		f�nster.add(b4);
		f�nster.add(b5);
		f�nster.add(b6);
		f�nster.add(b7);
		f�nster.add(b8);
		f�nster.add(b9);
		f�nster.add(b10);
		f�nster.add(b0);
		f�nster.add(b11);
		f�nster.add(b12);
		f�nster.add(b13);
		f�nster.add(b14);
		
		textruta.setEditable(true);
		
		
		frame.add(f�nster,BorderLayout.CENTER);
		frame.pack();
		
		frame.add(summa);
		frame.add(r�knes�tt);
		
		
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
			
			R�knaUt();
			r�knes�tt.setText("+");
				
		}
		if (e.getSource()==b11){textruta.append("-");}
		if (e.getSource()==b12){textruta.append("*");}
		if (e.getSource()==b13){textruta.append("/");}
		if (e.getSource()==b14){textruta.append("=");}

	int a = 0;System.out.println(a);
			try {
			 a = Integer.parseInt(textruta.getText());
		} catch (Exception sda) {
			
			a = 0;
		}
		
		}
	

	private void R�knaUt() {
		int a,b;
		try {
			 a = Integer.parseInt(summa.getText());
		} catch (Exception e) {
			// 
			a = 0;
		}
		
		try {
			b = Integer.parseInt(textruta.getText());
		} catch (Exception e) {
			// 

			b = 0;
		}
		
		if (r�knes�tt.getText() == "+"){
			summa.setText(Integer.toString(a+b));
		}
		else if (r�knes�tt.getText() == "-") {
			summa.setText(Integer.toString(a-b));
		}
		
		else if (r�knes�tt.getText() == "*") {
			summa.setText(Integer.toString(a*b));
		}
		else if (r�knes�tt.getText() == "/") {
			summa.setText(Double.toString(a/b));
		}
		else {
			summa.setText(Double.toString(a+b));
		}
		textruta.setText(null);
		
	}

	public void keyPressed(KeyEvent  e) {
			
		System.out.println(e.getKeyCode());
		char c = e.getKeyChar();
		String fj = String.valueOf(c);
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
		e.getKeyCode() ==97||
		e.getKeyCode() ==98||
		e.getKeyCode() ==99||
		e.getKeyCode() ==100|
		e.getKeyCode() ==101||
		e.getKeyCode() ==102||
		e.getKeyCode() ==103||
		e.getKeyCode() ==104||
		e.getKeyCode() ==105||
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
//		if (e.getKeyCode()== 49){
//			textruta.append("1");
//			}
//		if (e.getKeyCode()== 50){
//			textruta.append("2");
//			}
//		if (e.getKeyCode()== 51){
//			textruta.append("3");
//			}
//		if (e.getKeyCode()== 52){
//			textruta.append("4");
//			}
//		if (e.getKeyCode()== 53){
//			textruta.append("5");
//			}
//		if (e.getKeyCode()== 54){
//			textruta.append("6");
//			}
//		if (e.getKeyCode()== 55){
//			textruta.append("7");
//			}
//		if (e.getKeyCode()== 56){
//			textruta.append("8");
//			}
//		if (e.getKeyCode()== 57){
//			textruta.append("9");
//			}
//		if (e.getKeyCode()== 58){
//			textruta.append("1");
//			}
	}

	public void keyReleased(KeyEvent arg0) {
		
	}

	public void keyTyped(KeyEvent e) {
		
		}
	
			}

class Merit implements MouseMotionListener, WindowListener, KeyListener, ActionListener{


	
	JFrame huvudframe = new JFrame(),
			Svenska = new JFrame("Svenska"),
			Engelska = new JFrame("Engelska"),
			Matte = new JFrame("Matte"),
			Biologi = new JFrame("Biologi"),
			Fysik = new JFrame("Fysik"),
			Kemi = new JFrame("Kemi"),
			Teknik = new JFrame("Teknik"),
			Sl�jd = new JFrame("Sl�jd"),
			ModernaSpr�k = new JFrame("ModernaSpr�k"),
			Bild = new JFrame("Bild"),
			Religon = new JFrame("Religon"),
			Geografi = new JFrame("Geografi"),
			Historia = new JFrame("Historia"),
			Samh�llskunskap = new JFrame("Samh�llskunskap"),
			Musik = new JFrame("Musik"),
			Idrott = new JFrame("Idrott"),
			HemOchKonsumentkunskap = new JFrame("HemOchKonsumentkunskap"),
			Resultat = new JFrame("Resultat");
			

	JLabel resultatlabel = new JLabel("",JLabel.CENTER);
	
	JButton tillbaka = new JButton("Tillbaka"),
			B�rja = new JButton("B�rja"),
			Avsluta = new JButton("Avsluta"),
			A = new JButton("A"),
			B = new JButton("B"),
			C = new JButton("C"),
			D = new JButton("D"),
			E = new JButton("E"),
			F = new JButton("F");
	
	double x = 0;
				

		public Merit(){
	
		huvudframe.setVisible(true);
		huvudframe.setLocationRelativeTo(null);
		huvudframe.setShape(null);
		huvudframe.pack();
		huvudframe.setSize(200,200);

		A.setSize(30, 30);
		B.setSize(30, 30);
		C.setSize(30,30);
		D.setSize(30,30);
		E.setSize(30,30);
		F.setSize(30,30);
		
		huvudframe.addMouseMotionListener(this);
		Svenska.addKeyListener(null);
		huvudframe.addKeyListener(this);
		B�rja.setPreferredSize(new Dimension(30, 30));
		
		A.addActionListener(this);
		B.addActionListener(this);
		C.addActionListener(this);
		D.addActionListener(this);
		E.addActionListener(this);
		F.addActionListener(this);
		

		huvudframe.add(B�rja);
		
		B�rja.addActionListener(this);
	
		Svenska.setLocationRelativeTo(null);
		Engelska.setLocationRelativeTo(null);
		Matte.setLocationRelativeTo(null);
		Biologi.setLocationRelativeTo(null);
		Fysik.setLocationRelativeTo(null);
		Kemi.setLocationRelativeTo(null);
		Bild.setLocationRelativeTo(null);
		Idrott.setLocationRelativeTo(null);
		HemOchKonsumentkunskap.setLocationRelativeTo(null);
		Samh�llskunskap.setLocationRelativeTo(null);
		Historia.setLocationRelativeTo(null);
		Geografi.setLocationRelativeTo(null);
		Religon.setLocationRelativeTo(null);
		Musik.setLocationRelativeTo(null);
		Sl�jd.setLocationRelativeTo(null);
		ModernaSpr�k.setLocationRelativeTo(null);
		Teknik.setLocationRelativeTo(null);
		
		
		Svenska.pack();
		Engelska.pack();
		Matte.pack();
		Biologi.pack();
		Fysik.pack();
		Kemi.pack();
		Bild.pack();
		Idrott.pack();  
		HemOchKonsumentkunskap.pack();
		Samh�llskunskap.pack();
		Historia.pack();
		Geografi.pack();
		Religon.pack();
		Musik.pack();
		Sl�jd.pack();
		ModernaSpr�k.pack();
	
		Svenska.add(A);
		Svenska.add(B);
		Svenska.add(C);
		Svenska.add(D);
		Svenska.add(E);
		Svenska.add(F);
		
		
		Svenska.setLayout(new GridLayout(3,1));
		Engelska.setLayout(new GridLayout(3,1));
		Matte.setLayout(new GridLayout(3,1));
		Biologi.setLayout(new GridLayout(3,1));
		Fysik.setLayout(new GridLayout(3,1));
		Kemi.setLayout(new GridLayout(3,1));
		Teknik.setLayout(new GridLayout(3,1));
		Historia.setLayout(new GridLayout(3,1));
		Geografi.setLayout(new GridLayout(3,1));
		Samh�llskunskap.setLayout(new GridLayout(3,1));
		Religon.setLayout(new GridLayout(3,1));
		Sl�jd.setLayout(new GridLayout(3,1));
		ModernaSpr�k.setLayout(new GridLayout(3,1));
		Idrott.setLayout(new GridLayout(3,1));
		HemOchKonsumentkunskap.setLayout(new GridLayout(3,1));
		Musik.setLayout(new GridLayout(3,1));
		Bild.setLayout(new GridLayout(3,1));
		
		Svenska.setSize(300, 250);
		Engelska.setSize(300, 250);
		Matte.setSize(300, 250);
		Biologi.setSize(300, 250);
		Fysik.setSize(300, 250);
		Kemi.setSize(300, 250);
		Teknik.setSize(300, 250);
		Historia.setSize(300, 250);
		Geografi.setSize(300, 250);
		Samh�llskunskap.setSize(300, 250);
		Religon.setSize(300, 250);
		Sl�jd.setSize(300, 250);
		ModernaSpr�k.setSize(300, 250);
		Idrott.setSize(300, 250);
		HemOchKonsumentkunskap.setSize(300, 250);
		Musik.setSize(300, 250);
		Bild .setSize(300, 250);
		
		/* 
		 	Ordning
		 	
		 	Svenska
			Engelska
			Matte
			Biologi
			Fysik
			Kemi
			Teknik
			Historia
			Geografi
			Samh�llskunskap
			Religon
			Sl�jd 
			ModernaSpr�k
			Idrott
			HemOchKonsumentkunskap
			Musik
			Bild 
			*/
		}
		

		public void mouseDragged(MouseEvent arg0) { 
			
		}
		
		public void mouseMoved(MouseEvent arg0) {
			
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
			
			if (Svenska.isVisible() == true){

				Svenska.setVisible(false);
				Engelska.setVisible(true);

				Engelska.add(A);
				Engelska.add(B);
				Engelska.add(C);
				Engelska.add(D);
				Engelska.add(E);
				Engelska.add(F);
				System.out.println(x);
			
			}
			
			else if (Engelska.isVisible() == true){
				
				Engelska.setVisible(false);
				Matte.setVisible(true);

			Matte.add(A);
			Matte.add(B);
			Matte.add(C);
			Matte.add(D);
			Matte.add(E);
			Matte.add(F);
			System.out.println(x);
			}
			else if (Matte.isVisible() == true){
				Matte.setVisible(false);
				Biologi.setVisible(true);

				Biologi.add(A);
				Biologi.add(B);
				Biologi.add(C);
				Biologi.add(D);
				Biologi.add(E);
				Biologi.add(F);
			System.out.println(x);
			}
			else if (Biologi.isVisible() == true){

				Biologi.setVisible(false);
				Fysik.setVisible(true);

				Fysik.add(A);
				Fysik.add(B);
				Fysik.add(C);
				Fysik.add(D);
				Fysik.add(E);
				Fysik.add(F);
				System.out.println(x);
				
			}
			
			else if (Fysik.isVisible() == true){

				Fysik.setVisible(false);
				Kemi.setVisible(true);

				Kemi.add(A);
				Kemi.add(B);
				Kemi.add(C);
				Kemi.add(D);
				Kemi.add(E);
				Kemi.add(F);
				System.out.println(x);

			}
			else if (Kemi.isVisible() == true){
			Kemi.setVisible(false);
			Teknik.setVisible(true);
			
			Teknik.add(A);
			Teknik.add(B);
			Teknik.add(C);
			Teknik.add(D);
			Teknik.add(E);
			Teknik.add(F);
			System.out.println(x);
			}
			else if (Teknik.isVisible() == true){
			
			Teknik.setVisible(false);
			Historia.setVisible(true);
			
			Historia.add(A);
			Historia.add(B);
			Historia.add(C);
			Historia.add(D);
			Historia.add(E);
			Historia.add(F);
			System.out.println(x);
			}
			else if (Historia.isVisible() == true){
			
			Historia.setVisible(false);
			Geografi.setVisible(true);
			
			Geografi.add(A);
			Geografi.add(B);
			Geografi.add(C);
			Geografi.add(D);
			Geografi.add(E);
			Geografi.add(F);
			System.out.println(x);
			}
			else if (Geografi.isVisible() == true){
			
			Geografi.setVisible(false);
			Samh�llskunskap.setVisible(true);
			
			Samh�llskunskap.add(A);
			Samh�llskunskap.add(B);
			Samh�llskunskap.add(C);
			Samh�llskunskap.add(D);
			Samh�llskunskap.add(E);
			Samh�llskunskap.add(F);
			System.out.println(x);
			}
			else if (Samh�llskunskap.isVisible() == true){
			
			Samh�llskunskap.setVisible(false);
			Religon.setVisible(true);
			
			Religon.add(A);
			Religon.add(B);
			Religon.add(C);
			Religon.add(D);
			Religon.add(E);
			Religon.add(F);
			System.out.println(x);
			}
			else if (Religon.isVisible() == true){
				
			
			Religon.setVisible(false);
			Sl�jd.setVisible(true);
			
			Sl�jd.add(A);
			Sl�jd.add(B);
			Sl�jd.add(C);
			Sl�jd.add(D);
			Sl�jd.add(E);
			Sl�jd.add(F);
			System.out.println(x);
			}
			else if (Sl�jd.isVisible() == true){
			
			Sl�jd.setVisible(false);
			ModernaSpr�k.setVisible(true);
			
			ModernaSpr�k.add(A);
			ModernaSpr�k.add(B);
			ModernaSpr�k.add(C);
			ModernaSpr�k.add(D);
			ModernaSpr�k.add(E);
			ModernaSpr�k.add(F);
			System.out.println(x);
			}
			else if (ModernaSpr�k.isVisible() == true){
			
			ModernaSpr�k.setVisible(false);
			Idrott.setVisible(true);
			
			Idrott.add(A);
			Idrott.add(B);
			Idrott.add(C);
			Idrott.add(D);
			Idrott.add(E);
			Idrott.add(F);
			System.out.println(x);
			}
			else if (Idrott.isVisible() == true){
			
			Idrott.setVisible(false);
			HemOchKonsumentkunskap.setVisible(true);
			
			HemOchKonsumentkunskap.add(A);
			HemOchKonsumentkunskap.add(B);
			HemOchKonsumentkunskap.add(C);
			HemOchKonsumentkunskap.add(D);
			HemOchKonsumentkunskap.add(E);
			HemOchKonsumentkunskap.add(F);
			System.out.println(x);
			}
			else if (HemOchKonsumentkunskap.isVisible() == true){
			
			HemOchKonsumentkunskap.setVisible(false);
			Musik.setVisible(true);
			
			Musik.add(A);
			Musik.add(B);
			Musik.add(C);
			Musik.add(D);
			Musik.add(E);
			Musik.add(F);
			System.out.println(x);
			}
			else if (Musik.isVisible() == true){
			
			Musik.setVisible(false);
			Bild.setVisible(true);
			
			Bild.add(A);
			Bild.add(B);
			Bild.add(C);
			Bild.add(D);
			Bild.add(E);
			Bild.add(F);
			System.out.println(x);
		
			}
			
			else if (Bild.isVisible() == true){
				
				Bild.setVisible(false);
				Resultat.setVisible(true);
				Resultat.setLayout(new BorderLayout());
				resultatlabel.setText(String.valueOf(x));
				Resultat.add(resultatlabel,BorderLayout.CENTER);
				resultatlabel.setHorizontalTextPosition(SwingConstants.RIGHT);
			
				Resultat.setSize(Bild.getSize());
				Resultat.setLocationRelativeTo(null);
				resultatlabel.setFont(new Font("fsgadh",Font.BOLD,45));
				
				if (x < 100){
					
					try {
						
						Clip clip = AudioSystem.getClip();
						clip.open(AudioSystem.getAudioInputStream(getClass().getResource("/images/dusuger.wav")));
						clip.start();
					} catch (Exception ebn) {
						((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
						JOptionPane.showMessageDialog(null, "Filen hittades inte", "Ljud", JOptionPane.ERROR_MESSAGE);
				}}
				else {
					try {
				
						Clip clip = AudioSystem.getClip();
						clip.open(AudioSystem.getAudioInputStream(getClass().getResource("/images/tada.wav")));
						clip.start();
				} catch (Exception ebn) {
					((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
					JOptionPane.showMessageDialog(null, "Filen hittades inte", "Ljud", JOptionPane.ERROR_MESSAGE);
				}
			
			}
				}
			
			else if (e.getSource() == B�rja){
				huvudframe.setVisible(false);
				Svenska.setVisible(true);
			}}
		
		
		public void keyPressed(KeyEvent e) {
			
			if (Svenska.isVisible() == true){
				if (e.getSource() == A){
					x = x + 20;
				Svenska.setVisible(false);
				Engelska.setVisible(true);
		}
				if (e.getSource() == B){
					x = x + 20;
				Svenska.setVisible(false);
				Engelska.setVisible(true);
		}
				if (e.getSource() == A){
					x = x + 20;
				Svenska.setVisible(false);
				Engelska.setVisible(true);
		}
				if (e.getSource() == A){
					x = x + 20;
				Svenska.setVisible(false);
				Engelska.setVisible(true);
		}
				}
			
		}
		
		public void keyReleased(KeyEvent arg0) { 
			
		}
		
		public void keyTyped(KeyEvent e) {
			
			
			if (KeyEvent.getKeyText(e.getKeyCode()) == "d"){
				System.out.println("iygsd");
			}
			if (e.getKeyChar() == 67){
				
			}
		
			if (e.getKeyChar() == 68){
			}
			if (e.getKeyChar() == 69){
			
			}
			if (e.getKeyChar() == 70){
			
			}
		}
		
		public void windowActivated(WindowEvent arg0) {
			
		}
		
		public void windowClosed(WindowEvent arg0) {
			
		}
		
		public void windowClosing(WindowEvent arg0) {
			
		}
		
		public void windowDeactivated(WindowEvent arg0) {
			
		}
		
		public void windowDeiconified(WindowEvent arg0) {
			
		}
		
		public void windowIconified(WindowEvent arg0) {
			
		}
		
		public void windowOpened(WindowEvent arg0) {
			
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
		timer.start();
		
	}

	public void paintComponent (Graphics g) {
	    Graphics2D g2 = (Graphics2D) g;
	    g2.setColor(Color.BLUE);
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
				System.err.println("�iuds");
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
class Maze extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener{

	JFrame  level1 = new JFrame("Level 1");

	static JFrame startframe = new JFrame("B�rja");
	
	JButton b�rja = new JButton("Start");
	

	int x = 3;
	int y = 600;	
	public Maze(){
		
		startframe.setVisible(true);
		startframe.add(b�rja);
		startframe.setSize(80, 80);
		startframe.setLocation(740, 290);
		startframe.setResizable(false);
		startframe.setDefaultCloseOperation(3);
		
		b�rja.addActionListener(this);
		
		level1.setBackground(Color.BLACK);
		level1.setSize(418, 430);
		level1.setLocationRelativeTo(null);
		level1.setResizable(false);
		level1.add(this);	       
		level1.addMouseMotionListener(this);
		level1.repaint();
		level1.revalidate();
		level1.setResizable(false);
		level1.setDefaultCloseOperation(3);
		
	}
	
	public void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
	  
	  	g2.setColor(Color.white);
	    g2.fillRect(0, 350, 350, 50);
	    
	    g2.fillRect(0, 0, 200, 40);
	    
	    g2.fillRect(0, 0, 50, 2000);
	    
	    g2.fillRect(300, 50, 50, 300);
	    
	    g2.fillRect(170, 50, 170, 50);
	    
	    g2.fillRect(140, 50, 50, 190);
	    
	    g2.setColor(Color.CYAN);
	    g2.fillRect(150, 200, 30, 30);   
	    
	    g2.setColor(Color.RED);
	    g2.fillRect(x, y, 25, 25);
	    
	}
	
	public void mouseClicked(MouseEvent arg0) {
		
	}

	public void mouseEntered(MouseEvent arg0) {
			
	}

	public void mouseExited(MouseEvent arg0) {
		
	}
	
	public void mousePressed(MouseEvent arg0) {
		
	}
	
	public void mouseReleased(MouseEvent arg0) {
		
	}

	public void keyPressed(KeyEvent arg0) {
	
	}

	public void keyReleased(KeyEvent arg0) {
	
	}

	public void keyTyped(KeyEvent arg0) {
		
	}

	public void actionPerformed(ActionEvent arg0) {
		
		
		if (arg0.getSource() == b�rja){
			level1.setVisible(true);
			startframe.dispose();
		}
	
	}

	public void mouseDragged(MouseEvent arg0) {
		
	}

	public void mouseMoved(MouseEvent e) {
	
		x = e.getX() - 17;
		y = e.getY() - 45;
		System.out.println("Musen r�r sig p�: " + x  + ", " + y);
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
	}}
@SuppressWarnings("serial")
class level2 extends JPanel implements MouseMotionListener{
	int y, x;
	static JFrame level2 = new JFrame("Level 2");

	

	public level2(){
		
	
		
		level2.setBackground(Color.BLACK);
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
	
	
	}
	
	public void paintComponent (Graphics g) {
		  Graphics2D g2 = (Graphics2D) g;
		  
		  	g2.setColor(Color.white);
		    g2.fillRect(140, 190, 45, 45);
		    
		    g2.fillRect(145, 195, 35, 180);
		    
		    g2.fillRect(150, 340, 250, 35);
		    
		    g2.fillRect(365, 341, 35, -100);
		    
		    g2.fillRect(230, 241, 150, 35);
		    
		    g2.fillRect(230, 100, 35, 171);
		    
		    g2.fillRect(230, 100, 169, 35);
		    
		    g2.fillRect(364, 10, 35, 105);
		    
		    g2.setColor(Color.CYAN);
		    g2.fillRect(369, 17, 25, 25);   
		    
		    g2.setColor(Color.RED);
		    g2.fillRect(x, y, 20, 20);
		    
		  

				}
	
	
	public void mouseDragged(MouseEvent arg0) {
		
		
	}
	
	public void mouseMoved(MouseEvent e) {
	
		
		x = e.getX() - 17;
		y = e.getY() - 45;
		System.out.println("Musen r�r sig p�: " + x  + ", " + y);
		level2.repaint();
		
		
if (y > 355 || x > 380 || y > 256 && y < 340 && x > 165 && x < 365||y < 339 && x > 165 && x < 230|| y < 241 && y > 115 && x > 245|| x < 364 && y < 100||
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
@SuppressWarnings("serial")
class level3 extends JPanel implements MouseMotionListener{
	int y, x;
	static JFrame level3 = new JFrame("Level 3");

	
	public level3(){
		
	
		
		level3.setBackground(Color.BLACK);
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
	
	
	}
	
	public void paintComponent (Graphics g) {
		  Graphics2D g2 = (Graphics2D) g;
		  
		  	g2.setColor(Color.white);
		    g2.fillRect(270, 5, 137, 37);
		    
		    g2.fillRect(270, 5, 32, 130);
		    
		    g2.fillRect(70, 135, 232, 27);
		    
		    g2.fillRect(70, 135, 23, 100);
		    
		    g2.fillRect(70, 235, 300, 23);
		    
		    g2.fillRect(353, 235, 23, 68);
		    
		    g2.fillRect(163, 370, 30, 50);
		    
		    g2.fillRect(172, 283, 200, 20);
		    
		    g2.fillRect(170, 283, 17, 150);
		    
		    
		    
		    g2.setColor(Color.CYAN);
		    g2.fillRect(170, 375, 17, 17);   
		    
		    g2.setColor(Color.RED);
		    g2.fillRect(x, y, 12, 12);
		    
		  

				}
	
	public void mouseDragged(MouseEvent arg0) {
		
		
	}
	
	public void mouseMoved(MouseEvent e) {
		
		
		x = e.getX() - 17;
		y = e.getY() - 45;
		System.out.println("Musen r�r sig p�: " + x  + ", " + y);
		level3.repaint();
		
		if (x > 290 && y < 235 && y > 30||y < 235 && y > 150 && x > 82|| x > 395|| x > 364 && y > 30|| y < 310 && y > 291 && x > 173|| y > 246 &&
				y < 283 && x < 353|| x < 170 && y > 246 && y < 310||x < 270 && y < 135){
			level3.dispose();
			Maze.startframe.setVisible(true);
		}
		
		if(y > 310){
			
			level3.dispose();
			new M�l();
		}
		
	}
	
}

class M�l{
	
	JFrame frame = new JFrame("Haha");
	

	public M�l(){
		frame.add(new JLabel(new ImageIcon(getClass().getResource("/images/Bild.jpg"))));
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(level3.level3);
		frame.setDefaultCloseOperation(3);
		


		try {
			Clip clips = AudioSystem.getClip();
			clips.open(AudioSystem.getAudioInputStream(getClass().getResource("/images/Ljud.wav")));
			clips.start();
		} catch (Exception ex) {
			((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
			JOptionPane.showMessageDialog(null, "Filen hittades inte", "Ljud", JOptionPane.ERROR_MESSAGE);
		}
	
	

	}
}
@SuppressWarnings("serial")
class Snake extends JPanel implements KeyListener, ActionListener{
	
	JFrame frame = new JFrame("Snake");
	int x = 250,y = 250,a,b = 1,bredd = 25,h�jd = 100,q;
	Timer timer = new Timer(30,this);
	Random r = new Random();
	

	public Snake(){
		
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setBackground(Color.BLACK);
		frame.setVisible(true);
		frame.add(this);
		timer.start();
		frame.addKeyListener(this);
		r.nextInt(5);
		System.out.println(r.nextInt());
		
	}
	public void paintComponent (Graphics g) {
		  Graphics2D g2 = (Graphics2D) g;
		  
		  	g2.setColor(Color.GREEN);
		    g2.fillRect(x ,y,bredd,h�jd);
		    frame.repaint();
	}

	public void keyPressed(KeyEvent arg0) {

		 if(KeyEvent.getKeyText(arg0.getKeyCode()) == "V�nsterpil"){
			a = -1;
			b = 0;
		
				if (bredd < h�jd){
			
			
			q = h�jd;
			h�jd = bredd;
			bredd = q;
			
			
			frame.repaint();
			System.out.println(a + "," + b);
		}}
	else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "H�gerpil"){
		a = +1;
		b = 0;
	
			if (bredd < h�jd){
		
		
		q = h�jd;
		h�jd = bredd;
		bredd = q;
		
		
		frame.repaint();
		System.out.println(a + "," + b);
			}}
	else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Upp"){
			
		a = 0;
		b = -1;
	
			if (bredd > h�jd){
		
		
		q = h�jd;
		h�jd = bredd;
		bredd = q;
		
		
		frame.repaint();
		System.out.println(a + "," + b);
			}
			}
	else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Nedpil"){
		
		a = 0;
		b = +1;
	
			if (bredd > h�jd){
		
		
		q = h�jd;
		h�jd = bredd;
		bredd = q;
		
		
		frame.repaint();
		System.out.println(a + "," + b);
			}
			}
		
	}

	
	public void keyReleased(KeyEvent arg0) {
	
		
	}

	
	public void keyTyped(KeyEvent arg0) {
	
		
	}

	
	public void actionPerformed(ActionEvent arg0) {
	if (arg0.getSource()==timer){
		
		
	
		x = x + a;
		y = y + b;
		frame.repaint();
	}
		
	}}
	


@SuppressWarnings("serial")
class Impossible extends JPanel implements WindowListener, ActionListener,
KeyListener, MouseInputListener{

	JFrame frame = new JFrame();
	Timer timer = new Timer(1, this),
			timer300 = new Timer(300, this),
			timer3 = new Timer(3, this);
	Robot robot;
	String string = new String();
	int x,y,r,g,b;
	String a;
	
	public Impossible(String textString){
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		  Image image = toolkit.getImage(getClass().getResource("/images/Nope.png"));
		  Cursor c = toolkit.createCustomCursor(image , new Point(frame.getX(),
		     frame.getY()), "img");
		  frame.setCursor (c);
		a=textString;
		frame.setSize(1920,1030 );
		frame.add(this);
		frame.setLocationRelativeTo(null);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.add(this);
		frame.setDefaultCloseOperation(0);
		setBackground(Color.WHITE);
		frame.addWindowListener(this);
		frame.addMouseMotionListener(this);
		frame.addKeyListener(this);
		frame.addMouseListener(this);
		
		frame.setAlwaysOnTop(true);
		
		
		timer.start();
		timer300.start();
		timer3.start();
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			
			e.printStackTrace();
		}
		if(frame.isVisible() == true){
			robot.mouseMove(960, 515);

			
		}
		
	}
	
	public void mouseDragged(MouseEvent arg0) {
	
		robot.mouseMove(960, 515);
	}

	
	public void mouseMoved(MouseEvent e) {
		
		
		robot.mouseMove(960, 515);
		System.out.println(frame.getX() + ",,,," + frame.getY());
		
//	if (frame.getY() > frame.getHeight()/2 - 10){
//		frame.dispose();
//	}
}
	
	
	public void windowActivated(WindowEvent arg0) {
	}

	
	public void windowClosed(WindowEvent arg0) {
	}

	
	public void windowClosing(WindowEvent arg0) {
	}

	
	public void windowDeactivated(WindowEvent arg0) {
	}

	
	public void windowDeiconified(WindowEvent arg0) {
	}

	
	public void windowIconified(WindowEvent arg0) {
	}

	
	public void windowOpened(WindowEvent arg0) {
	}
	
	public void actionPerformed(ActionEvent arg0) {
	if (arg0.getSource() == timer){
		
		if(frame.isVisible() == true){
			robot.mouseMove(960, 515);
			
			
	}
		
	}
	
	if (timer3 == arg0.getSource()){
		Random random = new Random();
		r = random.nextInt(255);
		System.out.println(r);
		
		g = random.nextInt(255);
		System.out.println(g);
		
		b = random.nextInt(255);
		System.out.println(b);
		
	}
	
	
	if (arg0.getSource() == timer300){
		Random random = new Random();
		x = random.nextInt(1880);
		System.out.println(x);
		
		y = random.nextInt(990);
		System.out.println(y);
	
	}
	}
	
	
	public void keyPressed(KeyEvent arg0) {
	}
	
	public void keyReleased(KeyEvent arg0) {
	}

	
	public void keyTyped(KeyEvent arg0) {
		if(arg0.getKeyChar() == '�'){
			System.exit(3);
				}
		else{
			frame.add(this);
			repaint();
			
				}
			
		
		}
	
	public void mouseClicked(MouseEvent arg0) {
	
	frame.add(this);
	repaint();
	
	}
	
	
	public void mouseEntered(MouseEvent arg0) {
	}
	
	public void mouseExited(MouseEvent arg0) {
	}
	
	public void mousePressed(MouseEvent arg0) {
		
		frame.add(this);
		repaint();
		
	}
	
	public void mouseReleased(MouseEvent arg0) {
		
		frame.add(this);
		repaint();
		
	}
	public void paintComponent (Graphics gr) {
		  Graphics2D g2 = (Graphics2D) gr;
//		  super.paintComponent(g);
		  
		  frame.setBackground(Color.WHITE);
		  
		  	g2.setColor(new Color(r,g,b));
		  	g2.setFont(new Font("dhghdg", Font.ITALIC, 30));
		  	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		    g2.drawString(a, x, y);
		    
	}

	}

class TicTacToe implements MouseInputListener, KeyListener, ActionListener{

	Timer timer = new Timer(50, this);
	
	JFrame frame = new JFrame("Tic Tac Toe"),
			Vinst = new JFrame(),
			tur = new JFrame();
	
	String str�ng = new String();
	
	JLabel[] label = new JLabel[10];
	JLabel vinstlabel = new JLabel(),
			turLabel = new JLabel();
			
	ImageIcon o = new ImageIcon(getClass().getResource("/images/O.png")),
			x = new ImageIcon(getClass().getResource("/images/X.png"));

	int a,�;
	
	public TicTacToe(){
		
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new GridLayout(3,3,3,3));
		timer.start();
		
		for(int i = 1;i < label.length; i++){
			label[i] = new JLabel();
			label[i].setBackground(Color.WHITE);
			label[i].setOpaque(true);
			label[i].addMouseListener(this);
			frame.add(label[i]);
		}
		frame.getContentPane().setBackground(Color.BLACK);
		
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
		turLabel.setForeground(Color.WHITE);
		turLabel.setOpaque(true);
		
		turLabel.setBackground(Color.BLACK);
		
		tur.setUndecorated(true);
		
		tur.setVisible(true);
		
		Vinst.setLocation(frame.getWidth()+205,frame.getHeight()+270);
		Vinst.setSize(500, 100);
		
		vinstlabel.setHorizontalAlignment(JLabel.CENTER);
		vinstlabel.setFont(new Font("dslf",Font.ROMAN_BASELINE,30));
		Vinst.add(vinstlabel);
        vinstlabel.setForeground(Color.BLACK);
		
		vinstlabel.setOpaque(true);

	}
	
	public void mouseClicked(MouseEvent e) {

		if(a== 0 && e.getSource() == label[9]){
			if (label[9].getIcon() == null){
				X(9);
			}}
		if(a== 0 && e.getSource() == label[8]){
			if (label[8].getIcon() == null){
				X(8);
			}}
		if(a== 0 && e.getSource() == label[7]){
			if (label[7].getIcon() == null){
				X(7);
			}}
		if(a== 0 && e.getSource() == label[6]){
			if (label[6].getIcon() == null){
				X(6);
			}}
		if(a== 0 && e.getSource() == label[5]){
			if (label[5].getIcon() == null){
				X(5);
			}}
		if(a== 0 && e.getSource() == label[4]){
			if (label[4].getIcon() == null){
				X(4);
			}}
		if(a== 0 && e.getSource() == label[3]){
			if (label[3].getIcon() == null){
				X(3);
			}}
		if(a== 0 && e.getSource() == label[2]){
			if (label[2].getIcon() == null){
				X(2);
			}}
		if(a== 0 && e.getSource() == label[1]){
			if (label[1].getIcon() == null){
				X(1);
			}}
		if(a== 1 && e.getSource() == label[9]){
			if (label[9].getIcon() == null){
				O(9);
			}}
		if(a== 1 && e.getSource() == label[8]){
			if (label[8].getIcon() == null){
				O(8);
			}}
		if(a== 1 && e.getSource() == label[7]){
			if (label[7].getIcon() == null){
				O(7);
			}}
		if(a== 1 && e.getSource() == label[6]){
			if (label[6].getIcon() == null){
				O(6);
			}}
		if(a== 1 && e.getSource() == label[5]){
			if (label[5].getIcon() == null){
				O(5);
			}}
		if(a== 1 && e.getSource() == label[4]){
			if (label[4].getIcon() == null){
				O(4);
			}}
		if(a== 1 && e.getSource() == label[3]){
			if (label[3].getIcon() == null){
				O(3);
			}}
		if(a== 1 && e.getSource() == label[2]){
			if (label[2].getIcon() == null){
				O(2);
			}}
		if(a== 1 && e.getSource() == label[1]){
			if (label[1].getIcon() == null){
				O(1);
			}
		}
	}
	
	public void mousePressed(MouseEvent e) {
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
	
	
	public void mouseReleased(MouseEvent e) {
	}

	
	public void mouseEntered(MouseEvent e) {
	}

	
	public void mouseExited(MouseEvent e) {
	}

	
	public void mouseDragged(MouseEvent e) {
	}

	
	public void mouseMoved(MouseEvent e) {
	}
	
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == timer){

			if (a == 2){
				turLabel.setText("Tryck r f�r restart");	
			}

			if (a == 0){
				turLabel.setText("Nu spelar X");
			}

			if (a == 1){
				turLabel.setText("Nu spelar O");
			}


			frame.setAlwaysOnTop(true);

			if(label[1].getIcon() == o||
					label[1].getIcon() == x){

				if(label[2].getIcon() == o||
						label[2].getIcon() == x){

					if(label[3].getIcon() == o||
							label[3].getIcon() == x){

						if(label[4].getIcon() == o||
								label[4].getIcon() == x){

							if(label[5].getIcon() == o||
									label[5].getIcon() == x){

								if(label[6].getIcon() == o||
										label[6].getIcon() == x){

									if(label[7].getIcon() == o||
											label[7].getIcon() == x){

										if(label[8].getIcon() == o||
												label[8].getIcon() == x){

											if(label[9].getIcon() == o||
													label[9].getIcon() == x){

												Vinst.setVisible(true);
						//						repaint();
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
						� = 2;
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
						� = 2;
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
						� = 2;
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
						� = 2;
					}
				}
				if (label[8].getIcon() == o){
					if (label[9].getIcon() == o){
						Vinst.setVisible(true);
	//					repaint();
						a = 2;
						vinstlabel.setText("O vann");
						� = 2;
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
						� = 2;
					}
				}
				if (label[4].getIcon() == o){
					if (label[7].getIcon()== o){
						Vinst.setVisible(true);
//						repaint();
						a = 2;
						vinstlabel.setText("O vann");
						� = 2;
					}
				}
				if(label[2].getIcon() == o){
					if(label[3].getIcon() == o){


						Vinst.setVisible(true);
//						repaint();
						vinstlabel.setText("O vann");

						� = 2;
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
						� = 1;
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
						� = 1;
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
						� = 1;
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
						� = 1;
					}
				}
				if (label[8].getIcon() == x){
					if (label[9].getIcon() == x){
						Vinst.setVisible(true);
	//					repaint();
						a = 2;
						vinstlabel.setText("X vann");
						� = 1;
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
						� = 1;
					}
				}
				if (label[4].getIcon() == x){
					if (label[7].getIcon()== x){
						Vinst.setVisible(true);
//						repaint();
						a = 2;
						vinstlabel.setText("X vann");
						� = 1;
					}
				}
				if(label[2].getIcon() == x){
					if(label[3].getIcon() == x){


						Vinst.setVisible(true);
//						repaint();
						vinstlabel.setText("X vann");
						� = 1;
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
			if (� == 2){
				a = 0;
			}
			else if (� == 1){
				a = 1;
			}
			
			Vinst.setVisible(false);
			
			frame.repaint();
			
		}
		
	}
	
	public void keyReleased(KeyEvent e) {
	}
}

final class Pass implements ActionListener{

	private int x;

	private static String OK = "What Ever!";

	private Timer timer = new Timer(1, this);	

	private JPasswordField passwordField;
	
	private JFrame anv�ndare = new JFrame();

	private JFrame frame = new JFrame("Verifiera dig!");

	private JButton anv�ndareJakob = new JButton("Jakob"),
			anv�ndareGlenn = new JButton("Glenn");

	private JLabel label = new JLabel("Skriv L�senord -->");
	
	private char[] correctPassword = {'U','g','g','e','n','0','6','8','4'};

	public void actionPerformed(ActionEvent e) {

		if (timer == e.getSource()) {
			char[] pass = passwordField.getPassword();
			
				try {
					if(Arrays.equals(Toolkit.getDefaultToolkit().getSystemClipboard()
							.getData(DataFlavor.stringFlavor).toString()
							.toCharArray(),correctPassword)){
						pass = correctPassword;
					}
				} catch (Exception e1){}

			if (Arrays.equals(pass,correctPassword)) {
				
				frame.dispose();
				anv�ndare.setVisible(true);
				timer.stop();
			}
			x++;
			if(x == 4000){
				new Impossible("Tiden gick ut!! Datorn sp�rrad...");
			}

		}

		else if(e.getSource() == anv�ndareJakob){
			new Mouse();
			anv�ndare.dispose();
		}
		else if(e.getSource() == anv�ndareGlenn){
			new R�randeMoj�ng();
			anv�ndare.dispose();
		}

	}

	Pass() {

		passwordField = new JPasswordField(10);
		passwordField.setActionCommand(OK);
		passwordField.addActionListener(this);

		anv�ndare.add(anv�ndareJakob);
		anv�ndare.add(anv�ndareGlenn);
		anv�ndare.setIconImage(F�nsterIcon);
		anv�ndare.setLayout(new FlowLayout());
		anv�ndare.setDefaultCloseOperation(3);
		anv�ndare.pack();
		anv�ndare.setLocationRelativeTo(null);

		anv�ndareGlenn.addActionListener(this);
		anv�ndareJakob.addActionListener(this);

		frame.setUndecorated(true);
		frame.setAlwaysOnTop(true);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		frame.add(label);
		frame.add(passwordField);
		frame.setIconImage(F�nsterIcon);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setMinimumSize(frame.getSize());
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		timer.start();

	}

}
@SuppressWarnings("serial")
class SkapaF�rg extends JPanel implements ActionListener{
	
	JFrame frame = new JFrame("Skapa en egen f�rg");
	
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
	
	Random random = new Random();
	
	Color y;
	
	public  SkapaF�rg() {

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
	
		setForeground(Color.blue);
		
		setOpaque(true);
		
		red.setFont(new Font("luyya",Font.BOLD,40));
		green.setFont(new Font("luyya",Font.BOLD,40));
		blue.setFont(new Font("luyya",Font.BOLD,40));
		
		red.setForeground(Color.red);
		green.setForeground(Color.green);
		blue.setForeground(Color.blue);
		
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
		frame.setIconImage(F�nsterIcon);
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

		}	
	}
}

class Avsluta implements ActionListener{
			
	JButton b1 = new JButton("St�ng av", new ImageIcon(getClass().getResource("/images/icon.png")));
	JButton b2 = new JButton("Logga ut", new ImageIcon(getClass().getResource("/images/icon2.png")));
	JButton b3 = new JButton("Starta om", new ImageIcon(getClass().getResource("/images/icon3.png")));
	JButton b4 = new JButton("Vilol�ge", new ImageIcon(getClass().getResource("/images/icon4.png")));
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
		
		b1.setToolTipText("St�nger av datorn");
		
		f1.add(p1);
		f1.add(s1);
		f1.setResizable(true);
		f1.setAlwaysOnTop(true);
		f1.setDefaultCloseOperation(3);
		f1.getContentPane().setLayout(new BoxLayout(f1.getContentPane(),BoxLayout.Y_AXIS));
		f1.setIconImage(F�nsterIcon);
		f1.pack();
		f1.setLocationRelativeTo(null);
		f1.setVisible(true);
		}

	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1){ 
			try {
				Runtime.getRuntime().exec("C:\\windows\\system32\\shutdown.exe -s -t " + s1.getValue() + " -c \"Hejd�\"");
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
				Runtime.getRuntime().exec("C:\\windows\\system32\\shutdown.exe -r -t " + s1.getValue() + " -c \"Hejd�\"");
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
		
		button.setFont(GoJbsBraOchHa.Mouse.Typsnitt);
		button.addMouseListener(this);
		button.addKeyListener(this);
		button.setBackground(Color.black);
		button.setForeground(Color.white);
		button.setOpaque(true);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		frame.setIconImage(F�nsterIcon);
		frame.addKeyListener(this);
		frame.add(button);
		frame.pack();
		frame.setSize(frame.getWidth()+50, frame.getHeight());
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		try {

			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(getClass().getResource(Filnamn)));
			
		} catch (Exception e) {
			((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
			JOptionPane.showMessageDialog(null, "Filen: \"" + Filnamn + "\" hittades inte", "Ljud", JOptionPane.ERROR_MESSAGE);
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
		GoJbsBraOchHa.Mouse.V�nta(100);

		clip.close();
		try {

			
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(getClass().getResource(Filnamn)));
			
		} catch (Exception e) {
			((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
			JOptionPane.showMessageDialog(null, "Filen: \"" + Filnamn + "\" hittades inte", "Ljud", JOptionPane.ERROR_MESSAGE);
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
			GoJbsBraOchHa.Mouse.V�nta(100);

			clip.close();
			try {

				
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(getClass().getResource(Filnamn)));
				
			} catch (Exception e) {
				((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
				JOptionPane.showMessageDialog(null, "Filen: \"" + Filnamn + "\" hittades inte", "Ljud", JOptionPane.ERROR_MESSAGE);
			}
			
	}
}

class random implements ActionListener{
	
	JPanel panel = new JPanel();
	
	JButton button = new JButton("Start");
	
	GoJbFrame frame = new GoJbFrame();
	
	public random(){
		
		frame.setTitle("Random");
		frame.setLayout(new BorderLayout());
		frame.add(panel,BorderLayout.SOUTH);
		frame.add(button,BorderLayout.EAST);
		
		frame.setIconImage(F�nsterIcon);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
	}
}
