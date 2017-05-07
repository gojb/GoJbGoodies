package GlennsPack.GlennWebAPI;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class TwitchBot {

	boolean found,foundWord;
	int missmatch,a,charTrue;
	static int amounOfHello, amountOfBye;
	static ArrayList<WebElement> mathingsFound = new ArrayList<WebElement>();
	static ArrayList<String> stringsToLookFor = new ArrayList<String>(), memesToLookFor = new ArrayList<String>();
	String abc = new String("abcdefghijklmnopqrstuvwxyzåäö"), name = new String(), sortOfText = new String();
	Random randFrase = new Random();
	String send;

	public static void main(String[] args) {

		stringsToLookFor.add("Hey");
		stringsToLookFor.add("Hello");
		stringsToLookFor.add("Hi");
		stringsToLookFor.add("Sup");
		stringsToLookFor.add("What's up");
		stringsToLookFor.add("Yo");

		//Antal hälsningsfraser
		amounOfHello=6;
		
		stringsToLookFor.add("Bye");
		stringsToLookFor.add("See you");
		stringsToLookFor.add("Later");
		stringsToLookFor.add("See ya");
		stringsToLookFor.add("Lates");
		stringsToLookFor.add("Peace out");
		
		amountOfBye=6;
		
		stringsToLookFor.add("Cool");
		stringsToLookFor.add("Love");

		memesToLookFor.add("Kappa");
		memesToLookFor.add("SSSsss");
		memesToLookFor.add("VaultBoy");
		memesToLookFor.add("<3");
		memesToLookFor.add("B)");

		//		stringsToLookFor.add("");
		//		stringsToLookFor.add("");
		//		stringsToLookFor.add("");
		//		stringsToLookFor.add("");
		//		stringsToLookFor.add("");
		//		stringsToLookFor.add("");

		new TwitchBot(stringsToLookFor);
	}
	public TwitchBot(ArrayList<String> lookFor) {
		System.setProperty("webdriver.chrome.driver", "C:/Users/Glenn/Downloads/chromedriver/chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		driver.manage().window().setSize(new Dimension(2000, 800));

		driver.manage().deleteAllCookies();

		//		driver.get("http://twitch.tv/login");
		//
		//		driver.findElement(By.id("username")).sendKeys("kakansbot");
		//		driver.findElement(By.xpath("//*[@id=\"loginForm\"]/div[2]/input")).sendKeys("falcor1122");
		//		driver.findElement(By.xpath("//*[@id=\"loginForm\"]/div[3]/input")).click();
		//
		//		driver.get("http://www.twitch.tv/kakan9898/chat");


		driver.get("https://api.twitch.tv/kraken/oauth2/authorize"
				+ "?response_type=token&client_id=ic1pa8ll9hazkssp35klwsmpelqejqj&redirect_uri=http://twitch.tv/kakan9898/chat&scope=chat_login");
		try {
			driver.findElement(By.xpath("//*[@id=\"header_login\"]/span")).click();
		} catch (Exception e) {
			// FIXME: handle exception
		}

		driver.findElement(By.xpath("//*[@id=\"login\"]")).sendKeys("kakansbot");
		driver.findElement(By.xpath("//*[@id=\"password\"]")).sendKeys("--");
		driver.findElement(By.xpath("//*[@id=\"oauth_submit\"]")).click();
		try {
			driver.findElement(By.xpath("//span[contains(text(),'Authorize')]")).click();
		} catch (Exception e) {
			// FIXME: handle exception
		}

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}
		try {
			driver.findElement(By.xpath("//*[@id=\"noty_796318084299728000\"]/div/div[2]/svg")).click();
		} catch (Exception e) {
			// FIXME: handle exception
		}
		driver.findElement(By.id("ember673")).sendKeys("I am now monotoring this chat! Be aware!",Keys.ENTER);

		while(found!=true){
			try {
				for(int index = 0; index < lookFor.size();index++){
					List<WebElement> element = driver.findElements(By.xpath("//*[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ',\'"+abc+"\'),\""+lookFor.get(index).toLowerCase()+"\")]"));
					for(int i = 0;i<element.size();i++){
						if(!(mathingsFound.contains(element.get(i)))){
							mathingsFound.add(element.get(i));
							if(index<amounOfHello){
								sortOfText="greet";
								System.out.println(index + " <- index --- amountHello -> "+amounOfHello);
							}
							else if(index>=amounOfHello&&index<(amounOfHello+amountOfBye)){
								sortOfText="bye";
							}
							else {
								sortOfText="emoji";
							}
							System.out.println("FOUND "+lookFor.get(index)+"!");
							System.err.println(element.get(i));
							FoundString(lookFor.get(index).toLowerCase(),element.get(i).getText().toLowerCase(), driver, sortOfText);
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
				try {
					for(int i1 = 0;i1<memesToLookFor.size();i1++){
						List<WebElement> element = driver.findElements(By.xpath("//img[@alt='"+memesToLookFor.get(i1)+"']"));
						for(int i = 0;i<element.size();i++){
							if(!(mathingsFound.contains(element.get(i)))){
								mathingsFound.add(element.get(i));
								sortOfText="meme";
								FoundMeme(memesToLookFor.get(i1), driver);
							}
						}
					}
				} catch (Exception e) {
					// FIXME: handle exception
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				System.out.println("Still running though");
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// FIXME Auto-generated catch block
				e.printStackTrace();
				System.out.println("Still running though");
			}
		}
	}

	public void FoundString(String textOfElement, String totalText, WebDriver driver, String sortOf){    

		if(totalText.length()>1&&(((totalText.indexOf(textOfElement)>0)&&abc.indexOf(Character.toLowerCase(totalText.charAt((totalText.indexOf(textOfElement)-1))))==-1)||(totalText.indexOf(textOfElement)==0))){


			//Måste använda 6 istället för.size(), för att bara ta kolla hälsningar
			if(sortOf=="greet"){
				for(int i = 0; i < amounOfHello;i++){
					//				System.err.println("-"+stringsToLookFor.get(i).toLowerCase() + "- <--- stringsToLookFor -Lenght same? "+
					//						(stringsToLookFor.get(i).length()==textOfElement.length())+"  - textofelement ---> -" + textOfElement.toLowerCase() + "-   True? " + 
					//						(stringsToLookFor.get(i).toString().toLowerCase()==textOfElement.toString().toLowerCase()));
					if(textOfElement.length()==stringsToLookFor.get(i).length()){
						for (int i1=0;i1<textOfElement.length();i1++) {

							if(textOfElement.toLowerCase().equals(stringsToLookFor.get(i).toLowerCase())){
							
								//Använder 6 här också av samma anledning som For loopen tidigare ^
								int rand= randFrase.nextInt(amounOfHello);
								String send = stringsToLookFor.get(rand).toString()+"!";
								driver.findElement(By.id("ember673")).sendKeys(send,Keys.ENTER);
								charTrue=0;
								mathingsFound.addAll(driver.findElements(By.xpath("//*[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ',\'"+abc+"\'),\""+send.toLowerCase()+"\")]")));
							}
						}
					}
				}
			}
			else if(sortOf=="bye"){
				for(int i = amounOfHello; i < (amounOfHello+amountOfBye);i++){
					//				System.err.println("-"+stringsToLookFor.get(i).toLowerCase() + "- <--- stringsToLookFor -Lenght same? "+
					//						(stringsToLookFor.get(i).length()==textOfElement.length())+"  - textofelement ---> -" + textOfElement.toLowerCase() + "-   True? " + 
					//						(stringsToLookFor.get(i).toString().toLowerCase()==textOfElement.toString().toLowerCase()));
					if(textOfElement.length()==stringsToLookFor.get(i).length()){
						for (int i1=0;i1<textOfElement.length();i1++) {

							if(textOfElement.toLowerCase().equals(stringsToLookFor.get(i).toLowerCase())){
								charTrue++;
							}
							if(charTrue==textOfElement.length()){
								int rand= randFrase.nextInt(amountOfBye);
								String send = stringsToLookFor.get(amounOfHello+rand).toString()+"!";
								driver.findElement(By.id("ember673")).sendKeys(send,Keys.ENTER);
								charTrue=0;
								mathingsFound.addAll(driver.findElements(By.xpath("//*[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ',\'"+abc+"\'),\""+send.toLowerCase()+"\")]")));
							}
						}
					}
				}
			}
			else if (sortOf=="emoji") {
				System.out.println(textOfElement + " = text of element --- same? "+ (textOfElement.equals("love".toString())));
				if(textOfElement.equals("love")){
					send = "<3";
				}
				else if (textOfElement.equals("cool")) {
					send = "B)";
				}
				System.out.println(send);
				driver.findElement(By.id("ember673")).sendKeys(send,Keys.ENTER);
				mathingsFound.addAll(driver.findElements(By.xpath("//*[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZÅÄÖ',\'"+abc+"\'),\""+send.toLowerCase()+"\")]")));
				mathingsFound.addAll(driver.findElements(By.xpath("//img[@alt='"+send+"']")));
			}
			charTrue=0;
		}
	}
	public void FoundMeme(String meme, WebDriver driver) {
		charTrue=0;
		send=meme;
		driver.findElement(By.id("ember673")).sendKeys(send,Keys.ENTER);
		charTrue=0;
		mathingsFound.addAll(driver.findElements(By.xpath("//img[@alt='"+meme+"']")));
	}

}
