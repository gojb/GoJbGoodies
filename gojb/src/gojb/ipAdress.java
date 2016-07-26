package gojb;
import java.io.IOException;
import java.net.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;



public class ipAdress {

	String ip;
	
	public static void main(String[] args) {
		// FIXME Auto-generated method stub
			new ipAdress();
	}
	public ipAdress() {
		// FIXME Auto-generated constructor stub
		
		try {
			System.err.println(InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Document doc = Jsoup.parse(new URL("http://findipinfo.com"),10000);
			Element element = doc.select("h1").get(0);
			System.out.println(element.ownText().substring(20));
			System.out.println(InetAddress.getLocalHost().toString().substring(13));
			
			
//			for(int i =element.ownText().length();element.ownText().charAt(i)==" ".charAt(0);i--){
//				
//			}
			
		} catch (MalformedURLException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}
	}

}
