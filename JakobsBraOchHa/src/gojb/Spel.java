package gojb;

import static gojb.GoJbsBraOchHa.*;
import static gojb.Snake.Spell�ge.*;
import static java.awt.Color.*;
import static java.awt.Toolkit.getDefaultToolkit;
import static javax.swing.JOptionPane.*;
import static javax.swing.SwingConstants.CENTER;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

import GoJbFrame.GoJbFrame;

class FlappyGoJb extends JPanel implements KeyListener,WindowListener{
	private static final long serialVersionUID = 1L;

	private JFrame frame = new JFrame("FlappyGoJb");
	private int x,y,a,po�ng=-1;
	private final int bredd=35;
	private Timer timer = new Timer(20, e -> check());
	private boolean mellanslag;

	FlappyGoJb(){

		setBackground(new Color(255, 255, 255));
		frame.addKeyListener(this);
		frame.setIconImage(f�nsterIcon);
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
		if (showConfirmDialog(null, "Game over! Vill du spela igen?","Du f�rlorade!",YES_NO_OPTION,ERROR_MESSAGE)!=YES_OPTION) {
			frame.dispose();
			return;
		}
		po�ng=-1;
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
		po�ng++;
		a=a-getHeight();
		x=getWidth();
	}
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;

		g2.drawImage(f�nsterIcon, 20,y,null);
		g2.fillRect(x, a, bredd,getHeight());
		g2.drawRect(x, a, bredd,getHeight());
		g2.fillRect(x, a+164+getHeight(), bredd,getHeight());
		g2.drawRect(x, a+164+getHeight(), bredd,getHeight());
		g2.setFont(typsnitt);
		g2.setColor(green);
		g2.drawString(Integer.toString(po�ng), getWidth()/2, 50);
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
class Snake extends JPanel implements KeyListener, ActionListener, ComponentListener{
	enum Spell�ge {ONE,TWO,CLIENT,SERVER}
	Spell�ge spell�ge;
	private JFrame frame = new JFrame("Snake"), start = new JFrame("Start"),v�ntframe= new JFrame(),highFrame=new JFrame("Highscore");
	private final int startl�ngd=3, pixelstorlek=Math.round(SK�RM_SIZE.width/140), MAXIMUM = 101;
	private int[] x=new int[MAXIMUM], y=new int[MAXIMUM], z=new int[MAXIMUM], q=new int[MAXIMUM];
	private int snakel�ngdx,snakel�ngdz=-1,pluppX,pluppY,s=1,a=1,svartPo�ng,cyanPo�ng,yLoc;
	private Timer timer = new Timer(100, this);
	private String riktning = "ner",riktningz = "upp",vem;
	private BufferedReader in;
	private PrintWriter out;
	private boolean paused,gameover=true;
	private JButton local = new JButton("Play two on this computer"),
			online = new JButton("Play online"),
			one = new JButton("Single Player");
	private String[] highscore = new String[6];
	private Socket socket;

	public Snake(){
		setOpaque(true);
		setBackground(white);
		setPreferredSize(new Dimension(pixelstorlek*50+1, pixelstorlek*50+1));
		setMaximumSize(new Dimension(pixelstorlek*50+1, pixelstorlek*50+1));
		setMinimumSize(new Dimension(pixelstorlek*50+1, pixelstorlek*50+1));
		frame.setLayout(new BorderLayout());
		frame.add(this,BorderLayout.CENTER);		
		frame.setIconImage(f�nsterIcon);
		frame.setResizable(false);		
		frame.addKeyListener(this);
		frame.addWindowListener(autoListener);
		frame.getContentPane().setBackground(black);
		frame.addComponentListener(this);	
		frame.setDefaultCloseOperation(2);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {}public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}public void windowDeactivated(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}public void windowClosed(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				try {
					socket.close();
				} catch (Exception e1) {}
				try {
					highFrame.dispose();
					timer.stop();
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
		start.setIconImage(f�nsterIcon);
		start.setVisible(true);

		local.addActionListener(e -> {
			spell�ge=TWO;
			start.dispose();
			frame.setVisible(true);
			Restart();
			Pausa();
			frame.revalidate();
			frame.repaint();
		});
		one.addActionListener(e -> {
			spell�ge=ONE;
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
			highFrame.setIconImage(f�nsterIcon);
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
			spell�ge=SERVER;
			frame.setTitle(frame.getTitle() + " SERVER");
			start.dispose();
			v�ntframe.add(new JLabel("V�ntar p� anslutning...",Bild("/images/loading.gif"),CENTER));
			v�ntframe.setLocationRelativeTo(null);
			v�ntframe.setAlwaysOnTop(true);
			v�ntframe.repaint();
			v�ntframe.getContentPane().setBackground(white);
			v�ntframe.setIconImage(f�nsterIcon);
			v�ntframe.pack();
			v�ntframe.addWindowListener(autoListener);
			v�ntframe.setDefaultCloseOperation(2);
			v�ntframe.setVisible(true);
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
					v�ntframe.dispose();
					Restart();
					Pausa();
					frame.setVisible(true);	
					frame.pack();
					frame.repaint();
					while (true) {
						try {
							String string = in.readLine();
							if(a==1){
								if(string.equals("v�nster")){
									if (riktningz!="h�ger"&&riktningz!="v�nster"){
										riktningz="v�nster";
										a=0;
									}
								}
								else if(string.equals("h�ger")){
									if (riktningz!="h�ger"&&riktningz!="v�nster"){
										riktningz="h�ger";
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
								else if (string.equals("restart")) {
									Restart();
								}
								else if (string.equals("pause")) {
									Pausa();
								}
							}
							System.out.println(riktningz);

						} catch (Exception e) {
							System.err.println("Fr�nkopplad");
							break;
						}
					}
				}
			}.start();
		}
		else if (i==1) {
			start.dispose();
			spell�ge=CLIENT;
			try {
				socket = new Socket(showInputDialog("Adress till server"), 11622);
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
			frame.setVisible(true);
			frame.pack();
			paused=false;
			new Thread(){
				public void run() {
					Scanner scanner = null;
					while (true) {
						try {
							scanner = new Scanner(in.readLine());
							String string = scanner.next();
							if (string.equals("B")) {
								snakel�ngdx=0;
							}
							else if (string.equals("C")) {
								snakel�ngdz=0;
							}
							else if (string.equals("A")) {
								paused=false;
								gameover=false;
							}
							while (scanner.hasNext()) {
								if (string.equals("A")) {
									String string2 = scanner.next();
									if (string2.equals("paus")) {
										paused=true;
									}
									else if (string2.equals("gameover")) {
										gameover=true;
									}
									else {
										vem = string2;
									}
								}
								else if (string.equals("B")) {
									x[++snakel�ngdx]=Integer.parseInt(scanner.next())*pixelstorlek;
									y[snakel�ngdx]=Integer.parseInt(scanner.next())*pixelstorlek;
								}
								else if (string.equals("C")) {
									z[++snakel�ngdz]=Integer.parseInt(scanner.next())*pixelstorlek;
									q[snakel�ngdz]=Integer.parseInt(scanner.next())*pixelstorlek;
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
							System.err.println("Fr�nkopplad");
							break;
						}
					}
				}
			}.start();
		}
	}
	private void GameOver(Boolean svart){
		timer.stop();
		frame.repaint();
		frame.revalidate();

		gameover = true;

		((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
		if (spell�ge==ONE) {
			vem="Du";
			Scanner scanner = new Scanner(highscore[5]);
			int hs = scanner.nextInt();
			scanner.close();
			if (snakel�ngdx > hs) {
				highscore[5] = Integer.toString(snakel�ngdx) + " "
						+ showInputDialog("Skriv ditt namn");
				if (snakel�ngdx < 10) {
					highscore[5] = "0" + highscore[5];
				}

				Arrays.sort(highscore);
				String[] tillf�lligscore = new String[6];
				tillf�lligscore[1] = highscore[1];
				tillf�lligscore[2] = highscore[2];
				tillf�lligscore[3] = highscore[3];
				tillf�lligscore[4] = highscore[4];
				tillf�lligscore[5] = highscore[5];

				highscore[1] = tillf�lligscore[5];
				highscore[2] = tillf�lligscore[4];
				highscore[3] = tillf�lligscore[3];
				highscore[4] = tillf�lligscore[2];
				highscore[5] = tillf�lligscore[1];

				prop.setProperty("Score1", (highscore[1]));
				prop.setProperty("Score2", (highscore[2]));
				prop.setProperty("Score3", (highscore[3]));
				prop.setProperty("Score4", (highscore[4]));
				prop.setProperty("Score5", (highscore[5]));
				highFrame.repaint();
			}
			sparaProp("Highscore i Snakespel");
			Jakobs.skrivH�ndelsetext(highscore[1]);
			Jakobs.skrivH�ndelsetext(highscore[2]);
			Jakobs.skrivH�ndelsetext(highscore[3]);
			Jakobs.skrivH�ndelsetext(highscore[4]);
			Jakobs.skrivH�ndelsetext(highscore[5]);
		}
		else {
			if (svart) {
				cyanPo�ng++;
				System.err.println("Po�ng till Cyan. (C) "+cyanPo�ng+" - "+svartPo�ng+" (S)");
				vem="Svart";
			}
			else{
				svartPo�ng++;
				System.err.println("Po�ng till Svart. (C) "+cyanPo�ng+" - "+svartPo�ng+" (S)");
				vem="Cyan";
			}
			if(svartPo�ng==10){
				JOptionPane.showMessageDialog(null, "Svart vann!");
				frame.dispose();
			}
			else if (cyanPo�ng==10){
				JOptionPane.showMessageDialog(null, "Cyan vann!");
				frame.dispose();
			}
		}
	}
	private void Pausa(){
		if(!gameover){
			if (paused) {
				timer.start();
				paused=false;
			}
			else {
				timer.stop();
				paused=true;
				frame.repaint();
			}
		}
	}
	private void Restart() {
		if (paused||gameover){
			for (int i = 1; i < x.length; i++) {
				x[i]=-15;
				q[i]=-15;
				y[i]=-15;
				z[i]=-15;
			}
			repaint();

			int posx = random.nextInt(getWidth()/pixelstorlek)*pixelstorlek;
			int posy = random.nextInt(getHeight()/pixelstorlek)*pixelstorlek;

			int posyz = random.nextInt(getWidth()/pixelstorlek)*pixelstorlek;
			int posyq = random.nextInt(getHeight()/pixelstorlek)*pixelstorlek;

			if (posx>getWidth()*0.8||posx<getWidth()*0.2||posy>getHeight()*0.8||posy<getHeight()*0.2||
					posyz>getWidth()*0.8||posyz<getWidth()*0.2||posyq>getHeight()*0.8||posyq<getHeight()*0.2) {
				Restart();
			}
			else{		
				String [] arr = {"upp", "ner", "h�ger", "v�nster"};

				riktning=arr[random.nextInt(arr.length)];
				snakel�ngdx = startl�ngd;
				x[1]=posx;
				y[1]=posy;
				s=1;

				if (spell�ge!=ONE) {
					riktningz=arr[random.nextInt(arr.length)];
					snakel�ngdz = startl�ngd;
					z[1] = posyz;
					q[1] = posyq;
					a=1;
				}
				else {
					z[1]=-2;
				}
				PlaceraPlupp();
				gameover=false;
				paused=false;
				repaint();
				timer.start();
			}
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

		g.setColor(red);
		g.drawOval(pluppX+1, pluppY+1, pixelstorlek-2, pixelstorlek-2);
		g.fillOval(pluppX+1, pluppY+1, pixelstorlek-2, pixelstorlek-2);
		g.setColor(black);
		for (int i = 1; i <= snakel�ngdx; i++) {
			g.drawRect(x[i]+1, y[i]+1, pixelstorlek-2, pixelstorlek-2);
			g.fillRect(x[i]+1, y[i]+1, pixelstorlek-2, pixelstorlek-2);
		}
		s=1;
		if (spell�ge!=ONE) {
			g.setColor(cyan);
			for (int i = 1; i <= snakel�ngdz; i++) {
				g.drawRect(z[i] + 1, q[i] + 1, pixelstorlek - 2,pixelstorlek - 2);
				g.fillRect(z[i] + 1, q[i] + 1, pixelstorlek - 2,pixelstorlek - 2);
			}
			a = 1;
		}
		if(paused){
			g.setColor(blue);
			g.setFont(new Font(null, 0, 25));
			g.drawString("Spelet pausat. Tryck p� mellanslag f�r att forts�tta.", 10, getHeight()/2);
		}
		if (gameover) {
			g.setColor(red);
			g.setFont(new Font(null, 0, 25));
			g.drawString(vem+" f�rlorade!",10 , getHeight()/2-25);
			g.drawString("Tryck F2 eller \"R\" f�r \natt spela igen",10 , getHeight()/2);
		}
		if (spell�ge==SERVER) {
			try {
				out.print("A ");
				if (paused) {
					out.print("paus ");
				}
				if (gameover) {
					out.print("gameover " + vem);
				}
				out.println();
				out.print("B ");
				for (int i = 1; i <= snakel�ngdx; i++) {
					out.print(x[i] / pixelstorlek + " " + y[i] / pixelstorlek + " ");
				}
				out.println();
				out.print("C ");
				for (int i = 1; i <= snakel�ngdz; i++) {
					out.print(z[i] / pixelstorlek + " " + q[i] / pixelstorlek + " ");
				}
				out.println();
				out.println("P " + pluppX / pixelstorlek + " " + pluppY / pixelstorlek);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else if (spell�ge==ONE) {
			if(y[1] < 45) {
				yLoc = y[1] + 40;
			}
			if (y[1] > 45){
				yLoc = y[1] - 20;
			}
			g.setColor(GREEN);
			g.setFont(typsnitt);
			g.drawString(Integer.toString(snakel�ngdx), x[1], yLoc);
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (x[1]==pluppX&&y[1]==pluppY) {
			PlaceraPlupp();
			((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.asterisk")).run();
			System.err.println(++snakel�ngdx);
		}
		if (spell�ge!=ONE) {
			if (z[1] == pluppX && q[1] == pluppY) {
				PlaceraPlupp();
				((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.asterisk")).run();
				System.err.println(++snakel�ngdz);
			}
			for (int i = snakel�ngdz + 1; i >= 2; i--) {
				z[i] = z[i-1];
				q[i] = q[i-1];
			}
			if (riktningz == "ner") 
				q[1] += pixelstorlek;
			else if (riktningz == "upp") 
				q[1] -= pixelstorlek;
			else if (riktningz == "h�ger")
				z[1] += pixelstorlek;
			else if (riktningz == "v�nster")
				z[1] -= pixelstorlek;
		}
		for (int i = snakel�ngdx +1 ; i >= 2; i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		if (riktning=="ner") 
			y[1]+=pixelstorlek;
		else if (riktning=="upp")
			y[1]-=pixelstorlek;
		else if (riktning=="h�ger")
			x[1]+=pixelstorlek;
		else if (riktning=="v�nster")
			x[1]-=pixelstorlek;

		for (int i = 1; i <= snakel�ngdx||i<=snakel�ngdz; i++) {
			if((i>1&&x[1]==x[i]&&y[1]==y[i])||(x[1]==z[i]&&y[1]==q[i])) {
				GameOver(true);
				break;
			}
			else if ((i>1&&z[1]==z[i]&&q[1]==q[i])||(z[1]==x[i]&&q[1]==y[i])) {
				GameOver(false);
				break;
			}
		}
		if (x[1]<0||x[1]+pixelstorlek>getWidth()||y[1]<0||y[1]+pixelstorlek>getHeight())
			GameOver(true);
		else if (spell�ge!=ONE&&(z[1]<0||z[1]+pixelstorlek>getWidth()||q[1]<0||q[1]+pixelstorlek>getHeight()))
			GameOver(false);

		frame.repaint();
		frame.revalidate();
	}
	public void keyTyped(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void componentResized(ComponentEvent e) {}
	public void componentShown(ComponentEvent e) {}
	public void componentHidden(ComponentEvent e) {}
	public void keyPressed(KeyEvent e) {
		if (spell�ge==CLIENT) {
			System.err.println(riktningz);
			System.err.println(e.getKeyCode());
			if(e.getKeyCode() == KeyEvent.VK_LEFT)
				out.println("v�nster");
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
				out.println("h�ger");
			else if(e.getKeyCode() == KeyEvent.VK_UP)
				out.println("upp");
			else if(e.getKeyCode() == KeyEvent.VK_DOWN)
				out.println("ner");
			else if (e.getKeyCode() == KeyEvent.VK_R||e.getKeyCode() == KeyEvent.VK_F2)
				out.println("restart");
			else if(e.getKeyCode() == KeyEvent.VK_SPACE)
				out.println("pause");
			return;
		}
		if (s==1) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT&&riktning!="h�ger"&&riktning!="v�nster"){
				riktning="v�nster";
				s=0;
			}
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT&&riktning!="h�ger"&&riktning!="v�nster"){
				riktning="h�ger";
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
		if (a == 1&&spell�ge==TWO) {
			if (e.getKeyCode() ==  KeyEvent.VK_A&&riktningz != "h�ger"&&riktningz!="v�nster") {
				riktningz = "v�nster";
				a = 0;
			}
			else if (e.getKeyCode() == KeyEvent.VK_D&&riktningz != "v�nster"&&riktningz!="h�ger") {
				riktningz = "h�ger";
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
		if(e.getKeyCode() == KeyEvent.VK_F2||e.getKeyCode() == KeyEvent.VK_R){
			Restart();
		}
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			Pausa();
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
class Pongspel extends JPanel implements ActionListener,KeyListener,WindowListener,MouseMotionListener{

	private int x,y,V�nsterX=0,V�nsterY,H�gerX,H�gerY,RektBredd=10,RektH�jd=100,
			bredd=20,h�jd=30,hastighet,c, d,Po�ngV�nster=0,Po�ngH�ger=0,py=10,px=10;
	private JFrame frame = new JFrame("Spel");
	private Timer timer = new Timer(10, this);
	private Boolean GameOver=false,hupp=false,hner=false,vupp=false,vner=false;
	private String Po�ngTill,SpelareV�nster,SpelareH�ger;

	public Pongspel() {
		SpelareV�nster = showInputDialog("Spelare till v�nster:");
		if (SpelareV�nster == null) {
			return;
		}
		else if (SpelareV�nster.equals("")) {
			SpelareV�nster = "Spelare 1";
		}
		SpelareH�ger = showInputDialog("Spelare till h�ger:");
		if (SpelareH�ger == null) {
			return;
		}
		else if (SpelareH�ger.equals("")) {
			SpelareH�ger = "Spelare 2";
		}


		addMouseMotionListener(this);
		setForeground(red);
		setPreferredSize(new Dimension(700, 500));
		setOpaque(true);	
		setBackground(black.brighter());

		frame.addMouseMotionListener(this);
		frame.setIconImage(f�nsterIcon);
		frame.addWindowListener(this);
		frame.addKeyListener(this);
		frame.add(this);
		frame.pack();
		frame.repaint();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		H�gerY = getHeight()/2;
		V�nsterY = getHeight()/2;
		H�gerX=getWidth()-bredd-1;
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
	private void GameOver(String Po�ngTillV�nsterEllerH�ger) {
		timer.stop();
		System.out.println("Game over!");
		getDefaultToolkit().beep();

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
			if (hupp && H�gerY>0) {
				H�gerY=H�gerY-5;
			}
			if (hner && H�gerY+RektH�jd<getHeight()){
				H�gerY=H�gerY+5;
			}
			if (vupp && V�nsterY>0){
				V�nsterY=V�nsterY-5;
			}
			if (vner && V�nsterY+RektH�jd<getHeight()){
				V�nsterY=V�nsterY+5;
			}
			frame.repaint();
			if (x+bredd>=H�gerX) {
				if (y>=H�gerY&&y<=H�gerY+RektH�jd) {
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
		if (GameOver) {
			g2.setFont(new Font("", Font.BOLD, 50));
			g2.setColor(green);
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

			g2.setColor(green);
			g2.setFont(new Font("", Font.BOLD, 50));
			g2.drawString(Integer.toString(Po�ngV�nster) + " - " + Integer.toString(Po�ngH�ger), getWidth()/2-80, 40);

			g2.drawString(SpelareV�nster, 0, 40);
			g2.drawString(SpelareH�ger, getWidth()-250, 40);

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

@SuppressWarnings("serial")
class Kurve implements ActionListener,KeyListener{
	private Pixel[] pixels = new Pixel[100000];
	private int l�ngd,i,po�ng;
	private final int PIXEL = 5;
	private double x,y,riktning;
	private boolean h�ger,v�nster;
	private Timer timer = new Timer(10, this);
	boolean ritaom=true;
	Kurve() {
		frame.add(label);
		frame.addKeyListener(this);
		frame.addWindowListener(autoListener);
		frame.setResizable(false);
		frame.setSize((int)(SK�RM_SIZE.width*0.60),(int)(SK�RM_SIZE.height*0.60));
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		label.setOpaque(true);
		Restart();
	}
	private GoJbFrame  frame = new GoJbFrame("Kurve",false,JFrame.DISPOSE_ON_CLOSE){
		public void validate() {
			ritaom=true;
			super.validate();
			ritaom=true;
		}
		public void repaint() {
			ritaom=true;
			super.repaint();
			ritaom=true;
		};
	};
	private JLabel label = new JLabel(){
		protected void paintComponent(Graphics g) {
			Graphics2D g2=(Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			if (ritaom){
				g2.setColor(BLACK);
				g2.fillRect(0, 0, getWidth(), getHeight());
				ritaom=false;
				for (int i = 1; i < l�ngd; i++) {
					g2.setColor(red);
					pixels[i].draw(g2);
				}
				g2.setColor(GREEN);
				g2.drawString(po�ng+"", 50, getWidth()/2);
			}
			else {
				g2.setColor(red);
				try {
					pixels[l�ngd].draw(g2);
				} catch (Exception e) {}
			}
			if (!timer.isRunning()) {
				g2.setColor(MAGENTA);
				g2.setFont(typsnitt);
				g2.drawString("Game over!", label.getWidth()/2-g2.getFontMetrics().stringWidth("Game over!")/2, label.getHeight()/2);
			}

		}
	};
	@Override
	public void actionPerformed(ActionEvent e) {
		if (h�ger) riktning +=0.04;
		if (v�nster) riktning -=0.04;
		x += Math.cos(riktning);
		y += Math.sin(riktning);
		if (i++<200) {
			pixels[++l�ngd]=new Pixel(x,y);
			for (int i = 1; i < l�ngd-10; i++) {
				if (pixels[l�ngd].touch(pixels[i])) {
					GameOver();
					break;
				}
			}
			label.repaint();
		}
		else if (i>220) {
			i=0;
			ritaom=true;
			po�ng++;
		}
	}
	private void GameOver(){
		timer.stop();
		frame.repaint();
	}
	private void Restart() {
		po�ng=0;
		l�ngd=0;
		x=20;
		y=20;
		riktning=0;
		i=0;
		ritaom=true;
		frame.repaint();
		timer.start();
	}
	@Override 
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_LEFT) {
			v�nster=true;
		}
		else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
			h�ger=true;
		}
		else if (e.getKeyCode()==KeyEvent.VK_R) {
			Restart();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_LEFT) {
			v�nster=false;
		}
		else if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
			h�ger=false;
		}
	}
	public class Pixel{
		private double x,y;
		Pixel(double x,double y) {
			this.x=x;
			this.y=y;
		}
		void draw(Graphics2D g){
			g.fillOval((int)Math.round(x), (int)Math.round(y), PIXEL,PIXEL);
		}
		boolean touch(Pixel pixel){
			return Math.sqrt(Math.pow(x-pixel.x,2)+Math.pow(y-pixel.y,2))<PIXEL;
		}
	}
}

@SuppressWarnings("serial")
class Maze extends JPanel implements ActionListener, MouseMotionListener{

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
		startframe.setIconImage(f�nsterIcon);

		b�rja.addActionListener(this);

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
		level1.setIconImage(f�nsterIcon);

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
			level2.setIconImage(f�nsterIcon);


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
			System.out.println("Musen r�r sig p�: " + x  + ", " + y);
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
			level3.setIconImage(f�nsterIcon);

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
			System.out.println("Musen r�r sig p�: " + x  + ", " + y);
			level3.repaint();

			if (x > 290 && y < 235 && y > 30||y < 235 && y > 150 && x > 82||
					x > 395|| x > 364 && y > 30|| y < 310 && y > 291 && x > 173||y > 246 && 
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
			frame.add(new JLabel(Bild("/images/Bild.jpg")));
			frame.pack();
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(3);
			frame.setIconImage(f�nsterIcon);

			spelaLjud("/images/Ljud.wav");

		}
	}
}

class Memory implements MouseInputListener{

	GoJbFrame frame = new GoJbFrame();

	JLabel label1 = new JLabel(),
			label2 = new JLabel(),
			label3 = new JLabel(),
			label4 = new JLabel(),
			label5 = new JLabel(),
			label6 = new JLabel(),
			label7 = new JLabel(),
			label8 = new JLabel(),
			label9 = new JLabel();


	public Memory(){

		frame.addMouseListener(this);

		frame.setLayout(new GridLayout(3,3));	

		label1.addMouseListener(this);
		label2.addMouseListener(this);
		label3.addMouseListener(this);
		label4.addMouseListener(this);
		label5.addMouseListener(this);
		label6.addMouseListener(this);
		label7.addMouseListener(this);
		label8.addMouseListener(this);
		label9.addMouseListener(this);

		frame.add(label1);
		frame.add(label2);
		frame.add(label3);
		frame.add(label4);
		frame.add(label5);
		frame.add(label6);
		frame.add(label7);
		frame.add(label8);
		frame.add(label9);

		label1.setBackground(blue);
		label1.setOpaque(true);

		label2.setBackground(red);
		label2.setOpaque(true);

		label3.setBackground(green);
		label3.setOpaque(true);

		label4.setBackground(cyan);
		label4.setOpaque(true);

		label5.setBackground(black);
		label5.setOpaque(true);

		label6.setBackground(white);
		label6.setOpaque(true);

		label7.setBackground(orange);
		label7.setOpaque(true);

		label8.setBackground(magenta);
		label8.setOpaque(true);

		label9.setBackground(yellow);
		label9.setOpaque(true);


	}


	@Override
	public void mouseClicked(MouseEvent e) {

		if(e.getSource()==label1){
			System.err.println("1");
		}
		if(e.getSource()==label2){
			System.err.println("2");
		}
		if(e.getSource()==label3){
			System.err.println("3");
		}
		if(e.getSource()==label4){
			System.err.println("4");
		}
		if(e.getSource()==label5){
			System.err.println("5");
		}
		if(e.getSource()==label6){
			System.err.println("6");
		}
		if(e.getSource()==label7){
			System.err.println("7");
		}
		if(e.getSource()==label8){
			System.err.println("8");
		}
		if(e.getSource()==label9){
			System.err.println("9");
		}

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
		// FIXME Auto-generated method stub

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
		frame.setIconImage(f�nsterIcon);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

class TicTacToe implements MouseInputListener, KeyListener, ActionListener{

	Timer timer = new Timer(50, this);

	JFrame frame = new JFrame("Tic Tac Toe"),
			Vinst = new JFrame(),
			tur = new JFrame();

	String str�ng = new String();

	JLabel[] label = new JLabel[10];
	JLabel vinstlabel = new JLabel(),
			turLabel = new JLabel();

	ImageIcon 	o = Bild("/images/O.png"),
			x = Bild("/images/X.png");

	int a,�;

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
				turLabel.setText("Tryck r f�r restart");	
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

	public void keyReleased(KeyEvent e) {}
}