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

package GoJbFrame;
import static gojb.GoJbGoodies.autoListener;

import javax.swing.*;

/**
 * GoJbFrame är en vanlig JFrame fast med några inställningar förkonfigurerade 
 * bl.a. 
 * {@code
 * setSize,
 * setLocationRelativeTo,
 * setDefaultCloseOperation,
 * setVisible,
 * setIconImage.
 * }
 * @author GoJb - Glenn Olsson & Jakob Björns
 * 
 * @see <a href="http://gojb.bl.ee/">http://gojb.bl.ee/</a>
 * @version 1.0
 */

public class GoJbFrame extends JFrame{

	private static final long serialVersionUID = 168746L;


	public GoJbFrame() {
		this("", true,3);
	}
	public GoJbFrame(String title){
		
		this(title,true,3);
	}
	public GoJbFrame(Boolean visible){
		
		this("",visible,3);
	}
	public GoJbFrame(String title,Boolean visible){
		this(title, visible, 3);
	}
	public GoJbFrame(String title,Boolean visible, int close){
		setSize(500, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(close);
		setVisible(visible);
		setTitle(title);
		if(close==3){
		addWindowListener(autoListener);
		}
		try {
			setIconImage(new ImageIcon(getClass().getResource("/images/Java-icon.png")).getImage());
		} catch (Exception e) {
			System.err.println("Ikon saknas");
		}
	}
}
class Standard{
	public Standard(JFrame frame) {
		frame.setSize(500, 500);
		frame.setLocationRelativeTo(null);

		try {
			frame.setIconImage(new ImageIcon(getClass().getResource("/images/Java-icon.png")).getImage());
		} catch (Exception e) {
			System.err.println("Ikon saknas");
		}
	}
}
