package test;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

public class JSONTest {
	JsonObjectBuilder builder;
	public JSONTest() {
		// TODO Auto-generated constructor stub
		builder=Json.createObjectBuilder();
		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();
		jsonArrayBuilder.add(Json.createObjectBuilder().add("gha", "asdfg"));
		jsonArrayBuilder.add(Json.createObjectBuilder().add("gha", "22222"));
		jsonArrayBuilder.add(Json.createObjectBuilder().add("gha", "asdfg"));
		jsonArrayBuilder.add(Json.createObjectBuilder().add("gha", "asdfg"));
		jsonArrayBuilder.add(Json.createObjectBuilder().add("gha", "asdfg"));
		builder.add("hejs", jsonArrayBuilder);
		builder.add("}}}}}}][{6€$££$€6{[{6€$£@1@1@£@;;:_::;MNBVCXESD\"", "då");
		builder.add("hejp", "då").
		add("hej", "då");
		System.out.println(builder.build());
	}
	public static void main(String[] args) {
		new JSONTest();
	}

}
