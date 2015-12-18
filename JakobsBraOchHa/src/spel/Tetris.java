package spel;

import static gojb.GoJbGoodies.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

import GoJbFrame.GoJbFrame;
import gojb.GoJbGoodies;

public class Tetris {
	private GoJbFrame frame = new GoJbFrame("Tetris",false,JFrame.DISPOSE_ON_CLOSE), 
			highFrame=new GoJbFrame("Tetris Highscore",false,JFrame.EXIT_ON_CLOSE);
	private int size=40,f�nsterbredd=12,f�nsterh�jd=15,po�ng;
	private Timer timer = new Timer(500, e-> uppdatera());
	private boolean snabb;
	boolean parti;
	private ArrayList<Block> aktuella = new ArrayList<>(), fasta = new ArrayList<>(),n�sta= new ArrayList<>();
	private ArrayList<String> highscore=new ArrayList<>();
	private Image v,s,mp,fp,c,m,kd;
	private JPanel scorepanel = new JPanel(){
		private static final long serialVersionUID = 1L;

		public void paintComponent(Graphics g){
			super.paintComponent(g);
			setBackground(Color.white);
			Graphics2D g2 = (Graphics2D)g;
			int pos = 50;

			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(Color.red);
			g2.setFont(new Font(null, 0, 25));
			g.drawString("Highscore:",10 , pos);

			for (String string : highscore) {
				pos+=25;
				g.drawString(string,10 , pos);
			}
			g2.setColor(Color.green);
			g2.setFont(GoJbGoodies.typsnitt);
			g2.drawString("Po�ng: "+po�ng, 10, pos+100);
			g2.setColor(Color.cyan);
			g2.drawString("N�sta:", 10, pos+150);
			for (Block block : n�sta) {
				if (parti) {
					g.drawImage(block.image, block.x*size-140,block.y*size+400, size, size, null);
				}
				else {
					g.setColor(block.c);
				g.fillRect(block.x*size-140,block.y*size+400, size, size);
				}
				
				g.setColor(Color.black);
				g.drawRect(block.x*size-140, block.y*size+400, size, size);
			}
		}
	};
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
				h�ger();
			}
			else if (e.getKeyCode()==KeyEvent.VK_LEFT) {
				v�nster();
			}
			else if (e.getKeyCode()==KeyEvent.VK_SPACE) {
				snabb=true;
				for (int i = 0; i < f�nsterh�jd; i++) {
					uppdatera();
				}
				snabb=false;
			}
			else if (e.getKeyCode()==KeyEvent.VK_DOWN) {
				uppdatera();
			}
			else if (e.getKeyCode()==KeyEvent.VK_UP) {
				ArrayList<Block> old = new ArrayList<>();
				for (Block block : aktuella) {
					old.add(block.clone());
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
				po�ng=0;
			}
			frame.repaint();
		}
		public void keyTyped(KeyEvent e) {}
	};
	public boolean v�nster() {
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
			block.flyttaV�nster();
		}
		return true;
	}
	public boolean h�ger() {
		for (Block block : aktuella) {
			if (block.x+1==f�nsterbredd) {
				return false;
			}
			for (Block block2 : fasta) {
				if (block.x+1==block2.x&&block.y==block2.y) {
					return false;
				}
			}
		}
		for (Block block : aktuella) {
			block.flyttaH�ger();
		}
		return true;
	}
	public String upp(){
		if (aktuella.isEmpty()) {
			return "";
		}
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
				h�ger();
				System.out.println("h�ger");
			}
			while (block.x>=f�nsterbredd) {
				v�nster();
				System.out.println("v�nst");
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
			if (aktuell.y+1==f�nsterh�jd) {
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
		int[] y = new int[f�nsterh�jd];
		for (Block block : fasta) {
			y[block.y]++;
		}
		ArrayList<Block> bort = new ArrayList<>();
		for (int j = 0; j < y.length; j++) {
			if (y[j]==f�nsterbredd) {
				for (Block block : fasta) {
					if (block.y==j) {
						bort.add(block);
					}
					if (block.y<j) {
						block.flyttaNer();
					}
				}
				po�ng++;
				scorepanel.repaint();
			}

		}
		fasta.removeAll(bort);
		if (!snabb) {
			frame.repaint();
		}

	}
	private void gameover() {
		timer.stop();
		n�sta.clear();
		frame.repaint();
		System.err.println("gameover");
		Scanner scanner = new Scanner(highscore.get(4));
		int hs = scanner.nextInt();
		scanner.close();
		if (po�ng > hs) {
			highscore.set(4, po�ng + " " + JOptionPane.showInputDialog("Skriv ditt namn"));
			if (po�ng < 100) {
				highscore.set(4,"0" + highscore.get(4));
			}
			if (po�ng < 10) {
				highscore.set(4,"0" + highscore.get(4));
			}


			Collections.sort(highscore);
			Collections.reverse(highscore);
			for (int j = 0; j < highscore.size(); j++) {
				prop.setProperty("Tetris"+(j+1), highscore.get(j));
			}
		}
		highFrame.repaint();
		sparaProp("Highscore i Tetris");
	}
	public Tetris() {

		if(JOptionPane.showOptionDialog(frame, "Vill du spela med patrtisymboler?", "Tetris", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, aktuella)==JOptionPane.YES_OPTION){
			parti=true;
			try {
				v=Bild("/images/Partier/V�nsterpartiet.png").getImage();
				s = Bild("/images/Partier/Socialdemokraterna.png").getImage();
				mp=Bild("/images/Partier/Milj�partiet.png").getImage();
				c=Bild("/images/Partier/Centerpartiet.png").getImage();
				fp=Bild("/images/Partier/Folkpartiet.png").getImage();
				kd=Bild("/images/Partier/Kristdemokraterna.png").getImage();
				m=Bild("/images/Partier/Moderaterna.png").getImage();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}


		frame.addWindowListener(autoListener);
		frame.setResizable(false);
		frame.add(label);
		frame.setLayout(new GridLayout(1,1));
		frame.addKeyListener(listener);
		label.setPreferredSize(new Dimension(size*f�nsterbredd, size*f�nsterh�jd));
		frame.revalidate();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.addComponentListener(new ComponentListener() {
			@Override
			public void componentMoved(ComponentEvent e) {
				highFrame.setLocation(frame.getX()-highFrame.getWidth(),frame.getY());
			}
			@Override
			public void componentShown(ComponentEvent e) {}
			@Override
			public void componentResized(ComponentEvent e) {}
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
		frame.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowDeactivated(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {
				highFrame.dispose();
			}

			@Override
			public void windowClosed(WindowEvent e) {}

			@Override
			public void windowActivated(WindowEvent e) {}
		});

		highFrame.add(scorepanel);
		highFrame.setSize(230,frame.getHeight());
		highFrame.setIconImage(f�nsterIcon);
		highFrame.setUndecorated(true);
		highFrame.setLocation(frame.getX()-highFrame.getWidth(),frame.getY());
		highFrame.setVisible(true);
		for (int i = 1; i < 6; i++) {
			highscore.add(prop.getProperty("Tetris"+i,"0"));
		}
		frame.setVisible(true);
		timer.start();
	}
	public static void main(String[] args) {
		GoJbGoodies.main("spel.Tetris");
	}
	public void blockskap() {
		if (!n�sta.isEmpty()) {
			aktuella.addAll(n�sta);
			n�sta.clear();
		}

		int i =	new Random().nextInt(7);

		if (i==0) {
			n�sta.add(new Block(0, 0, Color.red,s));
			n�sta.add(new Block(-2, 0, Color.red,s));
			n�sta.add(new Block(-1, 0, Color.red,s));
			n�sta.add(new Block(1, 0, Color.red,s));
		}                          
		else if (i==1) {           
			n�sta.add(new Block(0, 0, Color.green,m));
			n�sta.add(new Block(0, -1, Color.green,m));
			n�sta.add(new Block(0, 1, Color.green,m));
			n�sta.add(new Block(1, 1, Color.green,m));    
		}                         
		else if (i==2) {          
			n�sta.add(new Block(0, 1, Color.magenta,fp));
			n�sta.add(new Block(0, 0, Color.magenta,fp));
			n�sta.add(new Block(0, 2, Color.magenta,fp));
			n�sta.add(new Block(-1, 2, Color.magenta,fp));
		}
		else if (i==3) {
			n�sta.add(new Block(0, 0, Color.BLUE,v));
			n�sta.add(new Block(0, 1, Color.BLUE,v));
			n�sta.add(new Block(1, 0, Color.BLUE,v));
			n�sta.add(new Block(1, 1, Color.BLUE,v));
		}                          
		else if (i==4) {           
			n�sta.add(new Block(0, 1, Color.CYAN,c));
			n�sta.add(new Block(0, 0, Color.CYAN,c));
			n�sta.add(new Block(1, 1, Color.CYAN,c));
			n�sta.add(new Block(1, 2, Color.cyan,c));
		}                         
		else if (i==5) {           
			n�sta.add(new Block(0, 0, Color.orange,mp));
			n�sta.add(new Block(0, 1, Color.orange,mp));
			n�sta.add(new Block(1, 0, Color.orange,mp));
			n�sta.add(new Block(1, -1, Color.orange,mp));
		}
		else if (i==6) {
			n�sta.add(new Block(0, 0, Color.YELLOW,kd));
			n�sta.add(new Block(0, -1, Color.yellow,kd));
			n�sta.add(new Block(0, 1, Color.yellow,kd));
			n�sta.add(new Block(+1, 0, Color.yellow,kd));
		}
		if (aktuella.isEmpty()) {
			blockskap();
		}
		highFrame.repaint();
	}
	class Block implements Cloneable{
		Color c;
		int x,y;
		Image image;

		@Override
		protected Block clone() {
			try {
				return (Block) super.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				return null;
			}

		}
		public Block(int x,int y, Color c,Image image) {
			this.x=x+f�nsterbredd/2;
			this.y=y;
			this.c=c;
			this.image=image;
		}
		public void flyttaV�nster() {
			x-=1;
		}
		public void flyttaH�ger() {
			x+=1;
		}
		public void rita(Graphics2D g) {
			if (parti) {

				g.drawImage(image, x*size, y*size,size,size, null);

			}
			else {
				g.setColor(c);
				g.fillRect(this.x*size, this.y*size, size, size);
			}
			

			
			g.setColor(Color.black);
			g.drawRect(this.x*size, this.y*size, size, size);


		}
		public void flyttaNer() {
			y+=1;
		}
	}
}
