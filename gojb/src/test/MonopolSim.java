package test;

import java.util.Random;

public class MonopolSim {

	static int[] tiles = new int[40], listChance = new int[16], listCommunity = new int[17], landedTiles = new int[40];
	static int tile=0,chance, community, amountDouble, jailTime=0;
	static boolean jail;

	public static void main(String[] args) {

		shuffle(listChance);
		shuffle(listCommunity);

		for (int i = 0; i < 20000; i++) {
			Monopol();
		}

		String string="";
		for (int i = 0; i < landedTiles.length; i++) {
			string=string+", "+landedTiles[i];
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
					if(tile>tiles.length){
						tile=tile-tiles.length;
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
			if(tile>tiles.length){
				tile=tile-tiles.length;
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
			if(community>listCommunity.length){
				community=community-listCommunity.length;
			}
			int communityTile=listCommunity[community];

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
			if(chance>listChance.length){
				chance=chance-listChance.length;
			}
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			int chanceTile=listChance[chance];

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
		}

//		System.out.println(tile);
		try {

			landedTiles[tile]++;
		} catch (Exception e) {
			System.err.println("tile: "+tile+ ", dice's "+diceRoll);
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