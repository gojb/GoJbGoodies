package gojb;

import static java.awt.Toolkit.getDefaultToolkit;
import static javax.swing.UIManager.*;

import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

class Temp{
	static Clip clip;
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
			
			clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(GoJbsBraOchHa.class.getResource("/images/alarm.wav")));
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			
		} catch (Exception e) {
			((Runnable) getDefaultToolkit().getDesktopProperty("win.sound.hand")).run();
			e.printStackTrace();
			}
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
		new Ping("ADAM-HP");
		
	}

} 
