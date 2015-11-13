package gojb;

import static javax.swing.UIManager.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

class Temp{
	public static void main(String[] args){
		try{setLookAndFeel(getSystemLookAndFeelClassName());}catch(Exception e){}
		//		new Mailkorg();
		//		new Snake();
		//		new Ping("192.168.2.131");
		//		new Merit();
		//		new Kurve(); 
		//		new OpenGLTest();
		//		GoJbsBraOchHa.main("snabb");
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(GoJbGoodies.class.getResource("/images/alarm.wav")));
			clip.loop(Clip.LOOP_CONTINUOUSLY);

		} catch (Exception e) {
			e.printStackTrace();
		}

		new Ping("GLENNSLENOVO");

	}

} 
