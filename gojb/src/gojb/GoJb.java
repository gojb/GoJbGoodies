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

package gojb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Created by Glenn on 2017-05-26.
 */
public class GoJb {
	static int currentIndex;
	static String pi;
	public static void main(String[] args) {
		
		int low=2, high=10;
		
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					"gojb\\src\\images\\pi.txt")));
			
			Object[] lines = reader.lines().toArray();
			pi = lines[0].toString();
			int recentIndex =  Integer.parseInt(lines[1].toString());
			currentIndex=addToIndex(recentIndex);
			while(currentIndex>low||currentIndex<high){
				currentIndex=addToIndex(recentIndex);
			}
			int number = Integer.parseInt(Character.toString(pi.charAt(currentIndex)));
			
			System.out.println(number);
			
			
			
			try(FileWriter writer = new FileWriter("gojb\\src\\images\\pi.txt")){
				writer.write(pi+"\n"+Integer.toString(currentIndex));
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			reader.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Returns a random integer between two integers, by consistently going through pi and its decimals.
	 *
	 * @param minInclude the lower bound of the random integer. The result might
	 *                   be this integer.
	 * @param maxInclude the upper bound of the random integer. The result might
	 *                   be this integer.
	 *
	 * @return returns a random integer, the GoJb way, between the two parameters (both included)
	 */
	public static int newRandom(int minInclude, int maxInclude){
		int randomInt=0;
		try{
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					"gojb\\src\\images\\pi.txt")));
			String pi = reader.lines().toArray()[0].toString();
			int recentIndex =  Integer.parseInt(reader.lines().toArray()[1].toString());
			int currentIndex = addToIndex(recentIndex);
			
			FileWriter writer = new FileWriter(new File("gojb\\src\\images\\pi.txt"));
			writer.write(pi+"\n"+Integer.toString(currentIndex));
			reader.close();
			writer.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
		
		return randomInt;
	}
	
	public static int addToIndex(int recentIndex){
		currentIndex=recentIndex+1==pi.length()?0:recentIndex+1;
		return currentIndex;
	}
	
	/**
	 * Returns a random integer between 0 and maxInclude by consistently going through pi and its decimals.
	 *
	 * @param maxInclude the upper bound of the random integer. The result might
	 *                   be this integer.
	 *
	 * @return returns a random integer, the GoJb way, between the 0 and maxInclude (both included)
	 */
	public static int newRandom(int maxInclude){return GoJb.newRandom(0, maxInclude);}
}
