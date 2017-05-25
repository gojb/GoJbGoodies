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

package test;

import static gojb.GoJbGoodies.prop;
import static gojb.GoJbGoodies.sparaProp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import GoJbFrame.GoJbFrame;
import gojb.GoJbGoodies;

public class imageScan {
	ArrayList<String> nr = new ArrayList<>();
	boolean bol = false;
	int sparI;
	long millis;

	public static void main(String[] args) {
		// FIXME Auto-generated method stub
		new imageScan();
	}
	public  imageScan() {
		millis=System.currentTimeMillis();
		BufferedImage img = null;
		try {
			img = ImageIO.read(GoJbGoodies.class.getResource("/images/swe.jpg"));
		} catch (IOException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}
		for(int i=0;i<img.getHeight();i++){
			for(int i1=0;i1<img.getWidth();i1++){
				String rgb = new String(new Color(img.getRGB(i1, i)).getRed()+"*"+new Color(img.getRGB(i1, i)).getGreen()+"*"+new Color(img.getRGB(i1, i)).getBlue());	
				bol=false;
//				for(int i2=0;i2<nr.size();i2++){
					//				System.out.println(rgb);
					if(nr.indexOf(rgb)!=-1){
						int i2=nr.indexOf(rgb);
						sparI=i2;
						bol=true;
						break;
					}
					
//				}
				if(bol){
					nr.set(sparI, nr.get(sparI)+"_"+i1+";"+i);
				}
				else{
					nr.add(rgb+"|_"+i1+";"+i);
					System.err.println("sdosdauidas" + rgb + " x = "+i1+"___ y= "+i);
				}

			}	
		}
		System.out.println(nr);
		prop.setProperty("Bild", nr.toString());
		sparaProp("Bild");
		
		GoJbFrame frame = new GoJbFrame(false);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setSize(img.getWidth(),img.getHeight());
		frame.getContentPane().setBackground(Color.blue);
		JLabel label = new JLabel(){
			private static final long serialVersionUID = 1L;

		@Override
			protected void paintComponent(Graphics g) {
			// FIXME Auto-generated method stub
					super.paintComponent(g);
			System.err.println("aaads");
			g.setColor(Color.red);
//			g.fillRect(0, 0, getWidth(), getHeight());
			System.out.println(getWidth() + " = w ___ h = "+getHeight());
			for(int i=0;i<nr.size();i++){

				System.out.println();
				String färg = nr.get(i).substring(0, nr.get(i).indexOf("|"));
				System.out.println(färg);
				//			System.err.println(färg.split("\\*")[0].toString());
				g.setColor(new Color(Integer.parseInt(färg.split("\\*")[0]), Integer.parseInt(färg.split("\\*")[1]), Integer.parseInt(färg.split("\\*")[2])));
				System.out.println(g.getColor());
				System.out.println(nr.get(i).split("_").length);
				for(int i1=1;i1<nr.get(i).split("_").length;i1++){

					//				System.out.println(nr.get(i).split("_")[1].split(";")[0]+" = x ___ y = "+nr.get(i).split("_")[1].split(";")[1]);
					//Andra for loopen ska kolla antal pixlar i just det indexet, alltså antalet objekt det blir i en lista som skapas av uppdelning av _.
					//Dock bör nog length-1 användas, då färgen innan första _ kommer att bli med. Sedan ska färgen identifieras mha split på *, och
					//tillslut ska r,g,b,x,y ha varsit värde, vilket passar

					int x = Integer.parseInt(nr.get(i).split("_")[i1].split(";")[0]), y = Integer.parseInt(nr.get(i).split("_")[i1].split(";")[1]);

					g.fillRect(x, y, 1, 1);
					if(i1%1000==0){
						System.err.println(i1);

					}
				}
			}
//			g.drawString("Klar", 50, 50);
			System.out.println("---------------KLARR!!------------------------");
			System.out.println("Det tog "+(int)((System.currentTimeMillis()-millis)/1000) +" sekunder och " + ((System.currentTimeMillis()-millis)%1000)+" millisekunder");
		}};
//		label.setBackground(Color.green);
		frame.add(label);
//		frame.revalidate();

		//	GoJbGoodies.vänta(10000);
		//	System.exit(3);

	}
}
