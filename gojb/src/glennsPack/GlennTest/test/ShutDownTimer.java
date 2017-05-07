package glennsPack.GlennTest.test;

import java.io.IOException;

public class ShutDownTimer {

	public static void main(String[] args) {
		new ShutDownTimer();

	}
	public ShutDownTimer(){
		
		try {
			
			System.out.println("Countdown started");
			
			
			//Skriv antal timmar som sista nummer(efter "...60)*")
			Thread.sleep((long) (((1000*60)*60)*3.5));
			
			//Antal minuter som sista nummer
//			Thread.sleep((long) ((1000*60)*15));
			
		} catch (InterruptedException e) {
		System.err.println("Wat");
		}
		System.err.println("Shuting down...");
		try {
			Runtime.getRuntime().exec("C:\\windows\\system32\\shutdown.exe -s -t 1 -c \"Shutting Down\"");
		} catch (IOException e) {
			new ShutDownTimer();
		}
	}
}
