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
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.interactions.Actions;

import java.util.Random;

public class Bot2048 {

	public static void main(String[] args) {
		// FIXME Auto-generated method stub

		new Bot2048();
		
	}
	public Bot2048(){
		System.setProperty("webdriver.chrome.driver", "/Users/glenn/Downloads/chromedriver_latest");
		WebDriver driver = new ChromeDriver();

		driver.manage().window().setSize(new Dimension(2000, 800));
		
		driver.get("http://annimon.github.io/16384/");
		
//		try {
//			Thread.sleep(2000);
//		} catch (Exception e) {
//			// FIXME: handle exception
//		}
//		
		WebElement element= driver.findElement(By.xpath("/html/body/div"));
//				("/html/body/div/div[2]"));
		
		Actions actions = new Actions(driver);
		actions.moveToElement(element);
		actions.click();
		CharSequence[] array = {Keys.ARROW_DOWN,Keys.ARROW_LEFT,Keys.ARROW_RIGHT};
		for(;;){
		Random random = new Random();
		actions.sendKeys(array[random.nextInt(3)]);;
		actions.sendKeys(Keys.ARROW_DOWN);
		actions.build().perform();		
		try {
//			Thread.sleep(1);
		} catch (Exception e) {
			// FIXME: handle exception
		}
		}
	}
}
