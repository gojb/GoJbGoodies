package gojb;

import javax.sound.sampled.*;
import javax.swing.JSlider;

import GoJbFrame.GoJbFrame;

public class Ljudgenerator {

	public Ljudgenerator() throws LineUnavailableException {
		GoJbFrame frame = new GoJbFrame();
		JSlider slider = new JSlider(0,300,50);
		frame.add(slider);
		
		frame.revalidate();
		frame.repaint();
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setMajorTickSpacing(1000);
		slider.setMinorTickSpacing(100);
		float a = 44100;
	    SourceDataLine sdl = AudioSystem.getSourceDataLine(new AudioFormat(a, 8, 1, true, false));
	    sdl.open();
	    sdl.start();
	    int i=0;
	    byte[] buf = new byte[1];
	    while(true) {
	        buf[0] = (byte)(Math.sin(i++/a*slider.getValue()*100*Math.PI)*100);
	        System.out.println(i);
	        sdl.write(buf, 0, 1 );
//	        if (i>100000) {
//				break;
//			}
	    }
//	    System.out.println(buf[0]);
	}
	public static void main(String[] args) {
		try {
			new Ljudgenerator();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			 
		}
	}
}
