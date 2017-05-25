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

package GlennsPack.GlennTest;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;

public class WriteWithPixels {

	JFrame frame = new JFrame();
	JButton[] button = new JButton[16*16];
	int[] a = new int[16*16];
	int b, i, x, y;
	
	public static void main(String[] args) {
		// FIXME Auto-generated method stub
	new WriteWithPixels();
	}

	public WriteWithPixels(){
		
		frame.setSize(675, 675);
		frame.setLayout(new GridLayout(16,16));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(3);
		frame.setVisible(true);
		for(int i = 0; i < button.length; i++){
			a[i]=50;
			button[i]=new JButton();
			button[i].setSize(8,8);
			button[i].setBackground(new Color(255, 255, 255));
			button[i].setFocusPainted(false);
			button[i].addActionListener(e -> {
				a[Arrays.asList(button).indexOf((JButton)e.getSource())]*=-1;
				if(a[Arrays.asList(button).indexOf((JButton)e.getSource())]<0){
					button[Arrays.asList(button).indexOf((JButton)e.getSource())].setBackground(new Color(255, 255, 255,190));
				}
				if(a[Arrays.asList(button).indexOf((JButton)e.getSource())]>0){
					button[Arrays.asList(button).indexOf((JButton)e.getSource())].setBackground(new Color(255, 255, 255));
				}
		
			
			});
			frame.add(button[i]);
		}
		frame.revalidate();
		
		
	}
	
}
