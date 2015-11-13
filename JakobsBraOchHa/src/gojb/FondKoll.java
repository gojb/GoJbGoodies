package gojb;

import static gojb.GoJbGoodies.*;
import static java.awt.Color.*;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

import java.awt.*;
import java.io.*;


import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import GoJbFrame.GoJbFrame;

import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;

@SuppressWarnings("unchecked")
class FondKoll implements Serializable{
	private static final long serialVersionUID = -2547083481485451726L;
	private GoJbFrame frame = new GoJbFrame("GoJbs Fondkoll", false, DISPOSE_ON_CLOSE);

	JButton button = new JButton("+");
	ArrayList<Fond> arrayList = new ArrayList<Fond>();
	JComboBox<Fond> comboBox;
	JPanel panel = new JPanel(new GridLayout(0, 4, 1, 1)),
			panel2 = new JPanel(new GridLayout(1, 0, 0, 0));
	JScrollPane scrollPane = new JScrollPane(panel,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	JMenuBar bar = new JMenuBar();
	JMenuItem edit = new JMenuItem("Ändra");
	private static Thread currentThread;
	public FondKoll() {

		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(BASE64DecoderStream.decode(prop.getProperty("Fonder").getBytes()));
			ObjectInputStream oInputStream = new ObjectInputStream(bis);
			arrayList = (ArrayList<Fond>) oInputStream.readObject();
		} catch (Exception e1) {
			System.err.println("inget sparat");
			e1.printStackTrace();
		}
		comboBox = new JComboBox<Fond>(arrayList.toArray(new Fond[0]));

		bar.add(edit);
		panel.setBackground(BLACK);
		panel.setOpaque(true);

		frame.setJMenuBar(bar);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		frame.add(scrollPane,BorderLayout.CENTER);
		frame.add(panel2,BorderLayout.NORTH);
		panel2.add(comboBox);
		panel2.add(button);

		button.addActionListener(e -> nyFond());
		comboBox.addActionListener(e -> läs());
		edit.addActionListener(e -> ändra());

		frame.setVisible(true);
		läs();
	}
	private void ändra() {
		GoJbFrame frame = new GoJbFrame("Ändra", true, DISPOSE_ON_CLOSE);
		JButton button = new JButton("Spara");
		frame.setLayout(new GridLayout(0,4,1,1));
		int a = arrayList.size();
		JTextField[] nameFields = new JTextField[100];
		JTextField[] IDFields = new JTextField[100];
		JTextField[] beloppFields = new JTextField[100];
		JTextField[] andelarFields = new JTextField[100];

		for (int i = 0; i < a; i++) {
			Fond fond = arrayList.get(i);
			nameFields[i]=new JTextField(fond.toString());
			IDFields[i]=new JTextField(fond.getID());
			beloppFields[i]=new JTextField(fond.getBelopp()+"");
			andelarFields[i]=new JTextField(fond.getAndelar()+"");
			frame.add(nameFields[i]);
			frame.add(IDFields[i]);
			frame.add(beloppFields[i]);
			frame.add(andelarFields[i]);
		}
		frame.add(button);
		frame.pack();
		button.addActionListener(e ->{
			arrayList.clear();
			for (int i = 0; i < a; i++) {
				new Fond(nameFields[i].getText(), IDFields[i].getText(), Integer.parseInt(beloppFields[i].getText()), Double.parseDouble(andelarFields[i].getText()));
			}
			frame.dispose();
			comboBox.setModel(new DefaultComboBoxModel<Fond>(arrayList.toArray(new Fond[0])));
			läs();
		});

	}	
	private void läs() {
		currentThread = new Thread(){
			@Override
			public void run() {
				frame.getRootPane().setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				super.run();
				panel.removeAll(); 
				Fond fond = (Fond)comboBox.getSelectedItem();
				fond.hämta();
				for (Fondkurs fondkurs:fond.kurser) {
					if (currentThread!=currentThread()) {
						System.err.println("Stoppad");
						panel.revalidate();
						return;
					}
					JLabel label = new JLabel(fondkurs.getDate()),
							label2 = new JLabel(fondkurs.getKurs()+""),
							label3 = new JLabel(fondkurs.getÖkning()+":-"),
							label4 = new JLabel(fondkurs.getProcentuellÖkning()+"%");

					label.setOpaque(true);
					label2.setOpaque(true);
					label3.setOpaque(true);
					label4.setOpaque(true);
					label.setBackground(white);
					label2.setBackground(white);
					label3.setBackground(GREEN);
					label4.setBackground(GREEN);
					if (label3.getText().startsWith("-")) {
						label3.setBackground(red);
						label4.setBackground(red);
					}
					else {
						label3.setText("+"+label3.getText());
					}
					panel.add(label);
					panel.add(label2);
					panel.add(label3);
					panel.add(label4);
				}
				frame.getRootPane().setCursor(Cursor.getDefaultCursor());
				panel.revalidate();

			}
		};
		currentThread.start();
	}
	private void nyFond() {
		GoJbFrame frame = new GoJbFrame("Lägg till ny fond",false,DISPOSE_ON_CLOSE);
		JLabel label = new JLabel("Namn"),
				label2 = new JLabel("ID"),
				label3 = new JLabel("Belopp"),
				label4 = new JLabel("Andelar"),
				label5 = new JLabel();
		JTextField field = new JTextField(), 
				field2 = new JTextField(), 
				field3 = new JTextField(), 
				field4 = new JTextField();
		JButton button = new JButton("Spara");
		frame.setLayout(new GridLayout(0,2,1,1));
		frame.getContentPane().setBackground(black);
		label.setOpaque(true);
		label2.setOpaque(true);
		label3.setOpaque(true);
		label4.setOpaque(true);
		label5.setOpaque(true);
		frame.add(label);
		frame.add(field);
		frame.add(label2);
		frame.add(field2);
		frame.add(label3);
		frame.add(field3);
		frame.add(label4);
		frame.add(field4);
		frame.add(label5);
		frame.add(button);
		frame.pack();
		frame.setVisible(true);
		button.addActionListener(e -> {new Fond(field.getText(), field2.getText(), Integer.parseInt(field3.getText()), Double.parseDouble(field4.getText()));frame.dispose();});
	}
	private void spara(){
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
			objectOut.writeObject(arrayList);
			prop.setProperty("Fonder", new String(BASE64EncoderStream.encode(byteOut.toByteArray())));
			sparaProp("Fond");
			System.out.println("Sparat");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	class Fond implements Serializable{
		private static final long serialVersionUID = 6413880318626101061L;
		private String name,ID;
		private int belopp;
		private double andelar;
		private ArrayList<Fondkurs> kurser;

		public Fond(String name, String ID, int belopp, double andelar) {
			this.name = name;
			this.ID = ID;
			this.belopp = belopp;
			this.andelar = andelar;
			kurser = new ArrayList<Fondkurs>();
			arrayList.add(this);
			spara();
		}
		@Override
		public String toString() {
			return name; 
		}
		public int getBelopp() {
			return belopp;
		}
		public double getAndelar(){
			return andelar;
		}
		public String getID() {
			return ID;
		}
		public int getKursLängd(){
			return kurser.size();
		}
		public Fondkurs getFondkurs(int i){
			return kurser.get(i);
		}
		public void addKurs(int index,String datum,double kurs){
			System.err.println(index+", "+datum+", "+kurs);
			Fondkurs fondkurs = new Fondkurs(datum, kurs,this);
			kurser.add(index,fondkurs);
		}
		public void hämta(){
			String startdatum;
			Scanner scanner;
			try {
				try {
					startdatum=kurser.get(0).getDate();
				} catch (Exception e) {
					e.printStackTrace();
					System.err.println(521);
					startdatum="2012-01-01";
				}
				System.err.println(startdatum);
				scanner = new Scanner(getID());
				Document doc = Jsoup.parse(new URL("http://web.msse.se/shb/sv/history/onefund.print?company="+scanner.next()+"&fundid="+scanner.next()+"&startdate="+startdatum), 10000);
				Elements elements = doc.select("td:last-child"), 
						elements2 = doc.select("td.positive");

				for (int i = 0; i < elements.size()-1; i++) {
					Element element = elements.get(i);
					Element element2 = elements2.get(i);
					addKurs(i, element.html(), Double.parseDouble(element2.html().replace(",", ".")));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			spara();

		}
	}
	class Fondkurs implements Serializable{
		private static final long serialVersionUID = 14565464644654646L;
		private String date;
		private double kurs;
		private long ökn;
		private double öknprocent;

		Fondkurs(String date, double kurs, Fond fond){
			this.date = date;
			this.kurs = kurs;
			ökn=Math.round(kurs*fond.getAndelar()-fond.getBelopp());
			öknprocent=Math.round(ökn*10000d/fond.getBelopp())/100d;
		}
		public String getDate() {
			return date;
		}
		public double getKurs() {
			return kurs;
		}
		public long getÖkning() {
			return ökn;
		}
		public double getProcentuellÖkning() {
			return öknprocent;
		}
	}
	public static void main(String[] args) {
		GoJbGoodies.main("gojb.FondKoll");
	}

}