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
