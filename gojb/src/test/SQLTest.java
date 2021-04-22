package test;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
			label[i].setFont(new Font("Arial", 0, 30));;
			final int nr=i/2+1;
			label[i].addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					try {
						System.out.println("test"+nr);
						statement.execute("UPDATE `styrning`.`Data` SET `Value` = '"+JOptionPane.showInputDialog("Nytt värde")+"' WHERE `Data`.`Data` = 'Temp"+nr+"R';");
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
			for (int i = 1; i <= 6; i++) {
				try {
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
					temp=1d/((Math.log((R*(X/1023d)/(1d-(X/1023d))/10000d)))/3950d+(1d/(273.15+25)))-273.15;
					System.out.println(temp+" "+R+" "+X);
					label[i*2-1].setText(Math.round(temp*10d)/10d+"");

					//					double average = 1023d / X - 1;
					//					average = R / average;
					//					double  THERMISTORNOMINAL =10000;
					//					double TEMPERATURENOMINAL =25;
					//					double BCOEFFICIENT =3950;
					//					double steinhart;
					//
					//					steinhart = average / THERMISTORNOMINAL;     // (R/Ro)
					//					steinhart = Math.log(steinhart);                  // ln(R/Ro)
					//					steinhart /= BCOEFFICIENT;                   // 1/B * ln(R/Ro)
					//					steinhart += 1.0 / (TEMPERATURENOMINAL + 273.15); // + (1/To)
					//					steinhart = 1.0 / steinhart;                 // Invert
					//					steinhart -= 273.15;                         // convert to C
					//					System.out.println(steinhart);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Thread.sleep(500);
		}

	}
	public static void main(String[] args) throws SQLException, InterruptedException {
		new SQLTest();
	}

}
