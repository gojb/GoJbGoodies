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

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class AnalyzeImage {
	
	public static void main(String[] args) {
		
		int[] sizes = new int[]{58,120,20,29,40,60,80,76,180,87,167,152,1024};
		
		for(int i : sizes){
			String path = "/Users/glenn/Documents/Double-Up/AppIcon/icon2.0" + i + ".png";
			new AnalyzeImage(path);
		}
	}
	
	public AnalyzeImage(String path){
		try {
			BufferedImage img = ImageIO.read(new File(path));
			System.out.println(path + " " + img.getHeight() + ", " + img.getWidth());
			for(int i=0;i<img.getWidth();i++) {
				for (int i1 = 0; i1 < img.getHeight(); i1++) {
					
					int alpha = new Color(img.getRGB(i, i1)).getAlpha();
					if(alpha < 255){
						System.out.println("Alpha at (" + i + ", " + i1 + ") is " + alpha);
					}
					else{
						//System.out.println("No alpha");
					}
					
				}
			}
			
			
		} catch (Exception e) {
			System.err.println("ERROR");
			System.exit(3);
		}
	}
}
