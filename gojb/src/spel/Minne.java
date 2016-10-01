package spel;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.Timer;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import GoJbFrame.GoJbFrame;
import gojb.GoJbGoodies;

public class Minne implements MouseListener {

	GoJbFrame frame = new GoJbFrame();

	JLabel grön = new JLabel(), röd = new JLabel(), blå = new JLabel(), gul = new JLabel();

	Color darkRed=new Color(180,0,0), darkGreen=new Color(0,180,0),darkBlue=new Color(0,0,180),darkYellow=new Color(255,200,0), 
			red = Color.red, green = Color.green, blue = new Color(0, 120, 255), yellow = Color.yellow;

	JMenuBar bar = new JMenuBar();

	JMenuItem start = new JMenuItem("New Game");

	int runda=2;

	Random random = new Random();

	String färger[] ={"r","g","b","y"};
	
	ArrayList<String> färgerSkaBlinka = new ArrayList<>();
	
	

	public static void main(String[] args) {
		// FIXME Auto-generated method stub
		new Minne();
	}
	public Minne() {

		frame.setLayout(new GridLayout(2, 2,5,5));
		frame.setJMenuBar(bar);

		bar.add(start);
		start.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// FIXME Auto-generated method stub
				start();
				bar.remove(start);
				frame.revalidate();
			}
		});

		frame.add(röd);
		röd.setBackground(darkRed);
		röd.setOpaque(true);
		röd.addMouseListener(this);

		frame.add(grön);
		grön.setBackground(darkGreen);
		grön.setOpaque(true);
		grön.addMouseListener(this);

		frame.add(blå);
		blå.setBackground(darkBlue);
		blå.setOpaque(true);
		blå.addMouseListener(this);

		frame.add(gul);
		gul.setBackground(darkYellow);
		gul.setOpaque(true);
		gul.addMouseListener(this);

		frame.revalidate();

	}

	public void start(){
		for(int i=0;i<runda;i++){
			String färg=färger[random.nextInt(4)];
			färgerSkaBlinka.add(färg);
		}
		blinka(färgerSkaBlinka);
	}
	public void blinka(ArrayList<String> färgSomSkaBlinka){
		Timer timer = new Timer(500, e->{});
		timer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(färgSomSkaBlinka.equals("r")){
					if(röd.getBackground()==darkRed){
						röd.setBackground(red);
					}
					else{
						röd.setBackground(darkRed);
						timer.stop();
					}
				}
			}
		});
		timer.start();
		if (färgSomSkaBlinka.equals("g")) {
			grön.setBackground(green);
			GoJbGoodies.vänta(300);
			grön.setBackground(darkGreen);
		}
		else if (färgSomSkaBlinka.equals("b")) {
			blå.setBackground(blue);
			GoJbGoodies.vänta(300);
			blå.setBackground(darkBlue);
		}
		else if (färgSomSkaBlinka.equals("y")) {
			gul.setBackground(yellow);
			GoJbGoodies.vänta(300);
			gul.setBackground(darkYellow);
		}
	}

	public void mousePressed(MouseEvent e) {
		if(e.getSource()==röd){
			//			if(röd.getBackground()==darkRed){
			//				röd.setBackground(red);
			//			}
			//			else{
			//				röd.setBackground(darkRed);
			//			}
		}

		else if(e.getSource()==grön){
			//			if(grön.getBackground()==darkGreen){
			//				grön.setBackground(green);
			//			}
			//			else{
			//				grön.setBackground(darkGreen);
			//			}
		}

		else if(e.getSource()==blå){
			//			if(blå.getBackground()==darkBlue){
			//				blå.setBackground(blue);
			//			}
			//			else{
			//				blå.setBackground(darkBlue);
			//			}
		}

		else if(e.getSource()==gul){
			//			if(gul.getBackground()==darkYellow){
			//				gul.setBackground(yellow);
			//			}
			//			else{
			//				gul.setBackground(darkYellow);
			//			}
		}

	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

}
