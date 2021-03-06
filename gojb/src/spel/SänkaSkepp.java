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

//Saker att l�gga till;
//Kanske g�ra s� att knappen blinkar b�ttre om man startat fr�n GoJbGoodies, och f�r ett chattmeddelande

package spel;

import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

import javax.swing.*;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import GoJbFrame.GoJbFrame;
import gojb.GoJbGoodies;

public class S�nkaSkepp {

	enum b�ttyp {fem,fyra,treEtt,treTv�,tv�};
	enum Riktning {hori,vert}

	b�ttyp b�tsort;

	static ArrayList<Integer> b�tar5 = new ArrayList<>(),b�tar4 = new ArrayList<>(),b�tar3_1 = new ArrayList<>(),b�tar3_2 = new ArrayList<>(),b�tar2 = new ArrayList<>();
	
	static WebSocketClient cc;

	Riktning riktning;

	static JButton chatButton = new JButton("Chat");

	static JTextArea chatArea = new JTextArea(20,50);

	static JTextField chatField = new JTextField();

	static Color tr�ffF�rg = Color.red, missF�rg = Color.blue, instruktioner1F�rg = Color.black, instruktioner2F�rg = Color.black;

	static GoJbFrame frame = new GoJbFrame(false), connectFrame = new GoJbFrame("Connect to...",false,2), chatFrame = new GoJbFrame("Chat",false,1);
	static JLabel[] egnaLabels, annanLabels;

	static JLabel egenLabel = new JLabel(), annanLabel = new JLabel(),egenText = new JLabel("Din ruta"), annanText = new JLabel("Motst�ndares ruta"), spelruta = new JLabel(),textruta = new JLabel();;

	Robot rb;

	static JMenuBar bar = new JMenuBar();

	static JMenuItem refresh = new JMenuItem("Uppdatera");

	static JButton[] connectButtons;

	static boolean bol5 = true, bol4 = true, bol3 = true, bol2 = true, bol1 = true, stayInside, error, minTur, revanch, flash, bol = false;

	static int last1, last2, antalRutor, y, y1, kollaRutor, antalPlacerade;

	static int skjutP�, antalMissade, antalTr�ffade, antalSkott;

	static double b, x, b1, x1;

	static int[] b�tar;

	static Scanner scanner;

	static String namn=null, instruktioner2="", instruktioner1="Placera dina skepp.", annanNamn;

	@SuppressWarnings("serial")
	static JLabel connectLabel = new JLabel(),inst�llningar = new JLabel() {

		protected void paintComponent(java.awt.Graphics g) {
			super.paintComponent(g);
			for (int i = 0; i < 22 * 5; i += 22) {
				g.setColor(new Color(200, 200, 200));
				if(bol5){
					g.fillRect(5 + i, 10, 20, 20);
				}
				if (i < 22 * 4&&bol4) {
					g.setColor(new Color(201, 200, 200));
					g.fillRect(5 + i, 60, 20, 20);
				}

				if (i < 22 * 3) {
					if(bol3){
						g.setColor(new Color(202, 200, 200));
						g.fillRect(5 + i, 110, 20, 20);
					}
					if(bol2){
						g.setColor(new Color(203, 200, 200));
						g.fillRect(5 + i, 160, 20, 20);
					}
				}
				if (i < 22 * 2&&bol1) {
					g.setColor(new Color(204, 200, 200));
					g.fillRect(5 + i, 210, 20, 20);
				}
			}
			g.setFont(new Font("", Font.BOLD, 32));
			g.setColor(instruktioner1F�rg);
			g.drawString(instruktioner1, inst�llningar.getWidth()/4, (inst�llningar.getHeight()/8)*4);
			g.setColor(instruktioner2F�rg);
			g.drawString(instruktioner2, inst�llningar.getWidth()/4, (inst�llningar.getHeight()/8)*5);

		};
	};

	public static void main(String[] args) {
	new S�nkaSkepp();
	}

	public S�nkaSkepp(){

		try {
			cc = new WebSocketClient(new URI("ws://wildfly-gojb.rhcloud.com:8000/skepp")){

				@Override
				public void onClose(int arg0, String arg1, boolean arg2) {
					// FIXME Auto-generated method stub

				}

				@Override
				public void onError(Exception arg0) {
					// FIXME Auto-generated method stub
					arg0.printStackTrace();
				}

				@Override
				public void onMessage(String message) {
					// FIXME Auto-generated method stub

					scanner = new Scanner(message);
					String string=scanner.next();

					if(string.toLowerCase().equals("starta")){
						S�nkaSkeppStart();
					}
					else if(string.toLowerCase().equals("alla")){
						String allaOnline ="";
						for(int i=0;i<message.split("lla online =")[1].split(";")[0].split(",").length;i++){
							allaOnline+=message.split("lla online =")[1].split(";")[0].split(",")[i]+", ";
						}
						JOptionPane.showMessageDialog(null, "Det �r "+ allaOnline.substring(0,allaOnline.length()-2).split(",").length +" till online : \n"+allaOnline.substring(0, allaOnline.length()-2));
						connectFrame.setTitle("Anslut till motst�ndare");
						connectFrame.setJMenuBar(bar);
						connectFrame.add(connectLabel);
						connectLabel.setLayout(new GridLayout(0, 1));

						bar.add(refresh);

						refresh.addActionListener(e->{cc.send("Refresh");});

						connectFrame.setVisible(true);
						connectButtons = new JButton[allaOnline.split(", ").length];
						connectLabel.removeAll();
						for(int i =0;i<allaOnline.split(", ").length;i++){
							connectButtons[i]=new JButton();
							connectLabel.add(connectButtons[i]);
							connectButtons[i].setText(allaOnline.split(", ")[i]);
							connectButtons[i].setName(message.split(";")[1].split(",")[i]);
							connectButtons[i].addActionListener(e->{
								String clicked =((JButton) e.getSource()).getName();
								System.out.println(clicked);
								int klickad = Arrays.asList(connectButtons).indexOf((JButton) e.getSource());
								System.out.println(klickad);

								connectButtons[klickad].setBackground(Color.GRAY);
								connectButtons[klickad].setEnabled(false);
								cc.send("Annan "+clicked);
							});
						}
					}
					else if (string.toLowerCase().equals("refresh")) {
						String allaOnline ="";	
						for(int i=0;i<message.split("refresh =")[1].split(";")[0].split(",").length;i++){
							allaOnline+=message.split("refresh =")[1].split(";")[0].split(",")[i]+", ";
						}
						connectFrame.setVisible(true);
						connectButtons = new JButton[allaOnline.split(", ").length];
						connectLabel.removeAll();
						for(int i =0;i<allaOnline.split(", ").length;i++){
							connectButtons[i]=new JButton();
							connectLabel.add(connectButtons[i]);
							connectButtons[i].setText(allaOnline.split(", ")[i]);
							connectButtons[i].setName(message.split(";")[1].split(",")[i]);
							connectButtons[i].addActionListener(e->{
								String clicked =((JButton) e.getSource()).getName();
								int klickad = Arrays.asList(connectButtons).indexOf((JButton) e.getSource());
								System.out.println(klickad);

								connectButtons[klickad].setBackground(Color.GRAY);
								connectButtons[klickad].setEnabled(false);
								cc.send("Annan "+clicked);
							});
						}
					}
					else if (string.toLowerCase().equals("ingen")) {
						JOptionPane.showMessageDialog(connectFrame, "Det �r ingen annan online");

						connectFrame.setTitle("Anslut till motst�ndare");
						connectFrame.setJMenuBar(bar);
						connectFrame.add(connectLabel);
						connectLabel.setLayout(new GridLayout(0, 1));

						bar.add(refresh);

						refresh.addActionListener(e->{cc.send("Refresh");});
						connectFrame.setVisible(true);
					}
					else if (string.toLowerCase().equals("ihopkopplad")) {
						annanNamn=message.substring(12);
						JOptionPane.showMessageDialog(connectFrame, "Ansluten med " + message.substring(12)+"!");
						S�nkaSkeppStart();

					}
					else if(string.toLowerCase().equals("fr�ga")){
						Object[] options = {"Ja","Nej"};
						int choice=JOptionPane.showOptionDialog(connectFrame, "Du har f�tt en inbjudan till ett spel av \n" + annanNamn+". Accepterar du?",
								"Inbjudan fr�n " + annanNamn	, JOptionPane.YES_NO_CANCEL_OPTION, 
								JOptionPane.DEFAULT_OPTION,
								null,options, options[0]);
						if(choice==0){
							send("svar ja");
						}
						else{
							send("svar nej");
						}
					}
					else if (string.toLowerCase().equals("svar")) {
						if(scanner.next().equals("nej")){
							JOptionPane.showMessageDialog(connectFrame, scanner.next() + " tackade nej");
						}
					}
					else if(string.toLowerCase().equals("klar")){
						instruktioner2="Motst�ndaren �r klar och v�ntar.";
						instruktioner1="Placera dina skepp.";
						inst�llningar.repaint();
					}
					else if(string.toLowerCase().equals("b�daklar")){
						instruktioner1="Dags att b�rja spela.";
						if(scanner.next().toLowerCase().equals("start")){
							instruktioner2="Du b�rjar skjuta";
							minTur=true;
						}
						else {
							instruktioner2="Din motst�ndare b�rjar.";
							minTur=false;
						}
						inst�llningar.repaint();

					}
					else if(string.toLowerCase().equals("skjut")){
						int skottRuta = scanner.nextInt();
						if(b�tar[skottRuta]<50){
							send("skott miss");
							minTur=true;
							egnaLabels[skottRuta].setBackground(missF�rg);
							instruktioner1F�rg=missF�rg;
							instruktioner1="Din motst�ndare bommade!";
							instruktioner2="Din tur";
							antalMissade++;
						}
						else {
							//							minTur=true;
							egnaLabels[skottRuta].setBackground(tr�ffF�rg);
							instruktioner1="Din motst�ndare tr�ffade!";
							if(b�tar5.indexOf(skottRuta)==-1){
								if(b�tar4.indexOf(skottRuta)==-1){
									if(b�tar3_1.indexOf(skottRuta)==-1){
										if(b�tar3_2.indexOf(skottRuta)==-1){
											//b�tar2
											b�tar2.remove(b�tar2.indexOf(skottRuta));
											if(b�tar2.size()==0){
												instruktioner1="Din jagare har sjunkit!";
												send("skott s�nk;2");
											}
											else{
												send("skott tr�ff");
											}
										}
										else{
											b�tar3_2.remove(b�tar3_2.indexOf(skottRuta));
											System.out.println(b�tar3_2.size() + " === Size");
											if(b�tar3_2.size()==0){
												instruktioner1="En av dina u-b�tar har sjunkit!";
												send("skott s�nk;3");
											}
											else{
												send("skott tr�ff");
											}
										}
									}	
									else{
										b�tar3_1.remove(b�tar3_1.indexOf(skottRuta));
										System.out.println(b�tar3_2.size() + " === Size");
										if(b�tar3_1.size()==0){
											instruktioner1="En av dina u-b�tar har sjunkit!";
											send("skott s�nk;3");
										}
										else{
											send("skott tr�ff");
										}
									}								
								}
								else{
									b�tar4.remove(b�tar4.indexOf(skottRuta));
									if(b�tar4.size()==0){
										instruktioner1="Ditt slagskepp har sjunkit!";
										send("skott s�nk;4");
									}
									else{
										send("skott tr�ff");
									}
								}
							}
							else{
								b�tar5.remove(b�tar5.indexOf(skottRuta));
								if(b�tar5.size()==0){
									instruktioner1="Ditt hangarfartyg har sjunkit!";
									send("skott s�nk;5");
								}
								else{
									send("skott tr�ff");
								}
							}
							antalTr�ffade++;
							instruktioner1F�rg=tr�ffF�rg;
							instruktioner2="Din motst�ndare forts�tter";
						}

						if(antalTr�ffade==17){

							instruktioner1F�rg=new Color(239,35,42);
							instruktioner2F�rg=new Color(239,35,42);

							instruktioner1="Du f�rlorade";
							instruktioner2="Din motst�ndare vann p� " + (antalMissade+antalTr�ffade)+" skott";
							minTur=false;

							inst�llningar.repaint();

							send("gameover");

						}
						inst�llningar.repaint();
						GoJbGoodies.v�nta(1000);
						instruktioner1F�rg=Color.black;
					}
					else if(string.toLowerCase().equals("skott")){
						String tr�ffat = scanner.next();
						if(tr�ffat.equals("tr�ff")){
							annanLabels[skjutP�].setBackground(tr�ffF�rg);
							instruktioner1F�rg=tr�ffF�rg;
							instruktioner1="Tr�ff!";	
							instruktioner2="Din tur igen";	
							minTur=true;
						}
						else if(tr�ffat.equals("miss")){ 
							annanLabels[skjutP�].setBackground(missF�rg);
							instruktioner1F�rg=missF�rg;
							instruktioner1="Bom!";		
							instruktioner2="Din motst�ndare siktar...";	
						}
						else {
							//S�nk
							minTur=true;							
							String b�tsort = message.split(";")[1];
							if(b�tsort.equals("5")){
								instruktioner1="Du s�nkte hangarfartyget!";	
							}
							else if (b�tsort.equals("4")) {
								instruktioner1="Du s�nkte slagskeppet!";	
							}
							else if (b�tsort.equals("3")) {
								instruktioner1="Du s�nkte en u-b�t!";	
							}
							else if (b�tsort.equals("2")) {
								instruktioner1="Du s�nkte j�garen!";	
							}
							annanLabels[skjutP�].setBackground(tr�ffF�rg);
							instruktioner1F�rg=tr�ffF�rg;
							instruktioner2="Din tur igen";

						}

						inst�llningar.repaint();
						GoJbGoodies.v�nta(1000);
						instruktioner1F�rg=Color.black;
					}
					else if(string.toLowerCase().equals("gameover")){
						minTur=false;

						instruktioner1F�rg=new Color(235,213,34);
						instruktioner2F�rg=new Color(235,213,34);

						instruktioner1="Du vann!";
						instruktioner2="Det tog " + (antalSkott)+" skott att vinna";

						inst�llningar.repaint();

					}
					else if(string.toLowerCase().equals("chat")){
						chatArea.setText(chatArea.getText()+"\n"+new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime())+
								" "+annanNamn+" : "+message.substring(5));
						if(!chatFrame.isVisible()){
							flash=true;
							flashingButton();
						}
					}
					else if(string.toLowerCase().equals("exit")){
						JOptionPane.showMessageDialog(spelruta, annanNamn + " har l�mnat spelet");
						System.exit(3);
					}
					System.out.println(message + " <-- Message");
				}
				@Override
				public void onOpen(ServerHandshake arg0) {
					// FIXME Auto-generated method stub
					cc.send("Namn;" + namn);
					//						cc.send("STARTA");

				}

			};

		} catch (Exception e) {
			e.printStackTrace();
		}
		boolean namnFunkar=false;
		while(namnFunkar==false){
			namn= JOptionPane.showInputDialog("Enter Username", "Gojb");
			if(namn.equals("")||namn.equals(null)){
				Object[] options = {"Prova igen","Avsluta"};
				int choice=JOptionPane.showOptionDialog(null, "Namnet accepterades ej. Prova igen eller avsluta?",
						"ERROR: USERNAME EQUALS null", JOptionPane.DEFAULT_OPTION, 
						JOptionPane.DEFAULT_OPTION,
						null,options, options[1]);
				if(choice==1){
					System.exit(3);
				}
			}
			else{
				namnFunkar=true;
				cc.connect();
			}
		}

		//		new S�nkaSkeppStart();
	}
	public void S�nkaSkeppStart() {
		frame.setSize((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2, 3*(int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/4);
		frame.setLocationRelativeTo(connectFrame);

		connectFrame.dispose();

		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		frame.setBackground(Color.blue);
		frame.setDefaultCloseOperation(2);
		frame.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				// FIXME Auto-generated method stub
				try {
					cc.send("exit");
				} catch (Exception e2) {
					System.err.println("Server funkar ej!");
				}
				System.exit(3);
			}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
		});

		frame.add(textruta, BorderLayout.NORTH);
		textruta.setOpaque(true);
		frame.add(spelruta, BorderLayout.CENTER);
		spelruta.setOpaque(true);
		frame.add(inst�llningar, BorderLayout.SOUTH);
		inst�llningar.setOpaque(true);

		inst�llningar.setPreferredSize(new Dimension(1000, 250));

		chatFrame.setLocationRelativeTo(null);
		chatFrame.setLayout(new BorderLayout());


		JScrollPane chatScroll = new JScrollPane(chatArea);
		chatScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		chatScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		chatFrame.add(chatScroll, BorderLayout.NORTH);
		chatFrame.setLocationRelativeTo(frame);

		chatArea.setLineWrap(true);
		chatArea.setWrapStyleWord(true);
		chatArea.setColumns(20);
		chatArea.setOpaque(true);
		chatArea.setMargin(new Insets(3,7,5,5));
		chatScroll.setPreferredSize(new Dimension(chatFrame.getWidth(), 17*chatFrame.getHeight()/20));
		chatArea.setEditable(false);
		chatArea.setBackground(null);

		chatFrame.add(chatField, BorderLayout.SOUTH);
		chatField.setPreferredSize(new Dimension(chatFrame.getWidth(), chatFrame.getHeight()/20));
		chatField.setOpaque(true);
		chatField.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {}
			public void keyReleased(KeyEvent e) {}
			public void keyPressed(KeyEvent e) {
				//Enter keycode = 10
				if(e.getKeyCode()==10&&!chatField.getText().equals("")){
					chatArea.setText(chatArea.getText()+"\n"+new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime())+
							" "+namn+" : "+chatField.getText());
					cc.send("chat "+chatField.getText());
					chatField.setText("");
				}
			}
		});

		inst�llningar.add(chatButton);
		chatButton.setSize(200, 40);
		chatButton.setLocation(390,30);
		chatButton.setBackground(new Color(200, 200, 200));
		chatButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				chatFrame.setVisible(true);
				chatField.requestFocus();
				flash=false;
				chatButton.setBackground(new Color(200, 200, 200));
			}
		});

		textruta.setPreferredSize(new Dimension(1000, 50));
		textruta.setLayout(new GridLayout(1, 2));
		textruta.add(egenText);
		textruta.add(annanText);

		egenText.setHorizontalAlignment(SwingConstants.CENTER);
		egenText.setFont(new Font("", Font.BOLD, 27));
		annanText.setHorizontalAlignment(SwingConstants.CENTER);
		annanText.setFont(new Font("", Font.BOLD, 27));

		spelruta.setLayout(new GridLayout(1, 2, 0, 50));
		spelruta.setPreferredSize(new Dimension(1000, 500));
		spelruta.add(egenLabel);
		spelruta.add(annanLabel);
		spelruta.setBackground(new Color(122, 221, 232));

		egnaLabels = new JLabel[100];
		egenLabel.setLayout(new GridLayout(10, 10, 3, 3));

		annanLabels = new JLabel[100];
		annanLabel.setLayout(new GridLayout(10, 10, 3, 3));

		b�tar=new int[100];

		for (int i = 0; i < egnaLabels.length; i++) {

			egnaLabels[i] = new JLabel();
			annanLabels[i] = new JLabel();

			egnaLabels[i].setText(Integer.toString(i));
			egnaLabels[i].setFont(new Font("", 0, 0));

			annanLabels[i].setText(Integer.toString(i));
			annanLabels[i].setFont(new Font("", 0, 0));

			egnaLabels[i].setBackground(new Color(207, 217, 220));
			annanLabels[i].setBackground(new Color(145, 176, 223));

			egnaLabels[i].setOpaque(true);
			annanLabels[i].setOpaque(true);

			egenLabel.add(egnaLabels[i]);
			annanLabel.add(annanLabels[i]);

			// Mouselisteners
			egnaLabels[i].addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {}
				public void mousePressed(MouseEvent e) {

					if(b�tsort!=null){

						int clicked = Integer.parseInt(((JLabel) e.getSource()).getText());

						if (SwingUtilities.isRightMouseButton(e)) {
							if(riktning==Riktning.hori){
								riktning=Riktning.vert;
							}
							else{
								riktning=Riktning.hori;
							}
						}
						else{
							if(riktning==Riktning.hori){
								if(!error){
									for(int i=0;i<antalRutor;i++){
										if(b�tar[clicked+(i*1)]<50){
											kollaRutor++;
										}
									}
									if(kollaRutor==antalRutor){
										for(int i=0;i<antalRutor;i++){
											if(b�tsort==b�ttyp.fem){
												b�tar5.add(clicked+i);
											}
											else if(b�tsort==b�ttyp.fyra){
												b�tar4.add(clicked+i);
											}
											else if(b�tsort==b�ttyp.treEtt){
												b�tar3_1.add(clicked+i);
											}
											else if(b�tsort==b�ttyp.treTv�){
												b�tar3_2.add(clicked+i);
											}
											else if(b�tsort==b�ttyp.tv�){
												b�tar2.add(clicked+i);
											}
											else {
												return;
											}
											b�tar[clicked+(i*1)]=100+antalRutor;		
											System.out.println(Math.round((antalPlacerade*40)));
											int rgbF�rg=(int) Math.round((antalPlacerade*40));
											egnaLabels[clicked+(i*1)].setBackground(new Color(rgbF�rg,rgbF�rg,rgbF�rg));
										}
										b�tsort=null;
										antalPlacerade++;
										antalRutor=0;
										stayInside=false;
									}
									kollaRutor=0;
								}
							}
							else if(riktning==Riktning.vert){

								if(!error){
									for(int i=0;i<antalRutor;i++){
										if(b�tar[clicked+(i*10)]<50){
											kollaRutor++;
										}
									}
									if(kollaRutor==antalRutor){
										for(int i=0;i<antalRutor;i++){		
											if(b�tsort==b�ttyp.fem){
												b�tar5.add(clicked+(i*10));
											}
											else if(b�tsort==b�ttyp.fyra){
												b�tar4.add(clicked+(i*10));
											}
											else if(b�tsort==b�ttyp.treEtt){
												b�tar3_1.add(clicked+(i*10));
											}
											else if(b�tsort==b�ttyp.treTv�){
												b�tar3_2.add(clicked+(i*10));
											}
											else if(b�tsort==b�ttyp.tv�){
												b�tar2.add(clicked+(i*10));
											}
											else{
												return;
											}
											b�tar[clicked+(i*10)]=100+antalRutor;
											System.out.println(Math.round((antalPlacerade*40)));
											int rgbF�rg=(int) Math.round((antalPlacerade*40));
											egnaLabels[clicked+(i*10)].setBackground(new Color(rgbF�rg,rgbF�rg,rgbF�rg));
										}
										b�tsort=null;
										antalPlacerade++;
										antalRutor=0;
										stayInside=false;

									}
									kollaRutor=0;
								}



							}
							if(antalPlacerade==5){
								Klar();
							}
						}
					}
				}

				public void mouseExited(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {}
			});
			egnaLabels[i].addMouseMotionListener(new MouseAdapter() {
				public void mouseMoved(MouseEvent e) {
					int clicked = Integer.parseInt(((JLabel) e.getSource()).getText());

					for(int i = 1;i<antalRutor;i++){
						if(riktning==Riktning.vert){
							try {
								if(egnaLabels[clicked+(10*i)].getBackground()!=Color.black&&b�tar[clicked+(10*i)]<50){
									egnaLabels[clicked+(10*i)].setBackground(Color.black);
								}
								error=false;
							} catch (Exception e2) {
								error=true;
							}
							try {
								for(int i1 = 0;i1<egnaLabels.length;i1++){
									if(egnaLabels[i1].getBackground()==Color.black&&b�tar[i1]<50){
										if(antalRutor==5&&i1!=clicked&&i1!=clicked+(10)&&i1!=clicked+(10*2)&&i1!=clicked+(10*3)&&i1!=clicked+(10*4)){
											egnaLabels[i1].setBackground(new Color(207, 217, 220));
										}
										else if(antalRutor==4&&i1!=clicked&&i1!=clicked+(10)&&i1!=clicked+(10*2)&&i1!=clicked+(10*3)){
											egnaLabels[i1].setBackground(new Color(207, 217, 220));

										}
										else if(antalRutor==3&&i1!=clicked&&i1!=clicked+(10)&&i1!=clicked+(10*2)){
											egnaLabels[i1].setBackground(new Color(207, 217, 220));

										}
										else if(antalRutor==2&&i1!=clicked&&i1!=clicked+(10)){
											egnaLabels[i1].setBackground(new Color(207, 217, 220));

										}
									}
								}

							} catch (Exception e2) {
								// FIXME: handle exception
							}
							if(b�tar[clicked]<50){
								egnaLabels[clicked].setBackground(Color.black);
							}
							last1 = clicked;
						}
						else{
							//Horisontellt

							try {

								b = ((double) (clicked+ 10)) / (double) (10);
								y = ((int) b); // y = radnummer, allts� 1 - 16
								x = ((((double) b) - ((double) y)) * 10) + 1d; // x = kolumnnummer, allts� 1 - 10

								b1 = ((double) (clicked+(1*i)+ 10)) / (double) (10);
								y1 = ((int) b1); // y = radnummer, allts� 1 - 16
								x1 = ((((double) b1) - ((double) y1)) * 10) + 1d; // x = kolumnnummer, allts� 1 - 10


								if(x<x1){
									if(egnaLabels[clicked+i].getBackground()!=Color.black&&b�tar[clicked+i]<50){
										egnaLabels[clicked+(1*i)].setBackground(Color.black);
									}
									error=false;
								}
								else {
									error=true;
								}
							} catch (Exception e2) {
								error=true;
							}
							try {
								for(int i1 = 0;i1<egnaLabels.length;i1++){

									if(egnaLabels[i1].getBackground()==Color.black&&b�tar[i1]<50){
										if(antalRutor==5&&i1!=clicked&&i1!=clicked+(1)&&i1!=clicked+(1*2)&&i1!=clicked+(1*3)&&i1!=clicked+(1*4)){
											egnaLabels[i1].setBackground(new Color(207, 217, 220));
										}
										else if(antalRutor==4&&i1!=clicked&&i1!=clicked+(1)&&i1!=clicked+(1*2)&&i1!=clicked+(1*3)){
											egnaLabels[i1].setBackground(new Color(207, 217, 220));

										}
										else if(antalRutor==3&&i1!=clicked&&i1!=clicked+(1)&&i1!=clicked+(1*2)){
											egnaLabels[i1].setBackground(new Color(207, 217, 220));
										}
										else if(antalRutor==2&&i1!=clicked&&i1!=clicked+(1)){
											egnaLabels[i1].setBackground(new Color(207, 217, 220));

										}
									}
								}

							} catch (Exception e2) {
								// FIXME: handle exception
							}
							if(b�tar[clicked]<50){
								egnaLabels[clicked].setBackground(Color.black);
							}
							last1 = clicked;
						}
					}
				}

				@Override
				public void mouseDragged(MouseEvent e) {
				}
			});

			annanLabels[i].addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {
				}

				public void mousePressed(MouseEvent e) {
					if(minTur){
						skjutP� = Integer.parseInt(((JLabel) e.getSource()).getText());
						if(annanLabels[skjutP�].getBackground()!=tr�ffF�rg&&annanLabels[skjutP�].getBackground()!=missF�rg){
							cc.send("skjut "+skjutP�);
							antalSkott++;
							minTur=false;
						}
					}
				}

				public void mouseExited(MouseEvent e) {
					// FIXME Auto-generated method stub
					if(annanLabels[last2].getBackground()!=missF�rg&&annanLabels[last2].getBackground()!=tr�ffF�rg){
						annanLabels[last2].setBackground(new Color(145, 176, 223));
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// FIXME Auto-generated method stub
					for(int i = 0;i<egnaLabels.length;i++){
						if(b�tar[i]<100&&egnaLabels[i].getBackground()!=tr�ffF�rg&&egnaLabels[i].getBackground()!=missF�rg){
							egnaLabels[i].setBackground((new Color(207, 217, 220)));
						}
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {}
			});
			annanLabels[i].addMouseMotionListener(new MouseAdapter() {
				public void mouseMoved(MouseEvent e) {
					if(minTur){
						int clicked = Integer.parseInt(((JLabel) e.getSource()).getText());
						if(bol5==false&&bol4==false&&bol3==false&&bol2==false&&bol1==false){
							if(annanLabels[last2].getBackground()!=tr�ffF�rg&&annanLabels[last2].getBackground()!=missF�rg){
								annanLabels[last2].setBackground(new Color(145, 176, 223));
							}
							if(annanLabels[clicked].getBackground()!=tr�ffF�rg&&annanLabels[clicked].getBackground()!=missF�rg){
								annanLabels[clicked].setBackground(Color.black);
							}			
							last2 = clicked;
						}
					}
				}

				@Override
				public void mouseDragged(MouseEvent e) {}
			});
			spelruta.addMouseListener(new MouseListener() {
				public void mouseReleased(MouseEvent e) {}
				public void mousePressed(MouseEvent e) {}
				public void mouseExited(MouseEvent e) {}
				public void mouseEntered(MouseEvent e) {}
				public void mouseClicked(MouseEvent e) {}
			});

		}
		frame.revalidate();
		inst�llningar.repaint();
		frame.repaint();

		inst�llningar.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// FIXME Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				try {
					rb = new Robot();
				} catch (AWTException e1) {
					// FIXME Auto-generated catch block
					e1.printStackTrace();
				}
				if(!stayInside){
					Color color = rb.getPixelColor(e.getLocationOnScreen().x, e.getLocationOnScreen().y);
					if (color.getRed() == 238) {
						color = rb.getPixelColor(e.getLocationOnScreen().x + 10, e.getLocationOnScreen().y);
					}
					if (color.getRed() == 200) {
						System.err.println(5);
						bol5=false;
						placeraSkepp(b�ttyp.fem);
					}
					else if (color.getRed() == 201) {
						System.err.println(4);
						bol4=false;
						placeraSkepp(b�ttyp.fyra);
					}
					else if (color.getRed() == 202) {
						System.err.println(3);
						bol3=false;
						placeraSkepp(b�ttyp.treEtt);
					}
					else if (color.getRed() == 203) {
						System.err.println(3);
						bol2=false;
						placeraSkepp(b�ttyp.treTv�);
					}
					else if (color.getRed() == 204) {
						System.err.println(2);
						bol1=false;
						placeraSkepp(b�ttyp.tv�);
					}
					inst�llningar.repaint();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// FIXME Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// FIXME Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {}
		});

	}


	public void placeraSkepp(b�ttyp b�t) {
		stayInside=true;
		riktning=Riktning.hori;
		b�tsort=b�t;
		if(b�t==b�ttyp.fem){
			antalRutor=5;
		}
		else if(b�t==b�ttyp.fyra){
			antalRutor=4;
		}
		else if(b�t==b�ttyp.treEtt){
			antalRutor=3;
		}
		else if(b�t==b�ttyp.treTv�){
			antalRutor=3;
		}
		else if(b�t==b�ttyp.tv�){
			antalRutor=2;
		}


	}
	public void Klar() {
		System.out.println("KLAR!!");
		// WebSocketImpl.DEBUG=true;
		cc.send("klar".toLowerCase());
		instruktioner1="V�ntar p� motst�ndare...";
		inst�llningar.repaint();
	}
	public static int setColor(int x){

		x+=1;
		int o,k;
		o = (((int)-0.5)*((int)((Math.pow(x, 2))))+(7*x))*7;
		k=o-(((int)-0.5)*((int)((Math.pow(1, 2))))+(7*1))*7;
		return k;

	}

	public static void flashingButton(){


		if(flash){
			System.err.println("adsdds");
			Timer timer = new Timer(900, e->{});
			timer.start();
			timer.addActionListener(e->{
				System.err.println("saddssd");
				if(bol){

					chatButton.setBackground(new Color(150,150,150));
					frame.revalidate();
					frame.repaint();
					if(!flash){
						timer.stop();
					}
					bol=false;
				}
				else{
					chatButton.setBackground(new Color(200,200,200));
					frame.revalidate();
					frame.repaint();
					if(!flash){
						timer.stop();
					}
					bol=true;
				}

			});

		}
	}
}
