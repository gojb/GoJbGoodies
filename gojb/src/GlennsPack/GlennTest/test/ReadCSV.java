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

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by glenn on 2017-06-26.
 */
public class ReadCSV {
	
	String start, slut, sömnkvalite, tidISäng, vaknade, steg;
	
	boolean isExceedNights = false;
	
	ArrayList<String> noteringar = new ArrayList<>();
	
	public static void main(String[] args) {
		
		System.out.println(new ReadCSV(671).start);
		
		ArrayList<ReadCSV> listOfJobbade = new ArrayList<>(), listOfNotJobbade = new ArrayList<>(), listOfAll = ReadCSV.getArrayListOfAllNights();
		
		for(ReadCSV readCSV : listOfAll){
			ArrayList<String> noteringar = readCSV.noteringar;
			if(noteringar.contains("Working")){
				listOfJobbade.add(readCSV);
			}
			else{
				listOfNotJobbade.add(readCSV);
			}
		}
		long snittJobbat=0;
		long snittEjJobbat=0;
		
		for(ReadCSV readCSV : listOfJobbade){
			try{
				snittJobbat+=Long.parseLong(readCSV.steg);
			}
			catch (Exception e){
			e.printStackTrace();
			}
		}
		snittJobbat=((long)snittJobbat/(long)listOfJobbade.size());
		
		int i = 0;
		
		for(ReadCSV readCSV : listOfNotJobbade){
			i++;
			try{
				snittEjJobbat+=Long.parseLong(readCSV.steg);
			}
			catch (Exception e){
				System.out.println(readCSV.start + " "+i);
				e.printStackTrace();
			}
		}
		snittEjJobbat=((long)snittEjJobbat/(long)listOfNotJobbade.size());
		
		System.out.println(snittJobbat+" : "+snittEjJobbat);
		
	}
	
	public ReadCSV(int lineNumber){
		String path="/Users/glenn/Documents/sleepdata-1.csv";
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = "";
			String cvsSplitBy =";";
			
			int actualLineNumber = (2*lineNumber)+2;
			
			Object[] linesArray = br.lines().toArray();
			
			//-1 för att sista är ett tomt
			if(actualLineNumber>=linesArray.length-1){
				isExceedNights=true;
				return;
			}
			
			String[] column = linesArray[actualLineNumber].toString().split(cvsSplitBy);
			
			start = column[0];
			slut = column[1];
			sömnkvalite = column[2];
			tidISäng = column[3];
			
			for (int i = 0; i < column[5].split(":").length; i++) {
				noteringar.add(column[5].split(":")[i]);
			}
			
			steg = column[7];
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static ArrayList<ReadCSV> getArrayListOfAllNights(){
		ArrayList<ReadCSV> listOfNights = new ArrayList<>();
		int i = 0;
		boolean exceedsNigth=false;
		while (!exceedsNigth){
			
			listOfNights.add(new ReadCSV(i));
			
			if(listOfNights.get(i).isExceedNights){
				exceedsNigth=true;
			}
			i++;
		}
		return listOfNights;
	}
	
}
