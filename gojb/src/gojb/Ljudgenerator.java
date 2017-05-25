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
