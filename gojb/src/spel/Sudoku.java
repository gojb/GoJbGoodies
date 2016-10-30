package spel;

import java.util.ArrayList;
import java.util.Random;

public class Sudoku {

	static ArrayList<Integer> list = new ArrayList<>();

	static Random random = new Random();

	public static void main(String[] args) {
		// FIXME Auto-generated method stub
		new Sudoku();
	}
	public Sudoku(){

		for(int i=0;i < 81; i++){	
			list.add(makeBoard(i,100));
		}
		for(int i=0;i < 81; i++){
			list.set(i, list.get(i)+1);
		}
//		System.out.println(makeBoard(50));
//		
		for(int i=0;i < 9; i++){
			System.out.println(list.get(0+(i*9))+ " " + list.get(1+(i*9))+ " " + list.get(2+(i*9))+ " : " + list.get(3+(i*9))+ " " + 
					list.get(4+(i*9))+ " " + list.get(5+(i*9))+ " : " + list.get(6+(i*9))+ " " + list.get(7+(i*9))+ " " + list.get(8+(i*9)));
		}
	}
	public static int makeBoard(Integer number, Integer rand){
		int k, a, b;
		if(rand==100){
			k = random.nextInt(9);
		}
		else{
			if(rand!=8)k=rand+1;
			else k=0;
		}
		a = (int)((double)(number) / (9d))+1;//Rad, 1-9
		b = (number%9);//Kolumn, 0-8
		
		for(int i = 0;i < b;i++){
			System.out.println(i + " = i ;; k = "+k + " ;;list == "+list.get((9*(a-1))+i) + " EQUALS?: "+(list.get((9*(a-1))+i)==k) + " b: "+b+" a: "+a);
			if(list.get((9*(a-1))+i)==k){
//				System.err.println("KOLUMN FINNS");
//				System.out.println(number);
				return makeBoard(number, rand);
			}
		}
		for(int i = 1;i < a;i++){
			System.out.println(i + " = i ;; k = "+k + " ;;list == "+list.get((9*(a-1))+i) + " EQUALS?: "+(list.get((9*(a-1))+i)==k) + " b: "+b+" a: "+a);
			if(list.get((9*(i-1))+b)==k){
//				System.err.println("RAD FINNS");
//				System.out.println(number);
				return makeBoard(number, rand);
			}
		}
		System.err.println("kolumn: "+b + " rad: "+a + " ");
		System.err.println(((9*(a-1))+b));
		
		return k;
	}
}
