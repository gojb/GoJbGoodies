package spel;

import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.Timer;

import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import GoJbFrame.GoJbFrame;
import gojb.GoJbGoodies;

public class Minne implements MouseListener {

	GoJbFrame frame = new GoJbFrame();

	JLabel greenLabel= new JLabel(), redLabel = new JLabel(), blueLabel = new JLabel(), yellowLabel = new JLabel();

	Color darkRed=new Color(180,0,0), darkGreen=new Color(0,180,0),darkBlue=new Color(0,0,180),darkYellow=new Color(255,200,0), 
			lightRed = Color.red, lightGreen = Color.green, lightBlue = new Color(0, 120, 255), lightYellow = Color.yellow;

	JMenuBar bar = new JMenuBar();

	JMenuItem start = new JMenuItem("New Game");

	int runda=1, index=0;

	Random random = new Random();

	String f�rger[] ={"r","g","b","y"};

	ArrayList<String> f�rgerSkaBlinka = new ArrayList<>();
	
	Clip greenClip, redClip, yellowClip, blueClip;

	boolean varranan,clickable = false, mouseDown;

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

		frame.add(redLabel);
		redLabel.setBackground(darkRed);
		redLabel.setOpaque(true);
		redLabel.addMouseListener(this);

		frame.add(greenLabel);
		greenLabel.setBackground(darkGreen);
		greenLabel.setOpaque(true);
		greenLabel.addMouseListener(this);

		frame.add(blueLabel);
		blueLabel.setBackground(darkBlue);
		blueLabel.setOpaque(true);
		blueLabel.addMouseListener(this);

		frame.add(yellowLabel);
		yellowLabel.setBackground(darkYellow);
		yellowLabel.setOpaque(true);
		yellowLabel.addMouseListener(this);

		frame.revalidate();

		//		clip = AudioSystem.getClip();
		//		clip.open(AudioSystem.getAudioInputStream(getClass().getResource("\\images\\greenSound.waw")));
		//		
		
		try {
			greenClip = AudioSystem.getClip();
			greenClip.open(AudioSystem.getAudioInputStream(GoJbGoodies.class.getResource("/images/greenSound.wav")));	
		} catch (Exception e1) {
			System.err.println("ERROR");
		}
		try {
			redClip = AudioSystem.getClip();
			redClip.open(AudioSystem.getAudioInputStream(GoJbGoodies.class.getResource("/images/redSound.wav")));	
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			blueClip = AudioSystem.getClip();
			blueClip.open(AudioSystem.getAudioInputStream(GoJbGoodies.class.getResource("/images/blueSound.wav")));	
		} catch (Exception e1) {
			System.err.println("ERROR");
		}
		try {
			yellowClip = AudioSystem.getClip();
			yellowClip.open(AudioSystem.getAudioInputStream(GoJbGoodies.class.getResource("/images/yellowSound.wav")));	
		} catch (Exception e1) {
			System.err.println("ERROR");
		}
	}

	public void start(){

		frame.setTitle("Niv� " + runda);

		for(int i=f�rgerSkaBlinka.size();i<runda;i++){
			String f�rg=f�rger[random.nextInt(4)];
			f�rgerSkaBlinka.add(f�rg);
		}
		blinka();
	}
	public void blinka(){
		clickable=false;
		Timer timer = new Timer(500, e->{});
		index = 0;
		redLabel.setBackground(darkRed);
		greenLabel.setBackground(darkGreen);
		blueLabel.setBackground(darkBlue);
		yellowLabel.setBackground(darkYellow);
		timer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(varranan==false){
					if(f�rgerSkaBlinka.size()>index){
						String f�rg= f�rgerSkaBlinka.get(index);
						if(f�rg.equals("r")){
							redLabel.setBackground(lightRed);
							redClip.start();
							redClip.setMicrosecondPosition(0);
						}
						else if(f�rg.equals("g")){
							greenLabel.setBackground(lightGreen);
							greenClip.start();
							greenClip.setMicrosecondPosition(0);
						}
						else if(f�rg.equals("b")){
							blueLabel.setBackground(lightBlue);
							blueClip.start();
							blueClip.setMicrosecondPosition(0);
						}
						else if(f�rg.equals("y")){
							yellowLabel.setBackground(lightYellow);
							yellowClip.start();
							yellowClip.setMicrosecondPosition(0);
						}

						index++;

						varranan=true;
					}
				}
				else{
					if(f�rgerSkaBlinka.size()<=index){
						index=0;

						clickable = true;

						timer.stop();
					}
					redLabel.setBackground(darkRed);
					greenLabel.setBackground(darkGreen);
					blueLabel.setBackground(darkBlue);
					yellowLabel.setBackground(darkYellow);

					varranan=false;
				}
			}
		});
		timer.start();
	}

	public void mousePressed(MouseEvent e) {
		mouseDown=true;
		if(clickable){
			if(e.getSource()==redLabel){
				if(f�rgerSkaBlinka.get(index).equals("r")){
					index++;
					redLabel.setBackground(lightRed);
					redClip.start();
					redClip.setMicrosecondPosition(0);
				}
				else{
					gameOver();
					return;
				}
			}

			else if(e.getSource()==greenLabel){
				if(f�rgerSkaBlinka.get(index).equals("g")){
					index++;
					greenLabel.setBackground(lightGreen);
					greenClip.start();
					greenClip.setMicrosecondPosition(0);
				}
				else{
					gameOver();
					return;
				}
			}

			else if(e.getSource()==blueLabel){
				if(f�rgerSkaBlinka.get(index).equals("b")){
					index++;
					blueLabel.setBackground(lightBlue);
					blueClip.start();
					blueClip.setMicrosecondPosition(0);
				}
				else{
					gameOver();
					return;
				}
			}

			else if(e.getSource()==yellowLabel){
				if(f�rgerSkaBlinka.get(index).equals("y")){
					index++;
					yellowLabel.setBackground(lightYellow);
					yellowClip.start();
					yellowClip.setMicrosecondPosition(0);
				}
				else{
					gameOver();
					return;
				}
			}

		}
	}
	public void gameOver() {

		Object[] options = {"Ja","Nej"};
		int choice=JOptionPane.showOptionDialog(frame, "Du klarade "+ (f�rgerSkaBlinka.size()-1) + " niv�er. Vill du spela igen?",
				"Spelet �r slut", JOptionPane.YES_NO_CANCEL_OPTION, 
				JOptionPane.DEFAULT_OPTION,
				null,options, options[0]);
		frame.dispose();
		if(choice==0){
			bar.add(start);
			frame.setVisible(true);
			frame.paintComponents(frame.getGraphics());
			clickable=false;
			runda=1;
			index=0;
			f�rgerSkaBlinka.clear();

		}
		else if(choice==1){
			System.exit(3);
		}

	}
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {
		mouseDown=false;
		if(e.getSource()==redLabel){
			redLabel.setBackground(darkRed);
		}

		else if(e.getSource()==blueLabel){
			blueLabel.setBackground(darkBlue);
		}

		else if(e.getSource()==yellowLabel){
			yellowLabel.setBackground(darkYellow);
		}

		else if(e.getSource()==greenLabel){
			greenLabel.setBackground(darkGreen);
		}

		if(index==f�rgerSkaBlinka.size()){
			runda++;
			clickable=false;
			redLabel.setBackground(darkRed);
			greenLabel.setBackground(darkGreen);
			blueLabel.setBackground(darkBlue);
			yellowLabel.setBackground(darkYellow);
			start();
		}
	}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

}
