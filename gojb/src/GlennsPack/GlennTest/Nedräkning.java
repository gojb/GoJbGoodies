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
