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
import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;



public class ServerTest {
    public static void main(String[] args) throws Exception {
        System.out.println("The capitalization server is running.");
        ServerSocket listener = new ServerSocket(11622);
        try {
            while (true) {
                new Capitalizer(listener.accept()).run();;
            }
        } finally {
            listener.close();
        }
    }
    private static class Capitalizer implements Runnable{
        private Socket socket;

        public Capitalizer(Socket socket) {
            this.socket = socket;
            log("New connection with client# "  + " at " + socket);
        }
        public void run() {
            try {
            	BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println("Hello, you are client #" + ".");
                while (true) {
                    String input = in.readLine();
                    if (input == null || input.equals(".")) {
                        break;
                    }
                    out.println(input.toUpperCase());
                }
            } catch (IOException e) {
                log("Frånkopplad"  + ": " + e);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {}
                log("Connection with client# " + " closed");
            }
        }
        private void log(String message) {
            System.out.println(message);
        }
    }
}