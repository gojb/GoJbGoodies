package GlennsPack.GlennTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.jsoup.*;
import org.jsoup.nodes.*;

public class XMLcheck {

	static Properties lastXML = new Properties();
	static Properties currentXML = new Properties();

	enum documentType {last, current};

	public static void main(String[] args) {
		// FIXME Auto-generated method stub
//		new XMLcheck("http://forums.ubi.com/showthread.php/1574625", "forums");
//		new XMLcheck("http://twitter.com", "KakansBotTwitt");
		new XMLcheck("https://www.reddit.com/r/Rainbow6/comments/5ttm3q/updated_known_issues_13feb2017", "Reddit");

	}
	public XMLcheck(String website, String filnamn){
		Document currentXMLDoc1=null;
		String currentXMLDoc;
		try {
			currentXMLDoc1 = Jsoup.parse(new URL(website), 10000);
			//			System.out.println(currentXML);
		} catch (Exception e) {
			e.printStackTrace();
		}
		currentXMLDoc=currentXMLDoc1.toString();

//		lastXML.setProperty("XML", currentXMLDoc);
		while(currentXMLDoc.toLowerCase().contains("time")){
			currentXMLDoc=currentXMLDoc.substring(0,currentXMLDoc.toLowerCase().indexOf("time"))+
					currentXMLDoc.substring(currentXMLDoc.toLowerCase().indexOf("time")+20);
		}
//		currentXML.setProperty("XML", currentXMLDoc);
		
//		sparaProp(lastXML, filnamn, website, documentType.last);
//		sparaProp(currentXML, filnamn, website, documentType.current);
//
//		if(true){
//			return;
//		}
		System.out.println(filnamn);

		Object object1 = laddaProp(XMLcheck.currentXML, filnamn, website, documentType.current);
		currentXML.setProperty("XML", currentXMLDoc.toString());
		currentXML.setProperty("Length", Integer.toString(currentXMLDoc.toString().length()));
		sparaProp(currentXML, filnamn, website, documentType.current);

		Object object = laddaProp(lastXML, filnamn, website, documentType.last);

		if(!lastXML.isEmpty()){
			if(currentXML.getProperty("XML").toString().toLowerCase().equals(lastXML.getProperty("XML").toString().toLowerCase())){

				System.err.println("WAZZAAAAAA");

			}
			else{
				int index = 101666;
				System.out.println("NYTT");
				System.out.println(currentXML.getProperty("XML").toString().length() + " = length CURRENT");
				System.out.println(lastXML.getProperty("XML").toString().length() + " = length LAST");
				
//				System.out.println(currentXML.getProperty("XML").toString().substring(0,index).toLowerCase().equals(lastXML.getProperty("XML").toString().substring(0,index).toLowerCase()));
				
			}
		}
		else{
			System.out.println("First entry from this site");
		}
		int index = 101667;
		lastXML.setProperty("XML", currentXML.getProperty("XML").toString());
		lastXML.setProperty("Length", (currentXML.getProperty("Length")));
//		lastXML.setProperty("XML", currentXML.getProperty("XML").toString().substring(0,index));
		sparaProp(lastXML, filnamn, website, documentType.last);
	}

	Object laddaProp(Properties prop, String filnamn, String website, documentType type){
		try {
			if(type==documentType.last){
				prop.load(new FileInputStream(System.getProperty ("user.home") + "\\AppData\\Roaming\\GoJb\\SparaXML\\"+filnamn+"\\"+filnamn+"Last.txt"));
			}
			else if (type==documentType.current){
				prop.load(new FileInputStream(System.getProperty ("user.home") + "\\AppData\\Roaming\\GoJb\\SparaXML\\"+filnamn+"\\"+filnamn+"Current.txt"));
			}
			return "OKAY";
		} catch (Exception e) {
			System.err.println(filnamn+".txt saknas. Skapar...");
			try {
				if(type==documentType.last){
					prop.store(new FileWriter(new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\SparaXML\\"+filnamn+"\\"+filnamn+"Last.txt")),"Sparar XML dokument(last) för "+website);
					prop.load(new FileInputStream(System.getProperty ("user.home") + "\\AppData\\Roaming\\GoJb\\SparaXML\\"+filnamn+"\\"+filnamn+"Last.txt"));
				}
				else if(type==documentType.current){
					prop.store(new FileWriter(new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\SparaXML\\"+filnamn+"\\"+filnamn+"Current.txt")),"Sparar XML dokument(current) för "+website);
					prop.load(new FileInputStream(System.getProperty ("user.home") + "\\AppData\\Roaming\\GoJb\\SparaXML\\"+filnamn+"\\"+filnamn+"Current.txt"));
				}
				return "OKAY";
			} catch (IOException e2) {
				System.err.println("Mappen finns inte! Skapar...");
				new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\SparaXML\\"+filnamn).mkdir();
				return laddaProp(prop, filnamn, website, type);
			}
		}
	}

	void sparaProp(Properties prop, String filnamn, String website, documentType type){
		try {
			if(type==documentType.last){
				prop.store(new FileWriter(new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\SparaXML\\"+filnamn+"\\"+filnamn+"Last.txt")),"Sparar XML dokument(last) för "+website);
			}
			else if(type==documentType.current){
				prop.store(new FileWriter(new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\SparaXML\\"+filnamn+"\\"+filnamn+"Current.txt")),"Sparar XML dokument(current) för "+website);
			}
		} catch (Exception e1) {
			System.err.println("Mappen finns inte! Skapar...");
			new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\SparaXML\\"+filnamn).mkdir();
			try {
				if(type==documentType.last){
					prop.store(new FileWriter(new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\SparaXML\\"+filnamn+"\\"+filnamn+"Last.txt")),"Sparar XML dokument(last) för "+website);
				}
				else if(type==documentType.current){
					prop.store(new FileWriter(new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\SparaXML\\"+filnamn+"\\"+filnamn+"Current.txt")),"Sparar XML dokument(current) för "+website);
				}
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}

}
