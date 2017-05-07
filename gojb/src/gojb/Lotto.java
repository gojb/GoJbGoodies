package gojb;

import java.awt.FlowLayout;
import java.util.*;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

public class Lotto {
	ArrayList<ArrayList<Integer>> list = new ArrayList<>();
	Random random = new Random();
	int försök;
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
		optionPane.setMessage(new Object[] { "Hur många rader vi du spela med? ", panel });
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

		System.out.println("Välkommen till GoJbs lotto! Du fick de slumpade raderna "+list);
		System.err.println("beräknar...");
		System.out.println();
		int[] rätt=new int[8];
		while (rätt[7]==0) {
			int antalrätt=0;
			ArrayList<Integer> list2 = new ArrayList<>();
			for (int i = 0; i < 7; i++) {
				list2.add(rand(list2));
			}

			for (ArrayList<Integer> arrayList : list) {/* De olika raderna*/
				for (int i = 0; i < 7; i++) {/* Rätta raden*/
					for (int j = 0; j < 7; j++) {
						if (arrayList.get(i)==list2.get(j)) {
							antalrätt++;
						}
					}
				}
				rätt[antalrätt]++;
				antalrätt=0;

			}
			försök++;

		}
		System.err.println("Det tog "+försök+" spelomgångar innan din rad dök upp. Under försöken fick du:");
		for (int i = 0; i < 7; i++) {
			System.out.println(i +" rätt " + rätt[i] + " gånger.");
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
