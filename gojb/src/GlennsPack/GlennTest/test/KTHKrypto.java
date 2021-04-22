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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class KTHKrypto {
	
	String output;
	String svenskaOrd;
	String theABC = "abcdefghijklmnopqrstuvwxyz213";
	String mostCommonInABC = "aeoivrst";
	String mostCommonInDocument = "eauintrl";
	String pathInput="/Users/glenn/Desktop/Krypto.txt";
	
	public static void main(String[] args) {
		new KTHKrypto();
	}
	
	
	public KTHKrypto(){
		
		try {
//			BufferedReader br = new BufferedReader(new FileReader(pathInput));
//			String line = br.readLine();
//			while (line != null){
//				builder.append(line.toLowerCase());
//				System.out.println(line);
//				line = br.readLine();
//			}
			//OBS --- 1 = Ä
			
			output = new String(Files.readAllBytes(Paths.get(pathInput))).toLowerCase();

//			svenskaOrd = new String(Files.readAllBytes(Paths.get("/Users/glenn/Desktop/ord.txt")));

//			wordsWithoutRepetition(output);

//			System.out.println(output);

//			findMultipleOfWord(output, 1);

//			findLetterCombination(output, "_*_*_");

			guessLetters(output);

//			replace("i", "u");
//			replace("p", "p");
//			replace("r", "g");
//			replace("n", "i");
//			replace("t","f");
//			replace("a", "t");

//			replace("l", "u");
//			replace("r", "p");
//			replace("t", "g");
//			replace("h", "i");
//			replace("n", "f");
//			replace("1", "t");

//			replace("u", "u");
//			replace("e", "p");
//			replace("n", "g");
//			replace("s", "i");
//			replace("a", "f");
//			replace("f", "t");

//			replace("e", "r");//E = N || R || S || T
//			replace("n", "o");
//			replace("t", "n");
//			replace("a", "e");
//			replace("r", "s");

//			replace("v", "a");
//			replace("e", "n");
//			replace("t", "d");
//			replace("r", "e");

//			replace("nsafu", "tredj");
			replace("hi1lgua", "tv2asex");
			
			
			Path path = Paths.get("/Users/glenn/Desktop/KryptoOut.html\"");
			
			Files.write(path, output.getBytes());



//			try(FileWriter file = new FileWriter("/Users/glenn/Desktop/KryptoOut.html\"")){
//				file.write(output + "  äääÄÄÄåååÖÖ");
//				file.close();
//			}
//			catch (Exception e) {
//				e.printStackTrace();
//			}
		
		}
		catch (Exception e){
			System.err.println("ERROR");
			e.printStackTrace();
		}
		
	}
	public void replace(String toReplace, String replaceWith){
		if(toReplace.length() != replaceWith.length()){
			System.err.println("Must be equally long words " + toReplace.length());
			return;
		}
		
		for (int i = 0; i < toReplace.length(); i++) {
			output = output.replace(Character.toString(toReplace.charAt(i)), Character.toString(replaceWith.charAt(i)).toUpperCase());
		}
	}
	
	public void findMultipleOfWord(String content, int wordLength){
		int lengthOfWord = wordLength;
		Map<String, Integer> twoLetterWords = new HashMap<>();
		content = content.replace(" ", "");
		for (int i = 0; i < content.length() - (lengthOfWord - 1); i++) {
			String word;
			word = content.substring(i, i + lengthOfWord);
			
			int integerToPut = 1;
			
			if(twoLetterWords.containsKey(word)) {
				integerToPut = twoLetterWords.get(word) + 1;
			}
			
			twoLetterWords.put(word, integerToPut);
		}
		
		Iterator it = twoLetterWords.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			
			int valueInt = (int) pair.getValue();
			double value = valueInt;
			
			System.out.println(pair.getKey() + " : " + pair.getValue() + " (" + (int) ((value / content.length())*100) + "%)");
			it.remove(); // avoids a ConcurrentModificationException
		}
	}
	
	
	//Contain a string like "*__*" where you want to find a combination that contaions two of
	//the same letter, but the two ones inbetween can be whatever
	public void findLetterCombination(String content, String whatToFind){
		content = content.replace(" ", "");
		int lengthOfWord = whatToFind.length();
		
		ArrayList<String> wordsFound = new ArrayList<>();
		
		for(int i = 0; i < content.length() - (lengthOfWord - 1); i++) {
			String word = content.substring(i, i + lengthOfWord);
			
			boolean works = true;
			
			String character = "";
			
			for (int j = 0; j < word.length(); j++) {
				
				if(whatToFind.charAt(j) == "*".charAt(0)){
					//Supposed to be the letter
					if(character.equals("")){
						//Not yet set
						character = word.substring(j, j+1);
					}
					else{
						
						if(!character.equals(word.substring(j, j+1))){
							//If the characters don't match
							works = false;
							j = lengthOfWord + 10;
						}
					}
					
				}
				else{
					//if not the wildcard letter
					if(character.equals(word.substring(j, j+1))){
						//If it is that letter anyways
						works = false;
						j = lengthOfWord + 10;
					}
				}
				
			}
			
			if(works){
				wordsFound.add(word);
			}
			
		}
		for(String s : wordsFound){
			System.out.println(s + " (Block nr. " + ((content.indexOf(s) / 5)+1));
		}
	}
	public void wordsWithoutRepetition(String content){
		content = content.replace(" ", "");
		String wordFlow = "";
		for (int i = 0; i < content.length() - 1; i++) {
			if(wordFlow.contains(content.substring(i, i+1))){
				//Repition of word
				System.out.println(wordFlow);
				wordFlow = "";
			}
			else{
				//No repetition
				wordFlow += content.substring(i, i+1);
			}
		}
		System.out.println(wordFlow);
	}
	
	
	public void guessLetters(String content){
		content = content.replace(" ", "");
		String[] possibleWords = new String[]{"kth", "campus", "krypto", "tävling", "andra", "tredje", "uppgift", "vid", "löst",
				"hundra", "år", "två", "sex", "tre"};
		//"Två av sex" funkar ej
		//"Tre av sex" -- || --
		
		
		int count = 0;
		
		int indexi = 0;
		int indexj = 0;
		int indexk = 0;
		int indexl = 0;
		int indexm = 0;
		int indexn = 0;
		int indexo = 0;
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(j == i){
					continue;
				}
				
				for (int k = 0; k < 8; k++) {
					if(k == i || k == j){
						continue;
					}
					
					for (int l = 0; l < 8; l++) {
						if(l == i || l == j || l == k){
							continue;
						}
						
						for (int m = 0; m < 8; m++) {
							if(m == i || m == j || m == k || m == l){
								continue;
							}
							
							for (int n = 0; n < 8; n++) {
								if(n == i || n == j || n == k || n == l || n == m){
									continue;
								}
								
								for (int o = 0; o < 8; o++) {
									if(o == i || o == j || o == k || o == l || o == m || o == n){
										continue;
									}
									
									for (int p = 0; p < 8; p++) {
										if(p == i || p == j || p == k || p == l || p == m || p == n || p == o){
											continue;
										}
										
										indexi = indexi + 1 == 8 ? 0 : indexi + 1;
										indexj = indexj + 1 == 8 ? 0 : indexj + 1;
										indexk = indexk + 1 == 8 ? 0 : indexk + 1;
										indexl = indexl + 1 == 8 ? 0 : indexl + 1;
										indexm = indexm + 1 == 8 ? 0 : indexm + 1;
										indexn = indexn + 1 == 8 ? 0 : indexn + 1;
										indexo = indexo + 1 == 8 ? 0 : indexo + 1;
//
//										System.out.println(mostCommonInDocument.charAt(0) + " : " + mostCommonInABC.charAt(i));
//										System.out.println(mostCommonInDocument.charAt(1) + " : " + mostCommonInABC.charAt(j));
//										System.out.println(mostCommonInDocument.charAt(2) + " : " + mostCommonInABC.charAt(k));
//										System.out.println(mostCommonInDocument.charAt(3) + " : " + mostCommonInABC.charAt(l));
//
//										System.out.println(mostCommonInDocument.charAt(4) + " : " + mostCommonInABC.charAt(m));
//										System.out.println(mostCommonInDocument.charAt(5) + " : " + mostCommonInABC.charAt(n));
//										System.out.println(mostCommonInDocument.charAt(6) + " : " + mostCommonInABC.charAt(o));
//										System.out.println(mostCommonInDocument.charAt(7) + " : " + mostCommonInABC.charAt(p));
//
//										System.out.println();
										
										String replacedCharacters = content
												.replace(Character.toString(mostCommonInDocument.charAt(0)), Character.toString(mostCommonInABC.charAt(i)).toUpperCase())
												.replace(Character.toString(mostCommonInDocument.charAt(1)), Character.toString(mostCommonInABC.charAt(j)).toUpperCase())
												.replace(Character.toString(mostCommonInDocument.charAt(2)), Character.toString(mostCommonInABC.charAt(k)).toUpperCase())
												.replace(Character.toString(mostCommonInDocument.charAt(3)), Character.toString(mostCommonInABC.charAt(l)).toUpperCase())
												.replace(Character.toString(mostCommonInDocument.charAt(4)), Character.toString(mostCommonInABC.charAt(m)).toUpperCase())
												.replace(Character.toString(mostCommonInDocument.charAt(5)), Character.toString(mostCommonInABC.charAt(n)).toUpperCase())
												.replace(Character.toString(mostCommonInDocument.charAt(6)), Character.toString(mostCommonInABC.charAt(o)).toUpperCase())
												.replace(Character.toString(mostCommonInDocument.charAt(7)), Character.toString(mostCommonInABC.charAt(p)).toUpperCase());
										
//										System.out.println(replacedCharacters);
										
										int numberOfWords = 0;
										for (String possibleWord : possibleWords) {
											if(replacedCharacters.contains(possibleWord.toUpperCase())) {
												numberOfWords++;
												
												System.out.println(possibleWord);
												
												if(numberOfWords >= 2)
													System.out.println(numberOfWords + " - " + possibleWord);
												else
													count++;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		
		System.out.println(count);
		
		
		
//
//			for (int i = 0; i < mostCommonInABC.length(); i++) {
//
//				Map<Character, Character> charsToSwap = new HashMap<>();
//				int index = i;
//				for (int j = 0; j < mostCommonInDocument.length(); j++) {
//					index = index + 1 == mostCommonInABC.length() ? 0 : index + 1;
//
//					charsToSwap.put(mostCommonInABC.charAt(index), mostCommonInDocument.charAt(j));
//
//				}
//				Iterator it = charsToSwap.entrySet().iterator();
//				while (it.hasNext()) {
//					Map.Entry pair = (Map.Entry) it.next();
//					replace(Character.toString((Character) pair.getKey()), Character.toString((Character) pair.getValue()));
//					System.out.println(pair.getKey() + " : " + pair.getValue());
//					it.remove(); // avoids a ConcurrentModificationException
//				}
//
//				try{
//					Path path = Paths.get("/Users/glenn/Desktop/KryptoOut/KryptoOut"+ i +".html");
//					Files.write(path, output.getBytes());
//					output = new String(Files.readAllBytes(Paths.get(pathInput))).toLowerCase();
//				}
//				catch (Exception e){
//					e.printStackTrace();
//				}
//
//
//				System.out.println();
//			}
		
	}
}









