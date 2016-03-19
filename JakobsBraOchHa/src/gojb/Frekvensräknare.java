package gojb;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import GoJbFrame.GoJbFrame;

public class Frekvensräknare {
	GoJbFrame frame = new GoJbFrame("Frekvens",false,3);
	JMenuBar bar = new JMenuBar();
	JMenuItem item = new JMenuItem("+");
	int tot;
	ArrayList<Knapp> buttons =new ArrayList<>();
	public Frekvensräknare() {
		frame.setJMenuBar(bar);
		bar.add(item);
		item.addActionListener(listener);
		frame.setVisible(true);
		frame.setLayout(new GridLayout(10, 4,10 , 10));
	}
	ActionListener listener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			System.err.println("d");
			Knapp knapp = new Knapp(JOptionPane.showInputDialog("Text?"));
			frame.add(knapp.button);
			
			frame.revalidate();
		}
	};
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GoJbGoodies.main("gojb.Frekvensräknare");
	}
	class Knapp{
		JButton button;
		int i;
		String name;
		public Knapp(String name) {
			this.name=name;
			button=new JButton(name);
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					button.setText(name+" - "+ ++i+" klick");
					frame.setTitle("Totalt "+ ++tot+" klick");
				}
			});
		}
	}
}
