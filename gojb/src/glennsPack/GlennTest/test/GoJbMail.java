package glennsPack.GlennTest.test;

import java.awt.*;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;

class GoJbMail{

	static String reciver = new String("glennholsson@gmail.com"), subject = new String("Yoyo"), message = new String("Hey"),
			user = new String("gojbmail@gmail.com"), password = new String("uggen0684");

	public static void main(String[] args) {
		new GoJbMail();
	}

	public GoJbMail() {
		System.err.println("kjgu.");

		Starta("hämta");
		//	Starta("skicka");

	}
	public static void Starta(String starta){		

		if(starta.toLowerCase().equals("hämta")){
			System.err.println("Hämta");
			HämtaMail.main(null);
		}
		else if(starta.toLowerCase().equals("skicka")){
			System.err.println("Skicka");

			//			new SkickaMail();

			try {
				SkickaMail.Skicka(reciver,subject,message);
			} catch (AddressException e) {
				e.printStackTrace();
				System.err.println("--Fel på Adress!!");
			} catch (MessagingException e) {
				System.err.println("--Fel på Meddelande");
				e.printStackTrace();
			}

		}
		else if(!starta.toLowerCase().equals("skicka")||!starta.toLowerCase().equals("hämta")) {
			System.err.println("---ERROR---");
		}
	}
}


class SkickaMail {

	public static void main(String[] args) throws Exception {
		System.err.println("Main");
	}

	public static void Skicka(String Till, String Ämne, String Meddelande) throws AddressException, MessagingException{

		System.err.println("Påbörjat");

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");

		Session mailSession = Session.getInstance(props, new Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(GoJbMail.user, GoJbMail.password);
			}
		});


		mailSession.setDebug(false); // Enable the debug mode

		Message msg = new MimeMessage( mailSession );

		System.out.println(Till +"--" + Ämne +"--"+ Meddelande);

		msg.setFrom(new InternetAddress( "GoJb<gojbmail@gmail.com>" ) );
		msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(Till));	
		msg.setSubject(Ämne);
		msg.setText(Meddelande);

		Transport.send( msg );

		System.err.println("---SKICKAT---");

	}

}

class HämtaMail implements Runnable{

	JFrame frame = new JFrame();

	JPanel panel = new JPanel();

	Boolean deleteBoolean = false;

	JFrame f = new JFrame();

	int x;

	JMenuBar bar = new JMenuBar();

	JScrollPane scrollBar = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

	public static void main(String[] args) {
		System.err.println("uvueshjd");

		new Thread(new HämtaMail()).start();
	}

	public void Kör() {

		System.err.println("Ktesgdhjklxfcg");
	}

	public void kärna(){

		frame.setVisible(true);
		frame.setSize(500,500);
		frame.setLocationRelativeTo(null);
		frame.add(scrollBar);
		frame.setJMenuBar(bar);
		frame.setDefaultCloseOperation(3);

		f.setUndecorated(true);

		System.out.println("Funkar");

		scrollBar.getVerticalScrollBar().setUnitIncrement(20);

		panel.setLayout(new GridLayout(0,1));

		frame.revalidate();
		frame.repaint();
	}

	@Override
	public void run(){
		kärna();
		Session session = Session.getDefaultInstance(System.getProperties());
		System.out.println("Funkar");
		try {
			session.setDebug(false); // Enable the debug mode
			Store store = session.getStore("imaps");
			store.connect("imap.googlemail.com", GoJbMail.user, GoJbMail.password);

			Folder folder = store.getFolder("Inbox");
			folder.open(Folder.READ_WRITE);
			Message[] msgs = folder.getMessages();

			for (int j = msgs.length-1; j > 0; j--) {
				if(j>10){
					x=1;
				}
				Message msg = msgs[j];

				if(!msg.isSet(Flags.Flag.DELETED)){				
					if(msg.isSet(Flags.Flag.SEEN)==true){
						System.out.println("SEEN");	
						LäggTill(msg,Color.red);
					}
					else if(msg.isSet(Flags.Flag.SEEN)==false){
						System.out.println("--Not SEEN");
						LäggTill(msg,Color.black);
					}
				}
			}

		}catch(Exception e)    {
			e.printStackTrace();
		}
	}
	public void LäggTill(Message message,Color color){
		JButton b = new JButton(),
				delete = new JButton("Delete"),
				reply = new JButton("Reply"),
				mark = new JButton("Mark as unread"),
				GoJb = new JButton("GoJb");

		JMenuBar bar = new JMenuBar();

		delete.setSize(100,20);
		GoJb.setSize(150,20);
		GoJb.setBackground(Color.blue);

		try {
			if(message.getSubject().equals("--Bugg/idéer--")){
				bar.add(GoJb);
			}
		} catch (MessagingException e2) {
			e2.printStackTrace();
		}

		delete.addActionListener(e -> {try {
			message.setFlag(Flags.Flag.DELETED, true);
			b.setForeground(Color.magenta);
			f.dispose();
		} catch (Exception e1) {
			e1.printStackTrace();
		}});

		reply.setSize(100, 20);
		mark.setSize(100,20);

		mark.addActionListener(e -> {
			try {
				message.setFlag(Flags.Flag.SEEN, false);
				b.setForeground(Color.black);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		b.setForeground(color);

		panel.add(b);

		b.addActionListener(e -> {JTextArea text = null;
		try {
			text = new JTextArea("-- From: "+ message.getFrom()[0].toString() + "\n \n--Subject: " + message.getSubject() + "\n \n--Content: \n" + 
					message.getContent().toString());
		} catch (Exception e1) {

			e1.printStackTrace();
		}f.add(text);f.setLocation(frame.getX()-500, frame.getY());
		text.setEditable(false);
		f.setVisible(true);
		f.setSize(500, 500);
		f.setJMenuBar(bar);
		text.setLineWrap(true);
		text.setWrapStyleWord(true);
		bar.add(delete);
		bar.add(reply);
		bar.add(mark);
		try {
			message.setFlag(Flags.Flag.SEEN, true);
		} catch (Exception e1) {
			e1.printStackTrace();
		}b.setForeground(Color.red);});

		b.setPreferredSize(new Dimension(500,100));

		try {
			b.setText(message.getSubject());
		} catch (MessagingException e) {
		}


		frame.revalidate();

	}
}


