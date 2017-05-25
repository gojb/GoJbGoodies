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
