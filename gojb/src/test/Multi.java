package test;

import java.util.ArrayList;

public class Multi {
	static String msg = "process it";
	static Object lock=new Object();
	public static void main(String[] args) {
		ArrayList<Thread> wArraylist=new ArrayList<>();

		for (int i = 0; i < 1000; i++) {
			wArraylist.add(new Thread(){
				@Override
				public void run() {
					String name = Thread.currentThread().getName();
//					synchronized (lock) {
						try{
							System.out.println(name+" waiting at time:"+System.currentTimeMillis());
							synchronized (lock) {
							lock.wait();
//							sleep(1000);
							}
							}catch(InterruptedException e){
							e.printStackTrace();
						}
						try {
							sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(name+" waiter thread got notified at time:"+System.currentTimeMillis());
						//process the message now
						//						System.out.println(name+" processed: "+Multi.msg.getMsg());
//					}
				};
			});
		}
		for (Thread thread : wArraylist) {
			thread.start();
		}
		Thread thread= new Thread("Nofifier"){
			public void run() {
				String name = Thread.currentThread().getName();
				System.out.println(name+" started");
				try {
					Thread.sleep(5000);
					synchronized (lock) {
						msg=name+" Notifier work done";
						//				Multi.msg.notify();
						lock.notifyAll();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		};
		thread.start();
		System.out.println("All the threads are started");
	}
}
