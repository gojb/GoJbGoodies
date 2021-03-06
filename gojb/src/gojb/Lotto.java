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

package gojb;

import java.awt.FlowLayout;
import java.util.*;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class Lotto {
	ArrayList<ArrayList<Integer>> list = new ArrayList<>();
	Random random = new Random();
	int f�rs�k;
	JFormattedTextField field;
	public Lotto() {
		JOptionPane optionPane = new JOptionPane();
		JSlider slider = new JSlider();
		try {
			field = new JFormattedTextField(new MaskFormatter("###"));
			field.setColumns(3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		slider.addChangeListener(e -> {
			field.setText(slider.getValue()+"");
		});

		slider.setMinimum(0);
		slider.setMaximum(50000);
		slider.setMinorTickSpacing(1000);
		slider.setMajorTickSpacing(10000);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(slider);
		panel.add(field);
		optionPane.setMessage(new Object[] { "Hur m�nga rader vi du spela med? ", panel });
		optionPane.setMessageType(JOptionPane.QUESTION_MESSAGE);
		optionPane.setOptionType(JOptionPane.DEFAULT_OPTION);
		JDialog dialog = optionPane.createDialog(null, "My Slider");

		dialog.setVisible(true);
		if (slider.getValue()==0) {
			System.exit(3);
		}
		for (int i = 0; i < Integer.parseInt(field.getText().trim()); i++) {
			list.add(new ArrayList<Integer>());
			for (int j = 0; j < 7; j++) {
				list.get(i).add(rand(list.get(i)));
			}
			Collections.sort(list.get(i));
		}

		System.out.println("V�lkommen till GoJbs lotto! Du fick de slumpade raderna "+list);
		System.err.println("ber�knar...");
		System.out.println();
		int[] r�tt=new int[8];
		while (r�tt[7]==0) {
			int antalr�tt=0;
			ArrayList<Integer> list2 = new ArrayList<>();
			for (int i = 0; i < 7; i++) {
				list2.add(rand(list2));
			}

			for (ArrayList<Integer> arrayList : list) {/* De olika raderna*/
				for (int i = 0; i < 7; i++) {/* R�tta raden*/
					for (int j = 0; j < 7; j++) {
						if (arrayList.get(i)==list2.get(j)) {
							antalr�tt++;
						}
					}
				}
				r�tt[antalr�tt]++;
				antalr�tt=0;

			}
			f�rs�k++;

		}
		System.err.println("Det tog "+f�rs�k+" spelomg�ngar innan din rad d�k upp. Under f�rs�ken fick du:");
		for (int i = 0; i < 7; i++) {
			System.out.println(i +" r�tt " + r�tt[i] + " g�nger.");
		}
		System.exit(3);
	}
	public static void main(String[] args) {
		GoJbGoodies.main("gojb.Lotto");
	}
	public int rand(ArrayList<Integer> list) {
		int i = random.nextInt(35)+1;
		for (int i2 : list) {
			if (i==i2) {
				return rand(list);
			}
		}
		return i;
	}
}
