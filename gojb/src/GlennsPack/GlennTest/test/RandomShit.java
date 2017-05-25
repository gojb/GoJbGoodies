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

package GlennsPack.GlennTest.test;

import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class RandomShit {

	String[] stringList;
	String abc = new String("abcdefghijklmnopqrstuvwxyzåäö"), totalString = new String(), memesToAdd = new String(), stringsToAdd = new String(),
			textOfElement = new String("hi"), totalText = new String();
	public static Properties prop = new Properties();
	JFrame frame = new JFrame("Change text");

	JPanel panel = new JPanel();

	JTextArea stringsToLookFor = new JTextArea();

	JButton done = new JButton("Done");
	public static void main(String[] args) {

		new RandomShit();
	}
	public RandomShit(){
		ShitSatains();

	}
	public void ShitSatains() {
		
		
		System.out.println(Math.floorDiv(30, 20));
		System.out.println(30/20);
		
		//		System.out.println(i.length + "    "+a);
		//		String string = new String("Psst… Now you can privately message a user without leaving chat."
		//				+ " Type /w <username> to send a whisper and start a private chat without leavinhig your favorite channels");
		//		
		//		System.out.println(string.charAt(7));
		//		System.err.println(abc.indexOf(string.charAt(9)));

//		try {
//			prop.load(new FileInputStream(System.getProperty ("user.home") + "\\Desktop\\Kakansbot.settings"));
//		} catch (Exception e) {
//			// FIXME Auto-generated catch block
//			try {
//				prop.setProperty("StringsToLookFor1", "");
//				prop.store(new FileWriter(new File(System.getProperty("user.home") + "\\Desktop\\Kakansbot.settings")),"Settings for KakansBot");
//			} catch (Exception e1) {
//				System.err.println("Mappen finns inte! Skapar...");
//				new File(System.getProperty("user.home") + "\\Desktop\\").mkdir();
//				try {
//					prop.store(new FileWriter(new File(System.getProperty("user.home") + "\\Desktop\\Kakansbot.settings")),"Settings for KakansBot");
//				} catch (IOException e2) {
//					e2.printStackTrace();
//				}
//			}
//		}
//
//		if(JOptionPane.showConfirmDialog(null, "Create Text again or nah?")==JOptionPane.YES_OPTION){
////			frame.setSize(500, 500);
////			frame.setLocationRelativeTo(null);
////			frame.setVisible(true);
////			frame.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
////			panel.add(stringsToLookFor);
////			panel.add(done);
//			stringsToLookFor.setText(prop.getProperty("StringsToLookFor1"));
//			//			stringsToAdd = JOptionPane.showInputDialog("Strings to add, separate by ','");
//			//			memes=JOptionPane.showInputDialog("Memes to add, separate by ','");
//			stringsToAdd="Hey,Hello,Yo,This,Hi,hitler";
//
//			System.out.println(Arrays.asList(stringsToAdd.split(",")).toString()+" = splitted string");
//
//			prop.setProperty("StringsToLookFor1", Arrays.asList(stringsToAdd.split(",")).toString());
//
//			try {
//				prop.store(new FileWriter(new File(System.getProperty("user.home") + "\\Desktop\\Kakansbot.settings")),"Settings for KakansBot");
//			} catch (Exception e1) {
//				System.err.println("Mappen finns inte! Skapar...");
//				new File(System.getProperty("user.home") + "\\Desktop\\").mkdir();
//				try {
//					prop.store(new FileWriter(new File(System.getProperty("user.home") + "\\Desktop\\Kakansbot.settings")),"Settings for KakansBot");
//				} catch (IOException e2) {
//					e2.printStackTrace();
//				}
//			}
//
//
//		}
//
//		System.err.println(Integer.parseInt(prop.getProperty("NrStringToLookFor")));
//		for(int nrList = 1;nrList!=Integer.parseInt(prop.getProperty("NrStringToLookFor"));nrList++){
//			stringList = prop.getProperty("StringsToLookFor"+nrList).toString().toLowerCase().split(",");
//			for(int i = 0; i < stringList.length;i++){
//				stringList[i] = stringList[i].replace(" ", "");
//				System.out.println(stringList[i]);
//				totalText=stringList[i].toString();
//				if(totalText.toString().contains(textOfElement.toLowerCase())
//						&&(totalText.length()>1&&(((totalText.indexOf(textOfElement)>0)&&
//								abc.indexOf(Character.toLowerCase(totalText.charAt((totalText.indexOf(textOfElement)-1))))==-1)||
//								(totalText.indexOf(textOfElement)==0)))&&
//						(totalText.length()>textOfElement.length()&&(abc.indexOf(totalText.charAt((totalText.indexOf(textOfElement)+textOfElement.length()+1)))==-1)||
//								totalText.length()==textOfElement.length()
//								)){
//					System.err.println("YASSSS "+totalText);
//				}
//			}
//		}
		//			try {
		//				prop.store(new FileWriter(new File(System.getProperty("user.home") + "C:\\Users\\Glenn\\Desktop\\Kakansbot.settings")),"Settings for KakansBot");
		//			} catch (Exception e1) {
		//				System.err.println("Mappen finns inte! Skapar...");
		//				new File(System.getProperty("user.home") + "C:\\Users\\Glenn\\Desktop\\").mkdir();
		//				try {
		//					prop.store(new FileWriter(new File(System.getProperty("user.home") + " C:\\Users\\Glenn\\Desktop\\Kakansbot.settings")),"Settings for KakansBot");
		//				} catch (IOException e2) {
		//					e2.printStackTrace();
		//				}
		//			}
		//			
		//			try {
		//				prop.store(new FileWriter(new File(System.getProperty("user.home") + "\\AppData\\Roaming\\KakansBot\\Kakansbot.settings")),"Inställningar för GoJbGuide");
		//			} catch (Exception e1) {
		//				System.err.println("Mappen finns inte! Skapar...");
		//				new File(System.getProperty("user.home") + "\\AppData\\Roaming\\KakansBot\\").mkdir();
		//				try {
		//					prop.store(new FileWriter(new File(System.getProperty("user.home") + "\\AppData\\Roaming\\KakansBot\\Kakansbot.settings")),"Inställningar för GoJbGuide");
		//				} catch (IOException e2) {
		//					e2.printStackTrace();
		//				}
		//			}



		//		prop.setProperty("Namn", totalString);



		//		while (string.indexOf("hi")!=-1) {
		//	
		//		System.out.println(string.indexOf("hi"));
		//		a=string.indexOf("hi");
		//		System.out.println(a + " = a");
		//		System.out.println(string.charAt(a-1));
		//		for(int i = 0; i < abc.length();i++){
		//			System.err.println("a = " + a + "  i = "+i);
		//			if(string.charAt(a-1)==abc.charAt(i)){
		//				System.out.println("Stämmer på " + abc.charAt(i));
		//				string=string.substring(a+("hi".length()));
		//				System.err.println(string);
		//				a=0;
		//				i=abc.length();
		//				}
		//		}
		//		}	
		//		if(a!=5){
		//			ShitSatains();
		//		}
		//		else{
		//			System.exit(3);
		//		}

	}

}
