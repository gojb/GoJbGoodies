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

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import GoJbFrame.GoJbFrame;

public class Frekvensr�knare {
	GoJbFrame frame = new GoJbFrame("Frekvens",false,3);
	JMenuBar bar = new JMenuBar();
	JMenuItem item = new JMenuItem("+");
	int tot;
	ArrayList<Knapp> buttons =new ArrayList<>();
	public Frekvensr�knare() {
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
		GoJbGoodies.main("gojb.Frekvensr�knare");
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
