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
	
	String string;
	
	Timer timer = new Timer(1,this);
	
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
//		int sökt = scanner.nextInt();
//		int j;
//		for (j = 0; j < n &&a[j]!=sökt ; j++) {
//			
//		}
//		if (j<n) {
//			System.out.println(j);
//		}
//		else {
//			System.err.println("oaergvjö");
//		}
		
//		JButton[] hButtons = {new JButton("ad"),new JButton("ja"),new JButton("gl"),new JButton("jo"),new JButton("ti"),new JButton("ma")};
		
		String[] ss = {"","0 j","0 j","0 j","jo","",""};
		Arrays.sort(ss);
		
		for (int i = 1; i < ss.length; i++) {
			System.out.println(ss[i]);
		}
		System.out.println(Arrays.binarySearch(ss, JOptionPane.showInputDialog("dlfhmöesklgjnlrhbdc")));
		
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
//				prop.store(new FileWriter(getClass().getResource("/images/Hej.txt").getFile()),"Inställningar för Txt.java");
//				
//				
				
				
				
				
				
				string = prop.getProperty("Språk");
				
				if (string.equals("Svenska")){
					text.setText("Hej!");
				}
				if (string.equals("English")){
					text.setText("Hello!");
				}
				
				
			} catch (Exception e) {
				text.setText("ERROR!");
				text.setFont(new Font("odfh", Font.ITALIC, 50));
			}
			
		}
		
	}
}
