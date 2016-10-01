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

	JLabel gr�n = new JLabel(), r�d = new JLabel(), bl� = new JLabel(), gul = new JLabel();

	Color darkRed=new Color(180,0,0), darkGreen=new Color(0,180,0),darkBlue=new Color(0,0,180),darkYellow=new Color(255,200,0), 
			red = Color.red, green = Color.green, blue = new Color(0, 120, 255), yellow = Color.yellow;

	JMenuBar bar = new JMenuBar();

	JMenuItem start = new JMenuItem("New Game");

	int runda=2;

	Random random = new Random();

	String f�rger[] ={"r","g","b","y"};
	
	ArrayList<String> f�rgerSkaBlinka = new ArrayList<>();
	
	

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

		frame.add(r�d);
		r�d.setBackground(darkRed);
		r�d.setOpaque(true);
		r�d.addMouseListener(this);

		frame.add(gr�n);
		gr�n.setBackground(darkGreen);
		gr�n.setOpaque(true);
		gr�n.addMouseListener(this);

		frame.add(bl�);
		bl�.setBackground(darkBlue);
		bl�.setOpaque(true);
		bl�.addMouseListener(this);

		frame.add(gul);
		gul.setBackground(darkYellow);
		gul.setOpaque(true);
		gul.addMouseListener(this);

		frame.revalidate();

	}

	public void start(){
		for(int i=0;i<runda;i++){
			String f�rg=f�rger[random.nextInt(4)];
			f�rgerSkaBlinka.add(f�rg);
		}
		blinka(f�rgerSkaBlinka);
	}
	public void blinka(ArrayList<String> f�rgSomSkaBlinka){
		Timer timer = new Timer(500, e->{});
		timer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(f�rgSomSkaBlinka.equals("r")){
					if(r�d.getBackground()==darkRed){
						r�d.setBackground(red);
					}
					else{
						r�d.setBackground(darkRed);
						timer.stop();
					}
				}
			}
		});
		timer.start();
		if (f�rgSomSkaBlinka.equals("g")) {
			gr�n.setBackground(green);
			GoJbGoodies.v�nta(300);
			gr�n.setBackground(darkGreen);
		}
		else if (f�rgSomSkaBlinka.equals("b")) {
			bl�.setBackground(blue);
			GoJbGoodies.v�nta(300);
			bl�.setBackground(darkBlue);
		}
		else if (f�rgSomSkaBlinka.equals("y")) {
			gul.setBackground(yellow);
			GoJbGoodies.v�nta(300);
			gul.setBackground(darkYellow);
		}
	}

	public void mousePressed(MouseEvent e) {
		if(e.getSource()==r�d){
			//			if(r�d.getBackground()==darkRed){
			//				r�d.setBackground(red);
			//			}
			//			else{
			//				r�d.setBackground(darkRed);
			//			}
		}

		else if(e.getSource()==gr�n){
			//			if(gr�n.getBackground()==darkGreen){
			//				gr�n.setBackground(green);
			//			}
			//			else{
			//				gr�n.setBackground(darkGreen);
			//			}
		}

		else if(e.getSource()==bl�){
			//			if(bl�.getBackground()==darkBlue){
			//				bl�.setBackground(blue);
			//			}
			//			else{
			//				bl�.setBackground(darkBlue);
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
