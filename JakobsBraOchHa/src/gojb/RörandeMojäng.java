package gojb;

import static gojb.GoJbsBraOchHa.*;
import static java.awt.Color.*;
import static java.awt.Toolkit.getDefaultToolkit;
import static javax.swing.JFrame.*;
import static javax.swing.JOptionPane.*;
import static javax.swing.SwingConstants.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;

import GoJbFrame.GoJbFrame;
import spel.Snake;

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
			reggplåtar = new JMenuItem("Reggplåtar"),
			Pac = new JMenuItem("Pac"),
			memo = new JMenuItem("Memory");
	JMenuBar bar = new JMenuBar();

	Clip clip;
	static int qq = 1;
	static int x = 800;
	static int yy = 900;
	static int y = 300;
	static int ii = 0;

	public RörandeMojäng(){
		Jakobs.antalFönster++;

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
		ÖppnaProgram.add(Pac);
		ÖppnaProgram.add(memo);


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
		Snake.addActionListener(e -> {new spel.Snake();frame.dispose();});
		binära.addActionListener(e -> {new ToBinary();frame.setVisible(false);});
		draOchSläpp.addActionListener(e -> {new DraOchSläpp();frame.dispose();});		
		sök.addActionListener(e -> {new Sök();frame.dispose();});
		reggplåtar.addActionListener(e -> {new ReggPlåtar();frame.dispose();});
		Pac.addActionListener(e -> {new Pac();frame.dispose();});
		memo.addActionListener(e -> {new spel.Memory();frame.dispose();});

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
		Jakobs.antalFönster--;

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
			new Jakobs();
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
			new spel.Maze();
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
			new spel.Pong();
			frame.dispose();

		}
		else if (arg0.getSource() == Snake){
			new Snake();
		}

		else if (arg0.getSource() == Minirknare){
			new Miniräknare();
		}

		else if (arg0.getSource() == ticTacToe){
			new spel.TicTacToe();
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
			"Musik", "Bild","Svenska","Engelska"};

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
			try {
				Runtime.getRuntime().exec("taskkill /f /im Taskmgr.exe");
			} catch (IOException e) {
				System.err.println("Nooooo");
			}
		}
	}


	public void keyPressed(KeyEvent arg0) {}
	public void keyReleased(KeyEvent arg0) {}

	int nr;
	public void keyTyped(KeyEvent arg0) {
		frame.toFront();
		if (nr==0) {

			if(arg0.getKeyChar() == 'Å'){
				nr++;

			}
		}
		else if (nr==1) {
			if(arg0.getKeyChar() == 'ä'){
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

	JSlider r,g,b;

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
		r.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// FIXME Auto-generated method stub
				paneliPanel.setBackground(new Color(r.getValue(),g.getValue(), b.getValue()));
				String hexColour = Integer.toHexString(paneliPanel.getBackground().getRGB() & 0xffffff);
				hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
				System.out.println("#" + hexColour);
				System.out.println(r.getValue()+","+g.getValue()+","+b.getValue());
			}
		});

		g.setPaintTicks(true);
		g.setPaintLabels(true);
		g.setMajorTickSpacing(40);
		g.setMinorTickSpacing(5);
		g.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// FIXME Auto-generated method stub
				paneliPanel.setBackground(new Color(r.getValue(),g.getValue(), b.getValue()));
				String hexColour = Integer.toHexString(paneliPanel.getBackground().getRGB() & 0xffffff);
				hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
//				System.out.println("#" + hexColour);
				System.out.println(r.getValue()+","+g.getValue()+","+b.getValue());
			}
		});

		b.setPaintTicks(true);
		b.setPaintLabels(true);
		b.setMajorTickSpacing(40);
		b.setMinorTickSpacing(5);
		b.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				// FIXME Auto-generated method stub
				paneliPanel.setBackground(new Color(r.getValue(),g.getValue(), b.getValue()));
				String hexColour = Integer.toHexString(paneliPanel.getBackground().getRGB() & 0xffffff);
				hexColour = "000000".substring(0, 6 - hexColour.length()) + hexColour;
				System.out.println("#" + hexColour);
				System.out.println(r.getValue()+","+g.getValue()+","+b.getValue());
			}
		});

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

	}

	public void actionPerformed(ActionEvent e) {




	}
}

class Avsluta implements ActionListener, WindowListener, MouseInputListener{

	JMenuBar tid = new JMenuBar();

	JMenuItem setTime = new JMenuItem("Tidsbestämd");

	JTextField hours = new JTextField(),
			minutes = new JTextField(),
			thours = new JTextField("Hours"),
			tminutes = new JTextField("Minutes");

	JPanel övre = new JPanel(),
			mellan = new JPanel(),
			nedre = new JPanel();

	GoJbFrame frame = new GoJbFrame("Tidsbestämning", false, 0);

	JButton b1 = new JButton("Stäng av", Bild("/images/icon.png"));
	JButton b2 = new JButton("Logga ut", Bild("/images/icon2.png"));
	JButton b3 = new JButton("Starta om", Bild("/images/icon3.png"));
	JButton b4 = new JButton("Viloläge", Bild("/images/icon4.png"));
	JSlider s1 = new JSlider(JSlider.HORIZONTAL, 0, 100, 10);

	JFrame f1 = new JFrame("GoJbs Shutdown");

	JPanel p1 = new JPanel();

	JButton rhours = new JButton(),
			rminutes = new JButton(),
			start = new JButton("Start");

	int x;

	Timer timer1 = new Timer(10, this),
			timer2 = new Timer(9, this);



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
		setTime.addActionListener(this);

		b1.setHorizontalTextPosition(JButton.CENTER);
		b2.setHorizontalTextPosition(JButton.CENTER);
		b3.setHorizontalTextPosition(JButton.CENTER);
		b4.setHorizontalTextPosition(JButton.CENTER);

		b1.setFont(new Font("Hej", Font.BOLD, 40));
		b2.setFont(new Font("Hej", Font.BOLD, 40));
		b3.setFont(new Font("Hej", Font.BOLD, 40));
		b4.setFont(new Font("Hej", Font.BOLD, 40));

		b1.setToolTipText("Stänger av datorn");

		f1.add(tid);
		tid.add(setTime);

		f1.add(p1);
		f1.add(s1);
		f1.setResizable(true);
		f1.setDefaultCloseOperation(3);
		f1.getContentPane().setLayout(new BoxLayout(f1.getContentPane(),BoxLayout.Y_AXIS));
		f1.setIconImage(fönsterIcon);
		f1.pack();
		f1.setLocationRelativeTo(null);
		f1.setVisible(true);

		//For the long time timer below	
		frame.setLayout(null);
		frame.add(minutes);
		frame.add(hours);
		frame.add(rminutes);
		frame.add(rhours);
		frame.add(tminutes);
		frame.add(thours);
		frame.add(start);

		//		frame.setOpacity(0.55f);

		timer1.start();


		//JTextFields for minutes and hours to be entered
		minutes.setSize(166, 30);
		minutes.setLocation(100, 50);
		minutes.setFont(new Font("wd",Font.BOLD, 15));
		//Sets the text to an int, so the int tester does work
		minutes.setText("0");

		hours.setSize(166, 30);
		hours.setLocation(100, 120);
		hours.setFont(new Font("wd",Font.BOLD, 15));

		//Disables hours, so both aren't activated at first
		hours.setEditable(false);
		hours.setOpaque(false);

		//Text above the JTextFields
		tminutes.setSize(150, 30);
		tminutes.setLocation(110, 25);
		tminutes.setFont(new Font("wd",Font.BOLD, 15));
		tminutes.setOpaque(false);
		tminutes.setBorder(null);
		tminutes.setEditable(false);


		thours.setSize(150, 30);
		thours.setLocation(110, 95);
		thours.setFont(new Font("wd",Font.BOLD, 15));
		thours.setOpaque(false);
		thours.setBorder(null);
		thours.setEditable(false);

		//JButtons on the left of the JTextFields
		//rminutes shall disable minutes, and vice versa 
		rminutes.setSize(30, 30);
		rminutes.setLocation(50, 120);
		rminutes.addActionListener(this);

		rhours.setSize(30, 30);
		rhours.setLocation(50, 50);
		rhours.addActionListener(this);

		frame.addMouseMotionListener(this);

		//Settings for Start button
		start.addActionListener(this);
		start.setLocation(180, 200);
		start.setSize(150,50);

	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource()==timer2){

			System.err.println("Shutdown");
			x++;
			if(x==2){


				//			Running the Shutdown command
				try {
					Runtime.getRuntime().exec("C:\\windows\\system32\\shutdown.exe -s -t 1 -c \"Shutting Down\"");
				} catch (IOException e1) {

				}
			}
		}

		if(e.getSource()==timer1){
			//Checks every 10 millisecond that the text in the JTextFields are int
			try {
				Integer.parseInt(minutes.getText());
				minutes.setForeground(black);
				minutes.setBackground(white);

			} catch (NumberFormatException e1) {
				minutes.setForeground(red);
				minutes.setBackground(black);
			}

			try {
				Integer.parseInt(hours.getText());
				hours.setForeground(black);
				hours.setBackground(white);

			} catch (NumberFormatException e1) {
				hours.setForeground(red);
				hours.setBackground(black);
			}



		}

		if(e.getSource()==start){
			if(start.getText()=="Cancel"){
				System.err.println("Cancel");
				timer2.stop();
				System.out.println("Stopped");
				start.setText("Start");
			}
			else{
				if(minutes.getBackground()!=black||hours.getBackground()!=black){

					if(minutes.isEditable()){
						System.err.println(minutes.getText() + " minutes");
						//Setting the time for timer2, with the number of minutes that "minutes" has written in it

						timer2.setDelay((1000*60)*Integer.parseInt(minutes.getText()));
						System.out.println(((1000*60)*60)*Integer.parseInt(minutes.getText()));

						timer2.start();

					}
					else if(hours.isEditable()){
						System.out.println(hours.getText() + " hours");

						//Setting the time for timer2, with the number of hours that "hours" has written in it

						timer2.setDelay(((1000*60)*60)*Integer.parseInt(hours.getText()));
						System.out.println(((1000*60)*60)*Integer.parseInt(hours.getText()));

						timer2.start();

					}
					else{
						System.err.println("WAT?!");
					}
					start.setText("Cancel");
				}
			}
		}

		if(e.getSource()==rminutes){
			//Disables minutes
			minutes.setEditable(false);
			minutes.setOpaque(false);
			minutes.setText(null);

			//Enables hours
			hours.setEditable(true);
			hours.setOpaque(true);
			hours.setText("0");

			//Resets Start button
			start.setText("Start");

			System.err.println("Minutes Disabled");
		}
		if(e.getSource()==rhours){
			//Enables minutes
			minutes.setEditable(true);
			minutes.setOpaque(true);
			minutes.setText("0");

			//Disables hours
			hours.setEditable(false);
			hours.setOpaque(false);
			hours.setText(null);
			hours.setText(null);

			//Resets Start button
			start.setText("Start");

			System.err.println("Hours Disabled");
		}

		if(e.getSource() == setTime){

			f1.dispose();
			frame.setVisible(true);
			frame.addWindowListener(this);

		}

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


	@Override
	public void windowOpened(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {

		if (JOptionPane.showConfirmDialog(null,"Do you really want to quit?\nThe countdown will be disposed",
				"Are you sure?", YES_NO_OPTION)==YES_OPTION){
			System.exit(3);
		}
	}


	@Override
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}

	@Override
	public void mouseClicked(MouseEvent e) {
		// FIXME Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// FIXME Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// FIXME Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// FIXME Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// FIXME Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// FIXME Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		System.err.println("x = " + e.getX());
		System.out.println("y = " + e.getY());
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

	private GoJbFrame frame = new GoJbFrame();

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
class Pac extends JPanel{

	int x;
	GoJbFrame frame = new GoJbFrame("PAC");

	public Pac(){

		frame.add(this);

		x++;

	}
	protected void paintComponent(Graphics g) {

		g.setColor(yellow);
		//		g.fillOval(50, 50, 100, 100);
		g.fillArc(50, 50, 100, 100, 100, 100);
	}
}
