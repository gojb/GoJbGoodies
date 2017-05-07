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