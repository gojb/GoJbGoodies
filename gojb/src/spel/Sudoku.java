package spel;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;

public class Sudoku {

<<<<<<< HEAD
	static ArrayList<Integer> list = new ArrayList<>(), numbersHorizontal=new ArrayList<>(),numbersVertical=new ArrayList<>(),numbersBox=new ArrayList<>();

=======
	static ArrayList<Integer> list = new ArrayList<>();
	int[][] integerss= new int[9][9];
	int ber;
>>>>>>> branch 'master' of https://github.com/gojb/GoJbsBraOchHa.git
	static Random random = new Random();

	public static void main(String[] args) {
		// FIXME Auto-generated method stub
		new Sudoku();
	}
	public Sudoku(){
		int f=0;
		for (int i = 0; i < 9; i=i+0) {
			if (f++==500000) {
				f=0;
				i-=2;
//				i=0;
//				print(false);
			}
			ber:{
				int[] ny = {1,2,3,4,5,6,7,8,9};
				for (int j = ny.length - 1; j > 0; j--){
					int index = random.nextInt(j + 1);
					int a = ny[index];
					ny[index] = ny[j];
					ny[j] = a;
				}
				for (int j = 0; j < 3; j++) {
					//Blockrad
					for (int j2 = 0; j2 < 3; j2++) {
						//siffra i raden
						int siffra = ny[j+(j2*3)];
						// 1 2 3
						// 4 5 6
						// 7 8 9

<<<<<<< HEAD
		for(int i=0;i < 81; i++){	
			list.add(makeBoard(1));
//			list.add(makeBoard(i,100));
=======
						for (int k2 = i%3; k2 > 0; k2--) {
							//tidigare block i raden
							for (int k = 0; k < 3; k++) {
								ber++;
								if (ny[j2+(j*3)]==integerss[i-k2][k+(j*3)]) {
//									System.err.println("56789");
//									print(false);
									break ber;
								}
							}
						}
						for (int k2 = i/3; k2 > 0; k2--) {
							//tidigare block i kolumnen
							//Siffra 1-3 inom tidigare ruta
							for (int k = 0; k < 3; k++) {
								if (siffra==integerss[i-(k2*3)][(3*k)+j]) {
//									System.err.println("ryhkmldf");
									break ber;
								}
							}

						}
					}
				}
				integerss[i]=ny;
				i++;
				f=0;
			}

>>>>>>> branch 'master' of https://github.com/gojb/GoJbsBraOchHa.git
		}
<<<<<<< HEAD
		for(int i=0;i < 81; i++){
			list.set(i, list.get(i)+1);
		}

		//		System.out.println(makeBoard(50));
		//		
		for(int i=0;i < 9; i++){
			System.out.println(list.get(0+(i*9))+ " " + list.get(1+(i*9))+ " " + list.get(2+(i*9))+ "   " + list.get(3+(i*9))+ " " + 
					list.get(4+(i*9))+ " " + list.get(5+(i*9))+ "   " + list.get(6+(i*9))+ " " + list.get(7+(i*9))+ " " + list.get(8+(i*9)));
			if(i==2||i==5){
				System.out.println();
			}
		}
//		System.out.println(numbersHorizontal);
=======
		print(true);
		System.err.println(ber);
>>>>>>> branch 'master' of https://github.com/gojb/GoJbsBraOchHa.git
	}
<<<<<<< HEAD
	public static int makeBoard(Integer number){
		for(int i = 0;i<9;i++){
		
		}
		
		return 2;
	}
	
/*	public static int makeBoard(Integer number, Integer rand){
		int k, a, b, a1, b1;
		if(rand==100){
			k = random.nextInt(9);
=======
	void print(boolean summa){
		PrintStream printStream;
		if (summa) {
			printStream=System.out;
>>>>>>> branch 'master' of https://github.com/gojb/GoJbsBraOchHa.git
		}
		else{
			printStream=System.err;
		}
<<<<<<< HEAD
		a = (int)((double)(number) / (9d))+1;//Rad, 1-9
		b = (number%9);//Kolumn, 0-8
		a1=(a-1)%3;//Rad i 3*3 ruta, 0-2
		b1=b%3;//Kolumn i 3*3 ruta, 0-2
		if(list.size()<17){
			for(int i = 0;i < b;i++){
				System.out.println(i + " = i ;; k = "+k + " ;;list == "+list.get((9*(a-1))+i) + " EQUALS?: "+(list.get((9*(a-1))+i)==k) + " b: "+b+" a: "+a);
				if(list.get((9*(a-1))+i)==k){
					//				System.err.println("KOLUMN FINNS");
					//				System.out.println(number);
					System.out.println(list);
					return makeBoard(number, rand);
				}
			}
			for(int i = 1;i < a;i++){
				//			System.out.println(i + " = i ;; k = "+k + " ;;list == "+list.get((9*(a-1))+(i-1)) + " EQUALS?: "+(list.get((9*(a-1))+(i-1))==k) + " b: "+b+" a: "+a);
				if(list.get((9*(i-1))+b)==k){
					//				System.err.println("RAD FINNS");
					//				System.out.println(number);
					return makeBoard(number, rand);
				}
			}


			//Kollar vilken rad och kulumn man är i, i varje 3*3 kub. tex 1 = rad/kolumn nr 2
			if(a1==1){
				if(b1==0){
					if(list.get(number-9)==k||list.get(number-8)==k||list.get(number-7)==k){
						return makeBoard(number, rand); 
					}
				}
				else if (b1==1) {
					if(list.get(number-9)==k||list.get(number-8)==k||list.get(number-10)==k){
						return makeBoard(number, rand); 
					}
				}
				else if (b1==2) {
					if(list.get(number-9)==k||list.get(number-10)==k||list.get(number-11)==k){
						return makeBoard(number, rand); 
					}
				}
			}
			else if(a1==2){
				if(b1==0){
					if(list.get(number-9)==k||list.get(number-8)==k||list.get(number-7)==k||list.get(number-18)==k||list.get(number-17)==k||list.get(number-16)==k){
						return makeBoard(number, rand); 
					}
				}
				else if (b1==1) {
					if(list.get(number-9)==k||list.get(number-8)==k||list.get(number-10)==k||list.get(number-18)==k||list.get(number-17)==k||list.get(number-19)==k){
						return makeBoard(number, rand); 
					}
				}
				else if (b1==2) {
					if(list.get(number-9)==k||list.get(number-10)==k||list.get(number-11)==k||list.get(number-18)==k||list.get(number-16)==k||list.get(number-17)==k){
						return makeBoard(number, rand); 
					}
				}
=======
		int b=0,
				c=0;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 3; j++) {
				for (int j2 = 0; j2 < 3; j2++) {
					printStream.print(integerss[b+j][j2+c]+" ");
				}
				printStream.print("  ");
>>>>>>> branch 'master' of https://github.com/gojb/GoJbsBraOchHa.git
			}
<<<<<<< HEAD
		}
				else{
					numbersBox.clear();
					numbersHorizontal.clear();
					numbersVertical.clear();
//					if(list.size()==17){
						for(int i = 0; i<b;i++){
							numbersHorizontal.add(list.get((9*(a-1))+i));
						}
						for(int i = 1;i < a;i++){
							numbersVertical.add(list.get((9*(i-1))+b));
						}
						if(a1==1){
							if(b1==0){
								numbersBox.add(list.get(number-9));
								numbersBox.add(list.get(number-8));
								numbersBox.add(list.get(number-7));
							}
							else if (b1==1) {
								numbersBox.add(list.get(number-9));
								numbersBox.add(list.get(number-8));
								numbersBox.add(list.get(number-10));
							}
							else if (b1==2) {
								numbersBox.add(list.get(number-9));
								numbersBox.add(list.get(number-10));
								numbersBox.add(list.get(number-11));	
							}
						}
						else if(a1==2){
							if(b1==0){
								numbersBox.add(list.get(number-9));
								numbersBox.add(list.get(number-8));
								numbersBox.add(list.get(number-7));
								numbersBox.add(list.get(number-18));
								numbersBox.add(list.get(number-17));
								numbersBox.add(list.get(number-16));
								}
							
							else if (b1==1) {
								numbersBox.add(list.get(number-9));
								numbersBox.add(list.get(number-8));
								numbersBox.add(list.get(number-10));
								numbersBox.add(list.get(number-18));
								numbersBox.add(list.get(number-17));
								numbersBox.add(list.get(number-19));
								}
							
							else if (b1==2) {
								numbersBox.add(list.get(number-9));
								numbersBox.add(list.get(number-10));
								numbersBox.add(list.get(number-11));
								numbersBox.add(list.get(number-18));
								numbersBox.add(list.get(number-16));
								numbersBox.add(list.get(number-17));
							}
						}
						for(int i = 1; i<10;i++){
							if(numbersBox.indexOf(i)==-1&&numbersHorizontal.indexOf(i)==-1&&numbersVertical.indexOf(i)==-1){
								return i-1;
							}
						}
						System.err.println("------ "+number + " --- "+numbersBox+" --- "+numbersHorizontal+" --- "+numbersVertical);
						return makeBoard(number, rand); 
//					}				
				}


		System.err.println("kolumn: "+b + " rad: "+a + " ");
		System.err.println(((9*(a-1))+b));

		return k;
	}*/
=======
			c+=3;
			if (c==9) {
				c=0;
				b+=3;
				printStream.println();
			}
			printStream.println();
		}
		printStream.println();
	}
>>>>>>> branch 'master' of https://github.com/gojb/GoJbsBraOchHa.git
}
