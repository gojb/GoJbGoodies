package GlennsPack.GlennWebAPI;

import javafx.scene.input.KeyCode;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Scanner;

/**
 * Created by Glenn on 2017-05-06. *
 */
public class Keybr {

    public static void main(String[] args) {
        new Keybr();
    }

    public Keybr() {
//        System.setProperty("webdriver.chrome.driver", "C:/Users/Glenn/Downloads/chromedriver/chromedriver.exe");
//        System.setProperty("webdriver.chrome.driver", "C:/Users/Glenn/Downloads/chromedriver/chromedriver.exe");

        ProfilesIni profileObj = new ProfilesIni();
        FirefoxProfile yourFFProfile = profileObj.getProfile("Glenn");

        WebDriver driver;

        driver = new FirefoxDriver(yourFFProfile);
        driver.get("https://www.keybr.com/practice");

        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Scanner reader = new Scanner(System.in);  // Reading from System.in
        System.out.println("Enter a number: ");
        int n = reader.nextInt();

        runer(driver);
    }

        public void runer(WebDriver driver){

        driver.findElement(By.className("Tour-close")).click();

        System.out.println("CLICKED");
//
        try{

            driver.findElement(By.id("Practice-textInput")).click();
            Thread.sleep(1000);
            WebElement element = driver.findElement(By.id("Practice"));

            Actions actions = new Actions(driver);
            actions.moveToElement(element);
            actions.click();

            String abc = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz[]-.,?!()\"'";

            Robot robot = new Robot();

            for (int i1 = 0; i1 < 30; i1++) {

                try{
                    for (int i = 0; i < 200; i++) {

                        String currLetter = driver.findElement(By.cssSelector("span.TextInput-item:nth-child("+(i+1)+")")).getText();
                        System.out.println(currLetter);

                        if(!abc.contains(currLetter)) {
                            currLetter = " ";
                            System.out.println("SPACE");
                            i++;
                        }

                        Thread.sleep(25);

                        robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(currLetter.charAt(0)));
                        actions.build().perform();
                        robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(currLetter.charAt(0)));
                    }
                }catch(Exception e){
                    System.out.println("Another row done");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }


    }
}
