package gojb;

import static javax.swing.UIManager.*;

import javax.swing.LookAndFeel;
import javax.swing.UnsupportedLookAndFeelException;

import spel.Snake;

class Temp{
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		LookAndFeel s = getLookAndFeel();
		try{setLookAndFeel(getSystemLookAndFeelClassName());}catch(Exception e){}
		try {
			setLookAndFeel(s);
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		new Randoms();
		//		new Mailkorg();
				new Snake();

//				new Ping("malla-hp");

//				new Ping("192.168.115.100");
		//		new Merit();
		//		new Kurve(); 
		//		new OpenGLTest();
//				GoJbGoodies.main("snabb");
		
//		try {
//			new SkickaMail().Skicka("glennholsson@gmail.com", "YO", "hey ehy");
//		} catch (AddressException e) {
//			e.printStackTrace();
//			System.err.println("--Fel på Adress!!");
//		} catch (MessagingException e) {
//			System.err.println("--Fel på Meddelande");
//			e.printStackTrace();
//		}
////		
//		try {
//			Clip clip = AudioSystem.getClip();
//			clip.open(AudioSystem.getAudioInputStream(GoJbGoodies.class.getResource("/images/tada.wav")));
//			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
//			gainControl.setValue(gainControl.getMaximum());
//			clip.start();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		new Ping("GLENNSLENOVO");
		//		GoJbsBraOchHa.main("snabb");
//		try {
//			Clip clip = AudioSystem.getClip();
//			clip.open(AudioSystem.getAudioInputStream(GoJbGoodies.class.getResource("/images/alarm.wav")));
//			clip.loop(Clip.LOOP_CONTINUOUSLY);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		new Ping("GLENNSLENOVO");

	}

} 
