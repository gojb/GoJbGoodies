package spel;

import java.awt.*;
import java.awt.event.*;
import java.net.URI;
import java.util.Scanner;

import javax.swing.*;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import GoJbFrame.GoJbFrame;

public class SänkaSkepp {

	enum båttyp {fem,fyra,treEtt,treTvå,två};
	enum Riktning {hori,vert}

	static WebSocketClient cc;

	Riktning riktning;

	static GoJbFrame frame = new GoJbFrame(false), connectFrame = new GoJbFrame("Connect to...",false,2);
	JLabel[] egnaLabels, annanLabels;
	JLabel egenLabel = new JLabel(), annanLabel = new JLabel(), egenText = new JLabel("Din ruta"),
			annanText = new JLabel("Motståndares ruta"), spelruta = new JLabel(), textruta = new JLabel(),
			inställningar = new JLabel() {
				

		/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

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

		};
	};

	Robot rb;

	static JMenuBar bar = new JMenuBar();
	
	static JMenuItem refresh = new JMenuItem("Uppdatera");

	static JButton[] connectButtons;

	boolean bol5 = true, bol4 = true, bol3 = true, bol2 = true, bol1 = true, stayInside, error;

	int last1, last2, antalRutor, y, y1, kollaRutor, antalPlacerade;

	double b, x, b1, x1;

	int[] båtar;

	static Scanner scanner;

	static String namn=null;

	public static void main(String[] args) {
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

					message=message.toLowerCase();

					connectFrame.setLayout(new GridLayout(0, 1));
					connectFrame.setTitle("Anslut till motståndare");
					connectFrame.setJMenuBar(bar);
					
					bar.add(refresh);
					
					refresh.addActionListener(e->{cc.send("Namn ettnammsomaldrigskrivs");});
					
					scanner = new Scanner(message);
					String string=scanner.next();

					if(string.equals("starta")){
						new SänkaSkepp();
					}
					else if(string.equals("alla")){
						String allaOnline ="";
						for(int i=0;i<message.split("lla online =")[1].split(";")[0].split(",").length;i++){
							allaOnline+=message.split("lla online =")[1].split(";")[0].split(",")[i]+", ";
						}
						if(!message.split("lla online =")[1].split(";")[1].equals("ettnammsomaldrigskrivs")){
						JOptionPane.showMessageDialog(null, "Det är "+ allaOnline.split(",").length +" till online : "+allaOnline.substring(0, allaOnline.length()-2));
						}
						try {
							for (int i = 0; i < connectButtons.length; i++) {
								connectButtons[i] = new JButton("");
								connectFrame.remove(connectButtons[i]);
							}
						} catch (Exception e) {
							System.err.println("sadassd");
							System.out.println(e);
						}
//						allaOnline.split(", ").length
						connectFrame.setVisible(true);
						connectButtons = new JButton[allaOnline.split(", ").length];
						for(int i =0;i<allaOnline.split(", ").length;i++){
							connectButtons[i]=new JButton();
							connectFrame.add(connectButtons[i]);
							connectButtons[i].setText(allaOnline.split(", ")[i]);
							connectButtons[i].setName(message.split(";")[1].split(",")[i]);
							connectButtons[i].addActionListener(e->{
								String clicked =((JButton) e.getSource()).getName();
								System.out.println(clicked);
								cc.send("Annan "+clicked);
								});
						}
					}
					else if (string.equals("ingen")) {
						JOptionPane.showMessageDialog(null, "Det är ingen annan online");
						connectFrame.setVisible(true);
					}
					else if (string.equals("ihopkopplad")) {
						JOptionPane.showMessageDialog(null, "Ansluten med " + scanner.next()+"!");
						connectFrame.dispose();
						new SänkaSkepp();
						
					}
					System.out.println(message + " <-- Message");
				}
				@Override
				public void onOpen(ServerHandshake arg0) {
					// FIXME Auto-generated method stub
					while(namn==null||namn==""){
						namn= JOptionPane.showInputDialog("Enter Username", "Gojb");
						cc.send("Namn " + namn);
//						cc.send("STARTA");
					}
				}

			};

		} catch (Exception e) {
			e.printStackTrace();
		}
		cc.connect();

	}

	public SänkaSkepp() {
		frame.setSize(1000, 780);
		frame.setLocationRelativeTo(null);
		// frame.setResizable(false);
		// ---------------------------------------------------------------------------
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		frame.setBackground(Color.blue);

		frame.add(textruta, BorderLayout.NORTH);
		textruta.setOpaque(true);
		frame.add(spelruta, BorderLayout.CENTER);
		spelruta.setOpaque(true);
		frame.add(inställningar, BorderLayout.SOUTH);
		inställningar.setOpaque(true);

		inställningar.setPreferredSize(new Dimension(1000, 250));

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

		båtar=new int[100];

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
				public void mouseReleased(MouseEvent e) {
				}

				public void mousePressed(MouseEvent e) {

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
									if(båtar[clicked+(i*1)]<50){
										kollaRutor++;
									}
								}
								if(kollaRutor==antalRutor){
									for(int i=0;i<antalRutor;i++){
										båtar[clicked+(i*1)]=100;
										egnaLabels[clicked+(i*1)].setBackground(new Color(70+(antalPlacerade*10),70+(antalPlacerade*10),70+(antalPlacerade*10)));
									}
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
									if(båtar[clicked+(i*10)]<50){
										kollaRutor++;
									}
								}
								if(kollaRutor==antalRutor){
									for(int i=0;i<antalRutor;i++){
										båtar[clicked+(i*10)]=100;
										egnaLabels[clicked+(i*10)].setBackground(new Color(70+(antalPlacerade*10),70+(antalPlacerade*10),70+(antalPlacerade*10)));
									}
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

				public void mouseExited(MouseEvent e) {
					// FIXME Auto-generated method stub
					//					egnaLabels[last1].setBackground(new Color(207, 217, 220));
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// FIXME Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// FIXME Auto-generated method stub

				}
			});
			egnaLabels[i].addMouseMotionListener(new MouseAdapter() {
				public void mouseMoved(MouseEvent e) {
					int clicked = Integer.parseInt(((JLabel) e.getSource()).getText());

					for(int i = 1;i<antalRutor;i++){
						if(riktning==Riktning.vert){
							try {
								if(egnaLabels[clicked+(10*i)].getBackground()!=Color.black&&båtar[clicked+(10*i)]<50){
									egnaLabels[clicked+(10*i)].setBackground(Color.black);
								}
								error=false;
							} catch (Exception e2) {
								error=true;
							}
							try {
								for(int i1 = 0;i1<egnaLabels.length;i1++){
									if(egnaLabels[i1].getBackground()==Color.black&&båtar[i1]<50){
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
							if(båtar[clicked]<50){
								egnaLabels[clicked].setBackground(Color.black);
							}
							last1 = clicked;
						}
						else{
							//Horisontellt

							try {

								b = ((double) (clicked+ 10)) / (double) (10);
								y = ((int) b); // y = radnummer, alltså 1 - 16
								x = ((((double) b) - ((double) y)) * 10) + 1d; // x = kolumnnummer, alltså 1 - 10

								b1 = ((double) (clicked+(1*i)+ 10)) / (double) (10);
								y1 = ((int) b1); // y = radnummer, alltså 1 - 16
								x1 = ((((double) b1) - ((double) y1)) * 10) + 1d; // x = kolumnnummer, alltså 1 - 10


								if(x<x1){
									if(egnaLabels[clicked+i].getBackground()!=Color.black&&båtar[clicked+i]<50){
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

									if(egnaLabels[i1].getBackground()==Color.black&&båtar[i1]<50){
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
							if(båtar[clicked]<50){
								egnaLabels[clicked].setBackground(Color.black);
							}
							last1 = clicked;
						}
					}
					for(int i = 0;i<antalRutor;i++){
						if(båtar[i]>50){
							egnaLabels[i].setBackground(new Color(70, 70, 70));
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
				}

				public void mouseExited(MouseEvent e) {
					// FIXME Auto-generated method stub
					annanLabels[last2].setBackground(new Color(145, 176, 223));
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// FIXME Auto-generated method stub
					for(int i = 0;i<egnaLabels.length;i++){
						if(båtar[i]<100){
							egnaLabels[i].setBackground((new Color(207, 217, 220)));
						}
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// FIXME Auto-generated method stub

				}
			});
			annanLabels[i].addMouseMotionListener(new MouseAdapter() {
				public void mouseMoved(MouseEvent e) {
					int clicked = Integer.parseInt(((JLabel) e.getSource()).getText());
					if(bol5==false&&bol4==false&&bol3==false&&bol2==false&&bol1==false){
						annanLabels[last2].setBackground(new Color(145, 176, 223));
						annanLabels[clicked].setBackground(Color.black);
						last2 = clicked;
					}

				}

				@Override
				public void mouseDragged(MouseEvent e) {
				}
			});
			spelruta.addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// FIXME Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					// FIXME Auto-generated method stub

				}

				@Override
				public void mouseExited(MouseEvent e) {
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// FIXME Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// FIXME Auto-generated method stub

				}
			});

		}
		frame.revalidate();
		inställningar.repaint();
		frame.repaint();

		inställningar.addMouseListener(new MouseListener() {

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
						placeraSkepp(båttyp.fem);
					}
					else if (color.getRed() == 201) {
						System.err.println(4);
						bol4=false;
						placeraSkepp(båttyp.fyra);
					}
					else if (color.getRed() == 202) {
						System.err.println(3);
						bol3=false;
						placeraSkepp(båttyp.treEtt);
					}
					else if (color.getRed() == 203) {
						System.err.println(3);
						bol2=false;
						placeraSkepp(båttyp.treTvå);
					}
					else if (color.getRed() == 204) {
						System.err.println(2);
						bol1=false;
						placeraSkepp(båttyp.två);
					}
					inställningar.repaint();
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


	public void placeraSkepp(båttyp båt) {
		stayInside=true;
		riktning=Riktning.hori;
		if(båt==båttyp.fem){
			antalRutor=5;
		}
		else if(båt==båttyp.fyra){
			antalRutor=4;
		}
		else if(båt==båttyp.treEtt){
			antalRutor=3;
		}
		else if(båt==båttyp.treTvå){
			antalRutor=3;
		}
		else if(båt==båttyp.två){
			antalRutor=2;
		}


	}
	public void Klar() {
		System.out.println("KLAR!!");
		// WebSocketImpl.DEBUG=true;

	}
}
