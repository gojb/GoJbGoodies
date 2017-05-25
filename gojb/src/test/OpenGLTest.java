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

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex3d;
import static org.lwjgl.util.glu.GLU.gluPerspective;

import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class OpenGLTest {
	private double xx,zz,rx,ry,rz;
	private int fps;
	long lastFPS;

	public OpenGLTest() {
		System.out.println("Starting");
		thread.start();
	}
	public static void main(String[] args) {
		new OpenGLTest();
	}
	private Thread thread = new Thread(){
		public void run() {
			System.out.println("Running");
			try {
				unZip();

				try {
//					if (getClass().getResource("/" + getClass().getName().replace('.','/') + ".class").toString().startsWith("jar:")){
						System.setProperty("org.lwjgl.librarypath", new File(System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\GoJbsBraOchHa\\windows_dll").getAbsolutePath());
//					}
					Display.setDisplayMode(new DisplayMode((int)(Toolkit.getDefaultToolkit().getScreenSize().width*0.9), (int)(Toolkit.getDefaultToolkit().getScreenSize().height*0.9)));
					Display.setTitle("GoJbGame");
					Display.create();
				} catch (LWJGLException e) {
					e.printStackTrace();
				}
				lastFPS= getTime();
				gameLoop();
				Display.destroy();
			} catch (Exception e) {
				e.printStackTrace();
				try {
					thread.wait();
				} catch (InterruptedException e1) {
					e1.printStackTrace();

				}
			}
		};
	};
	private void unZip(){
		try {
			System.err.println(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
			ZipInputStream zipIn = new ZipInputStream(getClass().getProtectionDomain().getCodeSource().getLocation().openStream());
			ZipEntry entry = zipIn.getNextEntry();
			while (entry != null) {
				if (entry.toString().startsWith("windows_dll/")) {
					String filePath =  System.getProperty("user.home") + "\\AppData\\Roaming\\GoJb\\GoJbsBraOchHa"+File.separator + entry.getName();
					System.err.println(entry);
					if (!entry.isDirectory()) {
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						byte[] bytesIn = new byte[4096];
						int read = 0;
						while ((read = zipIn.read(bytesIn)) != -1) {
							out.write(bytesIn, 0, read);
						}
						FileOutputStream fos = new FileOutputStream(filePath);
						fos.write(out.toByteArray());
						fos.close();
					}
					else {
						File dir = new File(filePath);
						dir.mkdir();
					}
				}
				zipIn.closeEntry();
				entry = zipIn.getNextEntry();
			}
			zipIn.close();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}
	public void updateFPS() {
		if (getTime() - lastFPS > 1000) {
			Display.setTitle("FPS: " + fps);
			fps = 0;
			lastFPS += 1000;
		}
		fps++;
	}
	public long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	private void gameLoop(){
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		gluPerspective(70,(float)Display.getWidth()/Display.getHeight(),0.3f,1000);
		glMatrixMode(GL_MODELVIEW);
		glEnable(GL_DEPTH_TEST);
		double x=0,y=0,z=0;
		final double SPEED = 0.1;
		while(!Display.isCloseRequested()){
			if (Keyboard.isKeyDown(Keyboard.KEY_F))
				setDisplayMode(1366,768, !Display.isFullscreen());
			if (Keyboard.isKeyDown(Keyboard.KEY_W))
				move(0.11,1);
			if (Keyboard.isKeyDown(Keyboard.KEY_S))
				move(-0.11,1);
			if (Keyboard.isKeyDown(Keyboard.KEY_A))
				move(0.11,0);
			if (Keyboard.isKeyDown(Keyboard.KEY_D))
				move(-0.11,0);
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
				ry -= SPEED;
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
				ry += SPEED;
			if (Keyboard.isKeyDown(Keyboard.KEY_UP))
				rx -= SPEED;
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
				rx += SPEED;
			if (Keyboard.isKeyDown(Keyboard.KEY_Z)) 
				x+=0.5;
			if (Keyboard.isKeyDown(Keyboard.KEY_X)) 
				y+=0.5;
			if (Keyboard.isKeyDown(Keyboard.KEY_C)) 
				z+=0.5;
			if (Keyboard.isKeyDown(Keyboard.KEY_V)) 
				x-=0.5;
			if (Keyboard.isKeyDown(Keyboard.KEY_B))
				y-=0.5;
			if (Keyboard.isKeyDown(Keyboard.KEY_N))
				z-=0.5;

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

			glLoadIdentity();

			glRotated(rx,1,0,0);
			glRotated(ry,0,1,0);
			glRotated(-rz,0,0,1);
			glTranslated(xx,0,zz);

			//			glPushMatrix();

			draw3d(x,y,z,2,2,2);

			//			draw3d(x,y,z,2,2,2);

			//			glPopMatrix();
			Display.update();
			updateFPS();
			Display.sync(200);


		}
	}
	private void draw3d(double rotX,double rotY,double rotZ,int längd,int höjd,int bredd){

		längd/=2;
		höjd/=2;
		bredd/=2;

		glTranslated(0,0,-10);

		glRotated(rotX,1,0,0);
		glRotated(rotY,0,1,0);
		glRotated(rotZ,0,0,1);
		glClear(21);

		glBegin(GL_QUADS);

		//Fram
		glColor3d(-1,-0.5,0);

		glVertex3d(-bredd,-höjd,längd);
		glVertex3d(bredd,-höjd,längd);
		glVertex3d(bredd,höjd,längd);
		glVertex3d(bredd,-höjd,längd);

		//Bak
		glColor3d(0,1,0);
		glVertex3d(-bredd,-höjd,-längd);
		glVertex3d(-bredd,höjd,-längd);
		glVertex3d(bredd,höjd,-längd);
		glVertex3d(bredd,-höjd,-längd);

		//Under
		glColor3d(0,0,1);
		glVertex3d(-bredd,höjd,-längd);
		glVertex3d(-bredd,-höjd,-längd);
		glVertex3d(bredd,-höjd,-längd);
		glVertex3d(bredd,-höjd,längd);
		glClear(5);

		//Över
		glColor3d(1,1,0);
		glVertex3d(-bredd,höjd,-längd);
		glVertex3d(-bredd,höjd,längd);
		glVertex3d(bredd,höjd,längd);
		glVertex3d(bredd,höjd,-längd);

//		//LeftFace
//		glColor3d(0,1,1);
//		glVertex3d(-bredd,-höjd,längd);
//		glVertex3d(-bredd,-höjd,-längd);
//		glVertex3d(-bredd,höjd,-längd);
//		glVertex3d(-bredd,höjd,längd);
//
//		//		//Right Face
//		glColor3d(1,0,1);
//		glVertex3d(bredd,-höjd,längd);
//		glVertex3d(bredd,höjd,längd);
//		glVertex3d(bredd,höjd,-längd);
//		glVertex3d(bredd,-höjd,-längd);

		glEnd();
	}

	/**
	 * Set the display mode to be used 
	 * 
	 * @param width The width of the display required
	 * @param height The height of the display required
	 * @param fullscreen True if we want fullscreen mode
	 */

	private void setDisplayMode(int width, int height, boolean fullscreen) {

		try {
			DisplayMode targetDisplayMode = null;

			if (fullscreen) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i=0;i<modes.length;i++) {
					DisplayMode current = modes[i];

					if ((current.getWidth() == width) && (current.getHeight() == height)) {
						if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}

						// if we've found a match for bpp and frequence against the 
						// original display mode then it's probably best to go for this one
						// since it's most likely compatible with the monitor
						if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
								(current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else {
				targetDisplayMode = new DisplayMode(width,height);
			}

			if (targetDisplayMode == null) {
				System.out.println("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
				return;
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(fullscreen);

		} catch (LWJGLException e) {
			System.out.println("Eror "+width+"x"+height+" fullscreen="+fullscreen + e);
		}
	}
	private void move(double amt, double dir){
		zz += amt * Math.sin(Math.toRadians(ry + 90 * dir));
		xx += amt * Math.cos(Math.toRadians(ry + 90 * dir));
	}
}