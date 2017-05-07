package glennsPack.GlennTest;

public class footballCalc {

	public static void main(String[] args) {
		// FIXME Auto-generated method stub
		new footballCalc(29);
	}
	public footballCalc(int poäng){
	
		int max8 = Math.floorDiv(poäng,8);
		int max7 = Math.floorDiv(poäng,7);
		int max6 = Math.floorDiv(poäng,6);
		int max3 = Math.floorDiv(poäng,3);
		int max2 = Math.floorDiv(poäng,2);
		
		
		int g=0;
		
		for(int i=0;i<max8;i++){
			for(int j=0;j<max7;j++){
				for(int k=0;k<max6;k++){
					for(int l=0;l<max3;l++){
						for(int m=0;m<max2;m++){
							int prelScore=(i*8)+(j*7)+(k*6)+(l*3)+(m*2);
							
							g++;
							
//							System.out.println(prelScore);
							if(prelScore==poäng){
								System.out.println(i+" 8 , "+ j + " 7 , " + k + " 6, "+ l+ " 3, "+ m +" 2");
							}
							
						}	
					}	
				}	
			}	
		}
		
		System.out.println(g);
		
		
	}
}
