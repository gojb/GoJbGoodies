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

package spel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Timer;

import javax.swing.JLabel;

import GoJbFrame.GoJbFrame;

public class Pacman implements KeyListener{
	enum Riktning {UPP, NER, HÖGER, VÄNSTER,STOPP};
	Riktning riktning;
	int öppning,pacX=1,pacY,pacSize=50;
	boolean öka = true;
	GoJbFrame frame = new GoJbFrame("Pacman");

	
	JLabel label = new JLabel(){
		private static final long serialVersionUID = 1L;
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			int gap = 0;
			if (riktning==Riktning.UPP) {
				gap=90;
			}
			if (riktning==Riktning.VÄNSTER) {
				gap=180;
			}
			if (riktning==Riktning.NER) {
				gap=270;
			}
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(Color.yellow);
			g2.fillArc(pacX, pacY, pacSize, pacSize, öppning+gap, 360-(öppning*2));
		}
	};
	Timer timer = new Timer(5, e-> {
		int speed=2;
		if (riktning==Riktning.UPP) {
			pacY -=speed;
		}
		else if (riktning==Riktning.NER) {
			pacY +=speed;
		}
		else if (riktning==Riktning.HÖGER) {
			pacX +=speed;
		}
		else if (riktning==Riktning.VÄNSTER) {
			pacX -=speed;
		}
		if (pacX<0){
			riktning=Riktning.STOPP;
			pacX=0;
		}
		if (pacY<0){
			riktning=Riktning.STOPP;
			pacY=0;
		}
		if(pacX+pacSize>label.getWidth()){
			riktning=Riktning.STOPP;
			pacX=label.getWidth()-pacSize;
		}
		if(pacY+pacSize>label.getHeight()){
			riktning=Riktning.STOPP;
			pacY=label.getHeight()-pacSize;
		}
		if(riktning!=Riktning.STOPP){
			if (öka) {
				öppning+=2;
				if (öppning>38) {
					öka = false;
				}
			}
			else {
				öppning-=2;
				if (öppning<1) {
					öka=true;
				}
			}
			frame.repaint();
		}
		
	}); 
	public Pacman() {
		frame.add(label);
		frame.revalidate();
		timer.start();
		frame.addKeyListener(this);
		label.setOpaque(true);
		label.setBackground(Color.black);
	}
	public static void main(String[] args) {
		new Pacman();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
			riktning=Riktning.HÖGER;
		}
		else if (e.getKeyCode()==KeyEvent.VK_LEFT) {
			riktning=Riktning.VÄNSTER;
		}
		else if (e.getKeyCode()==KeyEvent.VK_UP) {
			riktning=Riktning.UPP;
		}
		else if (e.getKeyCode()==KeyEvent.VK_DOWN) {
			riktning=Riktning.NER;
		}

	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
