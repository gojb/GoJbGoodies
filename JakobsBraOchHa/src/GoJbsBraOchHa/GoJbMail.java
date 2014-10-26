package GoJbsBraOchHa;

import java.awt.*;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;

class GoJbMail{

	String Mottagare, Ämne, innehållString;

	public static void Starta(String starta){		

		if(starta.toLowerCase().equals("hämta")){
			System.err.println("Hämta");
			HämtaMail.main(null);
		}
		else if(starta.toLowerCase().equals("skicka")){
			System.err.println("Skicka");

			new SkickaMail();
			try {
				SkickaMail.Skicka(null, null, null);
			} catch (AddressException e) {

				e.printStackTrace();
			} 
			catch (MessagingException e) {

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
		props.put("mail.smtp.host", "mx1.hostinger.se");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "2525");

		Session mailSession = Session.getInstance(props, new Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("gojb@gojb.bl.ee", "uggen0684");
			}
		});


		mailSession.setDebug(false); // Enable the debug mode

		Message msg = new MimeMessage( mailSession );

		System.out.println(Mailkorg.Mottagare.getText().toLowerCase() +"--"+ Mailkorg.Ämne.getText() +"--"+ Mailkorg.Innehåll.getText());




		msg.setFrom( new InternetAddress( "GoJb<gojb@gojb.bl.ee>" ) );
		msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(Mailkorg.Mottagare.getText().toLowerCase()));	
		msg.setSubject(Mailkorg.Ämne.getText());
		msg.setText(Mailkorg.Innehåll.getText());


		Transport.send( msg );

		System.err.println("---SKICKAT---");

	}

}

class HämtaMail implements Runnable{

	JFrame frame = new JFrame();

	JPanel panel = new JPanel();
	
	int x;

	
	JScrollPane Bar = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
	
	public static void main(String[] args) {
		System.err.println("uvueshjd");
		
		new Thread(new HämtaMail()).start();
	}

	public void Kör() {

		System.err.println("Ktesgdhjklxfcg");
	}
	@Override
	public void run(){
		Session session = Session.getDefaultInstance(System.getProperties());
		System.out.println("Funkar");
		try {
			Store store = session.getStore("imap");
			store.connect("mx1.hostinger.se", "gojb@gojb.bl.ee", "uggen0684");

			frame.setVisible(true);
			frame.setSize(500,500);
			frame.setLocationRelativeTo(null);
			frame.add(Bar);
			
			System.out.println("Funkar");
			
			Bar.getVerticalScrollBar().setUnitIncrement(20);
			
			panel.setLayout(new GridLayout(0,1));
			

			Folder folder = store.getFolder("Inbox");
			folder.open(Folder.READ_WRITE);
			Message[] msgs = folder.getMessages();

			for (int j = msgs.length-1; j > 0; j--) {
				if(j>10){
					x=1;
				}
				Message msg = msgs[j];
				
				if(!msg.isSet(Flags.Flag.DELETED))
				
				
				
				System.err.println("lerj");
				if(msg.isSet(Flags.Flag.SEEN)){
					System.out.println("SEEN");	
					LäggTill(msg,Color.red);
				}
				else{
					LäggTill(msg,Color.black);
				}
				
			}

		}catch(Exception e)    {
			e.printStackTrace();
		}
	}
	public void LäggTill(Message message, Color color){
		JButton b = new JButton();
		
		b.setForeground(color);
		
		panel.add(b);
		
		b.addActionListener(e -> {JFrame f = new JFrame();JTextArea text = null;
		try {
			text = new JTextArea("-- From: "+ message.getFrom()[0].toString() + "\n \n--Subject: " + message.getSubject() + "\n \n--Content: \n" + 
					message.getContent().toString());
		} catch (Exception e1) {

			e1.printStackTrace();
		}f.add(text);text.setEditable(false);f.setVisible(true);f.setSize(500, 500);text.setLineWrap(true);
		text.setWrapStyleWord(true);try {
			message.setFlag(Flags.Flag.DELETED, true);
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


