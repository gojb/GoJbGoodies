import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Hypnos extends JPanel implements ActionListener{
	JFrame frame = new JFrame("Studsa");
	Timer timer2 = new Timer(1, this);
	Random random = new Random();
//	private int b=5,r=100,g=255;
	int o = 500;
	int[] r=new int[o] ,g=new int[o],b=new int[o];
	int i=99;
	public static void main(String[] args) {
		new Hypnos();
	}
	public Hypnos() {

		
		frame.setSize(500,500);
		frame.setLocationRelativeTo(null);
//		frame.setIconImage(GoJbsBraOchHa.Mouse.FönsterIcon.getImage());
		frame.add(this);
//		frame.setUndecorated(true);
		frame.getContentPane().setBackground(Color.white);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(3);
		timer2.start();
		frame.repaint();
	}
	public void paintComponent(Graphics ag){
//		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D)ag;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
			r[r.length-1] = random.nextInt(255);
			g[r.length-1] = random.nextInt(255);
			b[r.length-1] = random.nextInt(255);
			
			for (int i = 1; i <= r.length-2; i++) {
				r[i] = r[i+1];
				g[i] = g[i+1];
				b[i] = b[i+1];
			
			}
			boolean h = true;
			int x=102,y=100;
			for (int i = r.length-2; i >= 1; i--) {

				if (h) {
					h=false;
							
							g2.setColor(new Color(r[i],g[i],b[i]));
							g2.drawRect(x, y, i, i);
							x++;
							y++;
				}
				else h=true;
				
	

			}
			
	    	
	}
			

		
	
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == timer2){
	
		frame.repaint();
		}
	
		
	}}