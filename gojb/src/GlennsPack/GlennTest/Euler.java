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

package GlennsPack.GlennTest;

import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Euler {
	
	public static void main(String[] args) {
//		euler7();
		euler29();
	}
	
	public static void euler29(){
		long millis = System.nanoTime();
		Map<String, Boolean> snopp = new HashMap<>();
		
		for (int a = 2; a <= 100; a++) {
			for (int b = 2; b <= 100; b++) {
				double power = Math.pow(a,b);
				snopp.put(Double.toString(power), true);
			}
		}
		System.out.println(snopp.size());
		System.out.println(System.nanoTime()-millis);
	}
	
	public static void euler1(){
		int sum = 0;
		for(int i = 0; i < 1000; i++){
			if(i%5==0 || i%3==0){
				sum += i;
			}
		}
		System.out.println(sum);
	}
	
	public static void euler7(){
		int i = 1;
		int prime;
		int number = 2;
		
		while(i < 10002){
			int root = Math.round((float)Math.sqrt(number));
			boolean foundPrime = true;
			for(int j = 2; j <= root; j++){
				if(number%j==0){
					foundPrime=false;
					break;
				}
			}
			if(foundPrime){
				i++;
				prime=number;
				System.out.println(prime);
			}
			number++;
		}
	}
}
