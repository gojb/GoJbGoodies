package glennsPack.GlennWebAPI;

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
		System.setProperty("webdriver.chrome.driver", "C:/Users/Glenn/Downloads/chromedriver/chromedriver.exe");
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
		for(int i = 0;i<1000000;i++){
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
