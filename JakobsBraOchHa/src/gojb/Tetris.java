package gojb;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import GoJbFrame.GoJbFrame;

public class Tetris {
	private GoJbFrame frame = new GoJbFrame("Tetris");
	private int size=20,fönsterbredd=12,fönsterhöjd=20;
	private Timer timer = new Timer(500, e-> uppdatera());
	boolean snabb;
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
		};
	};

	private KeyListener listener = new KeyListener() {
		public void keyReleased(KeyEvent e) {}
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
				boolean blockera = false;
				for (Block block : aktuella) {
					if (block.x+1==fönsterbredd) {
						blockera=true;
					}
				}
				if (!blockera) {
					for (Block block : aktuella) {
						block.flyttaHöger();
					}
				}
				frame.repaint();
			}
			else if (e.getKeyCode()==KeyEvent.VK_LEFT) {
				boolean blockera = false;
				for (Block block : aktuella) {
					if (block.x<=0) {
						blockera=true;
					}
				}
				if (!blockera) {
					for (Block block : aktuella) {
						block.flyttaVänster();
					}
				}
			}
			else if (e.getKeyCode()==KeyEvent.VK_DOWN) {
				snabb=true;
				for (int i = 0; i < fönsterhöjd; i++) {
					uppdatera();
				}
				snabb=false;
				
			}
			else if (e.getKeyCode()==KeyEvent.VK_UP) {
				int xMax = 0,xMin=fönsterbredd,yMax=0,yMin=fönsterhöjd;
				for (Block block : aktuella) {
					if (block.x>xMax) {
						xMax=block.x;
					}
					if (block.x<xMin) {
						xMin=block.x;
					}
					if (block.y>yMax) {
						yMax=block.y;
					}
					if (block.y<yMin) {
						yMin=block.y;
					}
				}
				
				for (Block block : aktuella) {
					int x = block.x;
					block.x=4-block.y;
					block.y=x;
				}
			}
			frame.repaint();
		}
		public void keyTyped(KeyEvent e) {}
	};

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
		}
		int[] y = new int[fönsterhöjd];
		for (Block block : fasta) {
			y[block.y]++;
		}
		ArrayList<Block> blocks = new ArrayList<>();
		for (int j = 0; j < y.length; j++) {
			int i = y[j];
			if (i==fönsterbredd) {
				for (Block block : fasta) {
					if (block.y==j) {
						blocks.add(block);
					}
					if (block.y<j) {
						block.flyttaNer();
					}
				}
			}

		}
		fasta.removeAll(blocks);
		if (!snabb) {
			frame.repaint();
		}
		
	}
	public Tetris() {
		frame.setResizable(false);
		frame.add(label);
		frame.setLayout(new GridLayout(1,1));
		blockskap();
		frame.repaint();
		frame.revalidate();
		timer.start();
		frame.addKeyListener(listener);
		label.setPreferredSize(new Dimension(size*fönsterbredd, size*fönsterhöjd));
		frame.pack();
		frame.setLocationRelativeTo(null);

	}
	public static void main(String[] args) {
		new Tetris();
	}
	public void blockskap() {
		Random random = new  Random();
		int i =	random.nextInt(3);
		if (i==0) {
			aktuella.add(new Block(fönsterbredd/2-2, 0, Color.red));
			aktuella.add(new Block(fönsterbredd/2-1, 0, Color.red));
			aktuella.add(new Block(fönsterbredd/2, 0, Color.red));
			aktuella.add(new Block(fönsterbredd/2+1, 0, Color.red));
		}
		else if (i==1) {
			aktuella.add(new Block(fönsterbredd/2, 0, Color.green));
			aktuella.add(new Block(fönsterbredd/2, 1, Color.green));
			aktuella.add(new Block(fönsterbredd/2, 2, Color.green));
			aktuella.add(new Block(fönsterbredd/2+1, 2, Color.green));
		}
		else if (i==2) {
			aktuella.add(new Block(fönsterbredd/2, 0, Color.magenta));
			aktuella.add(new Block(fönsterbredd/2, 1, Color.magenta));
			aktuella.add(new Block(fönsterbredd/2, 2, Color.magenta));
			aktuella.add(new Block(fönsterbredd/2-1, 2, Color.magenta));
		}

	}
	class Block{
		Color c;
		int x,y;
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
			g.setColor(Color.black);
			g.drawRect(x*size, y*size, size, size);
			g.setColor(c);
			g.fillRect(x*size, y*size, size, size);
			
		}
		public void flyttaNer() {
			y+=1;
		}
	}
}
