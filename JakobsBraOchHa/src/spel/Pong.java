package spel;

import static gojb.GoJbsBraOchHa.fönsterIcon;
import static java.awt.Color.BLUE;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public
class Pong extends JPanel implements ActionListener{
	Timer timer = new Timer(5,this);
	JFrame frame = new JFrame();
	int x,y,a=1,b,c=0;
	public Pong(){

		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setSize(200, 200);
		frame.add(this);
		frame.setIconImage(fönsterIcon);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		timer.start();

	}

	public void paintComponent (Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(BLUE);
		g2.fillRect(x, y, 25, 25);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == timer){

			if (x +43== frame.getWidth()){
				a = -1;
				b = 0;
			}

			if (y==0&& x +43 == frame.getWidth()){
				a = 0;
				b = -1;



			}
			if (y==0&&x==0){
				a=1;
				b=0;
				System.err.println("öiuds");
			}

			y = y + a;
			x = x + b;

			frame.repaint();
			if (y+72 == frame.getHeight()){
				a = 0 ;
				b =1;

			}
		}

	}
}

