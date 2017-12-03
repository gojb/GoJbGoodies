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

import static gojb.B�rsrobot.Signal.*;
public class B�rsrobot  {
	private String l�sen;
	private WebDriver driver;
	double kurs,SMA10,SMA20,RSI,saldobull,saldobear;
	int antalaff�rer;
	String senaste;
	Signal innehav=NEUTRAL;
	public static void main(String[] args) {
		try {
			new B�rsrobot();
		} catch (InterruptedException e) {
			e.printStackTrace();

		}
	}
	public enum Signal {S�LJ,K�P,NEUTRAL};
	public B�rsrobot() throws InterruptedException {

		l�sen=JOptionPane.showInputDialog("Skriv l�senord");
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
				nysignal=K�P;
				System.out.println("K�psignal");
			}
			else if (SMA10<SMA20&&kurs<SMA10) {
				nysignal=S�LJ;
				System.out.println("S�ljsignal");
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
					s�lj_produkt(innehav);
					antalaff�rer++;
					System.out.println("dagens vinst:"+(saldobear+saldobull));
					driver.close();
				}
			}
			System.out.println("BULL:"+saldobull+"  BEAR:"+saldobear+"  Antal:"+antalaff�rer);
			Thread.sleep(50000);
		}
		//		driver.quit();
	}
	private void s�lj_produkt(Signal riktning) {
		setSaldo(riktning,getSaldo(riktning)+kurs);
		innehav=NEUTRAL;
	}
	private void k�p_produkt(Signal riktning) {
		setSaldo(riktning,getSaldo(riktning)-kurs);
		innehav=riktning;
	}
	private void realsiera(Signal signal){
		antalaff�rer++;
		if (innehav!=NEUTRAL) {
			s�lj_produkt(innehav);
		}
		k�p_produkt(signal);
	}
	private double getSaldo(Signal riktning){
		if (riktning==K�P) {
			return saldobull;
		}
		else if (riktning==S�LJ) {
			return saldobear;
		}
		System.err.println("FELAKTIG RIKTNING");
		return 0;
	}
	private void setSaldo(Signal riktning,double value){
		if (riktning==K�P) {
			saldobull=value;
		}
		else if (riktning==S�LJ) {
			saldobear=value;
		}
	}

	public void loggaIn() throws InterruptedException {
		driver.get("https://avanza.se/start/forsta-oinloggad.html");
		driver.findElement(By.ByLinkText.linkText("Anv�ndarnamn & l�senord")).click();
		driver.findElements(By.name("j_username")).get(1).sendKeys("jakobbjorns");
		WebElement element = driver.findElements(By.name("j_password")).get(1);
		element.sendKeys(l�sen);
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
		kurs=h�mtaKurs(pointlist, 1);
		SMA10=h�mtaKurs(pointlist, 2);
		SMA20=h�mtaKurs(pointlist, 3);
		RSI=h�mtaKurs(pointlist, 4);
		System.out.println(kurs+" "+SMA10+" "+SMA20+" "+RSI);
	}
	public void reload() throws InterruptedException{
		WebElement element = driver.findElement(By.className("default"));
		element.click();
		Thread.sleep(1000);
	}
	double h�mtaKurs(List<WebElement> list,int n){
		String string=list.get(n).findElements(By.tagName("span")).get(1).getText()
				.replaceAll(",", ".")
				.replaceAll(" ", "");
		return Double.parseDouble(string);
	}

}
