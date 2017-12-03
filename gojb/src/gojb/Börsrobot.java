package gojb;

import java.util.Calendar;
import java.util.List;

import javax.swing.JOptionPane;

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
import org.openqa.selenium.interactions.Actions;

import static gojb.Börsrobot.Signal.*;
public class Börsrobot  {
	private String lösen;
	private WebDriver driver;
	double kurs,SMA10,SMA20,RSI,saldobull,saldobear;
	int antalaffärer;
	String senaste;
	Signal innehav=NEUTRAL;
	public static void main(String[] args) {
		try {
			new Börsrobot();
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
	}
	public enum Signal {SÄLJ,KÖP,NEUTRAL};
	public Börsrobot() throws InterruptedException {

		lösen=JOptionPane.showInputDialog("Skriv lösenord");
		System.setProperty("webdriver.chrome.driver", "C:/Users/Jakob/Downloads/chromedriver_win32/chromedriver.exe");
		driver = new ChromeDriver();
		loggaIn();
		openOMXS30();
		laddaData();
		Signal senaste=NEUTRAL;
		while (true) {
			reload();
			laddaData();
			Signal nysignal = NEUTRAL;
			if (SMA10>SMA20&&kurs>SMA10) {
				nysignal=KÖP;
				System.out.println("Köpsignal");
			}
			else if (SMA10<SMA20&&kurs<SMA10) {
				nysignal=SÄLJ;
				System.out.println("Säljsignal");
			}
			if (senaste==nysignal) {
				if (senaste==NEUTRAL)
					System.out.println("avvaktar");
				if(senaste!=innehav){
					realsiera(senaste);
				}
			}
			else{
				senaste=nysignal;
			}
			Calendar c = Calendar.getInstance();
			if (c.get(Calendar.HOUR_OF_DAY)==17) {
				if (c.get(Calendar.MINUTE)==23) {
					sälj_produkt(innehav);
					antalaffärer++;
					System.out.println("dagens vinst:"+(saldobear+saldobull));
					driver.close();
				}
			}
			System.out.println("BULL:"+saldobull+"  BEAR:"+saldobear+"  Antal:"+antalaffärer);
			Thread.sleep(50000);
		}
		//		driver.quit();
	}
	private void sälj_produkt(Signal riktning) {
		setSaldo(riktning,getSaldo(riktning)+kurs);
		innehav=NEUTRAL;
	}
	private void köp_produkt(Signal riktning) {
		setSaldo(riktning,getSaldo(riktning)-kurs);
		innehav=riktning;
	}
	private void realsiera(Signal signal){
		antalaffärer++;
		if (innehav!=NEUTRAL) {
			sälj_produkt(innehav);
		}
		köp_produkt(signal);
	}
	private double getSaldo(Signal riktning){
		if (riktning==KÖP) {
			return saldobull;
		}
		else if (riktning==SÄLJ) {
			return saldobear;
		}
		System.err.println("FELAKTIG RIKTNING");
		return 0;
	}
	private void setSaldo(Signal riktning,double value){
		if (riktning==KÖP) {
			saldobull=value;
		}
		else if (riktning==SÄLJ) {
			saldobear=value;
		}
	}

	public void loggaIn() throws InterruptedException {
		driver.get("https://avanza.se/start/forsta-oinloggad.html");
		driver.findElement(By.ByLinkText.linkText("Användarnamn & lösenord")).click();
		driver.findElements(By.name("j_username")).get(1).sendKeys("jakobbjorns");
		WebElement element = driver.findElements(By.name("j_password")).get(1);
		element.sendKeys(lösen);
		element.submit();
		Thread.sleep(500);
	}
	public void openOMXS30() {
		driver.get("https://avanza.se/index/om-indexet.html/19002/omx-stockholm-30");
		driver.findElement(By.className("ta")).click();
		driver.findElement(By.className("loadTemplate")).click();
		driver.findElement(By.className("ta")).click();
	}
	public void laddaData(){
		WebElement element=driver.findElement(By.className("highcharts-series")).findElement(By.tagName("path"));
		element.getLocation();
		element.getSize().getWidth();
		//		driver.getMouse();
		new Actions(driver).moveToElement(element).moveByOffset((int)Math.round(element.getSize().getWidth()/2d)+1, 10).click().build().perform();;
		List<WebElement> pointlist=driver.findElements(By.className("pointRow"));
		for (int i = 0; i <= 4; i++) {
			WebElement span=pointlist.get(i);
			//			List<WebElement> spans =span.findElements(By.tagName("span"));
			System.out.println(span.getText());
		}
		senaste=pointlist.get(0).getText();
		kurs=hämtaKurs(pointlist, 1);
		SMA10=hämtaKurs(pointlist, 2);
		SMA20=hämtaKurs(pointlist, 3);
		RSI=hämtaKurs(pointlist, 4);
		System.out.println(kurs+" "+SMA10+" "+SMA20+" "+RSI);
	}
	public void reload() throws InterruptedException{
		WebElement element = driver.findElement(By.className("default"));
		element.click();
		Thread.sleep(1000);
	}
	double hämtaKurs(List<WebElement> list,int n){
		String string=list.get(n).findElements(By.tagName("span")).get(1).getText()
				.replaceAll(",", ".")
				.replaceAll(" ", "");
		return Double.parseDouble(string);
	}

}
