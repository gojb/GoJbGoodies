package test;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import gojb.GoJbGoodies;

public class Personnr {

	static String pers = new String();
	static ArrayList<Integer> nr = new ArrayList<>();
	static Boolean bol = false, �rBol = false;
	static int tot,svar;

	public static void main(String[] args) {
		// FIXME Auto-generated method stub
		Object[] options = {"Testa","Skapa"};
		int choice=JOptionPane.showOptionDialog(null, "Skapa eller testa personnummer?",
				"Skapa eller testa?", JOptionPane.YES_NO_CANCEL_OPTION, 
				JOptionPane.DEFAULT_OPTION,
				null,options, options[0]);
		if(choice==0){
			Testa();
		}
		else if (choice==1) {
			Skapa();
		}
		else{
			System.exit(3);
		}
	}
	public static void Skapa(){
		String �r = JOptionPane.showInputDialog("F�rsta 6 siffrorna", "ex 981103");
		while(!�rBol){
			if(�r.length()==6){
				try {
					Integer.parseInt(�r);
					�rBol=true;
				} catch (Exception e) {
					// FIXME: handle exception
					JOptionPane.showMessageDialog(null, "OBS!! Endast siffror");
					�r = JOptionPane.showInputDialog("F�rsta 6 siffrorna", �r);
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "OBS!! 6 siffror");
				�r = JOptionPane.showInputDialog("F�rsta 6 siffrorna", �r);
			}}
		Object[] options = {"Man","Kvinna"};
		int k�n = JOptionPane.showOptionDialog(null, "Man eller kvinna?", "V�lj k�n", JOptionPane.YES_NO_OPTION, 
				JOptionPane.DEFAULT_OPTION, null, options, options[0]);
		if(k�n!=1&&k�n!=0){
			System.exit(3);
		}
		Random random = new Random(), random1 = new Random(), random2 = new Random();
		int k�nNr;
		if(k�n==0){
			k�nNr=2;
			while(k�nNr%2==0){
			k�nNr=random.nextInt(9);
			System.err.println(k�nNr);
			}
		}
		else{
			k�nNr=1;
			while(k�nNr%2!=0){
				k�nNr=random.nextInt(9);
			}
		}
		System.out.println("sdoudashu");
		//---------------
		
		int rand1=random1.nextInt(9), rand2=random2.nextInt(9);
		
		pers=�r+Integer.toString(rand1)+Integer.toString(rand2)+Integer.toString(k�nNr);
		
		
		for(int i = 0;i < pers.length();i++){	
			nr.add(Integer.parseInt(Character.toString(pers.charAt(i))));
			if(i%2==0){
				if(nr.get(i)*2>=10){
					System.err.println(nr.get(i)+"<-i___..."+(Integer.parseInt(Character.toString(Integer.toString(nr.get(i)*2).charAt(0)))+
							Integer.parseInt(Character.toString(Integer.toString(nr.get(i)*2).charAt(1)))));
					tot+=Integer.parseInt(Character.toString(Integer.toString(nr.get(i)*2).charAt(0)))+
							Integer.parseInt(Character.toString(Integer.toString(nr.get(i)*2).charAt(1)));
				}
				else{
					tot+=nr.get(i)*2;
				}
			}
			else{		
				tot+=nr.get(i);
			}


		}
		System.out.println(nr);
		if(tot>10){
			svar=10-Integer.parseInt(Character.toString(Integer.toString(tot).charAt(1)));
		}
		else{
			svar=10-tot;
		}
		
		//--------------

		
		JOptionPane.showMessageDialog(null, "Personnummret �r:\n"+�r+"-"+rand1+rand2+k�nNr+svar);
		System.exit(3);
	}
	public static void Testa(){
		pers=JOptionPane.showInputDialog("Persnnummer:","Personnummer � 10 siffror");
		while (bol == false){
			if(pers.length()==10){
				try {

					Double.parseDouble(pers);

					bol=true;
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "OBS! 10 siffrors personnummer");
					pers=JOptionPane.showInputDialog("Persnnummer:",pers);
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "OBS! 10 siffrors personnummer");
				pers=JOptionPane.showInputDialog("Persnnummer:",pers);
			}
		}
		for(int i = 0;i < pers.length()-1;i++){	
			nr.add(Integer.parseInt(Character.toString(pers.charAt(i))));
			if(i%2==0){
				if(nr.get(i)*2>=10){
					System.err.println(nr.get(i)+"<-i___..."+(Integer.parseInt(Character.toString(Integer.toString(nr.get(i)*2).charAt(0)))+
							Integer.parseInt(Character.toString(Integer.toString(nr.get(i)*2).charAt(1)))));
					tot+=Integer.parseInt(Character.toString(Integer.toString(nr.get(i)*2).charAt(0)))+
							Integer.parseInt(Character.toString(Integer.toString(nr.get(i)*2).charAt(1)));
					//						tot+=Integer.parseInt(Character.toString(pers.charAt(i)))*2-Math.floor((Integer.parseInt(Character.toString(pers.charAt(i))))*2/10);
					//						System.out.println((Integer.parseInt(Character.toString(pers.charAt(i))))*2+
					//								"<-nr__''"+(int)((Integer.parseInt(Character.toString(pers.charAt(i))))*2/10) + "  minus  tio  "
					//								+( (Integer.parseInt(Character.toString(pers.charAt(i)))*2)-((int)((Integer.parseInt(Character.toString(pers.charAt(i))))*2/10))));
				}
				else{
					tot+=nr.get(i)*2;
				}
			}
			else{		
				tot+=nr.get(i);
			}


		}
		System.out.println(nr);
		if(tot>10){
			svar=10-Integer.parseInt(Character.toString(Integer.toString(tot).charAt(1)));
		}
		else{
			svar=10-tot;
		}
		System.out.println(svar);
		System.err.println(pers.charAt(pers.length()-1));
		if(svar==Integer.parseInt(Character.toString(pers.charAt(pers.length()-1)))){
			JOptionPane.showMessageDialog(null, "Personnummret st�mmer", "St�mmer",JOptionPane.INFORMATION_MESSAGE,GoJbGoodies.Bild("/images/Done.jpg"));
		}
		else{
			JOptionPane.showMessageDialog(null, "Personnummret st�mmer INTE!! \nSista siffran skall vara "+svar, "ST�MMER EJ!!",JOptionPane.WARNING_MESSAGE,GoJbGoodies.Bild("/images/X.jpg"));
		}
		System.exit(3);
	}
}
