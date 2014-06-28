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
	
	JFrame			HuvudF�nster = new JFrame("Hej Hej :D"), 
					H�ndelseF�nster = new JFrame("H�ndelser"),
					HastighetsF�nster =  new JFrame(),
					om = new JFrame("Om"),
					R�knare = new JFrame("Minir�knare"),
					Laddf�nster = new JFrame("Startar..."),
					AvslutningsF�nster = new JFrame("Avslutar...");
	
	JTextArea 		text = new JTextArea(), 
					R�knartext = new JTextArea();
			
	JPanel 			KnappPanel = new JPanel(), 
					R�knarKnappar = new JPanel(),
					R�knarPanel = new JPanel();

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
				  	Spelknapp = new JMenuItem("�ppna spel");
	
	JButton 		knapp1 = new JButton("Bl�"),
					knapp2 = new JButton("Gr�n"),
					knapp3 = new JButton("R�d"),
					knapp4 = new JButton("Gul"),
					OK = new JButton("Klar"),
					Minir�nkarknapp0 = new JButton("0"),
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
					Minir�nkarknappLikamed = new JButton("="),
					Autoscrollknapp = new JButton("St�ng av autoscroll"),
					RensKnapp = new JButton("Rensa"),
					C = new JButton("C"),
					Punkt = new JButton(".");
	
	JScrollPane 	Jaha = new JScrollPane(text,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
											JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	JProgressBar	LaddstapelStart = new JProgressBar(0,100),
				 	LaddstapelAvslut = new JProgressBar(0, 100);
	
	JLabel 			omtext = new JLabel("<html>Hall�j! Det h�r programmet �r skapat av GoJbs Javaprogrammering"),
					Laddtext = new JLabel("Startar program..."),
					AvslutningsText = new JLabel("Avslutar program..."),
					Summa = new JLabel(""),
					R�knes�tt = new JLabel();
	
 	JSlider 		Slide = new JSlider(JSlider.HORIZONTAL,0,100,10);
 		
	boolean 		autoscroll = true,
					nyr�kning = false;
	
	int				FlyttHastighet = 10,posX = 125, posY = 75;
	
	static int		AntalF�nster = 0;

	double 			a = 0,
					b = 0;
	
	Timer 			StartTimer = new Timer(2, this),
					SlutTimer = new Timer(2, this);
	
	Pong			pong = new Pong();
	
	Robot			robot;
	
	Color			F�rg = new Color(0, 0, 255);
	
	static Font 	Typsnitt = new Font("Arial", 0, 40);
	
	public static ImageIcon F�nsterIcon = null;
	
	public Mouse(){
		
		
		try {
			F�nsterIcon = new ImageIcon(getClass().getResource("/images/Java-icon.png"));
		} catch (Exception e) {
			((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
	    	JOptionPane.showMessageDialog(null, "ImageIcon hittades inte","Filfel",JOptionPane.ERROR_MESSAGE);
		}
		
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
		Laddf�nster.setIconImage(F�nsterIcon.getImage());
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
		Arkiv.add(R�kna);
		Arkiv.add(Spelknapp);
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

		text.setEditable(false);
		
		setSize(10000,10000);
		
		H�ndelseF�nster.setSize(500,500);
		H�ndelseF�nster.setLayout(new BorderLayout());
		H�ndelseF�nster.add(Autoscrollknapp,BorderLayout.NORTH);
		H�ndelseF�nster.add(Jaha,BorderLayout.CENTER);
		H�ndelseF�nster.add(RensKnapp,BorderLayout.SOUTH);
		H�ndelseF�nster.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		H�ndelseF�nster.setAlwaysOnTop(true);
		H�ndelseF�nster.setResizable(false);
		H�ndelseF�nster.setIconImage(F�nsterIcon.getImage());
		
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
		HastighetsF�nster.add(Box.createRigidArea(new Dimension(100,100)),BorderLayout.SOUTH);
		HastighetsF�nster.setLocationRelativeTo(null);
		HastighetsF�nster.setResizable(false);

		KnappPanel.add(knapp1);
		KnappPanel.add(knapp2);
		KnappPanel.add(knapp3);
		KnappPanel.add(knapp4);
				
		omtext.setVerticalTextPosition(1);
		omtext.setFont(Typsnitt);
		omtext.addMouseListener(this);
		om.setSize(300,300);
		om.add(omtext);
		om.setIconImage(F�nsterIcon.getImage());
		om.setLocationRelativeTo(HuvudF�nster);
		
		HuvudF�nster.setJMenuBar(MenyRad);
		HuvudF�nster.setSize(800, 800);
		HuvudF�nster.setLayout(new BorderLayout());
		HuvudF�nster.setMinimumSize(new Dimension(400,400));
		HuvudF�nster.addKeyListener(this);
		HuvudF�nster.addWindowListener(this);
		HuvudF�nster.setIconImage(F�nsterIcon.getImage());
		HuvudF�nster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.NORTH);
		HuvudF�nster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.WEST);
		HuvudF�nster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.EAST);
		HuvudF�nster.add(Box.createRigidArea(new Dimension(20,20)),BorderLayout.SOUTH);
		HuvudF�nster.add(this,BorderLayout.CENTER);
		HuvudF�nster.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		HuvudF�nster.setLocationRelativeTo(null);		
		HuvudF�nster.revalidate();
		HuvudF�nster.repaint();
		
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
		R�knare.setIconImage(F�nsterIcon.getImage());
		R�knare.getContentPane().setBackground(Color.white);
		
		AvslutningsText.setFont(Typsnitt);
		AvslutningsText.setHorizontalAlignment(JLabel.CENTER);
		
		AvslutningsF�nster.setLayout(new BorderLayout(10,10));
		AvslutningsF�nster.add(LaddstapelAvslut,BorderLayout.CENTER);
		AvslutningsF�nster.add(AvslutningsText,BorderLayout.NORTH);
		AvslutningsF�nster.setUndecorated(true);
		AvslutningsF�nster.setSize(Laddf�nster.getSize());
		AvslutningsF�nster.setDefaultCloseOperation(3);
		AvslutningsF�nster.setResizable(false);
		AvslutningsF�nster.setAlwaysOnTop(true);
		AvslutningsF�nster.setIconImage(F�nsterIcon.getImage());
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
	
		new Mouse();
//		new Snake();
		
	}
	
	public void Minir�knare(){
		R�knare.setVisible(true);

	}

	public void mouseDragged(MouseEvent e) {
		SkrivH�ndelsetext("Du drog musen: " + e.getX() + ", " + e.getY());
		posX = e.getX();
		posY = e.getY();
//		repaint();
//		ee.repaint();
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
			Minir�knare();
			
		}
		else if (knapp.getSource() == Minir�nkarknappDelat){ 
			if (R�knes�tt.getText()==("")) {
				R�knes�tt.setText("del");
			}
			R�knaUt();
			R�knes�tt.setText("/");
			nyr�kning = false;
		}
		else if (knapp.getSource() == Minir�nkarknappG�nger){ 
			R�knaUt();
			R�knes�tt.setText("*");
			nyr�kning = false;
		}
		else if (knapp.getSource() == Minir�nkarknappMinus){ 
			R�knaUt();
			R�knes�tt.setText("-");
			nyr�kning = false;
		}
		else if (knapp.getSource() == Minir�nkarknappPlus){ 
			R�knaUt();
			R�knes�tt.setText("+");
			nyr�kning = false;
			
		}
		else if (knapp.getSource()==C) {
			R�knes�tt.setText(null);
			Summa.setText(null);
			R�knartext.setText(null);
		}
		
		if (nyr�kning==true){
			nyr�kning = false;
			C.doClick();
			
		}
		if (knapp.getSource() == Minir�nkarknappLikamed){
			R�knaUt();
			R�knes�tt.setText("");
			nyr�kning = true;
		}
		else if (knapp.getSource() == Minir�nkarknapp0){ 
			 R�knartext.append("0");
			 
		}
		else if (knapp.getSource() == Minir�nkarknapp1){ 
			 R�knartext.append("1");
			 
		}
		else if (knapp.getSource() == Minir�nkarknapp2){ 
			 R�knartext.append("2");
			 
		}
		else if (knapp.getSource() == Minir�nkarknapp3){ 
			 R�knartext.append("3");
			 
			 	}
		else if (knapp.getSource() == Minir�nkarknapp4){ 
			 R�knartext.append("4");
			 
			 	}
		else if (knapp.getSource() == Minir�nkarknapp5){ 
			 R�knartext.append("5");
			 
			 	}
		else if (knapp.getSource() == Minir�nkarknapp6){ 
			 R�knartext.append("6");
			 
			 	}
		else if (knapp.getSource() == Minir�nkarknapp7){ 
			 R�knartext.append("7");
			 
			 	}
		else if (knapp.getSource() == Minir�nkarknapp8){ 
			 R�knartext.append("8");
			 
			 	}
		else if (knapp.getSource() == Minir�nkarknapp9){ 
			 R�knartext.append("9");
			 
		}
		else if (knapp.getSource() == Punkt) {
			R�knartext.append(".");
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
		System.out.println("cd");
		if (e.getSource() == omtext) {
			System.out.println("-�khfnzxEH");
		}
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
		R�knare.dispose();
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
			AudioPlayer.player.start(new AudioStream(new FileInputStream(Filnamn)));
		} catch (IOException e) {
			((Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
			JOptionPane.showMessageDialog(null, "Filen: \"" + Filnamn + "\" hittades inte", "Ljud", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	public void SkrivH�ndelsetext(String H�ndlsetext){
		text.append(H�ndlsetext + "\n");
		DefaultCaret caret = (DefaultCaret)text.getCaret();
		caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
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
	
	public String Texten = "Dra eller anv�nd piltangenterna";

	public void paintComponent(Graphics g){
		  
		super.paintComponent(g);
		
	    Graphics2D g2 = (Graphics2D)g;
	    
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	    g2.setFont(new Font("Serif", Font.ROMAN_BASELINE, 35));

		g2.drawString(Texten,posX, posY); 
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

@SuppressWarnings("serial")
class Pong extends JPanel implements ActionListener,KeyListener,WindowListener,MouseMotionListener{
	
	private int x,y,V�nsterX=0,V�nsterY,H�gerX,H�gerY,RektBredd,RektH�jd,
			bredd=20,h�jd=30,hastighet,c, d,Po�ngV�nster=0,Po�ngH�ger=0,py=10,px=10;
	private JFrame frame;
	private Timer timer;
	private Boolean GameOver=false;
	private String Po�ngTill,SpelareV�nster,SpelareH�ger;
	
	public void Starta() {
		
			SpelareV�nster = JOptionPane.showInputDialog("Spelare till v�nster:");
			if (SpelareV�nster == null||SpelareV�nster=="") {
				SpelareV�nster = "Spelare 1";
			}
			SpelareH�ger = JOptionPane.showInputDialog("Spelare till h�ger:");
			if (SpelareH�ger == null) {
				SpelareH�ger = "Spelare 2";
			}
		try {
			frame.setVisible(true);
			Po�ngH�ger = 0;
			Po�ngV�nster = 0;
			StartaOm();
		}
		catch (Exception e) {
			
			frame = new JFrame("Spel");
			frame.add(this);
			setForeground(Color.red);
			setPreferredSize(new Dimension(700, 500));
			frame.setVisible(true);
			frame.setIconImage(Mouse.F�nsterIcon.getImage());
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
			RektH�jd = 100;
			RektBredd = 10;
			H�gerY = getHeight()/2;
			V�nsterY = getHeight()/2;
			H�gerX=getWidth()-bredd-1;
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
			V�nsterY=V�nsterY-20;
		}
		else if (e.getKeyCode() == 83) {
			V�nsterY=V�nsterY+20;
		}
		System.out.println("lllk");
		if(KeyEvent.getKeyText(e.getKeyCode()) == "Upp"){
			H�gerY=H�gerY-20;
			
		}
		else if(KeyEvent.getKeyText(e.getKeyCode()) == "Nedpil"){
			H�gerY=H�gerY+20;
			
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
			
			if (x+bredd>=H�gerX) {
				
				if (y>=H�gerY) {
					if (y<=H�gerY+RektH�jd) {
						System.out.println("aaaaal");
						System.out.println("jckfuhol");
						hastighet++;
						c= -hastighet;
					}
					else{
						GameOver("V�nster");;
					
					}
				}
				else{
					GameOver("V�nster");
				}
			}
			else if (x<=V�nsterX+RektBredd) {
				System.out.println("1");
				if (y>=V�nsterY) {
					System.out.println("2");
					if (y<=V�nsterY+RektH�jd) {
						System.out.println("aajckfuhol");
						hastighet++;
						c=hastighet;
					}
					else{
						GameOver("H�ger");
					}
				}
				else{
					GameOver("H�ger");
				}
			
			}
			else if (y+h�jd>=getHeight()) {
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
			H�gerX=getWidth()-RektBredd-1;
			
			
			
		}
	}
	public void paintComponent(Graphics g){
		
//		x = getWidth()/2;
//		y = getHeight()/2;
		Graphics2D g2 = (Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (GameOver==true) {
			g2.setFont(new Font("", Font.BOLD, 50));
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
	int snakel�ngd = 6,posx=300,posy=100;
	final int pixelstorlek=10;
	Timer timer = new Timer(250, this);
	
	public Snake() {
		for (int i = 1; i <= snakel�ngd; i++) {
			x[i]=posx;
			y[i]=posy;
			posy=posy-pixelstorlek;
		}
		
		frame.add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setIconImage(Mouse.F�nsterIcon.getImage());
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
		for (int i = 1; i <= snakel�ngd; i++) {
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
		
		if(KeyEvent.getKeyText(e.getKeyCode()) == "V�nsterpil"){
			posx=posx-10;

		}
		else if(KeyEvent.getKeyText(e.getKeyCode()) == "H�gerpil"){
			

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
			for (int i = 1; i <= snakel�ngd; i++) {
				x[i]=posx;
				y[i]=y[i]+pixelstorlek;
//				posy=posy+pixelstorlek;
				frame.repaint();
			}
		}
	}
	
}

