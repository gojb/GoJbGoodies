package gojb;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import GoJbFrame.GoJbFrame;

public class Tetris {
	private GoJbFrame frame = new GoJbFrame("Tetris",false,JFrame.EXIT_ON_CLOSE);
	private int size=20,fönsterbredd=12,fönsterhöjd=20,poäng;
	private Timer timer = new Timer(500, e-> uppdatera());
	private boolean snabb;
	private ArrayList<Block> aktuella = new ArrayList<>(), fasta = new ArrayList<>();

	private JLabel label = new JLabel(){
		private static final long serialVersionUID = 1;
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			for (Block block : aktuella) {
				block.rita(g2);
			}
			for (Block fast : fasta) {
				fast.rita(g2);
			}
			g2.drawString(poäng+"", 20, 20);
		};
	};

	private KeyListener listener = new KeyListener() {
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
				höger();
			}
			else if (e.getKeyCode()==KeyEvent.VK_LEFT) {
				vänster();
			}
			else if (e.getKeyCode()==KeyEvent.VK_DOWN) {
				snabb=true;
				for (int i = 0; i < fönsterhöjd; i++) {
					uppdatera();
				}
				snabb=false;
			}
			else if (e.getKeyCode()==KeyEvent.VK_UP) {
				ArrayList<Block> old = new ArrayList<>();
				for (Block block : aktuella) {
					try {
						old.add(block.clone());
					} catch (CloneNotSupportedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (upp().equals("fel")) {
					aktuella.clear();
					aktuella.addAll(old);
				}
			}
			else if (e.getKeyCode()==KeyEvent.VK_R) {
				fasta.clear();
				aktuella.clear();
				timer.start();
				poäng=0;
			}
			frame.repaint();
		}
		public void keyTyped(KeyEvent e) {}
	};
	public boolean vänster() {
		for (Block block : aktuella) {
			if (block.x-1<0) {
				return false;
			}
			for (Block block2 : fasta) {
				if (block.x-1==block2.x&&block.y==block2.y) {
					return false;
				}
			}
		}
		for (Block block : aktuella) {
			block.flyttaVänster();
		}
		return true;
	}
	public boolean höger() {
		for (Block block : aktuella) {
			if (block.x+1==fönsterbredd) {
				return false;
			}
			for (Block block2 : fasta) {
				if (block.x+1==block2.x&&block.y==block2.y) {
					return false;
				}
			}
		}
		for (Block block : aktuella) {
			block.flyttaHöger();
		}
		return true;
	}
	public String upp(){
		String ret="ok";
		int xMin,yMin;
		xMin=aktuella.get(0).x-2;
		yMin=aktuella.get(0).y-2;
		for (Block block : aktuella) {
			int x = block.x-xMin;
			int y = block.y-yMin;
			block.x=4-y+xMin;
			block.y=x+yMin;
		}
		for (Block block : aktuella) {
			while (block.x<0) {
				höger();
				System.out.println("höger");
			}
			while (block.x>=fönsterbredd) {
				vänster();
				System.out.println("vänst");
			}
			for (Block block2 : fasta) {
				if (block.x==block2.x&&block.y==block2.y) {
					ret="fel";
					System.err.println("err");
				}
			}
		}
		return ret;
	}
	public void uppdatera() {
		boolean b = false;
		for (Block aktuell : aktuella) {
			for (Block fast : fasta) {
				if (aktuell.y+1==fast.y&&aktuell.x==fast.x) {	
					b=true;
				}
			}
			if (aktuell.y+1==fönsterhöjd) {
				b=true;
			}
		}
		if (b) {
			fasta.addAll(aktuella);
			aktuella.clear();
		}
		else {
			for (Block block : aktuella) {
				block.flyttaNer();
			}
		}
		if (aktuella.isEmpty()&&!snabb) {
			blockskap();
			for (Block aktuell : aktuella) {
				for (Block fast : fasta) {
					if (aktuell.y+1==fast.y&&aktuell.x==fast.x) {	
						gameover();
						frame.repaint();
						return;
					}
				}
			}
		}
		int[] y = new int[fönsterhöjd];
		for (Block block : fasta) {
			y[block.y]++;
		}
		ArrayList<Block> bort = new ArrayList<>();
		for (int j = 0; j < y.length; j++) {
			if (y[j]==fönsterbredd) {
				for (Block block : fasta) {
					if (block.y==j) {
						bort.add(block);
					}
					if (block.y<j) {
						block.flyttaNer();
					}
				}
				poäng++;
			}

		}
		fasta.removeAll(bort);
		if (!snabb) {
			frame.repaint();
		}

	}
	private void gameover() {
		timer.stop();
		System.err.println("gameover");
	}
	public Tetris() {
		frame.setResizable(false);
		frame.add(label);
		frame.setLayout(new GridLayout(1,1));
		frame.addKeyListener(listener);
		label.setPreferredSize(new Dimension(size*fönsterbredd, size*fönsterhöjd));
		frame.revalidate();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		timer.start();
	}
	public static void main(String[] args) {
		new Tetris();
	}
	public void blockskap() {
		Random random = new  Random();
		int i =	random.nextInt(7);
		if (i==0) {
			aktuella.add(new Block(fönsterbredd/2, 0, Color.red));
			aktuella.add(new Block(fönsterbredd/2-2, 0, Color.red));
			aktuella.add(new Block(fönsterbredd/2-1, 0, Color.red));
			aktuella.add(new Block(fönsterbredd/2+1, 0, Color.red));
		}
		else if (i==1) {
			aktuella.add(new Block(fönsterbredd/2, 0, Color.green));
			aktuella.add(new Block(fönsterbredd/2, -1, Color.green));
			aktuella.add(new Block(fönsterbredd/2, 1, Color.green));
			aktuella.add(new Block(fönsterbredd/2+1, 1, Color.green));

		}
		else if (i==2) {
			aktuella.add(new Block(fönsterbredd/2, 1, Color.magenta));
			aktuella.add(new Block(fönsterbredd/2, 0, Color.magenta));
			aktuella.add(new Block(fönsterbredd/2, 2, Color.magenta));
			aktuella.add(new Block(fönsterbredd/2-1, 2, Color.magenta));
		}
		else if (i==3) {
			aktuella.add(new Block(fönsterbredd/2, 0, Color.BLUE));
			aktuella.add(new Block(fönsterbredd/2, 1, Color.BLUE));
			aktuella.add(new Block(fönsterbredd/2+1, 0, Color.BLUE));
			aktuella.add(new Block(fönsterbredd/2+1, 1, Color.BLUE));
		}
		else if (i==4) {
			aktuella.add(new Block(fönsterbredd/2, 1, Color.CYAN));
			aktuella.add(new Block(fönsterbredd/2, 0, Color.CYAN));
			aktuella.add(new Block(fönsterbredd/2+1, 1, Color.CYAN));
			aktuella.add(new Block(fönsterbredd/2+1, 2, Color.cyan));
		}
		else if (i==5) {
			aktuella.add(new Block(fönsterbredd/2, 0, Color.orange));
			aktuella.add(new Block(fönsterbredd/2, 1, Color.orange));

			aktuella.add(new Block(fönsterbredd/2+1, 0, Color.orange));
			aktuella.add(new Block(fönsterbredd/2+1, -1, Color.orange));
		}
		else if (i==6) {
			aktuella.add(new Block(fönsterbredd/2, 0, Color.YELLOW));
			aktuella.add(new Block(fönsterbredd/2, -1, Color.yellow));
			aktuella.add(new Block(fönsterbredd/2, 1, Color.yellow));
			aktuella.add(new Block(fönsterbredd/2+1, 0, Color.yellow));
		}
	}
	class Block implements Cloneable{
		Color c;
		int x,y;
		
		@Override
		protected Block clone() throws CloneNotSupportedException {
			return (Block) super.clone();
		}
		public Block(int x,int y, Color c) {
			this.x=x;
			this.y=y;
			this.c=c;
		}
		public void flyttaVänster() {
			x-=1;
		}
		public void flyttaHöger() {
			x+=1;
		}
		public void rita(Graphics2D g) {
			g.setColor(c);
			g.fillRect(x*size, y*size, size, size);
			g.setColor(Color.black);
			g.drawRect(x*size, y*size, size, size);

		}
		public void flyttaNer() {
			y+=1;
		}
	}
}
