package gojb;

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


import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;


public class Börsrobot  {
	private String lösen;
	public static void main(String[] args) {
		new Börsrobot();
	}
	public Börsrobot() {
		lösen=JOptionPane.showInputDialog("Skriv in lösenordet");


		openAvanza();
		//		twitterSpam();
		//		gojbSpam();

	}


	public void openAvanza() {
		System.setProperty("webdriver.chrome.driver", "C:/Users/Jakob/Downloads/chromedriver_win32/chromedriver.exe");

		WebDriver driver = new ChromeDriver();
		driver.get("https://avanza.se/start/forsta-oinloggad.html");
		WebElement element;
		driver.findElement(By.ByLinkText.linkText("Användarnamn & lösenord")).click();
		driver.findElements(By.name("j_username")).get(1).sendKeys("jakobbjorns");
		element = driver.findElements(By.name("j_password")).get(1);
		element.sendKeys(lösen);
		element.submit();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}
		driver.get("https://avanza.se/index/om-indexet.html/19002/omx-stockholm-30");
		driver.findElement(By.ByLinkText.linkText("TA")).click();
		driver.findElement(By.ByLinkText.linkText("SMA1020")).click();
		//		try {
		//			Thread.sleep(5000);
		//		} catch (InterruptedException e) {
		//			// FIXME Auto-generated catch block
		//			e.printStackTrace();
		//		}  // Let the user actually see something!
		//		driver.quit();
	}
}
