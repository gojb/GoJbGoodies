package GoJbsBraOchHa;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import com.sun.xml.internal.messaging.saaj.soap.ver1_1.Envelope1_1Impl;

@SuppressWarnings("serial")
public class Mouse extends JPanel implements 	ActionListener,
												MouseInputListener,
												KeyListener,
												WindowListener{
	
	JFrame			HuvudFönster = new JFrame("Hej Hej :D"), 
					HändelseFönster = new JFrame("Händelser"),
					HastighetsFönster =  new JFrame(),
					om = new JFrame("Om"),
					Räknare = new JFrame("Miniräknare"),
					Laddfönster = new JFrame("Startar..."),
					AvslutningsFönster = new JFrame("Avslutar...");
	
	JTextArea 		text = new JTextArea(), 
					Räknartext = new JTextArea();
			
	JPanel 			KnappPanel = new JPanel(), 
					RäknarKnappar = new JPanel(),
					RäknarPanel = new JPanel();

	JMenuBar 		MenyRad = new JMenuBar();
	
	JMenu 			Arkiv = new JMenu("Arkiv"), 
					Hjälp = new JMenu("Hjälp"),
					Redigera = new JMenu("Redigera"),
					Färgbyte = new JMenu("Byt bakgrundsfärg"),
					TextFärgByte = new JMenu("Byt Textfärg");

	JMenuItem 		Avsluta = new JMenuItem("Avsluta"), 
				  	Om = new JMenuItem("Om"),
				  	Visa = new JMenuItem("Visa"),
				  	Dölj = new JMenuItem("Dölj"),
				  	Nytt = new JMenuItem("Nytt"),
				  	TextByte = new JMenuItem("Ändra text på remsa"),
				  	Grön = new JMenuItem("Grön"),
				  	Röd = new JMenuItem("Röd"),
				  	Blå = new JMenuItem("Blå"),
				  	Gul = new JMenuItem("Gul"),
				  	Hastighet = new JMenuItem("Ändra hastighet på piltangenterna"),
				  	Händelse = new JMenuItem("Visa Händelsefönster"),
				  	Räkna = new JMenuItem("Öppna Miniräknare"),
				  	Spelknapp = new JMenuItem("Spela Pong"),
				  	Rörande = new JMenuItem("Öppna RörandeMojäng",new ImageIcon(getClass().getResource("/images/Nopeliten.png"))),
				  	StudsItem = new JMenuItem("Öppna Studsande Objekt"),
				  	Snake = new JMenuItem("Spela Snake");
	
	JButton 		knapp1 = new JButton("Blå"),
					knapp2 = new JButton("Grön"),
					knapp3 = new JButton("Röd"),
					knapp4 = new JButton("Gul"),
					OK = new JButton("Klar"),
					Miniränkarknapp0 = new JButton("0"),
					Miniränkarknapp1 = new JButton("1"),
					Miniränkarknapp2 = new JButton("2"),
					Miniränkarknapp3 = new JButton("3"),
					Miniränkarknapp4 = new JButton("4"),
					Miniränkarknapp5 = new JButton("5"),
					Miniränkarknapp6 = new JButton("6"),
					Miniränkarknapp7 = new JButton("7"),
					Miniränkarknapp8 = new JButton("8"),
					Miniränkarknapp9 = new JButton("9"),
					MiniränkarknappPlus = new JButton("+"),
					MiniränkarknappMinus = new JButton("-"),
					MiniränkarknappGånger = new JButton("*"),
					MiniränkarknappDelat = new JButton("/"),
					MiniränkarknappLikamed = new JButton("="),
					Autoscrollknapp = new JButton("Stäng av autoscroll"),
					RensKnapp = new JButton("Rensa"),
					C = new JButton("C"),
					Punkt = new JButton(".");
	
	JScrollPane 	Jaha = new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
											JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	JProgressBar	LaddstapelStart = new JProgressBar(0,100),
				 	LaddstapelAvslut = new JProgressBar(0, 100);
	
	JLabel 			omtext = new JLabel("<html>Hallåj! Det här programmet är skapat av GoJbs Javaprogrammering"),
					Laddtext = new JLabel("Startar program..."),
					AvslutningsText = new JLabel("Avslutar program..."),
					Summa = new JLabel(""),
					Räknesätt = new JLabel();
	
 	JSlider 		Slide = new JSlider(JSlider.HORIZONTAL,0,100,10);
 		
	boolean 		autoscroll = true,
					nyräkning = false;
	
	int				FlyttHastighet = 10,posX = 125, posY = 75;
	
	static int		AntalFönster = 0;

	double 			a = 0,
					b = 0;
	
	Timer 			StartTimer = new Timer(2, this),
					SlutTimer = new Timer(2, this);
	
	Pongspel		pong = new Pongspel();
	
	Robot			robot;
	
	Color			Färg = new Color(0, 0, 255);
	
	static Font 	Typsnitt = new Font("Arial", 0, 40);
	
	public static 	ImageIcon FönsterIcon = null;
	
	public Mouse(){
		
		
		
		String hexColour = Integer.toHexString(Färg.getRGB() & 0xffffff);
		 hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
		  System.out.println("#" + hexColour);
		
		Laddtext.setFont(Typsnitt);
		Laddtext.setHorizontalAlignment(JLabel.CENTER);
		Laddfönster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Laddfönster.setLayout(new BorderLayout(10,10));	
		Laddfönster.add(LaddstapelStart,BorderLayout.CENTER);
		Laddfönster.add(Laddtext,BorderLayout.NORTH);
		Laddfönster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.EAST);
		Laddfönster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.WEST);
		Laddfönster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.SOUTH);
		Laddfönster.setSize(400, 100);
		Laddfönster.setAlwaysOnTop(true);
		Laddfönster.setResizable(false);
		Laddfönster.setLocationRelativeTo(null);
		Laddfönster.setIconImage(FönsterIcon.getImage());
		Laddfönster.getContentPane().setBackground(Color.yellow);
		Laddfönster.setUndecorated(true);
		Laddfönster.setVisible(true);		
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		SkrivHändelsetext("Välkommen!");
		Avsluta.addActionListener(this);
		Om.addActionListener(this);
		Visa.addActionListener(this);
		Dölj.addActionListener(this);
		Nytt.addActionListener(this);
		TextByte.addActionListener(this);
		Gul.addActionListener(this);
		Röd.addActionListener(this);
		Grön.addActionListener(this);
		Blå.addActionListener(this);
		Hastighet.addActionListener(this);
		Händelse.addActionListener(this);
		OK.addActionListener(this);
		Räkna.addActionListener(this);
		Miniränkarknapp0.addActionListener(this);
		Miniränkarknapp1.addActionListener(this);
		Miniränkarknapp2.addActionListener(this);
		Miniränkarknapp3.addActionListener(this);
		Miniränkarknapp4.addActionListener(this);
		Miniränkarknapp5.addActionListener(this);
		Miniränkarknapp6.addActionListener(this);
		Miniränkarknapp7.addActionListener(this);
		Miniränkarknapp8.addActionListener(this);
		Miniränkarknapp9.addActionListener(this);
		MiniränkarknappGånger.addActionListener(this);
		MiniränkarknappDelat.addActionListener(this);
		MiniränkarknappMinus.addActionListener(this);
		MiniränkarknappPlus.addActionListener(this);
		MiniränkarknappLikamed.addActionListener(this);
		Autoscrollknapp.addActionListener(this);
		RensKnapp.addActionListener(this);
		KnappPanel.addMouseListener(this);
		C.addActionListener(this);
		Punkt.addActionListener(this);
		Spelknapp.addActionListener(this);
		Rörande.addActionListener(this);
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
		Arkiv.add(Rörande);
		Arkiv.add(StudsItem);
		Arkiv.add(Räkna);
		Arkiv.add(Spelknapp);
		Arkiv.add(Snake);
		Arkiv.addSeparator();
		Arkiv.add(Avsluta);
		
		Redigera.add(Färgbyte);
		Redigera.add(TextByte);
		Redigera.add(TextFärgByte);
		Redigera.add(Hastighet);
		Redigera.add(Händelse);
		
		Hjälp.add(Om);
		
		Färgbyte.add(Dölj);
		Färgbyte.add(Visa);
		
		TextFärgByte.add(Röd);
		TextFärgByte.add(Grön);
		TextFärgByte.add(Blå);
		TextFärgByte.add(Gul);
		
		MenyRad.add(Arkiv);
		MenyRad.add(Redigera);
		MenyRad.add(Hjälp);
		
		setOpaque(true);
		setBackground(Färg);
		setForeground(Color.YELLOW);
		addMouseMotionListener(this);
		addMouseListener(this);

		text.setEditable(false);
		
		setSize(10000,10000);
		
		HändelseFönster.setSize(500,500);
		HändelseFönster.setLayout(new BorderLayout());
		HändelseFönster.add(Autoscrollknapp,BorderLayout.NORTH);
		HändelseFönster.add(Jaha,BorderLayout.CENTER);
		HändelseFönster.add(RensKnapp,BorderLayout.SOUTH);
		HändelseFönster.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		HändelseFönster.setAlwaysOnTop(true);
		HändelseFönster.setResizable(false);
		HändelseFönster.setIconImage(FönsterIcon.getImage());
		
		Slide.setPaintTicks(true);
		Slide.setPaintLabels(true);
		Slide.setMajorTickSpacing(10);
		Slide.setMinorTickSpacing(1);
		
		HastighetsFönster.setSize(500,200);
		HastighetsFönster.setLayout(new BorderLayout());
		HastighetsFönster.add(Slide,BorderLayout.NORTH);
		HastighetsFönster.add(OK,BorderLayout.CENTER);
		HastighetsFönster.add(Box.createRigidArea(new Dimension(100,100)),BorderLayout.WEST);
		HastighetsFönster.add(Box.createRigidArea(new Dimension(100,100)),BorderLayout.EAST);
		HastighetsFönster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.SOUTH);
		HastighetsFönster.setLocationRelativeTo(null);
		HastighetsFönster.setResizable(false);
		HastighetsFönster.revalidate();
		

		KnappPanel.add(knapp1);
		KnappPanel.add(knapp2);
		KnappPanel.add(knapp3);
		KnappPanel.add(knapp4);
				
		omtext.setVerticalTextPosition(1);
		omtext.setFont(Typsnitt);
		omtext.addMouseListener(this);
		om.setSize(300,300);
		om.add(omtext);
		om.setIconImage(FönsterIcon.getImage());
		om.setLocationRelativeTo(HuvudFönster);
		
		HuvudFönster.setJMenuBar(MenyRad);
		HuvudFönster.setSize(800, 800);
		HuvudFönster.setLayout(new BorderLayout());
		HuvudFönster.setMinimumSize(new Dimension(400,400));
		HuvudFönster.addKeyListener(this);
		HuvudFönster.addWindowListener(this);
		HuvudFönster.setIconImage(FönsterIcon.getImage());
		HuvudFönster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.NORTH);
		HuvudFönster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.WEST);
		HuvudFönster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.EAST);
		HuvudFönster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.SOUTH);
		HuvudFönster.add(this,BorderLayout.CENTER);
		HuvudFönster.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		HuvudFönster.setLocationRelativeTo(null);		
		HuvudFönster.revalidate();
		HuvudFönster.repaint();
		
		RäknarKnappar.setLayout(new GridLayout(5,5,5,5));
		RäknarKnappar.add(Miniränkarknapp1);
		RäknarKnappar.add(Miniränkarknapp2);
		RäknarKnappar.add(Miniränkarknapp3);
		RäknarKnappar.add(MiniränkarknappPlus);
		RäknarKnappar.add(Miniränkarknapp4);
		RäknarKnappar.add(Miniränkarknapp5);
		RäknarKnappar.add(Miniränkarknapp6);
		RäknarKnappar.add(MiniränkarknappMinus);
		RäknarKnappar.add(Miniränkarknapp7);
		RäknarKnappar.add(Miniränkarknapp8);
		RäknarKnappar.add(Miniränkarknapp9);
		RäknarKnappar.add(MiniränkarknappGånger);
		RäknarKnappar.add(Punkt);
		RäknarKnappar.add(Miniränkarknapp0);
		RäknarKnappar.add(MiniränkarknappLikamed);	
		RäknarKnappar.add(MiniränkarknappDelat);
		RäknarKnappar.add(C);
		RäknarKnappar.setBackground(Color.white);
		Miniränkarknapp0.setPreferredSize(new Dimension(120,100));
		
		RäknarPanel.add(Summa);
		RäknarPanel.add(Räknesätt);
		RäknarPanel.add(Räknartext);
		RäknarPanel.setBackground(Color.white);

		Räknartext.setFont(Typsnitt);
		Summa.setFont(Typsnitt);
		Räknesätt.setFont(Typsnitt);
		
		Räknare.setLayout(new BorderLayout());
		Räknare.add(RäknarPanel,BorderLayout.NORTH);
		Räknare.add(RäknarKnappar,BorderLayout.CENTER);
		Räknare.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.WEST);
		Räknare.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.EAST);
		Räknare.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.SOUTH);
		Räknare.setBackground(Color.WHITE);
		Räknare.pack();
		Räknare.setIconImage(FönsterIcon.getImage());
		Räknare.getContentPane().setBackground(Color.white);
		
		AvslutningsText.setFont(Typsnitt);
		AvslutningsText.setHorizontalAlignment(JLabel.CENTER);
		
		AvslutningsFönster.setLayout(new BorderLayout(10,10));
		AvslutningsFönster.add(LaddstapelAvslut,BorderLayout.CENTER);
		AvslutningsFönster.add(AvslutningsText,BorderLayout.NORTH);
		AvslutningsFönster.setUndecorated(true);
		AvslutningsFönster.setSize(Laddfönster.getSize());
		AvslutningsFönster.setResizable(false);
		AvslutningsFönster.setAlwaysOnTop(true);
		AvslutningsFönster.setIconImage(FönsterIcon.getImage());
		AvslutningsFönster.setLocationRelativeTo(null);
		AvslutningsFönster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		AvslutningsFönster.getContentPane().setBackground(Color.yellow);
		AvslutningsFönster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.EAST);
		AvslutningsFönster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.WEST);
		AvslutningsFönster.add(Box.createRigidArea(new Dimension(5,5)),BorderLayout.SOUTH);
		
		LaddstapelAvslut.setValue(100);
		
		AntalFönster++;
		
		System.out.println(AntalFönster);
		
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
					FönsterIcon = new ImageIcon(getClass().getResource("/images/Java-icon.png"));
					} catch (Exception e) {
					((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
			    	JOptionPane.showMessageDialog(null, "ImageIcon hittades inte","Filfel",JOptionPane.ERROR_MESSAGE);
					}
				
					}
					}
		
		new setImageIcon();
		new Pass();
		
	}
	

	public void Miniräknare(){
		Räknare.setVisible(true);

	}

	public void mouseDragged(MouseEvent e) {
		SkrivHändelsetext("Du drog musen: " + e.getX() + ", " + e.getY());
		posX = e.getX();
		posY = e.getY();
//		repaint();
//		ee.repaint();
		HuvudFönster.repaint();
	
	}

	public void mouseMoved(MouseEvent e) {
		
		SkrivHändelsetext("Du rörde musen: " + e.getX() + ", " + e.getY());
		if(e.getX() == 50 && e.getY() == 50){
			System.exit(1);
		
		}
	
	}
	
	public void actionPerformed(ActionEvent knapp) {
//		System.out.println(knapp.getSource());
		
		
		
		if (knapp.getSource() == Avsluta){	
			AvslutningsFönster.setVisible(true);
			
			SlutTimer.start();
			
		}
		else if(knapp.getSource() == Om){
			om.setVisible(true);
			
		}
		else if(knapp.getSource() == knapp1){
			Färg = Color.blue;
			setBackground(Färg);
			knapp1.setEnabled(false);
			knapp2.setEnabled(true);
			knapp3.setEnabled(true);
			knapp4.setEnabled(true);
		}
		else if(knapp.getSource() == knapp2){
			Färg = Color.GREEN;
			setBackground(Färg);
			knapp1.setEnabled(true);
			knapp2.setEnabled(false);
			knapp3.setEnabled(true);
			knapp4.setEnabled(true);
			
		}
		else if(knapp.getSource() == knapp3){
			Färg = Color.red;
			setBackground(Färg);
			knapp1.setEnabled(true);
			knapp2.setEnabled(true);
			knapp3.setEnabled(false);
			knapp4.setEnabled(true);
	
		}	
		else if(knapp.getSource() == knapp4){
			Färg = Color.yellow;
			setBackground(Färg);
			knapp1.setEnabled(true);
			knapp2.setEnabled(true);
			knapp3.setEnabled(true);
			knapp4.setEnabled(false);
				
		}
		else if(knapp.getSource() == Visa){
			HuvudFönster.add(KnappPanel,BorderLayout.SOUTH);
			
		}
		else if(knapp.getSource() == Dölj){
			HuvudFönster.remove(KnappPanel);
			HuvudFönster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.SOUTH);
			
		}
		else if(knapp.getSource() == Nytt){
			new Mouse();
			
		
		}
		else if (knapp.getSource() == TextByte){
			setTexten();
			repaint();
			
		}
		else if (knapp.getSource() == Blå){
			setForeground(Color.BLUE);
			text.append("Textfärg ändrad till Blå");
		}
		else if (knapp.getSource() == Röd){
			setForeground(Color.RED);
			
		}
		else if (knapp.getSource() == Grön){
			setForeground(Color.GREEN);
			
		}
		else if (knapp.getSource() == Gul){
			setForeground(Color.YELLOW);
			
		}
		else if (knapp.getSource() == Hastighet){
			HastighetsFönster.setVisible(true);

		}
		else if (knapp.getSource() == Händelse){
			HändelseFönster.setVisible(true);
			HuvudFönster.toFront();
			
		}
		else if (knapp.getSource() == OK){
			FlyttHastighet = Slide.getValue();
			HastighetsFönster.dispose();
			
		}
		else if (knapp.getSource() == Räkna){
			Miniräknare();
			
		}
		else if (knapp.getSource() == MiniränkarknappDelat){ 
			if (Räknesätt.getText()==("")) {
				Räknesätt.setText("del");
			}
			RäknaUt();
			Räknesätt.setText("/");
			nyräkning = false;
		}
		else if (knapp.getSource() == MiniränkarknappGånger){ 
			RäknaUt();
			Räknesätt.setText("*");
			nyräkning = false;
		}
		else if (knapp.getSource() == MiniränkarknappMinus){ 
			RäknaUt();
			Räknesätt.setText("-");
			nyräkning = false;
		}
		else if (knapp.getSource() == MiniränkarknappPlus){ 
			RäknaUt();
			Räknesätt.setText("+");
			nyräkning = false;
			
		}
		else if (knapp.getSource()==C) {
			Räknesätt.setText(null);
			Summa.setText(null);
			Räknartext.setText(null);
		}
		
		if (nyräkning==true){
			nyräkning = false;
			C.doClick();
			
		}
		if (knapp.getSource() == MiniränkarknappLikamed){
			RäknaUt();
			Räknesätt.setText("");
			nyräkning = true;
		}
		else if (knapp.getSource() == Miniränkarknapp0){ 
			 Räknartext.append("0");
			 
		}
		else if (knapp.getSource() == Miniränkarknapp1){ 
			 Räknartext.append("1");
			 
		}
		else if (knapp.getSource() == Miniränkarknapp2){ 
			 Räknartext.append("2");
			 
		}
		else if (knapp.getSource() == Miniränkarknapp3){ 
			 Räknartext.append("3");
			 
			 	}
		else if (knapp.getSource() == Miniränkarknapp4){ 
			 Räknartext.append("4");
			 
			 	}
		else if (knapp.getSource() == Miniränkarknapp5){ 
			 Räknartext.append("5");
			 
			 	}
		else if (knapp.getSource() == Miniränkarknapp6){ 
			 Räknartext.append("6");
			 
			 	}
		else if (knapp.getSource() == Miniränkarknapp7){ 
			 Räknartext.append("7");
			 
			 	}
		else if (knapp.getSource() == Miniränkarknapp8){ 
			 Räknartext.append("8");
			 
			 	}
		else if (knapp.getSource() == Miniränkarknapp9){ 
			 Räknartext.append("9");
			 
		}
		else if (knapp.getSource() == Punkt) {
			Räknartext.append(".");
		}
		else if (knapp.getSource() == Autoscrollknapp){
			if (autoscroll == true) {
				autoscroll = false;
				Autoscrollknapp.setText("Slå på autoscroll");
				SkrivHändelsetext("Autoscroll avstängt");
			}
			else{
				autoscroll = true;
				Autoscrollknapp.setText("Stäng av autoscroll");
				SkrivHändelsetext("Autoscroll påslaget");
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
					Laddfönster.dispose();
					HuvudFönster.setVisible(true);
					StartTimer.stop();
					
					robot.mouseMove(HuvudFönster.getX() + HuvudFönster.getWidth()/2,HuvudFönster.getY() + HuvudFönster.getHeight()/2);
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
			pong.Starta();
		}
		else if (knapp.getSource()==Rörande) {
			new RörandeMojäng();
			HuvudFönster.dispose();
			
		}
		else if (knapp.getSource()==StudsItem) {
			new Studsa();
			
		}
		else if (knapp.getSource()==Snake) {
			new Snakespel();
			
		}
		
		HuvudFönster.revalidate();
		HuvudFönster.repaint();
		
	}
	

	public void mouseClicked(MouseEvent arg0) {
		
	}

	public void mouseEntered(MouseEvent e) {
		setBackground(Färg);
		
	}

	public void mouseExited(MouseEvent e) {
		setBackground(Color.gray);
	}

	public void mousePressed(MouseEvent e) {
		System.out.println("cd");
		if (e.getSource() == omtext) {
			System.out.println("-ökhfnzxEH");
		}
	}

	public void mouseReleased(MouseEvent arg0) {
		
	}

	public void keyPressed(KeyEvent arg0) {
		SkrivHändelsetext("Du tryckte på: " + KeyEvent.getKeyText(arg0.getKeyCode()));
		if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Esc"){
			System.exit(0);
		}
		else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Vänsterpil"){
			posX = posX - FlyttHastighet;
			repaint();
		}
		else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Högerpil"){
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
		SkrivHändelsetext("Du släppte : " + KeyEvent.getKeyText(arg0.getKeyCode()));
	}

	public void keyTyped(KeyEvent arg0) {
		
	}
	
	public void windowOpened(WindowEvent e) {
		
	}

	public void windowClosing(WindowEvent e) {
		
	}

	public void windowClosed(WindowEvent e) {
		AntalFönster--;
		System.out.println(AntalFönster);
		HändelseFönster.dispose();
		HastighetsFönster.dispose();
		om.dispose();
		Räknare.dispose();
		if (AntalFönster == 0) {
		
			AvslutningsFönster.setVisible(true);
		
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
	public void SkrivHändelsetext(String Händlsetext){
		text.append(Händlsetext + "\n");
		DefaultCaret caret = (DefaultCaret)text.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
		if (autoscroll == true) {
		text.setCaretPosition(text.getDocument().getLength());
		}
	}
	public static void Vänta(int millisekunder){
		try {
			Thread.sleep(millisekunder);
		} catch (InterruptedException e) {
			e.printStackTrace();
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
	
	public String Texten = "Dra eller använd piltangenterna";

	public void paintComponent(Graphics g){
		  
		super.paintComponent(g);
		
	    Graphics2D g2 = (Graphics2D)g;
	    
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	    g2.setFont(new Font("Serif", Font.ROMAN_BASELINE, 35));

		g2.drawString(Texten,posX, posY); 
	  }
	  public void setTexten(){
		  String Text = JOptionPane.showInputDialog("Ändra text på dragbar remsa");
		  setTexten(Text);
		  
	  }
	  public void setTexten(String Text){
		  if(Text == null){
			  Text = "Dra eller använd piltangenterna";
		  }
		  Texten = Text;
		  System.out.println("Texten ändrad till: " + Texten);
	  
	}
	
}

@SuppressWarnings("serial")
class Pongspel extends JPanel implements ActionListener,KeyListener,WindowListener,MouseMotionListener{
	
	private int x,y,VänsterX=0,VänsterY,HögerX,HögerY,RektBredd,RektHöjd,
			bredd=20,höjd=30,hastighet,c, d,PoängVänster=0,PoängHöger=0,py=10,px=10;
	private JFrame frame;
	private Timer timer;
	private Boolean GameOver=false;
	private String PoängTill,SpelareVänster,SpelareHöger;
	
	public void Starta() {
		
try {
				
			
			SpelareVänster = JOptionPane.showInputDialog("Spelare till vänster:");
			if (SpelareVänster == null) {
				throw new Exception();
			}
			SpelareHöger = JOptionPane.showInputDialog("Spelare till höger:");
			if (SpelareHöger == null) {
				throw new Exception();
			}
			frame = new JFrame("Spel");
			frame.add(this);
			setForeground(Color.red);
			setPreferredSize(new Dimension(700, 500));
			frame.setVisible(true);
			frame.setIconImage(Mouse.FönsterIcon.getImage());
			frame.repaint();
			frame.pack();
			frame.addWindowListener(this);
			frame.addKeyListener(this);
			addMouseMotionListener(this);
			frame.addMouseMotionListener(this);
			setOpaque(true);	
			setBackground(Color.BLACK);
			frame.getContentPane().setBackground(Color.BLUE);
			x = getWidth()/2;
			y = 5;
			timer= new Timer(10, this);
			hastighet =2;
			c = hastighet;
			d = hastighet;
			frame.setVisible(true);
			PoängHöger = 0;
			PoängVänster = 0;
			StartaOm();
			frame.setLocationRelativeTo(null);
			RektHöjd = 100;
			RektBredd = 10;
			HögerY = getHeight()/2;
			VänsterY = getHeight()/2;
			HögerX=getWidth()-bredd-1;
			timer.start();
			}
			catch (Exception e) {
				
			}
		
	}
	private void StartaOm(){
		x = getWidth()/2;
		y = 5;
		hastighet =2;
		c = hastighet;
		d = hastighet;
		timer.start();
		
	}
	private void GameOver(String PoängTillVänsterEllerHöger) {
		timer.stop();
		System.out.println("Game over!");
		Toolkit.getDefaultToolkit().beep();
		
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

	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 87 ) {
			VänsterY=VänsterY-20;
		}
		else if (e.getKeyCode() == 83) {
			VänsterY=VänsterY+20;
		}
		System.out.println("lllk");
		if(KeyEvent.getKeyText(e.getKeyCode()) == "Upp"){
			HögerY=HögerY-20;
			
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()) == "Nedpil"){
			HögerY=HögerY+20;
			
		}
		
		else if (KeyEvent.getKeyText(e.getKeyCode()) == "Mellanslag") {
			GameOver = false;
			frame.repaint();
			StartaOm();
		}
		else if (KeyEvent.getKeyText(e.getKeyCode()) == "Esc") {
			frame.dispose();
		}
		frame.repaint();
	}

	public void keyReleased(KeyEvent e) {
		
	}
	
	
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==timer){
			
			if (x+bredd>=HögerX) {
				
				if (y>=HögerY) {
					if (y<=HögerY+RektHöjd) {
						System.out.println("aaaaal");
						System.out.println("jckfuhol");
						hastighet++;
						c= -hastighet;
					}
					else{
						GameOver("Vänster");;
					
					}
				}
				else{
					GameOver("Vänster");
				}
			}
			else if (x<=VänsterX+RektBredd) {
				System.out.println("1");
				if (y>=VänsterY) {
					System.out.println("2");
					if (y<=VänsterY+RektHöjd) {
						System.out.println("aajckfuhol");
						hastighet++;
						c=hastighet;
					}
					else{
						GameOver("Höger");
					}
				}
				else{
					GameOver("Höger");
				}
			
			}
			else if (y+höjd>=getHeight()) {
				System.out.println("jl,");
				d=-hastighet;
			}
			else if(y<=getHeight()-getHeight()){
				System.out.println("jl,2");
				d=hastighet;
			}
			x=x+c;
			
			y=y+d;
			frame.repaint();
			HögerX=getWidth()-RektBredd-1;
			
			
			
		}
	}
	public void paintComponent(Graphics g){
		
//		x = getWidth()/2;
//		y = getHeight()/2;
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (GameOver==true) {
			g2.setFont(new Font("", Font.BOLD, 50));
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
		
		g2.setColor(Color.green);
		g2.setFont(new Font("", Font.BOLD, 50));
		g2.drawString(Integer.toString(PoängVänster) + " - " + Integer.toString(PoängHöger), getWidth()/2-80, 40);
		
		g2.drawString(SpelareVänster, 0, 40);
		g2.drawString(SpelareHöger, getWidth()-250, 40);
		
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
class Snakespel extends JPanel implements KeyListener, ActionListener{
	JFrame frame = new JFrame("Snake");
	final int Längd = 3,pixelstorlek=10;
	int[] x=new int[50],y=new int[50]; 
	int snakelängd,posx=100,posy=100,pluppX,pluppY, stringy;
	Timer timer = new Timer(100, this);
	String riktning = "ner";
	Random random = new Random();
	boolean förlust;
	
	public Snakespel() {

		frame.add(this);		
		frame.setIconImage(Mouse.FönsterIcon.getImage());
		frame.setResizable(false);		
		frame.addKeyListener(this);
		frame.setSize(pixelstorlek*50, pixelstorlek*50);
		frame.setLocationRelativeTo(null);
		setBackground(Color.white);
		frame.setVisible(true);
		Restart();
		
	}
	private void GameOver(){
		timer.stop();
		förlust = true;
	}
	private void Restart() {
		posx = random.nextInt(getWidth()/pixelstorlek)*pixelstorlek;
		posy = random.nextInt(getHeight()/pixelstorlek)*pixelstorlek;
		if (	posx>frame.getWidth()*0.7||
				posx<frame.getWidth()*0.3||
				posy>frame.getHeight()*0.7||
				posy<frame.getHeight()*0.3) {
			System.out.println("Räknar om: " + posx);
			Restart();
		}
		else{
		snakelängd = Längd;
		x[1]=posx;
		y[1]=posy;
		PlaceraPlupp();
		timer.start();
		förlust = false;
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
		if (förlust) {
			g.setColor(Color.red);
			g.setFont(new Font(null, 0, 25));
			g.drawString("Du förlorade! Tryck F2 för att spela igen",10 , frame.getHeight()/2);
		}
		g.setColor(Color.red);
		g.drawOval(pluppX, pluppY, 8, 8);
		g.fillOval(pluppX, pluppY, 8, 8);
		g.setColor(Color.black);
		g.drawRect(x[1], y[1], pixelstorlek, pixelstorlek);
		g.fillRect(x[1], y[1], pixelstorlek, pixelstorlek);
		g.setColor(Color.GREEN);
		g.setFont(Mouse.Typsnitt);
		g.drawString(Integer.toString(snakelängd), x[1], stringy);
		for (int i = snakelängd+1; i >= 2; i--) {
			
			g.setColor(Color.black);
			x[i]=x[i-1];
			y[i]=y[i-1];
			g.drawRect(x[i], y[i], pixelstorlek, pixelstorlek);
			g.fillRect(x[i], y[i], pixelstorlek, pixelstorlek);
			System.out.println(i);
		}
		
		
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		
		if(KeyEvent.getKeyText(e.getKeyCode()) == "Vänsterpil"){
			if (riktning!="höger"){
				riktning="vänster";
				
			}
			

		}
		else if(KeyEvent.getKeyText(e.getKeyCode()) == "Högerpil"){
			if (riktning!="vänster"){
				riktning="höger";
			}
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()) == "Upp"){
			if (riktning!="ner"){
				riktning="upp";
			}
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()) == "Nedpil"){
			if (riktning!="upp"){
				riktning="ner";
			}
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()) == "F2"){
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
				snakelängd++;
				((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.asterisk")).run();
				System.err.println(snakelängd);
				
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
			for (int i = 2; i <= snakelängd; i++) {
				if (x[1]==x[i]&&y[1]==y[i]) {
					System.out.println("GameOver");
					GameOver();
				}
			
			}
			if (x[1]-pixelstorlek<0) {
				GameOver();
			}
			if (x[1]+pixelstorlek+pixelstorlek>frame.getWidth()) {
				GameOver();
			}
			if (y[1]-pixelstorlek<0) {
				GameOver();		
			}
			if (y[1]+pixelstorlek*6>frame.getHeight()) {
				GameOver();
			}
	
			frame.repaint();
			
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
		frame.setIconImage(Mouse.FönsterIcon.getImage());
		frame.add(this);
		frame.setUndecorated(true);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		timer.start();
		timer2.start();
	}
	public void paintComponent(Graphics ag){
//		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D)ag;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		
		g2.setColor(new Color(r, g, bl));
		
		g2.drawOval(x, y, a, b);
		g2.fillOval(x, y, a, b);
//		g2.drawImage(Mouse.FönsterIcon.getImage(), x, y, null);
		
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
//			frame.repaint();
//			frame.revalidate();
//			revalidate();
		}
		
	}
	
}
@SuppressWarnings("serial")
class Hypnos extends JPanel implements ActionListener{
	JFrame frame = new JFrame("Studsa");
	Timer timer = new Timer(1, this),
			timer2 = new Timer(5, this);
	Random random = new Random();
	int x=1,y=1,a=5,b=5,c=2,d=2,r=100,g=255,bl=25;
	public Hypnos() {

		
		frame.setSize(500,500);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(Mouse.FönsterIcon.getImage());
		frame.add(this);
		frame.setUndecorated(true);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		timer.start();
		timer2.start();
	}
	public void paintComponent(Graphics ag){
//		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D)ag;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(Color.BLUE);
	    g2.fillRect(0, 425, 150, 1000);
	    
	    g2.setColor(Color.GREEN);
	    g2.fill3DRect(1000, 200, 100, 350, false);
	    
	    g2.setColor(Color.ORANGE);
	    g2.fill3DRect(1155, 0, 110, 495, true);
	    
	    g2.setColor(Color.MAGENTA);
	    g2.fillRect(205, 550, 1000, 100);
	    
	    g2.setColor(Color.WHITE);
	    g2.drawRect(700, 650, 90, 90);
	    

	    
	    g2.setColor(new Color(233,5,6));
	    g2.fill3DRect(150, 425, 300, 70, true);
	   
	    g2.setColor(Color.BLACK);
	    g2.setFont(new Font("", Font.ROMAN_BASELINE,20));
	    for(int i = 90; i > 0; i--){
			
			r = random.nextInt(255);
			g = random.nextInt(255);
			b = random.nextInt(255);
			
		g2.setColor(new Color(r,g,b));
		g2.drawRect(700, 650, i, i);
				
}
	    
	    g2.setColor(Color.cyan);
	    g2.fillRect(x, y, 50,50);
		
	}
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == timer2){
		r=random.nextInt(255);
		g=random.nextInt(255);
		bl=random.nextInt(255);
		repaint();
		}
		if (e.getSource()==timer) {
			
	
		}
		
	}
	
}
@SuppressWarnings("serial")
class RörandeMojäng extends JPanel implements MouseMotionListener, WindowListener, KeyListener, ActionListener{

	
	JFrame frame = new JFrame("Det här är försök  " + qq),
		 Vinst = new JFrame("Grattis!");
 	
 	Timer timer = new Timer(1000, this);
 
 	JLabel textlabel = new JLabel();
 
 	JTextArea textruta = new JTextArea();
 	
 	Random random = new Random();
 	
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
			färg = new JMenuItem("Skapa färg");
	JMenuBar bar = new JMenuBar();
	
	Robot robot;
	
	Clip clip;

	static int qq = 1;
	static int x = 800;
	static int yy = 900;
	static int y = 300;
	static int ii = 0;
		public RörandeMojäng(){
			GoJbsBraOchHa.Mouse.AntalFönster++;
			frame.setVisible(true);
//			frame.getContentPane().setForeground(new Color(49,130,240));
//			frame.getContentPane().setBackground(new Color(190,230,60));
		
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//			RörandeMojäng3 rö3 = new RörandeMojäng3();
//			frame.getContentPane().add(new RörandeMojäng2());
			
//			Bana bana = new Bana();
//			frame.add(rö);
//			frame.add(rö3);
//			frame.add(bana);
			
			frame.setIconImage(GoJbsBraOchHa.Mouse.FönsterIcon.getImage());
			
			
			Vinst.setLocationRelativeTo(null);
			Vinst.setSize(190, 100);
			Vinst.add(textruta);
			textruta.setFont(new Font("",Font.BOLD, 20));
			textruta.setText("Grattis! Du vann \nefter " + qq + " försök! :D");
			textruta.setEditable(false);
			
			frame.add(textlabel);
			
			textlabel.setLocation(400, 200);
			textlabel.setOpaque(false);
			
			
			frame.setBackground(Color.gray);
			frame.setForeground(Color.pink);
			
			frame.setSize(1845, 800);
			frame.addMouseMotionListener(this);
			frame.addKeyListener(this);
			frame.setResizable(false);
		frame.setJMenuBar(bar);
//		frame.setJMenuBar(bar2);
		bar.add(ÖppnaProgram);
		bar.add(menu);
		bar.add(menu2);
		bar.add(menu1);
		 Container contentPane = frame.getContentPane();
		    contentPane.add(new RörandeMojäng3());
		
		    frame.addWindowListener(this);
		    
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
		ÖppnaProgram.add(Snake);
		ÖppnaProgram.add(impossible);
		ÖppnaProgram.add(ticTacToe);
		ÖppnaProgram.add(lösenord);
		ÖppnaProgram.add(färg);
		
		
		frame.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
		  
		

		Mouse.addActionListener(this);
		Pong.addActionListener(this);
		Återställ.addActionListener(this);
		item1.addActionListener(this);
		Minirknare.addActionListener(this);
		Betyg.addActionListener(this);
		OrginalFönster.addActionListener(this);
		Maze.addActionListener(this);
		Snake.addActionListener(this);
		impossible.addActionListener(this);
		ticTacToe.addActionListener(this);
		lösenord.addActionListener(this);
		färg.addActionListener(this);
		
		timer.addActionListener(this);
		timer.start();
		
		
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			
			e.printStackTrace();
		}
		robot.mouseMove(200, 100);
//		String [] names ={"Biologi", "Fysik", "Kemi", "Teknik", "Historia", "Geografi", "Sammhällskunskap", "Religon", "Slöjd", "ModernaSpråk", "Idrott", "HemOchKonsumentkunskap", "Musik", "Bild"};
//	      for( String name : names ) {
//	         System.out.println(name + ".setLayout(new GridLayout(3,1));");
//	      }
		}
	
		
		
		    
		
		


	public void mouseMoved(MouseEvent e) {
		//  
		
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

		
		System.out.println("Musen rör sig på: " + e.getX()  + ", " + e.getY());
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
//		
	
		
		if ( x == 50) {
						if (y == 50){
							
						System.exit(0);	
						}
			}
		
		else {
//		 for (int i = 1; i <= 1000; i++){
//					System.out.println(x + y);
//			text.append(x + ", " + y + NEWLINE);}
//					repaint();
//				}}
			
		}
		}
	
	public void keyTyped(KeyEvent e){
//		int id = e.getID();
//		if (id == KeyEvent.KEY_TYPED){
//			char c = e.getKeyChar();
//			System.out.println(c + " " + id);
//		
//		}

//	int idd = e.getID();
//	if (idd == KeyEvent.KEY_LOCATION_NUMPAD) {
//		char y = e.getKeyChar();
//		System.out.println(y + " " + idd);
	}
	
	
	
	
	
	
	public void keyPressed(KeyEvent e) {
		//  
		
		
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
		//  
		
	}
	
	public void windowActivated(WindowEvent arg0) {
		//  
		
	}
	
	public void windowClosed(WindowEvent arg0) {
		GoJbsBraOchHa.Mouse.AntalFönster--;
		
		
	}
	
	public void windowClosing(WindowEvent arg0) {
		//  
		
	}
	
	public void windowDeactivated(WindowEvent arg0) {
		//  
	
		
		
	}
	
	public void windowDeiconified(WindowEvent arg0) {
		//  
		System.exit(3);
	}
	
	public void windowIconified(WindowEvent arg0) {
		//  
		System.exit(3);
	}
	
	public void windowOpened(WindowEvent arg0) {
		//  
		
	}
	
	public void mouseDragged(MouseEvent arg0) {
		//  
		
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
			if (frame.isVisible() == false){
				timer.stop();
			}
	
			
			
		}
		
		if (arg0.getSource() == Mouse){
			frame.dispose();
			new Mouse();
		}
		
		if (arg0.getSource() == Återställ){
			x = 300;
			y = 800;
			frame.revalidate();
			frame.repaint();
			Container contentPane = frame.getContentPane();
		    contentPane.add(new RörandeMojäng3());
			
		}
		
		
		if (arg0.getSource() == färg){
			new SkapaFärg();
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
		
		if (arg0.getSource() == Pong){
			new Pong();
			frame.dispose();
			
		}
		
		if (arg0.getSource() == Snake){
			new Snake();
		}
		
		if (arg0.getSource() == Minirknare){
			new Miniräknare();
		}
		
		if (arg0.getSource() == ticTacToe){
			new TicTacToe();
			frame.dispose();
		}
		
		if (arg0.getSource() == lösenord){
			new Pass();
			frame.dispose();
		}
		
		if (arg0.getSource() == Betyg){
			new Merit();
		}
		
		if (arg0.getSource() == OrginalFönster){
			new RörandeMojäng();
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


class RörandeMojäng2 extends JPanel implements ActionListener {
	
	
	
	
	
	public void actionPreformed(ActionEvent e){
		repaint();
	}
	
	
	
	
	public void actionPerformed(ActionEvent arg0) {
		  
		
	}
	public void paint(Graphics gg){
		super.paint(gg);
		
		gg.setColor(new Color(0, 50, 0));
		gg.fillRect(RörandeMojäng.x, RörandeMojäng.y, 40, 40);
		
		
	}
}
//
//class Bana extends JPanel implements ActionListener{
//	public void paint(Graphics g){
//		super.paint(g);
//		
//		g.setColor(Color.BLACK);
//		g.fillRect(RörandeMojäng.x, RörandeMojäng.yy, 100, 100);
//	}
//
//	
//	public void actionPerformed(ActionEvent arg0) {
//		//  
//		
//	}
//}

class RörandeMojäng3 extends JPanel implements ActionListener {
	
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
	    g2.drawString("Dra genom labyrinten till den \nfärgglada kvadraten för att vinna.\n Lycka till! :D", 300, 150);
	    for(int i = 90; i > 0; i--){
			
			r = random.nextInt(255);
			g = random.nextInt(255);
			b = random.nextInt(255);
			
		g2.setColor(new Color(r,g,b));gr.drawRect(700, 650, i, i);
				
}
	    
	    g2.setColor(Color.cyan);
	    g2.fillRect(x, y, 50,50);
}

	
	public void actionPerformed(ActionEvent arg0) {
		//  
		
	}
	
}

class RörandeMojäng4 {
public void paint (Graphics g) {    
 


}
}
static class GameOver implements ActionListener, WindowListener{
JButton b1 = new JButton("Spela igen");
		JButton b2 = new JButton("Avsluta");
		JFrame ram = new JFrame("GAME OVER");
public GameOver(){
	
		ram.add(b1);
		ram.add(b2);
		ram.setVisible(true);
		ram.setSize(500, 500);
		ram.setLayout(new GridLayout(2, 1));
		
		RörandeMojäng.ii = 1;
		
		b1.addActionListener(this);
		b2.addActionListener(this);
		ram.addWindowListener(this);
	ram.setDefaultCloseOperation(3);
}


	





public void actionPerformed(ActionEvent arg0) {
	//  
	
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








public void windowOpened(WindowEvent e) {
	//  
	
}








public void windowClosing(WindowEvent e) {
	//  
	
}















public void windowIconified(WindowEvent e) {
	//  
	
}








public void windowDeiconified(WindowEvent e) {
//	  
	
}








public void windowActivated(WindowEvent e) {
	
}








public void windowDeactivated(WindowEvent e) {
	
		


}








public void windowClosed(WindowEvent e) {
}}}


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
		} catch (Exception sda) {
			
			a = 0;
		}
		
		
		
		}
	

	private void RäknaUt() {
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
		//  
		//  
			
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
//					if (e.getKeyCode()== 49){
//						textruta.append("1");
//						}
//					if (e.getKeyCode()== 50){
//						textruta.append("2");
//						}
//					if (e.getKeyCode()== 51){
//						textruta.append("3");
//						}
//					if (e.getKeyCode()== 52){
//						textruta.append("4");
//						}
//					if (e.getKeyCode()== 53){
//						textruta.append("5");
//						}
//					if (e.getKeyCode()== 54){
//						textruta.append("6");
//						}
//					if (e.getKeyCode()== 55){
//						textruta.append("7");
//						}
//					if (e.getKeyCode()== 56){
//						textruta.append("8");
//						}
//					if (e.getKeyCode()== 57){
//						textruta.append("9");
//						}
//					if (e.getKeyCode()== 58){
//						textruta.append("1");
//						}
	}
					
	
	
	

	public void keyReleased(KeyEvent arg0) {
		//  
		
	}

	public void keyTyped(KeyEvent e) {
		//  
		
		}
	
			}
			
@SuppressWarnings("serial")
class Merit extends JComponent implements MouseMotionListener, WindowListener, KeyListener, ActionListener{


	
	JFrame huvudframe = new JFrame(),
			Svenska = new JFrame("Svenska"),
			Engelska = new JFrame("Engelska"),
			Matte = new JFrame("Matte"),
			Biologi = new JFrame("Biologi"),
			Fysik = new JFrame("Fysik"),
			Kemi = new JFrame("Kemi"),
			Teknik = new JFrame("Teknik"),
			Slöjd = new JFrame("Slöjd"),
			ModernaSpråk = new JFrame("ModernaSpråk"),
			Bild = new JFrame("Bild"),
			Religon = new JFrame("Religon"),
			Geografi = new JFrame("Geografi"),
			Historia = new JFrame("Historia"),
			Samhällskunskap = new JFrame("Samhällskunskap"),
			Musik = new JFrame("Musik"),
			Idrott = new JFrame("Idrott"),
			HemOchKonsumentkunskap = new JFrame("HemOchKonsumentkunskap"),
			Resultat = new JFrame("Resultat");
			

	JLabel resultatlabel = new JLabel("",JLabel.CENTER);
	
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
		Börja.setPreferredSize(new Dimension(30, 30));
		
		A.addActionListener(this);
		B.addActionListener(this);
		C.addActionListener(this);
		D.addActionListener(this);
		E.addActionListener(this);
		F.addActionListener(this);
		
		
		huvudframe.add(Börja);
		
		Börja.addActionListener(this);
	
		Svenska.setLocationRelativeTo(null);
		Engelska.setLocationRelativeTo(null);
		Matte.setLocationRelativeTo(null);
		Biologi.setLocationRelativeTo(null);
		Fysik.setLocationRelativeTo(null);
		Kemi.setLocationRelativeTo(null);
		Bild.setLocationRelativeTo(null);
		Idrott.setLocationRelativeTo(null);
		HemOchKonsumentkunskap.setLocationRelativeTo(null);
		Samhällskunskap.setLocationRelativeTo(null);
		Historia.setLocationRelativeTo(null);
		Geografi.setLocationRelativeTo(null);
		Religon.setLocationRelativeTo(null);
		Musik.setLocationRelativeTo(null);
		Slöjd.setLocationRelativeTo(null);
		ModernaSpråk.setLocationRelativeTo(null);
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
		Samhällskunskap.pack();
		Historia.pack();
		Geografi.pack();
		Religon.pack();
		Musik.pack();
		Slöjd.pack();
		ModernaSpråk.pack();
	
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
		Samhällskunskap.setLayout(new GridLayout(3,1));
		Religon.setLayout(new GridLayout(3,1));
		Slöjd.setLayout(new GridLayout(3,1));
		ModernaSpråk.setLayout(new GridLayout(3,1));
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
		Samhällskunskap.setSize(300, 250);
		Religon.setSize(300, 250);
		Slöjd.setSize(300, 250);
		ModernaSpråk.setSize(300, 250);
		Idrott.setSize(300, 250);
		HemOchKonsumentkunskap.setSize(300, 250);
		Musik.setSize(300, 250);
		Bild .setSize(300, 250);
		
		repaint();
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
			Samhällskunskap
			Religon
			Slöjd 
			ModernaSpråk
			Idrott
			HemOchKonsumentkunskap
			Musik
			Bild 
			*/
		}
		


		
		public void mouseDragged(MouseEvent arg0) {
			//  
			
		}
		
		public void mouseMoved(MouseEvent arg0) {
			//  
			
		}
		
		public void actionPerformed(ActionEvent e) {
			//  
			
			
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
			Samhällskunskap.setVisible(true);
			
			Samhällskunskap.add(A);
			Samhällskunskap.add(B);
			Samhällskunskap.add(C);
			Samhällskunskap.add(D);
			Samhällskunskap.add(E);
			Samhällskunskap.add(F);
			System.out.println(x);
			}
			else if (Samhällskunskap.isVisible() == true){
			
			Samhällskunskap.setVisible(false);
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
			Slöjd.setVisible(true);
			
			Slöjd.add(A);
			Slöjd.add(B);
			Slöjd.add(C);
			Slöjd.add(D);
			Slöjd.add(E);
			Slöjd.add(F);
			System.out.println(x);
			}
			else if (Slöjd.isVisible() == true){
			
			Slöjd.setVisible(false);
			ModernaSpråk.setVisible(true);
			
			ModernaSpråk.add(A);
			ModernaSpråk.add(B);
			ModernaSpråk.add(C);
			ModernaSpråk.add(D);
			ModernaSpråk.add(E);
			ModernaSpråk.add(F);
			System.out.println(x);
			}
			else if (ModernaSpråk.isVisible() == true){
			
			ModernaSpråk.setVisible(false);
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
			
			else if (e.getSource() == Börja){
				huvudframe.setVisible(false);
				Svenska.setVisible(true);
			}}
		
		
		public void keyPressed(KeyEvent e) {
			//  
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
			//  
			
		}
		
		public void keyTyped(KeyEvent e) {
			//  
			
			
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
			//  
			
		}
		
		public void windowClosed(WindowEvent arg0) {
			//  
			
		}
		
		public void windowClosing(WindowEvent arg0) {
			//  
			
		}
		
		public void windowDeactivated(WindowEvent arg0) {
			//  
			
		}
		
		public void windowDeiconified(WindowEvent arg0) {
			//  
			
		}
		
		public void windowIconified(WindowEvent arg0) {
			//  
			
		}
		
		public void windowOpened(WindowEvent arg0) {
			//  
			
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
			}
			
			y = y + a+c;
			x = x + b+c;
			
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
		
		börja.addActionListener(this);
		
		level1.setBackground(Color.BLACK);
		level1.setSize(418, 430);
		level1.setLocationRelativeTo(null);
		level1.setResizable(false);
		level1.add(this);	       
		level1.addMouseMotionListener(this);
		level1.repaint();
		level1.revalidate();
		level1.setResizable(false);
		

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
		System.out.println("Musen rör sig på: " + x  + ", " + y);
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
		System.out.println("Musen rör sig på: " + x  + ", " + y);
		level3.repaint();
		
		if (x > 290 && y < 235 && y > 30||y < 235 && y > 150 && x > 82|| x > 395|| x > 364 && y > 30|| y < 310 && y > 291 && x > 173|| y > 246 &&
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
		frame.add(new JLabel(new ImageIcon(getClass().getResource("/images/Bild.jpg"))));
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(level3.level3);
		


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
	int x = 250,y = 250,a,b = 1,bredd = 25,höjd = 100,q;
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
		    g2.fillRect(x ,y,bredd,höjd);
		    frame.repaint();
	}

	public void keyPressed(KeyEvent arg0) {

		 if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Vänsterpil"){
			a = -1;
			b = 0;
		
				if (bredd < höjd){
			
			
			q = höjd;
			höjd = bredd;
			bredd = q;
			
			
			frame.repaint();
			System.out.println(a + "," + b);
		}}
	else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Högerpil"){
		a = +1;
		b = 0;
	
			if (bredd < höjd){
		
		
		q = höjd;
		höjd = bredd;
		bredd = q;
		
		
		frame.repaint();
		System.out.println(a + "," + b);
			}}
	else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Upp"){
			
		a = 0;
		b = -1;
	
			if (bredd > höjd){
		
		
		q = höjd;
		höjd = bredd;
		bredd = q;
		
		
		frame.repaint();
		System.out.println(a + "," + b);
			}
			}
	else if(KeyEvent.getKeyText(arg0.getKeyCode()) == "Nedpil"){
		
		a = 0;
		b = +1;
	
			if (bredd > höjd){
		
		
		q = höjd;
		höjd = bredd;
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
		if(arg0.getKeyChar() == 'Å'){
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
	
	String sträng = new String();
	
	JLabel[] label = new JLabel[10];
	JLabel vinstlabel = new JLabel(),
			turLabel = new JLabel();
			
	ImageIcon o = new ImageIcon(getClass().getResource("/images/O.png")),
			x = new ImageIcon(getClass().getResource("/images/X.png"));

	int a,å;
	
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
			 if(label[9].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))||label[9].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))){
				}
				else if (label[9].getIcon() == null){
				X(9);
			}}
			if(a== 0 && e.getSource() == label[8]){
			 if(label[8].getIcon() == o||label[8].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))){
				}
				else if (label[8].getIcon() == null){
				X(8);
			}}
			if(a== 0 && e.getSource() == label[7]){
			 if(label[7].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))||label[7].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))){
				}
				else if (label[7].getIcon() == null){
				X(7);
			}}
			if(a== 0 && e.getSource() == label[6]){
			 if(label[6].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))||label[6].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))){
				}
				else if (label[6].getIcon() == null){
				X(6);
			}}
			if(a== 0 && e.getSource() == label[5]){
			 if(label[5].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))||label[5].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))){
				}
				else if (label[5].getIcon() == null){
				X(5);
			}}
			if(a== 0 && e.getSource() == label[4]){
			 if(label[4].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))||label[4].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))){
				}
				else if (label[4].getIcon() == null){
				X(4);
			}}
			if(a== 0 && e.getSource() == label[3]){
			 if(label[3].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))||label[3].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))){
				}
				else if (label[3].getIcon() == null){
				X(3);
			}}
			if(a== 0 && e.getSource() == label[2]){
			 if(label[2].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))||label[2].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))){
				}
				else if (label[2].getIcon() == null){
				X(2);
			}}
			if(a== 0 && e.getSource() == label[1]){
			 if(label[1].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))||label[1].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))){
				}
				else if (label[1].getIcon() == null){
				X(1);
			}}
			if(a== 1 && e.getSource() == label[9]){
				 if(label[9].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))||label[9].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))){
					}
					else if (label[9].getIcon() == null){
					O(9);
				}}
				if(a== 1 && e.getSource() == label[8]){
				 if(label[8].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))||label[8].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))){
					}
					else if (label[8].getIcon() == null){
					O(8);
				}}
				if(a== 1 && e.getSource() == label[7]){
				 if(label[7].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))||label[7].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))){
					}
					else if (label[7].getIcon() == null){
					O(7);
				}}
				if(a== 1 && e.getSource() == label[6]){
				 if(label[6].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))||label[6].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))){
					}
					else if (label[6].getIcon() == null){
					O(6);
				}}
				if(a== 1 && e.getSource() == label[5]){
				 if(label[5].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))||label[5].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))){
					}
					else if (label[5].getIcon() == null){
					O(5);
				}}
				if(a== 1 && e.getSource() == label[4]){
				 if(label[4].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))||label[4].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))){
					}
					else if (label[4].getIcon() == null){
					O(4);
				}}
				if(a== 1 && e.getSource() == label[3]){
				 if(label[3].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))||label[3].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))){
					}
					else if (label[3].getIcon() == null){
					O(3);
				}}
				if(a== 1 && e.getSource() == label[2]){
				 if(label[2].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))||label[2].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))){
					}
					else if (label[2].getIcon() == null){
					O(2);
				}}
				if(a== 1 && e.getSource() == label[1]){
				 if(label[1].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))||label[1].getIcon() == new ImageIcon(getClass().getResource("/images/O.png"))){
					}
					else if (label[1].getIcon() == null){
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
	@Override
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
	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
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
	@Override
	public void keyReleased(KeyEvent e) {
	}
}

class Pass implements ActionListener{
	
		int x;
	
	 private static String OK = "What Ever!";
	 
	 
	 Timer timer = new Timer(1, this);	
	 
	    private JPasswordField passwordField;
	    private JFrame användare = new JFrame();
	    
	    private JFrame frame = new JFrame("Verifiera dig!");
	    
	    JButton användareJakob = new JButton("Jakob"),
	    		användareGlenn = new JButton("Glenn");
	    
	    private JLabel label = new JLabel("Skriv Lösenord -->");

	    
	  public void actionPerformed(ActionEvent e) {

		  
	        if (timer == e.getSource()) { //Process the password.
	            
	        	String string = null;
	            try {
	            	Toolkit toolkit = Toolkit.getDefaultToolkit();
	        		Clipboard clipboard = toolkit.getSystemClipboard();
	        		string = (String) clipboard.getData(DataFlavor.stringFlavor);
	        		
				} catch (Exception e1) {
					e1.printStackTrace();
					timer.stop();
				} 
	            if(string.equals("Uggen0684")){
 	             passwordField.setText("Uggen0684");
 		               
 	            }
	            char[] input = passwordField.getPassword();
	            
	            x++;
	         
	            if (isPasswordCorrect(input)) {
	            	frame.dispose();
	               användare.setVisible(true);
	               timer.stop();
	            }
	            
	            
	            if(x == 4000){
	            	new Impossible("Tiden gick ut!! Datorn spärrad...");
	            }
	            
	        }
	        
	        if(e.getSource() == användareJakob){
	        	new Mouse();
	        	användare.dispose();
	        }
	        else if(e.getSource() == användareGlenn){
	        	new RörandeMojäng();
	        	användare.dispose();
	        }
	        
	    }

	    private static boolean isPasswordCorrect(char[] input) {
	        boolean isCorrect = true;
	        char[] correctPassword = { 'U','g','g','e','n','0','6','8','4'};

	        if (input.length != correctPassword.length) {
	            isCorrect = false;
	        } else {
	            isCorrect = Arrays.equals (input, correctPassword);
	            
	        }

	        return isCorrect;
	    }

	 
	    public Pass() {
	    	
	        passwordField = new JPasswordField(10);
	        passwordField.setActionCommand(OK);
	        passwordField.addActionListener(this);
	        
	        användare.add(användareJakob);
	        användare.add(användareGlenn);
	        användare.setIconImage(Mouse.FönsterIcon.getImage());
	        användare.setLayout(new FlowLayout());
	        användare.pack();
	        användare.setLocationRelativeTo(null);
	        
	        användareGlenn.addActionListener(this);
	        användareJakob.addActionListener(this);
	        
	    	frame.setUndecorated(true);
	    	frame.setAlwaysOnTop(true);
	        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
	        frame.add(label);
	        frame.add(passwordField);
	        frame.setIconImage(Mouse.FönsterIcon.getImage());
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.pack();
	        frame.setMinimumSize(frame.getSize());
	        frame.setVisible(true);
	        frame.setLocationRelativeTo(null);
	        timer.start();
	        
	       
	       
	        
	    }


	}
@SuppressWarnings("serial")
class SkapaFärg extends JPanel implements ActionListener{
	
	JFrame frame = new JFrame("Skapa en egen färg");
	
	int xb,xg,xr;
	
	JPanel Panel = new JPanel(),
			panel = new JPanel(),
			paneliPanel = new JPanel();
	
	JSlider r,
			g,
			b;
	
	Timer timer = new Timer(1, this);
	
	Random random = new Random();
	
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
	
	setForeground(Color.blue);
	
	frame.setVisible(true);
	
	panel.setLayout(new BorderLayout());
	Panel.setLayout(new BorderLayout());
	
	setOpaque(true);
	
	frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
	
	Panel.setPreferredSize(new Dimension(250, 300));
	panel.add(r,BorderLayout.NORTH);
	panel.add(g,BorderLayout.CENTER);
	panel.add(b,BorderLayout.SOUTH);
	frame.add(panel);
	frame.add(Panel);
	frame.pack();

	Panel.add(paneliPanel,BorderLayout.CENTER);
	Panel.add(Box.createRigidArea(new Dimension(25,25)),BorderLayout.NORTH);
	Panel.add(Box.createRigidArea(new Dimension(25,25)),BorderLayout.SOUTH);
	Panel.add(Box.createRigidArea(new Dimension(25,25)),BorderLayout.EAST);
	Panel.add(Box.createRigidArea(new Dimension(25,25)),BorderLayout.WEST);
	
	paneliPanel.setBackground(new Color(r.getValue(),g.getValue(), b.getValue()));
	
	paneliPanel.setOpaque(true);
	
	
	
	frame.repaint();
	frame.setLocationRelativeTo(null);
	
	timer.start();
	
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
	
		if (timer == e.getSource()){
			paneliPanel.setBackground(new Color(r.getValue(),g.getValue(), b.getValue()));
		
		}
		
	}
}

