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

package GlennsPack.GlennWebAPI;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;


public class findTextInPage {

	boolean found;
	int missmatch;
	ArrayList<String> mathingsFound = new ArrayList<String>();
	static ArrayList<String> whatToLookFor = new ArrayList<String>();

	String name = new String();

	public static void main(String[] args) {

		whatToLookFor.add("bulle");

		whatToLookFor.add("har anslutit.");
		//		whatToLookFor.add("");
		//		whatToLookFor.add("");
		//		whatToLookFor.add("");
		//		whatToLookFor.add("");
		//		whatToLookFor.add("");
		//		whatToLookFor.add("");
		//		whatToLookFor.add("");
		//		whatToLookFor.add("");
		//		whatToLookFor.add("");
		//		whatToLookFor.add("");

		new findTextInPage(whatToLookFor);
	}
	public findTextInPage(ArrayList<String> lookFor) {
		System.setProperty("webdriver.chrome.driver", "C:/Users/Glenn/Downloads/chromedriver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		driver.manage().window().setSize(new Dimension(2000, 800));

		driver.get("http://gojb.ml/chat");

		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			// FIXME Auto-generated catch block
			e1.printStackTrace();
		}
		driver.findElement(By.xpath("//*[@id=\"menu\"]/header/div[1]")).click();
		driver.findElement(By.id("knapp")).click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}
		driver.switchTo().alert().sendKeys("GoJbBot");
		driver.switchTo().alert().accept();
		driver.findElement(By.id("knapp2")).click();



		while(found!=true){
			try {
				for(int index = 0; index < lookFor.size();index++){
					List<WebElement> element = driver.findElements(By.xpath("//*[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ',"
							+ "'abcdefghijklmnopqrstuvwxyzåäö'),\""+lookFor.get(index).toLowerCase()+"\")]"));

					for(int i = 0;i<element.size();i++){
						
						if(!mathingsFound.contains(element.get(i).getText())){
							mathingsFound.add(element.get(i).getText());
							System.out.println("FOUND!");
							Found(lookFor.get(index).toLowerCase(),element.get(i).getText(), driver);
						}
						else{
							missmatch++;
							if((missmatch*lookFor.size())==element.size()){
								System.out.println("OR ELSE");
								throw new Exception();
							}
						}
					}
				}
			} catch (Exception e2) {
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// FIXME Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

	public void Found(String textOfElemnt, String totalText, WebDriver driver){

		if(textOfElemnt=="har anslutit"){
			if(totalText.charAt(0)==("*").charAt(0)){
			name=totalText.substring(1);
			name = name.split(" ")[0];
			driver.findElement(By.id("chat")).sendKeys("Välkommen "+name,Keys.ENTER);
		}
		}
	}
}
