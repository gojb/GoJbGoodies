package GoJbsBraOchHa;

import java.awt.GridLayout;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JFrame;
import javax.swing.JLabel;

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

	int x;
	
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
			frame.setLayout(new GridLayout(0,3));
			
			System.out.println("Funkar");

			Folder folder = store.getFolder("Inbox");
			folder.open(Folder.READ_WRITE);
			Message[] msgs = folder.getMessages();

			for (int j = msgs.length-1; j > 0; j--) {
				if(j>10){
					x=1;
				}
				Message msg = msgs[j];
				
				Date date = new Date();
				date = msg.getReceivedDate();
				System.out.println(date.getTime());
				
				frame.add(new JLabel(Integer.toString(j)));
				
				System.err.println("lerj");
				if(msg.isSet(Flags.Flag.SEEN)){
					System.out.println("SEEN");	
				}
				
			}

		}catch(Exception e)    {
			e.printStackTrace();
		}
	}
}


