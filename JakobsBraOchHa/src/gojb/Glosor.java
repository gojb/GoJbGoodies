package gojb;

import static gojb.GoJbsBraOchHa.*;
import static java.awt.Color.RED;
import static javax.swing.JOptionPane.*;
import static javax.swing.SwingConstants.*;
import static javax.swing.WindowConstants.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.text.*;

import GoJbFrame.*;

class Glosor{
	private GoJbFrame frame = new GoJbFrame("Glosor"),frame2 = new GoJbFrame("St�ll in",false,3);
	private JLabel label = new JLabel(),r�ttLabel = new JLabel(),felLabel = new JLabel(),label2 = new JLabel();
	private JTextField textField = new JTextField();
	private JMenuBar bar = new JMenuBar();
	private JMenu instMenu = new JMenu("Inst�llningar");
	private JMenuItem instItem = new JMenuItem("St�ll in glosor");
	private JCheckBoxMenuItem ljudItem = new JCheckBoxMenuItem("Ljud", Bild("/images/Sound.jpg"),Boolean.valueOf(prop.getProperty("glosljud", "true")));
	private JButton button = new JButton("Spara"),button2 = new JButton("Byt plats"),button3 = new JButton("Rensa"),button4 = new JButton("Starta om");
	private String[] spr�k1 = new String[28],
			spr�k2 = new String[spr�k1.length];
	private JTextField[] ettFields = new JTextField[spr�k1.length],
			tv�Fields = new JTextField[spr�k1.length];
	private int index,fel,r�tt;
	private ArrayList<String> ettList,tv�List;
	private JPanel panel = new JPanel(new BorderLayout()),restartPanel = new JPanel(new FlowLayout());
	private Timer timer = new Timer(2000, e -> {stoppatimer();label2.setText("");});
	private void stoppatimer(){timer.stop();}

	Glosor() {
		ljudItem.addActionListener(e -> {prop.setProperty("glosljud", Boolean.toString(ljudItem.isSelected()));sparaProp("Ljud");});
		frame.setSize(frame.getWidth(), 300);
		frame.setJMenuBar(bar);
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(panel,BorderLayout.NORTH);
		frame.add(label,BorderLayout.CENTER);
		frame.add(textField,BorderLayout.SOUTH);
		frame2.add(new JLabel("Spr�k1:"));
		frame2.add(new JLabel("Spr�k2:"));
		for (int i = 0; i < spr�k1.length; i++) {
			spr�k1[i]=prop.getProperty("spr�k1[" + i + "]");
			spr�k2[i]=prop.getProperty("spr�k2[" + i + "]");
			ettFields[i]=new JTextField();
			tv�Fields[i]=new JTextField();
			frame2.add(ettFields[i]);
			frame2.add(tv�Fields[i]);
			ettFields[i].setPreferredSize(new Dimension(60, ettFields[i].getPreferredSize().height));
			ettFields[i].setText(spr�k1[i]);
			tv�Fields[i].setText(spr�k2[i]);
			ettFields[i].addFocusListener(f); 
			tv�Fields[i].addFocusListener(f);
		}	
		frame2.setLocation(frame2.getX(), 5);
		frame2.setUndecorated(true);
		frame2.add(button2);
		frame2.add(button);
		frame2.add(button3);
		frame2.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		frame2.setLayout(new GridLayout(spr�k1.length+3, 2));
		frame2.pack();
		panel.add(r�ttLabel,BorderLayout.WEST);
		panel.add(restartPanel,BorderLayout.CENTER);
		panel.add(felLabel,BorderLayout.EAST);
		panel.add(label2,BorderLayout.SOUTH);
		label2.setVerticalAlignment(BOTTOM);
		label2.setHorizontalAlignment(CENTER);
		label2.setFont(typsnitt);
		label2.setForeground(RED);
		r�ttLabel.setFont(typsnitt);
		felLabel.setFont(typsnitt);
		r�ttLabel.setText("R�tt: 0");
		felLabel.setText("Fel: 0");
		label.setFont(typsnitt);
		label.setVerticalAlignment(BOTTOM);
		label.setHorizontalAlignment(CENTER);
		textField.setFont(typsnitt);
		restartPanel.add(Box.createHorizontalGlue());
		restartPanel.add(Box.createHorizontalGlue());
		restartPanel.add(button4);
		bar.add(instMenu);
		instMenu.add(instItem);
		instMenu.add(ljudItem);
		instItem.addActionListener(e -> {frame2.setVisible(true);frame.setEnabled(false);});
		button.addActionListener(e -> spara());
		button2.addActionListener(e -> byt());
		button3.addActionListener(e -> rens());
		button4.addActionListener(e -> spara());
		textField.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e){}public void keyReleased(KeyEvent e){}public void keyPressed(KeyEvent e){
				if (e.getKeyCode()==10)kolla(); if (e.getKeyCode()==82&&e.getModifiersEx()==128)spara();
			}});
		frame.addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {}public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {}public void windowActivated(WindowEvent e) {}
			public void windowClosed(WindowEvent e) {
				boolean b = false;
				for (Frame frame : JFrame.getFrames()) {
					if (frame.isVisible()) {
						b = true;
					}
				}
				if (!b) {
					System.exit(0);
				}
			}
		});
		blanda();
		s�tt();
	}
	private FocusListener f = new FocusListener() {
		public void focusLost(FocusEvent e) {}
		public void focusGained(FocusEvent e) {
			((JTextComponent) e.getSource()).selectAll();
		}
	};
	private void byt() {
		String[] strings = new String[ettFields.length];
		for (int i = 0; i < ettFields.length; i++) {
			strings[i]=ettFields[i].getText();
			ettFields[i].setText(tv�Fields[i].getText());
			tv�Fields[i].setText(strings[i]);
		}
		s�tt();
	}
	private void spara() {
		for (int i = 0; i < ettFields.length; i++) {
			spr�k1[i]=ettFields[i].getText();
			spr�k2[i]=tv�Fields[i].getText();
			prop.setProperty("spr�k1[" + i + "]", spr�k1[i]);
			prop.setProperty("spr�k2[" + i + "]", spr�k2[i]);
		}
		sparaProp("Glosor");
		frame2.dispose();
		frame.setEnabled(true);
		frame.toFront();
		textField.setText("");
		blanda();
		s�tt();
	}
	private void rens() {
		if (showConfirmDialog(null, "�r du s�ker p� att du vill rensa allt?","Rensa",YES_NO_OPTION,WARNING_MESSAGE)==YES_OPTION) {
			for (int i = 0; i < ettFields.length; i++) {
				ettFields[i].setText("");
				tv�Fields[i].setText("");
			}
		}
	}
	private void kolla() {
		if (textField.getText().equals(tv�List.get(index))){
			r�tt++;
			r�ttLabel.setText("R�tt: " + r�tt);
			if (ljudItem.isSelected()) {
				spelaLjud("/images/tada.wav");
			}
			ettList.set(index, "");
		}
		else {
			if (ljudItem.isSelected()) {
				spelaLjud("/images/Wrong.wav");
			}
			fel++;
			felLabel.setText("Fel: " + fel);
			label2.setText(tv�List.get(index));
			timer.start();
		}
		index++;
		textField.setText("");
		s�tt();
	}
	private void s�tt() {
		System.out.println(index);
		if (index==spr�k1.length) {
			if (r�tt==0&&fel==0) {
				return;
			}
			label.setText("");
			if (fel!=0) {
				String[] strings = {"Starta om","�va p� felaktiga"};
				JOptionPane optionPane = new JOptionPane("R�tt: " + r�tt + "\nFel: " + fel, INFORMATION_MESSAGE, DEFAULT_OPTION, Bild("/images/logga100.png"), strings, strings[1]);
				JDialog dialog = optionPane.createDialog("Resultat");
				dialog.setIconImage(f�nsterIcon);
				dialog.setLocation(frame.getX()+50, frame.getY()-50);
				dialog.setVisible(true);
				if (optionPane.getValue().equals(optionPane.getOptions()[1])) {
					index=0;
					fel=0;
					felLabel.setText("Fel: " + fel);
				}
				else {
					blanda();
				}
			}
			else {
				showMessageDialog(frame, "Alla r�tt!","Resultat", INFORMATION_MESSAGE, Bild("/images/Done.jpg"));
				blanda();
			}
		}
		if (!ettList.get(index).equals("")&&!ettList.get(index).equals(null)) {
			label.setText(ettList.get(index));
			frame.repaint();
		}
		else {
			index++;
			s�tt();
		}
	}
	private void blanda(){
		index=0;
		r�tt=0;
		fel=0;
		r�ttLabel.setText("R�tt: 0");
		felLabel.setText("Fel: 0");
		ettList = new ArrayList<String>(Arrays.asList(spr�k1)); 
		tv�List = new ArrayList<String>(Arrays.asList(spr�k2)); 
		Long seed = System.currentTimeMillis();
		Collections.shuffle(ettList,new Random(seed));
		Collections.shuffle(tv�List,new Random(seed));
	}
	public static void main(String[] args) {
		GoJbsBraOchHa.main("gojb.Glosor");
	}
}
