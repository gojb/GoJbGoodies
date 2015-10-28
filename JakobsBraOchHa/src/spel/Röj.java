package spel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import GoJbFrame.GoJbFrame;

public class Röj {

	GoJbFrame frame = new GoJbFrame("Röj");
	JButton[] button = new JButton[16*16];
	JMenu menu = new JMenu("Hey");
	JMenuBar mBar = new JMenuBar();
	JMenuItem res = new JMenuItem("Restart"), validate = new JMenuItem("Validate field");
	int[] a = new int[(16*16)];
	int[] a1= new int [16*16];
	int[] a2= new int[40];
	int[] revInt= new int[16*16];	
	int[] redButton = new int[16*16];
	double b,b1,x1,x,y1;
	int c, d, e, i, y, z, q, runOnce, nrRed, bomb;
	public static void main(String[] args) {
		new Röj();
	}

	public Röj() {
		frame.setLayout(new GridLayout(16,16));
		frame.setSize(700, 700);
		frame.setLocationRelativeTo(null);
		frame.setJMenuBar(mBar);
		mBar.add(res);
		res.addActionListener(e ->{
			new Röj();
			frame.dispose();
		});

		validate.addActionListener(e -> {
			mBar.remove(validate);
			for(int i = 0; i <button.length;i++){
				if(button[i].getBackground()==Color.red){
					button[i].setBackground(new Color(200,20,20,100));
				}
				else if(revInt[i]!=10){
					button[i].setBackground(new Color(200,200,200));
				}
				else if(revInt[i]==10){
					button[i].setBackground(new Color(230,230,230));
				}
				button[i].setText(a[i]+"");
				if(a[i]==0){
					button[i].setText(null);
				}
				if(a[i]==1){
					button[i].setForeground(new Color(255, 0, 0));
				}
				if(a[i]==2){
					button[i].setForeground(new Color(255, 0, 150));
				}
				if(a[i]==3){
					button[i].setForeground(new Color(255, 150, 0));
				}
				if(a[i]==4){
					button[i].setForeground(new Color(0, 100, 255));
				}
				if(a[i]==5){
					button[i].setForeground(new Color(0, 150, 90));
				}
				if(a[i]==6){
					button[i].setForeground(new Color(150, 0, 255));
				}
				if(a[i]==7){
					button[i].setForeground(new Color(120, 50, 100));
				}
				if(a[i]==8){
					button[i].setForeground(new Color(255, 255, 255));
				}
				if(a[i]>50){
					button[i].setText("B");
				}
			}
			button[bomb].setBackground(new Color(50, 50, 50));
		});
		for (int i = 0; i < button.length; i++) {
			redButton[i]=50;
			button[i]=new JButton();
			button[i].setSize(8,8);
			button[i].setBackground(new Color(166, 166, 166));
			button[i].setFocusPainted(false);
			button[i].addMouseListener(new MouseListener() {

				@Override
				public void mouseReleased(MouseEvent e) {
					// FIXME Auto-generated method stub

				}

				@Override
				public void mousePressed(MouseEvent e) {
					// FIXME Auto-generated method stub
					if(SwingUtilities.isRightMouseButton(e)){

						if(runOnce==10){
							if(redButton[Arrays.asList(button).indexOf((JButton)e.getSource())]==-50){
								redButton[Arrays.asList(button).indexOf((JButton)e.getSource())]*=-1;
								button[Arrays.asList(button).indexOf((JButton)e.getSource())].setBackground(new Color(166, 166, 166));
							}
							else if(redButton[Arrays.asList(button).indexOf((JButton)e.getSource())]==50){
								redButton[Arrays.asList(button).indexOf((JButton)e.getSource())]*=-1;



								System.err.println(("RIGHT"));
								button[Arrays.asList(button).indexOf((JButton)e.getSource())].setBackground(Color.red);
								for(int i = 0;i<button.length;i++){
									if(button[i].getBackground()== Color.red){
										nrRed++;
										if(nrRed<=41){
											a2[nrRed--]=i;
										}
									}
								}
								if(nrRed==41){
									nrRed=0;
									for(int i = 0; i < 41;i++){	
										if(a[a2[i]]==100){
											nrRed++;
										}
									}
									if(nrRed==40){
										Win();
									}
								}
							}
						}
					}
					else if(button[Arrays.asList(button).indexOf((JButton)e.getSource())].getBackground() != Color.red){

						if(runOnce!=10){
							runOnce=10;
							for (int i = 0; i <= 40; i++) {
								int x = new Random().nextInt(16*16);
								if(a[x]!=100&&x!=Arrays.asList(button).indexOf((JButton)e.getSource())&&(x-17!=Arrays.asList(button).indexOf((JButton)e.getSource())
										&&x-16!=Arrays.asList(button).indexOf((JButton)e.getSource())&&x-15!=Arrays.asList(button).indexOf((JButton)e.getSource())
										&&x-1!=Arrays.asList(button).indexOf((JButton)e.getSource())&&x+1!=Arrays.asList(button).indexOf((JButton)e.getSource())
										&&x+15!=Arrays.asList(button).indexOf((JButton)e.getSource())&&x+16!=Arrays.asList(button).indexOf((JButton)e.getSource())
										&&x+17!=Arrays.asList(button).indexOf((JButton)e.getSource()))){
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
						click(Arrays.asList(button).indexOf((JButton)e.getSource()));

					}

				}

				@Override
				public void mouseExited(MouseEvent e) {
					// FIXME Auto-generated method stub

				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// FIXME Auto-generated method stub

				}

				@Override
				public void mouseClicked(MouseEvent e) {
					// FIXME Auto-generated method stub

				}
			});
			frame.add(button[i]);
		}
		frame.revalidate();

	}

	public void click(int i){

		if(a1[i]!=10){
			a1[i]=10;
			if(a[i]<50){

				button[i].setBackground(new Color(230, 230, 230));
				button[i].setText(Integer.toString(a[i]));
				if(a[i]==1){
					button[i].setForeground(new Color(255, 0, 0));
				}
				if(a[i]==2){
					button[i].setForeground(new Color(255, 0, 150));
				}
				if(a[i]==3){
					button[i].setForeground(new Color(255, 150, 0));
				}
				if(a[i]==4){
					button[i].setForeground(new Color(0, 100, 255));
				}
				if(a[i]==5){
					button[i].setForeground(new Color(0, 150, 90));
				}
				if(a[i]==6){
					button[i].setForeground(new Color(150, 0, 255));
				}
				if(a[i]==7){
					button[i].setForeground(new Color(120, 50, 100));
				}
				if(a[i]==8){
					button[i].setForeground(new Color(255, 255, 255));
				}
				if(a[i]==0){	
					button0(i);
				}
			}
			else{
				GameOver(i);
			}
		}
	}
	public void Win() {
		
		for(int i1 = 0; i1 < button.length;i1++){
			button[i1].setBackground(Color.white);
			if(a1[i1]==10){
				revInt[i1]=10;
			}
			a1[i1]=10;
		}
		
		//Skriver Smiley
		//Rad 4
		button[63+6].setBackground(Color.black);
		button[63+11].setBackground(Color.black);

		//Rad 5
		button[79+6].setBackground(Color.black);
		button[79+11].setBackground(Color.black);

		//Rad 7
		button[95+4].setBackground(Color.black);
		button[95+13].setBackground(Color.black);

		//Rad 8
		button[111+4].setBackground(Color.black);
		button[111+13].setBackground(Color.black);

		//Rad 9
		button[127+4].setBackground(Color.black);
		button[127+13].setBackground(Color.black);

		//Rad 10
		button[143+5].setBackground(Color.black);
		button[143+12].setBackground(Color.black);

		//Rad 11
		button[159+6].setBackground(Color.black);
		button[159+7].setBackground(Color.black);
		button[159+8].setBackground(Color.black);
		button[159+9].setBackground(Color.black);
		button[159+10].setBackground(Color.black);
		button[159+11].setBackground(Color.black);
		
		
	}
	public void GameOver(int i){

		//Skriver Game Over
		//Rad 2
		button[15+1].setBackground(Color.black);
		button[15+2].setBackground(Color.black);
		button[15+5].setBackground(Color.black);
		button[15+6].setBackground(Color.black);
		button[15+7].setBackground(Color.black);
		button[15+9].setBackground(Color.black);
		button[15+10].setBackground(Color.black);
		button[15+12].setBackground(Color.black);
		button[15+13].setBackground(Color.black);
		button[15+15].setBackground(Color.black);
		button[15+16].setBackground(Color.black);

		//Rad 3
		button[31+1].setBackground(Color.black);
		button[31+5].setBackground(Color.black);
		button[31+7].setBackground(Color.black);
		button[31+9].setBackground(Color.black);
		button[31+11].setBackground(Color.black);
		button[31+13].setBackground(Color.black);
		button[31+15].setBackground(Color.black);

		//Rad 4
		button[47+1].setBackground(Color.black);
		button[47+5].setBackground(Color.black);
		button[47+6].setBackground(Color.black);
		button[47+7].setBackground(Color.black);
		button[47+9].setBackground(Color.black);
		button[47+13].setBackground(Color.black);
		button[47+15].setBackground(Color.black);
		button[47+16].setBackground(Color.black);

		//Rad 5
		button[63+1].setBackground(Color.black);
		button[63+3].setBackground(Color.black);
		button[63+5].setBackground(Color.black);
		button[63+7].setBackground(Color.black);
		button[63+9].setBackground(Color.black);
		button[63+13].setBackground(Color.black);
		button[63+15].setBackground(Color.black);

		//Rad 6
		button[79+1].setBackground(Color.black);
		button[79+2].setBackground(Color.black);
		button[79+3].setBackground(Color.black);
		button[79+5].setBackground(Color.black);
		button[79+7].setBackground(Color.black);
		button[79+9].setBackground(Color.black);
		button[79+13].setBackground(Color.black);
		button[79+15].setBackground(Color.black);
		button[79+16].setBackground(Color.black);

		//Rad 7
		button[95+3].setBackground(Color.black);

		//Rad 9
		button[127+2].setBackground(Color.black);
		button[127+3].setBackground(Color.black);
		button[127+6].setBackground(Color.black);
		button[127+8].setBackground(Color.black);
		button[127+10].setBackground(Color.black);
		button[127+11].setBackground(Color.black);
		button[127+13].setBackground(Color.black);
		button[127+14].setBackground(Color.black);
		button[127+15].setBackground(Color.black);

		//Rad 10
		button[143+1].setBackground(Color.black);
		button[143+4].setBackground(Color.black);
		button[143+6].setBackground(Color.black);
		button[143+8].setBackground(Color.black);
		button[143+10].setBackground(Color.black);
		button[143+13].setBackground(Color.black);
		button[143+16].setBackground(Color.black);

		//Rad 11
		button[159+1].setBackground(Color.black);
		button[159+4].setBackground(Color.black);
		button[159+6].setBackground(Color.black);
		button[159+8].setBackground(Color.black);
		button[159+10].setBackground(Color.black);
		button[159+13].setBackground(Color.black);
		button[159+16].setBackground(Color.black);

		//Rad 12
		button[175+1].setBackground(Color.black);
		button[175+4].setBackground(Color.black);
		button[175+6].setBackground(Color.black);
		button[175+8].setBackground(Color.black);
		button[175+10].setBackground(Color.black);
		button[175+13].setBackground(Color.black);
		button[175+14].setBackground(Color.black);
		button[175+15].setBackground(Color.black);
		button[175+11].setBackground(Color.black);

		//Rad 13
		button[191+1].setBackground(Color.black);
		button[191+4].setBackground(Color.black);
		button[191+6].setBackground(Color.black);
		button[191+8].setBackground(Color.black);
		button[191+10].setBackground(Color.black);
		button[191+13].setBackground(Color.black);
		button[191+14].setBackground(Color.black);

		//Rad 14
		button[207+1].setBackground(Color.black);
		button[207+4].setBackground(Color.black);
		button[207+6].setBackground(Color.black);
		button[207+8].setBackground(Color.black);
		button[207+10].setBackground(Color.black);
		button[207+13].setBackground(Color.black);
		button[207+15].setBackground(Color.black);

		//Rad 15
		button[223+2].setBackground(Color.black);
		button[223+3].setBackground(Color.black);
		button[223+7].setBackground(Color.black);
		button[223+10].setBackground(Color.black);
		button[223+11].setBackground(Color.black);
		button[223+13].setBackground(Color.black);
		button[223+16].setBackground(Color.black);

		button[i].setBackground(new Color(50, 50, 50));
		bomb=i;
		for(int i1 = 0; i1 < button.length;i1++){
			if(a1[i1]==10){
				revInt[i1]=10;
			}
			a1[i1]=10;
		}
		mBar.add(validate);
		frame.revalidate();

	}
	public void button0(int i){

		a1[i]=10;

		b=(i+16)/16d;
		y =(int)b; //y = radnummer, alltså 1 - 16
		x = (((double)b-(double)y)*16d)+1d; //x = kolumnnummer, alltså 0 - 15

		for(int q = -17; q<18;){
			if(i+q>=0&&i+q<=16*16-1){

				b1=(((double)i+q)+16)/16d;
				y1 = Math.floor(b1); //y = radnummer, alltså 1 - 16
				x1 = (((double)b1-(double)y1)*16d)+1d; //x = kolumnnummer, alltså 0 - 15

				//				if(((x==1&&(q!=-17&&q!=15&&q!=-1))||(x==16&&(q!=-15&&q!=17&&q!=1)))||(x>1&&x<16)){
				//				System.err.println(x1+" == x1");
				//				System.out.println(y1);
				System.out.println("X = "+x +"  x1= "+x1+"  x-x1= "+(int)(x-x1) + "  True? = "+((x-x1<7)&&(x-x1>-7)));

				if(((x-x1<7)&&(x-x1>-7))) {
					System.out.println(q);


					if(!button[i+q].getBackground().equals(new Color(230, 230, 230))){
						button[i+q].setBackground(new Color(230, 230, 230));
						button[i+q].setText(Integer.toString(a[i+q]));
						if(a[i+q]==1){
							button[i+q].setForeground(new Color(255, 0, 0));
							a1[i+q]=10;
						}
						if(a[i+q]==2){
							button[i+q].setForeground(new Color(255, 0, 150));
							a1[i+q]=10;
						}
						if(a[i+q]==3){
							button[i+q].setForeground(new Color(255, 150, 0));
							a1[i+q]=10;
						}
						if(a[i+q]==4){
							button[i+q].setForeground(new Color(0, 100, 255));
							a1[i+q]=10;
						}
						if(a[i+q]==5){
							button[i+q].setForeground(new Color(0, 150, 90));
							a1[i+q]=10;
						}
						if(a[i+q]==6){
							button[i+q].setForeground(new Color(150, 0, 255));
							a1[i+q]=10;
						}
						if(a[i+q]==7){
							button[i+q].setForeground(new Color(120, 50, 100));
							a1[i+q]=10;
						}
						if(a[i+q]==8){
							button[i+q].setForeground(new Color(255, 255, 255));
							a1[i+q]=10;
						}
						button[i].setText(Integer.toString(a[i]));
						if(a[i]==0){
							button[i].setText(null);
						}
						if(a[i+q]==0){
							click(q+i);
						}
					}
					//					}
				}
			}

			if(q==-15||q==1){
				q+=14;
			}
			else{
				q++;
			}
		}

	}

}
