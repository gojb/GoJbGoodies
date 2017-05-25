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
			System.err.println("Söker adresser för: " + InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Document doc = Jsoup.parse(new URL("http://findipinfo.com"),10000);
			Element element = doc.select("h1").get(0);
			System.out.println();
			System.out.println("Extern ip: "+element.ownText().substring(20));
			String[] strings=InetAddress.getLocalHost().toString().split("/");
			System.out.println("Intern ip: "+strings[strings.length-1]);
			
			
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
