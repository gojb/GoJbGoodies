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