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
