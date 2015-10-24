package gojb;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Random;

import javax.swing.JButton;

import GoJbFrame.GoJbFrame;

public class Röj implements ActionListener{

	GoJbFrame frame = new GoJbFrame("Röj");
	JButton[] button = new JButton[16*16];
	int[] a = new int[(16*16)];
	double b;
	int c, d, e, i, x, y, z;
	public static void main(String[] args) {
		new Röj();
	}
	public void button0(int i){
		
		//Kollar om de har vit bakrund innan de "klickas", så att de inte tas igen
		
		b=(i+16)/16;
		y = (int)b; //y = radnummer, alltså 1 - 16
		x = i+1-(((int)i/16)*16); //x = kolumnnummer, alltså 0 - 15

		if(y > 1){
//			if(f!=-16){
				button[i-16].setText(Integer.toString(a[i-16]));
				button[i-16].setBackground(new Color(230, 230, 230));
//			}
			if(x>1){
//				if(f!=-17){
					button[i-17].setBackground(new Color(230, 230, 230));
					button[i-17].setText(Integer.toString(a[i-17]));
				}
//			}
			if(x<16){
//				if(f!=-15){
					button[i-15].setBackground(new Color(230, 230, 230));
					button[i-15].setText(Integer.toString(a[i-15]));
				}
//			}

		}
		if(y < 16){
//			if(f!=16){
				button[i+16].setText(Integer.toString(a[i+16]));
				button[i+16].setBackground(new Color(230, 230, 230));
//			}
			if(x>1){
//				if(f!=15){
					button[i+15].setBackground(new Color(230, 230, 230));
					button[i+15].setText(Integer.toString(a[i+15]));
//				}
			}
			if(x<16){
//				if(f!=17){
					button[i+17].setText(Integer.toString(a[i+17]));
					button[i+17].setBackground(new Color(230, 230, 230));
//				}
			}
		}
		if(x>1){
//			if(f!=-1){
				button[i-1].setText(Integer.toString(a[i-1]));
				button[i-1].setBackground(new Color(230, 230, 230));
//			}
		}
		if(x<16){
//			if(f!=1){
				button[i+1].setText(Integer.toString(a[i+1]));
				button[i+1].setBackground(new Color(230, 230, 230));
//			}
		}

		if(a[i+17]==0){
			button0(i+17);
		}
		if(a[i+16]==0){
			button0(i+16);
		}
		if(a[i+15]==0){
			button0(i+15);
		}
		if(a[i+1]==0){
			button0(i+1);
		}
		if(a[i-17]==0){
			button0(i-17);
		}
		if(a[i-16]==0){
			button0(i-16);
		}
		if(a[i-15]==0){
			button0(i-15);
		}
		if(a[i-1]==0){
			button0(i-1);
		}



		//    	if(a[Arrays.asList(button).indexOf((JButton)e.getSource())+1]==0||
		//    			a[Arrays.asList(button).indexOf((JButton)e.getSource())-1]==0||
		//    					a[Arrays.asList(button).indexOf((JButton)e.getSource())+16]==0||a[Arrays.asList(button).indexOf((JButton)e.getSource())+17]||
		//    	    	a[Arrays.asList(button).indexOf((JButton)e.getSource())+15]||a[Arrays.asList(button).indexOf((JButton)e.getSource())-16]||
		//    	    	a[Arrays.asList(button).indexOf((JButton)e.getSource())-15]==0||a[Arrays.asList(button).indexOf((JButton)e.getSource())-17]==0);
		//    	
	}
	public Röj() {
		frame.setLayout(new GridLayout(16,16));
		frame.setSize(675, 675);
		frame.setLocationRelativeTo(null);
		for (int i = 0; i < button.length; i++) {
			button[i]=new JButton();
			button[i].setSize(8,8);
			button[i].setBackground(new Color(166, 166, 166));
			button[i].setFocusPainted(false);
			button[i].addActionListener(e ->{
				if(a[Arrays.asList(button).indexOf((JButton)e.getSource())]<50){
					((JButton)e.getSource()).setBackground(new Color(230, 230, 230));
					((JButton)e.getSource()).setText(Integer.toString(a[Arrays.asList(button).indexOf((JButton)e.getSource())]));
					if(a[Arrays.asList(button).indexOf((JButton)e.getSource())]==0){
					
						button0(Arrays.asList(button).indexOf((JButton)e.getSource()));
					}


				}
				else{
					((JButton)e.getSource()).setBackground(new Color(0, 0, 0));
				}
			});
			frame.add(button[i]);
		}
		frame.revalidate();
		for (int i = 0; i <= 40; i++) {
			int x = new Random().nextInt(16*16-1);
			if(a[x]!=100){
				a[x]=100;
			}
			else{
				i--;
			}
		}
		for (int i=0;i<a.length;i++) {
			if(a[i]>50){
				//Om bomb
				b=(i+16)/16;
				y = (int)b; //y = radnummer, alltså 1 - 16
				x = i+1-(((int)i/16)*16); //x = kolumnnummer, alltså 0 - 15
				if(y > 1){
					a[i-16]+=1;
					if(x>1){
						a[i-17]+=1;
					}
					if(x<16){
						a[i-15]+=1;
					}
				}
				if(y < 16){
					a[i+16]+=1;
					if(x>1){
						a[i+15]+=1;
					}
					if(x<16){
						a[i+17]+=1;
					}
				}
				if(x>1){
					a[i-1]+=1;
				}
				if(x<16){
					a[i+1]+=1;
				}

			}
		}

	}
	@Override
	public void actionPerformed(ActionEvent e) {

	}

}
