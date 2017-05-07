package glennsPack.GlennTest;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;

public class RandomBild implements MouseInputListener{

	JFrame frame = new JFrame();
	
	Random r = new Random();
	
	JLabel label = new JLabel(){
		protected void paintComponent(java.awt.Graphics g) {
			long intiger = System.currentTimeMillis();
			for(int y = 0; y<1080;y++){
				for(int i = 0; i < 1920;i++){
				g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
					g.drawRect(i, y, i, i);
			}
			}
			System.out.println((System.currentTimeMillis()-intiger)/1000d);

		};
	};

	public static void main(String[] args) {
		// FIXME Auto-generated method stub
		new RandomBild();
	}

	public RandomBild() {
		// FIXME Auto-generated constructor stub
		frame.setSize(600, 800);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(3);
		frame.setVisible(true);
		frame.add(label);
		frame.addMouseListener(this);
		label.repaint();
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// FIXME Auto-generated method stub
		label.repaint();
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// FIXME Auto-generated method stub
		label.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// FIXME Auto-generated method stub
		label.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// FIXME Auto-generated method stub
		label.repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// FIXME Auto-generated method stub
		label.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// FIXME Auto-generated method stub
		label.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// FIXME Auto-generated method stub
		label.repaint();
	}

}
