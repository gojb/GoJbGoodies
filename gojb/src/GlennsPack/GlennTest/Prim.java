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

import java.util.ArrayList;
import java.util.Collections;

public class Prim {

	ArrayList<Long> list = new ArrayList<>();
	int a;
	Boolean prim =true;

	public static void main(String[] args) {
		// FIXME Auto-generated method stub
//		new Prim();
		new Prim(21474836470l);
	}
	public Prim(){
		long millis=System.currentTimeMillis();
		list.add(2l);
		int top;
		top=1500000;
		for(int b=3;b<top;b+=2){
			int sq = (int)Math.sqrt(b);
			System.out.println((top-b) + " loopar kvar");
			prim=true;
			for(int i=2;i<sq+1;i++){
				if(b%i==0){
					//Inte prim
					prim=false;
				}			
			}
			if(prim==true){
				list.add((long)b);
			}
		}
		for(int i = 0;i<list.size();i++){
			System.err.println(list.get(i));
		}
		System.out.println(list.size() + " många primtal mellan 0 och "+top);
		long currentMillis=(System.currentTimeMillis()-millis);
		System.out.println("Det tog " + currentMillis + " millisekunder, eller " + (currentMillis/1000)+" sekunder");
	}
	public Prim(Long b) {
		int sq = (int)Math.sqrt(b);
		for(int i=2;i<sq+1;i++){
			if(b%i==0){
				prim=false;
				list.add((long)i);
				list.add(b/i);
			}
		}
		if(prim==true){
			System.err.println("PRIMTAL");
		}
		else{
			Collections.sort(list);
			System.out.println(list);
		}
	}
		
}
