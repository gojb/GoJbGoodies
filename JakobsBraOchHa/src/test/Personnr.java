package test;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

import gojb.GoJbGoodies;

public class Personnr {

	static String pers = new String();
	static ArrayList<Integer> nr = new ArrayList<>();
	static Boolean bol = false, årBol = false;
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
		String år = JOptionPane.showInputDialog("Första 6 siffrorna", "ex 981103");
		while(!årBol){
			if(år.length()==6){
				try {
					Integer.parseInt(år);
					årBol=true;
				} catch (Exception e) {
					// FIXME: handle exception
					JOptionPane.showMessageDialog(null, "OBS!! Endast siffror");
					år = JOptionPane.showInputDialog("Första 6 siffrorna", år);
				}
			}
			else{
				JOptionPane.showMessageDialog(null, "OBS!! 6 siffror");
				år = JOptionPane.showInputDialog("Första 6 siffrorna", år);
			}}
		Object[] options = {"Man","Kvinna"};
		int kön = JOptionPane.showOptionDialog(null, "Man eller kvinna?", "Välj kön", JOptionPane.YES_NO_OPTION, 
				JOptionPane.DEFAULT_OPTION, null, options, options[0]);
		if(kön!=1&&kön!=0){
			System.exit(3);
		}
		Random random = new Random(), random1 = new Random(), random2 = new Random();
		int könNr;
		if(kön==0){
			könNr=2;
			while(könNr%2==0){
			könNr=random.nextInt(9);
			System.err.println(könNr);
			}
		}
		else{
			könNr=1;
			while(könNr%2!=0){
				könNr=random.nextInt(9);
			}
		}
		System.out.println("sdoudashu");
		//---------------
		
		int rand1=random1.nextInt(9), rand2=random2.nextInt(9);
		
		pers=år+Integer.toString(rand1)+Integer.toString(rand2)+Integer.toString(könNr);
		
		
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

		
		JOptionPane.showMessageDialog(null, "Personnummret är:\n"+år+"-"+rand1+rand2+könNr+svar);
		System.exit(3);
	}
	public static void Testa(){
		pers=JOptionPane.showInputDialog("Persnnummer:","Personnummer á 10 siffror");
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
			JOptionPane.showMessageDialog(null, "Personnummret stämmer", "Stämmer",JOptionPane.INFORMATION_MESSAGE,GoJbGoodies.Bild("/images/Done.jpg"));
		}
		else{
			JOptionPane.showMessageDialog(null, "Personnummret stämmer INTE!! \nSista siffran skall vara "+svar, "STÄMMER EJ!!",JOptionPane.WARNING_MESSAGE,GoJbGoodies.Bild("/images/X.jpg"));
		}
		System.exit(3);
	}
}
