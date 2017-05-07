package glennsPack.GlennTest;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Nedräkning implements ActionListener{

	JFrame frame = new JFrame();
	
	int isek = 59, idel = 9, seki, mini;
	
	JLabel min = new JLabel(),
			sek = new JLabel(Integer.toString(isek)),
			del = new JLabel(Integer.toString(idel));
	
	Timer timer = new Timer(100, this);
	
	public static void main(String[] args) {
		new Nedräkning();	
	}
	
	public Nedräkning(){
		
		frame.setSize(500, 500);
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(3);
		frame.setVisible(true);
		
		
		frame.add(sek);
		sek.setSize(150, 30);
		sek.setLocation(160, 185);
		sek.setFont(new Font("wd",Font.BOLD, 30));
		sek.setOpaque(false);
		sek.setBorder(null);
		
		frame.add(del);
		del.setSize(150, 30);
		del.setLocation(200, 185);
		del.setFont(new Font("wd",Font.BOLD, 30));
		del.setOpaque(false);
		del.setBorder(null);
		
		System.out.println("s");
		System.err.println(min.getText());
		
		timer.start();


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(timer==e.getSource()){
			idel--;
			if(idel<0){
				idel=9;
			}
			del.repaint();
			del.setText(Integer.toString(idel));
			System.err.println("s");
			seki++;
			if(seki==10){
				seki=0;
				isek--;
				if(isek<0){
					isek=59;
				}
				if(isek<10){
				sek.setText("0"+Integer.toString(isek));
				}
				else{
					sek.setText(Integer.toString(isek));
				}
				
			}
			
		}
		
	}

}
