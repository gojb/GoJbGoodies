package glennsPack.GlennWebAPI;

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
