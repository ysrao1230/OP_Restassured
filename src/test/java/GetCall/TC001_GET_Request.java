package GetCall;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TC001_GET_Request {

	@Test(priority = 0, enabled = false)
	void getweatherDetails() {
		// Specify base URI
		RestAssured.baseURI = "http://restapi.demoqa.com/utilities/weather/city";

		// Request object
		RequestSpecification httpRequest = RestAssured.given();

		// Response object
		Response response = httpRequest.request(Method.GET, "/Hyderabad");

		// print response in console window

		String responseBody = response.getBody().asString();
		System.out.println("Response Body is:" + responseBody);

		// status code validation
		int statusCode = response.getStatusCode();
		System.out.println("Status code is: " + statusCode);
		Assert.assertEquals(statusCode, 200);

		// status line verification
		String statusLine = response.getStatusLine();
		System.out.println("Status line is:" + statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");

	}

	@Test(priority = 1, enabled = true)
	void googleMapTest() {

		// Specify base URI
		RestAssured.baseURI = "https://maps.googleapis.com";

		// Request object
		RequestSpecification httpRequest = RestAssured.given();

		// Response object
		Response response = httpRequest.request(Method.GET,
				"/maps/api/place/nearbysearch/xml?location=-33.8670522,151.1957362&radius=1500&type=supermarket&key=AIzaSyBjGCE3VpLU4lgTqSTDmHmJ2HoELb4Jy1s");
		System.out.println();
		System.out.println("*******************");
		// print response in console window
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is:" + responseBody);
		System.out.println("*******************");
		System.out.println("Response Body is printed");

		// validating headers
		String contentType = response.header("Content-Type");// capture details of Content-Type header
		System.out.println("Content Type is::::" + contentType);
		Assert.assertEquals(contentType, "application/xml; charset=UTF-8");

		String contentEncoding = response.header("Content-Encoding");// capture details of Content-Encoding header
		System.out.println("Content Encoding is::::" + contentEncoding);
		Assert.assertEquals(contentEncoding, "gzip");

		// Status line info
		String statusLine = response.getStatusLine();
		System.out.println("STATUS LINE IS: " + statusLine);
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK", "Status line compared successfully");

		System.out.println("STATUS CODE IS: " + response.statusCode());

	}

	@Test(priority = 2, enabled = true)
	public void guru99Datachecking() {
		getResponseBody();
		getResponseStatus();
		System.out.println("Last***************Data");
		getSpecificPartOfResponseBody();
	}

	// Guru99 input data

	final static String url = "http://demo.guru99.com/V4/sinkministatement.php?CUSTOMER_ID=68195&PASSWORD=1234!&Account_No=1";

	// This will fetch the response body as is and log it. given and when are
	// optional here
	public static void getResponseBody() {
		given().when().get(url).then().log().all();
		System.out.println("Displayed all the log information");
		given().queryParam("CUSTOMER_ID", "68195").queryParam("PASSWORD", "1234!").queryParam("Account_No", "1").when()
				.get("http://demo.guru99.com/V4/sinkministatement.php").then().log().body();
		System.out.println("DISPLAYED THE ALL BODY INFORMATION");
		given().when().get(url).then().assertThat().statusCode(200);

		String statusline = given().when().get(url).statusLine();
		System.out.println("ENd URI conetnt type is: " + statusline);

		System.out.println("Content type is::::" + get(url).contentType());
		System.out.println("Hash Code is::::" + get(url).hashCode());
		System.out.println("Time is:  " + get(url).time());
		// Header Value
		System.out.println("Header Value IS:::::" + get(url).then().extract().headers());
		// response time
		System.out.println(
				"The time taken to fetch the response " + get(url).timeIn(TimeUnit.MILLISECONDS) + " MILLISECONDS");
		System.out.println();
	}

	public static void getResponseStatus() {
		int statusCode = given().queryParam("username", "sathish").queryParam("password", "admin@123!").when()
				.get("https://hrmsapiqa.onpassive.com/admin/auth/signin").getStatusCode();
		System.out.println("The response status is " + statusCode);

	}
	
	public static void getSpecificPartOfResponseBody(){

		ArrayList<String> amounts = when().get(url).then().extract().path("result.statements.AMOUNT") ;
		int sumOfAll=0;
		for(String a:amounts){

		    System.out.println("The amount value fetched is "+a);
		    sumOfAll=sumOfAll+Integer.valueOf(a);
		}
		System.out.println("The total amount is "+sumOfAll);

		}
}
