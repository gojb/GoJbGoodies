/*
 * Copyright 2017 GoJb Development
 *
 * Permission is hereby granted, free of charge, to any
 * person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to
 *  use, copy, modify, merge, publish, distribute, sublicense, and/or
 *  sell copies of the Software, and to permit persons to whom
 *  the Software is furnished to do so, subject to the following
 *  conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF
 * ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
 * SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package spel;

import static gojb.GoJbGoodies.*;
import static java.awt.Color.*;
import static java.awt.Toolkit.getDefaultToolkit;
import static javax.swing.JOptionPane.showInputDialog;
import static spel.Snake.Spelläge.*;

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
	//private JPanel highPanel = new JPanel();
	ArrayList<Pixel> pixels= new ArrayList<>();
	ArrayList<Highscore> highscores = new ArrayList<>();
	WebSocketClient cc;

	public Snake(){
		setOpaque(true);
		setBackground(white);
		setPreferredSize(new Dimension(pixelstorlek*50+1, pixelstorlek*50+1));
		setMaximumSize(new Dimension(pixelstorlek*50+1, pixelstorlek*50+1));
		setMinimumSize(new Dimension(pixelstorlek*50+1, pixelstorlek*50+1));
		//		highPanel.setMinimumSize(new Dimension((pixelstorlek*50+1)/2,(pixelstorlek*50+1)/2));
		//		highPanel.setSize((pixelstorlek*50+1)/2, (pixelstorlek*50+1)/2);
		frame.setLayout(new BorderLayout(1,1));
		frame.add(this,BorderLayout.EAST);
		frame.setIconImage(fönsterIcon);
		frame.setResizable(false);
		frame.pack();
		frame.getContentPane().setBackground(Color.black);
		frame.setLocationRelativeTo(null);
		frame.addKeyListener(this);
		frame.addWindowListener(autoListener);
		frame.getContentPane().setBackground(black);
		frame.addComponentListener(this);
		frame.setDefaultCloseOperation(2);


		;
		frame.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {}public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}public void windowDeactivated(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}public void windowClosed(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				try {
					highFrame.dispose();
					timer.stop();
					cc.close();
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
			highFrame.setSize(frame.getWidth()/2 ,frame.getHeight());
			highFrame.setIconImage(fönsterIcon);
			highFrame.setUndecorated(true);
			highFrame.setLocation(frame.getX()-highFrame.getWidth(),frame.getY());
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
			highFrame.setVisible(true);
			frame.toFront();
		});
		//		online.addActionListener(e -> online());

		frame.revalidate();
		frame.repaint();


	}
	//	void online(){
	//		frame.add(highPanel,BorderLayout.WEST);
	//		start.dispose();
	//		spelläge=CLIENT;
	//		gameover=false;
	//		//				WebSocketImpl.DEBUG=true;
	//		try {
	//			cc = new WebSocketClient( new URI("ws://wildfly-gojb.rhcloud.com:8000/snake")) {
	//				@Override
	//				public void onMessage( String message ) {
	//					JsonObject obj = new Json;
	//					try {
	//						obj = Json.createParser(message);
	//					}
	//					catch (Exception e) {
	//						if (message.equals("START")||message=="OPEN") {
	//							System.out.println(message);
	//						}
	//						else{
	//							System.err.println(message);
	//						}
	//						return;
	//					}
	//					JsonArray datas= obj.getJsonArray("data");
	//					for (int i = 0; i < datas.size(); i++) {
	//						JsonObject data = datas.getJsonObject(i);
	//						String type=data.getString("type");
	//						if(type=="plupp"){
	//							pluppX=data.getInt("X");
	//							pluppY=data.getInt("Y");
	//						}
	//						else if (type=="players") {
	//							pixels.clear();
	//							JsonArray players=data.getJsonArray("players");
	//							for (int int2 = 0; int2 < players.size(); int2++) {
	//								JsonObject player= players.getJsonObject(int2);
	//								JsonArray pixlar= player.getJsonArray("pixels");
	//								Color färg = Color.decode("#"+player.getInt("färg"));
	//								for (int int3 = 0; int3 < pixlar.size(); int3++) {
	//									JsonObject pixel=pixlar.getJsonObject(int3);
	//									pixels.add(new Pixel(pixel.getInt("X"), pixel.getInt("Y"), färg));
	//								}
	//							}
	//						}
	//						else if(type=="highscore"){
	////							$('.highscore').empty();
	////							$('.highscore').append(
	////									'<tr>'+
	////									'<th>Spelare</th>'+
	////									'<th>Poäng</th>'+
	////									'<th>Highscore</th>'+
	////									'</tr>'
	////							);
	////							var highscores=data.highscore;
	////							for (var int3 = 0; int3 < highscores.length; int3++) {
	////								var highscore=highscores[int3];
	//////								var highscore=new Highscore(scanner);
	////								$('.highscore').append(
	////										'<tr style="color:#'+highscore.färg+';">'+
	////										'<td><script type="text/plain">'+highscore.namn+'</script></td>'+
	////										'<td>'+highscore.poäng+'</td>'+
	////										'<td>'+highscore.highscore+'</td>'+
	////										'</div>'
	////								);
	////							}
	//						}
	//						else if(type=="gameover"){
	//							System.out.println(data);
	//							vem=data.getString("namn");
	//							gameover = true;
	//
	//						}
	//						else if(type=="delay"){
	//							System.out.println(data.getString("delay"));
	//						}
	//						else if(type=="cleangameover"){
	//							gameover=false;
	//						}
	//						else if(type=="pause"){
	//							paused=true;
	//						}
	//						else if(type=="unpause"){
	//							paused=true;
	//						}
	//					System.err.println(message);
	////					Scanner scanner = new Scanner(message);
	////					String type = scanner.next();
	////
	////					if (type.equals("A")) {
	////						gameover=false;
	////						paused=false;
	////
	////						String string = scanner.next();
	////						if (string.equals("PAUSE")) {
	////							paused=true;
	////						}
	////						else if (string.equals("GAMEOVER")) {
	//							scanner.useDelimiter("\\z");
	////							vem=scanner.next();
	////							gameover = true;
	////						}
	////						frame.repaint();
	////					}
	////					else if (type.equals("P")) {
	////						pluppX=scanner.nextInt();
	////						pluppY=scanner.nextInt();
	////					}
	////					else if (type.equals("B")) {
	////						pixels.clear();
	////						scanner.useDelimiter("\\z");
	////						String string=scanner.next();
	////						String[] strings = string.split(";");
	////						for (int i = 0; i < strings.length; i++) {
	////							Scanner scanner2 = new Scanner(strings[i]);
	////							Color color = Color.decode("#"+scanner2.next());
	////							while (scanner2.hasNext()) {
	////								pixels.add(new Pixel(scanner2.nextInt(), scanner2.nextInt(), color));
	////							}
	////							scanner2.close();
	////						}
	////						repaint();
	////					}
	////					else if (type.equals("H")) {
	////						highscores.clear();
	////
	////						scanner.useDelimiter("\\z");
	////						String string=scanner.next();
	////						String[] strings = string.split(";");
	////						for (int i = 0; i < strings.length; i++) {
	////							Scanner scanner2 = new Scanner(strings[i]);
	////							highscores.add(new Highscore(scanner2,strings[++i]));
	////							scanner2.close();
	////						}
	////						JPanel panel = new JPanel();
	////
	////						Font font = new Font("", 0, 20);
	////
	////						panel.setBackground(Color.gray);
	////						panel.setOpaque(true);
	////						JLabel labe = new JLabel("Spelare",SwingConstants.CENTER);
	////						labe.setBackground(Color.white);
	////						labe.setOpaque(true);
	////						labe.setFont(font);
	////						panel.add(labe);
	////
	////						JLabel labe2 = new JLabel("Poäng",SwingConstants.CENTER);
	////						labe2.setBackground(Color.white);
	////						labe2.setOpaque(true);
	////						labe2.setFont(font);
	////						panel.add(labe2);
	////
	////						JLabel labe3 = new JLabel("High score",SwingConstants.CENTER);
	////						labe3.setBackground(Color.white);
	////						labe3.setOpaque(true);
	////						labe3.setFont(font);
	////						panel.add(labe3);
	////
	////						panel.setLayout(new GridLayout(0, 3, 1, 1));
	////
	////						highscores.sort(new Comparator<Highscore>() {
	////							public int compare(Highscore o1, Highscore o2) {
	////								return o2.highscore-o1.highscore;
	////							};
	////
	////						});
	////						for (Highscore highscore : highscores) {
	////
	////							JLabel label = new JLabel(highscore.namn,SwingConstants.CENTER);
	////							label.setBackground(Color.white);
	////							label.setForeground(highscore.color);
	////							label.setOpaque(true);
	////							label.setFont(font);
	////							panel.add(label);
	////
	////							JLabel label2 = new JLabel(Integer.toString(highscore.p),SwingConstants.CENTER);
	////							label2.setBackground(Color.white);
	////							label2.setForeground(highscore.color);
	////							label2.setOpaque(true);
	////							label2.setFont(font);
	////							panel.add(label2);
	////
	////							JLabel label3 = new JLabel(Integer.toString(highscore.highscore),SwingConstants.CENTER);
	////							label3.setBackground(Color.white);
	////							label3.setForeground(highscore.color);
	////							label3.setOpaque(true);
	////							label3.setFont(font);
	////							panel.add(label3);
	////						}
	////						//							while (panel.getComponents().length<20) {
	////						//								panel.add(Box.createGlue());
	////						//							}
	////						highPanel.removeAll();
	////						highPanel.add(panel);
	////						highPanel.revalidate();
	////						frame.pack();
	////
	////
	//					}
	////					scanner.close();
	//				}
	//
	//				@Override
	//				public void onOpen( ServerHandshake handshake ) {
	//					System.out.println("Öppnar");
	//					String namn=showInputDialog("Vad heter du?");
	//					if (namn==null||namn.equals("")) {
	//						namn="Okänd";
	//					}
	//
	//					cc.send("INIT "+Integer.toHexString(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)).getRGB()).substring(2)+" "+namn);
	//				}
	//
	//				@Override
	//				public void onClose( int code, String reason, boolean remote ) {
	//					start.setVisible(true);
	//					frame.dispose();
	//				}
	//				@Override
	//				public void onError( Exception ex ) {
	//					ex.printStackTrace();
	//				}
	//			};
	//		} catch (URISyntaxException e) {
	//			e.printStackTrace();
	//		}
	//		System.out.println("Ansluter");
	//		cc.connect();
	//		frame.setVisible(true);
	//		frame.pack();
	//		paused=false;
	//
	//	}
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
			g.setColor(Color.black);
			g.drawLine(0, 0, 0, getHeight());
			g.drawLine(0, 0, getWidth(), 0);
			g.drawLine(0,getHeight(),getWidth(), getHeight());
			g.drawLine(getWidth(),0,getWidth(), getHeight());
			//Client
			g.setColor(red);
			g.drawOval(pluppX*pixelstorlek+1, pluppY*pixelstorlek+1, pixelstorlek-2, pixelstorlek-2);
			g.fillOval(pluppX*pixelstorlek+1, pluppY*pixelstorlek+1, pixelstorlek-2, pixelstorlek-2);

			for (Pixel pixel :new ArrayList<>(pixels)) {
				g.setColor(pixel.color);
				g.drawRect(pixel.x*pixelstorlek+1, pixel.y*pixelstorlek+1, pixelstorlek-2, pixelstorlek-2);
				g.fillRect(pixel.x*pixelstorlek+1, pixel.y*pixelstorlek+1, pixelstorlek-2, pixelstorlek-2);
			}
		}
		if(paused){
			g.setColor(blue);
			g.setFont(new Font(null, 0, 25));
			g.drawString("Spelet pausat. Tryck på mellanslag för att fortsätta.", 10, getHeight()/2);
		}
		if (gameover) {
			g.setColor(red);
			g.setFont(new Font(null, 0, 25));
			g.drawString(vem+" förlorade!",25 , getHeight()/2-25);
		}
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
			//((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.asterisk")).run();
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
	public void componentShown(ComponentEvent e) {
		highFrame.toFront();
		frame.toFront();
	}
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
			}
			else if(e.getKeyCode() == KeyEvent.VK_SPACE)
				cc.send("PAUSE");
			else if (e.getKeyCode() == KeyEvent.VK_CONTROL&&e.getKeyCode() == KeyEvent.VK_A) {
				cc.send("AUTO");
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
				g2.drawString(highscore[i],10, pos);

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
	class Highscore{
		Color color;
		int p;
		int highscore;
		String namn;
		public Highscore(Scanner scanner, String namn){
			p=scanner.nextInt();
			color=Color.decode("#"+scanner.next());
			highscore=scanner.nextInt();
			this.namn=namn;
		}
	}

	public static void main(String[] args) {
		GoJbGoodies.main("spel.Snake");
	}
}
