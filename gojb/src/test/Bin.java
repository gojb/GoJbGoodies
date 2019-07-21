package test;

public class Bin {
	public static void main(String[] args) {
		int x=(int) (Math.pow(2, 31)-1);
		System.out.println(x);
		x+=1;
		System.out.println(x);
		x-=1;
		System.out.println(x);
	}
}
