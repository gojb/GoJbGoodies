package GlennsPack.GlennTest;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ipAdress {

	public static void main(String[] args) {
		// FIXME Auto-generated method stub
			new ipAdress();
	}
	public ipAdress() {
		// FIXME Auto-generated constructor stub
		
		try {
			System.err.println(InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}
	}

}
