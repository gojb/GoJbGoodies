/*
 * Copyright 2017 GoJb Development
 *
 * Permission is hereby granted, free of charge, to any
 * person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software
 *  without restriction, including without limitation the rights to
 *  use, copy, modify, merge, publish, distribute, sublicense, and/or
 *  sell copies of the Software, and to permit persons to whom
 *  the Software is furnished to do so, subject to the following
 *  conditions:
 *
 * The above copyright notice and this permission notice shall
 * be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF
 * ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 * TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT
 * SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR
 * ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */

package GlennsPack.GlennTest;

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
