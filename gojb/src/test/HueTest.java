package test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketAddress;
import java.net.URL;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonStructure;
import javax.json.JsonWriter;

public class HueTest {

	public static void main(String[] args) throws IOException, InterruptedException {
		URL url = new URL("http://hue.lan/api/1Ct9oM4V40HVsMkaWFq76MFchV3yygkBCTDl7SaH/lights/2/state");
		SocketAddress address = new InetSocketAddress(5555);
		Proxy proxy = new Proxy(Type.HTTP,address);
		HttpURLConnection connection= (HttpURLConnection) url.openConnection(proxy);

		connection.setRequestMethod("PUT");
		connection.setDoOutput(true);
		JsonWriter writer = Json.createWriter(connection.getOutputStream());
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
		objectBuilder.add("transitiontime", 200);
		objectBuilder.add("on", false);
		objectBuilder.add("bri", 254);
		writer.writeObject(objectBuilder.build());
		connect(connection);
	}
	static void connect(HttpURLConnection connection) throws IOException, InterruptedException {
		while (connection.getResponseCode()!=200) {
			Thread.sleep(100);
			System.out.println(connection.getResponseCode());
			System.out.println(connection.getResponseMessage());
		}
		JsonStructure jsonObject=Json.createReader(connection.getInputStream()).read();
		System.out.println(jsonObject);
	}
}
