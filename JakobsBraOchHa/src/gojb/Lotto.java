package gojb;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Lotto {
	int[] is = new int[7];
	ArrayList<Integer> list = new ArrayList<>();
	Random random = new Random();
	int försök;

	public Lotto() {
		for (int i = 0; i < 7; i++) {
			list.add(rand(list));
		}
		Collections.sort(list);
		System.out.println("Välkommen till GoJbs lotto! Du fick den slumpade raden "+list);
		System.err.println("beräknar...");
		System.out.println();
		int[] rätt=new int[8];
		while (rätt[7]==0) {
			int antalrätt=0;
			ArrayList<Integer> list2 = new ArrayList<>();
			for (int i = 0; i < 7; i++) {
				list2.add(rand(list2));
			}
			for (int i = 0; i < 7; i++) {
				for (int i2 = 0; i2 < 7; i2++) {
					if (list.get(i)==list2.get(i2)) {
						antalrätt++;
					}
				}
			}
			rätt[antalrätt]++;
			försök++;
		}
		System.err.println("Det tog "+försök+" spel innan din rad dök upp. Under försöken fick du:");
		for (int i = 0; i < 7; i++) {
			System.out.println(i +" rätt " + rätt[i] + " gånger.");
		}
	}

	public static void main(String[] args) {
		new Lotto();

	}
	public int rand(ArrayList<Integer> list) {
		int i = random.nextInt(35)+1;
		for (int i2 : list) {
			if (i==i2) {
				return rand(list);
			}
		}
		return i;
	}

}
