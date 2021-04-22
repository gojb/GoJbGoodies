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

package GlennsPack.GlennTest;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by glenn on 2017-07-18.
 */
public class PingPi {
	Mottagare[] mottagare = {new Mottagare("84.217.30.33","jakob_b99@live.se"), new Mottagare("84.55.99.94","glennholsson@gmail.com")};
	static long vantaMinuter = 1;
	
	public static void main(String[] args) {
		boolean shallRun = true;
		while (shallRun){
			try{
				System.out.println("Running in 10 sec");
				Thread.sleep(10000);
				new PingPi();
			}
			catch (Exception e){
				shallRun=false;
				e.printStackTrace();
			}
		}
	}
	
	public PingPi(){
		for(int i = 0; i < mottagare.length; i++){
			pingSpecifikIp(mottagare[i]);
		}
	}
	
	public void pingSpecifikIp(Mottagare mottagare) {
		try {
			Process proc = Runtime.getRuntime().exec("ping -c 1 -W 1 " + mottagare.getIpAdress());
			proc.waitFor();
			
			BufferedReader reader =
					new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = "", lineOfContent = "";
			while ((line = reader.readLine()) != null) {
				System.out.println("-"+line+"-");
				if(line.contains("1 packets transmitted")) {
					System.out.println(line);
					lineOfContent = line;
				}
			}
			
			
			
			mottagare.setInnehallISvar(lineOfContent);
			
			System.out.println(lineOfContent);
			
			if(mottagare.arNere()) {
				arNere(mottagare);
			} else {
				arUppe(mottagare);
			}
		}
		catch(Exception e){
			//TODO: Handle exception
			e.printStackTrace();
		}
	}
	
	public void arNere(Mottagare mottagare){
		String lineOfContent = mottagare.getInnehallISvar();
		
		if(lineOfContent.equals("")){
			//WHY THOUGH?!
		}
		
		if(lineOfContent.contains("1 recevied")){
			//Fick tillbaka ping = uppe
			System.out.println("Uppe igen!");
			skickaMail(mottagare);
			mottagare.setNere(false);
		}
	}
	
	public void arUppe(Mottagare mottagare){
		String lineOfContent = mottagare.getInnehallISvar();
		
		System.out.println("UPPE fortfarande");
		
		if(lineOfContent.equals("")){
			//WHY THOUGH?!
		}
		
		if(lineOfContent.contains("100%")){
			//100% packet loss
			skickaMail(mottagare);
			mottagare.setNere(true);
		}
		
	}
	
	public static void skickaMail(Mottagare mottagare){
		try {
			String mottagaresMail = mottagare.getMailAdress();
			String ip = mottagare.getIpAdress();
			
			System.err.println("Paborjar att skicka mail. Ligger nere? "+mottagare.liggerNere);
			
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "587");
			
			Session mailSession = Session.getInstance(props, new Authenticator() {
				
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication("gojbmail@gmail.com", "uggen0684");
				}
			});
			
			mailSession.setDebug(false); // Enable the debug mode
			
			Message msg = new MimeMessage(mailSession);
			
			msg.setFrom(new InternetAddress("GoJb<gojbmail@gmail.com>"));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mottagaresMail));
			
			if(mottagare.liggerNere) {
				msg.setSubject("Din server med ip adressen " + ip + " ligger just nu nere.");
				msg.setText("Du kommer att meddelas nar den ar uppkopplad igen");
			}
			else{
				msg.setSubject("Din server med ip adressen " + ip + " ar ater uppe nu");
				msg.setText("Den lag nere i "+mottagare.getTidNere() + " minuter");
			}
			
			Transport.send(msg);
			
			System.err.println("---SKICKAT---");
		}
		catch (Exception e){
			//TODO: Handle exception
			e.printStackTrace();
		}
	}
	
}

class Mottagare{
	String ipAdress;
	String mailAdress;
	String innehallISvar;
	boolean liggerNere;
	long millisekunder;
	
	
	public Mottagare(String ipAdress, String mailAdress){
	
	}
	
	public boolean arNere(){
		return this.liggerNere;
	}
	
	public void setNere(boolean arNere){
		this.liggerNere=arNere;
		if(arNere)
			millisekunder=System.currentTimeMillis();
	}
	
	public String getIpAdress(){
		return this.ipAdress;
	}
	
	public String getMailAdress(){
		return this.mailAdress;
	}
	
	public void setInnehallISvar(String innehall){
		this.innehallISvar=innehall;
	}
	
	public String getInnehallISvar(){
		return this.innehallISvar;
	}
	
	public String getTidNere(){
		String tidNere="";
		long currentMillis= System.currentTimeMillis();
		
		long diffrenceInMs=currentMillis-millisekunder;
		double differenceInMinutes = ((double)diffrenceInMs/1000d)/60d;
		
		tidNere=Double.toString(differenceInMinutes);
		
		tidNere=Character.toString(tidNere.charAt(tidNere.indexOf(".")+1)).equals("0")?tidNere.substring(0,2):tidNere.substring(0, tidNere.indexOf(".")+2);
		
		return tidNere;
	}
}