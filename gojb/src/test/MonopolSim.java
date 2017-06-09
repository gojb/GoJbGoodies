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

package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MonopolSim {
	
	static int tile=0,chance, community, amountDouble, jailTime=0, tilesSize=40, chanceSize=16, communitySize=17;
	static boolean jail=false;
	
	static ArrayList<Integer>  landedTiles = new ArrayList<>()
			,listChance = new ArrayList<>(), listCommunity = new ArrayList<>();
	
	public static void main(String[] args) {
		
		for(int i = 0; i < tilesSize; i++){
			if(i<chanceSize) listChance.add(i);
			if(i<communitySize) listCommunity.add(i);
			landedTiles.add(0);
		}
		
		
		
		for(int i = 0; i < listChance.size(); i++){
			System.out.println(listChance.get(i));
		}
		int cap=2000000;
		for (int j = 0; j < cap; j++) {
			for (int i = 0; i < 100; i++) {
				Monopol();
			}
			chance=0;
			community=0;
			jailTime=0;
			amountDouble=0;
			jail=false;
			tile=0;
			Collections.shuffle(listChance);
			Collections.shuffle(listCommunity);
		}
		String string="";
		for (int i = 0; i < landedTiles.size(); i++) {
			string=string+", Tile"+i+": "+landedTiles.get(i)+"; "+
					((Math.floor(((double)landedTiles.get(i)/(double)(cap+100))*1000))/10d)+"%";
		}
		System.out.println(string.substring(2));
		
	}
	
	public static void Monopol() {
		
		String diceRoll=dice();
		int diceValue=Integer.parseInt(diceRoll.split(",")[0]);
		boolean diceDouble = Boolean.parseBoolean(diceRoll.split(",")[1]);
		
		if(tile==10&&jail){
			//If already in jail, cannot roll out except if rolled a double, or 3 failed attempts of double
			if(jailTime<3){
				jailTime++;
				if(diceDouble){
					tile+=diceValue;
					if(tile>=landedTiles.size()){
						tile=tile-landedTiles.size();
					}
				}
			}
			else{
				jailTime=0;
			}
		}
		//If not on jail tile
		else{
			tile=tile+diceValue;
			if(tile>=landedTiles.size()){
				tile=tile-landedTiles.size();
			}
		}
		//Checks different values of the tile it landed on. For example, community chest can send you to another tile, and Go To Jail tile sends you to jail
		if(tile==30){
			//Go to jail
			tile=10;
			jail=true;
		}
		else if(tile==2||tile==17||tile==33){
			//Community chest
			community++;
			if(community>=listCommunity.size()){
				community=community-listCommunity.size();
			}
			int communityTile=listCommunity.get(community);
			
			switch (communityTile) {
				case 1:
					//Go
					tile=0;
					break;
				case 2:
					//Jail
					tile=10;
					jail=true;
					break;
				default:
					break;
			}
			
		}
		else if(tile==7||tile==22||tile==36){
			//Chance
			chance++;
			if(chance>=listChance.size()){
				chance=chance-listChance.size();
			}
			int chanceTile=listChance.get(chance);
			
			switch (chanceTile) {
				case 1:
					//Go
					tile=0;
					break;
				case 2:
					//Illionis ave
					tile=24;
					break;
				case 3:
					//St. Charles Place
					tile=11;
					break;
				case 4:
					//Nearest utility
					if(tile==22){
						//closest to water
						tile=28;
					}
					else {
						//closest to electricity
						tile=12;
					}
					break;
				case 5:
					//Nearest Railroad
					switch (tile) {
						case 7:
							tile=15;
							break;
						case 22:
							tile=25;
							break;
						case 36:
							tile=5;
							break;
					}
					break;
				case 6:
					//Back 3 spaces
					tile=tile-3;
					if(tile<0){
						tile=40+tile;
					}
					break;
				case 7:
					//Jail
					tile=10;
					jail=true;
					break;
				case 8:
					//Reading railroad
					tile=5;
					break;
				case 9:
					//Boardwalk
					tile=39;
					break;
				
				default:
					break;
			}
		}
		
		//Done with checking the tile, checking doubleDice
		if(diceDouble){
			amountDouble++;
		}
		else {
			amountDouble=0;
		}
		if(amountDouble==3){
			//Go to jail if 3 continous double dices'
			amountDouble=0;
			tile=10;
			jail=true;
			System.err.println("JAIL TIME!!");
		}

//		System.out.println(tile);
		try {
			landedTiles.set(tile,landedTiles.get(tile)+1);
		} catch (Exception e) {
			System.err.println("tile: "+tile+ ", dice's "+diceRoll);
			e.printStackTrace();
		}
		
	}
	
	
	public static String dice(){
		Random random = new Random();
		int first = random.nextInt(6)+1;
		int second = random.nextInt(6)+1;
		return Integer.toString(first+second)+","+Boolean.toString(second==first);
	}
	
	public static void shuffle(int[] list){
		for(int i = 0; i < list.length;i++){
			list[i] = getRand(list);
			list[i]--;
		}
	}
	public static int getRand(int[] arr){
		int k = new Random().nextInt(arr.length+1);
		for(int i = 0; i < arr.length; i++){
			if(k==arr[i]){
				return getRand(arr);
			}
		}
		return k;
	}
}