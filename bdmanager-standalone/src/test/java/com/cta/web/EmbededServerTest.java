package com.cta.web;

import static com.jayway.restassured.RestAssured.given;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class EmbededServerTest {

	@BeforeClass
	public static void startEmbeddedServer() {
		BdManagerEmbededServer.main(new String[] {});
	}
	
	@AfterClass
	public static void stopEmbeddedServer() {
		BdManagerEmbededServer.stopServer(BdManagerEmbededServer.getServer());
	}
	
	@Test
	public void testEmbedServerStart() {
		 given()
		 .expect().response().statusCode(200)
		.when().get("/test/echo");
	}
}
