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
