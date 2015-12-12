package spel;

import static gojb.GoJbGoodies.SKÄRM_SIZE;
import static gojb.GoJbGoodies.autoListener;
import static gojb.GoJbGoodies.fönsterIcon;
import static gojb.GoJbGoodies.prop;
import static gojb.GoJbGoodies.random;
import static gojb.GoJbGoodies.sparaProp;
import static gojb.GoJbGoodies.typsnitt;
import static java.awt.Color.GREEN;
import static java.awt.Color.black;
import static java.awt.Color.blue;
import static java.awt.Color.cyan;
import static java.awt.Color.red;
import static java.awt.Color.white;
import static java.awt.Toolkit.getDefaultToolkit;
import static javax.swing.JOptionPane.showInputDialog;
import static spel.Snake.Spelläge.CLIENT;
import static spel.Snake.Spelläge.ONE;
import static spel.Snake.Spelläge.TWO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import gojb.GoJbGoodies;
import gojb.Jakobs;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

@SuppressWarnings("serial")
public class Snake extends JPanel implements KeyListener, ActionListener, ComponentListener{
	enum Spelläge {ONE,TWO,CLIENT}
	Spelläge spelläge;
	private JFrame frame = new JFrame("Snake"), start = new JFrame("Start"),highFrame=new JFrame("Highscore");
	private final int startlängd=3, pixelstorlek=Math.round(SKÄRM_SIZE.width/140), MAXIMUM = 101;
	private int[] x=new int[MAXIMUM], y=new int[MAXIMUM], z=new int[MAXIMUM], q=new int[MAXIMUM];
	private int snakelängdx,snakelängdz=-1,pluppX=-1,pluppY=-1,s=1,a=1,svartPoäng,cyanPoäng,yLoc;
	private Timer timer = new Timer(100, this);
	private String riktning = "ner",riktningz = "upp",vem;
	private boolean paused,gameover=true;
	private JButton local = new JButton("Play two on this computer"),
			online = new JButton("Play online"),
			one = new JButton("Single Player");
	private String[] highscore = new String[6];
	private static boolean rep;
	ArrayList<Pixel> pixels= new ArrayList<>();
	WebSocketClient cc;

	public Snake(){
		setOpaque(true);
		setBackground(white);
		setPreferredSize(new Dimension(pixelstorlek*50+1, pixelstorlek*50+1));
		setMaximumSize(new Dimension(pixelstorlek*50+1, pixelstorlek*50+1));
		setMinimumSize(new Dimension(pixelstorlek*50+1, pixelstorlek*50+1));
		frame.setLayout(new BorderLayout());
		frame.add(this,BorderLayout.CENTER);		
		frame.setIconImage(fönsterIcon);
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
		start.setIconImage(fönsterIcon);
		start.setVisible(true);

		local.addActionListener(e -> {
			spelläge=TWO;
			start.dispose();
			frame.setVisible(true);
			Restart();
			Pausa();
			frame.revalidate();
			frame.repaint();
		});
		one.addActionListener(e -> {
			spelläge=ONE;
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


		start.dispose();
		spelläge=CLIENT;
		gameover=false;
		//		WebSocketImpl.DEBUG=true;
		try {
			cc = new WebSocketClient( new URI("ws://wildfly-gojb.rhcloud.com:8000/snake")) {
				@Override
				public void onMessage( String message ) {
					Scanner scanner = new Scanner(message);
					String type = scanner.next();
					if (type.equals("CLEAR")) {
						pixels.clear();
					}
					else if (type.equals("A")) {
						gameover=false;
						paused=false;
						try {
							String string = scanner.next();
							if (string.equals("PAUS")) {
								paused=true;
							}
							else if (string.equals("GAMEOVER")) {
								vem=scanner.next();
								gameover = true;
							}
						} catch (Exception e) {

						}
					}
					else if (type.equals("P")) {
						pluppX=scanner.nextInt();
						pluppY=scanner.nextInt();
					}
					else if (type.equals("B")) {
						Color color = new Color(scanner.nextInt());
						while (scanner.hasNext()) {
							pixels.add(new Pixel(scanner.nextInt(), scanner.nextInt(), color));
						}
					}
					scanner.close();
					if (rep) {
						rep=false;
						repaint();

					}

				}

				@Override
				public void onOpen( ServerHandshake handshake ) {
					System.out.println("OPEN");
					String namn=showInputDialog("Vad heter du?");
					if (namn==null||namn.equals("")) {
						namn="Okänd";
					}
					cc.send("INIT "+new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB()+" "+namn);
				}

				@Override
				public void onClose( int code, String reason, boolean remote ) {
					start.setVisible(true);
					frame.dispose();
				}
				@Override
				public void onError( Exception ex ) {
					ex.printStackTrace();
				}
			};
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		System.out.println("Ansluter");
		cc.connect();
		frame.setVisible(true);
		frame.pack();
		paused=false;
		//		new Thread(){
		//			public void run() {
		//				Scanner scanner = null;
		//				while (true) {
		//					try {
		//						scanner = new Scanner(in.readLine());
		//						String string = scanner.next();
		//						if (string.equals("B")) {
		//							snakelängdx=0;
		//						}
		//						else if (string.equals("C")) {
		//							snakelängdz=0;
		//						}
		//						else if (string.equals("A")) {
		//							paused=false;
		//							gameover=false;
		//						}
		//						while (scanner.hasNext()) {
		//							if (string.equals("A")) {
		//								String string2 = scanner.next();
		//								if (string2.equals("paus")) {
		//									paused=true;
		//								}
		//								else if (string2.equals("gameover")) {
		//									gameover=true;
		//								}
		//								else {
		//									vem = string2;
		//								}
		//							}
		//							else if (string.equals("B")) {
		//								x[++snakelängdx]=Integer.parseInt(scanner.next())*pixelstorlek;
		//								y[snakelängdx]=Integer.parseInt(scanner.next())*pixelstorlek;
		//							}
		//							else if (string.equals("C")) {
		//								z[++snakelängdz]=Integer.parseInt(scanner.next())*pixelstorlek;
		//								q[snakelängdz]=Integer.parseInt(scanner.next())*pixelstorlek;
		//							}
		//							else if (string.equals("P")) {
		//								pluppX=Integer.parseInt(scanner.next())*pixelstorlek;
		//								pluppY=Integer.parseInt(scanner.next())*pixelstorlek;
		//							}
		//						}
		//						synchronized (frame) {
		//							frame.repaint();
		//						}						
		//					}
		//					catch (Exception e) {
		//						System.err.println("Frånkopplad");
		//						break;
		//					}
		//				}
		//			}}.start();

	}
	private void GameOver(Boolean svart){
		timer.stop();
		frame.repaint();
		frame.revalidate();

		gameover = true;

		((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
		if (spelläge==ONE) {
			vem="Du";
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
			Jakobs.skrivHändelsetext(highscore[1]);
			Jakobs.skrivHändelsetext(highscore[2]);
			Jakobs.skrivHändelsetext(highscore[3]);
			Jakobs.skrivHändelsetext(highscore[4]);
			Jakobs.skrivHändelsetext(highscore[5]);
		}
		else {
			if (svart) {
				cyanPoäng++;
				System.err.println("Poäng till Cyan. (C) "+cyanPoäng+" - "+svartPoäng+" (S)");
				vem="Svart";
			}
			else{
				svartPoäng++;
				System.err.println("Poäng till Svart. (C) "+cyanPoäng+" - "+svartPoäng+" (S)");
				vem="Cyan";
			}
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
				String [] arr = {"upp", "ner", "höger", "vänster"};

				riktning=arr[random.nextInt(arr.length)];
				snakelängdx = startlängd;
				x[1]=posx;
				y[1]=posy;
				s=1;

				if (spelläge!=ONE) {
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


		if (spelläge!=CLIENT) {
			g.setColor(red);
			g.drawOval(pluppX+1, pluppY+1, pixelstorlek-2, pixelstorlek-2);
			g.fillOval(pluppX+1, pluppY+1, pixelstorlek-2, pixelstorlek-2);
			g.setColor(black);
			for (int i = 1; i <= snakelängdx; i++) {
				g.drawRect(x[i]+1, y[i]+1, pixelstorlek-2, pixelstorlek-2);
				g.fillRect(x[i]+1, y[i]+1, pixelstorlek-2, pixelstorlek-2);
			}
			s=1;
			if (spelläge!=ONE) {
				g.setColor(cyan);
				for (int i = 1; i <= snakelängdz; i++) {
					g.drawRect(z[i] + 1, q[i] + 1, pixelstorlek - 2,pixelstorlek - 2);
					g.fillRect(z[i] + 1, q[i] + 1, pixelstorlek - 2,pixelstorlek - 2);
				}
				a = 1;
			}
		}
		else {
			//Client
			g.setColor(red);
			g.drawOval(pluppX*pixelstorlek+1, pluppY*pixelstorlek+1, pixelstorlek-2, pixelstorlek-2);
			g.fillOval(pluppX*pixelstorlek+1, pluppY*pixelstorlek+1, pixelstorlek-2, pixelstorlek-2);
			g.setColor(black);
			for (Pixel pixel :new ArrayList<>(pixels)) {
				g.setColor(pixel.color);
				g.drawRect(pixel.x*pixelstorlek+1, pixel.y*pixelstorlek+1, pixelstorlek-2, pixelstorlek-2);
				g.fillRect(pixel.x*pixelstorlek+1, pixel.y*pixelstorlek+1, pixelstorlek-2, pixelstorlek-2);
			}
			rep=true;
		}
		if(paused){
			g.setColor(blue);
			g.setFont(new Font(null, 0, 25));
			g.drawString("Spelet pausat. Tryck på mellanslag för att fortsätta.", 10, getHeight()/2);
		}
		if (gameover) {
			g.setColor(red);
			g.setFont(new Font(null, 0, 25));
			g.drawString(vem+" förlorade!",10 , getHeight()/2-25);
			g.drawString("Tryck F2 eller \"R\" för \natt spela igen",10 , getHeight()/2);
		}
		//		if (spelläge==SERVER) {
		//			try {
		//				out.print("A ");
		//				if (paused) {
		//					out.print("paus ");
		//				}
		//				if (gameover) {
		//					out.print("gameover " + vem);
		//				}
		//				out.println();
		//				out.print("B ");
		//				for (int i = 1; i <= snakelängdx; i++) {
		//					out.print(x[i] / pixelstorlek + " " + y[i] / pixelstorlek + " ");
		//				}
		//				out.println();
		//				out.print("C ");
		//				for (int i = 1; i <= snakelängdz; i++) {
		//					out.print(z[i] / pixelstorlek + " " + q[i] / pixelstorlek + " ");
		//				}
		//				out.println();
		//				out.println("P " + pluppX / pixelstorlek + " " + pluppY / pixelstorlek);
		//			} catch (Exception e) {
		//				e.printStackTrace();
		//			}
		//		}
		else if (spelläge==ONE) {
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
		if (x[1]==pluppX&&y[1]==pluppY) {
			PlaceraPlupp();
			((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.asterisk")).run();
			System.err.println(++snakelängdx);
		}
		if (spelläge!=ONE) {
			if (z[1] == pluppX && q[1] == pluppY) {
				PlaceraPlupp();
				((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.asterisk")).run();
				System.err.println(++snakelängdz);
			}
			for (int i = snakelängdz + 1; i >= 2; i--) {
				z[i] = z[i-1];
				q[i] = q[i-1];
			}
			if (riktningz == "ner") 
				q[1] += pixelstorlek;
			else if (riktningz == "upp") 
				q[1] -= pixelstorlek;
			else if (riktningz == "höger")
				z[1] += pixelstorlek;
			else if (riktningz == "vänster")
				z[1] -= pixelstorlek;
		}
		for (int i = snakelängdx +1 ; i >= 2; i--) {
			x[i]=x[i-1];
			y[i]=y[i-1];
		}
		if (riktning=="ner") 
			y[1]+=pixelstorlek;
		else if (riktning=="upp")
			y[1]-=pixelstorlek;
		else if (riktning=="höger")
			x[1]+=pixelstorlek;
		else if (riktning=="vänster")
			x[1]-=pixelstorlek;

		for (int i = 1; i <= snakelängdx||i<=snakelängdz; i++) {
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
		else if (spelläge!=ONE&&(z[1]<0||z[1]+pixelstorlek>getWidth()||q[1]<0||q[1]+pixelstorlek>getHeight()))
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
		if (spelläge==CLIENT) {
			if(e.getKeyCode() == KeyEvent.VK_LEFT)
				cc.send("R left");
			else if(e.getKeyCode() == KeyEvent.VK_RIGHT)
				cc.send("R right");
			else if(e.getKeyCode() == KeyEvent.VK_UP)
				cc.send("R up");
			else if(e.getKeyCode() == KeyEvent.VK_DOWN)
				cc.send("R down");
			else if (e.getKeyCode() == KeyEvent.VK_R||e.getKeyCode() == KeyEvent.VK_F2){
				cc.send("RES");
				cc.send("START");
				rep=true;
			}
			else if(e.getKeyCode() == KeyEvent.VK_SPACE)
				cc.send("pause");
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
		if (a == 1&&spelläge==TWO) {
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
	class Pixel{
		int x,y;
		Color color;
		public Pixel(int x,int y,Color color) {
			this.x=x;
			this.y=y;
			this.color=color;
		}
	}
	public static void main(String[] args) {
		GoJbGoodies.main("spel.Snake");
	}
}
