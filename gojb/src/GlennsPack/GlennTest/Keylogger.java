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

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextArea;

import GoJbFrame.GoJbFrame;

public class Keylogger implements KeyListener{

	 GoJbFrame frame = new GoJbFrame("");
	 JTextArea area = new JTextArea();
	 
	public static void main(String[] args) {
		new Keylogger();
	}
	public Keylogger() {

		 frame.add(area);
		 area.addKeyListener(this);
		 area.requestFocus();
		 frame.revalidate();
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// FIXME Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// FIXME Auto-generated method stub
		System.out.println(e.getModifiersEx());
		frame.setTitle(e.getKeyText(e.getKeyCode()));
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// FIXME Auto-generated method stub
		
	}

}
