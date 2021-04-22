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

import GoJbFrame.GoJbFrame;

import javax.swing.*;
import java.awt.*;

import static gojb.GoJbGoodies.typsnitt;
import static java.awt.Color.*;

public class Clock {
	
	GoJbFrame frame;
	
	private final int middlePoint = 250;
	
	public static void main(String[] args) {
	    new Clock();
	}
	
	public Clock(){
		frame = new GoJbFrame();
		frame.add(label);
		
		frame.revalidate();
	}
	
	private JLabel label = new JLabel(){
		protected void paintComponent(Graphics g) {
			//Midpoint
			g.drawOval(middlePoint - 1, middlePoint - 1, 2,2);
			
			g.drawOval(middlePoint - 100, middlePoint - 100, 200, 200);
			
			double time = 2;
			
			double timeRad = (2 * Math.PI * time) / 12;
			
			int endX = (int) (Math.cos(timeRad - (Math.PI / 2d)) * 1000);
			int endY = (int) (Math.sin(timeRad + (Math.PI / 2d)) * 1000);
			
			g.drawLine(middlePoint, middlePoint,middlePoint + endX, middlePoint + endY);
			
		}
	};
	
}































