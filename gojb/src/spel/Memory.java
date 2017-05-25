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

package spel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.event.MouseInputListener;

import GoJbFrame.GoJbFrame;

public class Memory implements MouseInputListener{

	GoJbFrame frame = new GoJbFrame();
	Random random = new Random();
	JLabel[] label;
	public static void main(String[] args) {
		new Memory();
	}

	public Memory(){

		label = new JLabel[64];
		frame.setLayout(new GridLayout((int)(Math.sqrt(label.length)),(int)(Math.sqrt(label.length)),2,2));	
		for(int i = 0; i<label.length;i++){
			label[i]=new JLabel();
			label[i].addMouseListener(this);
			frame.add(label[i]);
			label[i].setBackground(Color.green);
			label[i].setOpaque(true);

			frame.revalidate();
		}
	}


	@Override
	public void mouseClicked(MouseEvent e) {

				System.out.println(e.getSource().getClass().getDeclaredClasses());

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

