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

package test;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Properties;

import javax.swing.*;



public class Txt implements ActionListener {

	JFrame frame = new JFrame();
	
	JLabel text = new JLabel("Hejffsd");
	
	Properties prop = new Properties();
	
	String string = "";
	
	Timer timer = new Timer(1,this);
	int a, b;
	
	public static void main(String[] args) {
		
		new Txt();

	}

	public Txt() {
		
		frame.setSize(300,200);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(3);
		frame.setLayout(new FlowLayout());
		frame.add(text);
		frame.setVisible(true);
		text.setFont(new Font("lisd", Font.ITALIC, 100));	
		text.setVerticalTextPosition(SwingConstants.CENTER);
		text.setHorizontalAlignment(SwingConstants.CENTER);
		timer.start();		
		System.out.println("sdaads");
		System.out.println(a > b ? -1 : a == b ? 0 : 1);
//		
//		@SuppressWarnings("resource")
//		Scanner scanner = new Scanner(System.in);
//		System.out.println("giopaslfasldf");
//		final int n = scanner.nextInt();
//		int[] a = new int[n];
//		for (int i = 0; i < n; i++) {
//			System.out.println(i);
//			a[i] = scanner.nextInt();
//			
//		}
//		System.out.println("lkergn?");
//		int s�kt = scanner.nextInt();
//		int j;
//		for (j = 0; j < n &&a[j]!=s�kt ; j++) {
//			
//		}
//		if (j<n) {
//			System.out.println(j);
//		}
//		else {
//			System.err.println("oaergvj�");
//		}
		
//		JButton[] hButtons = {new JButton("ad"),new JButton("ja"),new JButton("gl"),new JButton("jo"),new JButton("ti"),new JButton("ma")};
		
		String[] ss = {"","0 j","0 j","0 j","jo","",""};
		Arrays.sort(ss);
		
		for (int i = 1; i < ss.length; i++) {
			System.out.println(ss[i]);
		}
		System.out.println(Arrays.binarySearch(ss, JOptionPane.showInputDialog("dlfhm�esklgjnlrhbdc")));
		
		System.out.println(System.getProperties().toString());
//		frame.add(hButtons[0]);
//		frame.add(hButtons[1]);
//		frame.add(hButtons[2]);
//		frame.add(hButtons[3]);
//		Arrays.sort(hButtons);
//		for (int i = 1; i < ss.length; i++) {
//			System.out.println(ss[i]);
//		}
//		System.out.println(Arrays.binarySearch(ss, "tina"));
		
	

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(timer == arg0.getSource()){
			try {
				
				prop.load(getClass().getResource("/images/Hej.txt").openStream());
				
//				string = prop.getProperty("Hiscore1");
//				System.err.println(string);
//				
//				string = prop.getProperty("Hiscore2");
//				System.err.println(string);
//				
//				string = prop.getProperty("Hiscore3");
//				System.err.println(string);
//				
//				prop.setProperty("Hiscore3", "htgrfed");
//				
//				prop.store(new FileWriter(getClass().getResource("/images/Hej.txt").getFile()),"Inst�llningar f�r Txt.java");
//				
//				
				
				
				
				
				
				string = prop.getProperty("Spr�k"," ");
				
				if (string.equals("Svenska")){
					text.setText("Hej!");
				}
				if (string.equals("English")){
					text.setText("Hello!");
				}
				
				
			} catch (Exception e) {
				text.setText("ERROR!");
				text.setFont(new Font("odfh", Font.ITALIC, 50));
				e.printStackTrace();
			}
			
		}
		
	}
}
