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

package GlennsPack.GlennTest.test;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Menti {

	public static void main(String[] args) {
		for (int i = 0; i < 1; i++) {
			new Thread("" + i) {
				public void run() {
					WebDriver driver = startMenti();
					for (int j = 0; j < 1; j++) {
						vote(driver);
						System.out.println(j);
					}
				}

			}.start();
		}
	}

	public static WebDriver startMenti(){
		System.setProperty("webdriver.chrome.driver", "/Users/glenn/Downloads/chromedriver_latest");
		WebDriver driver = new ChromeDriver();

		driver.manage().window().setSize(new Dimension(1000, 800));

		return driver;
	}

	static void vote(WebDriver driver){
		try{
			driver.manage().deleteAllCookies();
			driver.get("https://www.menti.com/7t5ybmracf"); //8927948
			//CSS Selector of <span> of radio button. This is 5th option
			driver.findElement(By.xpath("//*[@id=\"app\"]/div/div[2]/div[1]/form/fieldset/div/textarea"))
					.sendKeys("BAJS");
			
			
			//Submit button, should be same
			//driver.findElement(By.cssSelector("#app > div > div.m-a.m-ai.m-j.m-c.m-d.m-l.m-f.m-aj.m-ak.m-al.m-h.m-n.r-box > div.m-a.m-c.m-d.m-l.m-f.m-g.m-h.m-n.r-box > form > div:nth-child(3) > button")).click();
			Thread.sleep(500);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

}
