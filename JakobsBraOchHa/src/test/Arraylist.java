package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Arraylist {

	public Arraylist() {
		List<String> strings = new ArrayList<>();
		strings.add("hej");
		strings.add("på");
		strings.add("dig");
		strings.add("hej");
		strings.add("då");
		strings.add("tja");
		System.err.println(strings.toString());
		
		List<String> arrayList = Arrays.asList(strings.toString().substring(1,strings.toString().length()-1).split(", "));
		System.err.println(arrayList);
		System.err.println(arrayList.get(1));
		
	}
	public static void main(String[] args) {
		new Arraylist();
	}

}
