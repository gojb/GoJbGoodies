package gojb;

import static javax.swing.UIManager.*;

import java.awt.Color;

class Temp{
	public static void main(String[] args){
		try{setLookAndFeel(getSystemLookAndFeelClassName());}catch(Exception e){}
		new Randoms();
		//		new Mailkorg();
		//		new Snake();
		//		new Ping("192.168.2.131");
		//		new Merit();
		//		new Kurve(); 
		//		new OpenGLTest();
		//		GoJbsBraOchHa.main("snabb");		
		
//		try {
//			new SkickaMail().Skicka("glennholsson@gmail.com", "YO", "hey ehy");
//		} catch (AddressException e) {
//			e.printStackTrace();
//			System.err.println("--Fel på Adress!!");
//		} catch (MessagingException e) {
//			System.err.println("--Fel på Meddelande");
//			e.printStackTrace();
//		}
//		
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
		int[] x = new int[10];
		x[1]=1;
		x[2]=2;
		x[3]=3;
		System.out.println(x.toString());
		;
		System.err.println(new Color(50,50,50).getRGB());

	}

} 
