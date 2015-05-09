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