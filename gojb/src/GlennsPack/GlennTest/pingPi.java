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

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * Created by glenn on 2017-07-18.
 */
public class pingPi {
	Mottagare[] mottagare = {new Mottagare("84.217.30.33","jakob_b99@live.se")};
	static long v�ntaMinuter = 1;
	
	public static void main(String[] args) {
		boolean shallRun = true;
		while (shallRun){
			try{
				Thread.sleep(v�ntaMinuter*60*1000);
				new pingPi();
			}
			catch (Exception e){
				shallRun=false;
				e.printStackTrace();
			}
		}
	}
	
	public pingPi(){
		for(int i = 0; i < mottagare.length; i++){
			pingSpecifikIp(mottagare[i]);
		}
	}
	
	public void pingSpecifikIp(Mottagare mottagare) {
		try {
			Process proc = Runtime.getRuntime().exec("ping -c 1 -W 1 " + mottagare.getIpAdress());
			BufferedReader reader =
					new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String line = "", lineOfContent = "";
			while ((line = reader.readLine()) != null) {
				if(line.contains("1 packets transmitted")) {
					lineOfContent = line;
				}
			}
			proc.waitFor();
			
			mottagare.setInneh�llISvar(lineOfContent);
			
			if(mottagare.�rNere()) {
				�rNere(mottagare);
			} else {
				�rUppe(mottagare);
			}
		}
		catch(Exception e){
			//TODO: Handle exception
			e.printStackTrace();
		}
	}
	
	public void �rNere(Mottagare mottagare){
		String lineOfContent = mottagare.getInneh�llISvar();
		
		if(lineOfContent.equals("")){
			//WHY THOUGH?!
		}
		
		if(lineOfContent.contains("1 recevied")){
			//Fick tillbaka ping = uppe
			skickaMail(mottagare);
			mottagare.setNere(false);
		}
	}
	
	public void �rUppe(Mottagare mottagare){
		String lineOfContent = mottagare.getInneh�llISvar();
		
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
			
			System.err.println("P�b�rjar att skicka mail. Ligger nere? "+mottagare.liggerNere);
			
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
				msg.setText("Du kommer att meddelas n�r den �r uppkopplad igen");
			}
			else{
				msg.setSubject("Din server med ip adressen " + ip + " �r �ter uppe nu");
				msg.setText("Den l�g nere i "+mottagare.getTidNere() + " minuter");
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
	String inneh�llISvar;
	boolean liggerNere;
	long millisekunder;
	
	
	public Mottagare(String ipAdress, String mailAdress){
	
	}
	
	public boolean �rNere(){
		return this.liggerNere;
	}
	
	public void setNere(boolean �rNere){
		this.liggerNere=�rNere;
		if(�rNere)
			millisekunder=System.currentTimeMillis();
	}
	
	public String getIpAdress(){
		return this.ipAdress;
	}
	
	public String getMailAdress(){
		return this.mailAdress;
	}
	
	public void setInneh�llISvar(String inneh�ll){
		this.inneh�llISvar=inneh�ll;
	}
	
	public String getInneh�llISvar(){
		return this.inneh�llISvar;
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










