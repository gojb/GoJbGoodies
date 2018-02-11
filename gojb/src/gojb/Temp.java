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

import static javax.swing.UIManager.*;

class Temp{
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		try{setLookAndFeel(getSystemLookAndFeelClassName());}catch(Exception e){}

		//		new Randoms();
		//		new Mailkorg();
		//				new Snake();

		//				new Ping("malla-hp");
		//String string="rtfdjkltrdf";
		//System.out.println(string.replace(";", "."));

		//		new Impossible("Test");
		long max=10000000000l;
		int a=0;
		long start= System.currentTimeMillis();
		long l = 0;
		while(++l<max){
			a = 43243123>>1;
		}
		System.out.println("1:"+a+"   "+ (System.currentTimeMillis()-start));
		start= System.currentTimeMillis();
		l = 0;
		while(++l<max){
			a = 43243123/2;
		}
		System.out.println("2:"+a+"   "+ (System.currentTimeMillis()-start));
		//		try {
		//			JOptionPane.showMessageDialog(null, "Du skrev siffran "+Integer.parseInt(JOptionPane.showInputDialog("Skriv din siffra")));
		//
		//			//			javax.swing.
		//		} catch (NumberFormatException e) {
		//			// TODO Auto-generated catch block
		//			System.err.println("Fel format");
		//
		//		}

		//		int i = 4/3;
		//		System.out.println(i);
		//		new Randoms();

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
