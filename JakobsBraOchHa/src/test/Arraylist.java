package test;

import java.util.ArrayList;
import java.util.List;

public class Arraylist {

	public Arraylist() {
		List<String> strings = new ArrayList<>();
		
		strings.add("Nr:");
		System.out.println(strings);
		strings.set(0, strings.get(0)+"1,2,3,4,5,6");
		System.err.println(strings);
		strings.set(0, strings.get(0)+",7,8,-9,10");
		System.out.println(strings);
		strings.add("sads-Nrr");
		
		strings.add("He-j");
	
		System.err.println(strings.get(0).toString().split(",")[1]+"    nr");
		
		
		
//		String string="dig";
//		strings.add(string);
//		strings.add("hej");
//		strings.add("på");
//		strings.add("dig");
//		strings.add("hej");
//		strings.add("då");
//		strings.add("tja");
//		strings.remove(string);
//		strings.remove(string);
//		strings.remove(string);
//		System.err.println(strings.toString());
//		
//		List<String> arrayList = Arrays.asList(strings.toString().substring(1,strings.toString().length()-1).split(", "));
//		System.err.println(arrayList);
//		System.err.println(arrayList.get(1));
		
	}
	public static void main(String[] args) {
		new Arraylist();
	}

}
