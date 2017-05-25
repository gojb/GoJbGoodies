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

import javax.swing.*;
import java.util.Random;


public class Example  {

	Random rand = new Random();
	String abc = new String("abcdefghijklmnopqrstuvwxyzåäö"), str = new String(), string = new String();
	int x;
	long millisAtStart, lastMillis;

	Timer timer;
	boolean found;


	public static void main(String[] args) {
		new Example();
	}
	public Example() {



		testGoogleSearch();
		//		twitterSpam();
		//		gojbSpam();
		
	}


	public void gojbSpam() {
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
		driver.findElement(By.xpath("//*[@id=\"menu\"]/div/ul/li[1]/div/p")).click();;
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
		for(int i = 0;i < 1000; i++){
			str="";
			for(int i1 = 0; i1<10;i1++){
				str=str+abc.charAt(rand.nextInt(abc.length()));
			}
			driver.findElement(By.id("chat")).sendKeys(str,Keys.ENTER);
		}
	}
	public void twitterSpam(){
		System.setProperty("webdriver.chrome.driver", "C:/Users/Glenn/Downloads/chromedriver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		driver.manage().window().setSize(new Dimension(2000, 800));

		driver.get("https://twitter.com/login");


		driver.findElement(By.xpath("//*[@id=\"page-container\"]/div/div[1]/form/fieldset/div[1]/input")).sendKeys("kakansbot");
		driver.findElement(By.xpath("//*[@id=\"page-container\"]/div/div[1]/form/fieldset/div[2]/input")).sendKeys("falcor1122");
		driver.findElement(By.xpath("//*[@id=\"page-container\"]/div/div[1]/form/div[2]/button")).click();

		millisAtStart=System.currentTimeMillis();
		try {


			for(int i = 0;i < 100; i++){
				str="";
				for(int i1 = 0; i1<10;i1++){
					str=str+abc.charAt(rand.nextInt(abc.length()));
				}

				driver.findElement(By.xpath("//*[@id=\"global-new-tweet-button\"]")).click();
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// FIXME Auto-generated catch block
					e.printStackTrace();
				}
				driver.findElement(By.id("tweet-box-global")).sendKeys(str);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// FIXME Auto-generated catch block
					e.printStackTrace();
				}
				driver.findElement(By.xpath("//*[@id=\"global-tweet-dialog-dialog\"]/div[2]/div[4]/form/div[2]/div[2]/button")).click();
				x++;
				System.out.println("Nu har "+x+" tweets skickats, "+ (int)((System.currentTimeMillis()-millisAtStart)/1000)+" sekunder efter start");
				if(lastMillis>0){
					System.out.println("Senaste tweetet skickades för "+(int)((System.currentTimeMillis()-lastMillis)/1000)+" sekunder sen");
				}
				lastMillis=System.currentTimeMillis();
				System.err.println("");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// FIXME Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			driver.close();
			twitterSpam();
		}
		driver.close();
	}
	public static void testGoogleSearch() {
		// Optional, if not specified, WebDriver will search your path for chromedriver.
		System.setProperty("webdriver.chrome.driver", "C:/Users/Glenn/Downloads/chromedriver/chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		driver.get("http://whatismybrowser.com");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}  // Let the user actually see something!
		WebElement searchBox = driver.findElement(By.name("q"));
		searchBox.sendKeys("ChromeDriver");
		searchBox.submit();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}  // Let the user actually see something!
		driver.quit();
	}
}