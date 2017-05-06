package glennsPack.GlennTest;

import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class parseHTML2 {

	public static void main(String[] args) {
		// FIXME Auto-generated method stub

		int randomInt = new Random().nextInt(100);

		System.out.println(randomInt);
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter link to parse in index2: ");
		String url = reader.nextLine();
		try {
			Document document = Jsoup.connect(url).userAgent("Chrome").get();
			String doc=document.toString().replace("reddit: the front page of the internet", "glenndit: because fuck jensens blacklist");

			String path=null;
			if(System.getProperty("os.name").toLowerCase().contains("windows")){
				path="C:\\Users\\Glenn\\Desktop\\Newww.html";
			}
			else if (System.getProperty("os.name").toLowerCase().contains("linux")) {
				path="/var/lib/tomcat7/webapps/ROOT/Website-Pi/html/index/"+randomInt+".html";
				System.out.println("LINUX");
			}

			try(FileWriter file = new FileWriter(path)){
				file.write(doc.toString());
				System.out.println("84.55.99.94:8080/Website-Pi/html/index/index"+randomInt+".html");

			} catch (Exception e) {
				// FIXME: handle exception
				e.printStackTrace();
			}

		} catch (Exception e) {
			// FIXME: handle exception
			System.err.println("Not valid url, try again");
			parseHTML2.main(null);
			return;
		}


	}

}
