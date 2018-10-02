package börsrobot;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

public class Start {

	/**
	 * @param args
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws Exception {
		new Start();
	}
	public Start() throws IOException, InterruptedException {
		CookieManager cookieManager = new CookieManager( null, CookiePolicy.ACCEPT_ALL );
		CookieHandler.setDefault(cookieManager);
		login(cookieManager,"9901225095");
		//		String cookiesHeader = connection.getHeaderField("Set-Cookie");
		//		List<HttpCookie> cookies = HttpCookie.parse(cookiesHeader);
		//		cookies.forEach(cookie -> manager.getCookieStore().add(null, cookie));
		System.out.println(cookieManager.getCookieStore().getCookies().toString());
		HttpURLConnection connection=openConnection("https://www.avanza.se/ab/component/accountsdistribution/chart", "GET");
		//		connection.setRequestProperty("Cookie",
		//			    "".join(";",  cookieManager.getCookieStore().getCookies()));
		connect(connection);
		JsonReader reader=Json.createReader(connection.getInputStream());
		JsonArray jsonArray=reader.readArray();
		System.out.println(jsonArray);
		//		JsonObject jsonObject=makeJson(connection);
		//		System.out.println(jsonObject);
	}
	private void login(CookieManager manager,String personnummer) throws IOException, InterruptedException {
		HttpURLConnection connection=openConnection("https://www.avanza.se/ab/bankid/authenticate", "POST");
		Map<String, String> parameters = new HashMap<>();
		parameters.put("personnummer", personnummer);
		add_parameters(connection, parameters);
		connect(connection);
		JsonObject jsonObject=makeJson(connection);
		System.out.println(jsonObject);
		String transID=jsonObject.getString("transactionId");
		System.out.println(transID);
		while(true) {
			connection=openConnection("https://www.avanza.se/ab/bankid/collect", "POST");
			parameters = new HashMap<>();
			parameters.put("transactionId", transID);
			add_parameters(connection, parameters);
			connect(connection);
			jsonObject=makeJson(connection);
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
		//		System.out.println(connection.getHeaderFields().get(key));
		//		List<String> cookiesHeader = connection.getHeaderFields().get("Set-Cookie");
		//		cookiesHeader.forEach(cookie -> {
		//			manager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
		//		});
	}
	JsonObject makeJson(HttpURLConnection connection) throws IOException {
		JsonReader reader=Json.createReader(connection.getInputStream());
		return reader.readObject();
	}
	void connect(HttpURLConnection connection) throws IOException, InterruptedException {
		while (connection.getResponseCode()!=200) {
			Thread.sleep(100);
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
	void add_parameters(HttpURLConnection  connection,Map<String, String> parameters) throws UnsupportedEncodingException, IOException {
		connection.setDoOutput(true);
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());
		out.writeBytes(getParamsString(parameters));
		out.flush();
		out.close();
	}
	String getParamsString(Map<String, String> params) throws UnsupportedEncodingException{
		StringBuilder result = new StringBuilder();
		for (Map.Entry<String, String> entry : params.entrySet()) {
			result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			result.append("&");
		}
		String resultString = result.toString();
		return resultString.length() > 0
				? resultString.substring(0, resultString.length() - 1)
						: resultString;
	}

}

