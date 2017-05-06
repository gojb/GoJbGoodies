package glennsPack.GlennTest;

import java.awt.Font;
import java.awt.event.*;

import javax.swing.*;


public class PinSolver implements ActionListener{

	JFrame frame = new JFrame(),
			frame2 = new JFrame();

	static JTextField pin = new JTextField(),
			info = new JTextField();

	JTextField counter = new JTextField();

	JButton solve = new JButton("SOLVE");

	Thread thread1 = null;
	
	int test = -1, part, second, minute;

	Timer timer = new Timer(200, this);

	static boolean stop;

	public static void main(String[] args) {
		new PinSolver();

	}

	public PinSolver(){

		frame.setSize(300, 250);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(3);
		frame.setLayout(null);
		frame.add(counter);

		frame2.setSize(300, 250);
		frame2.setLocationRelativeTo(null);
		frame2.setDefaultCloseOperation(3);
		frame2.setVisible(true);
		frame2.setLayout(null);
		frame2.add(info);
		frame2.add(pin);
		frame2.add(solve);

		info.setLocation(30, 0);
		info.setSize(250, 20);
		info.setText("Enter a 4 digit number and press \"Solve\"");
		info.setBorder(null);
		info.setBackground(null);
		info.setEditable(false);

		pin.setSize(230,100);
		pin.setBackground(null);
		pin.setFont(new Font("da",Font.BOLD,80));
		pin.setLocation(50, 20);
		pin.setBorder(null);

		solve.setSize(180, 50);
		solve.setLocation(50, 140);
		solve.addActionListener(this);

		pin.requestFocusInWindow();

		timer.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		pin.requestFocusInWindow();
		test=-1;
		
		if(solve==e.getSource()){
		try {
			test = Integer.parseInt(pin.getText());
		} catch (Exception e2) {
			System.err.println("Nooo");
			
		}
		}
		
		if(test>0){
			if(pin.getText().length() == 4){
				runSolver();
			}
		}
		if(test==0000){
			runSolver();
		}
		if(timer==e.getSource()){
			//			part=part+1;
			part++;
			System.out.println(part);
			if(part==10){
				second++;
				part=0;				
			}
			if(second==60){
				minute++;
				second=0;
			}
			counter.setText(second+";"+minute);
			frame.add(counter);
			//			System.err.println(part);
			if(stop==true){
				timer.stop();
				System.err.println("Yayaya");
			}
		}
	}
	
	public void runSolver(){
		frame.setVisible(true);
		frame2.dispose();

		frame.add(pin);
		pin.setSize(80, 50);
		pin.setLocation(100, 140);
		pin.setFont(new Font("da",Font.BOLD,30));
		pin.setEditable(false);

		counter.setSize(230,100);
		counter.setBackground(null);
		counter.setFont(new Font("da",Font.BOLD,60));
		counter.setLocation(80, 20);
		counter.setBorder(null);

		timer.start();
		try {
			new Thread(new for1()).start();
		} catch (Exception e) {
		}
		try {
			new Thread(new for2()).start();
		} catch (Exception e) {
		}

	}

}


class for1 implements Runnable{
	public void run() {
		for(int i = -1111; i<10000;i=i+1111){
			System.err.println(Integer.toString(i)+"  "+PinSolver.pin.getText().toString());
			System.out.println(i==Integer.parseInt(PinSolver.pin.getText().toString()));
			if(i==Integer.parseInt(PinSolver.pin.getText().toString())){
				System.err.println("TRUE");
				PinSolver.stop=true;
			}
		}
	}}
class for2 implements Runnable{
	int x;
	public void run() {
		
		for(int i = 0; i<1000;i=i+1){
			if(i==Integer.parseInt(PinSolver.pin.getText().toString())){
				System.err.println("TRUE");
				PinSolver.stop=true;
			}

	}}

class for3 implements Runnable{
	public void run() {

	}}
class for4 implements Runnable{
	public void run() {

	}}
class for5 implements Runnable{
	public void run() {

	}}
class for6 implements Runnable{
	public void run() {

	}}
class for7 implements Runnable{
	public void run() {

	}}
class for8 implements Runnable{
	public void run() {

	}}
class for9 implements Runnable{
	public void run() {

	}}
class for10 implements Runnable{
	public void run() {
		
	}}}