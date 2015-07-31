package test;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

import javax.swing.*;


public class KlientTest {

    private BufferedReader in;
    private PrintWriter out;
    private JFrame frame = new JFrame("Capitalize Client");
    private JTextField dataField = new JTextField(40);
    private JTextArea messageArea = new JTextArea(8, 60);
    private JScrollPane jScrollPane = new JScrollPane(messageArea);
    public KlientTest() {

        messageArea.setEditable(false);
        frame.add(dataField, "North");
        frame.add(jScrollPane, "Center");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        try {
			connectToServer();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        dataField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                out.println(dataField.getText());
                   String response;
                try {
                    response = in.readLine();
                    if (response == null || response.equals("")) {
                          System.exit(0);
                      }
                } catch (IOException ex) {
                       response = "Error: " + ex;
                }
                messageArea.append(response + "\n");
                dataField.selectAll();
            }
        });
    }
    public void connectToServer() throws IOException {
        String serverAddress = JOptionPane.showInputDialog(
            frame,
            "Enter IP Address of the Server:",
            "Welcome to the Capitalization Program",
            JOptionPane.QUESTION_MESSAGE);
        @SuppressWarnings("resource")
		Socket socket = new Socket(serverAddress, 11622);
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        while (true) {
            messageArea.append(in.readLine() + "\n");
            messageArea.setCaretPosition(messageArea.getDocument().getLength());
        }
    }
    public static void main(String[] args) throws Exception {
       new KlientTest();
    }
}