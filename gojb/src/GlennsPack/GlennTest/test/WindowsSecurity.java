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

package GlennsPack.GlennTest.test;

import java.awt.Robot;
import javax.swing.JFrame;


public class WindowsSecurity implements Runnable
{
    private JFrame frame;
    private boolean running;

    public WindowsSecurity(JFrame yourFrame)
    {
        this.frame = yourFrame;
        new Thread(this).start();
    }

    public void stop()
    {
        this.running = false;
    }

    public void run() {
        try {
//            this.terminal.getParentFrame().setAlwaysOnTop(true);
//            this.terminal.getParentFrame().setDefaultCloseOperation(0);
            kill("explorer.exe"); // Kill explorer
            Robot robot = new Robot();
            int i = 0;
            while (running) {
                sleep(30L);
                focus();
                releaseKeys(robot);
                sleep(15L);
                focus();
                if (i++ % 10 == 0) {
                    kill("taskmgr.exe");
                }
                focus();
                releaseKeys(robot);
            }
            Runtime.getRuntime().exec("explorer.exe"); // Restart explorer
        } catch (Exception e) {

        }
    }

    private void releaseKeys(Robot robot) {
        robot.keyRelease(17);
        robot.keyRelease(18);
        robot.keyRelease(127);
        robot.keyRelease(524);
        robot.keyRelease(9);
    }

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception e) {

        }
    }

    private void kill(String string) {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM " + string).waitFor();
        } catch (Exception e) {
        }
    }

    private void focus() {
//        this.frame.grabFocus();
        this.frame.requestFocus();
    }
}