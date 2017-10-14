package test;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;

import javax.swing.JLabel;

import GoJbFrame.GoJbFrame;
public class SQLTest {
	Connection connection;
	public SQLTest() throws SQLException, InterruptedException {
		GoJbFrame goJbFrame = new GoJbFrame("Teperaturer");
		connection = DriverManager.getConnection("jdbc:mysql://192.168.32.113:3306/", "jakob", "furugatan10" );
		JLabel[] label=new JLabel[6*2];
		goJbFrame.getContentPane().setLayout(new GridLayout(0, 2, 1, 1));
		goJbFrame.getContentPane().setBackground(Color.BLACK);
		//		goJbFrame.getContentPane().
		ResultSet resultSet;
		Statement statement = connection.createStatement();
		for (int i = 0; i < label.length; i++) {
			label[i]=new JLabel(""+(i/2+1));
			label[i].setBackground(Color.white);
			label[i].setOpaque(true);
			goJbFrame.add(label[i]);
			label[++i]=new JLabel("0");
			goJbFrame.add(label[i]);
			label[i].setBackground(Color.white);
			label[i].setOpaque(true);
			final int nr=i;
			label[i].addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					try {
						System.out.println("test"+nr);
						statement.executeQuery("select * from styrning.Data WHERE Data='Temp"+nr+"R'");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseExited(MouseEvent e) {}
			});

		}

		while(true){
			for (int i = 1; i <= 1; i++) {
				resultSet = statement.executeQuery("select * from styrning.Data WHERE Data='Temp"+i+"U'");
				double X = 0,R = 0;
				while (resultSet.next()) {
					X=resultSet.getInt("Value");
				}
				resultSet = statement.executeQuery("select * from styrning.Data WHERE Data='Temp"+i+"R'");
				while (resultSet.next()) {
					R=resultSet.getInt("Value");
					resultSet.toString();
				}
				double temp;
				temp=1d/((Math.log((R*(X/1024d)/(1d-(X/1024d))/10000d)))/3930d+(1d/(25.0+273.15)))-273.15;
				System.out.println(temp+" "+R+" "+X);
				label[i*2-1].setText(temp+"");
			}
			Thread.sleep(1000);
		}

	}
	public static void main(String[] args) throws SQLException, InterruptedException {
		new SQLTest();
	}

}
