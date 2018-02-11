/*
 * Copyright 2017 GoJb Development
 *
 * Permission is hereby granted, free of charge, to any
 * person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to
 *  use, copy, modify, merge, publish, distribute, sublicense, and/or
 *  sell copies of the Software, and to permit persons to whom
 *  the Software is furnished to do so, subject to the following
 *  conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF
 * ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
 * SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package gojb;
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
		private static final long serialVersionUID = 1L;

		protected void paintComponent(java.awt.Graphics g) {
			long intiger = System.currentTimeMillis();
			for(int y = 0; y<1080;y++){
				for(int i = 0; i < 1920;i++){
					g.setColor(new Color(r.nextInt(255),r.nextInt(255),r.nextInt(255)));
					g.drawRect(i, y, 1, 1);
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

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// FIXME Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// FIXME Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// FIXME Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// FIXME Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// FIXME Auto-generated method stub

	}

}
