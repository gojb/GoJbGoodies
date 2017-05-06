package glennsPack.GlennTest;

import java.io.*;
import org.jsoup.Jsoup;


public class parseHTML{
public static void main(String args[]){

			String url = "https://www.reddit.com/";
		org.jsoup.nodes.Document document=null;
		try {

			document = Jsoup.connect(url).userAgent("Chrome").get();
			
			String doc=document.toString().replace("reddit: the front page of the internet", "glenndit: because fuck jensens blacklist");
			
			doc=doc.split("<body class=\"listing-page hot-page front-page\">")[0]+
					"<body class=\"listing-page hot-page front-page\"><button onclick=\"window.open('/index2.html')\">To index2.html</button>"+doc.split("<body class=\"listing-page hot-page front-page\">")[1];
			
			//Write the html
			String path=null;
			
			if(System.getProperty("os.name").toLowerCase().contains("windows")){
				path="C:\\Users\\Glenn\\Desktop\\New.html";
			}
			else if (System.getProperty("os.name").toLowerCase().contains("linux")) {
				path="/var/lib/tomcat7/webapps/ROOT/index.html";
				System.out.println("LINUX");
			}
				try(FileWriter file = new FileWriter(path)){
					file.write(doc.toString());
				}
			} catch (Exception e) {
				// FIXME: handle exception
				e.printStackTrace();
			}
}

}