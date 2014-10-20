package GoJbsBraOchHa;

import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;

class GoJbMail{
	
	String Mottagare, �mne, inneh�llString;
	
	public static void Starta(String starta){		
		
		if(starta.toLowerCase().equals("h�mta")){
			System.err.println("H�mta");
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

		System.out.println(Mouse.Mottagare+"--"+ Mouse.�mne + "---" + Mouse.Inneh�ll);
		
		msg.setFrom( new InternetAddress( "GoJb<gojb@gojb.bl.ee>" ) );
		msg.setRecipients(Message.RecipientType.TO,InternetAddress.parse(Mouse.Mottagare));	
		msg.setSubject(Mouse.�mne);
		msg.setText(Mouse.Inneh�ll);


		Transport.send( msg );

		System.err.println("---SKICKAT---");
		
	}

}

class H�mtaMail implements Runnable{

	public static void main(String[] args) {
		new Thread(new H�mtaMail()).start();
	}
	@Override
	public void run(){
		Session session = Session.getDefaultInstance(System.getProperties());
		System.out.println("Funkar");
		try {
			Store store = session.getStore("imap");
			store.connect("mx1.hostinger.se", "gojb@gojb.bl.ee", "uggen0684");

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


