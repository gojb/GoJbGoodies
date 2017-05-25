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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

class ReggPlåtar implements ActionListener{

	Random random1 = new Random(),
			random2 = new Random(),
			random3 = new Random(),
			random4 = new Random();

	int High = 999;

	String numbers = new String();

	Timer timer = new Timer(3, this);

	char string;	
	public static void main(String[] args) {
		new ReggPlåtar();
	}

	public ReggPlåtar(){


		new JFrame().setVisible(false);

		timer.start();

		System.err.println("");

	}

	public void Kör(){

		Random r = new Random();
		char c = (char)(r.nextInt(26) + 'a');
		char c2 = (char)(r.nextInt(26) + 'a');
		char c3 = (char)(r.nextInt(26) + 'a');

		if (c == 'i'||c == 'v'||c == 'q') {

		}
		else if (c2 == 'i'||c2 == 'v'||c2 == 'q') {

		}
		else if (c3 == 'i'||c3 == 'v'||c3 == 'q') {

		}
		else {

			numbers=Integer.toString(r.nextInt(High));

			if(numbers.length()==1){
				numbers="00"+numbers;
			}
			if(numbers.length()==2){
				numbers="0"+numbers;
			}

			System.err.println(numbers + "  "+ ("" + c  + c2 + c3).toUpperCase());
			string = c;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		Kör();

	}

}