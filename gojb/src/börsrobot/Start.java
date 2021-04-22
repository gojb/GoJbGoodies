package börsrobot;
import java.io.*;
import java.net.*;
import java.util.stream.Collectors;

import javax.json.*;

public class Start {

	/**
	 * @param args
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws Exception {
		new Start();
	}
	public Start() throws IOException, InterruptedException {
		//Lägg till automatisk cookie Manager
		CookieManager cookieManager = new CookieManager( null, CookiePolicy.ACCEPT_ALL );
		CookieHandler.setDefault(cookieManager);


		login("9901225095");

		System.out.println(cookieManager.getCookieStore().getCookies().toString());

		HttpURLConnection connection=openConnection("https://www.avanza.se/ab/component/accountsdistribution/chart", "GET");
		connect(connection);
		JsonReader reader=Json.createReader(connection.getInputStream());
		JsonArray jsonArray=reader.readArray();
		System.out.println(jsonArray);


		connection=openConnection("https://www.avanza.se/start", "GET");
		connection.addRequestProperty("Referer", "https://www.avanza.se/start");
		connect(connection);


		InputStreamReader reader2 = new InputStreamReader(connection.getInputStream());
		BufferedReader bufferedReader = new BufferedReader(reader2);
		String string=bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
		System.out.println(string);



		connection=openConnection("https://www.avanza.se/ab/component/highstockchart/getchart/orderbook", "POST");
		connection.setDoOutput(true);
		//		String[] strings=cookieManager.getCookieStore().getCookies().toString().split(",");
		//		System.out.println(strings.toString());
		//		CookieManager cookies = new CookieManager( null, CookiePolicy.ACCEPT_ALL );
		//		for (String string : strings) {
		//			System.err.println(string);
		//			cookieManager.getCookieStore().add(null, HttpCookie.parse(string).get(0));
		//		}
		//		System.err.println(cookies.getCookieStore().getCookies().toString());
		//		HttpCookie cookie= HttpCookie.parse(cookieManager.getCookieStore().getCookies().toString()).get(0);
		//		System.out.println(cookie);
		JsonWriter writer = Json.createWriter(connection.getOutputStream());
		JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
		objectBuilder.add("orderbookId", 19002);
		objectBuilder.add("chartType", "AREA");
		objectBuilder.add("widthOfPlotContainer", 640);
		objectBuilder.add("chartResolution", "WEEK");
		objectBuilder.add("navigator", true);
		objectBuilder.add("percentage", false);
		objectBuilder.add("volume", false);
		objectBuilder.add("owners", false);
		objectBuilder.add("timePeriod",  "today");
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
		JsonObjectBuilder objectBuilder2 = Json.createObjectBuilder();
		objectBuilder2.add("type", "sma");
		objectBuilder2.add("timeFrame", 10);
		arrayBuilder.add(objectBuilder2.build());
		objectBuilder2.add("type", "sma");
		objectBuilder2.add("timeFrame", 20);
		arrayBuilder.add(objectBuilder2.build());
		objectBuilder2.add("type", "rsi");
		objectBuilder2.add("timeFrame", 14);
		arrayBuilder.add(objectBuilder2.build());
		objectBuilder.add("ta",arrayBuilder.build());
		writer.writeObject(objectBuilder.build());
		//		connection.setRequestProperty("Content-Type", "application/json");
		//		connection.setRequestProperty("Content-Type", cookieManager.getCookieStore().getCookies());
		connect(connection);
		System.out.println(getResponse(connection));

	}
	private void login(String personnummer) throws IOException, InterruptedException {
		HttpURLConnection connection=openConnection("https://www.avanza.se/ab/bankid/authenticate", "POST");
		write(connection, "personnummer="+personnummer);
		System.out.println(connection.getOutputStream().toString());
		connect(connection);
		JsonObject jsonObject=Json.createReader(connection.getInputStream()).readObject();
		System.out.println(jsonObject);
		String transID=jsonObject.getString("transactionId");
		System.out.println(transID);
		while(true) {
			connection=openConnection("https://www.avanza.se/ab/bankid/collect", "POST");
			write(connection, "transactionId="+transID);
			connect(connection);
			jsonObject=Json.createReader(connection.getInputStream()).readObject();
			System.out.println(jsonObject);
			if (jsonObject.get("error")!=null) {
				throw new IOException("Inloggning Avbruten!");
			}
			else if (jsonObject.get("redirectUrl")!=null) {
				System.out.println("Inloggad");
				break;
			}
			else{
				System.out.println("Väntar på BankID");;
			}
			Thread.sleep(1000);
		}
	}
	void write(HttpURLConnection connection,String string) throws IOException, InterruptedException {
		connection.setDoOutput(true);
		OutputStream outputStream = connection.getOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		writer.write(string);
		writer.close();
		outputStream.close();
	}
	void connect(HttpURLConnection connection) throws IOException, InterruptedException {
		while (connection.getResponseCode()!=200) {
			Thread.sleep(100);
			System.out.println(connection.getResponseCode());
			System.out.println(connection.getResponseMessage());
		}
	}
	String getResponse(HttpURLConnection connection) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;
		StringBuffer content = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}
		in.close();
		return content.toString();
	}
	HttpURLConnection openConnection(String URL,String metod) throws IOException {
		URL url = new URL(URL);
		HttpURLConnection connection= (HttpURLConnection) url.openConnection();
		connection.setRequestMethod(metod);
		return connection;
	}
}

