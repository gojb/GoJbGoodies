package GoJbsBraOchHa;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JFrame;

class GoJbMail{
	
	String Mottagare, �mne, inneh�llString;
	
	public static void Starta(String starta){		
		
		if(starta.toLowerCase().equals("h�mta")){
			System.err.println("H�mta");
			H�mtaMail.main(null);
		}
		else if(starta.toLowerCase().equals("skicka")){
			System.err.println("Skicka");
			
			new SkickaMail();
			try {
				SkickaMail.Skicka(null, null, null);
			} catch (AddressException e) {
				// FIXME Auto-generated catch block
				e.printStackTrace();
			} catch (MessagingException e) {
				// FIXME Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(!starta.toLowerCase().equals("skicka")||!starta.toLowerCase().equals("h�mta")) {
			System.err.println("---ERROR---");
		}
	}
}


class SkickaMail {

	public static void main(String[] args) throws Exception {
		System.err.println("Main");
	}

	public static void Skicka(String Till, String �mne, String Meddelande) throws AddressException, MessagingException{

		System.err.println("P�b�rjat");

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

		System.out.println(Mailkorg.label1.getText().toLowerCase() +"--"+ Mailkorg.label2.getText().toLowerCase() +"--"+ Mailkorg.label3.getText().toLowerCase());
		
		
		
		
		msg.setFrom( new InternetAddress( "GoJb<gojb@gojb.bl.ee>" ) );
		msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(Mailkorg.Mottagare.getText().toLowerCase()));	
		msg.setSubject(Mailkorg.�mne.getText());
		msg.setText(Mailkorg.Inneh�ll.getText());


		Transport.send( msg );

		System.err.println("---SKICKAT---");
		
	}

}

class H�mtaMail implements Runnable{

	JFrame frame;
	
	public static void main(String[] args) {
		System.err.println("uvueshjd");
		new Thread(new H�mtaMail()).start();
	}
	
	public void K�r() {
		System.err.println("Ktesgdhjklxfcg");
	}
	@Override
	public void run(){
		Session session = Session.getDefaultInstance(System.getProperties());
		System.out.println("Funkar");
		try {
			Store store = session.getStore("imap");
			store.connect("mx1.hostinger.se", "gojb@gojb.bl.ee", "uggen0684");

			frame = new JFrame();
			frame.setVisible(false);
			
			System.out.println("Funkar");

			Folder folder = store.getFolder("Inbox");
			folder.open(Folder.READ_WRITE);
			Message[] msgs = folder.getMessages();

			for (int j = msgs.length-1; j > 0; j--) {
		
				Message msg = msgs[j];
				
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


