package gojb;

import javax.sound.sampled.*;

public class Ljudgenerator {

	public Ljudgenerator() throws LineUnavailableException {
		float a = 44100;
	    SourceDataLine sdl = AudioSystem.getSourceDataLine(new AudioFormat(a, 8, 1, true, false));
	    sdl.open();
	    sdl.start();
	    int f = 600,i=0;
	    byte[] buf = new byte[1];
	    while(true) {
	        buf[0] = (byte)(Math.sin(i++/a*f*2*Math.PI)*100);
	        sdl.write( buf, 0, 1 );
	        if (i>100000) {
				break;
			}
	    }
	    System.out.println(buf[0]);
	}
	public static void main(String[] args) {
		try {
			new Ljudgenerator();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
			 
		}
	}
}
