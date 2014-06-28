package JakobsBraOchHa;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

import sun.audio.*;


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
				  	Spelknapp = new JMenuItem("Öppna spel");
	
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
	
	Pong			pong = new Pong();
	
	Robot			robot;
	
	Color			Färg = new Color(0, 0, 255);
	
	static Font 	Typsnitt = new Font("Arial", 0, 40);
	
	public static ImageIcon FönsterIcon = null;
	
	public Mouse(){
		
		
		try {
			FönsterIcon = new ImageIcon(getClass().getResource("/images/Java-icon.png"));
		} catch (Exception e) {
			((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
	    	JOptionPane.showMessageDialog(null, "ImageIcon hittades inte","Filfel",JOptionPane.ERROR_MESSAGE);
		}
		
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
		Arkiv.add(Räkna);
		Arkiv.add(Spelknapp);
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
		HastighetsFönster.add(Box.createRigidArea(new Dimension(100,100)),BorderLayout.SOUTH);
		HastighetsFönster.setLocationRelativeTo(null);
		HastighetsFönster.setResizable(false);

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
		AvslutningsFönster.setDefaultCloseOperation(3);
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
	
		new Mouse();
//		new Snake();
		
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
					SpelaLjud("C:\\WINDOWS\\Media\\tada.wav");
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
			AudioPlayer.player.start(new AudioStream(new FileInputStream(Filnamn)));
		} catch (IOException e) {
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
class Pong extends JPanel implements ActionListener,KeyListener,WindowListener,MouseMotionListener{
	
	private int x,y,VänsterX=0,VänsterY,HögerX,HögerY,RektBredd,RektHöjd,
			bredd=20,höjd=30,hastighet,c, d,PoängVänster=0,PoängHöger=0,py=10,px=10;
	private JFrame frame;
	private Timer timer;
	private Boolean GameOver=false;
	private String PoängTill,SpelareVänster,SpelareHöger;
	
	public void Starta() {
		
			SpelareVänster = JOptionPane.showInputDialog("Spelare till vänster:");
			if (SpelareVänster == null||SpelareVänster=="") {
				SpelareVänster = "Spelare 1";
			}
			SpelareHöger = JOptionPane.showInputDialog("Spelare till höger:");
			if (SpelareHöger == null) {
				SpelareHöger = "Spelare 2";
			}
		try {
			frame.setVisible(true);
			PoängHöger = 0;
			PoängVänster = 0;
			StartaOm();
		}
		catch (Exception e) {
			
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
			
			frame.setLocationRelativeTo(null);
			RektHöjd = 100;
			RektBredd = 10;
			HögerY = getHeight()/2;
			VänsterY = getHeight()/2;
			HögerX=getWidth()-bredd-1;
			timer.start();
			
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

	@Override
	public void windowOpened(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		timer.stop();
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
	@Override
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
class Snake extends JPanel implements KeyListener, ActionListener{
	JFrame frame = new JFrame("Snake");
	int[] x=new int[20],y=new int[20]; 
	int snakelängd = 6,posx=300,posy=100;
	final int pixelstorlek=10;
	Timer timer = new Timer(250, this);
	
	public Snake() {
		for (int i = 1; i <= snakelängd; i++) {
			x[i]=posx;
			y[i]=posy;
			posy=posy-pixelstorlek;
		}
		
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setIconImage(Mouse.FönsterIcon.getImage());
		frame.setSize(500, 500);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.addKeyListener(this);
		timer.start();
		setBackground(Color.WHITE);
				
	}
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		setForeground(Color.black);
		for (int i = 1; i <= snakelängd; i++) {
			g.setColor(Color.BLACK);
			System.out.println(i);
			g.drawRect(x[i], y[i], pixelstorlek, pixelstorlek);
			g.fillRect(x[i], y[i], pixelstorlek, pixelstorlek);
		}
		g.setColor(Color.red);
		g.drawOval(100, 100, 8, 8);
		g.fillOval(100, 100, 8, 8);
		
	}

	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		
		if(KeyEvent.getKeyText(e.getKeyCode()) == "Vänsterpil"){
			posx=posx-10;

		}
		else if(KeyEvent.getKeyText(e.getKeyCode()) == "Högerpil"){
			

		}
		else if(KeyEvent.getKeyText(e.getKeyCode()) == "Upp"){
			

		}
		else if(KeyEvent.getKeyText(e.getKeyCode()) == "Nedpil"){
			

		}
	}

	public void keyReleased(KeyEvent e) {
		
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==timer){
			for (int i = 1; i <= snakelängd; i++) {
				x[i]=posx;
				y[i]=y[i]+pixelstorlek;
//				posy=posy+pixelstorlek;
				frame.repaint();
			}
		}
	}
	
}

